# Threads in Java

## 1. What Is a Thread?

A **thread** is the smallest unit of execution within a process.
In Java:

* A **process** = program in execution
* A **thread** = lightweight sub-process
* Multiple threads can run **concurrently**

Java provides **built-in multithreading support** using the `Thread` class and the `Runnable` interface.

---

## 2. Why Use Threads?

### Benefits:

* Perform multiple tasks at the same time
* Better CPU utilization
* Improves performance for I/O-based tasks
* Enables asynchronous programming
* Useful in server applications, games, UI apps, background jobs

---

## 3. How to Create a Thread

There are **two main ways**:

---

## 3.1 Extending the Thread Class

```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

public class Demo {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start(); // starts a new thread
    }
}
```

### Important:

* `run()` → contains task logic
* `start()` → creates a new thread and then calls `run()`

Never call `run()` directly.

---

## 3.2 Implementing Runnable Interface

```java
class MyTask implements Runnable {
    public void run() {
        System.out.println("Task executed by: " + Thread.currentThread().getName());
    }
}

public class Demo {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyTask());
        t1.start();
    }
}
```

### When to use Runnable?

* If your class already extends another class
* Cleaner and better for multiple-thread usage

---

## 3.3 Using Lambda (Java 8+)

```java
Thread t = new Thread(() -> System.out.println("Lambda thread"));
t.start();
```

---

## 4. Thread Lifecycle (States)

A thread goes through several states defined in `Thread.State`.

```
NEW → RUNNABLE → RUNNING → BLOCKED/WAITING → TERMINATED
```

### Explanation:

| State                       | Meaning                              |
| --------------------------- | ------------------------------------ |
| **NEW**                     | Thread created but not started       |
| **RUNNABLE**                | Thread is ready to run               |
| **RUNNING**                 | Actively executing                   |
| **BLOCKED**                 | Waiting for a lock                   |
| **WAITING / TIMED_WAITING** | Waiting for another thread to signal |
| **TERMINATED**              | Finished execution                   |

---

## 5. Thread Methods

### Common Methods

| Method            | Description                          |
| ----------------- | ------------------------------------ |
| `start()`         | Starts a new thread                  |
| `run()`           | Executes thread logic                |
| `sleep(ms)`       | Pauses thread                        |
| `join()`          | Waits for another thread to finish   |
| `interrupt()`     | Interrupts a sleeping/waiting thread |
| `isAlive()`       | Checks if thread is running          |
| `setPriority()`   | Sets priority (1–10)                 |
| `currentThread()` | Returns current thread object        |

---

## 6. Thread Priority

Range: **1 to 10**

```java
Thread t = new Thread();
t.setPriority(Thread.MAX_PRIORITY); // 10
```

Higher priority **may** get more CPU time, but JVM scheduling varies—no guarantee.

---

## 7. Sleep Method

```java
try {
    Thread.sleep(1000); // 1 second
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

Used for:

* Pauses
* Timers
* Simulating delay

---

## 8. Joining a Thread

`join()` waits for a thread to finish before continuing.

```java
t1.start();
t1.join(); // wait until t1 finishes
System.out.println("t1 completed");
```

---

## 9. Daemon Threads (Background Threads)

Daemon threads run in the background (e.g. garbage collector).

```java
Thread t = new Thread(() -> System.out.println("Daemon thread"));
t.setDaemon(true);
t.start();
```

Characteristics:

* Low priority
* JVM ends when only daemon threads remain

---

## 10. Thread Synchronization

When multiple threads access **shared resources**, we must prevent race conditions.

### Using synchronized keyword:

```java
synchronized void increment() {
    count++;
}
```

Can be used on:

* Methods
* Blocks

### Example (synchronized block):

```java
synchronized (this) {
    sharedCounter++;
}
```

---

## 11. Deadlock (Important)

Occurs when two threads wait forever for each other’s lock.

```java
synchronized(lock1) {
    synchronized(lock2) { }
}
```

Avoid by:

* Consistent lock ordering
* Using `tryLock()`

---

## 12. Inter-Thread Communication

Using:

* `wait()`
* `notify()`
* `notifyAll()`

### Example:

```java
synchronized (obj) {
    obj.wait();    // releases lock
    obj.notify();  // wakes waiting thread
}
```

Used in producer–consumer problems.

---

## 13. Thread Pools (Executor Framework)

Creating too many threads is expensive.
Use thread pools:

```java
ExecutorService service = Executors.newFixedThreadPool(5);

service.submit(() -> System.out.println("Task running"));
service.shutdown();
```

Advantages:

* Better performance
* Reuses threads
* Manages concurrency

---

## 14. Real Example: Multithreading

```java
class PrintTask implements Runnable {
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " -> " + i);
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        Thread t1 = new Thread(new PrintTask(), "Thread-1");
        Thread t2 = new Thread(new PrintTask(), "Thread-2");

        t1.start();
        t2.start();
    }
}
```

Output (interleaved):

```
Thread-1 -> 1
Thread-2 -> 1
Thread-1 -> 2
Thread-2 -> 2
...
```

---

## 15. Common Interview Questions

### Q1. Difference between Thread and Runnable?

Thread is a class; Runnable is an interface.
Runnable preferred because Java doesn’t support multiple inheritance.

### Q2. Difference between start() and run()?

start(): new thread created
run(): executed in same thread

### Q3. What is a daemon thread?

Background service thread.

### Q4. What is thread safety?

Handling shared resources safely.

### Q5. What is race condition?

Multiple threads modifying shared data → incorrect results.

### Q6. What is deadlock?

Threads waiting for each other’s lock forever.

### Q7. What is Executor Framework?

Thread pool mechanism.

---

## Key Takeaways

* Thread = smallest execution unit.

* Use `start()` to launch a thread.

* Use Runnable or lambda for cleaner multithreading.

* Use synchronization to avoid race conditions.

* Daemon threads run in background.

* Use ExecutorService for scalable thread management.

* Understand thread states, scheduling, and deadlocks.

---