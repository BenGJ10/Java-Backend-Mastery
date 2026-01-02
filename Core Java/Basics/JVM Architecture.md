# JVM Architecture & Execution Engine

The **Java Virtual Machine (JVM)** is the core runtime component responsible for:

* **loading bytecode**
* **verifying and managing memory**
* **executing instructions**
* **garbage collection**
* **JIT compilation and optimization**


---

## 1. How Java Program Runs

```
.java  --compiled-->  .class (bytecode)  --executed by-->  JVM  --on-->  OS/Hardware
```

Flow overview:

1. **Source code** compiled by `javac` → `.class` bytecode
2. Bytecode is **platform-independent**
3. JVM loads class into memory
4. Bytecode verified for safety
5. Execution Engine executes instructions using:

   * **Interpreter**
   * **JIT Compiler**
6. Memory managed by **Heap + Stack + Metaspace**
7. **Garbage Collector** frees unused objects

---

## 2. JVM Architecture Overview

JVM is divided into major components:

![JVM Architecture](images/jvm_model.png)

---

## 3. Class Loader Subsystem

Responsible for **loading classes (.class files) into memory**.

### 3.1 Class Loading Phases

#### 1. **Loading**

* Reads `.class` bytecode into memory
* Creates `java.lang.Class` object

#### 2. **Linking**

* **Verification**: ensures bytecode validity
* **Preparation**: allocates memory for static fields
* **Resolution**: replaces symbolic references with direct references

#### 3. **Initialization**

* static blocks executed
* static variables assigned values

---

### 3.2 Types of Class Loaders

| Class Loader                | Role                                        |
| --------------------------- | ------------------------------------------- |
| **Bootstrap ClassLoader**   | Loads core JDK classes (`rt.jar`, `java.*`) |
| **Extension ClassLoader**   | Loads `javax.*` / extensions                |
| **Application ClassLoader** | Loads application classes (`classpath`)     |

They follow **parent delegation model**
→ child asks parent first → ensures security and consistency.

---

## 4. JVM Runtime Data Areas (Memory Model)

JVM memory is divided into **thread-shared** and **thread-specific** regions.

### Thread-Shared Areas

* Heap
* Method Area (Metaspace)

### Thread-Specific Areas

* JVM Stack
* PC Register
* Native Method Stack

---

## 4.1 Heap Memory (Object Area)

* Stores **objects & arrays**
* Shared among all threads
* Garbage Collected region

Divided conceptually into:

| Area                   | Purpose               |
| ---------------------- | --------------------- |
| Young Generation       | newly created objects |
| Survivor Space         | surviving objects     |
| Old/Tenured Generation | long-lived objects    |

Garbage collector continuously removes unreachable objects.

---

## 4.2 Method Area / Metaspace

Stores:

* class metadata
* method information
* constant pool
* field information
* static variables

### Java 8 change

* **PermGen removed**
* replaced by **Metaspace**
* lives in **native memory**
* grows dynamically

---

## 4.3 Java Stack (Per Thread)

Each thread has its own **stack frame** per method call.

Stack frame stores:

* local variables
* method arguments
* reference variables
* return values
* operand stack

Errors:

* **StackOverflowError** → deep recursion
* **OutOfMemoryError: unable to create new native thread**

---

## 4.4 PC (Program Counter) Register

Stores **address of currently executing instruction** for each thread.

Why needed?

* JVM supports **multithreading**
* Each thread must track its own execution location

---

## 4.5 Native Method Stack

Used when Java calls:

* C/C++ functions
* system-level operations

Through **JNI (Java Native Interface)**.

---

## 5. Execution Engine (Heart of JVM)

Execution engine executes bytecode.

Components:

* **Interpreter**
* **JIT Compiler**
* **Garbage Collector**
* **Profiler & Optimizer**

---

## 5.1 Interpreter

* Reads bytecode instruction **line by line**
* Simple but slower due to repeated interpretation

Good for **small running methods**.

---

## 5.2 JIT (Just-in-Time Compiler)

To improve performance, JVM **compiles hot code paths** to **native machine code**.

Steps:

1. Identifies frequently executed code (hotspot methods/loops)
2. Converts bytecode to native machine code
3. Caches compiled code
4. Subsequent executions run at **near C/C++ speed**

Optimizations include:

* inlining
* constant folding
* dead code elimination
* loop unrolling
* escape analysis

---

## 5.3 HotSpot JVM

Most JVMs use **HotSpot**:

* identifies "hot" methods
* optimizes them dynamically
* balances interpreter + JIT

---

## 6. Garbage Collection (Brief in Context)

GC automatically frees unused objects.

Major collectors include:

* Serial GC
* Parallel GC
* CMS
* G1 GC (default modern JVM)
* ZGC / Shenandoah (low-latency)

GC deals with:

* heap compaction
* marking reachable objects
* removing unreachable ones

Errors:

* **OutOfMemoryError: Java heap space**

---

## 7. Step-by-Step: What Happens When You Run a Java Program?

Example:

```java
class Test {
    public static void main(String[] args) {
        int x = 10;
        Test t = new Test();
    }
}
```

### Execution Steps

1. `javac Test.java` → produces `Test.class`
2. JVM launched
3. ClassLoader loads `Test.class`
4. Bytecode verified
5. Memory allocated
6. `main()` pushed to **stack**
7. `x=10` stored in **stack**
8. `new Test()` stored in **heap**
9. reference `t` stored in stack pointing to heap object
10. Execution completes
11. GC eventually removes unused object

---

## 8. JVM vs JRE vs JDK

| Component | Contains              | Purpose              |
| --------- | --------------------- | -------------------- |
| JVM       | Execution engine      | Runs bytecode        |
| JRE       | JVM + libraries       | Run applications     |
| JDK       | JRE + compilers/tools | Develop applications |

---

## 9. Important Interview Questions

### Q1. What are JVM runtime data areas?

Heap, Method Area, Stack, PC Register, Native method stack.

### Q2. Difference between JIT and Interpreter?

Interpreter executes line-by-line, JIT converts hot code to native machine code.

### Q3. Where are objects stored?

Heap.

### Q4. Where are local variables stored?

JVM stack frames.

### Q5. What is Metaspace?

Memory area storing class metadata (replaced PermGen in Java 8).

### Q6. What is parent delegation model?

ClassLoader delegates loading to parent before loading class itself.

### Q7. Why is Java platform-independent?

Because JVM executes bytecode, not machine code.

---

## Key Takeaways

* JVM provides **abstraction over OS and hardware**

* Class Loader loads & verifies classes

* Runtime Data Areas manage memory

* Execution Engine runs bytecode using:

  * Interpreter
  * JIT compiler

* Garbage collector manages heap memory

---
