# Exception Methods in Java


## 1. Overview

All exception classes in Java inherit from:

```
java.lang.Throwable
```

So all key exception-related methods come from **Throwable**.
These methods help you understand the error, print diagnostic details, and debug issues.

---

## 2. Important Exception Methods (Throwable Class)

---

### 2.1 `getMessage()`

Returns the **detailed message** of the exception.

### Example:

```java
try {
    int x = 10 / 0;
} catch (Exception e) {
    System.out.println(e.getMessage());
}
```

Output:

```
/ by zero
```

Purpose: User-friendly error descriptions.

---

### 2.2 `toString()`

Returns a **short description** of the exception:

```
<exception class>: <message>
```

### Example:

```java
catch (Exception e) {
    System.out.println(e.toString());
}
```

Output:

```
java.lang.ArithmeticException: / by zero
```

---

### 2.3 `printStackTrace()`

Prints the **complete stack trace** to standard error.
This is the most used debugging method.

### Example:

```java
catch (Exception e) {
    e.printStackTrace();
}
```

Output example:

```
java.lang.ArithmeticException: / by zero
  at Demo.main(Demo.java:5)
```

Shows:

* Exception type
* Description
* Exact line number
* Method call sequence

---

### 2.4 `getStackTrace()`

Returns stack trace elements **as an array of `StackTraceElement`** instead of printing them.

### Example:

```java
catch (Exception e) {
    for (StackTraceElement el : e.getStackTrace()) {
        System.out.println(el);
    }
}
```

Used in logging systems.

---

### 2.5 `initCause(Throwable cause)`

Assigns another exception as the **cause** of the current exception.

Used when wrapping exceptions.

### Example:

```java
IOException e1 = new IOException("Outer");
e1.initCause(new NullPointerException("Cause"));
```

---

### 2.6 `getCause()`

Returns the **root cause** of the exception.

### Example:

```java
try {
    throw new Exception("Main", new RuntimeException("Cause"));
} catch (Exception e) {
    System.out.println(e.getCause());
}
```

Output:

```
java.lang.RuntimeException: Cause
```

---

### 2.7 `fillInStackTrace()`

Updates the stack trace information for the exception.

Used rarely (e.g., custom frameworks).

---

### 2.8 `getLocalizedMessage()`

Returns a localized error message (used for internationalization).
By default, same as `getMessage()`.

---

## 3. Summary Table of Exception Methods

| Method                  | Description                  |
| ----------------------- | ---------------------------- |
| `getMessage()`          | Returns error message        |
| `toString()`            | Returns class + message      |
| `printStackTrace()`     | Prints full stack trace      |
| `getStackTrace()`       | Returns stack trace as array |
| `getCause()`            | Returns root cause           |
| `initCause(Throwable)`  | Sets cause                   |
| `fillInStackTrace()`    | Updates stack trace          |
| `getLocalizedMessage()` | Localized message            |

---

## 4. Example Program Demonstrating All Exception Methods

```java
public class ExceptionMethodsDemo {
    public static void main(String[] args) {
        try {
            int x = 10 / 0;
        } catch (Exception e) {

            System.out.println("getMessage(): " + e.getMessage());
            System.out.println("toString(): " + e.toString());

            System.out.println("printStackTrace(): ");
            e.printStackTrace();

            System.out.println("getStackTrace():");
            for (StackTraceElement el : e.getStackTrace()) {
                System.out.println(el);
            }
        }
    }
}
```

---

## 5. When to Use Which?

| Scenario                       | Use                          |
| ------------------------------ | ---------------------------- |
| Show readable error to user    | `getMessage()`               |
| Log full details for debugging | `printStackTrace()`          |
| Custom logging frameworks      | `getStackTrace()`            |
| Chained exceptions             | `getCause()` / `initCause()` |
| Short debugging info           | `toString()`                 |

---

## Key Takeaways

* All exception methods come from **Throwable**.

* `printStackTrace()` is best for debugging.

* `getMessage()` is best for user-facing messages.

* `getCause()` is essential for chained exceptions.

* Stack trace methods help in logging and debugging frameworks.

---
