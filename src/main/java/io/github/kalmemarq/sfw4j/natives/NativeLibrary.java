package io.github.kalmemarq.sfw4j.natives;

import java.lang.foreign.Arena;
import java.lang.reflect.Proxy;

public interface NativeLibrary {
    static <T> T load(String name, Class<T> clazz) {
        return load(name, clazz, Arena.global());
    }

    static <T> T load(String name, Class<T> clazz, Arena arena) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{ clazz }, new NativeLibraryInvocationHandler(name, clazz, arena));
    }
}
