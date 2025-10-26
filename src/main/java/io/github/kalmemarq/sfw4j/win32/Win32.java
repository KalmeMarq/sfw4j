package io.github.kalmemarq.sfw4j.win32;

import java.lang.foreign.*;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

public class Win32 {
    public static final int CS_VREDRAW = 0x0001;
    public static final int CS_HREDRAW = 0x0002;
    public static final int CS_OWNDC = 0x0020;
    public static final int WS_MINIMIZE = 0x20000000;
    public static final int WS_MAXIMIZE = 0x01000000;
    public static final int WS_VISIBLE = 0x10000000;
    public static final int WS_OVERLAPPED = 0x00000000;
    public static final int WS_CAPTION = 0x00C00000;
    public static final int WS_SYSMENU = 0x00080000;
    public static final int WS_THICKFRAME = 0x00040000;
    public static final int WS_MINIMIZEBOX = 0x00020000;
    public static final int WS_MAXIMIZEBOX = 0x00010000;
    public static final int CW_USEDEFAULT = 0x80000000;
    public static final int PFD_TYPE_RGBA = 0;
    public static final int PFD_MAIN_PLANE = 0;
    public static final int PFD_DOUBLEBUFFER = 1;
    public static final int PFD_DRAW_TO_WINDOW = 4;
    public static final int PFD_SUPPORT_OPENGL = 0;
    public static final int WM_CLOSE = 0x0010;
    public static final int WM_DESTROY = 0x0002;
    public static final int SW_SHOW = 5;
    public static final int SW_HIDE = 0;
    public static final int SW_MAXIMIZE = 3;
    public static final int SW_MINIMIZE = 6;
    public static final int PM_REMOVE = 0x0001;
    public static final int WM_QUIT = 0x0012;
    public static final int WM_LBUTTONDOWN = 0x0201;
    public static final int WM_LBUTTONUP = 0x0202;
    public static final int WM_RBUTTONDOWN = 0x0204;
    public static final int WM_RBUTTONUP = 0x0205;
    public static final int WM_MBUTTONDOWN = 0x0207;
    public static final int WM_MBUTTONUP = 0x0208;
    public static final int WM_XBUTTONDOWN = 0x020B;
    public static final int WM_XBUTTONUP = 0x020C;
    public static final int WM_MOUSEMOVE = 0x0200;
    public static final int WM_SYSKEYDOWN = 0x0104;
    public static final int WM_SYSKEYUP = 0x0105;
    public static final int WM_KEYDOWN = 0x0100;
    public static final int WM_KEYUP = 0x0101;

