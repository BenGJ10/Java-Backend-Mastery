# JIT (Just-In-Time Compiler) & JVM Execution Engine

## 1. Overview of JVM Execution

When you run a Java program, the **Java Virtual Machine (JVM)** executes your bytecode through a component called the **Execution Engine**.

The Execution Engine consists of:

1. **Interpreter**
2. **JIT Compiler (Just-In-Time Compiler)**
3. **Garbage Collector**
4. **Runtime support components (stack frames, native methods, etc.)**

The goal is to turn platform-independent **bytecode** into **native machine code** efficiently.

---

## 2. JVM Execution Engine Architecture

```
             +-------------------------+
             |     Java Source (.java) |
             +-------------------------+
                           |
                           v
             +-------------------------+
             |     Java Compiler (Javac)|
             | Output: Bytecode (.class)|
             +-------------------------+
                           |
                           v
             +-----------------------------+
             |        JVM Runtime          |
             |  (Class Loader + Execution) |
             +-----------------------------+
                           |
                           v
            +-----------------------------------------+
            |              Execution Engine           |
            |-----------------------------------------|
            |  Interpreter |   JIT Compiler  |  GC     |
            +-----------------------------------------+
                           |
                           v
                     Native Machine Code
```

---

## 3. Interpreter (Part of Execution Engine)

The **Interpreter** reads bytecode one instruction at a time and executes it.

### Pros

* Fast startup time
* Simple execution

### Cons

* Slow for repeatedly executed code (loops, frequently invoked methods) because it interprets line-by-line every time

Thus JVM uses **JIT Compiler** to optimize performance.

---

## 4. JIT Compiler (Just-In-Time Compiler)

The **JIT Compiler** improves performance by converting frequently executed bytecode (hot code) into **native machine code at runtime**.

It works *just in time* — when needed.
This allows the JVM to run code much faster after the initial interpretation phase.

### 4.1 Why Use JIT Compiler?

Without JIT:

* Code is interpreted every time → slower execution.

With JIT:

* Compiled to native code once → reused many times → much faster.

### 4.2 How JIT Works (Execution Flow)

```
Bytecode → Interpreter → Detects hot code → Sends to JIT → Converts to native code → JVM stores it → Executes native code directly
```

**Hot code** refers to methods or loops executed many times.

### 4.3 Types of JIT Optimizations

JIT performs several advanced optimizations:

**1. Method Inlining**

Replaces a method call with the actual method body.

```java
// Instead of calling smallMethod(), JVM pastes its code directly.
```

This reduces method-call overhead.

**2. Loop Unrolling**

Expands loops to reduce iteration overhead.

```java
for (i=0; i<4; i++) { ... }

// becomes internally:
{...}{...}{...}{...}
```


**3. Dead Code Elimination**

Removes code that is never used.

**4. Constant Folding**

Evaluates constant expressions at runtime and replaces them with computed values.

**5. Escape Analysis**

Determines whether an object is confined to a method.
If yes → JVM may allocate it on **stack** instead of heap → reduces GC pressure.

**6. Optimistic Optimizations**

JIT assumes certain conditions (like types) and optimizes aggressively.
If assumption fails → JVM performs *de-optimization* and falls back to interpreted mode.

---

## 5. Types of JIT Compilers in JVM

JVM uses multiple JIT compilers internally:

### 5.1 C1 Compiler (Client Compiler)

* Used for simple optimizations
* Targets **quick startup** (useful for desktop apps)

### 5.2 C2 Compiler (Server Compiler)

* Aggressive optimizations
* Targets **long running applications** (enterprise servers)

### 5.3 Tiered Compilation (Combines C1 + C2)

Modern JVM uses **tiered compilation (default)**:

| Tier   | Component   | Purpose                   |
| ------ | ----------- | ------------------------- |
| Tier 0 | Interpreter | Startup speed             |
| Tier 1 | C1 JIT      | Lightweight optimizations |
| Tier 4 | C2 JIT      | Heavy optimizations       |

This approach gives **both fast startup** and **high execution speed**.

---

## 6. How JVM Decides What to JIT-Compile

JVM monitors:

* Method invocation count
* Loop iteration count
* Profiling data (branch prediction, inlining potential)

When thresholds are crossed → code becomes *hot* → JIT compiles it.

---

## 7. Execution Steps Summary (Interview-Ready)

1. **Javac compiles .java → .class (bytecode)**

2. **Class Loader loads bytecode into JVM**

3. **Execution Engine starts interpreting bytecode**

4. JVM detects frequently used code (hotspots)

5. **JIT Compiler converts hot bytecode → native machine code**

6. Native code stored & reused → improves performance

7. Garbage Collector handles memory cleanup

---

## 8. Example: When JIT Helps

### Example Code:

```java
for (int i = 0; i < 1_000_000; i++) {
    compute();
}
```

JVM behavior:

* Interpreter runs first few iterations
* JVM realizes method `compute()` is frequently called
* JIT compiles `compute()` to machine code
* Subsequent executions run at native speed

---

## 9. Advantages of JIT Compiler

| Advantage                       | Description                          |
| ------------------------------- | ------------------------------------ |
| Faster execution                | Converts hot code to native code     |
| Profiling-based optimization    | JIT knows runtime behavior           |
| Adaptive optimization           | Changes based on actual execution    |
| Reduced repeated interpretation | Methods run faster after compilation |

---

## 10. JIT vs Interpreter

| Feature   | Interpreter            | JIT Compiler            |
| --------- | ---------------------- | ----------------------- |
| Execution | Line-by-line           | Converts to native code |
| Speed     | Slow for repeated code | Very fast               |
| Startup   | Fast                   | Slightly slower         |
| Usage     | Initial execution      | After code becomes hot  |

---

## 11. JIT in Modern JVM (HotSpot)

HotSpot JVM uses both:

* **Interpreter (for quick startup)**
* **JIT (for performance)**

This hybrid approach is why Java can match native C/C++ performance in long-running applications.

---

## 12. Interview Questions

### Q1: What is JIT in Java?

A runtime compiler that converts frequently executed bytecode into native machine code to improve performance.

---

### Q2: Why does Java use both Interpreter and JIT?

Interpreter → fast startup
JIT → fast execution
Together → optimal performance.

---

### Q3: What is HotSpot JVM?

A JVM that detects hot code and optimizes it using JIT.

---

### Q4: What is method inlining?

Replacing method calls with actual code to reduce call overhead.

---

### Q5: What is tiered compilation?

A hybrid approach combining C1 (fast) + C2 (optimized) compilation.

---

## Key Takeaways

* The **Execution Engine** runs bytecode through Interpreter + JIT + GC.

* **JIT Compiler** transforms repeated bytecode into native code for high performance.

* Modern JVM uses **tiered compilation** for both fast startup and optimal execution.

* JIT includes advanced optimizations: inlining, loop unrolling, escape analysis, etc.

* JVM dynamically adapts to runtime behavior for maximum speed.

---
