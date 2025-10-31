package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.MemoryStack;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.charset.StandardCharsets;

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
    public static final int WS_EX_APPWINDOW = 0x00040000;
    public static final int WS_EX_TOPMOST = 0x00000008;
    public static final int WM_CHAR = 0x0102;
    public static final int WM_SYSCHAR = 0x0106;
    public static final int WM_UNICHAR = 0x0109;
    public static final int WM_THEMECHANGED = 0x031A;
    public static final int WM_STYLECHANGED = 0x007D;
    public static final int WM_STYLECHANGING = 0x007C;
    public static final int WM_SETTINGCHANGE = 0x001A;

    public static boolean isUsingDarkTheme() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            MemorySegment dwType = stack.allocate(ValueLayout.JAVA_INT);
            MemorySegment szData = stack.allocate(ValueLayout.JAVA_INT);
            MemorySegment cbData = stack.allocate(ValueLayout.JAVA_INT);
            cbData.set(ValueLayout.JAVA_INT, 0, 4);

            var res = Advapi32.INSTANCE.RegGetValueW(
                    Advapi32.HKEY_CURRENT_USER,
                    stack.allocateFrom("Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize", StandardCharsets.UTF_16LE),
                    stack.allocateFrom("AppsUseLightTheme", StandardCharsets.UTF_16LE),
                    Advapi32.RRF_RT_DWORD,
                    dwType,
                    szData,
                    cbData
            );

            if (res == 0 && dwType.get(ValueLayout.JAVA_INT, 0) == Advapi32.REG_DWORD) {
               return szData.get(ValueLayout.JAVA_INT, 0) == 0;
            }
        }

        return false;
    }
}
