package io.github.kalmemarq.sfw4j;

public sealed interface Theme {
    record Light() implements Theme {
        public static final Light DEFAULT = new Light();
    }

    record Dark() implements Theme {
        public static final Dark DEFAULT = new Dark();
    }
}
