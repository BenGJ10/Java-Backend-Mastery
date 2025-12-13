# Locks and Semaphores in Java

## 1. Introduction

Java provides multiple mechanisms to control access to shared resources in multithreaded applications.
The two core synchronization primitives are:

* **Locks** (Mutual Exclusion / Mutex)
* **Semaphores** (Permits-based Concurrency Control)

They help prevent:

* Race conditions
* Data inconsistency
* Concurrent modification issues

This section explains both concepts, how Java implements them, how to build **custom locks**, and when to use which.

---

## 2. What Is a Lock?

A **Lock** allows only **one thread** at a time to execute critical code (mutual exclusion).

It is similar to using `synchronized`, but with additional flexibility:

* Try acquiring lock (`tryLock()`)
* Lock with timeout
* Fair or unfair locking
* Interruptible lock acquisition
* Manual lock release

Java provides locks via:

```
java.util.concurrent.locks.Lock
```

---

## 3. ReentrantLock — The Most Common Lock in Java

A **Reentrant Lock** allows a thread that already holds the lock to acquire it again (re-entrant behavior).


### 3.1 Basic Example

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();  // acquire lock
        try {
            count++;
        } finally {
            lock.unlock();  // must release lock
        }
    }

    public int getCount() { return count; }
}
```

### Key Notes:

* `lock.lock()` blocks until lock is available
* `unlock()` **must** be inside `finally` to prevent deadlocks

---

## 4. Advanced Lock Features

### 4.1 `tryLock()`

Attempts to acquire lock **without blocking**.

```java
if (lock.tryLock()) {
    try {
        // critical section
    } finally {
        lock.unlock();
    }
} else {
    System.out.println("Lock busy");
}
```

Useful for **deadlock avoidance**.


### 4.2 `tryLock(long timeout, TimeUnit unit)`

Waits for a certain duration.

```java
if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
    try { ... } finally { lock.unlock(); }
}
```

### 4.3 `lockInterruptibly()`

Thread can be interrupted while waiting for the lock.

```java
lock.lockInterruptibly();
```

Useful in cancellation-sensitive tasks.

### 4.4 Fair vs Unfair Locking

```java
Lock fairLock = new ReentrantLock(true);  // fair
Lock unfairLock = new ReentrantLock(false);  // default
```

Fair lock:

* Ensures **first come first served**

Unfair lock:

* Higher performance
* May cause thread starvation

---

## 5. ReadWriteLock

Allows:

* **Multiple readers** to access shared data concurrently
* Only **one writer** at a time (exclusive lock)
* When reading, writers are blocked, and vice versa

Example:

```java
ReadWriteLock rw = new ReentrantReadWriteLock();
Lock read = rw.readLock();
Lock write = rw.writeLock();
```

---

## 6. What Is a Semaphore?

A **Semaphore** controls access using a fixed number of **permits**.
Threads acquire permits before accessing a resource.

Two types:

1. **Binary Semaphore** (1 permit) → behaves like a lock
2. **Counting Semaphore** (N permits) → limits concurrent access

Java implementation:

```
java.util.concurrent.Semaphore
```

---

## 7. Semaphore Example (Limiting Concurrent Threads)

```java
import java.util.concurrent.Semaphore;

class Printer {
    private final Semaphore semaphore = new Semaphore(3); // allow 3 threads

    public void printDocument(String doc) {
        try {
            semaphore.acquire();  // acquire permit
            System.out.println(Thread.currentThread().getName() + " printing " + doc);
            Thread.sleep(1000);
        } catch (Exception e) {} 
        finally {
            semaphore.release();  // release permit
        }
    }
}
```

3 threads can print simultaneously; others wait.

---

## 8. Producer–Consumer Using Semaphore (Classic Example)

```java
Semaphore empty = new Semaphore(5);  // buffer size = 5
Semaphore full = new Semaphore(0);
Semaphore mutex = new Semaphore(1);

void produce() throws Exception {
    empty.acquire();
    mutex.acquire();
    // add item
    mutex.release();
    full.release();
}

