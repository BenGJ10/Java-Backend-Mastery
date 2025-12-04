# Method Reference in Java


## 1. Overview

**Method Reference** (introduced in Java 8) provides a **shorter and cleaner** way to write lambda expressions when an existing method can be reused.

Instead of writing:

```java
list.forEach(x -> System.out.println(x));
```

We can write:

```java
list.forEach(System.out::println);
```

It improves readability and avoids unnecessary lambda boilerplate.

Method reference works only when the lambda **matches an existing method signature**.

---

## 2. Types of Method References

Java provides **four** types of method references:

| Type                                                                        | Syntax                      | Meaning                                               |
| --------------------------------------------------------------------------- | --------------------------- | ----------------------------------------------------- |
| 1. Reference to a static method                                             | `ClassName::staticMethod`   | Calls a static method                                 |
| 2. Reference to an instance method of a particular object                   | `obj::instanceMethod`       | Calls a method on a specific object                   |
| 3. Reference to an instance method of an arbitrary object (of a given type) | `ClassName::instanceMethod` | Calls an instance method on each object in the stream |
| 4. Reference to a constructor                                               | `ClassName::new`            | Calls a constructor to create objects                 |

Let's study each in detail.

---

## 3. Reference to a Static Method

Syntax:

```java
ClassName::staticMethod
```

### Example:

```java
public class Utils {
    public static void printSquare(int x) {
        System.out.println(x * x);
    }
}

List<Integer> list = List.of(1, 2, 3);

list.forEach(Utils::printSquare);
```

Equivalent lambda:

```java
list.forEach(x -> Utils.printSquare(x));
```

---

## 4. Reference to an Instance Method of a Particular Object

Syntax:

```java
object::instanceMethod
```

### Example:

```java
public class Printer {
    public void print(String msg) {
        System.out.println(msg);
    }
}

Printer p = new Printer();

List<String> names = List.of("Java", "Spring", "SQL");

names.forEach(p::print);
```

Equivalent lambda:

```java
names.forEach(s -> p.print(s));
```

---

## 5. Reference to an Instance Method of an Arbitrary Object of a Type

Syntax:

```java
ClassName::instanceMethod
```

Used when the lambda uses the parameter as the calling object.

### Example:

```java
List<String> names = List.of("java", "spring", "docker");

names.stream()
     .map(String::toUpperCase)
     .forEach(System.out::println);
```

Equivalent lambda:

```java
names.stream()
     .map(s -> s.toUpperCase())
```

Another example:

```java
list.stream().map(String::length);
```

Equivalent lambda:

```java
list.stream().map(s -> s.length());
```

---

## 6. Reference to a Constructor

Syntax:

```java
ClassName::new
```

Used to create new objects.

### Example 1: No-arg Constructor

```java
Supplier<Employee> supplier = Employee::new;
Employee e = supplier.get();
```

Equivalent lambda:

```java
Supplier<Employee> supplier = () -> new Employee();
```

---

### Example 2: Constructor with Arguments

```java
Function<String, User> function = User::new;
User u = function.apply("Ben");
```

Equivalent lambda:

```java
Function<String, User> function = name -> new User(name);
```

---

## 7. Method Reference vs Lambda Expression

| Lambda Expression               | Method Reference               |
| ------------------------------- | ------------------------------ |
| Can have logic                  | Only refers to existing method |
| More flexible                   | More concise                   |
| Slightly slower                 | Faster (direct call)           |
| Preferred when logic is complex | Preferred when logic is simple |

---

## 8. Real-World Examples

### 8.1 Sorting with Comparator

```java
list.sort(String::compareToIgnoreCase);
```

Instead of:

```java
list.sort((a, b) -> a.compareToIgnoreCase(b));
```

### 8.2 Converting numbers to Strings

```java
Stream.of(1, 2, 3)
      .map(String::valueOf)
      .forEach(System.out::println);
```

### 8.3 Creating object list using constructor reference

```java
List<String> names = List.of("A", "B", "C");

List<Employee> employees =
    names.stream()
         .map(Employee::new)
         .collect(Collectors.toList());
```

Equivalent lambda:

```java
.map(name -> new Employee(name))
```

---

## 9. When Should You Use Method References?

Use them when:

* lambda simply calls an existing method
* code becomes cleaner and more readable
* no additional logic is needed in the lambda

Avoid when:

* lambda contains logic
* readability decreases
* method reference becomes ambiguous

---

## 10. Interview Questions

### Q1: What are method references?

A shorthand syntax for calling existing methods using `::` operator.

### Q2: How many types of method references exist?

Four:

1. static method
2. instance method of object
3. instance method of class
4. constructor

### Q3: Difference between `String::length` and `obj::method`?

* `String::length` → method of arbitrary object (stream element)
* `obj::method` → method of a specific object

### Q4: Can method references replace all lambda expressions?

No. Only when lambda body calls an existing method directly.

### Q5: Which functional interface fits constructor references?

* `Supplier<T>` → no-arg constructor
* `Function<A,T>` → one-arg constructor
* `BiFunction<A,B,T>` → two-arg constructor

---

## Key Takeaways

* Method references are a concise form of lambda expressions.

* They use the `::` operator.

* Four types: static, specific instance, arbitrary instance, constructor.

* Useful for clean, readable, functional-style code.

* Cannot contain custom logic—only method calls.

---
