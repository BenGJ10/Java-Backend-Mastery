# abstract Keyword in Java

## 1. Overview

The **`abstract` keyword** in Java is used to define:

1. **Abstract classes**
2. **Abstract methods**

It allows you to create a base structure without specifying full implementation.
Abstraction focuses on *what* needs to be done, not *how* it will be done.

---

## 2. abstract Methods

An **abstract method**:

* Has a declaration (signature)
* Has **no body**
* Must be implemented by child classes

### Syntax:

```java
abstract void draw();
```

### Example:

```java
abstract class Shape {
    abstract void draw();   // no implementation
}
```

Child class must implement it:

```java
class Circle extends Shape {
    void draw() {
        System.out.println("Drawing Circle");
    }
}
```

---

## 3. Rules for abstract Methods

| Rule                              | Explanation                                      |
| --------------------------------- | ------------------------------------------------ |
| Must be inside an abstract class  | Cannot exist in a normal class                   |
| Cannot be private                 | Must be overridden by child                      |
| Cannot be final                   | Final methods cannot be overridden               |
| Cannot be static                  | Abstract behavior belongs to instance, not class |
| Must be overridden in child class | Unless child is abstract                         |

---

## 4. abstract Classes

A class declared with `abstract` keyword **cannot be instantiated**.

### Syntax:

```java
abstract class Animal {
    abstract void sound();       // abstract method

    void sleep() {               // normal method
        System.out.println("Animal sleeps");
    }
}
```

### Key Properties:

* Can contain abstract + concrete methods
* Can have constructors
* Can have variables, static methods, blocks
* Can extend another class (abstract or concrete)

### Cannot Do:

```java
Animal a = new Animal(); // error: cannot instantiate abstract class
```

---

## 5. Constructor in Abstract Class

Even though abstract classes cannot be instantiated, they **can have constructors**.
These are used for initial setup of fields.

### Example:

```java
abstract class Person {
    String name;

    Person(String name) {
        this.name = name;
    }

    abstract void work();
}

class Employee extends Person {
    Employee(String name) {
        super(name);
    }

    void work() {
        System.out.println(name + " is working");
    }
}
```

---

## 6. Abstraction Level in abstract Classes

Abstract class provides **partial abstraction**.

* Some methods can be abstract
* Some methods can be fully implemented

This is different from interfaces (which originally gave 100% abstraction).

---

## 7. Why Use abstract Classes?

### When:

* You want to provide **common functionality** in base class
* You want child classes to **implement specific behavior**
* You want to avoid object creation of incomplete classes

### Example Scenario:

A class like `Shape` is too generic to create objects:

```java
Shape s = new Shape(); // meaningless
```

But:
Circle, Rectangle, Triangle → meaningful.

---

## 8. Real Example — Payment System

```java
abstract class Payment {
    abstract void pay(double amount);

    void showReceipt() {
        System.out.println("Payment completed");
    }
}

class UPI extends Payment {
    void pay(double amount) {
        System.out.println("Paid via UPI: " + amount);
    }
}
```

Usage:

```java
Payment p = new UPI();
p.pay(400);
p.showReceipt();
```

---

## 9. abstract Class vs Interface

| Feature              | abstract class          | interface                 |
| -------------------- | ----------------------- | ------------------------- |
| Methods              | Abstract + concrete     | Abstract, default, static |
| Variables            | Any type                | public static final only  |
| Constructor          | Allowed                 | Not allowed               |
| Multiple inheritance | Only one abstract class | Multiple interfaces       |
| Abstraction level    | Partial                 | Complete (mostly)         |

---

## 10. abstract + final + static — Valid or Not?

| Combination                        | Valid? | Reason                          |
| ---------------------------------- | ------ | ------------------------------- |
| abstract + final method            | ❌      | final cannot be overridden      |
| abstract + static method           | ❌      | static cannot be abstract       |
| abstract + private method          | ❌      | private cannot be overridden    |
| abstract class with final methods  | ✔      | final concrete methods allowed  |
| abstract class with static methods | ✔      | static concrete methods allowed |

---

## 11. Memory Behavior of abstract Classes

When creating a child object:

```java
Payment p = new UPI();
```

Memory:

```
Reference type: Payment (abstract)
Object type: UPI (concrete)
```

* Abstract methods resolved via overriding
* Concrete methods inherited normally

---

## 12. Common Interview Questions

### Q1: Can abstract class have a constructor?

Yes. Used to initialize common fields.

### Q2: Can abstract method be private?

No. It must be accessible to child classes for overriding.

### Q3: Is it mandatory for child class to override all abstract methods?

Yes, unless the child class is also abstract.

### Q4: What is the purpose of abstract class?

To provide a base template with some enforced behavior.

### Q5: Can we declare an abstract class as final?

No. It contradicts the purpose of inheritance.

---

## Key Takeaways

* `abstract` keyword is used for **classes** and **methods**.

* Abstract methods have **no body** and must be implemented by child classes.

* Abstract classes **cannot be instantiated**, but can have constructors, fields, and methods.

* Provides **partial abstraction** and supports inheritance.

* Used to enforce a structure while allowing flexible implementations.

---

