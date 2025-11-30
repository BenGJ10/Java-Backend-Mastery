# Java Memory Management

## 1. Overview

Java memory management is handled automatically by the **JVM**, which allocates, manages, and reclaims memory through **automatic garbage collection**.
This prevents memory leaks and makes Java safer than languages that use manual memory allocation.

Java memory is divided into two main areas:

1. **Heap Memory**
2. **Stack Memory**

Plus additional areas like **Method Area**, **PC Registers**, and **Native Method Stack**.

---

## 2. JVM Memory Structure

Java divides memory into major runtime areas:

![Memory Structure](images/java_memory.png)

---

## 3. Heap Memory (Objects & Class Instances)

### Key Points:

* Stores **objects**, **arrays**, **instance variables**
* Shared among all threads
* Garbage Collector works **only in Heap**
* Largest memory area in JVM

### Memory Sections in Heap (Java 8+):

* **Young Generation**

  * Eden space
  * Survivor space S1/S2
* **Old Generation (Tenured)**

---

## 3.1 Young Generation

Newly created objects are allocated here.

### Consists of:

* **Eden** → new objects created
* **Survivor 1**
* **Survivor 2**

Objects surviving multiple GC cycles are promoted to the **Old Generation**.

---

## 3.2 Old Generation

* Contains long-living objects
* Full GC takes place here
* Heavy operations, more time-consuming

---

## 4. Stack Memory (Method Calls + Primitive Data)

Each thread has its own **Stack**.
Every method call creates a **stack frame** that stores:

* Local variables (primitives)
* References to objects in heap
* Return values
* Method call information

Stack is **LIFO (Last In First Out)**.

When a method finishes, its stack frame is removed.

---

## 5. Method Area (Class Area)

Stores:

* Class bytecode
* Static variables
* Method definitions
* Constant pool
* Field metadata
* Constructor information

Shared across all threads.

---

## 6. PC (Program Counter) Register

Each thread has a small memory area called a PC register used to track the **current executing instruction**.
Because of this we are able to have multiple threads executing independently.

---

## 7. Native Method Stack

For executing native (non-Java) code, like:

```java
System.loadLibrary("xyz");
```

---

## 8. Garbage Collection (Automatic Memory Cleanup)

Java uses garbage collection to remove unused objects from heap.

### When is an object eligible for GC?

If there are **no live references** pointing to it.

Example:

```java
Student s = new Student();
s = null;        // eligible for GC
```

Or:

```java
Student s1 = new Student();
Student s2 = new Student();
s1 = s2;         // Old object previously in s1 is now eligible
```

---

## 8.1 Types of Garbage Collectors

### 1. Serial GC

* Single-threaded
* Suitable for small applications

### 2. Parallel GC

* Multiple threads
* Best for CPU-intensive apps

### 3. CMS (Concurrent Mark Sweep) — deprecated in Java 14

* Low pause GC

### 4. G1 GC (Default from Java 9+)

* Region-based
* Low pause
* Balanced performance

---

## 9. Memory Leaks in Java (Possible Despite GC)

Even with GC, **logical memory leaks** can occur:

### Causes:

* Unclosed resources
* Static references holding objects
* List or Map growing indefinitely
* Listeners not removed
* Cache without eviction policy

Example:

```java
static List<Object> list = new ArrayList<>();
void add() {
    list.add(new Object()); // stays forever (static)
}
```

---

## 10. Finalization (Deprecated)

Earlier, Java used `finalize()` to clean up resources.
Now it's deprecated due to unpredictability.

Modern cleanup uses:

* try-with-resources
* AutoCloseable

Example:

```java
try (FileInputStream fs = new FileInputStream("a.txt")) {
    // auto-close
}
```

---

## 11. Memory Allocation Path

1. Method is invoked
2. Stack frame created
3. Variables allocated
4. Objects created in heap
5. Methods return → stack frame destroyed
6. Objects without references → GC

---

## 12. Stack vs Heap Comparison

| Feature       | Stack                                      | Heap                  |
| ------------- | ------------------------------------------ | --------------------- |
| Stores        | Local variables, references, method frames | Objects, arrays       |
| Access Speed  | Fast                                       | Slow                  |
| Managed By    | JVM                                        | Garbage Collector     |
| Thread-Safety | Each thread has its own                    | Shared across threads |
| Memory Size   | Small                                      | Large                 |
| Lifetime      | Ends after method                          | Until GC clears       |

---

## 13. Example: Memory Behavior in Java

```java
public void test() {
    int x = 10;              // stack
    String s = "hello";      // SCP reference
    Student st = new Student(); // heap
}
```

Memory distribution:

| Code           | Memory               |
| -------------- | -------------------- |
| `x = 10`       | Stack                |
| `"hello"`      | String Constant Pool |
| `st` reference | Stack                |
| Student object | Heap                 |

---

## 14. Escape Analysis (JIT Optimization)

The JVM may move an object from **heap → stack** if:

* Object does not escape the method
* No references exist outside method

This reduces GC load.

---

## 15. Common Interview Questions

### Q1: What is the difference between heap and stack?

Stack stores method frames & primitives; heap stores objects.

### Q2: What is Garbage Collection?

Automatic removal of unused objects.

### Q3: What is OutOfMemoryError?

Thrown when heap or method area memory is exhausted.

### Q4: What is a memory leak?

Objects remain referenced unintentionally, preventing GC.

### Q5: What is the difference between Young and Old generation?

Young = new objects; Old = long-lived objects.

---

## Key Takeaways

* Java memory is divided into **Heap**, **Stack**, **Method Area**, and more.

* **Heap** → objects; **Stack** → method calls, primitives.

* Garbage Collector automatically removes unused objects.

* Memory leaks can still occur due to improper code.

* Modern GC uses G1 and region-based memory management.

* String Constant Pool resides in heap (Java 7+).

---