# final Keyword in Java

## 1. Overview

The **`final` keyword** in Java is a non-access modifier used to apply restrictions on:

1. **Variables**
2. **Methods**
3. **Classes**

Its meaning changes based on where it is used:

| Usage            | Meaning                            |
| ---------------- | ---------------------------------- |
| `final` variable | Value cannot be changed (constant) |
| `final` method   | Method cannot be overridden        |
| `final` class    | Class cannot be inherited          |

---

## 2. final Variables

A **final variable** becomes a constant — once assigned, its value cannot be modified.

### Example:

```java
final int x = 10;
x = 20;  // error: cannot assign a value to final variable
```

---

### Types of final variables:

#### 2.1 Final Instance Variable

Must be initialized:

* At declaration
* OR inside constructor

```java
class Demo {
    final int a;

    Demo() {
        a = 100;   // allowed
    }
}
```

---

#### 2.2 Final Static Variable (Constant)

Must be initialized:

* At declaration
* OR inside static block

```java
class Test {
    static final int MAX;

    static {
        MAX = 500;   // allowed
    }
}
```

Used for constants like:

```java
public static final double PI = 3.14159;
```

---

#### 2.3 Final Local Variable

Assigned once inside a method.

```java
void show() {
    final int x = 50;
}
```

---

### Key Notes About final Variables

* Must be assigned exactly **once**.
* Commonly used for **constants**.
* Helps JVM optimize performance.
* References declared final cannot refer to another object, but **object content can still change**.

Example:

```java
final Student s = new Student();
s = new Student(); // error

s.name = "Ben";    // allowed
```

---

## 3. final Methods

A final method **cannot be overridden** by child classes.

### Example:

```java
class A {
    final void show() {
        System.out.println("A show");
    }
}

class B extends A {
    void show() { }  // error: cannot override final method
}
```

### Why use final methods?

* To prevent modification of important behavior.
* Improves security.
* Stabilizes core logic in parent classes.

---

## 4. final Classes

A final class **cannot be extended** (no inheritance allowed).

### Example:

```java
final class A {
    void show() { }
}

class B extends A { }  // error: cannot inherit final class
```

### Why use final classes?

* To prevent inheritance.
* To create immutable classes.
* For security reasons (e.g., standard library classes).

### Real Example:

`String` class in Java is final.

```java
public final class String { ... }
```

Reason:

* Prevent modification
* Ensure immutability
* Allow safe use in collections

---

## 5. final with Methods and Variables Inside final Class

Even if a class is final:

* Its methods **can be non-final**
* Its variables **can be changed**

Example:

```java
final class A {
    int x = 10;      // not final
    void display() { }
}
```

Only restriction → **class cannot be extended**.

---

## 6. final and Inheritance

### A final variable in parent is inherited:

```java
class A {
    final int x = 10;
}

class B extends A {
    // x is inherited but cannot be changed
}
```

### A final method in parent is inherited:

```java
class A {
    final void show() { }
}

class B extends A {
    // show() available but cannot override
}
```

---

## 7. Blank Final Variable

A **blank final** variable is a final variable not initialized at declaration.

Must be initialized:

* In constructor for instance variable
* In static block for static variable

Example:

```java
class Demo {
    final int a;  // blank final

    Demo() {
        a = 200;  // must be initialized here
    }
}
```

---

## 8. final Parameter

Method parameters can be declared final.

```java
void show(final int x) {
    x = x + 10;  // error
}
```

Used when the parameter should not be modified.

---

## 9. final and Performance Optimization

JVM uses final variables and methods to:

* Inline method calls
* Optimize constant values
* Improve execution speed

---

## 10. Common Interview Questions

### Q1: Can final methods be overloaded?

Yes. Only overriding is restricted.

### Q2: Can we initialize a final static variable inside a constructor?

No. Static variables belong to class, not object.

### Q3: Is an object immutable if its reference is final?

No. Only reference can't be changed. Object contents can be modified unless they are final too.

### Q4: Why String class is final?

To ensure immutability, security, and allow String Pool optimization.

### Q5: Can constructors be final?

No — constructors are not inherited or overridden.

### Q6: Difference between **finally** and **final**?

* `final` → keyword (restriction)
* `finally` → block in exception handling
* `finalize()` → method used before garbage collection (deprecated)

---

## Key Takeaways

* **final variable** → constant (cannot reassign)

* **final method** → cannot be overridden

* **final class** → cannot be extended

* Used for immutability, security, and performance

* Static final variables are compile-time constants

* Object referenced by a final variable can still change internally

---
