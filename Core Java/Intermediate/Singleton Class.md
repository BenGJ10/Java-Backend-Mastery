# Singleton Class in Java

## 1. What is a Singleton?

A **singleton** is a class that can have **only one instance (object) at a time**.

After creating an instance, if you try to instantiate the singleton class again, the new variable also points to the **first instance**.

### Example Use Cases:

* Database connections
* Logger classes
* Configuration managers
* Thread pools
* Cache managers

### Key Characteristics:

* Private constructor (prevents external instantiation)
* Private static instance of the class
* Public static method to get the instance

---

## 2. Why Use Singleton?

| Benefit | Explanation |
|---------|-------------|
| **Controlled Access** | Only one instance exists throughout the application |
| **Saves Memory** | No multiple instances created unnecessarily |
| **Thread Safety** | Can control access in multi-threaded environments |
| **Global Access Point** | Access instance from anywhere in the application |
| **Lazy Initialization** | Instance created only when needed |

---

## 3. Singleton Method 1: Eager Initialization

The instance is **created at class loading time**.

```java
class DatabaseConnection {
    // Static instance created immediately
    private static DatabaseConnection instance = new DatabaseConnection();

    // Private constructor
    private DatabaseConnection() {
    }

    // Public method to get instance
    public static DatabaseConnection getInstance() {
        return instance;
    }
}
```

### Usage:

```java
DatabaseConnection db1 = DatabaseConnection.getInstance();
DatabaseConnection db2 = DatabaseConnection.getInstance();

System.out.println(db1 == db2); // true
```

### Advantages:

* Simple and straightforward
* Thread-safe (JVM handles synchronization during class loading)

### Disadvantages:

* Instance created even if never used
* Wastes memory if instance not needed

---

## 4. Singleton Method 2: Lazy Initialization (Thread-Safe)

The instance is **created only when first requested** using synchronized method.

```java
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
    }

    // Synchronized method - thread-safe but slower
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
```

### Advantages:

* Instance created only when needed
* Thread-safe

### Disadvantages:

* Synchronized method is slower (locks entire method)
* Performance overhead on every call

---

## 5. Singleton Method 3: Double-Checked Locking

Optimized version that **minimizes synchronization overhead**.

```java
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        // First check (no lock)
        if (instance == null) {
            // Synchronized block (only if null)
            synchronized (DatabaseConnection.class) {
                // Second check (with lock)
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}
```

### How It Works:

1. First `if` check without lock → fast path
2. Only if null, enter synchronized block
3. Second `if` check inside block → ensures safety
4. After initialization, subsequent calls bypass lock

### Advantages:

* Lazy initialization
* Thread-safe
* Better performance than fully synchronized method

### Disadvantages:

* Requires `volatile` keyword for complete safety (Java 5+)

### Corrected Implementation (with volatile):

```java
class DatabaseConnection {
    private static volatile DatabaseConnection instance;

    private DatabaseConnection() {
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
}
```

---

## 6. Singleton Method 4: Bill Pugh Singleton (Recommended)

Uses **static inner helper class** to ensure thread-safety without synchronization.

```java
class DatabaseConnection {
    private DatabaseConnection() {
    }

    // Static inner class
    private static class SingletonHelper {
        private static final DatabaseConnection instance = 
            new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return SingletonHelper.instance;
    }
}
```

### How It Works:

1. `SingletonHelper` class is not loaded until `getInstance()` is called
2. JVM ensures thread-safe class loading (no synchronization needed)
3. Instance created only when first accessed

### Advantages:

* **Most recommended approach**
* Lazy initialization
* Thread-safe without synchronization overhead
* Clean and efficient

### Disadvantages:

* Slightly more complex code structure

---

## 7. Singleton Method 5: Enum Singleton

Using **enum** to create singleton (Java's approach).

```java
enum DatabaseConnection {
    INSTANCE;
    
    private String connectionString = "jdbc:mysql://localhost/db";

    public void connect() {
        System.out.println("Connecting to: " + connectionString);
    }
}
```

### Usage:

```java
DatabaseConnection.INSTANCE.connect();
```

### Advantages:

* **Safest approach** against reflection and serialization attacks
* Concise syntax
* Guaranteed single instance
* Thread-safe by default

### Disadvantages:

* Cannot extend another class (enum already extends Enum)
* May seem unfamiliar to some developers

---

## 8. Breaking Singleton Pattern

### Using Reflection:

```java
Constructor<DatabaseConnection> constructor = 
    DatabaseConnection.class.getDeclaredConstructor();
constructor.setAccessible(true);
DatabaseConnection obj = constructor.newInstance();
```

This creates a **new instance**, breaking the singleton pattern!

### Protection Against Reflection:

```java
private DatabaseConnection() {
    if (instance != null) {
        throw new RuntimeException("Cannot create another instance");
    }
}
```

### Using Cloning:

```java
DatabaseConnection clone = (DatabaseConnection) instance.clone();
```

This also breaks singleton!

### Protection Against Cloning:

```java
@Override
public Object clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
}
```

---

## 9. Comparison of Singleton Methods

| Method | Thread-Safe | Lazy Init | Performance | Reflection-Safe |
|--------|:-----------:|:---------:|:-----------:|:---------------:|
| Eager | ✓ | ✗ | Good | ✗ |
| Synchronized | ✓ | ✓ | Poor | ✗ |
| Double-Checked | ✓ | ✓ | Good | ✗ |
| Bill Pugh | ✓ | ✓ | Excellent | ✗ |
| Enum | ✓ | ✓ | Excellent | ✓ |

---

## 10. When to Use Singleton

**Use Singleton for:**

* Logger instances
* Database connection pools
* Configuration managers
* Thread pools
* Caches
* Resource managers

**Avoid Singleton for:**

* Objects that frequently change state
* Objects needed in multiple different configurations
* Testing scenarios (hard to mock)


---

## 11. Common Interview Questions

### Q1: What is a singleton class?

A class that can have only one instance at a time.

### Q2: How do you implement a singleton in Java?

Use private constructor, private static instance, and public static method to get the instance.

### Q3: What are the different ways to implement singleton?

Eager initialization, lazy initialization (synchronized), double-checked locking, Bill Pugh singleton, enum singleton.

### Q4: Which singleton implementation is best?

Bill Pugh singleton and enum singleton are generally recommended for their thread-safety and performance.

### Q5: How can you break a singleton pattern?

Using reflection or cloning can create multiple instances, breaking the singleton pattern.

--- 

## Key Takeaways

* Singleton pattern ensures only one instance of a class exists.

* Multiple implementation methods with different trade-offs.

* Bill Pugh singleton and enum singleton are the most robust approaches.

* Be aware of ways to break singleton and how to protect against them.

---