# Daemon Threads in Java

## 1. What Is a Daemon Thread?

A **daemon thread** is a **background service thread** in Java that runs to support non-daemon (user) threads.
Its purpose is **not** to perform tasks for the application directly, but to provide **auxiliary services**.

### Key Characteristics:

* Runs in the background
* Has **low priority**
* JVM **does not wait** for daemon threads to finish
* When all **user threads** finish, the JVM terminates and daemon threads are killed automatically

In simple terms:

> If the program ends, daemon threads are stopped automatically — they do *not* prevent JVM shutdown.

---

## 2. Examples of Daemon Threads

Java internally uses daemon threads for system-level tasks:

* **Garbage Collector (GC thread)**
* **Finalizer thread**
* **JIT compiler threads**
* **Attach Listener thread**

These are services that exist only as long as the JVM exists.

---

## 3. Daemon Thread vs User Thread

| Feature  | User Thread (Normal Thread) | Daemon Thread                     |
| -------- | --------------------------- | --------------------------------- |
| Purpose  | Main work of the program    | Background tasks                  |
| Priority | Higher/Normal               | Low                               |
| JVM Exit | JVM waits for them          | JVM does *not* wait               |
| Examples | main(), worker threads      | GC, finalizer                     |
| Lifetime | Must finish execution       | Ends automatically when JVM exits |

---

## 4. How to Create a Daemon Thread

You can convert any thread into a daemon thread using:

```java
thread.setDaemon(true);
```

Important:

> You must call `setDaemon(true)` **before** `start()`.

---

### Example:

```java
class MyDaemon extends Thread {
    public void run() {
        while (true) {
            System.out.println("Daemon running...");
            try { Thread.sleep(500); } catch (Exception e) {}
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        MyDaemon d = new MyDaemon();
        d.setDaemon(true); // Must be before start()
        d.start();

        System.out.println("Main thread ends...");
    }
}
```

### Output (may vary):

```
Daemon running...
Main thread ends...
```

Then program ends and the daemon thread is **killed immediately**.

---

## 5. Why JVM Does Not Wait for Daemon Threads?

Because daemon threads are designed for tasks **only useful while the application is alive**.

Examples:

* Garbage collection is useless after program ends
* Log cleaners, monitoring, background syncs — not required after JVM exit

If JVM waited for daemon threads indefinitely → program would never terminate.

---

## 6. Important Rules for Daemon Threads

### Rule 1: Must set daemon before starting the thread

```java
t.setDaemon(true);
t.start();
```

Calling `setDaemon(true)` after `start()` throws:

```
java.lang.IllegalThreadStateException
```

### Rule 2: Inherited Property

If a thread is created from a daemon thread:

> The new thread is also automatically a **daemon thread**.

### Rule 3: Daemon Threads Should Not Handle Critical Logic

Since daemon threads can die *at any moment* when the JVM exits, they should **not** handle:

* File operations
* Database updates
* Important application logic
* Saving user data

Only use them for **non-essential background services**.

---

## 7. Real-World Use Cases

### 1. Background cleanup tasks

Example: closing idle connections

### 2. Monitoring services

Example: thread monitoring, metrics collection

### 3. JVM internal services

Garbage Collector

### 4. Timer scheduling (non-critical tasks)

---

## 8. Daemon Threads in Thread Pools?

**ExecutorService threads are NOT daemon threads by default**, because:

* Server apps should run critical tasks
* Daemon threads might end abruptly, causing inconsistent states

To create daemon threads in a pool:

```java
ExecutorService executor = Executors.newFixedThreadPool(3, runnable -> {
    Thread t = new Thread(runnable);
    t.setDaemon(true);
    return t;
});
```

---

## 9. Example Showing JVM Does Not Wait for Daemon Threads

```java
public class Demo {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for(int i = 1; i <= 100000; i++) {
                System.out.println("Daemon working: " + i);
            }
        });

        t.setDaemon(true);
        t.start();

        System.out.println("Main thread ends");
    }
}
```

### Output:

```
Main thread ends
```

And the program terminates instantly — daemon thread never prints all 100k lines because JVM stops it.

---

## 10. Common Interview Questions

### Q1. What is a daemon thread?

A background thread that performs supportive tasks and does not prevent JVM shutdown.

---

### Q2. When does JVM exit?

When **all user threads** finish execution.
Daemon threads do not keep JVM alive.

---

### Q3. Why are daemon threads low priority?

Because they should only run when CPU is idle.

---

### Q4. What happens if the main thread finishes?

All running daemon threads are terminated immediately.

---

### Q5. Can daemon threads perform important tasks?

No. They can be killed at any time.

---

### Q6. What is the difference between user and daemon threads?

User threads keep JVM running; daemon threads do not.

---

### Q7. How do you make a daemon thread?

```java
thread.setDaemon(true);
```

Before calling `start()`.

---

### Q8. Does a thread inherit daemon status?

Yes. Child thread inherits the daemon nature of its parent.

---

## Key Takeaways

* Daemon threads are **background, service threads**.


* JVM exits when all user threads finish, regardless of daemon threads.

* Daemon threads should be used for **non-critical** tasks.

* Always set daemon before `start()`.

* Many JVM internal threads (like GC) are daemon threads.

---
