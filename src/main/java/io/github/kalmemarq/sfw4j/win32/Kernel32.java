package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.natives.NativeLibrary;

import java.lang.foreign.MemorySegment;

public interface Kernel32 extends NativeLibrary {
    Kernel32 INSTANCE = NativeLibrary.load("kernel32", Kernel32.class);

    MemorySegment GetModuleHandleW(MemorySegment lpModuleName);
}
