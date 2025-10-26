package io.github.kalmemarq.sfw4j.hints;

public interface WindowHint<T> {
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
    WindowHint<Boolean> VISIBLE = new DefaultWindowHint<>(); // Initially visible
    WindowHint<Boolean> MINIMIZED = new DefaultWindowHint<>(); // Initially minimized
    WindowHint<Boolean> MAXIMIZED = new DefaultWindowHint<>(); // Initially maximized
    WindowHint<Boolean> MINIMIZABLE = new DefaultWindowHint<>();
    WindowHint<Boolean> MAXIMIZABLE = new DefaultWindowHint<>();
    WindowHint<Boolean> RESIZEABLE = new DefaultWindowHint<>();

    record DefaultWindowHint<T>() implements WindowHint<T> {
    }
}
