# Wrapper Classes in Java

## 1. Overview

Java is **not purely object-oriented** because primitive types (`int`, `double`, `char`…) are **not objects**.
To allow primitives to be used where **objects are required** (e.g., Collections, Generics), Java provides **Wrapper Classes** — object representations of primitive data types.

Each primitive has a corresponding wrapper class:

| Primitive | Wrapper Class |
| --------- | ------------- |
| `byte`    | `Byte`        |
| `short`   | `Short`       |
| `int`     | `Integer`     |
| `long`    | `Long`        |
| `float`   | `Float`       |
| `double`  | `Double`      |
| `char`    | `Character`   |
| `boolean` | `Boolean`     |

Wrapper classes are **immutable** and stored in **Heap memory**.

---

## 2. Why Do We Need Wrapper Classes?

1. **Collections require objects**

```java
List<int> list;    // ❌ invalid
List<Integer> list; // ✔ valid
```

2. **Generics require objects**
3. **Object methods (toString(), equals(), hashCode())**
4. **Serialization**
5. **Utility methods (parseInt, valueOf, etc.)**
6. **Autoboxing / Unboxing support**

---

## 3. Autoboxing (Primitive → Wrapper)

Automatic conversion of primitive into wrapper class.

### Example:

```java
int x = 10;
Integer y = x;  // autoboxing
```

Equivalent to:

```java
Integer y = Integer.valueOf(x);
```

Used frequently in Collections:

```java
List<Integer> list = new ArrayList<>();
list.add(5); // primitive int is autoboxed to Integer
```

---

## 4. Unboxing (Wrapper → Primitive)

Automatic conversion of wrapper class into primitive.

### Example:

```java
Integer a = 20;
int b = a;  // unboxing
```

Equivalent to:

```java
int b = a.intValue();
```

---

## 5. Wrapper Class Utility Methods

Each wrapper class provides useful utility methods.

### 5.1 Parsing Strings → Primitives

```java
int x = Integer.parseInt("100");
double d = Double.parseDouble("12.5");
boolean b = Boolean.parseBoolean("true");
```

---

### 5.2 Converting Primitives → Strings

```java
String s1 = String.valueOf(10);
String s2 = Integer.toString(20);
```

---

### 5.3 Wrapper Constants

```java
int max = Integer.MAX_VALUE;
int min = Integer.MIN_VALUE;
```

---

## 6. Wrapper Classes Are Immutable

Just like Strings, wrapper classes are **immutable**.
Once created, the value cannot change.

```java
Integer n = 10;
n = 20; // creates new object, does not modify existing one
```

---

## 7. Integer Caching (Important for Interviews)

Java caches Integer objects from **–128 to +127**.

```java
Integer a = 100;
Integer b = 100;

System.out.println(a == b); // true (cached)
```

Outside cache range:

```java
Integer a = 200;
Integer b = 200;

System.out.println(a == b); // false (different objects)
```

Caching applies to:

* `Byte`
* `Short`
* `Integer`
* `Long`
* `Character` (0–127)

Not applicable to:

* `Float`
* `Double`

---

## 8. Performance Considerations

Autoboxing can cause:

* Unnecessary object creation
* Extra garbage for GC
* Slower performance

### Example of BAD practice:

```java
Integer sum = 0;
for (int i = 0; i < 100000; i++) {
    sum += i; // repeated unboxing + boxing
}
```

Better:

```java
int sum = 0;
```

---

## 9. Wrapper Class Comparison: == vs equals()

### == compares references

```java
Integer a = 128;
Integer b = 128;

System.out.println(a == b); // false
```

### equals() compares values

```java
System.out.println(a.equals(b)); // true
```

Because of caching, small integers behave differently:

```java
Integer a = 10;
Integer b = 10;
System.out.println(a == b); // true
```

---

## 10. Wrapper Classes in Collections

Collections cannot store primitives:

```java
ArrayList<int> list;   // ❌
```

Correct:

```java
ArrayList<Integer> list = new ArrayList<>();
```

Autoboxing makes usage seamless.

---

## 11. Wrapper Class Hierarchy

Wrapper classes are part of **java.lang** package:

```
Object
  ├── Number
  │     ├── Integer
  │     ├── Double
  │     ├── Float
  │     ├── Long
  │     ├── Short
  │     └── Byte
  ├── Boolean
  └── Character
```

---

## 12. Creating Wrapper Objects

### 12.1 Using constructors (Deprecated)

```java
Integer i = new Integer(10);   // ❌ discouraged
```

### 12.2 Preferred method: valueOf()

```java
Integer i = Integer.valueOf(10);
```

Benefits:

* Uses cached values
* Efficient memory usage

---

## 13. Real-World Examples

---

### Example 1: Storing primitives in a collection

```java
List<Integer> nums = new ArrayList<>();
nums.add(1);
nums.add(2);
```

---

### Example 2: Converting String to int

```java
int age = Integer.parseInt("25");
```

---

### Example 3: Handling null safely

```java
Integer value = null;

// int x = value;  // throws NullPointerException
```

---

## 14. Interview Questions

### Q1: Why do we need wrapper classes?

Because generics and collections require objects, not primitives.

---

### Q2: What is autoboxing & unboxing?

Automatic conversion between primitive ↔ wrapper types.

---

### Q3: Why is Integer immutable?

To support caching and allow safe sharing.

---

### Q4: Why does Integer use caching?

To reduce memory footprint and improve performance.

---

### Q5: Why is Float not cached?

Floating numbers have infinite possible values → caching impractical.

---

### Q6: What is the difference between valueOf() and new Integer()?

`valueOf()` uses cache; constructors always create new objects.

---

## Key Takeaways

* Wrapper classes wrap primitive data types into objects.

* Used for generics, collections, OOP features.

* Support autoboxing and unboxing.

* Immutable objects stored in heap.

* Integer caching improves performance.

* Use `equals()` to compare values, not `==`.

---

