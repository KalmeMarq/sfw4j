package io.github.kalmemarq.sfw4j.win32;

import io.github.kalmemarq.sfw4j.natives.NativeLibrary;

import java.lang.foreign.MemorySegment;

public interface Advapi32 extends NativeLibrary {
    Advapi32 INSTANCE = NativeLibrary.load("advapi32", Advapi32.class);
    long HKEY_CURRENT_USER = 0x80000001L;
    int RRF_RT_DWORD = 0x00000018;
    int REG_DWORD = 4;

    int RegGetValueW(long hkey, MemorySegment lpSubKey, MemorySegment lpValue, int dwFlags, MemorySegment pdwType, MemorySegment pvData, MemorySegment pcbData);
}
