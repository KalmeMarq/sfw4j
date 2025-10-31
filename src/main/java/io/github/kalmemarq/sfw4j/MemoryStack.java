package io.github.kalmemarq.sfw4j;

import java.lang.foreign.Arena;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

public class MemoryStack implements Arena {
    private static final ThreadLocal<MemoryStack> STACK = ThreadLocal.withInitial(MemoryStack::new);

    private final MemorySegment segment;
    private long pointer;
    private int frameIndex;
    private final long[] frames;

    private MemoryStack() {
        this(1024);
    }

    private MemoryStack(int size) {
        this.segment = Arena.global().allocate(size);
        this.pointer = 0;
        this.frameIndex = 0;
        this.frames = new long[64];
    }

    public static MemoryStack stackPush() {
        return STACK.get().push();
    }

    public MemoryStack push() {
        if (this.frameIndex == this.frames.length) {
            throw new RuntimeException("Frame Overflow");
        }
        this.frames[this.frameIndex++] = this.pointer;
        return this;
    }

    @Override
    public MemorySegment allocate(long byteSize, long byteAlignment) {
        MemorySegment memSeg = this.segment.asSlice(this.pointer, byteSize, byteAlignment);
        this.pointer += byteSize;
        return memSeg;
    }

    @Override
    public MemorySegment allocate(long byteSize) {
        return this.allocate(byteSize, 1);
    }

    @Override
    public MemorySegment allocate(MemoryLayout layout) {
        MemorySegment memSeg = this.segment.asSlice(this.pointer, layout);
        this.pointer += layout.byteSize();
        return memSeg;
    }

    @Override
    public MemorySegment.Scope scope() {
        return Arena.global().scope();
    }

    @Override
    public void close() {
        if (this.frameIndex == 0) {
            throw new RuntimeException("Frame Underflow");
        }
        this.pointer = this.frames[--this.frameIndex];
    }
}
