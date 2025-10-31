package io.github.kalmemarq.sfw4j.win32;

import java.lang.foreign.*;
import java.lang.foreign.MemoryLayout.PathElement;
import java.lang.invoke.VarHandle;

public record PIXELFORMATDESCRIPTOR(MemorySegment memorySegment) {
    public static final StructLayout LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_SHORT.withName("nSize"),
            ValueLayout.JAVA_SHORT.withName("nVersion"),
            ValueLayout.JAVA_INT.withName("dwFlags"),
            ValueLayout.JAVA_BYTE.withName("iPixelType"),
            ValueLayout.JAVA_BYTE.withName("cColorBits"),
            ValueLayout.JAVA_BYTE.withName("cRedBits"),
            ValueLayout.JAVA_BYTE.withName("cRedShift"),
            ValueLayout.JAVA_BYTE.withName("cGreenBits"),
            ValueLayout.JAVA_BYTE.withName("cGreenShift"),
            ValueLayout.JAVA_BYTE.withName("cBlueBits"),
            ValueLayout.JAVA_BYTE.withName("cBlueShift"),
            ValueLayout.JAVA_BYTE.withName("cAlphaBits"),
            ValueLayout.JAVA_BYTE.withName("cAlphaShift"),
            ValueLayout.JAVA_BYTE.withName("cAccumBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumRedBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumGreenBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumBlueBits"),
            ValueLayout.JAVA_BYTE.withName("cAccumAlphaBits"),
            ValueLayout.JAVA_BYTE.withName("cDepthBits"),
            ValueLayout.JAVA_BYTE.withName("cStencilBits"),
            ValueLayout.JAVA_BYTE.withName("cAuxBuffers"),
            ValueLayout.JAVA_BYTE.withName("iLayerType"),
            ValueLayout.JAVA_BYTE.withName("bReserved"),
            ValueLayout.JAVA_INT.withName("dwLayerMask"),
            ValueLayout.JAVA_INT.withName("dwVisibleMask"),
            ValueLayout.JAVA_INT.withName("dwDamageMask")
    );
    public static final long SIZE = LAYOUT.byteSize();

    public static final VarHandle NSIZE_VH = LAYOUT.varHandle(PathElement.groupElement("nSize"));
    public static final VarHandle NVERSION_VH = LAYOUT.varHandle(PathElement.groupElement("nVersion"));
    public static final VarHandle DWFLAGS_VH = LAYOUT.varHandle(PathElement.groupElement("dwFlags"));
    public static final VarHandle IPIXELTYPE_VH = LAYOUT.varHandle(PathElement.groupElement("iPixelType"));
    public static final VarHandle CCOLORBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cColorBits"));
    public static final VarHandle CREDBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cRedBits"));
    public static final VarHandle CREDSHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cRedShift"));
    public static final VarHandle CGREENBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cGreenBits"));
    public static final VarHandle CGREENSHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cGreenShift"));
    public static final VarHandle CBLUEBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cBlueBits"));
    public static final VarHandle CBLUESHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cBlueShift"));
    public static final VarHandle CALPHABITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAlphaBits"));
    public static final VarHandle CALPHASHIFT_VH = LAYOUT.varHandle(PathElement.groupElement("cAlphaShift"));
    public static final VarHandle CACCUMBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumBits"));
    public static final VarHandle CACCUMREDBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumRedBits"));
    public static final VarHandle CACCUMGREENBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumGreenBits"));
    public static final VarHandle CACCUMBLUEBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumBlueBits"));
    public static final VarHandle CACCUMALPHABITS_VH = LAYOUT.varHandle(PathElement.groupElement("cAccumAlphaBits"));
    public static final VarHandle CDEPTHBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cDepthBits"));
    public static final VarHandle CSTENCILBITS_VH = LAYOUT.varHandle(PathElement.groupElement("cStencilBits"));
    public static final VarHandle CAUXBUFFERS_VH = LAYOUT.varHandle(PathElement.groupElement("cAuxBuffers"));
    public static final VarHandle ILAYERTYPE_VH = LAYOUT.varHandle(PathElement.groupElement("iLayerType"));
    public static final VarHandle BRESERVED_VH = LAYOUT.varHandle(PathElement.groupElement("bReserved"));
    public static final VarHandle DWLAYERMASK_VH = LAYOUT.varHandle(PathElement.groupElement("dwLayerMask"));
    public static final VarHandle DWVISIBLEMASK_VH = LAYOUT.varHandle(PathElement.groupElement("dwVisibleMask"));
    public static final VarHandle DWDAMAGEMASK_VH = LAYOUT.varHandle(PathElement.groupElement("dwDamageMask"));

    public PIXELFORMATDESCRIPTOR(Arena arena) {
        this(arena.allocate(SIZE).fill((byte) 0));
    }

    public void nSize(short nSize) {
        NSIZE_VH.set(this.memorySegment, 0L, nSize);
    }

    public void nVersion(short nVersion) {
        NVERSION_VH.set(this.memorySegment, 0L, nVersion);
    }

    public void dwFlags(int dwFlags) {
        DWFLAGS_VH.set(this.memorySegment, 0L, dwFlags);
    }

    public void iPixelType(byte iPixelType) {
        IPIXELTYPE_VH.set(this.memorySegment, 0L, iPixelType);
    }

    public void cColorBits(byte cColorBits) {
        CCOLORBITS_VH.set(this.memorySegment, 0L, cColorBits);
    }

    public void cRedBits(byte cRedBits) {
        CREDBITS_VH.set(this.memorySegment, 0L, cRedBits);
    }

    public void cRedShift(byte cRedShift) {
        CREDSHIFT_VH.set(this.memorySegment, 0L, cRedShift);
    }

    public void cGreenBits(byte cGreenBits) {
        CGREENBITS_VH.set(this.memorySegment, 0L, cGreenBits);
    }

    public void cGreenShift(byte cGreenShift) {
        CGREENSHIFT_VH.set(this.memorySegment, 0L, cGreenShift);
    }

    public void cBlueBits(byte cBlueBits) {
        CBLUEBITS_VH.set(this.memorySegment, 0L, cBlueBits);
    }

    public void cBlueShift(byte cBlueShift) {
        CBLUESHIFT_VH.set(this.memorySegment, 0L, cBlueShift);
    }

    public void cAlphaBits(byte cAlphaBits) {
        CALPHABITS_VH.set(this.memorySegment, 0L, cAlphaBits);
    }

    public void cAlphaShift(byte cAlphaShift) {
        CALPHASHIFT_VH.set(this.memorySegment, 0L, cAlphaShift);
    }

    public void cAccumBits(byte cAccumBits) {
        CACCUMBITS_VH.set(this.memorySegment, 0L, cAccumBits);
    }

    public void cAccumRedBits(byte cAccumRedBits) {
        CACCUMREDBITS_VH.set(this.memorySegment, 0L, cAccumRedBits);
    }

    public void cAccumGreenBits(byte cAccumGreenBits) {
        CACCUMGREENBITS_VH.set(this.memorySegment, 0L, cAccumGreenBits);
    }

    public void cAccumBlueBits(byte cAccumBlueBits) {
        CACCUMBLUEBITS_VH.set(this.memorySegment, 0L, cAccumBlueBits);
    }

    public void cAccumAlphaBits(byte cAccumAlphaBits) {
        CACCUMALPHABITS_VH.set(this.memorySegment, 0L, cAccumAlphaBits);
    }

    public void cDepthBits(byte cDepthBits) {
        CDEPTHBITS_VH.set(this.memorySegment, 0L, cDepthBits);
    }

    public void cStencilBits(byte cStencilBits) {
        CSTENCILBITS_VH.set(this.memorySegment, 0L, cStencilBits);
    }

    public void cAuxBuffers(byte cAuxBuffers) {
        CAUXBUFFERS_VH.set(this.memorySegment, 0L, cAuxBuffers);
    }

    public void iLayerType(byte iLayerType) {
        ILAYERTYPE_VH.set(this.memorySegment, 0L, iLayerType);
    }

    public void bReserved(byte bReserved) {
        BRESERVED_VH.set(this.memorySegment, 0L, bReserved);
    }

    public void dwLayerMask(int dwLayerMask) {
        DWLAYERMASK_VH.set(this.memorySegment, 0L, dwLayerMask);
    }

    public void dwVisibleMask(int dwVisibleMask) {
        DWVISIBLEMASK_VH.set(this.memorySegment, 0L, dwVisibleMask);
    }

    public void dwDamageMask(int dwDamageMask) {
        DWDAMAGEMASK_VH.set(this.memorySegment, 0L, dwDamageMask);
    }
}
