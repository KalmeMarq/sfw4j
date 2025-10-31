package io.github.kalmemarq.sfw4j.win32;

import java.lang.foreign.*;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.invoke.VarHandle;

public record RECT(MemorySegment memorySegment) {
    public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("left"),
            ValueLayout.JAVA_INT.withName("top"),
            ValueLayout.JAVA_INT.withName("right"),
            ValueLayout.JAVA_INT.withName("bottom")
    );
    public static final long SIZE = LAYOUT.byteSize();

    public static final VarHandle LEFT_VH = LAYOUT.varHandle(PathElement.groupElement("left"));
    public static final VarHandle TOP_VH = LAYOUT.varHandle(PathElement.groupElement("top"));
    public static final VarHandle RIGHT_VH = LAYOUT.varHandle(PathElement.groupElement("right"));
    public static final VarHandle BOTTOM_VH = LAYOUT.varHandle(PathElement.groupElement("bottom"));

    public RECT(Arena arena) {
        this(arena.allocate(SIZE).fill((byte) 0));
    }

    public void left(int left) {
        LEFT_VH.set(this.memorySegment, 0L, left);
    }

    public void top(int top) {
        TOP_VH.set(this.memorySegment, 0L, top);
    }

    public void right(int right) {
        RIGHT_VH.set(this.memorySegment, 0L, right);
    }

    public void bottom(int bottom) {
        BOTTOM_VH.set(this.memorySegment, 0L, bottom);
    }

    public int left() {
        return (int) LEFT_VH.get(this.memorySegment, 0L);
    }

    public int top() {
        return (int) TOP_VH.get(this.memorySegment, 0L);
    }

    public int right() {
        return (int) RIGHT_VH.get(this.memorySegment, 0L);
    }

    public int bottom() {
        return (int) BOTTOM_VH.get(this.memorySegment, 0L);
    }
}
