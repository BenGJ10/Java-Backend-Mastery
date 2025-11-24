# Functional Interface and Marker Interface

## 1. What is a Functional Interface?

A **Functional Interface** is an interface that contains exactly **one abstract method**.

It may contain:

* One abstract method (mandatory)
* Any number of default methods
* Any number of static methods
* Methods inherited from `Object` (not counted)

Used heavily in **Lambda Expressions** and **Streams API** (Java 8+).

---

## 2. Syntax of a Functional Interface

```java
@FunctionalInterface
interface Operation {
    int compute(int a, int b);   // one abstract method
}
```

Usage with lambda:

```java
Operation add = (a, b) -> a + b;
System.out.println(add.compute(5, 3)); // 8
```

---

## 3. Characteristics of Functional Interfaces

* Must have **exactly one abstract method**
* Marked using `@FunctionalInterface` annotation (optional but recommended)
* Used with lambda expressions and method references
* Can contain default and static methods without breaking the functional rule

---

## 4. Examples of Functional Interfaces (Java Built-in)

| Interface       | Abstract Method | Package              |
| --------------- | --------------- | -------------------- |
| `Runnable`      | `run()`         | java.lang            |
| `Callable`      | `call()`        | java.util.concurrent |
| `Comparator`    | `compare()`     | java.util            |
| `Supplier<T>`   | `get()`         | java.util.function   |
| `Consumer<T>`   | `accept()`      | java.util.function   |
| `Function<T,R>` | `apply()`       | java.util.function   |
| `Predicate<T>`  | `test()`        | java.util.function   |

---

## 5. Example with Default and Static Methods

```java
@FunctionalInterface
interface Printer {
    void print(String msg);   // only abstract method

    default void info() {
        System.out.println("Default method inside functional interface");
    }

    static void help() {
        System.out.println("Static method inside functional interface");
    }
}
```

---

## 6. Why Functional Interfaces?

* Enable functional programming in Java
* Support lambda expressions
* Cleaner and concise code
* Reduce boilerplate code in anonymous classes

---

# Marker Interface

---

## 7. What is a Marker Interface?

A **Marker Interface** is an interface that contains **no methods and no fields**.
It simply *marks* a class to indicate some metadata or special behavior to JVM or frameworks.

Examples:

* `Serializable`
* `Cloneable`
* `RandomAccess`
* `Remote` (RMI)

---

## 8. Purpose of Marker Interface

Marker interfaces provide **metadata** to:

* JVM
* Compiler
* Frameworks

They allow the system to check:

> "Is this object allowed to perform this operation?"

### Example:

```java
class Student implements Serializable { }
```

This tells JVM that objects of `Student` **can be serialized**.

---

## 9. How Marker Interfaces Work

Even though they contain no methods, JVM checks presence using:

```java
if (obj instanceof Serializable) {
    // allow serialization
}
```

This is how marker interfaces guide runtime behavior.

---

## 10. Custom Marker Interface Example

```java
interface MyMarker { }

class Demo implements MyMarker { }

class Test {
    static void check(Object obj) {
        if (obj instanceof MyMarker) {
            System.out.println("Marked class detected!");
        } else {
            System.out.println("Not marked");
        }
    }
}
```

---

## 11. Why Not Use Annotations Instead?

After Java 5, **annotations** replaced many marker interface use cases.

Example:

Instead of:

```java
class Student implements Serializable { }
```

We can use annotations like:

```java
@Deprecated
class Student { }
```

But some legacy frameworks/JDK classes still rely on marker interfaces.

---

# Functional Interface vs Marker Interface

---

### 12. Comparison Table

| Feature          | Functional Interface                     | Marker Interface            |
| ---------------- | ---------------------------------------- | --------------------------- |
| Contains methods | ✔ One abstract method                    | ❌ None                      |
| Purpose          | Supports lambda & functional programming | Marker/metadata for JVM     |
| Introduced       | Java 8                                   | Java 1.0                    |
| Example          | Runnable, Callable, Comparator           | Serializable, Cloneable     |
| Behavior         | Provides functionality                   | Provides ability/permission |
| Annotation       | `@FunctionalInterface`                   | No annotation required      |

---

## 13. Real-World Comparison Example

### Functional Interface Use Case

Thread using Runnable:

```java
Runnable r = () -> System.out.println("Running...");
new Thread(r).start();
```

### Marker Interface Use Case

Serialization:

```java
class User implements Serializable {
    String name;
}
```

JVM checks:

```java
if (obj instanceof Serializable) { ... }
```

---

# Key Takeaways

* **Functional Interface** = one abstract method + supports lambdas.

* **Marker Interface** = no methods + gives metadata/ability to a class.

* Marker interfaces are older; annotations now replace many use-cases.

* Functional interfaces power the entire **Streams API** and modern Java functional programming.

---


