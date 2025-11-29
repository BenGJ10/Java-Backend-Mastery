# Checked vs Unchecked Exceptions in Java


## 1. What Are Exceptions?

An **exception** is an event that occurs during program execution and disrupts the normal flow of the program.

All exceptions and errors in Java inherit from the class:

```
java.lang.Throwable
```

Throwable has two major branches:

```
Throwable
 ├── Error        (Serious issues, not handled by programmer)
 └── Exception
       ├── Checked Exceptions
       └── Unchecked Exceptions (Runtime Exceptions)
```

---

## 2. Checked Exceptions

Checked exceptions are **checked at compile time**.
The compiler ensures you **handle them** using:

* `try-catch`
* or `throws` keyword.

If not handled, the program will NOT compile.

### Examples:

* `IOException`
* `SQLException`
* `ClassNotFoundException`
* `FileNotFoundException`
* `InterruptedException`

### Example:

```java
public static void main(String[] args) {
    FileReader fr = new FileReader("data.txt");  // compile-time error if not handled
}
```

Error: *Unhandled exception: FileNotFoundException*

### Correct Handling:

```java
try {
    FileReader fr = new FileReader("data.txt");
} catch (FileNotFoundException e) {
    System.out.println(e.getMessage());
}
```

Or:

```java
void readFile() throws FileNotFoundException {
    FileReader fr = new FileReader("data.txt");
}
```

### When to use checked exceptions?

Use them when the caller is **expected to recover** from the issue (e.g., missing file, database error).

---

## 3. Unchecked Exceptions (Runtime Exceptions)

Unchecked exceptions are **not checked at compile time**.
They occur at **runtime** and usually indicate **programming errors**.

These exceptions inherit from:

```
java.lang.RuntimeException
```

### Examples:

* `NullPointerException`
* `ArrayIndexOutOfBoundsException`
* `ArithmeticException`
* `ClassCastException`
* `NumberFormatException`

### Example:

```java
public static void main(String[] args) {
    int x = 10 / 0; // ArithmeticException at runtime
}
```

Compiler DOES NOT force you to handle this.

---

# #4. Differences Between Checked vs Unchecked Exceptions

| Feature               | Checked Exceptions          | Unchecked Exceptions             |
| --------------------- | --------------------------- | -------------------------------- |
| Compile-time checking | ✔ Yes                       | ❌ No                             |
| Must be handled?      | ✔ Must                      | ❌ Optional                       |
| Subclass of           | Exception                   | RuntimeException                 |
| Common examples       | IOException, SQLException   | NPE, AIOOBE, ArithmeticException |
| Represents            | Expected recoverable issues | Logical programming errors       |
| Handling style        | Required                    | Optional                         |
| Occurs                | External environment issues | Programmer mistakes              |

---

## 5. Exception Hierarchy Diagram

```
Throwable
 ├── Error
 │     ├── OutOfMemoryError
 │     └── StackOverflowError
 └── Exception
        ├── Checked Exceptions
        │     ├── IOException
        │     ├── SQLException
        │     └── FileNotFoundException
        └── Unchecked Exceptions (RuntimeException)
              ├── ArithmeticException
              ├── NullPointerException
              ├── ArrayIndexOutOfBoundsException
              └── ClassCastException
```

---

## 6. Key Examples

### 6.1 Checked Exception Example

```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

### 6.2 Unchecked Exception Example

```java
String s = null;
System.out.println(s.length()); // NullPointerException
```

---

## 7. Why Checked Exceptions Exist?

* They **force the developer** to handle predictable failures
* Useful for operations involving:

  * I/O
  * Database
  * Network
  * Resources

---

## 8. Why Unchecked Exceptions Exist?

* To indicate **bugs** in code
* Developer is responsible for fixing the logic
* Handling them usually hides problems instead of solving them

---

## 9. Should You Use Checked or Unchecked?

### Use **checked exceptions** when:

* Caller can fix the problem
* External resource failures
* Example: File not found → user can give another file

### Use **unchecked exceptions** when:

* It’s a programming mistake
* Example: Division by zero, accessing null, wrong index

---

## 10. Interview Questions

### Q1: Why Java has checked exceptions?

To enforce error handling for recoverable conditions.

---

### Q2: Why RuntimeException is unchecked?

Because they usually indicate bugs and should not be forced to handle.

---

### Q3: Is NullPointerException checked or unchecked?

**Unchecked**.

---

### Q4: Can we create custom checked & unchecked exceptions?

Yes.

Custom checked:

```java
class MyCheckedException extends Exception {}
```

Custom unchecked:

```java
class MyUncheckedException extends RuntimeException {}
```

---

## Key Takeaways

* **Checked exceptions** → must be handled (compile-time enforcement).

* **Unchecked exceptions** → occur at runtime; no compile-time checks.

* Both extend `Throwable`, but unchecked extend `RuntimeException`.

* Checked = recoverable; Unchecked = programming errors.

---

