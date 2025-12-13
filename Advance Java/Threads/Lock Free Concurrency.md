# Lock-Free Concurrency in Java

## 1. Introduction

Traditional concurrency relies on **locks** (synchronized blocks, ReentrantLock).
However, locks can cause:

* **Blocking**
* **Contention**
* **Deadlocks**
* **Context switching overhead**

To solve this, Java provides **lock-free concurrency** through:

1. **CAS (Compare-and-Swap) operations**
2. **Atomic variables (java.util.concurrent.atomic)**
3. **volatile keyword (visibility guarantees)**

Lock-free algorithms improve performance in highly concurrent systems (CPUs with many cores).

---

## 2. What Is Lock-Free Concurrency?

**Lock-free concurrency** means multiple threads operate on shared data **without acquiring mutual exclusion locks**.

Instead of locking, lock-free algorithms use:

* Non-blocking updates
* Atomic operations
* Hardware-level CAS instructions

### Benefits:

* Avoid deadlocks
* No thread blocking
* Faster on multi-core machines
* Lower context-switching overhead
* High scalability

### Where used?

* Concurrent data structures (ConcurrentHashMap, ConcurrentLinkedQueue)
* Atomic variables
* ForkJoinPool
* Disruptor frameworks

---

## 3. Compare-and-Swap (CAS)

CAS is the foundation of lock-free algorithms.

CAS is a **hardware-provided atomic instruction**.

```
CAS(location, expectedValue, newValue)
```

Meaning:

> "Update the variable at `location` to `newValue` **only if** its current value == `expectedValue`."

If another thread changes the value simultaneously, CAS **fails** and the thread retries.

**3.1 What CAS Really Does (Internal Meaning)**

CAS performs this check-and-update **as one atomic CPU instruction**:

```
if (*address == expectedValue)
    *address = newValue
return successOrFailure;
```

This guarantees that **no other thread** can modify the memory location while this check occurs — making it atomic without using locks.

---

**3.2 CPU-Level Implementation**

Most modern CPUs provide machine instructions for CAS:

| Architecture | Instruction                    |
| ------------ | ------------------------------ |
| x86          | `CMPXCHG`, `LOCK CMPXCHG`      |
| ARM          | `LDXR/STXR` pair (LL/SC model) |
| PowerPC      | `lwarx/stwcx.` (LL/SC model)   |

Java abstracts this using **Unsafe.compareAndSwapXXX()**, used internally by all Atomic classes.

---

**3.3 CAS Uses *Memory Fences***

CAS implicitly includes **memory barriers**, ensuring:

1. **Loads before CAS cannot move after it**
2. **Stores after CAS cannot move before it**

