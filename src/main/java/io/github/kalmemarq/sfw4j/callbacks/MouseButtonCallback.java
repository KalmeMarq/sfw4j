package io.github.kalmemarq.sfw4j.callbacks;

public interface MouseButtonCallback {
    void invoke(int x, int y, int button, boolean pressed);
}
