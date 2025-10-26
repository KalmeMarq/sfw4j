package io.github.kalmemarq.sfw4j.hints;

import java.util.IdentityHashMap;
import java.util.Map;

public class WindowHintBag {
    private final Map<WindowHint<?>, Object> map;

    public WindowHintBag() {
        this.map = new IdentityHashMap<>();
    }

    public <T> void put(WindowHint<T> hint, T value) {
        this.map.put(hint, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(WindowHint<T> hint) {
        Object value = this.map.get(hint);
        return value == null ? null : (T) value;
    }

    @SuppressWarnings("unchecked")
    public <T> T getOr(WindowHint<T> hint, T defaultValue) {
        Object value = this.map.get(hint);
        return value == null ? defaultValue : (T) value;
    }
}
