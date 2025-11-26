# throw vs throws in Java

## 1. Overview

`throw` and `throws` are used for **exception handling**, but they serve **different purposes**:

* `throw` — **manually throw an exception**
* `throws` — **declare that a method may throw an exception**

They look similar but behave very differently.

---

## 2. throw Keyword

The `throw` keyword is used to **explicitly throw** an exception from your code.

### Syntax:

```java
throw new ExceptionType("message");
```

### Example:

```java
void checkAge(int age) {
    if (age < 18) {
        throw new IllegalArgumentException("Underage");
    }
}
```

### Key Features of throw:

| Feature         | Description                                |
| --------------- | ------------------------------------------ |
| Purpose         | Throw an exception manually                |
| Type            | Throws **one** exception at a time         |
| Used inside     | Method or block                            |
| Object to throw | Must be an object of `Throwable` class     |
| Flow            | Stops execution and jumps to nearest catch |

### Example with try-catch:

```java
try {
    throw new ArithmeticException("Math Error");
} catch (ArithmeticException e) {
    System.out.println(e.getMessage());
}
```

---

## 3. throws Keyword

The `throws` keyword is used in method declaration to **indicate that the method might throw** certain exceptions.

### Syntax:

```java
void method() throws ExceptionType1, ExceptionType2 { }
```

### Example:

```java
void readFile() throws IOException {
    FileReader fr = new FileReader("abc.txt");
}
```

Who must handle the exception?

* Either the **caller handles it** with try-catch
* OR caller must also declare `throws`

---

## 4. throw vs throws 

| Feature              | throw                                           | throws                            |
| -------------------- | ----------------------------------------------- | --------------------------------- |
| Meaning              | Explicitly throw exception                      | Declare potential exception       |
| Place of use         | Inside method                                   | In method signature               |
| Number of exceptions | One at a time                                   | Multiple exceptions allowed       |
| Who handles?         | Handled by nearest catch                        | Caller handles or rethrows        |
| Type                 | Used for **runtime** and **checked** exceptions | Mostly for **checked** exceptions |
| Object needed?       | Yes (new exception object)                      | No object needed                  |
| Execution            | Immediately stops method execution              | Does not stop execution           |

---

## 5. Real Examples

---

### Example 1: Using throw inside a method

```java
void validate(int age) {
    if (age < 18) {
        throw new ArithmeticException("Not eligible");
    }
    System.out.println("Eligible");
}
```

---

### Example 2: Using throws in method declaration

```java
void m1() throws IOException {
    FileReader fr = new FileReader("data.txt");
}
```

Caller must handle:

```java
try {
    m1();
} catch (IOException e) {
    System.out.println("Handled");
}
```

---

### Example 3: throw + throws together

```java
void process() throws Exception {
    throw new Exception("Something went wrong");
}
```

Caller must handle this exception.

---

## 6. throw with Custom Exceptions

```java
class AgeException extends Exception {
    AgeException(String msg) {
        super(msg);
    }
}

void check(int age) throws AgeException {
    if (age < 18) {
        throw new AgeException("Age must be 18+");
    }
}
```

---

## 7. Which One to Use When?

### Use **throw** when:

* You want to manually trigger an exception
* You want custom exception messages
* You want custom exception logic

### Use **throws** when:

* Method may throw checked exceptions
* You want caller to decide how to handle exception
* You want to avoid writing try-catch in the current method

---

## 8. Common Interview Questions

### Q1: Can we use multiple throw statements?

Yes, but only **one can execute at runtime**.

### Q2: Can a constructor use throws?

Yes.

### Q3: Can we throw checked exceptions without declaring throws?

No. Compiler error.

### Q4: Can throws handle exception?

No. It only **declares**, does not handle.

### Q5: Can `throw` be used for unchecked exceptions?

Yes.

---

## Key Takeaways

* `throw` → manually throw an exception.

* `throws` → declare that a method may throw exceptions.

* `throw` is inside method; `throws` is in method header.

* `throw` throws a single exception; `throws` lists multiple possible exceptions.

* `throw` causes immediate execution stop; `throws` passes responsibility to caller.

---

