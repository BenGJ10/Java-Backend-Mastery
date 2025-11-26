# Different Types of Errors in Java

In Java, errors represent problems that stop normal execution.
They can occur at **compile-time** or **runtime**, depending on the cause.

Java categorizes errors into three major types:

1. **Compile-Time Errors**

2. **Runtime Errors (Exceptions)**

3. **Errors (Serious system-level failures)**

---

## 1. Compile-Time Errors (Syntax Errors)

These errors are detected **by the compiler** before the program runs.

### Causes:

* Syntax mistakes
* Missing semicolon
* Wrong keywords
* Type mismatch
* Undefined variables
* Improper method signatures

### Example:

```java
int x = 10
System.out.println(x);
```

Error:

```
';' expected
```

---

## 2. Runtime Errors (Exceptions)

These occur **during program execution**.
They are represented by classes derived from:

```
java.lang.Exception
```

### Common runtime errors:

#### 2.1 Arithmetic Error

```java
int x = 10 / 0;  // ArithmeticException
```

#### 2.2 Null Pointer Error

```java
String s = null;
s.length();  // NullPointerException
```

#### 2.3 Array Index Error

```java
int[] arr = new int[3];
arr[5] = 10; // ArrayIndexOutOfBoundsException
```

#### 2.4 Input Mismatch

```java
Scanner sc = new Scanner(System.in);
int x = sc.nextInt(); // if user enters a string → InputMismatchException
```

#### 2.5 Number Format Error

```java
int x = Integer.parseInt("abc"); // NumberFormatException
```

#### 2.6 File Not Found

```java
new FileInputStream("abc.txt"); // FileNotFoundException
```

### Runtime errors (exceptions) must be handled using **try-catch**, `throws`, or allowed to propagate.

---

## 3. Errors (Serious System-Level Failures)

These represent **serious problems** beyond the control of the application.
They extend:

```
java.lang.Error
```

These **cannot be handled** using try-catch normally, and the program usually terminates.

### Common types:

#### 3.1 OutOfMemoryError

Occurs when JVM runs out of heap space.

```java
int[] arr = new int[1000000000];
```

#### 3.2 StackOverflowError

Infinite recursion.

```java
void test() {
    test(); // infinite recursion
}
```

#### 3.3 NoClassDefFoundError

Class required at runtime not found.

#### 3.4 AssertionError

Thrown when an assertion fails.

---

## Summary Table

| Category                        | Detected At  | Meaning                     | Examples                         | Can Handle?               |
| ------------------------------- | ------------ | --------------------------- | -------------------------------- | ------------------------- |
| **Compile-Time Errors**         | Compile time | Syntax errors               | Missing semicolon, wrong type    | Yes (fix code)            |
| **Runtime Errors (Exceptions)** | Runtime      | Violations during execution | NPE, AIOOBE, ArithmeticException | Yes (try-catch)           |
| **Errors (java.lang.Error)**    | Runtime      | Serious system issues       | OOM, StackOverflowError          | No (mostly unrecoverable) |

---

## 4. Hierarchy Diagram

```
Throwable
   ├── Exception  (Recoverable)
   │       ├── RuntimeException (Unchecked)
   │       └── IOException, SQLException (Checked)
   └── Error       (Unrecoverable)
           ├── OutOfMemoryError
           └── StackOverflowError
```

---

## 5. Checked vs Unchecked vs Errors (Important)

| Type                     | Parent Class               | When Occurs       | Examples                  | Need to Handle? |
| ------------------------ | -------------------------- | ----------------- | ------------------------- | --------------- |
| **Checked Exceptions**   | java.lang.Exception        | Compile-time      | IOException, SQLException | Yes             |
| **Unchecked Exceptions** | java.lang.RuntimeException | Runtime           | NPE, AIOOBE               | Optional        |
| **Errors**               | java.lang.Error            | Runtime (serious) | OOM, SOF                  | No              |

---

## 6. Real-World Examples

### Syntax Error (compile-time):

```java
System.out.println("Hello" // missing parenthesis
```

### NullPointerException (runtime):

```java
String s = null;
System.out.println(s.length());
```

### OutOfMemoryError:

```java
List<int[]> list = new ArrayList<>();
while (true) list.add(new int[1000000]);
```

---

## Key Takeaways

* **Compile-time errors** occur before execution — fix syntax.

* **Runtime errors (exceptions)** occur during execution — handle using try-catch.

* **Errors** represent serious JVM issues — usually cannot be handled.

* Java clearly separates **Exceptions** (recoverable) and **Errors** (non-recoverable).

---
