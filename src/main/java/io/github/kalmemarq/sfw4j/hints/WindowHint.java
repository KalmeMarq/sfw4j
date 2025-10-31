package io.github.kalmemarq.sfw4j.hints;

public interface WindowHint<T> {
    WindowHint<Boolean> VISIBLE = new DefaultWindowHint<>(); // Initially visible
    WindowHint<Boolean> MINIMIZED = new DefaultWindowHint<>(); // Initially minimized
    WindowHint<Boolean> MAXIMIZED = new DefaultWindowHint<>(); // Initially maximized
    WindowHint<Boolean> MINIMIZABLE = new DefaultWindowHint<>();
    WindowHint<Boolean> MAXIMIZABLE = new DefaultWindowHint<>();
    WindowHint<Boolean> RESIZEABLE = new DefaultWindowHint<>();
    WindowHint<Boolean> TOPMOST = new DefaultWindowHint<>();

    record DefaultWindowHint<T>() implements WindowHint<T> {
    }
}
