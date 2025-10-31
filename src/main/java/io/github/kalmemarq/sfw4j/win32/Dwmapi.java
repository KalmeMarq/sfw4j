package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.natives.NativeLibrary;

import java.lang.foreign.MemorySegment;

public interface Dwmapi extends NativeLibrary {
    Dwmapi INSTANCE = NativeLibrary.load("dwmapi", Dwmapi.class);

    int DwmSetWindowAttribute(MemorySegment hwnd, int dwAttribute, MemorySegment pvAttribute, int cbAttribute);
}
