# ThreadPoolExecutor Framework

The **`ThreadPoolExecutor`** is the **core implementation** behind Java’s thread pool mechanism.

All factory methods in `Executors` (`newFixedThreadPool`, `newCachedThreadPool`, etc.) internally use `ThreadPoolExecutor`.

---

## 1. What Is ThreadPoolExecutor?

`ThreadPoolExecutor` is a **concrete class** in `java.util.concurrent` that:

* Manages a pool of worker threads
* Accepts tasks (`Runnable` / `Callable`)
* Controls thread creation, reuse, and termination
* Uses a queue to hold waiting tasks
* Applies rejection policies when overloaded

It gives **full control** over how concurrency behaves.

---

## 2. Class Hierarchy

```
Executor
   ↓
ExecutorService
   ↓
AbstractExecutorService
   ↓
ThreadPoolExecutor
```

This means:

* It supports task execution
* It supports lifecycle management
* It supports result handling via `Future`

---

## 3. Why ThreadPoolExecutor Exists

The `Executors` utility class provides **convenience methods**, but they:

* Hide important configuration
* Often create **unbounded queues or threads**
* Can lead to **memory leaks and OOM errors**

Example problem:

```java
Executors.newFixedThreadPool(10);
```

Internally uses:

* Unbounded `LinkedBlockingQueue` → tasks can pile up endlessly

**ThreadPoolExecutor** exists to give **explicit control**.

---

## 4. ThreadPoolExecutor Constructor

```java
ThreadPoolExecutor(
    int corePoolSize,
    int maximumPoolSize,
    long keepAliveTime,
    TimeUnit unit,
    BlockingQueue<Runnable> workQueue,
    ThreadFactory threadFactory,
    RejectedExecutionHandler handler
)
```

Every parameter directly affects performance and stability.

---

## 5. Core Parameters Explained

### 5.1 corePoolSize

* Minimum number of threads **kept alive**
* Threads are created **even if idle**
* Tasks are executed immediately if core threads are available

Example:

```java
corePoolSize = 4
```

→ At least 4 threads always exist.

---

### 5.2 maximumPoolSize

* Maximum number of threads allowed
* If number of threads == corePoolSize and queue is full, new threads are created up to this limit

Example:

```java
maximumPoolSize = 8
```

→ Pool can scale from 4 → 8 threads under load.

---

### 5.3 keepAliveTime

* Time an **extra thread** (beyond core) can stay idle
* After timeout, thread is terminated

```java
keepAliveTime = 30 seconds
```

Applies only to threads **above corePoolSize** (unless configured otherwise).

---

### 5.4 TimeUnit

Defines unit for keepAliveTime:

```java
TimeUnit.SECONDS
TimeUnit.MILLISECONDS
```

---

### 5.5 workQueue

Holds tasks waiting for execution.

Common implementations:

| Queue                   | Behavior                         |
| ----------------------- | -------------------------------- |
| `ArrayBlockingQueue`    | Bounded, fixed capacity          |
| `LinkedBlockingQueue`   | Optional bound (often unbounded) |
| `SynchronousQueue`      | No storage, direct handoff       |
| `PriorityBlockingQueue` | Priority-based execution         |

### Queue Choice Effects

* **Large queue** → fewer threads, more waiting
* **Small queue** → more threads, faster execution
* **Unbounded queue** → risk of OOM

---

### 5.6 ThreadFactory

Responsible for creating threads.

Used to:

* Name threads
* Set daemon status
* Set priority
* Attach uncaught exception handlers

Example:

```java
ThreadFactory factory = r -> {
    Thread t = new Thread(r);
    t.setName("worker-thread");
    t.setDaemon(false);
    return t;
};
```

---

### 5.7 RejectedExecutionHandler (Rejection Policy)

Triggered when:

* Queue is full
* Max threads are active

---

## 6. Rejection Policies

---

### 6.1 AbortPolicy (Default)

```java
new ThreadPoolExecutor.AbortPolicy()
```

* Throws `RejectedExecutionException`
* Safest for detecting overload

---

### 6.2 CallerRunsPolicy

