package io.github.kalmemarq.sfw4j;

import io.github.kalmemarq.sfw4j.callbacks.CursorPosCallback;
import io.github.kalmemarq.sfw4j.callbacks.KeyCallback;
import io.github.kalmemarq.sfw4j.callbacks.MouseButtonCallback;
import io.github.kalmemarq.sfw4j.hints.WindowHintBag;
import io.github.kalmemarq.sfw4j.win32.Win32Window;

public interface Window extends AutoCloseable {
    static Window create(int width, int height, String title, WindowHintBag hintBag) {
        return switch (OperatingSystem.get()) {
             case WINDOWS -> new Win32Window(width, height, title, hintBag);
             default -> null;
        };
    }

    void setKeyCallback(KeyCallback callback);
    void setMouseButtonCallback(MouseButtonCallback callback);
    void setCursorPosCallback(CursorPosCallback callback);

    void makeContextCurrent();
    void swapBuffers();

    void show();
    void hide();
    void maximize();
    void minimize();

    void pollEvents();
    void setShouldClose(boolean shouldClose);
    boolean shouldClose();

    @Override
    void close();
}
