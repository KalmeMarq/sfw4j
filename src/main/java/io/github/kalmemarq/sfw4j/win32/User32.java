package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.natives.NativeLibrary;

import java.lang.foreign.MemorySegment;

public interface User32 extends NativeLibrary {
    User32 INSTANCE = NativeLibrary.load("user32", User32.class);

    short RegisterClassExW(MemorySegment clazz);
    int AdjustWindowRect(MemorySegment lpRect, int dwStyle, int bMenu);
    MemorySegment CreateWindowExW(int dwExStyle, MemorySegment lpClassName, MemorySegment lpWindowName, int dwStyle, int x, int y, int nWidth, int nHeight, MemorySegment hWndParent, MemorySegment hMenu, MemorySegment hInstance, MemorySegment lpParam);
    MemorySegment GetDC(MemorySegment hWnd);
    int ShowWindow(MemorySegment hWnd, int nCmdShow);
    int UpdateWindow(MemorySegment hWnd);
    void PostQuitMessage(int nExitCode);
    long DefWindowProcW(MemorySegment hWnd, int msg, long wParam, long lParam);
    int PeekMessageW(MemorySegment lpMsg, MemorySegment hWnd, int wMsgFilterMin, int wMsgFilterMax, int wRemoveMsg);
    int TranslateMessage(MemorySegment lpMsg);
    long DispatchMessageW(MemorySegment lpMsg);
    int DestroyWindow(MemorySegment hWnd);
    MemorySegment GetPropA(MemorySegment hwnd, MemorySegment lpString);
    int SetPropA(MemorySegment hwnd, MemorySegment lpString, MemorySegment hData);
    int SetWindowTextW(MemorySegment hWnd, MemorySegment lpString);
}
