package io.github.kalmemarq.sfw4j.hints;

public interface OpenGlHints<T> extends WindowHint<T> {
    WindowHint<Integer> CONTEXT_MAJOR = new DefaultWindowHint<>();
    WindowHint<Integer> CONTEXT_MINOR = new DefaultWindowHint<>();

    WindowHint<Integer> RED_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> GREEN_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> BLUE_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> ALPHA_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> DEPTH_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> STENCIL_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> ACCUM_RED_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> ACCUM_GREEN_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> ACCUM_BLUE_BITS = new DefaultWindowHint<>();
    WindowHint<Integer> ACCUM_ALPHA_BITS = new DefaultWindowHint<>();
}
