package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.natives.NativeLibrary;

import java.lang.foreign.MemorySegment;

public interface Gdi32 extends NativeLibrary {
    Gdi32 INSTANCE = NativeLibrary.load("gdi32", Gdi32.class);

    int ChoosePixelFormat(MemorySegment hdc, MemorySegment ppfd);
    int SetPixelFormat(MemorySegment hdc, int format, MemorySegment ppfd);
    int SwapBuffers(MemorySegment hdc);
}
