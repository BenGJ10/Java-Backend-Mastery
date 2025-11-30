# Garbage Collection in Java

## 1. What Is Garbage Collection?

**Garbage Collection (GC)** in Java is an automatic process that identifies and removes **unused objects** from the **heap memory** to free space and prevent memory leaks.

Java’s GC eliminates the need for manual memory management (unlike C/C++), making applications safer and more reliable.

GC works only on **Heap Memory**, not on stack.

---

## 2. When Does an Object Become Eligible for Garbage Collection?

An object becomes eligible for GC when **no live reference** points to it.

### Examples:

### 2.1 Reference set to null

```java
Student s = new Student();
s = null;   // eligible
```

### 2.2 Reference reassigned

```java
Student s1 = new Student();
Student s2 = new Student();
s1 = s2;   // old object of s1 eligible
```

### 2.3 Object created inside method

```java
void test() {
    Student s = new Student();
}   // s goes out of scope → object eligible
```

### 2.4 Island of Isolation

Two objects referencing each other BUT no external references.

```java
A a = new A();
B b = new B();

a.b = b;
b.a = a;

a = null;
b = null;
// Both eligible for GC (mutually reachable but unreachable from program roots)
```

---

## 3. How Garbage Collection Works (Detailed)

Modern JVM uses multiple generations to optimize GC.

---

### 3.1 Heap Memory Structure (Java 8+)

```
Heap
 ├── Young Generation
 │       ├── Eden
 │       ├── Survivor 1
 │       └── Survivor 2
 └── Old Generation
```

---

### 3.2 Young Generation (Minor GC)

Where new objects are created.

* **Eden**: All new objects allocated here
* After Minor GC → surviving objects moved to **Survivor 1**
* Next GC → moved to **Survivor 2**
* After several cycles → promoted to **Old Generation**

Minor GC is **frequent** and **fast**.

---

### 3.3 Old Generation (Major/Full GC)

Contains long-lived objects.

* Full GC is costlier and can cause noticeable pause times.
* Uses **Mark-Sweep-Compact** algorithm.

---

## 4. GC Algorithms in Java

---

### 4.1 Mark and Sweep

#### Steps:

1. **Mark**: Traverse object graph and mark reachable objects.
2. **Sweep**: Remove unmarked (dead) objects.
3. **Compact (optional)**: Rearrange memory to avoid fragmentation.

---

### 4.2 Generational Garbage Collection

Based on the idea:
**Most objects die young**.

So JVM stores objects in generations and applies different GC strategies per generation.

Results: Faster GC and better performance.

---

### 4.3 Copying Algorithm (Young Gen)

In young generation:

* Live objects are copied between survivor spaces
* Dead objects are discarded
* Very fast because copying uses sequential allocation

---

### 4.4 Stop-the-World (STW)

Certain GC phases **pause all running threads**.

STW pauses can occur during:

* Minor GC
* Major GC
* Compaction
* Root scanning

---

## 5. Types of Garbage Collectors in Java

---

### 5.1 Serial Garbage Collector

* Single-threaded
* Suitable for single-core, small applications
* Used in client machines

Enable using:

```
-XX:+UseSerialGC
```

---

### 5.2 Parallel Garbage Collector (Throughput GC)

* Multi-threaded GC for young gen
* Highest throughput
* Used in server-class machines

Enable:

```
-XX:+UseParallelGC
```

---

### 5.3 CMS (Concurrent Mark Sweep) — *Deprecated from Java 14*

* Low pause collector
* Most work done concurrently with application threads
* Not used in modern Java

Enable:

```
-XX:+UseConcMarkSweepGC
```

---

### 5.4 G1 Garbage Collector (Default from Java 9+)

* Region-based GC
* Low latency
* Predictable pause times
* Divides heap into many small regions (1–32 MB)

Enable:

```
-XX:+UseG1GC
```

---

### 5.5 Z Garbage Collector (ZGC)

* Ultra low latency (<10ms pauses)
* Suitable for large heaps (multi-GB to TB)
* Mostly concurrent

Enable:

```
-XX:+UseZGC
```

---

### 5.6 Shenandoah GC

* Red Hat’s low-pause GC
* Similar to ZGC

Enable:

```
-XX:+UseShenandoahGC
```

---

## 6. Finalization (Deprecated)

`finalize()` was used for cleanup before GC.

```java
@Override
protected void finalize() {
    System.out.println("Finalize called");
}
```

But it's deprecated due to:

* Unpredictability
* Poor performance
* Dangerous behavior

### Modern replacement:

* **try-with-resources**
* **AutoCloseable**

---

## 7. Forcing Garbage Collection (Not guaranteed)

You *may* request GC but JVM decides whether to run it.

```java
System.gc();
Runtime.getRuntime().gc();
```

The JVM **may ignore this call**.

---

## 8. Memory Leaks in Java (Even with GC)

Garbage Collector cannot solve logical memory leaks.

### Common Causes:

#### 8.1 Long-living static references

```java
static List<String> list = new ArrayList<>();
```

#### 8.2 Unclosed resources

```java
FileInputStream fs = new FileInputStream("a.txt");
```

#### 8.3 Caches without eviction

#### 8.4 Listeners not removed in GUI apps

#### 8.5 Poorly managed collections

---

## 9. Reference Types and Garbage Collection

Java has four types of references:

| Reference Type | Package                       | Purpose                                      |
| -------------- | ----------------------------- | -------------------------------------------- |
| Strong         | Default                       | Never collected                              |
| Weak           | `java.lang.ref.WeakReference` | Collected aggressively (used in WeakHashMap) |
| Soft           | `SoftReference`               | Collected only when memory is low            |
| Phantom        | `PhantomReference`            | Used for post-mortem cleanup                 |

---

## Example: WeakReference

```java
WeakReference<Student> ref = new WeakReference<>(new Student());
```

Object is collected when no strong reference exists.

---

## 10. Tools to Monitor GC

### 1. jconsole

### 2. jvisualvm

### 3. jstat

### 4. GC logs

Enable GC logging:

```
-XX:+PrintGCDetails -XX:+PrintGCTimeStamps
```

---

## 11. Example: Full GC Cycle Visualization

```
1. New object → Eden
2. Minor GC → Survivor 1
3. Next Minor GC → Survivor 2
4. After several cycles → Old Gen
5. Old Gen fills → Full GC
6. Mark → Sweep → Compact
```

---

## 12. Interview Questions

### Q1. What are the different generations in heap memory?

Young (Eden + S1 + S2) and Old Gen.

### Q2. What triggers a Full GC?

* Old gen becomes full
* Metadata area needs cleaning
* System.gc() (not recommended)

### Q3. Why was CMS deprecated?

Higher overhead, fragmentation, complex implementation.

### Q4. What is stop-the-world (STW)?

JVM pauses all threads during certain GC phases.

### Q5. Is GC guaranteed when calling System.gc()?

No. JVM may ignore the request.

### Q6. Difference between Minor GC and Major GC?

* Minor → short pause, young gen
* Major → long pause, old gen

### Q7. What is memory leak in Java?

Objects unintentionally remain referenced and cannot be garbage collected.

---

## Key Takeaways

* GC removes unreachable objects to free heap memory.

* Works with multiple generations: Young, Survivor, Old.

* Minor GC: fast; Full GC: slow but performs compaction.

* Several collectors exist: Serial, Parallel, G1 (default), ZGC, Shenandoah.

* `finalize()` is deprecated; use `AutoCloseable` instead.

* Memory leaks can still occur if references are unintentionally kept.

* GC improves performance but must be tuned for large applications.

---