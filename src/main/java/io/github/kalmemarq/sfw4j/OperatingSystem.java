package io.github.kalmemarq.sfw4j;

import java.util.Locale;

public enum OperatingSystem {
    WINDOWS,
    OSX,
    SOLARIS,
    LINUX,
    UNKNOWN;

    private static OperatingSystem osCached;

    public static OperatingSystem get() {
        if (osCached == null) {
            String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
            if (osName.contains("windows")) {
                osCached = WINDOWS;
            } else if (osName.contains("mac") || osName.contains("darwin")) {
                osCached = OSX;
            } else if (osName.contains("solaris")) {
                osCached = SOLARIS;
            } else if (osName.contains("linux") || osName.contains("unix")) {
                osCached = LINUX;
            } else {
                osCached = UNKNOWN;
            }
        }
        return osCached;
    }
}
