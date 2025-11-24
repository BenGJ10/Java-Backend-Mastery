# Class vs Abstract Class vs Interface

## 1. Overview

Java provides three core building blocks for object-oriented design:

1. **Class**
2. **Abstract Class**
3. **Interface**

All three define types, but they differ in purpose, capabilities, and usage.
This note explains each one clearly with rules, examples, and a comparison table.

---

## 2. What is a Class?

A **class** is a blueprint from which objects are created.
It contains:

* Fields (variables)
* Methods (fully implemented)
* Constructors
* Static blocks
* Final methods
* Inner classes

### Example:

```java
class Car {
    String brand;

    Car(String brand) {
        this.brand = brand;
    }

    void start() {
        System.out.println(brand + " starting...");
    }
}
```

### Key Properties:

* Can be instantiated (`new Car("BMW")`)
* Supports complete implementation
* Can extend one parent class
* Can implement multiple interfaces

---

## 3. What is an Abstract Class?

An **abstract class** is a partially implemented class.
It can contain:

* Abstract methods (no body)
* Concrete methods (with body)
* Constructors
* Variables
* Final/static blocks
* Static methods

### Example:

```java
abstract class Animal {
    abstract void sound();   // must be overridden

    void sleep() {           // fully implemented
        System.out.println("Sleeping...");
    }
}
```

### Key Properties:

* Cannot be instantiated directly
* Child class must implement abstract methods
* Allows shared code + partial abstraction
* Can implement interfaces
* Can extend only one class

### When to use?

* When you want **base functionality + force child to implement some behavior**.

---

## 4. What is an Interface?

An **interface** defines a contract.
Classes implementing the interface must provide the behavior.

Interfaces contain:

* Abstract methods
* Default methods (Java 8+)
* Static methods (Java 8+)
* Private methods (Java 9+)
* Constants (`public static final`)

### Example:

```java
interface Payment {
    void pay(double amount);     // abstract method
}
```

Implementation:

```java
class UPI implements Payment {
    public void pay(double amount) {
        System.out.println("Paid: " + amount);
    }
}
```

### Key Properties:

* Cannot have instance fields (only constants)
* No constructors
* Supports multiple inheritance
* Implemented using `implements` keyword
* 100% abstraction (except default/static methods)

### When to use?

* When you want **common behavior across unrelated classes**
  (e.g., `Runnable`, `Comparable`, `List`)

---

# 5. Detailed Comparison Table

| Feature                       | Class       | Abstract Class      | Interface                          |
| ----------------------------- | ----------- | ------------------- | ---------------------------------- |
| Instantiation                 | ✔ Yes       | ❌ No                | ❌ No                               |
| Methods allowed               | Concrete    | Abstract + Concrete | Abstract, Default, Static          |
| Variables allowed             | Any type    | Any type            | Only `public static final`         |
| Constructors                  | ✔ Yes       | ✔ Yes               | ❌ No                               |
| Inheritance                   | Single      | Single              | Multiple                           |
| Access modifiers              | All allowed | All allowed         | Abstract methods are always public |
| Supports multiple inheritance | ❌ No        | ❌ No                | ✔ Yes                              |
| Level of Abstraction          | 0%          | 0–100%              | 100% (traditionally)               |
| Keyword                       | `class`     | `abstract class`    | `interface`                        |
| Can extend another class?     | ✔ Yes       | ✔ Yes               | ❌ No                               |
| Can implement interfaces?     | ✔ Yes       | ✔ Yes               | ✔ Yes (extend only interfaces)     |
| Object creation               | Yes         | No                  | No                                 |

---

## 6. When to Use What?

### Use **Class** when:

* You need full implementation
* No abstraction is required
* You want to create objects directly

---

### Use **Abstract Class** when:

* You want to share common behavior among related classes
* You want to enforce some mandatory methods
* You need constructors or non-final variables
* You need partial abstraction

Example use case:
All vehicles have common features → base class `Vehicle`.

---

### Use **Interface** when:

* You want multiple inheritance
* You want to define behavior for unrelated classes
* You want pure abstraction/contract
* You want to allow different implementations

Example use case:
`Runnable`, `Comparable`, `List`, `Map`

---

## 7. Real-World Analogy

### Class → Complete House Blueprint

You can build a house (object) directly.

### Abstract Class → Incomplete House Blueprint

Foundation is ready, but child classes must complete missing parts.

### Interface → Rules/Contract

“A building *must* have doors, windows, walls.”
But how they are built → up to the implementing class.

---

## 8. Code Comparison Example

### Abstract Class Example

```java
abstract class Animal {
    abstract void sound();

    void breathe() {
        System.out.println("Breathing");
    }
}
```

### Interface Example

```java
interface Pet {
    void play();
}
```

### Class Implementing Both

```java
class Dog extends Animal implements Pet {
    void sound() {
        System.out.println("Bark");
    }

    public void play() {
        System.out.println("Dog playing");
    }
}
```

---

## 9. Important Interview Questions

### Q1: Why interface cannot have constructors?

Because interfaces cannot create objects.

### Q2: Can an abstract class have final methods?

Yes. Only abstract methods must be overridden.

### Q3: Can an abstract class implement an interface?

Yes, but must provide implementation or remain abstract.

### Q4: Why Java supports multiple inheritance using interfaces?

To avoid the diamond problem with classes while still supporting polymorphism.

### Q5: Can an interface extend a class?

No. It can only extend other interfaces.

---

## Key Takeaways

* **Class** → Full implementation

* **Abstract Class** → Partial abstraction + common code

* **Interface** → Full abstraction + multiple inheritance

* Use abstract class for related types, interface for common behavior

* Interface methods are public by default

* Abstract class can have constructors, interfaces cannot

---
