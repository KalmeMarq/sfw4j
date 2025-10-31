package io.github.kalmemarq.sfw4j.natives;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class NativeLibraryInvocationHandler implements InvocationHandler {
    private final Map<String, LibMethodEntry> entries;

    public NativeLibraryInvocationHandler(String name, Class<?> clazz) {
        this(name, clazz, Arena.global());
    }

    public NativeLibraryInvocationHandler(String name, Class<?> clazz, Arena arena) {
        this.entries = new HashMap<>();

        Linker linker = Linker.nativeLinker();
        SymbolLookup lookup = SymbolLookup.libraryLookup(name, arena);

        for (var mth : clazz.getDeclaredMethods()) {
            MemoryLayout[] paramLayouts = new MemoryLayout[mth.getParameterCount()];
            for (int i = 0; i < mth.getParameterCount(); ++i) {
                var paramClazz = mth.getParameterTypes()[i];
                paramLayouts[i] = getLayout(paramClazz);
            }

            FunctionDescriptor fnDesc = mth.getReturnType() == Void.class || mth.getReturnType() == void.class ? FunctionDescriptor.ofVoid(paramLayouts) : FunctionDescriptor.of(getLayout(mth.getReturnType()), paramLayouts);
            MemorySegment memSeg = lookup.findOrThrow(mth.getName());
            MethodHandle mthHdl = linker.downcallHandle(memSeg, fnDesc).asSpreader(Object[].class, mth.getParameterCount());

            this.entries.put(mth.getName(), new LibMethodEntry(memSeg, mthHdl, fnDesc));
        }
    }

    private static MemoryLayout getLayout(Class<?> clazz) {
        if (clazz == MemorySegment.class) {
            return ValueLayout.ADDRESS;
        } else if (clazz == int.class || clazz == Integer.class) {
            return ValueLayout.JAVA_INT;
        } else if (clazz == short.class || clazz == Short.class) {
            return ValueLayout.JAVA_SHORT;
        } else if (clazz == byte.class || clazz == Byte.class) {
            return ValueLayout.JAVA_BYTE;
        } else if (clazz == long.class || clazz == Long.class) {
            return ValueLayout.JAVA_LONG;
        }
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        LibMethodEntry entry = this.entries.get(method.getName());
        if (entry != null) {
            try {
                return entry.mthHdl.invoke(args);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public record LibMethodEntry(MemorySegment memSeg, MethodHandle mthHdl, FunctionDescriptor fnDesc) {}
}
