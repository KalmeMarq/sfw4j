package io.github.kalmemarq;

public class Main {
    //        Map<Integer, MemorySegment> propMap = new HashMap<>();
//
//        Linker linker = Linker.nativeLinker();
//        FunctionDescriptor fnDesc = FunctionDescriptor.ofVoid();
//
//        Test test1 = new Test(1);
//        Test test2 = new Test(2);
//
//        MethodHandle print1MthHdl = MethodHandles.lookup().findVirtual(Test.class, "print", fnDesc.toMethodType()).bindTo(test1);
//        MethodHandle print2MthHdl = MethodHandles.lookup().findVirtual(Test.class, "print", fnDesc.toMethodType()).bindTo(test2);
//
//        MemorySegment print1MemSeg = linker.upcallStub(print1MthHdl, fnDesc, Arena.global());
//        MemorySegment print2MemSeg = linker.upcallStub(print2MthHdl, fnDesc, Arena.global());
//
//        propMap.put(test1.id(), print1MemSeg);
//        propMap.put(test2.id(), print2MemSeg);
//
//        MethodHandle rcPrint1MthHdl = linker.downcallHandle(propMap.get(1), fnDesc);
//        MethodHandle rcPrint2MthHdl = linker.downcallHandle(propMap.get(2), fnDesc);
//
//        rcPrint1MthHdl.invokeExact();
//        rcPrint2MthHdl.invokeExact();

//    record Test(int id) {
//        public void print() {
//            IO.println("Test " + this.id);
//        }
//    }
}
