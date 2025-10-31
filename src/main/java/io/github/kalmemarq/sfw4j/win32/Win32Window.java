package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.MemoryStack;
import io.github.kalmemarq.sfw4j.Theme;
import io.github.kalmemarq.sfw4j.Window;
import io.github.kalmemarq.sfw4j.WindowEventListener;
import io.github.kalmemarq.sfw4j.hints.OpenGlHints;
import io.github.kalmemarq.sfw4j.hints.WindowHint;
import io.github.kalmemarq.sfw4j.hints.WindowHintBag;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Win32Window implements Window {
    private static final AtomicLong WINDOW_ID = new AtomicLong(0);
    private static final Map<Long, Win32Window> WINDOWS = new HashMap<>();
    private static final MemorySegment ID_PROP_MEM_SEG = Arena.global().allocateFrom("SFW4J", StandardCharsets.US_ASCII);

    private static String winClassName = "SFW4J_Class";
    private static MethodHandle wndProcMthHdl;
    private static MemorySegment wndProcMemSeg;

    private final long id;
    private final MemorySegment idMemSeg;

    private final MemorySegment hwnd;
    private final MemorySegment dc;
    private final MemorySegment dummy_context;

    private boolean shouldClose;
    private final MSG msg = new MSG(Arena.global());

    private final List<WindowEventListener> listeners;
    private Theme theme = Theme.Light.DEFAULT;

    public static void setWindowClassName(String className) {
        winClassName = className;
    }

    protected static void initialize() {
        if (wndProcMthHdl == null) {
            Linker linker = Linker.nativeLinker();
            try {
                wndProcMthHdl = MethodHandles.lookup().findStatic(Win32Window.class, "globalWndProc", MethodType.methodType(long.class, MemorySegment.class, int.class, long.class, long.class));
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            wndProcMemSeg = linker.upcallStub(wndProcMthHdl, FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG), Arena.global());
        }
    }

    public Win32Window(int width, int height, String title, WindowHintBag hintBag) {
        this.listeners = new ArrayList<>();

        this.id = WINDOW_ID.addAndGet(1);
        WINDOWS.put(this.id, this);

        initialize();

        try (Arena arena = Arena.ofConfined()) {
            WNDCLASSEXW wc = new WNDCLASSEXW(arena);
            wc.cbSize((int) WNDCLASSEXW.SIZE);
            wc.style(Win32.CS_HREDRAW | Win32.CS_VREDRAW | Win32.CS_OWNDC);
            wc.hInstance(Kernel32.INSTANCE.GetModuleHandleW(MemorySegment.NULL));
            wc.lpfnWndProc(wndProcMemSeg);
            wc.lpszClassName(arena.allocateFrom(winClassName, StandardCharsets.UTF_16LE));
            wc.cbClsExtra((int) ValueLayout.JAVA_LONG.byteSize());

            User32.INSTANCE.RegisterClassExW(wc.memorySegment());

            RECT rect = new RECT(arena);
            rect.right(width);
            rect.bottom(height);

            int windowStyle = Win32.WS_OVERLAPPED | Win32.WS_CAPTION | Win32.WS_SYSMENU | Win32.WS_THICKFRAME;
            int windowExStyle = Win32.WS_EX_APPWINDOW;

            if (hintBag.getOr(WindowHint.MINIMIZABLE, true)) {
                windowStyle |= Win32.WS_MINIMIZEBOX;
            }
            if (hintBag.getOr(WindowHint.MAXIMIZABLE, true)) {
                windowStyle |= Win32.WS_MAXIMIZEBOX;
            }
            if (!hintBag.getOr(WindowHint.RESIZEABLE, true)) {
                windowStyle ^= Win32.WS_THICKFRAME;
                windowStyle ^= Win32.WS_MAXIMIZEBOX;
            }
            if (hintBag.getOr(WindowHint.TOPMOST, false)) {
                windowExStyle |= Win32.WS_EX_TOPMOST;
            }

            if (hintBag.getOr(WindowHint.MINIMIZED, false)) {
                windowStyle |= Win32.WS_MINIMIZE;
            }
            if (hintBag.getOr(WindowHint.MAXIMIZED, false)) {
                windowStyle |= Win32.WS_MAXIMIZE;
            }

            User32.INSTANCE.AdjustWindowRect(rect.memorySegment(), windowStyle, 0);

            this.hwnd = User32.INSTANCE.CreateWindowExW(
                    windowExStyle,
                    wc.lpszClassName(),
                    arena.allocateFrom(title, StandardCharsets.UTF_16LE),
                    windowStyle,
                    Win32.CW_USEDEFAULT,
                    Win32.CW_USEDEFAULT,
                    rect.right() - rect.left(),
                    rect.bottom() - rect.top(),
                    MemorySegment.NULL,
                    MemorySegment.NULL,
                    wc.hInstance(),
                    MemorySegment.NULL
            );

            this.idMemSeg = Arena.global().allocateFrom(ValueLayout.JAVA_LONG, this.id);
            User32.INSTANCE.SetPropA(this.hwnd, ID_PROP_MEM_SEG, this.idMemSeg);
            if (Win32.isUsingDarkTheme()) {
                Dwmapi.INSTANCE.DwmSetWindowAttribute(this.hwnd, 20, arena.allocateFrom(ValueLayout.JAVA_INT, 1), (int) ValueLayout.JAVA_INT.byteSize());
                this.theme = Theme.Dark.DEFAULT;
            }

            this.dc = User32.INSTANCE.GetDC(this.hwnd);

            int redBits = hintBag.getOr(OpenGlHints.RED_BITS, 8);
            int greenBits = hintBag.getOr(OpenGlHints.GREEN_BITS, 8);
            int blueBits = hintBag.getOr(OpenGlHints.BLUE_BITS, 8);
            int alphaBits = hintBag.getOr(OpenGlHints.ALPHA_BITS, 8);

            PIXELFORMATDESCRIPTOR pfd = new PIXELFORMATDESCRIPTOR(arena);
            pfd.nSize((short) PIXELFORMATDESCRIPTOR.SIZE);
            pfd.nVersion((short) 1);
            pfd.dwFlags(Win32.PFD_DRAW_TO_WINDOW | Win32.PFD_SUPPORT_OPENGL | Win32.PFD_DOUBLEBUFFER);
            pfd.iPixelType((byte) Win32.PFD_TYPE_RGBA);
            pfd.cAccumRedBits((byte) (int) hintBag.getOr(OpenGlHints.ACCUM_RED_BITS, 0));
            pfd.cAccumGreenBits((byte) (int) hintBag.getOr(OpenGlHints.ACCUM_GREEN_BITS, 0));
            pfd.cAccumBlueBits((byte) (int) hintBag.getOr(OpenGlHints.ACCUM_BLUE_BITS, 0));
            pfd.cAccumAlphaBits((byte) (int) hintBag.getOr(OpenGlHints.ACCUM_ALPHA_BITS, 0));
            pfd.cColorBits((byte) (redBits + greenBits + blueBits + alphaBits));
            pfd.cRedBits((byte) redBits);
            pfd.cGreenBits((byte) greenBits);
            pfd.cBlueBits((byte) blueBits);
            pfd.cAlphaBits((byte) alphaBits);
            pfd.cDepthBits((byte) (int) hintBag.getOr(OpenGlHints.DEPTH_BITS, 24));
            pfd.cStencilBits((byte) (int) hintBag.getOr(OpenGlHints.STENCIL_BITS, 8));
            pfd.iLayerType((byte) Win32.PFD_MAIN_PLANE);

            int pixel_format = Gdi32.INSTANCE.ChoosePixelFormat(this.dc, pfd.memorySegment());
            Gdi32.INSTANCE.SetPixelFormat(this.dc, pixel_format, pfd.memorySegment());
            this.dummy_context = OpenGl32.INSTANCE.wglCreateContext(this.dc);
            this.makeContextCurrent();

            if (hintBag.getOr(WindowHint.VISIBLE, true)) {
                User32.INSTANCE.ShowWindow(this.hwnd, Win32.SW_SHOW);
            }
            User32.INSTANCE.UpdateWindow(this.hwnd);
        }
    }

    @Override
    public Theme getTheme() {
        return this.theme;
    }

    @Override
    public void addEventListener(WindowEventListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeEventListener(WindowEventListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public void setTitle(String title) {
        try (Arena arena = Arena.ofConfined()) {
            User32.INSTANCE.SetWindowTextW(this.hwnd, arena.allocateFrom(title, StandardCharsets.UTF_16LE));
        }
    }

    public void show() {
        User32.INSTANCE.ShowWindow(this.hwnd, Win32.SW_SHOW);
        User32.INSTANCE.UpdateWindow(this.hwnd);
    }

    public void hide() {
        User32.INSTANCE.ShowWindow(this.hwnd, Win32.SW_HIDE);
        User32.INSTANCE.UpdateWindow(this.hwnd);
    }

    public void minimize() {
        User32.INSTANCE.ShowWindow(this.hwnd, Win32.SW_MINIMIZE);
        User32.INSTANCE.UpdateWindow(this.hwnd);
    }

    public void maximize() {
        User32.INSTANCE.ShowWindow(this.hwnd, Win32.SW_MAXIMIZE);
        User32.INSTANCE.UpdateWindow(this.hwnd);
    }

    public void makeContextCurrent() {
        OpenGl32.INSTANCE.wglMakeCurrent(this.dc, this.dummy_context);
    }

    public void pollEvents() {
        while (User32.INSTANCE.PeekMessageW(this.msg.memorySegment(), MemorySegment.NULL, 0, 0, Win32.PM_REMOVE) != 0) {
            if (this.msg.message() == Win32.WM_QUIT) {
                this.shouldClose = true;
            } else {
                User32.INSTANCE.TranslateMessage(this.msg.memorySegment());
                User32.INSTANCE.DispatchMessageW(this.msg.memorySegment());
            }
        }
    }

    public boolean shouldClose() {
        return this.shouldClose;
    }

    @Override
    public void setShouldClose(boolean shouldClose) {
        this.shouldClose = shouldClose;
    }

    public void swapBuffers() {
        Gdi32.INSTANCE.SwapBuffers(this.dc);
    }

    @Override
    public void close() {
        OpenGl32.INSTANCE.wglMakeCurrent(this.dc, MemorySegment.NULL);
        User32.INSTANCE.DestroyWindow(this.hwnd);
        WINDOWS.remove(this.id);
    }

    private long wndProc(MemorySegment window, int msg, long wparam, long lparam) {
        switch (msg) {
            case Win32.WM_CLOSE, Win32.WM_DESTROY -> {
                this.shouldClose = true;
                return 0;
            }
            case Win32.WM_LBUTTONDOWN, Win32.WM_RBUTTONDOWN, Win32.WM_MBUTTONDOWN, Win32.WM_XBUTTONDOWN,
                 Win32.WM_LBUTTONUP, Win32.WM_RBUTTONUP, Win32.WM_MBUTTONUP, Win32.WM_XBUTTONUP -> {
                int x = (short) ((lparam) & 0xFFFFL);
                int y = (short) ((lparam >> 16) & 0xFFFFL);

                int button = switch (msg) {
                    case Win32.WM_LBUTTONDOWN, Win32.WM_LBUTTONUP -> 0;
                    case Win32.WM_RBUTTONDOWN, Win32.WM_RBUTTONUP -> 1;
                    case Win32.WM_MBUTTONDOWN, Win32.WM_MBUTTONUP -> 2;
                    default -> (int) (((wparam >> 16) & 0xFFFFL) + 2);
                };

                boolean pressed = msg == Win32.WM_LBUTTONDOWN || msg ==  Win32.WM_RBUTTONDOWN || msg == Win32.WM_MBUTTONDOWN || msg == Win32.WM_XBUTTONDOWN;

                for (var listener : this.listeners) {
                    listener.onMouseButton(x, y, button, pressed);
                }
            }
            case Win32.WM_MOUSEMOVE -> {
                for (var listener : this.listeners) {
                    listener.onCursorPos((short) ((lparam) & 0xFFFFL), (short) ((lparam >> 16) & 0xFFFFL));
                }
            }
            case Win32.WM_SYSKEYDOWN, Win32.WM_KEYDOWN -> {
            }
            case Win32.WM_SYSKEYUP, Win32.WM_KEYUP -> {
            }
            case Win32.WM_SYSCHAR, Win32.WM_CHAR -> {
                int codepoint = (int) wparam;

                for (var listener : this.listeners) {
                    listener.onChar(codepoint);
                }
            }
            case Win32.WM_SETTINGCHANGE -> {
                String name = MemorySegment.ofAddress(lparam).reinterpret(128).getString(0, StandardCharsets.UTF_16LE);
                if ("ImmersiveColorSet".equals(name)) {
                    boolean isDarkTheme = Win32.isUsingDarkTheme();
                    Theme theme = isDarkTheme ? Theme.Dark.DEFAULT : Theme.Light.DEFAULT;

                    if (theme != this.theme) {
                        this.theme = theme;
                        try (MemoryStack stack = MemoryStack.stackPush()) {
                            Dwmapi.INSTANCE.DwmSetWindowAttribute(this.hwnd, 20, stack.allocateFrom(ValueLayout.JAVA_INT, isDarkTheme ? 1 : 0), (int) ValueLayout.JAVA_INT.byteSize());
                        }
                        for (var listener : this.listeners) {
                            listener.onTheme(theme);
                        }
                    }
                }
            }
            default -> {
//                IO.println("MSG: " + msg);
            }
        }

        return User32.INSTANCE.DefWindowProcW(window, msg, wparam, lparam);
    }

    private static long globalWndProc(MemorySegment hwnd, int msg, long wParam, long lParam) {
        MemorySegment hData = User32.INSTANCE.GetPropA(hwnd, ID_PROP_MEM_SEG);
        if (hData.address() != MemorySegment.NULL.address()) {
            long id = hData.reinterpret(ValueLayout.JAVA_LONG.byteSize()).get(ValueLayout.JAVA_LONG, 0L);
            Win32Window window = WINDOWS.get(id);

            if (window != null) {
                return window.wndProc(hwnd, msg, wParam, lParam);
            }
        }

        return switch (msg) {
            case Win32.WM_CLOSE, Win32.WM_DESTROY -> {
                User32.INSTANCE.PostQuitMessage(0);
                yield 0;
            }
            default -> User32.INSTANCE.DefWindowProcW(hwnd, msg, wParam, lParam);
        };
    }
}
