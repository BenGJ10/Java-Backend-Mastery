# Singleton Class and Immutable Class in Java

## 1. Overview

Two important design patterns/concepts in Java are:

1. **Singleton Class** → ensures *only one instance* of a class exists.
2. **Immutable Class** → ensures *state cannot change* after creation.

Both patterns improve **memory efficiency**, **thread-safety**, and **program predictability**, but they solve different problems.

---

## 2. Singleton Class in Java

A **Singleton Class** ensures:

* Exactly **one instance** of the class is created.
* A **global access point** to that instance is provided.

---

### 2.1 Requirements for Singleton

To implement a Singleton:

1. **Private constructor**
2. **A static private reference** of the class
3. **A public static method** to return the instance

---

### 2.2 Types of Singleton Implementations

---

### A. Eager Initialization Singleton

Instance created at class loading time.

```java
public class Singleton {
    private static final Singleton instance = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return instance;
    }
}
```

### Pros:

* Simple and thread-safe

### Cons:

* Instance created even when not used

---

### B. Lazy Initialization Singleton

Instance created only when needed.

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
```

### Problem:

* **Not thread-safe**

---

### C. Thread-Safe Singleton (Synchronized Method)

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();
        return instance;
    }
}
```

### Cons:

* Slower due to method-level synchronization

---

### D. Double-Checked Locking Singleton (Efficient & Thread Safe)

```java
public class Singleton {

    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
}
```

### Notes:

* Uses **volatile** to prevent instruction reordering
* Considered best practice for multi-threaded environments

---

### E. Bill Pugh Singleton (Most Recommended)

Uses static inner class.

```java
public class Singleton {

    private Singleton() {}

    private static class Helper {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Helper.INSTANCE;
    }
}
```

### Advantages:

* Lazy
* Thread-safe
* No synchronization overhead

---

### F. Enum Singleton (Best, Simplest, Thread-safe)

```java
public enum Singleton {
    INSTANCE;
}
```

### Advantages:

* Serialization-safe
* Reflection-safe
* JVM guarantees only one instance

---

### 2.3 Singleton Real-World Usage

* Logging (Logger)
* Database connection pool
* Configuration managers
* Caches
* Thread pool managers

---

### 2.4 Problems with Singleton

* Difficult to test (global state)
* Breaks dependency injection if overused
* Can be misused and lead to tight coupling

---

## 3. Immutable Class in Java

An **Immutable Class** is a class whose object **cannot be modified** after creation.

Example of immutable classes:

* `String`
* `Integer`
* `LocalDate`
* `BigDecimal`

---

### 3.1 Rules to Make a Class Immutable

1. **Make the class final**
   → prevents subclassing

2. **Make all fields private and final**

3. **No setters**

4. **Initialize fields via constructor**

5. **Prevent exposing mutable fields directly**

   * Use defensive copying for arrays/collections

---

### 3.2 Example — Immutable Class Implementation

```java
public final class Employee {

    private final String name;
    private final int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }

    public int getAge() { return age; }
}
```

Object state cannot change:

```java
Employee e = new Employee("John", 25);
// e.name = "David"; // ❌ cannot modify
```

---

### 3.3 Immutable Class with Mutable Field (Important!)

Handling a mutable object (e.g., Date, List):

```java
public final class Person {

    private final String name;
    private final Date dob;

    public Person(String name, Date dob) {
        this.name = name;
        this.dob = new Date(dob.getTime()); // defensive copy
    }

    public String getName() { return name; }

    public Date getDob() {
        return new Date(dob.getTime()); // return copy
    }
}
```

---

## 4. Singleton vs Immutable — Comparison

| Feature       | Singleton                       | Immutable                   |
| ------------- | ------------------------------- | --------------------------- |
| Purpose       | Ensure one instance             | Ensure unmodifiable objects |
| Instances     | One only                        | Many allowed                |
| Mutability    | Can be mutable or immutable     | Always immutable            |
| Memory        | Saves memory (single object)    | Safe & thread-safe          |
| Thread Safety | Requires careful implementation | Naturally thread-safe       |
| Example       | Logger, DB connection           | String, Integer             |

---

## 5. Can a Singleton Be Immutable?

Yes.

Example:

```java
public class Config {

    private static final Config INSTANCE = new Config();

    private final String version = "1.0";

    private Config() {}

    public static Config getInstance() {
        return INSTANCE;
    }

    public String getVersion() { return version; }
}
```

Here:

* Only one instance exists (singleton)
* Cannot modify state (immutable)

---

## 6. Interview Questions

---

### Q1: Why do we use Singleton?

To ensure only one instance of a resource (database connection, logger) exists.

---

### Q2: How do you prevent Singleton from breaking via reflection?

Use **Enum Singleton** or throw exception in constructor.

---

### Q3: How do you prevent Singleton from breaking via serialization?

Implement:

```java
protected Object readResolve() {
    return instance;
}
```

Better: use Enum Singleton.

---

### Q4: Why are immutable objects thread-safe?

Because their state cannot change once created → no data races.

---

### Q5: Why is String immutable?

* Security
* Class loader safety
* Thread-safety
* Hashing optimization
* String pool support

---

## Key Takeaways

* **Singleton** ensures only **one instance** exists globally.

* **Immutable class** ensures **object state cannot change**.

* Singleton may or may not be immutable.

* Immutable objects are naturally thread-safe.

* Enum-based singleton is the most robust implementation.

* Defensive copying is essential when immutables contain mutable objects.

---
