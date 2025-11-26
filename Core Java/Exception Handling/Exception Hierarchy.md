# Exceptions & Exception Hierarchy in Java

## 1. What Is an Exception?

An **exception** is an event that disrupts normal program execution.
Java handles exceptions using:

* `try`
* `catch`
* `finally`
* `throw`
* `throws`

Exceptions are objects that inherit from the **Throwable** class.

---

## 2. The Throwable Class

`Throwable` is the **root** of Java's exception hierarchy.

Two main subclasses:

1. **Exception** – recoverable issues
2. **Error** – serious system-level problems (not recoverable)

```
Throwable
   ├── Exception
   └── Error
```

---

## 3. Exception vs Error

| Aspect                  | Exception                                         | Error                                |
| ----------------------- | ------------------------------------------------- | ------------------------------------ |
| Meaning                 | Problems caused by program or external conditions | Serious issues in JVM/system         |
| Recoverable?            | Yes, usually                                      | No                                   |
| Handled with try-catch? | Yes                                               | Not recommended                      |
| Examples                | NullPointerException, IOException                 | OutOfMemoryError, StackOverflowError |

---

## 4. Exception Types

Exceptions are mainly categorized into:

1. **Checked Exceptions**
2. **Unchecked Exceptions**
3. **Errors**

---

## 5. Checked Exceptions (Compile-Time)

Checked exceptions must be **handled or declared** using `throws`.

Examples:

### 5.1 IOException

Occurs during file operations.

```java
FileReader fr = new FileReader("abc.txt");
```

### 5.2 SQLException

Database access problems.

### 5.3 ClassNotFoundException

Class not found during runtime loading.

### 5.4 FileNotFoundException

File missing.

### 5.5 InterruptedException

Thread interruptions.

### 5.6 ParseException

Error while parsing formatted text.

Checked exceptions represent external failures that the program should handle gracefully.

---

## 6. Unchecked Exceptions (Runtime – Not Checked at Compile Time)

Subclass of `RuntimeException`.
Do **not** need to be declared or handled.

Examples:

### 6.1 NullPointerException (NPE)

```java
String s = null;
s.length();
```

### 6.2 ArithmeticException

```java
int x = 10 / 0;
```

### 6.3 ArrayIndexOutOfBoundsException

```java
int[] arr = new int[5];
arr[10] = 2;
```

### 6.4 NumberFormatException

```java
int x = Integer.parseInt("abc");
```

### 6.5 ClassCastException

```java
Object o = new Integer(5);
String s = (String) o;
```

### 6.6 IllegalArgumentException

Bad input to method.

### 6.7 IllegalStateException

Method called at wrong time.

Unchecked exceptions usually represent **programming errors**.

---

## 7. Error (Unrecoverable)

Errors occur in the JVM or system.
You should NOT handle them with try-catch.

Examples:

### 7.1 OutOfMemoryError

```java
new int[1000000000];
```

### 7.2 StackOverflowError

```java
void test() { test(); }
```

### 7.3 InternalError

JVM internal issue.

### 7.4 NoClassDefFoundError

Class missing at runtime.

### 7.5 AssertionError

Assertion failure.

Errors indicate **serious problems** the application cannot fix.

---

## 8. Complete Exception Hierarchy (Diagram)

```
Throwable
│
├── Exception (Recoverable)
│     ├── RuntimeException (Unchecked)
│     │      ├── NullPointerException
│     │      ├── ArithmeticException
│     │      ├── ArrayIndexOutOfBoundsException
│     │      ├── ClassCastException
│     │      ├── NumberFormatException
│     │      └── IllegalArgumentException
│     │
│     ├── IOException (Checked)
│     │      ├── FileNotFoundException
│     │      └── EOFException
│     │
│     ├── SQLException (Checked)
│     ├── ClassNotFoundException (Checked)
│     ├── InterruptedException (Checked)
│     └── InvocationTargetException (Checked)
│
└── Error (Not Recoverable)
       ├── OutOfMemoryError
       ├── StackOverflowError
       ├── InternalError
       ├── AssertionError
       └── NoClassDefFoundError
```

---

## 9. Most Common Exceptions Explained

### **NullPointerException**

Accessing a method or property on a null reference.

### **ArithmeticException**

Division by zero or illegal arithmetic operation.

### **ArrayIndexOutOfBoundsException**

Index outside array boundary.

### **ClassCastException**

Invalid type casting.

### **NumberFormatException**

String → number conversion fails.

### **IllegalArgumentException**

Invalid argument to a method.

### **IOException**

I/O failures → external resource issues.

---

## 10. Key Differences Summary Table

| Category                     | Checked Exception | Unchecked Exception  | Error            |
| ---------------------------- | ----------------- | -------------------- | ---------------- |
| Parent                       | Exception         | RuntimeException     | Error            |
| Checked at compile time?     | Yes               | No                   | No               |
| Must handle using try/throws | Yes               | No                   | No               |
| Recoverable?                 | Usually           | Sometimes            | No               |
| Example                      | IOException       | NullPointerException | OutOfMemoryError |

---

## Key Takeaways

* **Throwable** is the root of all exceptions and errors.

* **Exception** = problems you should handle.

* **RuntimeException** = programming mistakes.

* **Error** = serious system failures → not handled.

* Checked exceptions → must be handled.

* Unchecked exceptions → runtime failures.

---

