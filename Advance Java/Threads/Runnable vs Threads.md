# Runnable vs Thread in Java

## 1. Overview

In Java, multithreading can be implemented in two primary ways:

1. **Extending the `Thread` class**
2. **Implementing the `Runnable` interface**

Both approaches allow you to define the logic that runs in a separate thread, but they differ in design, flexibility, and best practices.

---

## 2. What Is Thread?

`Thread` is a **class** in Java (java.lang.Thread) that represents a thread of execution.
A thread starts when you call the `start()` method on a `Thread` object.

When you extend the `Thread` class, you override the `run()` method to define the thread’s task.

### Example: Extending Thread

```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread running: " + Thread.currentThread().getName());
    }
}

public class Demo {
    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start();
    }
}
```

---

## 3. What Is Runnable?

`Runnable` is a **functional interface** (java.lang.Runnable) containing a single method:

```java
public void run();
```

You pass a `Runnable` implementation to a `Thread` object.

### Example: Implementing Runnable

```java
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Running task: " + Thread.currentThread().getName());
    }
}

public class Demo {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyTask());
        t1.start();
    }
}
```

---

## 4. Runnable vs Thread

| Feature               | Runnable                                  | Thread                                          |
| --------------------- | ----------------------------------------- | ----------------------------------------------- |
| Type                  | Interface                                 | Class                                           |
| Recommended?          | ✔ Yes                                     | ❌ Only when needed                              |
| Multiple inheritance  | Supported (can implement many interfaces) | Not supported (class can extend only one class) |
| Thread creation       | Passed to Thread object                   | Direct subclass is itself a Thread              |
| Reusability           | Higher (task separated from thread)       | Lower                                           |
| Suitable for          | Large, scalable applications              | Simple or custom thread types                   |
| Design principle      | Promotes separation of task & thread      | Mixes task with thread behavior                 |
| Functional interface  | Yes (works with Lambdas)                  | No                                              |
| Used in thread pools? | ✔ Yes (ExecutorService)                   | ❌ No                                            |

---

## 5. Why Runnable Is Preferred?

### Reason 1: Java does not support multiple inheritance

If you extend Thread:

```java
class MyTask extends Thread { }
```

You **cannot extend any other class**.
But if you use Runnable:

```java
class MyTask implements Runnable { }
```

You can still extend another class.

---

### Reason 2: Better separation of concerns

Runnable → represents the **task**
Thread → represents the **worker**

This aligns with good object-oriented design.

---

### Reason 3: Works with Executor Framework (Thread Pools)

Only Runnable (and Callable) can be submitted to thread pools:

```java
ExecutorService service = Executors.newFixedThreadPool(3);
service.submit(new MyTask());
```

Classes extending Thread **cannot** be reused this way.

---

### Reason 4: More scalable & reusable

Runnable can be executed by:

* Multiple threads
* Thread pools
* Timer tasks

Thread subclass instances cannot be reused after finishing.

---

## 6. Thread and Runnable Together

You can combine both:

```java
Runnable task = () -> System.out.println("Task");
Thread t = new Thread(task);
t.start();
```

This is the recommended approach in modern Java.

---

## 7. Example Showing Why Runnable Is Better

### Using Thread:

```java
class MyTask extends Thread {
    @Override
    public void run() {
        System.out.println("Task by: " + getName());
    }
}
```

### Using Runnable:

```java
class MyTask implements Runnable {
    @Override
    public void run() {
        System.out.println("Task by: " + Thread.currentThread().getName());
    }
}
```

Runnable keeps the logic independent from thread creation.

---

## 8. Example: Multiple Threads Using Same Runnable

```java
Runnable task = () -> System.out.println("Task executed by " + Thread.currentThread().getName());

Thread t1 = new Thread(task);
Thread t2 = new Thread(task);

t1.start();
t2.start();
```

Same Runnable → multiple threads execute it.

You **cannot** do this with Thread subclass objects.

---

## 9. Performance Difference?

There is **no runtime performance difference**.
Both use the same underlying JVM threading model.

Difference is purely **design & flexibility**.

---

## 10. Summary Table

| Aspect               | Runnable               | Thread                |
| -------------------- | ---------------------- | --------------------- |
| Type                 | Interface              | Class                 |
| run() method         | Defined in Runnable    | Inherited from Thread |
| Thread start         | Need Thread object     | Direct start()        |
| Multiple inheritance | Possible               | Not possible          |
| Reusability          | High                   | Low                   |
| Suitable for         | Large or scalable apps | Small/simple apps     |
| Works with Executor  | Yes                    | No                    |
| Recommended?         | ✔ Yes                  | ❌ No (unless needed)  |

---

# Key Takeaways

* **Use Runnable** for most cases.

* **Use Thread** only when you need to override additional thread behavior (rare).

* Runnable promotes clean design and works well with thread pools.

* Both define tasks for threads, but Runnable is more flexible and scalable.

---
