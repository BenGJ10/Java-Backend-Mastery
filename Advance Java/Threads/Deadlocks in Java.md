# Deadlocks in Java

##  What Is a Deadlock?

A **deadlock** occurs when two or more threads are **blocked forever**, each waiting for a resource (lock) held by another thread.
None of the threads can continue execution, causing the program to freeze.

Deadlock = **circular waiting + no thread can proceed**

---

## Coffman’s Conditions for Deadlock

A deadlock occurs when all of the following are true:

1. **Mutual Exclusion** – resources cannot be shared
2. **Hold and Wait** – a thread holds one lock and waits for another
3. **No Preemption** – locks cannot be forcibly taken
4. **Circular Wait** – thread A waits for B, B waits for C, C waits for A

If even one condition is broken → deadlock cannot occur.

---

## Classic Deadlock Example

```java
class DeadlockExample {
    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public void method1() {
        synchronized (lock1) {
            System.out.println("Thread 1: Holding lock1...");
            try { Thread.sleep(100); } catch (Exception e) {}
            
            synchronized (lock2) {
                System.out.println("Thread 1: Holding lock1 & lock2...");
            }
        }
    }

    public void method2() {
        synchronized (lock2) {
            System.out.println("Thread 2: Holding lock2...");
            try { Thread.sleep(100); } catch (Exception e) {}

            synchronized (lock1) {
                System.out.println("Thread 2: Holding lock2 & lock1...");
            }
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        DeadlockExample obj = new DeadlockExample();

        Thread t1 = new Thread(() -> obj.method1());
        Thread t2 = new Thread(() -> obj.method2());

        t1.start();
        t2.start();
    }
}
```

### What happens?

* `t1` acquires `lock1` and waits for `lock2`
* `t2` acquires `lock2` and waits for `lock1`
* Both wait forever → **deadlock**

---

## How to Avoid Deadlocks

### 1. Follow **consistent lock ordering**

Always acquire locks in the same order.

### 2. Use `tryLock()` (from `ReentrantLock`)

It attempts acquiring a lock without blocking forever.

```java
if(lock.tryLock(100, TimeUnit.MILLISECONDS)) { ... }
```

### 3. Minimize the scope of `synchronized`

Keep critical sections short.

### 4. Use higher-level concurrency tools

* `java.util.concurrent` locks
* Executors
* Synchronized queues
* Semaphores

### 5. Detect deadlocks (for debugging)

Use:

```
jstack <pid>
```

---

## Why `stop()`, `suspend()`, and `resume()` Are Deprecated in Java

These methods existed in early Java versions but were deprecated because they are **unsafe** and can lead to **serious concurrency issues**.

### Why `stop()` Is Deprecated

```java
thread.stop();
```

### 1. Problems with `stop()`

#### ✔ It kills a thread **immediately**, without cleanup

The thread:

* Does NOT release locks
* Does NOT complete critical sections
* May leave shared objects in **corrupted states**

#### ✔ It violates thread safety

Example scenario:

```
Thread acquires a lock → modifies object → stop() kills it → lock never released → deadlock
```

#### ✔ Risk of inconsistent data

If a thread is updating a shared list, and `stop()` interrupts halfway:

* List may become partially updated
* Other threads may crash

### 2. Modern Replacement for `stop()`

Use a **flag** to stop threads gracefully.

```java
class MyTask implements Runnable {
    private volatile boolean running = true;

    public void stop() { running = false; }

    public void run() {
        while (running) {
            // task logic
        }
    }
}
```

---

## Why `suspend()` and `resume()` Are Deprecated

### `suspend()`

Pauses a thread **without releasing locks**.

### `resume()`

Resumes a suspended thread.

---

### Problems with `suspend()`

#### Problem 1: **Thread holds locks while suspended**

If a thread suspended itself in a synchronized block:

```java
synchronized(obj) {
    thread.suspend(); // locks never released!
}
```

* Other threads waiting for `obj` → blocked forever → **deadlock**

---

#### Problem 2: Uncontrolled Pausing of Threads

If you call `suspend()` at the wrong moment:

* The thread might be holding a critical resource
* Entire application can freeze

Since the caller cannot know what the thread is doing, it's unsafe.

---

#### Problems with `resume()`

If a thread is not suspended but you call `resume()`:

* Nothing happens (no error)
* Logic becomes unpredictable

If `resume()` is lost before the thread suspends → thread stays suspended **forever**.

---

#### Modern Replacement

Use:

* `wait() / notify()`
* `LockSupport.park()` / `unpark()`
* Conditions (`ReentrantLock.newCondition()`)

Example using wait/notify:

```java
synchronized (lock) {
    lock.wait();  // replaces suspend()
}
```

```java
synchronized (lock) {
    lock.notify();  // replaces resume()
}
```

---

## Summary: Why These Methods Are Deprecated

| Method          | Reason for Deprecation                                                        |
| --------------- | ----------------------------------------------------------------------------- |
| `stop()`        | Causes inconsistent object states, breaks synchronization, can corrupt memory |
| `suspend()`     | Suspends threads while holding locks → causes deadlocks                       |
| `resume()`      | Can be called at wrong time → thread may remain suspended forever             |
| Combined effect | Unpredictable behavior, deadlocks, race conditions                            |

---

## Recommended Modern Alternatives

| Old Method  | Modern Safe Alternative                              |
| ----------- | ---------------------------------------------------- |
| `stop()`    | Graceful termination with flags (`volatile boolean`) |
| `suspend()` | `wait()`, `LockSupport.park()`                       |
| `resume()`  | `notify()`, `LockSupport.unpark()`                   |

These alternatives ensure the thread:

* Releases locks
* Completes critical operations
* Does not corrupt shared memory

---

## Interview Questions

### 1. What is a deadlock?

Two or more threads waiting for each other forever, causing the program to freeze.

---

### 2. How do deadlocks occur?

When multiple threads acquire locks in different orders, creating circular dependency.

---

### 3. How do you prevent deadlocks?

* Consistent lock order
* Use `tryLock()`
* Reduce synchronized scope
* Avoid nested locks

---

### 4. Why was `stop()` deprecated?

It kills a thread immediately **without releasing locks**, leaving objects in inconsistent states.

---

### 5. Why were `suspend()` and `resume()` deprecated?

`suspend()` suspends a thread while holding locks → deadlocks.
`resume()` doesn’t ensure correct resume timing → unpredictable behavior.

---

### 6. What is the safe way to stop a thread?

Use a `volatile boolean` flag or interrupt mechanism.

---

### 7. Can deadlocks be detected at runtime?

Yes, using tools like:

```
jstack <pid>
```

---

## Key Takeaways

* Deadlocks occur due to circular wait and improper lock handling

* `stop()`, `suspend()`, and `resume()` are unsafe and cause deadlocks or memory corruption

* Modern Java uses **interrupts**, **wait/notify**, **LockSupport**, and **thread pools**

* Avoid forcing threads to stop or suspend abruptly

* Always design thread-safe and lock-safe code

---