This guarantees ordering (part of Java's *happens-before* semantics).

---

**3.4 Why CAS Is Faster Than Locks**

1. **No thread blocking**

   * No context switching
   * No kernel involvement
2. **Low latency**

   * Single CPU instruction (plus cache coherency)
3. **Better scalability**

   * Multiple threads can *attempt* to update simultaneously
4. **Optimistic concurrency**

   * Threads assume success; if not, retry

Whereas locks use **pessimistic concurrency**:

> "Assume someone else will interfere, so lock first."

---

**3.5 Example: CAS Retry Loop (Spin Loop)**

CAS is often used in a loop until it succeeds:

```java
AtomicInteger count = new AtomicInteger(0);

void increment() {
    int current;
    do {
        current = count.get();
    } while (!count.compareAndSet(current, current + 1));
}
```

This is called a **spin loop** because the thread *keeps retrying* until CAS succeeds.

---

**3.6 CAS Contention Behavior**

Under heavy contention:

* Many threads repeatedly fail CAS
* CPUs waste cycles in spinning
* This leads to **livelock** (system active, no progress)

Compared to locks:

* Locks cause blocking → less CPU burn but more context switch overhead
* CAS loops cause spinning → more CPU burn but lower latency

---

**3.7 CAS in Java’s Concurrency Frameworks**

CAS is used internally in nearly all non-blocking Java components:

| Component                     | Purpose                              |
| ----------------------------- | ------------------------------------ |
| `AtomicInteger`               | Lock-free counters                   |
| `ConcurrentHashMap`           | Node updates, resizing, CAS inserts  |
| `ConcurrentLinkedQueue`       | Lock-free linked queues              |
| `LinkedTransferQueue`         | High-performance task transfer queue |
| `LongAdder / LongAccumulator` | Striping + CAS for high throughput   |
| `StampedLock` (optimistic)    | CAS on stamp values                  |

Even Java's **synchronized** is partially CAS-based on newer JVMs for lock elimination and biased locking.

---

**3.8 Logical Flow of CAS**

- Step 1: Thread reads the current value

- Step 2: Thread computes the new value

- Step 3: CAS attempts to write the value

- Step 4: If CAS fails → retry

```
Thread A: expected=5, new=6
Thread B: expected=5, new=7

A CAS(5,6) → SUCCESS
B CAS(5,7) → FAIL (value is now 6)
```

Thread B retries with updated expectation.

---

**3.9 CAS vs LL/SC (Load-Linked / Store-Conditional)**

Some CPUs (e.g., ARM, PowerPC) use LL/SC instead of CAS.

| CAS                         | LL/SC                             |
| --------------------------- | --------------------------------- |
| Single atomic instruction   | Pair of instructions              |
| Must retry on failed CAS    | Must retry on failed SC           |
| Simpler hardware design     | More flexible for complex updates |
| Used by Java via intrinsics | JVM maps CAS to LL/SC internally  |

Functionally, **both achieve the same end result**: lock-free atomic updates.

---

**3.10 ABA Problem — Deep Explanation**

CAS compares values, not state history.

If the value becomes:

```
A → B → A
```

CAS sees `A` again and assumes nothing changed.

Example risk: lock-free stack

1. Thread A reads top = NodeA
2. Thread B pops NodeA, pushes NodeB, pushes NodeA back
3. Thread A CAS(top, NodeA, newNode) → succeeds incorrectly

The structure becomes corrupted.

---

**3.11 Solving ABA — Techniques**

**1. AtomicStampedReference**

Adds a version number:

```
(A, 1) → (B, 2) → (A, 3)
```

CAS checks both:

* value
* stamp

**2. AtomicMarkableReference**

Tracks only *boolean* marks for quick checks.

**3. Hazard Pointers / Epoch-based Reclamation**

Used in advanced lock-free designs (not provided by Java directly).

---

**3.12 CAS Limitations (Important for Interviews)**

1. **CAS cannot update multiple variables atomically**

   * Need locks or immutability
2. **CAS loops may spin forever under contention**
3. **ABA problem**
4. **Complex to design lock-free structures**
5. **High CPU usage due to retries**

CAS is not a magic bullet — it's a primitive for designing non-blocking algorithms.

---

**3.13 CAS vs Locks — When to Use What**

Use **CAS (atomic classes)** when:

✔ simple variable updates
✔ counters, flags, reference swaps
✔ low contention
✔ high performance needed

Use **locks** when:

✔ multi-step operations
✔ updating multiple related variables
✔ high contention (to avoid spin-wasting CPU)
✔ design simplicity matters

---

**3.14 Real-World CAS Example (Java 17+)**

ConcurrentHashMap `putIfAbsent` uses CAS internally:

```java
if (node == null) {
    if (casTabAt(tab, index, null, new Node(key, value)))
        return null;
}
```

This CAS ensures only one thread inserts into an empty bucket.

---

**3.15 Visual Representation of CAS**

```
Thread A                Thread B
----------              ----------
reads V=10              reads V=10
expected=10             expected=10
tries CAS(10,11)        tries CAS(10,12)

CAS succeeds → V=11     CAS fails (V != 10)
                        retries...
```

This illustrates **optimistic concurrency**.

---

## 4. Atomic Variables in Java

Java provides lock-free atomic classes under:

```
java.util.concurrent.atomic
```

They use CAS internally.

### 4.1 Common Atomic Classes

| Class                    | Purpose                 |
| ------------------------ | ----------------------- |
| `AtomicInteger`          | Atomic int operations   |
| `AtomicLong`             | Atomic long operations  |
| `AtomicBoolean`          | Atomic boolean          |
| `AtomicReference<T>`     | Atomic object reference |
| `AtomicStampedReference` | Fixes ABA problem       |
| `AtomicIntegerArray`     | Atomic array operations |

Atomic classes provide operations like:

* `get()`
* `set()`
* `getAndIncrement()`
* `incrementAndGet()`
* `compareAndSet(expected, update)`

### 4.2 Example: Using AtomicInteger

```java
AtomicInteger count = new AtomicInteger(0);

void increment() {
    count.incrementAndGet();
}
```

Internally:

```
value = value + 1 using CAS (lock-free)
```

### 4.3 AtomicReference Example (Atomic objects)

```java
AtomicReference<String> ref = new AtomicReference<>("A");

ref.compareAndSet("A", "B");  // atomic object update
```

### 4.4 Fixing ABA Problem: AtomicStampedReference

```java
AtomicStampedReference<String> ref =
        new AtomicStampedReference<>("A", 1);

int stamp = ref.getStamp();
ref.compareAndSet("A", "B", stamp, stamp + 1);
```

Stamp (version) solves ABA:

```
A → B → A
stamp: 1 → 2 → 3
```

CAS now detects changes.

---

## 5. volatile Variables

`volatile` ensures **visibility**, not atomicity.

### Guarantees:

1. **Visibility**:
   A write to a volatile variable is immediately visible to all threads.

2. **Ordering (happens-before guarantee)**:
   Prevents instruction reordering around volatile reads/writes.

### Does NOT guarantee:

* Atomic increments
* Thread safety for compound operations

---

### 5.1 Example: Visibility Problem Without volatile

```java
boolean running = true;

void worker() {
    while (running) { }
}
```

If another thread sets:

```
running = false;
```

Worker thread **may never see** the update → infinite loop.

### 5.2 Fix Using volatile

```java
volatile boolean running = true;
```

Now updates become visible to all threads immediately.

---

## 6. volatile vs Atomic vs Lock

| Feature               | volatile | Atomic    | Lock             |
| --------------------- | -------- | --------- | ---------------- |
| Visibility            | Yes      | Yes       | Yes              |
| Atomic operations     | No       | Yes (CAS) | Yes              |
| Blocking              | No       | No        | Yes              |
| Prevents deadlock     | Yes      | Yes       | No               |
| Multi-field atomicity | No       | No        | Yes (sync block) |
| Performance           | Fastest  | Fast      | Slower           |

### When to use which?

* **volatile** → shared flags, single variable visibility
* **atomic classes** → counters, reference updates, lock-free operations
* **locks** → complex atomic sections, multi-variable updates

---

## 7. Lock-Free Increment Example (AtomicInteger)

```java
AtomicInteger count = new AtomicInteger();

public void increment() {
    count.incrementAndGet();  // lock-free
}
```

---

## 8. Lock-Free Stack Example (Using AtomicReference)

```java
class Node {
    int value;
    Node next;
    Node(int value, Node next) { this.value = value; this.next = next; }
}

class LockFreeStack {
    private AtomicReference<Node> top = new AtomicReference<>(null);

    public void push(int value) {
        Node newNode;
        Node oldTop;
        do {
            oldTop = top.get();
            newNode = new Node(value, oldTop);
        } while (!top.compareAndSet(oldTop, newNode));
    }
}
```

This is how **ConcurrentLinkedQueue** works internally.

---

## 9. Why Lock-Free Concurrency Matters

### Benefits:

* High throughput
* Reduced blocking
* Better CPU utilization
* Lower latency for concurrent collections
* Core of Java's modern concurrency structures:

  * ConcurrentHashMap
  * ConcurrentLinkedQueue
  * Atomic classes
  * ForkJoinPool
  * StampedLock (optimistic read locks)

Lock-free algorithms scale extremely well in multi-core CPUs.

---

## 10. Interview Questions

### Q1. What is CAS?

An atomic operation that updates a value only if its current value matches an expected value.

---

### Q2. Why is CAS used in Java?

To implement **lock-free**, non-blocking synchronization.

---

### Q3. What is the ABA problem?

When a value goes A → B → A and CAS incorrectly assumes no change.

Solution: `AtomicStampedReference`.

---

### Q4. Is volatile atomic?

No. Only ensures visibility.

---

### Q5. Difference between AtomicInteger and synchronized?

| AtomicInteger          | synchronized                  |
| ---------------------- | ----------------------------- |
| Lock-free              | Blocking                      |
| CAS-based              | JVM monitor                   |
| Faster                 | Slower under contention       |
| Atomic single-variable | Can handle multiple variables |

---

### Q6. When is volatile enough?

When:

* One variable
* Read/write only
* No dependent operations

---

### Q7. Why are atomic classes faster than locks?

They use **CPU-level instructions** (CAS) instead of blocking synchronization.

---

### Q8. Can volatile replace AtomicInteger?

No. Not for increments or multi-step operations.

---

## Key Takeaways

* **CAS** is the foundation of Java's lock-free algorithms.

* **Atomic classes** provide thread-safe, non-blocking operations.

* **volatile** ensures visibility, not atomicity.

* Lock-free concurrency avoids deadlocks and improves performance.

* Use atomic types for counters and simple updates; use locks for complex critical sections.

---

