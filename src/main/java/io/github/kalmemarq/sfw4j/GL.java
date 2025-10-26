package io.github.kalmemarq.sfw4j;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class GL {
    public static final int GL_COLOR_BUFFER_BIT = 0x00004000;
    public static final int GL_DEPTH_BUFFER_BIT = 0x00000100;

    public static void glClearColor(float red, float green, float blue, float alpha) {
        try {
            glClearColor_MthHdl.invokeExact(red, green, blue, alpha);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void glClear(int mask) {
        try {
            glClear_MthHdl.invokeExact(mask);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static final FunctionDescriptor glClearColor_FnDesc = FunctionDescriptor.ofVoid(ValueLayout.JAVA_FLOAT, ValueLayout.JAVA_FLOAT, ValueLayout.JAVA_FLOAT, ValueLayout.JAVA_FLOAT);
    private static MemorySegment glClearColor_MemSeg;
    private static MethodHandle glClearColor_MthHdl;

    private static final FunctionDescriptor glClear_FnDesc = FunctionDescriptor.ofVoid(ValueLayout.JAVA_INT);
    private static MemorySegment glClear_MemSeg;
    private static MethodHandle glClear_MthHdl;

    static {
        // TOOD: Use wglGetProcAddress
        if (OperatingSystem.get() == OperatingSystem.WINDOWS) {
            Linker linker = Linker.nativeLinker();
            SymbolLookup lookup = SymbolLookup.libraryLookup("opengl32", Arena.global());

            glClearColor_MemSeg = lookup.findOrThrow("glClearColor");
            glClearColor_MthHdl = linker.downcallHandle(glClearColor_MemSeg, glClearColor_FnDesc);

            glClear_MemSeg = lookup.findOrThrow("glClear");
            glClear_MthHdl = linker.downcallHandle(glClear_MemSeg, glClear_FnDesc);
        }
    }
}
