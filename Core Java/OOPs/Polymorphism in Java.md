# Polymorphism in Java

## 1. What is Polymorphism?

**Polymorphism** means *one entity with many forms*.
In Java, it allows the same method or reference to behave differently based on:

1. **Compile-time (Static) Polymorphism** → Method Overloading

2. **Runtime (Dynamic) Polymorphism** → Method Overriding

Polymorphism enables flexibility, reusability, and clean code design.

---

## 2. Types of Polymorphism in Java

| Type                          | When Decided?      | Example            |
| ----------------------------- | ------------------ | ------------------ |
| **Compile-time polymorphism** | During compilation | Method Overloading |
| **Runtime polymorphism**      | During execution   | Method Overriding  |

---

## 3. Compile-Time Polymorphism (Method Overloading)

Method overloading occurs when multiple methods in the same class share the **same name** but differ in:

* Number of parameters
* Type of parameters
* Order of parameters

### Example:

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }
}

public class Demo {
    public static void main(String[] args) {
        Calculator c = new Calculator();
        System.out.println(c.add(5, 10));        // 15
        System.out.println(c.add(5.5, 10.5));    // 16.0
        System.out.println(c.add(1, 2, 3));      // 6
    }
}
```

### Key Points:

* Resolved at **compile time**.

* Return type alone cannot differentiate overloaded methods.

* Overloading improves readability and flexibility.

---

## 4. Runtime Polymorphism (Method Overriding)

Method overriding occurs when a subclass provides its own implementation of a method already defined in the parent class.

### Requirements for Overriding:

* Same method name

* Same parameters

* Same return type (or covariant)

* Occurs between **parent and child** classes

* Access modifier in child must be equal or more accessible

### Example:

```java
class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

public class Demo {
    public static void main(String[] args) {
        Animal a = new Dog();    // Upcasting
        a.sound();               // Dog's version called
    }
}
```

**Output:**

```
Dog barks
```

### Why this happens?

The reference type is `Animal` but the **actual object** is `Dog`.
Hence JVM decides which method to call at **runtime** → dynamic binding.

---

## 5. Upcasting and Runtime Polymorphism

Upcasting plays a major role in runtime polymorphism.

```java
Animal a = new Dog();  // Upcasting
a.sound();             // Calls Dog's method
```

Memory Flow:

```
Reference Type: Animal
Actual Object: Dog
Runtime decides: executes Dog's sound()
```

---

## 6. Rules of Method Overriding

| Rule                  | Description                          |
| --------------------- | ------------------------------------ |
| Same method signature | Name + parameters must match         |
| Access modifier       | Cannot be reduced                    |
| Return type           | Must be same or a subtype            |
| static methods        | Cannot be overridden (method hiding) |
| private methods       | Cannot be overridden                 |
| final methods         | Cannot be overridden                 |

---

## 7. Method Hiding (Static Polymorphism Misconception)

Static methods cannot be overridden; they are hidden.

```java
class A {
    static void show() { System.out.println("A show"); }
}

class B extends A {
    static void show() { System.out.println("B show"); }
}

public class Demo {
    public static void main(String[] args) {
        A a = new B();
        a.show();    // A show (not B)
    }
}
```

Static binding happens at compile time → no runtime polymorphism.

---

## 8. Real-World Example of Polymorphism

### Payment System

```java
class Payment {
    void pay() { System.out.println("Processing payment..."); }
}

class CreditCard extends Payment {
    @Override
    void pay() { System.out.println("Paid through Credit Card"); }
}

class UPI extends Payment {
    @Override
    void pay() { System.out.println("Paid through UPI"); }
}

public class Demo {
    public static void main(String[] args) {
        Payment p;

        p = new CreditCard();
        p.pay();  // Credit Card

        p = new UPI();
        p.pay();  // UPI
    }
}
```

---

## 9. Overloading vs Overriding

| Feature              | Method Overloading            | Method Overriding         |
| -------------------- | ----------------------------- | ------------------------- |
| Relationship         | Same class                    | Parent–Child classes      |
| Parameter list       | Must be different             | Must be same              |
| Return type          | Can differ                    | Must be same or covariant |
| Binding              | Compile-time                  | Runtime                   |
| Access modifier      | No restriction                | Cannot reduce visibility  |
| Static methods       | Can be overloaded             | Cannot be overridden      |
| Purpose              | Code flexibility, readability | Runtime polymorphism      |
| Inheritance required | No                            | Yes                       |

---                   

## 10. Common Interview Questions

### Q1: Can constructors be overloaded?

Yes, constructors can be overloaded. This is not polymorphism but overloading.

### Q2: Can constructors be overridden?

No, constructors cannot be overridden.

### Q3: Why private methods cannot be overridden?

Because they are not visible to child classes.

### Q4: What is the difference between overloading and overriding?

* **Overloading** → compile-time, same class, different parameters
* **Overriding** → runtime, different classes, same method signature

### Q5: What is dynamic binding?

JVM choosing which overridden method to call at runtime.

---

## Key Takeaways

* Polymorphism = one interface, many implementations.

* Overloading → compile-time polymorphism.

* Overriding → runtime polymorphism.

* Upcasting enables dynamic method dispatch.

* static, final, and private methods cannot participate in dynamic polymorphism.

---

