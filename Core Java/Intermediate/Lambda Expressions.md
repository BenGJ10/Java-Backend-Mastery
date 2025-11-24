# Lambda Expressions in Java

## 1. What Are Lambda Expressions?

A **lambda expression** is a concise way to represent a **function** (a block of code) as an **object**.
Introduced in **Java 8**, lambdas enable **functional programming**, allowing you to pass behavior as data.

They work **only with Functional Interfaces** (interfaces with exactly one abstract method).

---

## 2. Why Use Lambda Expressions?

### Benefits:

1. **Cleaner, shorter code** (no need for anonymous classes)
2. **Functional programming support**
3. **Used extensively in Streams API**
4. **Improves readability**
5. **Ideal for passing small functions**

---

## 3. Basic Syntax

```java
(parameters) -> expression

(parameters) -> { statements }
```

Examples:

```java
() -> System.out.println("Hello")
(x) -> x * x
(a, b) -> a + b
```

---

## 4. Lambda vs Anonymous Class

### Without Lambda (old way):

```java
Runnable r = new Runnable() {
    public void run() {
        System.out.println("Running");
    }
};
```

### With Lambda:

```java
Runnable r = () -> System.out.println("Running");
```

Cleaner and shorter.

---

## 5. Lambda Expression Components

### Example:

```java
(int x, int y) -> x + y
```

* **Parameters:** `(int x, int y)`
* **Arrow token:** `->`
* **Body:** `x + y`

If type can be inferred → omit type:

```java
(x, y) -> x + y
```

If single parameter → parentheses optional:

```java
x -> x * 10
```

---

## 6. Lambda with Functional Interface

### Example Functional Interface:

```java
@FunctionalInterface
interface Operation {
    int compute(int a, int b);
}
```

Usage:

```java
Operation add = (a, b) -> a + b;
System.out.println(add.compute(5, 3)); // 8
```

---

## 7. Types of Lambda Expressions

### 7.1 No Parameter

```java
Runnable r = () -> System.out.println("Hello");
```

### 7.2 Single Parameter

```java
Consumer<String> c = s -> System.out.println(s);
```

### 7.3 Multiple Parameters

```java
BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
```

### 7.4 Multiple Statements

```java
(x, y) -> {
    int z = x + y;
    return z;
}
```

---

## 8. Lambda with Standard Functional Interfaces

Java provides many ready-made functional interfaces in `java.util.function`.

### 8.1 Predicate (takes input → returns boolean)

```java
Predicate<Integer> isEven = x -> x % 2 == 0;
System.out.println(isEven.test(10)); // true
```

---

### 8.2 Function (T → R)

```java
Function<String, Integer> len = s -> s.length();
```

---

### 8.3 Consumer (T → void)

```java
Consumer<String> c = s -> System.out.println(s);
```

---

### 8.4 Supplier (no input → returns value)

```java
Supplier<Double> random = () -> Math.random();
```

---

### 8.5 Comparator

```java
Comparator<Integer> comp = (a, b) -> a - b;
```

---

## 9. Lambda with Collections

### Sorting with Lambda

```java
List<Integer> list = Arrays.asList(5, 2, 8, 1);

list.sort((a, b) -> a - b);
```

---

## 10. Lambda with Streams API

```java
List<Integer> nums = Arrays.asList(1,2,3,4,5);

nums.stream()
    .filter(n -> n % 2 == 0)
    .forEach(n -> System.out.println(n));
```

Output:

```
2
4
```

---

## 11. Capturing Variables in Lambdas

Lambdas can use variables from outside if they are **effectively final**.

```java
int x = 10;
Runnable r = () -> System.out.println(x);
```

You cannot modify `x` after this.

---

## 12. Method Reference (Shortcut for Lambda)

Sometimes lambda just calls a method:

```java
list.forEach(s -> System.out.println(s));
```

Can be replaced with:

```java
list.forEach(System.out::println);
```

---

## 13. Lambda Expression Limitations

* Cannot modify effectively-final local variables
* No checked exceptions unless handled
* Cannot create standalone functions (Java is not purely functional)
* Only works with functional interfaces

---

## 14. Common Interview Questions

### Q1: What is a lambda expression?

A short block of code that implements a functional interface.

### Q2: Can lambda create objects?

Yes, lambdas are objects implementing a functional interface.

### Q3: Why lambdas need functional interfaces?

Because JVM knows they provide only one method to implement.

### Q4: What are effectively final variables?

Variables whose value does not change after assignment.

### Q5: Do lambdas improve performance?

Yes, fewer anonymous class objects → faster invocation.

---

## Key Takeaways

* Lambda expressions implement functional interfaces.

* They simplify anonymous classes.

* Essential for Streams API and modern Java.

* Support cleaner, functional-style programming.

* Variables used must be effectively final.

---

