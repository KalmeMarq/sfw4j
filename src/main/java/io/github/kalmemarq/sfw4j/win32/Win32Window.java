package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.Window;
import io.github.kalmemarq.sfw4j.hints.WindowHint;
import io.github.kalmemarq.sfw4j.hints.WindowHintBag;
import io.github.kalmemarq.sfw4j.callbacks.CursorPosCallback;
import io.github.kalmemarq.sfw4j.callbacks.KeyCallback;
import io.github.kalmemarq.sfw4j.callbacks.MouseButtonCallback;
import io.github.kalmemarq.sfw4j.win32.Win32.MSG;
import io.github.kalmemarq.sfw4j.win32.Win32.PIXELFORMATDESCRIPTOR;
import io.github.kalmemarq.sfw4j.win32.Win32.RECT;
import io.github.kalmemarq.sfw4j.win32.Win32.WNDCLASSEXW;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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

    private KeyCallback keyCallback;
    private MouseButtonCallback mouseButtonCallback;
    private CursorPosCallback cursorPosCallback;

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
        this.id = WINDOW_ID.addAndGet(1);
        WINDOWS.put(this.id, this);

        initialize();

        try (Arena arena = Arena.ofConfined()) {
            WNDCLASSEXW wc = new WNDCLASSEXW(arena);
            wc.cbSize((int) WNDCLASSEXW.SIZE);
            wc.style(Win32.CS_HREDRAW | Win32.CS_VREDRAW | Win32.CS_OWNDC);
            wc.hInstance(Win32.GetModuleHandleW(MemorySegment.NULL));
            wc.lpfnWndProc(wndProcMemSeg);
            wc.lpszClassName(arena.allocateFrom(winClassName, StandardCharsets.UTF_16LE));
            wc.cbClsExtra((int) ValueLayout.JAVA_LONG.byteSize());

            Win32.RegisterClassExW(wc.memorySegment());

            RECT rect = new RECT(arena);
            rect.right(width);
            rect.bottom(height);

            int windowStyle = Win32.WS_OVERLAPPED | Win32.WS_CAPTION | Win32.WS_SYSMENU;
            if (hintBag.getOr(WindowHint.MINIMIZABLE, true)) {
                windowStyle |= Win32.WS_MINIMIZEBOX;
            }
            if (hintBag.getOr(WindowHint.MAXIMIZABLE, true)) {
                windowStyle |= Win32.WS_MAXIMIZEBOX;
            }
            if (hintBag.getOr(WindowHint.RESIZEABLE, true)) {
                windowStyle |= Win32.WS_THICKFRAME;
            }

            if (hintBag.getOr(WindowHint.VISIBLE, true)) {
                windowStyle |= Win32.WS_VISIBLE;
            }
            if (hintBag.getOr(WindowHint.MINIMIZED, false)) {
                windowStyle |= Win32.WS_MINIMIZE;
            }
            if (hintBag.getOr(WindowHint.MAXIMIZED, false)) {
                windowStyle |= Win32.WS_MAXIMIZE;
            }

            Win32.AdjustWindowRect(rect.memorySegment(), windowStyle, false);

            this.hwnd = Win32.CreateWindowExW(
                    0,
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
            Win32.SetPropA(this.hwnd, ID_PROP_MEM_SEG, this.idMemSeg);

            this.dc = Win32.GetDC(this.hwnd);

            int redBits = hintBag.getOr(WindowHint.RED_BITS, 8);
            int greenBits = hintBag.getOr(WindowHint.GREEN_BITS, 8);
            int blueBits = hintBag.getOr(WindowHint.BLUE_BITS, 8);
            int alphaBits = hintBag.getOr(WindowHint.ALPHA_BITS, 8);

            PIXELFORMATDESCRIPTOR pfd = new PIXELFORMATDESCRIPTOR(arena);
            pfd.nSize((short) PIXELFORMATDESCRIPTOR.SIZE);
            pfd.nVersion((short) 1);
            pfd.dwFlags(Win32.PFD_DRAW_TO_WINDOW | Win32.PFD_SUPPORT_OPENGL | Win32.PFD_DOUBLEBUFFER);
            pfd.iPixelType((byte) Win32.PFD_TYPE_RGBA);
            pfd.cAccumRedBits((byte) (int) hintBag.getOr(WindowHint.ACCUM_RED_BITS, 0));
            pfd.cAccumGreenBits((byte) (int) hintBag.getOr(WindowHint.ACCUM_GREEN_BITS, 0));
            pfd.cAccumBlueBits((byte) (int) hintBag.getOr(WindowHint.ACCUM_BLUE_BITS, 0));
            pfd.cAccumAlphaBits((byte) (int) hintBag.getOr(WindowHint.ACCUM_ALPHA_BITS, 0));
            pfd.cColorBits((byte) (redBits + greenBits + blueBits + alphaBits));
            pfd.cRedBits((byte) redBits);
            pfd.cGreenBits((byte) greenBits);
            pfd.cBlueBits((byte) blueBits);
            pfd.cAlphaBits((byte) alphaBits);
            pfd.cDepthBits((byte) (int) hintBag.getOr(WindowHint.DEPTH_BITS, 24));
            pfd.cStencilBits((byte) (int) hintBag.getOr(WindowHint.STENCIL_BITS, 8));
            pfd.iLayerType((byte) Win32.PFD_MAIN_PLANE);

            int pixel_format = Win32.ChoosePixelFormat(this.dc, pfd.memorySegment());
            Win32.SetPixelFormat(this.dc, pixel_format, pfd.memorySegment());
            this.dummy_context = Win32.wglCreateContext(this.dc);
            this.makeContextCurrent();

            Win32.UpdateWindow(this.hwnd);
        }
    }

    @Override
    public void setKeyCallback(KeyCallback callback) {
        this.keyCallback = callback;
    }

    @Override
    public void setMouseButtonCallback(MouseButtonCallback callback) {
        this.mouseButtonCallback = callback;
    }

    @Override
    public void setCursorPosCallback(CursorPosCallback callback) {
        this.cursorPosCallback = callback;
    }

    public void show() {
        Win32.ShowWindow(this.hwnd, Win32.SW_SHOW);
        Win32.UpdateWindow(this.hwnd);
    }

    public void hide() {
        Win32.ShowWindow(this.hwnd, Win32.SW_HIDE);
        Win32.UpdateWindow(this.hwnd);
    }

    public void minimize() {
        Win32.ShowWindow(this.hwnd, Win32.SW_MINIMIZE);
        Win32.UpdateWindow(this.hwnd);
    }

    public void maximize() {
        Win32.ShowWindow(this.hwnd, Win32.SW_MAXIMIZE);
        Win32.UpdateWindow(this.hwnd);
    }

    public void makeContextCurrent() {
        Win32.wglMakeCurrent(this.dc, this.dummy_context);
    }

    public void pollEvents() {
        while (Win32.PeekMessageW(this.msg.memorySegment(), MemorySegment.NULL, 0, 0, Win32.PM_REMOVE)) {
            if (this.msg.message() == Win32.WM_QUIT) {
                this.shouldClose = true;
            } else {
                Win32.TranslateMessage(this.msg.memorySegment());
                Win32.DispatchMessageW(this.msg.memorySegment());
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
        Win32.SwapBuffers(this.dc);
    }

    @Override
    public void close() {
        Win32.wglMakeCurrent(this.dc, MemorySegment.NULL);
        Win32.DestroyWindow(this.hwnd);
        WINDOWS.remove(this.id);
    }

    private long wndProc(MemorySegment window, int msg, long wparam, long lparam) {
        switch (msg) {
            case Win32.WM_CLOSE, Win32.WM_DESTROY -> {
                this.shouldClose = true;
                return 0;
            }
            case Win32.WM_LBUTTONDOWN, Win32.WM_LBUTTONUP -> {
                if (this.mouseButtonCallback != null) {
                    this.mouseButtonCallback.invoke((short) ((lparam) & 0xFFFFL), (short) ((lparam >> 16) & 0xFFFFL), 0, msg == Win32.WM_LBUTTONDOWN);
                }
            }
            case Win32.WM_RBUTTONDOWN, Win32.WM_RBUTTONUP -> {
                if (this.mouseButtonCallback != null) {
                    this.mouseButtonCallback.invoke((short) ((lparam) & 0xFFFFL), (short) ((lparam >> 16) & 0xFFFFL), 1, msg == Win32.WM_RBUTTONDOWN);
                }
            }
            case Win32.WM_MBUTTONDOWN, Win32.WM_MBUTTONUP -> {
                if (this.mouseButtonCallback != null) {
                    this.mouseButtonCallback.invoke((short) ((lparam) & 0xFFFFL), (short) ((lparam >> 16) & 0xFFFFL), 2, msg == Win32.WM_MBUTTONDOWN);
                }
            }
            case Win32.WM_XBUTTONDOWN, Win32.WM_XBUTTONUP -> {
                if (this.mouseButtonCallback != null) {
                    this.mouseButtonCallback.invoke((short) ((lparam) & 0xFFFFL), (short) ((lparam >> 16) & 0xFFFFL), (int) ((wparam >> 16) & 0xFFFFL) + 2, msg == Win32.WM_XBUTTONDOWN);
                }
            }
            case Win32.WM_MOUSEMOVE -> {
                if (this.cursorPosCallback != null) {
                    this.cursorPosCallback.invoke((short) ((lparam) & 0xFFFFL), (short) ((lparam >> 16) & 0xFFFFL));
                }
            }
            case Win32.WM_SYSKEYDOWN, Win32.WM_KEYDOWN -> {

            }
            case Win32.WM_SYSKEYUP, Win32.WM_KEYUP -> {

            }
            default -> {
//                IO.println("MSG: " + msg);
            }
        }

        return Win32.DefWindowProcW(window, msg, wparam, lparam);
    }

    private static long globalWndProc(MemorySegment hwnd, int msg, long wParam, long lParam) {
        MemorySegment hData = Win32.GetPropA(hwnd, ID_PROP_MEM_SEG);
        if (hData.address() != MemorySegment.NULL.address()) {
            long id = hData.reinterpret(ValueLayout.JAVA_LONG.byteSize()).get(ValueLayout.JAVA_LONG, 0L);
            Win32Window window = WINDOWS.get(id);

            if (window != null) {
                return window.wndProc(hwnd, msg, wParam, lParam);
            }
        }

        return switch (msg) {
            case Win32.WM_CLOSE, Win32.WM_DESTROY -> {
                Win32.PostQuitMessage(0);
                yield 0;
            }
            default -> Win32.DefWindowProcW(hwnd, msg, wParam, lParam);
        };
    }
}
