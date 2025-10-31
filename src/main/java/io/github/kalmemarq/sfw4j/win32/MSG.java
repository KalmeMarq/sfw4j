package io.github.kalmemarq.sfw4j.win32;

import java.lang.foreign.*;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.invoke.VarHandle;

public record MSG(MemorySegment memorySegment) {
    public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.ADDRESS.withName("hwnd"),
            ValueLayout.JAVA_INT.withName("message"),
            MemoryLayout.paddingLayout(4),
            ValueLayout.JAVA_LONG.withName("wParam"),
            ValueLayout.JAVA_LONG.withName("lParam"),
            ValueLayout.JAVA_INT.withName("time"),
            MemoryLayout.paddingLayout(4),
            ValueLayout.ADDRESS.withName("pt")
    );

    public static final long SIZE = LAYOUT.byteSize();

    public static final VarHandle HWND_VH = LAYOUT.varHandle(PathElement.groupElement("hwnd"));
    public static final VarHandle MESSAGE_VH = LAYOUT.varHandle(PathElement.groupElement("message"));
    public static final VarHandle WPARAM_VH = LAYOUT.varHandle(PathElement.groupElement("wParam"));
    public static final VarHandle LPARAM_VH = LAYOUT.varHandle(PathElement.groupElement("lParam"));
    public static final VarHandle TIME_VH = LAYOUT.varHandle(PathElement.groupElement("time"));
    public static final VarHandle PT_VH = LAYOUT.varHandle(PathElement.groupElement("pt"));

    public MSG(Arena arena) {
        this(arena.allocate(SIZE).fill((byte) 0));
    }

    public void hwnd(MemorySegment hwnd) {
        HWND_VH.set(this.memorySegment, 0L, hwnd);
    }

    public void message(int message) {
        MESSAGE_VH.set(this.memorySegment, 0L, message);
    }

    public void wParam(long wParam) {
        WPARAM_VH.set(this.memorySegment, 0L, wParam);
    }

    public void lParam(long lParam) {
        LPARAM_VH.set(this.memorySegment, 0L, lParam);
    }

    public void time(int time) {
        TIME_VH.set(this.memorySegment, 0L, time);
    }

    public void pt(MemorySegment pt) {
        PT_VH.set(this.memorySegment, 0L, pt);
    }

    public MemorySegment hwnd() {
        return (MemorySegment) HWND_VH.get(this.memorySegment, 0L);
    }

    public int message() {
        return (int) MESSAGE_VH.get(this.memorySegment, 0L);
    }

    public long wParam() {
        return (long) WPARAM_VH.get(this.memorySegment, 0L);
    }

    public long lParam() {
        return (long) LPARAM_VH.get(this.memorySegment, 0L);
    }

    public int time() {
        return (int) TIME_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment pt() {
        return (MemorySegment) PT_VH.get(this.memorySegment, 0L);
    }
}
