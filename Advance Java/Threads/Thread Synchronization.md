# Thread Synchronization in Java

## 1. What Is Thread Synchronization?

When multiple threads access **shared resources** (variables, objects, files), inconsistent results may occur due to concurrent modifications.
This is known as a **race condition**.

**Thread Synchronization** ensures that only **one thread at a time** can access the critical section (shared resource), maintaining data consistency.

---

## 2. Why Synchronization Is Needed

Without synchronization:

* Threads may overwrite each other’s data
* Results become unpredictable
* Hard-to-debug issues occur
* Shared objects are not thread-safe

Example of race condition:

```java
class Counter {
    int count = 0;

    void increment() {
        count++;   // not atomic
    }
}
```

Multiple threads calling `increment()` can lead to incorrect results.

---

## 3. Types of Synchronization in Java

1. `synchronized` method
2. `synchronized` block
3. Static synchronization
4. Lock APIs (`ReentrantLock`)
5. Volatile keyword
6. Atomic variables
7. Inter-thread communication (`wait`, `notify`)

---

## 4. synchronized Keyword

Java’s simplest synchronization mechanism.

---

### 4.1 synchronized Method

Locks the entire method; only one thread can execute it at a time for that object.

```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }
}
```

### How it works:

* Lock is on the **current object** (`this`)
* Other threads trying to call `increment()` must wait

---

### 4.2 synchronized Block

Provides fine-grained control; recommended over synchronized methods when possible.

```java
class Counter {
    private int count = 0;

    void increment() {
        synchronized (this) {
            count++;
        }
    }
}
```

### Why use synchronized block?

* Locks only the needed section
* More efficient
* Reduces unnecessary blocking

---

## 5. Static Synchronization

Used when multiple threads access **static data**.

```java
class Counter {
    private static int count = 0;

    public static synchronized void increment() {
        count++;
    }
}
```

### Locking mechanism:

* Locks **class-level lock**
* Not object-level

Equivalent using block:

```java
synchronized (Counter.class) {
    count++;
}
```

---

## 6. Lock API (ReentrantLock)

More flexible locking than `synchronized`.

```java
Lock lock = new ReentrantLock();

lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}
```

### Advantages over synchronized:

* `tryLock()` (non-blocking attempt)
* Fairness settings
* Interruptible locks
* More control

---

## 7. volatile Keyword

Ensures **visibility** of shared variable updates across threads.

```java
volatile boolean flag = true;
```

What volatile guarantees:

* Read/write happens directly from main memory
* Prevents thread-local caching issues

What volatile does **not** guarantee:

* Atomic operations
* Mutual exclusion

So `volatile` is NOT a replacement for synchronization.

---

## 8. Atomic Variables (java.util.concurrent.atomic)

For lock-free thread-safe operations:

```java
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet();  // atomic operation
```

Atomic classes:

* AtomicInteger
* AtomicLong
* AtomicBoolean
* AtomicReference

Advantages:

* Very fast
* No blocking

---

## 9. Inter-Thread Communication (wait, notify)

Used to coordinate threads.

```java
synchronized (obj) {
    obj.wait();    // releases lock, waits
    obj.notify();  // wakes one waiting thread
}
```

Used for producer–consumer problems.

---

## 10. Example: Synchronized Counter

```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class Demo {
    public static void main(String[] args) throws Exception {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 1000; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 1000; i++) counter.increment();
        });

        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println(counter.getCount());
    }
}
```

**Output:**

```
2000
```

Without synchronization, result will be random (e.g., 1834, 1970).

---

## 11. Deadlock Due to Improper Synchronization

Example:

```java
synchronized(lock1) {
    synchronized(lock2) { }
}
```

Another thread locks `lock2` then waits for `lock1` → deadlock.

Prevent by:

* Lock ordering
* Using `tryLock()`
* Avoiding nested locks

---

## 12. Summary Table

| Mechanism        | Purpose              | Guarantees                 | Notes                        |
| ---------------- | -------------------- | -------------------------- | ---------------------------- |
| synchronized     | Mutual exclusion     | Atomicity + visibility     | Easy but blocking            |
| volatile         | Variable visibility  | Visibility only            | No mutual exclusion          |
| ReentrantLock    | Advanced locking     | Controlled locking         | Manual unlock                |
| Atomic Variables | Lock-free atomic ops | Atomicity + visibility     | Best performance             |
| wait/notify      | Thread coordination  | Inter-thread communication | Must use inside synchronized |

---

## Key Takeaways

* Synchronization prevents race conditions and inconsistent data.

* Use synchronized blocks for better control.

* Use ReentrantLock for advanced locking needs.

* Volatile ensures visibility but not atomicity.

* Atomic classes provide fast, lock-free thread-safe operations.

* Proper synchronization prevents deadlocks, race conditions, and memory issues.

---

