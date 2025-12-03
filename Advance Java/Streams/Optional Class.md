# Optional Class in Java

## 1. NullPointerException (NPE)

A **NullPointerException (NPE)** is Java’s most common runtime exception.
It occurs when you try to use a reference that points to **null** as if it pointed to an actual object.

Example:

```java
String s = null;
System.out.println(s.length()); // ❌ NullPointerException
```

---

## 2. When Does NPE Occur?

Below are the typical situations:

### 2.1 Calling a method on a null reference

```java
obj.method(); // obj is null → NPE
```

### 2.2 Accessing fields of a null object

```java
employee.name = "Bob"; // employee is null
```

### 2.3 Accessing array length when array is null

```java
int[] arr = null;
int len = arr.length; // NPE
```

### 2.4 Using null in autoboxing/unboxing

```java
Integer x = null;
int y = x;   // ❌ NPE (unboxing)
```

### 2.5 Chaining method calls

```java
person.getAddress().getCity(); // if getAddress() is null → NPE
```

---

## 3. How to Prevent NullPointerExceptions

### A. Null checks

```java
if (s != null) {
    System.out.println(s.length());
}
```

### B. Using `Objects` utility class

```java
Objects.requireNonNull(obj, "Object cannot be null");
```

### C. Defensive programming

Initialize fields properly to avoid null defaults.


### D. Use `Optional` instead of returning null (recommended)

---

## 4. Optional Class in Java

`Optional<T>` is a container object introduced in Java 8 that **may contain a non-null value, or may be empty**.

Purpose:

* Avoid returning null
* Avoid NullPointerExceptions
* Write cleaner, expressive, functional code

---

## 5. Creating Optional Objects

### 5.1 Optional.of()

Use when value is guaranteed non-null.

```java
Optional<String> opt = Optional.of("Hello");
```

If value is `null`, this throws NPE.

### 5.2 Optional.ofNullable()

Use when value **may or may not** be null.

```java
Optional<String> opt = Optional.ofNullable(name);
```

### 5.3 Optional.empty()

Represents empty Optional.

```java
Optional<String> opt = Optional.empty();
```

---

## 6. Checking Optional Values

### 6.1 isPresent()

```java
if (opt.isPresent()) {
    System.out.println(opt.get());
}
```

### 6.2 isEmpty()

```java
if (opt.isEmpty()) {
    System.out.println("No value");
}
```

---

## 7. Getting Values Safely

### 7.1 get() — Not recommended (may throw exception)

```java
String value = opt.get(); // Throws NoSuchElementException if empty
```

Use safer alternatives below.

### 7.2 orElse()

Provides a default value.

```java
String value = opt.orElse("Default");
```

### 7.3 orElseGet()

Uses a supplier function (lazy version of orElse).

```java
String value = opt.orElseGet(() -> "Generated default");
```

### 7.4 orElseThrow()

Throws a custom exception.

```java
String value =
    opt.orElseThrow(() -> new IllegalArgumentException("Value missing"));
```

---

## 8. Transforming Optional Values

### 8.1 map()

Applies a function if value is present.

```java
Optional<String> upper =
    opt.map(s -> s.toUpperCase());
```

### 8.2 flatMap()

Used when the function returns another Optional.

```java
Optional<String> city =
    person.getAddress()        // Optional<Address>
          .flatMap(Address::getCity); // Optional<String>
```

## 9. Filtering Values

```java
Optional<String> result =
    Optional.of("Java")
            .filter(s -> s.startsWith("J"));
```

If filter fails → returns empty Optional.

---

## 10. Avoiding NPE Using Optional — Real Example

### Without Optional (traditional)

```java
String city = person.getAddress().getCity().getName();
```

This throws NPE if any step is null.


### With Optional (safe)

```java
String city =
    Optional.ofNullable(person)
            .map(Person::getAddress)
            .map(Address::getCity)
            .map(City::getName)
            .orElse("Unknown");
```

This eliminates NPE completely.

## 11. When Not to Use Optional

Optional **should NOT** be used:

1. **As a field in a class** (adds overhead)
2. **For method parameters**
3. **In collections**
4. **In performance-critical areas**

Correct use-case: **method return types**.

---

## 12. Common Interview Questions

### Q1: What causes a NullPointerException?

Accessing a method/field of a null reference.

### Q2: How does Optional help reduce NPE?

It wraps potentially null values and forces safe handling.

### Q3: Difference between `Optional.of()` and `Optional.ofNullable()`?

| Method              | Behavior                                 |
| ------------------- | ---------------------------------------- |
| `of(value)`         | Throws NPE if value is null              |
| `ofNullable(value)` | Accepts null and wraps as empty Optional |

### Q4: Is Optional a replacement for null everywhere?

No. It should mainly be used as a return type.

### Q5: What is the difference between `orElse()` and `orElseGet()`?

* `orElse()` → evaluates default value **eagerly**
* `orElseGet()` → evaluates default value **lazily**

---

## Key Takeaways

* NullPointerException occurs when dereferencing a null reference.

* Optional helps avoid NPE through safe, expressive APIs.

* Use Optional for return types that may contain null.

* Do not rely on Optional for class fields or method parameters.

* Use `map()`, `flatMap()`, `filter()`, `orElse()` to handle values elegantly.

---