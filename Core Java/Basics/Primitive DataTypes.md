# Java Primitive Data Types

## 1. Overview

Java provides **8 primitive data types**, which are **not objects** and store simple values directly in memory (usually on the stack).
They are the **fastest** and most memory-efficient way to store data.

Primitive types differ from **wrapper classes** (`Integer`, `Double`, `Boolean`, etc.) because:

* They store **values**, not objects
* They cannot be `null` (except `char` default `\u0000` and numeric default `0`)
* They are not stored on heap (unless part of an object)

---

## 2. List of Java Primitive Data Types

| Type      | Size (bits)                 | Default Value | Range                           | Wrapper Class |
| --------- | --------------------------- | ------------- | ------------------------------- | ------------- |
| `byte`    | 8                           | 0             | –128 to 127                     | `Byte`        |
| `short`   | 16                          | 0             | –32,768 to 32,767               | `Short`       |
| `int`     | 32                          | 0             | –2,147,483,648 to 2,147,483,647 | `Integer`     |
| `long`    | 64                          | 0L            | –9.22e18 to 9.22e18             | `Long`        |
| `float`   | 32                          | 0.0f          | ~6–7 decimal digits precision   | `Float`       |
| `double`  | 64                          | 0.0d          | ~15 decimal digits precision    | `Double`      |
| `char`    | 16                          | '\u0000'      | Unicode characters              | `Character`   |
| `boolean` | JVM-dependent internal size | false         | true / false                    | `Boolean`     |

---

## 3. Integer Types (byte, short, int, long)

Used to store whole numbers.

### 3.1 byte

```java
byte b = 10;
```

* Smallest integer type
* Useful for memory-constrained applications or streams

---

### 3.2 short

```java
short s = 1000;
```

Rarely used except in large arrays to save memory.

---

### 3.3 int (Most commonly used)

```java
int age = 21;
```

Default choice for integers.

---

### 3.4 long

```java
long population = 1380004385L; // must end with L
```

Used for large values.

---

## 4. Floating-Point Types (float, double)

Used for decimal numbers.

### 4.1 float (32-bit)

```java
float price = 10.5f; // suffix f required
```

### 4.2 double (64-bit)

```java
double height = 5.98765;
```

Default type for decimals → more precision.

---

## 5. char (Character Type)

Stores a single Unicode character.

```java
char c = 'A';
char unicode = '\u0041'; // Unicode for A
```

* 16-bit
* Range: `\u0000` to `\uFFFF`

---

## 6. boolean

Stores `true` or `false`.

```java
boolean isJavaFun = true;
```

Used in:

* Conditions
* Comparisons
* Logical operations

---

## 7. Default Values of Primitives in Java

When primitives are fields inside a class (not local variables), they get default values:

| Type    | Default                   |
| ------- | ------------------------- |
| byte    | 0                         |
| short   | 0                         |
| int     | 0                         |
| long    | 0L                        |
| float   | 0.0f                      |
| double  | 0.0d                      |
| char    | '\u0000' (null character) |
| boolean | false                     |

**Local variables have no default value** — must be explicitly initialized.

---

## 8. Memory Size and Performance

Primitive types store values **directly**, which makes them:

* Fast to access
* Memory efficient
* Suitable for loops, arithmetic, scientific calculations

Wrapper classes are objects → slow and memory-heavy.

---

### Example: Primitive vs Wrapper

```java
int x = 10;        // primitive
Integer y = 10;    // wrapper (object)
```

Wrapper:

* Stored in heap
* Can be null
* Has methods (`y.intValue()`)

Primitive:

* Stored on stack
* Cannot be null
* Faster

---

## 9. Numeric Literal Enhancements (Java 7+)

### Underscores in Numbers

```java
int num = 1_000_000;     // improves readability
long big = 9_223_372_036_854_775_807L;
```

---

## 10. Type Casting & Promotion Rules

### Implicit Widening

Small → large types (safe):

```java
int a = 10;
long b = a;  // OK
```

### Explicit Narrowing

Large → small types (risk of data loss):

```java
long a = 1000L;
int b = (int) a; // must cast
```

---

## 11. Overflow & Underflow

```java
int x = Integer.MAX_VALUE;
x = x + 1;

System.out.println(x);  // prints negative value
```

- Java does NOT throw an error for overflow.

- Because it uses **two's complement** representation, it wraps around. 

- For example, adding 1 to `Integer.MAX_VALUE` results in `Integer.MIN_VALUE`.

- This cyclic behavior is important to understand for numerical computations.

---

## 12. Summary Table

| Category       | Types                  |
| -------------- | ---------------------- |
| Integer        | byte, short, int, long |
| Floating-point | float, double          |
| Text           | char                   |
| Boolean        | boolean                |

---

## Key Takeaways

* Java has **8 primitive types**, which are the foundation of Java memory efficiency.

* Primitives are stored directly in stack memory and are faster than objects.

* Wrapper classes allow primitives to work with collections and generics.

* Type promotion rules and overflow behavior are essential for interviews.

* `int` and `double` are the default choices for integer and decimal operations.

---