    public static MemorySegment GetModuleHandleW(MemorySegment lpModuleName) {
        try {
            return (MemorySegment) GetModuleHandleW_MthHdl.invokeExact(lpModuleName);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static short RegisterClassExW(MemorySegment clazz) {
        try {
            return (short) RegisterClassExW_MthHdl.invokeExact(clazz);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean AdjustWindowRect(MemorySegment lpRect, int dwStyle, boolean bMenu) {
        try {
            return ((int) AdjustWindowRect_MthHdl.invokeExact(lpRect, dwStyle, bMenu ? 1 : 0)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment CreateWindowExW(int dwExStyle, MemorySegment lpClassName, MemorySegment lpWindowName, int dwStyle, int x, int y, int nWidth, int nHeight, MemorySegment hWndParent, MemorySegment hMenu, MemorySegment hInstance, MemorySegment lpParam) {
        try {
            return (MemorySegment) CreateWindowExW_MthHdl.invokeExact(dwExStyle, lpClassName, lpWindowName, dwStyle, x, y, nWidth, nHeight, hWndParent, hMenu, hInstance, lpParam);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment GetDC(MemorySegment hWnd) {
        try {
            return (MemorySegment) GetDC_MthHdl.invokeExact(hWnd);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static int ChoosePixelFormat(MemorySegment hdc, MemorySegment ppfd) {
        try {
            return (int) ChoosePixelFormat_MthHdl.invokeExact(hdc, ppfd);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static int SetPixelFormat(MemorySegment hdc, int format, MemorySegment ppfd) {
        try {
            return (int) SetPixelFormat_MthHdl.invokeExact(hdc, format, ppfd);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment wglCreateContext(MemorySegment hdc) {
        try {
            return (MemorySegment) wglCreateContext_MthHdl.invokeExact(hdc);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean wglMakeCurrent(MemorySegment hdc, MemorySegment ctx) {
        try {
            return ((int) wglMakeCurrent_MthHdl.invokeExact(hdc, ctx)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean ShowWindow(MemorySegment hWnd, int nCmdShow) {
        try {
            return ((int) ShowWindow_MthHdl.invokeExact(hWnd, nCmdShow)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean UpdateWindow(MemorySegment hWnd) {
        try {
            return ((int) UpdateWindow_MthHdl.invokeExact(hWnd)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void PostQuitMessage(int nExitCode) {
        try {
            PostQuitMessage_MthHdl.invokeExact(nExitCode);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static long DefWindowProcW(MemorySegment hWnd, int msg, long wParam, long lParam) {
        try {
            return (long) DefWindowProcW_MthHdl.invokeExact(hWnd, msg, wParam, lParam);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean PeekMessageW(MemorySegment lpMsg, MemorySegment hWnd, int wMsgFilterMin, int wMsgFilterMax, int wRemoveMsg) {
        try {
            return ((int) PeekMessageW_MthHdl.invokeExact(lpMsg, hWnd, wMsgFilterMin, wMsgFilterMax, wRemoveMsg)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean TranslateMessage(MemorySegment lpMsg) {
        try {
            return ((int) TranslateMessage_MthHdl.invokeExact(lpMsg)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static long DispatchMessageW(MemorySegment lpMsg) {
        try {
            return (long) DispatchMessageW_MthHdl.invokeExact(lpMsg);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean SwapBuffers(MemorySegment hdc) {
        try {
            return ((int) SwapBuffers_MthHdl.invokeExact(hdc)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean DestroyWindow(MemorySegment hWnd) {
        try {
            return ((int) DestroyWindow_MthHdl.invokeExact(hWnd)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean UnregisterClassW(MemorySegment lpClassName, MemorySegment hInstance) {
        try {
            return ((int) UnregisterClassW_MthHdl.invokeExact(lpClassName, hInstance)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static MemorySegment GetPropA(MemorySegment hwnd, MemorySegment lpString) {
        try {
            return (MemorySegment) GetPropA_MthHdl.invokeExact(hwnd, lpString);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean SetPropA(MemorySegment hwnd, MemorySegment lpString, MemorySegment hData) {
        try {
            return ((int) SetPropA_MthHdl.invokeExact(hwnd, lpString, hData)) != 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    public record RECT(MemorySegment memorySegment) {
        public static final StructLayout LAYOUT = MemoryLayout.structLayout(
                ValueLayout.JAVA_INT.withName("left"),
                ValueLayout.JAVA_INT.withName("top"),
                ValueLayout.JAVA_INT.withName("right"),
                ValueLayout.JAVA_INT.withName("bottom")
        );
        public static final long SIZE = LAYOUT.byteSize();

        public static final VarHandle LEFT_VH = LAYOUT.varHandle(PathElement.groupElement("left"));
        public static final VarHandle TOP_VH = LAYOUT.varHandle(PathElement.groupElement("top"));
        public static final VarHandle RIGHT_VH = LAYOUT.varHandle(PathElement.groupElement("right"));
        public static final VarHandle BOTTOM_VH = LAYOUT.varHandle(PathElement.groupElement("bottom"));

        public RECT(Arena arena) {
            this(arena.allocate(SIZE).fill((byte) 0));
        }

        public void left(int left) {
            LEFT_VH.set(this.memorySegment, 0L, left);
        }

        public void top(int top) {
            TOP_VH.set(this.memorySegment, 0L, top);
        }

        public void right(int right) {
            RIGHT_VH.set(this.memorySegment, 0L, right);
        }

        public void bottom(int bottom) {
            BOTTOM_VH.set(this.memorySegment, 0L, bottom);
        }

        public int left() {
            return (int) LEFT_VH.get(this.memorySegment, 0L);
        }

        public int top() {
            return (int) TOP_VH.get(this.memorySegment, 0L);
        }

        public int right() {
            return (int) RIGHT_VH.get(this.memorySegment, 0L);
        }

        public int bottom() {
            return (int) BOTTOM_VH.get(this.memorySegment, 0L);
        }
    }

    public record WNDCLASSEXW(MemorySegment memorySegment) {
        public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("cbSize"),
            ValueLayout.JAVA_INT.withName("style"),
            ValueLayout.ADDRESS.withName("lpfnWndProc"),
            ValueLayout.JAVA_INT.withName("cbClsExtra"),
            ValueLayout.JAVA_INT.withName("cbWndExtra"),
            ValueLayout.ADDRESS.withName("hInstance"),
            ValueLayout.ADDRESS.withName("hIcon"),
            ValueLayout.ADDRESS.withName("hCursor"),
            ValueLayout.ADDRESS.withName("hbrBackground"),
            ValueLayout.ADDRESS.withName("lpszMenuName"),
            ValueLayout.ADDRESS.withName("lpszClassName"),
            ValueLayout.ADDRESS.withName("hIconSm")
        );
        public static final long SIZE = LAYOUT.byteSize();

        public static final VarHandle CBSIZE_VH = LAYOUT.varHandle(PathElement.groupElement("cbSize"));
        public static final VarHandle STYLE_VH = LAYOUT.varHandle(PathElement.groupElement("style"));
        public static final VarHandle LPFNWNDPROC_VH = LAYOUT.varHandle(PathElement.groupElement("lpfnWndProc"));
        public static final VarHandle CBCLSEXTRA_VH = LAYOUT.varHandle(PathElement.groupElement("cbClsExtra"));
        public static final VarHandle CBWNDEXTRA_VH = LAYOUT.varHandle(PathElement.groupElement("cbWndExtra"));
        public static final VarHandle HINSTANCE_VH = LAYOUT.varHandle(PathElement.groupElement("hInstance"));
        public static final VarHandle HICON_VH = LAYOUT.varHandle(PathElement.groupElement("hIcon"));
        public static final VarHandle HCURSOR_VH = LAYOUT.varHandle(PathElement.groupElement("hCursor"));
        public static final VarHandle HBRBACKGROUND_VH = LAYOUT.varHandle(PathElement.groupElement("hbrBackground"));
        public static final VarHandle LPSZMENUNAME_VH = LAYOUT.varHandle(PathElement.groupElement("lpszMenuName"));
        public static final VarHandle LPSZCLASSNAME_VH = LAYOUT.varHandle(PathElement.groupElement("lpszClassName"));
        public static final VarHandle HICONSM_VH = LAYOUT.varHandle(PathElement.groupElement("hIconSm"));

        public WNDCLASSEXW(Arena arena) {
            this(arena.allocate(SIZE).fill((byte) 0));
        }

        public void cbSize(int cbSize) {
            CBSIZE_VH.set(this.memorySegment, 0L, cbSize);
        }

        public void style(int style) {
            STYLE_VH.set(this.memorySegment, 0L, style);
        }

        public void lpfnWndProc(MemorySegment lpfnWndProc) {
            LPFNWNDPROC_VH.set(this.memorySegment, 0L, lpfnWndProc);
        }

        public void cbClsExtra(int cbClsExtra) {
            CBCLSEXTRA_VH.set(this.memorySegment, 0L, cbClsExtra);
        }

        public void cbWndExtra(int cbWndExtra) {
            CBWNDEXTRA_VH.set(this.memorySegment, 0L, cbWndExtra);
        }

        public void hInstance(MemorySegment hInstance) {
            HINSTANCE_VH.set(this.memorySegment, 0L, hInstance);
        }

        public void hIcon(MemorySegment hIcon) {
            HICON_VH.set(this.memorySegment, 0L, hIcon);
        }

        public void hCursor(MemorySegment hCursor) {
            HCURSOR_VH.set(this.memorySegment, 0L, hCursor);
        }

        public void hbrBackground(MemorySegment hbrBackground) {
            HBRBACKGROUND_VH.set(this.memorySegment, 0L, hbrBackground);
        }

        public void lpszMenuName(MemorySegment lpszMenuName) {
            LPSZMENUNAME_VH.set(this.memorySegment, 0L, lpszMenuName);
        }

        public void lpszClassName(MemorySegment lpszClassName) {
            LPSZCLASSNAME_VH.set(this.memorySegment, 0L, lpszClassName);
        }

        public void hIconSm(MemorySegment hIconSm) {
            HICONSM_VH.set(this.memorySegment, 0L, hIconSm);
        }

        public int cbSize() {
            return (int) CBSIZE_VH.get(this.memorySegment, 0L);
        }

        public int style() {
            return (int) STYLE_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment lpfnWndProc() {
            return (MemorySegment) LPFNWNDPROC_VH.get(this.memorySegment, 0L);
        }

        public int cbClsExtra() {
            return (int) CBCLSEXTRA_VH.get(this.memorySegment, 0L);
        }

        public int cbWndExtra() {
            return (int) CBWNDEXTRA_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment hInstance() {
            return (MemorySegment) HINSTANCE_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment hIcon() {
            return (MemorySegment) HICON_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment hCursor() {
            return (MemorySegment) HCURSOR_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment hbrBackground() {
            return (MemorySegment) HBRBACKGROUND_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment lpszMenuName() {
            return (MemorySegment) LPSZMENUNAME_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment lpszClassName() {
            return (MemorySegment) LPSZCLASSNAME_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment hIconSm() {
            return (MemorySegment) HICONSM_VH.get(this.memorySegment, 0L);
        }
    }

    public record PIXELFORMATDESCRIPTOR(MemorySegment memorySegment) {
        public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_SHORT.withName("nSize"),
            ValueLayout.JAVA_SHORT.withName("nVersion"),
            ValueLayout.JAVA_INT.withName("dwFlags"),
            ValueLayout.JAVA_BYTE.withName("iPixelType"),
            ValueLayout.JAVA_BYTE.withName("cColorBits"),
            ValueLayout.JAVA_BYTE.withName("cRedBits"),
            ValueLayout.JAVA_BYTE.withName("cRedShift"),
            ValueLayout.JAVA_BYTE.withName("cGreenBits"),
            ValueLayout.JAVA_BYTE.withName("cGreenShift"),
            ValueLayout.JAVA_BYTE.withName("cBlueBits"),
            ValueLayout.JAVA_BYTE.withName("cBlueShift"),
            ValueLayout.JAVA_BYTE.withName("cAlphaBits"),
            ValueLayout.JAVA_BYTE.withName("cAlphaShift"),
            ValueLayout.JAVA_BYTE.withName("cAccumBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumRedBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumGreenBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumBlueBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumAlphaBits"),
            ValueLayout.JAVA_BYTE.withName("cDepthBits"),
            ValueLayout.JAVA_BYTE.withName("cStencilBits"),
            ValueLayout.JAVA_BYTE.withName("cAuxBuffers"),
            ValueLayout.JAVA_BYTE.withName("iLayerType"),
            ValueLayout.JAVA_BYTE.withName("bReserved"),
            ValueLayout.JAVA_INT.withName("dwLayerMask"),
            ValueLayout.JAVA_INT.withName("dwVisibleMask"),
            ValueLayout.JAVA_INT.withName("dwDamageMask")
        );
        public static final long SIZE = LAYOUT.byteSize();

        public static final VarHandle NSIZE_VH = LAYOUT.varHandle(PathElement.groupElement("nSize"));
        public static final VarHandle NVERSION_VH = LAYOUT.varHandle(PathElement.groupElement("nVersion"));
        public static final VarHandle DWFLAGS_VH = LAYOUT.varHandle(PathElement.groupElement("dwFlags"));
        public static final VarHandle IPIXELTYPE_VH = LAYOUT.varHandle(PathElement.groupElement("iPixelType"));
        public static final VarHandle CCOLORBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cColorBits"));
        public static final VarHandle CREDBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cRedBits"));
        public static final VarHandle CREDSHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cRedShift"));
        public static final VarHandle CGREENBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cGreenBits"));
        public static final VarHandle CGREENSHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cGreenShift"));
        public static final VarHandle CBLUEBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cBlueBits"));
        public static final VarHandle CBLUESHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cBlueShift"));
        public static final VarHandle CALPHABITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAlphaBits"));
        public static final VarHandle CALPHASHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cAlphaShift"));
        public static final VarHandle CACCUMBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumBits"));
        public static final VarHandle CACCUMREDBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumRedBits"));
        public static final VarHandle CACCUMGREENBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumGreenBits"));
        public static final VarHandle CACCUMBLUEBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumBlueBits"));
        public static final VarHandle CACCUMALPHABITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumAlphaBits"));
        public static final VarHandle CDEPTHBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cDepthBits"));
        public static final VarHandle CSTENCILBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cStencilBits"));
        public static final VarHandle CAUXBUFFERS_VH = LAYOUT.varHandle(PathElement.groupElement("cAuxBuffers"));
        public static final VarHandle ILAYERTYPE_VH = LAYOUT.varHandle(PathElement.groupElement("iLayerType"));
        public static final VarHandle BRESERVED_VH = LAYOUT.varHandle(PathElement.groupElement("bReserved"));
        public static final VarHandle DWLAYERMASK_VH = LAYOUT.varHandle(PathElement.groupElement("dwLayerMask"));
        public static final VarHandle DWVISIBLEMASK_VH = LAYOUT.varHandle(PathElement.groupElement("dwVisibleMask"));
        public static final VarHandle DWDAMAGEMASK_VH = LAYOUT.varHandle(PathElement.groupElement("dwDamageMask"));

        public PIXELFORMATDESCRIPTOR(Arena arena) {
            this(arena.allocate(SIZE).fill((byte) 0));
        }

        public void nSize(short nSize) {
            NSIZE_VH.set(this.memorySegment, 0L, nSize);
        }

        public void nVersion(short nVersion) {
            NVERSION_VH.set(this.memorySegment, 0L, nVersion);
        }

        public void dwFlags(int dwFlags) {
            DWFLAGS_VH.set(this.memorySegment, 0L, dwFlags);
        }

        public void iPixelType(byte iPixelType) {
            IPIXELTYPE_VH.set(this.memorySegment, 0L, iPixelType);
        }

        public void cColorBits(byte cColorBits) {
            CCOLORBITS_VH.set(this.memorySegment, 0L, cColorBits);
        }

        public void cRedBits(byte cRedBits) {
            CREDBITS_VH.set(this.memorySegment, 0L, cRedBits);
        }

        public void cRedShift(byte cRedShift) {
            CREDSHIFT_VH.set(this.memorySegment, 0L, cRedShift);
        }

        public void cGreenBits(byte cGreenBits) {
            CGREENBITS_VH.set(this.memorySegment, 0L, cGreenBits);
        }

        public void cGreenShift(byte cGreenShift) {
            CGREENSHIFT_VH.set(this.memorySegment, 0L, cGreenShift);
        }

        public void cBlueBits(byte cBlueBits) {
            CBLUEBITS_VH.set(this.memorySegment, 0L, cBlueBits);
        }

        public void cBlueShift(byte cBlueShift) {
            CBLUESHIFT_VH.set(this.memorySegment, 0L, cBlueShift);
        }

        public void cAlphaBits(byte cAlphaBits) {
            CALPHABITS_VH.set(this.memorySegment, 0L, cAlphaBits);
        }

        public void cAlphaShift(byte cAlphaShift) {
            CALPHASHIFT_VH.set(this.memorySegment, 0L, cAlphaShift);
        }

        public void cAccumBits(byte cAccumBits) {
            CACCUMBITS_VH.set(this.memorySegment, 0L, cAccumBits);
        }

        public void cAccumRedBits(byte cAccumRedBits) {
            CACCUMREDBITS_VH.set(this.memorySegment, 0L, cAccumRedBits);
        }

        public void cAccumGreenBits(byte cAccumGreenBits) {
            CACCUMGREENBITS_VH.set(this.memorySegment, 0L, cAccumGreenBits);
        }

        public void cAccumBlueBits(byte cAccumBlueBits) {
            CACCUMBLUEBITS_VH.set(this.memorySegment, 0L, cAccumBlueBits);
        }

        public void cAccumAlphaBits(byte cAccumAlphaBits) {
            CACCUMALPHABITS_VH.set(this.memorySegment, 0L, cAccumAlphaBits);
        }

        public void cDepthBits(byte cDepthBits) {
            CDEPTHBITS_VH.set(this.memorySegment, 0L, cDepthBits);
        }

        public void cStencilBits(byte cStencilBits) {
            CSTENCILBITS_VH.set(this.memorySegment, 0L, cStencilBits);
        }

        public void cAuxBuffers(byte cAuxBuffers) {
            CAUXBUFFERS_VH.set(this.memorySegment, 0L, cAuxBuffers);
        }

        public void iLayerType(byte iLayerType) {
            ILAYERTYPE_VH.set(this.memorySegment, 0L, iLayerType);
        }

        public void bReserved(byte bReserved) {
            BRESERVED_VH.set(this.memorySegment, 0L, bReserved);
        }

        public void dwLayerMask(int dwLayerMask) {
            DWLAYERMASK_VH.set(this.memorySegment, 0L, dwLayerMask);
        }

        public void dwVisibleMask(int dwVisibleMask) {
            DWVISIBLEMASK_VH.set(this.memorySegment, 0L, dwVisibleMask);
        }

        public void dwDamageMask(int dwDamageMask) {
            DWDAMAGEMASK_VH.set(this.memorySegment, 0L, dwDamageMask);
        }
    }

    public record MSG(MemorySegment memorySegment) {
        public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.ADDRESS.withName("hwnd"),
            ValueLayout.JAVA_INT.withName("message"),
            MemoryLayout.paddingLayout(4),
            ValueLayout.JAVA_LONG.withName("wParam"),
            ValueLayout.JAVA_LONG.withName("lParam"),
            ValueLayout.JAVA_INT.withName("time"),
            MemoryLayout.paddingLayout(4),
            ValueLayout.ADDRESS.withName("pt")
        );

        public static final long SIZE = LAYOUT.byteSize();

        public static final VarHandle HWND_VH = LAYOUT.varHandle(PathElement.groupElement("hwnd"));
        public static final VarHandle MESSAGE_VH = LAYOUT.varHandle(PathElement.groupElement("message"));
        public static final VarHandle WPARAM_VH = LAYOUT.varHandle(PathElement.groupElement("wParam"));
        public static final VarHandle LPARAM_VH = LAYOUT.varHandle(PathElement.groupElement("lParam"));
        public static final VarHandle TIME_VH = LAYOUT.varHandle(PathElement.groupElement("time"));
        public static final VarHandle PT_VH = LAYOUT.varHandle(PathElement.groupElement("pt"));

        public MSG(Arena arena) {
            this(arena.allocate(SIZE).fill((byte) 0));
        }

        public void hwnd(MemorySegment hwnd) {
            HWND_VH.set(this.memorySegment, 0L, hwnd);
        }

        public void message(int message) {
            MESSAGE_VH.set(this.memorySegment, 0L, message);
        }

        public void wParam(long wParam) {
            WPARAM_VH.set(this.memorySegment, 0L, wParam);
        }

        public void lParam(long lParam) {
            LPARAM_VH.set(this.memorySegment, 0L, lParam);
        }

        public void time(int time) {
            TIME_VH.set(this.memorySegment, 0L, time);
        }

        public void pt(MemorySegment pt) {
            PT_VH.set(this.memorySegment, 0L, pt);
        }

        public MemorySegment hwnd() {
            return (MemorySegment) HWND_VH.get(this.memorySegment, 0L);
        }

        public int message() {
            return (int) MESSAGE_VH.get(this.memorySegment, 0L);
        }

        public long wParam() {
            return (long) WPARAM_VH.get(this.memorySegment, 0L);
        }

        public long lParam() {
            return (long) LPARAM_VH.get(this.memorySegment, 0L);
        }

        public int time() {
            return (int) TIME_VH.get(this.memorySegment, 0L);
        }

        public MemorySegment pt() {
            return (MemorySegment) PT_VH.get(this.memorySegment, 0L);
        }
    }

    public static final FunctionDescriptor GetModuleHandleW_FnDesc = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment GetModuleHandleW_MemSeg;
    public static final MethodHandle GetModuleHandleW_MthHdl;

    public static final FunctionDescriptor RegisterClassExW_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_SHORT, ValueLayout.ADDRESS);
    public static final MemorySegment RegisterClassExW_MemSeg;
    public static final MethodHandle RegisterClassExW_MthHdl;

    public static final FunctionDescriptor AdjustWindowRect_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT);
    public static final MemorySegment AdjustWindowRect_MemSeg;
    public static final MethodHandle AdjustWindowRect_MthHdl;

    public static final FunctionDescriptor CreateWindowExW_FnDesc = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment CreateWindowExW_MemSeg;
    public static final MethodHandle CreateWindowExW_MthHdl;

    public static final FunctionDescriptor GetDC_FnDesc = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment GetDC_MemSeg;
    public static final MethodHandle GetDC_MthHdl;

    public static final FunctionDescriptor ChoosePixelFormat_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment ChoosePixelFormat_MemSeg;
    public static final MethodHandle ChoosePixelFormat_MthHdl;

    public static final FunctionDescriptor SetPixelFormat_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.ADDRESS);
    public static final MemorySegment SetPixelFormat_MemSeg;
    public static final MethodHandle SetPixelFormat_MthHdl;

    public static final FunctionDescriptor wglCreateContext_FnDesc = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment wglCreateContext_MemSeg;
    public static final MethodHandle wglCreateContext_MthHdl;

    public static final FunctionDescriptor wglMakeCurrent_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment wglMakeCurrent_MemSeg;
    public static final MethodHandle wglMakeCurrent_MthHdl;

    public static final FunctionDescriptor ShowWindow_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.JAVA_INT);
    public static final MemorySegment ShowWindow_MemSeg;
    public static final MethodHandle ShowWindow_MthHdl;

    public static final FunctionDescriptor UpdateWindow_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS);
    public static final MemorySegment UpdateWindow_MemSeg;
    public static final MethodHandle UpdateWindow_MthHdl;

    public static final FunctionDescriptor PostQuitMessage_FnDesc = FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT);
    public static final MemorySegment PostQuitMessage_MemSeg;
    public static final MethodHandle PostQuitMessage_MthHdl;

    public static final FunctionDescriptor DefWindowProcW_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_LONG, ValueLayout.JAVA_LONG);
    public static final MemorySegment DefWindowProcW_MemSeg;
    public static final MethodHandle DefWindowProcW_MthHdl;

    public static final FunctionDescriptor PeekMessageW_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT, ValueLayout.JAVA_INT);
    public static final MemorySegment PeekMessageW_MemSeg;
    public static final MethodHandle PeekMessageW_MthHdl;

    public static final FunctionDescriptor TranslateMessage_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS);
    public static final MemorySegment TranslateMessage_MemSeg;
    public static final MethodHandle TranslateMessage_MthHdl;

    public static final FunctionDescriptor DispatchMessageW_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);
    public static final MemorySegment DispatchMessageW_MemSeg;
    public static final MethodHandle DispatchMessageW_MthHdl;

    public static final FunctionDescriptor SwapBuffers_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS);
    public static final MemorySegment SwapBuffers_MemSeg;
    public static final MethodHandle SwapBuffers_MthHdl;

    public static final FunctionDescriptor DestroyWindow_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS);
    public static final MemorySegment DestroyWindow_MemSeg;
    public static final MethodHandle DestroyWindow_MthHdl;

    public static final FunctionDescriptor UnregisterClassW_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment UnregisterClassW_MemSeg;
    public static final MethodHandle UnregisterClassW_MthHdl;

    public static final FunctionDescriptor GetPropA_FnDesc = FunctionDescriptor.of(ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment GetPropA_MemSeg;
    public static final MethodHandle GetPropA_MthHdl;

    public static final FunctionDescriptor SetPropA_FnDesc = FunctionDescriptor.of(ValueLayout.JAVA_INT, ValueLayout.ADDRESS, ValueLayout.ADDRESS, ValueLayout.ADDRESS);
    public static final MemorySegment SetPropA_MemSeg;
    public static final MethodHandle SetPropA_MthHdl;

    static {
        Linker linker = Linker.nativeLinker();
        SymbolLookup kernelLk = SymbolLookup.libraryLookup("kernel32", Arena.global());
        SymbolLookup gdiLk = SymbolLookup.libraryLookup("gdi32", Arena.global());
        SymbolLookup userLk = SymbolLookup.libraryLookup("user32", Arena.global());
        SymbolLookup openglLk = SymbolLookup.libraryLookup("opengl32", Arena.global());

        GetModuleHandleW_MemSeg = kernelLk.findOrThrow("GetModuleHandleW");
        GetModuleHandleW_MthHdl = linker.downcallHandle(GetModuleHandleW_MemSeg, GetModuleHandleW_FnDesc);

        RegisterClassExW_MemSeg = userLk.findOrThrow("RegisterClassExW");
        RegisterClassExW_MthHdl = linker.downcallHandle(RegisterClassExW_MemSeg, RegisterClassExW_FnDesc);

        AdjustWindowRect_MemSeg = userLk.findOrThrow("AdjustWindowRect");
        AdjustWindowRect_MthHdl = linker.downcallHandle(AdjustWindowRect_MemSeg, AdjustWindowRect_FnDesc);

        CreateWindowExW_MemSeg = userLk.findOrThrow("CreateWindowExW");
        CreateWindowExW_MthHdl = linker.downcallHandle(CreateWindowExW_MemSeg, CreateWindowExW_FnDesc);

        GetDC_MemSeg = userLk.findOrThrow("GetDC");
        GetDC_MthHdl = linker.downcallHandle(GetDC_MemSeg, GetDC_FnDesc);

        ChoosePixelFormat_MemSeg = gdiLk.findOrThrow("ChoosePixelFormat");
        ChoosePixelFormat_MthHdl = linker.downcallHandle(ChoosePixelFormat_MemSeg, ChoosePixelFormat_FnDesc);

        SetPixelFormat_MemSeg = gdiLk.findOrThrow("SetPixelFormat");
        SetPixelFormat_MthHdl = linker.downcallHandle(SetPixelFormat_MemSeg, SetPixelFormat_FnDesc);

        wglCreateContext_MemSeg = openglLk.findOrThrow("wglCreateContext");
        wglCreateContext_MthHdl = linker.downcallHandle(wglCreateContext_MemSeg, wglCreateContext_FnDesc);

        wglMakeCurrent_MemSeg = openglLk.findOrThrow("wglMakeCurrent");
        wglMakeCurrent_MthHdl = linker.downcallHandle(wglMakeCurrent_MemSeg, wglMakeCurrent_FnDesc);

        ShowWindow_MemSeg = userLk.findOrThrow("ShowWindow");
        ShowWindow_MthHdl = linker.downcallHandle(ShowWindow_MemSeg, ShowWindow_FnDesc);

        UpdateWindow_MemSeg = userLk.findOrThrow("UpdateWindow");
        UpdateWindow_MthHdl = linker.downcallHandle(UpdateWindow_MemSeg, UpdateWindow_FnDesc);

        PostQuitMessage_MemSeg = userLk.findOrThrow("PostQuitMessage");
        PostQuitMessage_MthHdl = linker.downcallHandle(PostQuitMessage_MemSeg, PostQuitMessage_FnDesc);

        DefWindowProcW_MemSeg = userLk.findOrThrow("DefWindowProcW");
        DefWindowProcW_MthHdl = linker.downcallHandle(DefWindowProcW_MemSeg, DefWindowProcW_FnDesc);

        PeekMessageW_MemSeg = userLk.findOrThrow("PeekMessageW");
        PeekMessageW_MthHdl = linker.downcallHandle(PeekMessageW_MemSeg, PeekMessageW_FnDesc);

        TranslateMessage_MemSeg = userLk.findOrThrow("TranslateMessage");
        TranslateMessage_MthHdl = linker.downcallHandle(TranslateMessage_MemSeg, TranslateMessage_FnDesc);

        DispatchMessageW_MemSeg = userLk.findOrThrow("DispatchMessageW");
        DispatchMessageW_MthHdl = linker.downcallHandle(DispatchMessageW_MemSeg, DispatchMessageW_FnDesc);

        SwapBuffers_MemSeg = gdiLk.findOrThrow("SwapBuffers");
        SwapBuffers_MthHdl = linker.downcallHandle(SwapBuffers_MemSeg, SwapBuffers_FnDesc);

        DestroyWindow_MemSeg = userLk.findOrThrow("DestroyWindow");
        DestroyWindow_MthHdl = linker.downcallHandle(DestroyWindow_MemSeg, DestroyWindow_FnDesc);

        UnregisterClassW_MemSeg = userLk.findOrThrow("UnregisterClassW");
        UnregisterClassW_MthHdl = linker.downcallHandle(UnregisterClassW_MemSeg, UnregisterClassW_FnDesc);

        GetPropA_MemSeg = userLk.findOrThrow("GetPropA");
        GetPropA_MthHdl = linker.downcallHandle(GetPropA_MemSeg, GetPropA_FnDesc);

        SetPropA_MemSeg = userLk.findOrThrow("SetPropA");
        SetPropA_MthHdl = linker.downcallHandle(SetPropA_MemSeg, SetPropA_FnDesc);
    }
}
