# Autoboxing and Unboxing in Java

## 1. Overview

Java provides **autoboxing** and **unboxing** to seamlessly convert between **primitive types** and **wrapper classes**.

* **Autoboxing** → primitive → wrapper object
* **Unboxing** → wrapper object → primitive

These conversions happen **automatically** by the Java compiler, making code cleaner and reducing manual conversions.

---

## 2. Why Do We Need Autoboxing & Unboxing?

1. **Collections** cannot store primitives

   ```java
   List<int> list;  // ❌ invalid
   List<Integer> list; // ✔ valid
   ```

2. **Generics** work only with objects

3. Useful for APIs that expect objects (`Integer`, `Double`, etc.)

4. Helps integrate primitive operations with object-based frameworks

---

## 3. Autoboxing (Primitive → Wrapper)

Automatic conversion by the compiler.

### Example 1:

```java
int x = 10;
Integer y = x;   // autoboxing
```

Equivalent to:

```java
Integer y = Integer.valueOf(x);
```

### Example 2: Using in Collections

```java
List<Integer> list = new ArrayList<>();
list.add(5);  // primitive int converted to Integer
```

---

## 4. Unboxing (Wrapper → Primitive)

Automatic conversion from wrapper object to primitive type.

### Example 1:

```java
Integer a = 20;
int b = a;  // unboxing
```

Equivalent to:

```java
int b = a.intValue();
```

### Example 2:

```java
Integer obj = 50;
int result = obj + 10;  // obj unboxed → primitive
```

---

## 5. Autoboxing & Unboxing in Expressions

Java automatically applies conversions in arithmetic operations.

```java
Integer a = 5;
Integer b = 10;

int sum = a + b;  // unboxing happens
Integer result = sum; // autoboxing
```

---

## 6. Potential Issues with Autoboxing & Unboxing

### 6.1 Performance Overhead

Autoboxing creates **unnecessary objects**, increasing memory usage and GC pressure.

Example (inefficient):

```java
Integer sum = 0;
for (int i = 1; i <= 1_000_000; i++) {
    sum += i;  // repeated unboxing + autoboxing
}
```

Better:

```java
int sum = 0;
```

---

### 6.2 NullPointerException (common mistake)

Unboxing a null wrapper results in **NPE**.

```java
Integer x = null;
int y = x;   // ❌ NullPointerException
```

---

### 6.3 Unexpected Identity Behavior with == (due to caching)

```java
Integer a = 100;
Integer b = 100;
System.out.println(a == b); // true (cached)
```

But:

```java
Integer a = 200;
Integer b = 200;
System.out.println(a == b); // false (no caching)
```

Use `.equals()` to compare values.

---

## 7. Autoboxing and Unboxing with Methods

### Autoboxing example:

```java
void printInteger(Integer i) {}

printInteger(10);  // int → Integer
```

### Unboxing example:

```java
int square(Integer n) {
    return n * n; // unboxing occurs
}
```

---

## 8. Example Demonstrating Both

```java
public class Example {
    public static void main(String[] args) {
        Integer a = 5;     // autoboxing
        int b = a;         // unboxing

        Integer c = a + b; // unboxing + computation + autoboxing
        System.out.println(c);
    }
}
```

---

## 9. Wrapper Caching Influence (Important)

Java caches values for:

* `Integer` → –128 to +127
* `Short`, `Byte`, `Character` (0–127)
* `Long` (–128 to +127)

This affects comparisons:

```java
Integer x = 127;
Integer y = 127;
System.out.println(x == y);  // true (cached)

Integer p = 128;
Integer q = 128;
System.out.println(p == q);  // false (different objects)
```

---

## 10. Summary Table

| Concept    | Description                        | Example                      |
| ---------- | ---------------------------------- | ---------------------------- |
| Autoboxing | primitive → wrapper                | `Integer x = 10;`            |
| Unboxing   | wrapper → primitive                | `int y = x;`                 |
| Used in    | Collections, generics, expressions | `list.add(5)`                |
| Risk       | NPE, performance overhead          | `int y = obj; // obj = null` |
| Caching    | affects `==` comparison            | `Integer a = 100;`           |

---

## Key Takeaways

* Autoboxing and unboxing automate conversion between primitives and wrapper types.

* Useful when working with Collections and Generics.

* Be careful of:

  * **NullPointerException**
  * **Performance overhead**
  * **Incorrect comparisons using ==**

* Wrapper caching affects identity comparison.

---
