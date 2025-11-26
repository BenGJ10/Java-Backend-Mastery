# Exception Handling in Java

## 1. What Is Exception Handling?

Exception handling is a mechanism to handle **runtime errors** gracefully without stopping the program abruptly.

Java provides:

* `try`
* `catch`
* `finally`
* `throw`
* `throws`
* `try-with-resources`

---

## 2. Basic try-catch

A **try block** contains code that may cause an exception.
A **catch block** handles that exception.

### Syntax:

```java
try {
    // risky code
} catch (ExceptionType e) {
    // handling code
}
```

### Example:

```java
try {
    int x = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
```

---

## 3. How try-catch Works

1. Code in try block runs
2. If exception occurs:

   * Remaining try block is skipped
   * Control jumps to matching catch block
3. If no exception occurs:

   * Catch block is skipped

---

## 4. Multiple Catch Blocks

You can write multiple catch blocks to handle different exceptions separately.

### Example:

```java
try {
    int[] arr = new int[3];
    arr[5] = 10;    // ArrayIndexOutOfBoundsException
} catch (ArithmeticException e) {
    System.out.println("Arithmetic error");
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array index error");
} catch (Exception e) {
    System.out.println("General exception");
}
```

---

## 5. Why Order of catch Blocks Matters

Catch blocks must be ordered from **most specific to most general**.

This is wrong:

```java
catch (Exception e) { }
catch (ArithmeticException e) { } // Error
```

Because `ArithmeticException` is a subclass of `Exception`, and becomes unreachable.

Correct:

```java
catch (ArithmeticException e) { }
catch (Exception e) { }
```

---

## 6. Multi-Catch Block (Java 7+)

Use one catch block to handle **multiple exceptions**.

### Syntax:

```java
try {
    // risky code
} catch (IOException | SQLException e) {
    e.printStackTrace();
}
```

### Rules:

* Exceptions must not be parent-child
  (e.g., `Exception | RuntimeException` is invalid)
* Exception variable is **final** automatically

---

## 7. finally Block (Always Executes)

Used to release resources or perform cleanup.

Executed:

* Whether exception occurs or not
* Whether exception is caught or not
* Even if return statement is used

### Example:

```java
try {
    System.out.println("Try");
} catch (Exception e) {
    System.out.println("Catch");
} finally {
    System.out.println("Finally");
}
```

---

## 8. try-with-resources (Java 7+)

Used to automatically close resources such as:

* FileInputStream
* BufferedReader
* Connection
* Statement

### Syntax:

```java
try (ResourceType r = new ResourceType()) {
    // use resource
} catch (Exception e) {
    // handle
}
```

Resource must implement **AutoCloseable**.

### Example:

```java
try (FileReader fr = new FileReader("data.txt")) {
    int data = fr.read();
    System.out.println(data);
} catch (IOException e) {
    System.out.println("IOException handled");
}
```

No need for `finally` to close `fr`.

---

## 9. Nested try-catch

A try block inside another try block.

```java
try {
    try {
        int a = 5 / 0;
    } catch (ArithmeticException e) {
        System.out.println("Inner catch");
    }
} catch (Exception e) {
    System.out.println("Outer catch");
}
```

---

## 10. Exception Hierarchy (Important for multiple catches)

```
Throwable
   ├── Error
   └── Exception
         ├── RuntimeException
         │       ├── ArithmeticException
         │       ├── NullPointerException
         │       └── IndexOutOfBoundsException
         └── IOException
```

This hierarchy helps you order catch blocks correctly.

---

## 11. Common Example Combining Everything

```java
try (Scanner sc = new Scanner(new File("data.txt"))) {
    int x = 10 / 0;   // ArithmeticException
} 
catch (FileNotFoundException e) {
    System.out.println("File not found");
}
catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
catch (Exception e) {
    System.out.println("General exception");
}
finally {
    System.out.println("Program ended");
}
```

---

## 12. Common Interview Questions

### Q1: Can we have try without catch?

Yes, with **finally** or **try-with-resources**.

```java
try { } finally { }
```

### Q2: Can we have catch without try?

No.

### Q3: Does finally always execute?

Yes, except when:

* JVM crashes
* System.exit() is called

### Q4: Why use multi-catch?

Cleaner code and avoids repetition.

### Q5: What happens if exception is not handled?

Program terminates with stack trace.

---

## Key Takeaways

* Use `try` for risky code.

* Use `catch` to handle specific exceptions.

* Use multiple catch blocks for different exception types.

* Use multi-catch (`|`) to combine handling.

* Use `finally` for cleanup.

* Use **try-with-resources** for automatic resource management.

* Always order catch blocks from **specific to general**.

---

