# Interfaces in Java

## 1. What is an Interface?

An **interface** in Java is a completely abstract type used to specify a **contract** or **behavior** that classes must implement.

An interface contains:

* Abstract methods (until Java 7)
* Default methods (Java 8+)
* Static methods (Java 8+)
* Private methods (Java 9+)
* Constants (`public static final`)

A class implements an interface using the `implements` keyword.

---

## 2. Why Interfaces Are Used

### Main Purposes:

1. **Achieve Abstraction**
   Defines *what* a class must do, not *how*.

2. **Support Multiple Inheritance**
   Since Java does not support multiple inheritance with classes, interfaces fill this gap.

3. **Loose Coupling**
   Code depends on interfaces, not concrete implementations.

4. **Standardization**
   Interfaces set common behavior for multiple classes.

---

## 3. Syntax of Interface

```java
interface Vehicle {
    void start();  // abstract method
    void stop();   // abstract method
}
```

### Implementing the Interface

```java
class Car implements Vehicle {
    public void start() {
        System.out.println("Car starts");
    }

    public void stop() {
        System.out.println("Car stops");
    }
}
```

---

## 4. Features of Interfaces

### 4.1 Interface Variables

All variables in interfaces are:

* `public`
* `static`
* `final`

Example:

```java
interface Test {
    int x = 10;      // public static final int x = 10;
}
```

---

### 4.2 Interface Methods

Before Java 8:

* Only abstract methods allowed

After updates:

| Java Version       | Allowed Members                 |
| ------------------ | ------------------------------- |
| Java 8             | default methods, static methods |
| Java 9             | private methods                 |
| Java 7 and earlier | abstract methods only           |

---

## 5. Abstract Methods in Interface

```java
interface Animal {
    void sound();  // implicit: public abstract
}
```

Every implementing class **must override** the method.

---

## 6. Default Methods (Java 8+)

Provide **method implementation inside interface**.

```java
interface Vehicle {
    default void honk() {
        System.out.println("Beep!");
    }
}
```

Implementing class may override it or use it as is.

---

## 7. Static Methods (Java 8+)

Belong to the interface itself.

```java
interface MathUtil {
    static void info() {
        System.out.println("Math utilities");
    }
}
```

Usage:

```java
MathUtil.info();
```

---

## 8. Private Methods (Java 9+)

Used for internal code reuse inside interfaces.

```java
interface Demo {
    private void helper() {
        System.out.println("Inside private method");
    }

    default void test() {
        helper();
    }
}
```

Not accessible outside the interface.

---

## 9. Interface Inheritance

An interface can extend:

* Single interface
* Multiple interfaces

### Example:

```java
interface A {
    void a();
}

interface B {
    void b();
}

interface C extends A, B {
    void c();
}
```

---

## 10. Multiple Inheritance Using Interfaces

Java avoids multiple inheritance with classes, but supports it with interfaces.

```java
interface X { void run(); }
interface Y { void run(); }

class Test implements X, Y {
    public void run() {
        System.out.println("Running...");
    }
}
```

No ambiguity — child class provides implementation.

---

## 11. Interface vs Abstract Class

| Feature              | Interface                          | Abstract Class                      |
| -------------------- | ---------------------------------- | ----------------------------------- |
| Methods              | abstract, default, static, private | abstract + concrete                 |
| Variables            | public static final                | any type                            |
| Constructor          | Not allowed                        | Allowed                             |
| Multiple inheritance | Yes                                | No                                  |
| Abstraction level    | Full (mostly)                      | Partial                             |
| Use cases            | Common behavior across classes     | Shared implementation + abstraction |

---

## 12. Real-World Example — Payment System

### Interface:

```java
interface Payment {
    void pay(double amount);
}
```

### Implementations:

```java
class UPI implements Payment {
    public void pay(double amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}

class CreditCard implements Payment {
    public void pay(double amount) {
        System.out.println("Paid using Credit Card: " + amount);
    }
}
```

### Usage:

```java
Payment p = new UPI();
p.pay(500);

p = new CreditCard();
p.pay(1000);
```

---

## 13. Functional Interfaces (Java 8)

A **functional interface** has exactly **one abstract method**.

Examples:

* `Runnable`
* `Callable`
* `Comparator`
* `Consumer`

Can be used with **lambda expressions**.

Example:

```java
@FunctionalInterface
interface Operation {
    int add(int a, int b);
}

Operation o = (x, y) -> x + y;
System.out.println(o.add(5, 10));
```

---

## 14. Marker Interfaces

Interfaces with **no methods**.

Examples:

* `Serializable`
* `Cloneable`
* `RandomAccess`

Used to provide metadata or enable specific behavior.

---

## 15. Common Interview Questions

### Q1: Why does Java allow multiple inheritance through interfaces?

Because only method signatures are inherited — no concrete implementation → avoids ambiguity.

### Q2: Can we declare variables inside an interface?

Yes, but they are always `public static final`.

### Q3: Can an interface extend a class?

No. It can extend only interfaces.

### Q4: Can an interface implement another interface?

No. It can **extend** another interface.

### Q5: Can interface methods be protected or private?

* Abstract methods → always public
* Private methods → only inside interface (Java 9+)
* Protected → not allowed

### Q6: Why default methods were introduced?

To allow backward compatibility without breaking old implementations.

---

## Key Takeaways

* Interfaces define a contract for classes.

* They support multiple inheritance.

* They can contain abstract, default, static, and private methods.

* Variables inside interfaces are always constants.

* Widely used in frameworks (Spring, Collections API, Threads).

---
