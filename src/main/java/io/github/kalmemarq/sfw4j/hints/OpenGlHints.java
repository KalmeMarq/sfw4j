package io.github.kalmemarq.sfw4j.hints;

public interface OpenGlHints<T> extends WindowHint<T> {
    WindowHint<Integer> CONTEXT_MAJOR = new DefaultWindowHint<>();
    WindowHint<Integer> CONTEXT_MINOR = new DefaultWindowHint<>();
}
