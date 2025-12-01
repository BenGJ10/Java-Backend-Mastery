# Generics vs Wildcards in Java

## 1. Overview

Both **generics** and **wildcards** help achieve type safety and flexibility in Java.
However, they serve **different purposes**.

* **Generics** define type parameters in classes, interfaces, and methods.
* **Wildcards** (`?`, `? extends`, `? super`) add **flexibility** when *using* generic classes/methods.

Understanding the difference between them is essential for writing clean, robust, and reusable code.

---

## 2. What Are Generics?

Generics allow you to define **type parameters** for:

* Classes
* Interfaces
* Methods

### Example: Generic Class

```java
class Box<T> {
    T value;
}
```

Usage:

```java
Box<String> b = new Box<>();
```

Here, **T** is a placeholder for *any actual type*.

---

## 3. What Are Wildcards?

Wildcards are used **when consuming or working with generic types**, especially with collections.

They allow more flexible parameterization without specifying exact types.

Types of wildcards:

1. `?` — Unbounded wildcard
2. `? extends T` — Upper bounded
3. `? super T` — Lower bounded

Used primarily in method parameters.

---

## 4. Generics vs Wildcards — Core Difference

| Aspect                   | Generics                             | Wildcards                             |
| ------------------------ | ------------------------------------ | ------------------------------------- |
| Used For                 | Declaring types                      | Using types                           |
| Defined In               | Class, interface, method declaration | Method parameters                     |
| Syntax                   | `<T>`                                | `<?>`, `<? extends T>`, `<? super T>` |
| Flexibility              | Strict                               | Flexible                              |
| Can write to collection? | Yes                                  | Depends on wildcard                   |
| Compile-time checks      | Strong                               | Relaxed                               |

---

## 5. Where Generics Are Used

### 5.1 Defining a generic class

```java
class Pair<K, V> { ... }
```

### 5.2 Defining a generic method

```java
public <T> void print(T item) { ... }
```

### 5.3 Defining generic interfaces

```java
interface Comparable<T> { ... }
```

**Generics set the rules.**

---

## 6. Where Wildcards Are Used

Wildcards are used **while consuming existing generic classes**, especially in method arguments.

### Example:

```java
void printList(List<?> list) {
    list.forEach(System.out::println);
}
```

Wildcards **relax** type constraints.

---

## 7. Types of Wildcards and Their Rules

---

### 7.1 Unbounded Wildcard: `?`

```java
List<?> list = new ArrayList<String>();
```

Meaning: list of **unknown type**

* Can **read** elements (as `Object`)
* Cannot **add** anything except `null`

### Use When:

You want to accept a list of any type.

---

### 7.2 Upper Bounded Wildcard: `? extends T`

```java
List<? extends Number> nums;
```

Means the list contains:

* Number
* or subclasses (Integer, Double)

### Rules:

| Operation | Allowed?           |
| --------- | ------------------ |
| Read      | ✔ Yes              |
| Add       | ❌ No (except null) |

### Why no add?

Because the exact subtype is unknown.

Example:

If the actual type is `List<Integer>`, adding a `Double` would break type safety.

---

### 7.3 Lower Bounded Wildcard: `? super T`

```java
List<? super Integer> list;
```

Means the list can hold:

* Integer
* or its superclasses (Number, Object)

### Rules:

| Operation | Allowed?          |
| --------- | ----------------- |
| Read      | ❌ Only as Object  |
| Add       | ✔ Can add Integer |

### Use When:

You want to **write** elements into a structure.

---

## 8. Generics vs Wildcards – Practical Difference

### Generic method:

```java
public <T> void test(List<T> list) { ... }
```

Requires exact type match:

```java
test(new ArrayList<Integer>()); // allowed
test(new ArrayList<Number>());  // not allowed
```

### Wildcard method:

```java
public void test(List<? extends Number> list) { ... }
```

Allows flexible types:

```java
test(new ArrayList<Integer>()); // allowed
test(new ArrayList<Double>());  // allowed
```

---

## 9. PECS Rule (Important for Interviews)

**Producer Extends, Consumer Super**

| Scenario           | Use           |
| ------------------ | ------------- |
| List produces data | `? extends T` |
| List consumes data | `? super T`   |

Example:

```java
void addInt(List<? super Integer> list) {
    list.add(10); // allowed
}
```

For reading values:

```java
void sum(List<? extends Number> list) { ... }
```

---

## 10. When to Use Generics vs Wildcards

| Use Case                     | Generics | Wildcards     |
| ---------------------------- | -------- | ------------- |
| Define a reusable class      | ✔        | ❌             |
| Define a reusable method     | ✔        | ❌             |
| Accept different typed lists | ❌        | ✔             |
| Enforce strict type          | ✔        | ❌             |
| Read-only access             | ❌        | ✔ (`extends`) |
| Write-only access            | ❌        | ✔ (`super`)   |

---

## 11. Real-World Example

### Using Generics

```java
class Box<T> {
    T value;
}
```

You define your own type.

### Using Wildcards

```java
void process(List<? extends Number> nums) {
    nums.forEach(System.out::println);
}
```

You accept many types of lists.

---

## 12. Common Interview Questions

---

### Q1: Why do we need wildcards?

To allow flexibility when accepting generic types without compromising type safety.

---

### Q2: Can we replace all wildcards with generics?

No. Generics require exact type; wildcards allow multiple types.

---

### Q3: Why can't we add to `List<? extends T>`?

Because the actual subtype is unknown.

---

### Q4: What is PECS?

Producer Extends, Consumer Super — rule for choosing wildcards.

---

### Q5: Difference between `<T>` and `?`?

* `<T>` declares a new type parameter.
* `?` is an unknown type placeholder.

---

## Key Takeaways

* **Generics define type parameters** — used while declaring classes/methods.

* **Wildcards relax type constraints** — used when passing generic objects.

* `? extends T` → read-only (producer)

* `? super T` → write-only (consumer)

* `<?>` → read-only with unknown type

* Generics are strict, wildcards are flexible.

---

