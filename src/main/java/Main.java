import io.github.kalmemarq.sfw4j.*;
import io.github.kalmemarq.sfw4j.hints.OpenGlHints;
import io.github.kalmemarq.sfw4j.hints.WindowHint;
import io.github.kalmemarq.sfw4j.hints.WindowHintBag;

void main() {
    var hintBag = new WindowHintBag();
    hintBag.put(WindowHint.VISIBLE, true);
    hintBag.put(WindowHint.RESIZEABLE, false);
    hintBag.put(WindowHint.TOPMOST, true);
    hintBag.put(OpenGlHints.CONTEXT_MAJOR, 4);
    hintBag.put(OpenGlHints.CONTEXT_MINOR, 5);

    createWindow(hintBag);
}

int srnRed = 255;
int srnGreen = 127;
int srnBlue = 127;

void createWindow(WindowHintBag hintBag) {
    try (var window = Window.create(800, 600, "Test 1", hintBag)) {
        if (window == null)
            return;

        Random random = new Random();

        window.addEventListener(new WindowEventListener() {
            @Override
            public void onMouseButton(int x, int y, int button, boolean pressed) {
                srnRed = random.nextInt(0, 256);
                srnGreen = random.nextInt(0, 256);
                srnBlue = random.nextInt(0, 256);
            }

            @Override
            public void onChar(int codepoint) {
                IO.println((char) codepoint);
            }

            @Override
            public void onTheme(Theme theme) {
                IO.println("Theme changed " + theme);
            }
        });

        long lastTime = System.nanoTime();
        int frames = 0;

        while (!window.shouldClose()) {
            window.pollEvents();

            GL.glClearColor(srnRed / 255f, srnGreen / 255f, srnBlue / 255f, 1.0f);
            GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

            window.swapBuffers();
            ++frames;

            long now = System.nanoTime();
            if (now - lastTime > 1_000_000_000L) {
                lastTime += 1_000_000_000L;
                window.setTitle(frames + " FPS");
                frames = 0;
            }

            Thread.onSpinWait();
        }
    }
}

void createWindows(WindowHintBag hintBag) {
    Thread thread1 = new Thread(() -> {
        try (var window = Window.create(800, 600, "Test 1", hintBag)) {
            if (window == null)
                return;

            while (true) {
                window.pollEvents();
                if (window.shouldClose()) {
                    window.close();
                    break;
                }

                window.makeContextCurrent();

                GL.glClearColor(1.0f, 0.5f, 0.5f, 1.0f);
                GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

                window.swapBuffers();
            }
        }
    });
    thread1.start();

    Thread thread2 = new Thread(() -> {
        try (var window = Window.create(600, 600, "Test 2", hintBag)) {
            if (window == null)
                return;

            while (true) {
                window.pollEvents();
                if (window.shouldClose()) {
                    window.close();
                    break;
                }

                window.makeContextCurrent();

                GL.glClearColor(1.0f, 0.5f, 0.5f, 1.0f);
                GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

                window.swapBuffers();
            }
        }
    });

    thread2.start();
}