package io.github.kalmemarq.sfw4j.win32;

import java.lang.foreign.*;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.invoke.VarHandle;

public record WNDCLASSEXW(MemorySegment memorySegment) {
    public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("cbSize"),
            ValueLayout.JAVA_INT.withName("style"),
            ValueLayout.ADDRESS.withName("lpfnWndProc"),
            ValueLayout.JAVA_INT.withName("cbClsExtra"),
            ValueLayout.JAVA_INT.withName("cbWndExtra"),
            ValueLayout.ADDRESS.withName("hInstance"),
            ValueLayout.ADDRESS.withName("hIcon"),
            ValueLayout.ADDRESS.withName("hCursor"),
            ValueLayout.ADDRESS.withName("hbrBackground"),
            ValueLayout.ADDRESS.withName("lpszMenuName"),
            ValueLayout.ADDRESS.withName("lpszClassName"),
            ValueLayout.ADDRESS.withName("hIconSm")
    );
    public static final long SIZE = LAYOUT.byteSize();

    public static final VarHandle CBSIZE_VH = LAYOUT.varHandle(PathElement.groupElement("cbSize"));
    public static final VarHandle STYLE_VH = LAYOUT.varHandle(PathElement.groupElement("style"));
    public static final VarHandle LPFNWNDPROC_VH = LAYOUT.varHandle(PathElement.groupElement("lpfnWndProc"));
    public static final VarHandle CBCLSEXTRA_VH = LAYOUT.varHandle(PathElement.groupElement("cbClsExtra"));
    public static final VarHandle CBWNDEXTRA_VH = LAYOUT.varHandle(PathElement.groupElement("cbWndExtra"));
    public static final VarHandle HINSTANCE_VH = LAYOUT.varHandle(PathElement.groupElement("hInstance"));
    public static final VarHandle HICON_VH = LAYOUT.varHandle(PathElement.groupElement("hIcon"));
    public static final VarHandle HCURSOR_VH = LAYOUT.varHandle(PathElement.groupElement("hCursor"));
    public static final VarHandle HBRBACKGROUND_VH = LAYOUT.varHandle(PathElement.groupElement("hbrBackground"));
    public static final VarHandle LPSZMENUNAME_VH = LAYOUT.varHandle(PathElement.groupElement("lpszMenuName"));
    public static final VarHandle LPSZCLASSNAME_VH = LAYOUT.varHandle(PathElement.groupElement("lpszClassName"));
    public static final VarHandle HICONSM_VH = LAYOUT.varHandle(PathElement.groupElement("hIconSm"));

    public WNDCLASSEXW(Arena arena) {
        this(arena.allocate(SIZE).fill((byte) 0));
    }

    public void cbSize(int cbSize) {
        CBSIZE_VH.set(this.memorySegment, 0L, cbSize);
    }

    public void style(int style) {
        STYLE_VH.set(this.memorySegment, 0L, style);
    }

    public void lpfnWndProc(MemorySegment lpfnWndProc) {
        LPFNWNDPROC_VH.set(this.memorySegment, 0L, lpfnWndProc);
    }

    public void cbClsExtra(int cbClsExtra) {
        CBCLSEXTRA_VH.set(this.memorySegment, 0L, cbClsExtra);
    }

    public void cbWndExtra(int cbWndExtra) {
        CBWNDEXTRA_VH.set(this.memorySegment, 0L, cbWndExtra);
    }

    public void hInstance(MemorySegment hInstance) {
        HINSTANCE_VH.set(this.memorySegment, 0L, hInstance);
    }

    public void hIcon(MemorySegment hIcon) {
        HICON_VH.set(this.memorySegment, 0L, hIcon);
    }

    public void hCursor(MemorySegment hCursor) {
        HCURSOR_VH.set(this.memorySegment, 0L, hCursor);
    }

    public void hbrBackground(MemorySegment hbrBackground) {
        HBRBACKGROUND_VH.set(this.memorySegment, 0L, hbrBackground);
    }

    public void lpszMenuName(MemorySegment lpszMenuName) {
        LPSZMENUNAME_VH.set(this.memorySegment, 0L, lpszMenuName);
    }

    public void lpszClassName(MemorySegment lpszClassName) {
        LPSZCLASSNAME_VH.set(this.memorySegment, 0L, lpszClassName);
    }

    public void hIconSm(MemorySegment hIconSm) {
        HICONSM_VH.set(this.memorySegment, 0L, hIconSm);
    }

    public int cbSize() {
        return (int) CBSIZE_VH.get(this.memorySegment, 0L);
    }

    public int style() {
        return (int) STYLE_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment lpfnWndProc() {
        return (MemorySegment) LPFNWNDPROC_VH.get(this.memorySegment, 0L);
    }

    public int cbClsExtra() {
        return (int) CBCLSEXTRA_VH.get(this.memorySegment, 0L);
    }

    public int cbWndExtra() {
        return (int) CBWNDEXTRA_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment hInstance() {
        return (MemorySegment) HINSTANCE_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment hIcon() {
        return (MemorySegment) HICON_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment hCursor() {
        return (MemorySegment) HCURSOR_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment hbrBackground() {
        return (MemorySegment) HBRBACKGROUND_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment lpszMenuName() {
        return (MemorySegment) LPSZMENUNAME_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment lpszClassName() {
        return (MemorySegment) LPSZCLASSNAME_VH.get(this.memorySegment, 0L);
    }

    public MemorySegment hIconSm() {
        return (MemorySegment) HICONSM_VH.get(this.memorySegment, 0L);
    }
}
