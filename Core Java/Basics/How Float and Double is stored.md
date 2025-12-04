# How FLOAT and DOUBLE Numbers Are Stored in Memory 


## 1. Overview

Java uses the **IEEE 754 standard** to store floating-point numbers.

| Java Type | Size    | IEEE 754 Format  | Approx. Precision     |
| --------- | ------- | ---------------- | --------------------- |
| `float`   | 32 bits | Single Precision | ~6–7 decimal digits   |
| `double`  | 64 bits | Double Precision | ~15–16 decimal digits |

Floating-point numbers are stored using **scientific notation in binary**, not decimal.

Every floating-point number is represented using:

```
Value = (–1)^sign  ×  (1.mantissa) ×  2^(exponent – bias)
```

Where:

* **sign** → 0 for positive, 1 for negative
* **exponent** → biased exponent
* **mantissa (fraction)** → fractional binary digits
* **bias**:

  * float → 127
  * double → 1023

---

## 2. IEEE 754 Single Precision (float – 32 bits)

A 32-bit float is divided as follows:

```
1 bit  → Sign
8 bits → Exponent
23 bits → Mantissa (fraction)
```

Memory layout:

```
SEEEEEEE EMMMMMMMM MMMMMMMM MMMMMMMM
```

---

## Example: Storing 6.5 in float

### Step 1: Convert integer + fraction to binary

6.5 = 110.1₂

### Step 2: Normalize to form 1.x × 2^n

```
110.1₂  = 1.101 × 2^2
```

So:

* Mantissa = **101000…**
* Exponent = **2**

### Step 3: Apply exponent bias

```
Exponent stored = 2 + 127 = 129
129 in binary = 10000001
```

### Step 4: Construct final IEEE 754 float

```
Sign:     0
Exponent: 10000001
Mantissa: 10100000000000000000000
```

Full 32-bit representation:

```
0 10000001 10100000000000000000000
```

---

## 3. IEEE 754 Double Precision (double – 64 bits)

A 64-bit double is split as:

```
1 bit  → Sign
11 bits → Exponent
52 bits → Mantissa
```

Memory layout:

```
SEEEEEEEEEEE MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
```

Double precision increases:

* Range
* Accuracy
* Reduced rounding errors

---

## 4. Why Are Floating-Point Values Inaccurate?

Binary cannot represent many decimal fractions exactly.

Example:

```
0.1 in decimal = 0.0001100110011001100... (repeating in binary)
```

Since it’s repeating, binary cannot store it accurately → rounding occurs.

Example in Java:

```java
System.out.println(0.1 + 0.2);
```

Output:

```
0.30000000000000004
```

This is not a bug but a consequence of binary fraction rounding.

---

## 5. float vs double — Key Differences

| Feature     | float (32-bit)         | double (64-bit)                  |
| ----------- | ---------------------- | -------------------------------- |
| Precision   | ~7 digits              | ~15 digits                       |
| Performance | Faster on some systems | Standard default                 |
| Memory      | 4 bytes                | 8 bytes                          |
| Accuracy    | Lower                  | Higher                           |
| Use Case    | Graphics, large arrays | Scientific, finance computations |

Java **defaults to double** for decimal literals.

---

## 6. Special IEEE 754 Values

Floating-point numbers can represent special values:


### 6.1 Positive & Negative Infinity

```java
double x = 1.0 / 0;
System.out.println(x); // Infinity
```

### 6.2 NaN (Not a Number)

```java
double y = 0.0 / 0.0;
System.out.println(y); // NaN
```

Important: `NaN != NaN` (always false)

## 6.3 Signed Zero

```
+0.0 and -0.0 exist
```

---

## 7. Internal Calculation Formula

Every floating-point number is reconstructed as:

```
Value = (–1)^sign × (1 + mantissa) × 2^(exponent – bias)
```

Bias values:

* float → 127
* double → 1023

---

## 8. Visual Comparison

### float (32-bit)

```
[Sign:1][Exponent:8][Mantissa:23]
```

### double (64-bit)

```
[Sign:1][Exponent:11][Mantissa:52]
```

Double uses more bits → more precision and range.

---

## 9. Practical Example: Why Using float May Cause Errors?

```java
float a = 1.0f;
System.out.println(a / 3);
```

Output:

```
0.33333334
```

float cannot store 1/3 exactly.

---

## 10. When Should You Use BigDecimal?

For **precise decimal calculations**, such as:

* Money
* Tax/GST
* Banking
* Accounting

Example:

```java
BigDecimal a = new BigDecimal("0.1");
BigDecimal b = new BigDecimal("0.2");
System.out.println(a.add(b)); // 0.3 exactly
```

---

## Key Takeaways

* Java floating numbers follow **IEEE 754 binary scientific notation**.

* Values are stored as **sign + biased exponent + mantissa**.

* `float` is 32-bit (7 digits precision), `double` is 64-bit (15 digits).

* Many decimal values **cannot be represented exactly** in binary → rounding happens.

* Use `BigDecimal` for financial or high-precision work.

* `double` is default and preferred for general use.

---