```java
new ThreadPoolExecutor.CallerRunsPolicy()
```

* Task runs in the **calling thread**
* Slows down producer
* Acts as backpressure mechanism

---

### 6.3 DiscardPolicy

```java
new ThreadPoolExecutor.DiscardPolicy()
```

* Silently drops task
* Dangerous for critical systems

---

### 6.4 DiscardOldestPolicy

```java
new ThreadPoolExecutor.DiscardOldestPolicy()
```

* Drops oldest queued task
* Enqueues new task

---

## 7. Task Execution Flow

When a task is submitted:

```
1. If active threads < corePoolSize
      → Create new thread

2. Else if queue not full
      → Enqueue task

3. Else if active threads < maximumPoolSize
      → Create new thread

4. Else
      → Reject task (handler invoked)
```

This flow is **core to understanding thread pools**.

---

## 8. Example: Custom ThreadPoolExecutor

```java
ExecutorService executor =
    new ThreadPoolExecutor(
        2,
        4,
        30,
        TimeUnit.SECONDS,
        new ArrayBlockingQueue<>(5),
        Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.AbortPolicy()
    );
```

Meaning:

* Minimum 2 threads
* Maximum 4 threads
* Queue size = 5
* Reject tasks when overloaded

---

## 9. Core vs Max Threads with Queue

Example:

```java
core = 2
max = 4
queue = 3
```

Task arrival:

| Task | Action              |
| ---- | ------------------- |
| 1    | Create thread       |
| 2    | Create thread       |
| 3    | Queue               |
| 4    | Queue               |
| 5    | Queue               |
| 6    | Create extra thread |
| 7    | Create extra thread |
| 8    | Reject              |

---

## 10. allowCoreThreadTimeOut

By default:

* Core threads never die

You can allow core threads to time out:

```java
executor.allowCoreThreadTimeOut(true);
```

Now even core threads respect `keepAliveTime`.

Useful for:

* Bursty workloads
* Resource optimization

---

## 11. Monitoring ThreadPoolExecutor

Important methods:

```java
getPoolSize()
getActiveCount()
getQueue().size()
getCompletedTaskCount()
```

Used in production monitoring.

---

## 12. Shutdown Behavior

### Graceful Shutdown

```java
executor.shutdown();
```

* Stops accepting new tasks
* Finishes existing tasks

---

### Force Shutdown

```java
executor.shutdownNow();
```

* Interrupts running tasks
* Returns queued tasks

---

## 13. Executors vs ThreadPoolExecutor

| Executors      | ThreadPoolExecutor  |
| -------------- | ------------------- |
| Simple         | Advanced            |
| Less control   | Full control        |
| Risky defaults | Explicit limits     |
| Good for demos | Best for production |

---

## 14. Best Practices

* Avoid `Executors.newFixedThreadPool` in production
* Always use bounded queues
* Always define rejection policy
* Monitor pool metrics
* Size pool based on workload
* Shutdown pools properly

---

## 15. Common Production Mistakes

* Unbounded queues → memory leak
* Cached thread pool misuse
* Blocking I/O in small pools
* Ignoring rejected tasks
* Forgetting shutdown

---

## 16. Interview Questions

### Q1. What is ThreadPoolExecutor?

A configurable, production-grade implementation of a thread pool.

---

### Q2. Difference between corePoolSize and maximumPoolSize?

* Core → minimum threads
* Max → upper limit under load

---

### Q3. When does a task get rejected?

When queue is full and max threads are active.

---

### Q4. Why avoid Executors factory methods?

They hide configuration and may cause OOM.

---

### Q5. What is SynchronousQueue used for?

Direct handoff; used in cached thread pools.

---

### Q6. What is backpressure in thread pools?

Slowing task producers using CallerRunsPolicy.

---

## Key Takeaways

* `ThreadPoolExecutor` is the backbone of Java concurrency

* Task execution follows a strict decision flow

* Queue choice is as important as thread count

* Rejection policies define overload behavior

* Executors are wrappers; ThreadPoolExecutor is the real engine

* Correct configuration prevents performance disasters

---
