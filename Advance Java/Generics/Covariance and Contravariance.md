# Covariance & Contravariance

## 1. Overview

Covariance and contravariance describe **how type relationships behave when applied to generic types or arrays**.

They answer the question:

```
If A is a subtype of B, is List<A> also a subtype of List<B>?
```

Java’s generics are **invariant** by default, meaning:

```java
List<Integer> is NOT a subtype of List<Number>
```

But using **wildcards**, we can achieve covariance or contravariance.

---

## 2. Covariance ( ? extends T )

Covariance means **you can assign a more specific type to a more general reference**.

### Example:

```java
Integer extends Number
```

But with generics:

```java
List<Integer> ≠ List<Number>   // invariance
```

To enable it:

```java
List<? extends Number> list = new ArrayList<Integer>();
```

Here, the list is **covariant** with respect to Number.

---

### 2.1 What Does Covariance Allow?

#### ✔ Reading is safe

```java
Number n = list.get(0);
```

#### ❌ Writing is not allowed

```java
list.add(10); // ERROR
```

Reason:
The compiler does not know the exact type → could be List<Double>.

---

### 2.2 When to Use Covariance?

Use it when the collection **produces** values (read-only).

Example:

```java
double sum(List<? extends Number> nums) { ... }
```

Can accept:

* List<Integer>
* List<Double>
* List<Float>

---

## 3. Contravariance ( ? super T )

Contravariance means **you can assign a more general type to a more specific reference**.

Example:

```java
List<? super Integer> list = new ArrayList<Number>();
```

Here, the list is **contravariant** with respect to Integer.

---

### 3.1 What Does Contravariance Allow?

#### ✔ Writing is safe

```java
list.add(10); // allowed
list.add(20);
```

#### ❌ Reading gives Object

```java
Object obj = list.get(0);  // returns Object
```

Because actual type could be Integer, Number, or Object.

---
## 4. Invariance (Default Behavior)

Without wildcards, Java generics are **invariant**.

```java
List<Number> nums = new ArrayList<Integer>();  // ❌ Compile error
```

Even though Integer → Number, the generic types do NOT follow the same hierarchy.

---

## 5. Arrays Are Covariant (But Unsafe)

Java arrays behave differently:

```java
Integer[] ia = {1, 2, 3};
Number[] na = ia; // ✔ Allowed (covariant)
```

But this is dangerous:

```java
na[0] = 3.14; // Runtime ArrayStoreException
```

Arrays perform **runtime type checks**
Generics rely on **compile-time checks** and **erasure**.

---

## 6. PECS Rule (Important Interview Rule)

PECS stands for:

> **Producer Extends, Consumer Super**

| Wildcard      | Meaning               | Usage                |
| ------------- | --------------------- | -------------------- |
| `? extends T` | T or its subclasses   | Use when **reading** |
| `? super T`   | T or its superclasses | Use when **writing** |

---

## 7. Visual Summary

```
Covariance ( ? extends Number )
Read ✓ | Write ✗
Accepts: List<Integer>, List<Double>, ...

Contravariance ( ? super Integer )
Read ✗ (Object only) | Write ✓
Accepts: List<Integer>, List<Number>, List<Object>

Invariance
List<Number> ≠ List<Integer>

Arrays (unsafe covariance)
Integer[] -> Number[]
```

---

## 8. Example Comparing All Three

### Covariance Example (Reading)

```java
List<? extends Number> list = new ArrayList<Integer>();
Number n = list.get(0); // OK
list.add(10);           // ERROR
```

---

### Contravariance Example (Writing)

```java
List<? super Integer> list = new ArrayList<Object>();
list.add(10); // OK
Integer x = list.get(0); // ERROR (returns Object)
```

---

### Invariance Example

```java
List<Number> ln = new ArrayList<Number>();
List<Integer> li = new ArrayList<Integer>();

ln = li; // ERROR
```

---

## 9. Practical Use Cases


### 9.1 Covariance Example: Sum of Numbers

```java
double sum(List<? extends Number> nums) {
double total = 0;
for (Number n : nums)
total += n.doubleValue();
return total;
}
```

Can pass:

* List<Integer>
* List<Double>

---
### 9.2 Contravariance Example: Adding Integers

```java
void addIntegers(List<? super Integer> list) {
    list.add(1);
    list.add(2);
}
```
---

## 10. Interview Questions


### Q1. Difference between covariance and contravariance?

* Covariance: `? extends T` → allows reading
* Contravariance: `? super T` → allows writing

---

### Q2. Why are arrays covariant but generics are not?

Arrays need runtime checks → unsafe.
Generics need compile-time safety → invariant.

---

### Q3. Can we use both extends and super at once?

No:

```java
List<? extends super Number> // ❌ illegal
```

---

### Q4. What is PECS?

Producer Extends, Consumer Super.

---

### Q5. Why can't we write to a `? extends` list?

Because the exact subtype is unknown.

---

## Key Takeaways

* **Covariance (`? extends T`)** → read-only, flexible input.

* **Contravariance (`? super T`)** → write-only, flexible output.

* **Invariance** → generics don't follow subtype relationships.

* Use **PECS**: Producer Extends, Consumer Super.

* Arrays are covariant but unsafe; generics are invariant but safe.

---
