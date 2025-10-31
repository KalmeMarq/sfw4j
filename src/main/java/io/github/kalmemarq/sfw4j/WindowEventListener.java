package io.github.kalmemarq.sfw4j;

public interface WindowEventListener {
    default void onChar(int codepoint) {
    }

    default void onCursorPos(int x, int y) {
    }

    default void onMouseButton(int x, int y, int button, boolean pressed) {
    }

    default void onKey(int key) {
    }

    default void onTheme(Theme theme) {
    }
}