void consume() throws Exception {
    full.acquire();
    mutex.acquire();
    // remove item
    mutex.release();
    empty.release();
}
```

Semaphores coordinate buffer usage.

---

## 9. Locks vs Semaphores (Differences)

| Feature   | Lock               | Semaphore                             |
| --------- | ------------------ | ------------------------------------- |
| Purpose   | Mutual exclusion   | Limit concurrent access               |
| Permits   | 1 (usually)        | 1 or more                             |
| Ownership | Thread-owned       | Not thread-owned                      |
| Release   | Only owning thread | Any thread can release                |
| Use Case  | Critical sections  | Resource pools (connections, buffers) |

---

## 10. When to Use What?

### Use **Locks** when:

* Only one thread should access a critical section
* Need fairness, interruptibility, or timeout-based lock acquisition

### Use **Semaphores** when:

* Limiting number of threads accessing a resource (pool, queue)
* Coordinating threads (producer–consumer)
* Creating bounded systems

---

## 11. Custom Locks (Implementing Your Own Lock)

You can create a custom lock using:

* `volatile`
* busy-wait (spinlock)
* CAS operations (`AtomicBoolean`)
* Semaphores

### 11.1 Simple Spinlock Using `AtomicBoolean`

```java
import java.util.concurrent.atomic.AtomicBoolean;

class SpinLock {
    private AtomicBoolean locked = new AtomicBoolean(false);

    public void lock() {
        while (!locked.compareAndSet(false, true)) {
            // spin until lock becomes available
        }
    }

    public void unlock() {
        locked.set(false);
    }
}
```

### Shortcomings:

* CPU-intensive (busy waiting)
* Good only for very short locks

### 11.2 Custom Lock Using Semaphore (Binary Semaphore)

```java
class SimpleLock {
    private final Semaphore semaphore = new Semaphore(1);

    public void lock() throws InterruptedException {
        semaphore.acquire(); // only 1 thread allowed
    }

    public void unlock() {
        semaphore.release();
    }
}
```

### 11.3 Custom Reentrant Lock

A reentrant lock allows the same thread to acquire lock multiple times.

```java
class CustomReentrantLock {
    private Thread owner = null;
    private int holdCount = 0;

    public synchronized void lock() {
        Thread current = Thread.currentThread();
        while (owner != null && owner != current) {
            try { wait(); } catch (Exception e) {}
        }
        owner = current;
        holdCount++;
    }

    public synchronized void unlock() {
        if (Thread.currentThread() == owner) {
            holdCount--;
            if (holdCount == 0) {
                owner = null;
                notifyAll();
            }
        }
    }
}
```

---

## 12. Advantages of Using Java’s Lock Framework

* More flexible than `synchronized`
* Provides condition variables (`Condition`)
* Can avoid deadlocks using `tryLock`
* Supports fairness policies
* Better for highly concurrent systems

---

## 13. Interview Questions

### Q1. What is a lock?

A synchronization mechanism that allows only one thread to execute a block of code at a time.

---

### Q2. Difference between `synchronized` and `Lock`?

`synchronized` → automatic lock release, simpler
`Lock` → manual release, more powerful (tryLock, fairness, interruptibility)

---

### Q3. What is a semaphore?

A counter controlling how many threads may access a shared resource.

---

### Q4. Difference between binary and counting semaphore?

Binary → like a lock (1 permit)
Counting → multiple permits (resource pool)

---

### Q5. Why are locks preferred over `synchronized` sometimes?

* Ability to check lock availability
* Interruptible locking
* Lock fairness
* Condition variables

---

### Q6. Can semaphore be released by a thread that didn't acquire it?

Yes. This is why semaphores are **not thread-exclusive**, unlike locks.

---

### Q7. What is a spinlock?

A lock where thread waits in a loop until lock is free (busy waiting).

---

### Q8. What is reentrancy?

A thread holding a lock can acquire it again without blocking.

---

## Key Takeaways

* **Locks** → mutual exclusion

* **Semaphores** → permit-based concurrency

* Java provides powerful lock APIs in `java.util.concurrent.locks`

* Custom locks can be implemented for learning or specialized use cases

* Understanding locks is critical for building thread-safe applications

---

