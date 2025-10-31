package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.natives.NativeLibrary;

import java.lang.foreign.MemorySegment;

public interface OpenGl32 extends NativeLibrary {
    OpenGl32 INSTANCE = NativeLibrary.load("opengl32", OpenGl32.class);

    MemorySegment wglCreateContext(MemorySegment hdc);
    int wglMakeCurrent(MemorySegment hdc, MemorySegment ctx);
}
