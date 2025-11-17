# Abstraction in Java

## 1. What is Abstraction?

**Abstraction** is the OOP principle of hiding internal implementation details and showing only the **necessary features** to the user.

In Java, abstraction is achieved through:

1. **Abstract Classes**
2. **Interfaces**

Abstraction focuses on **what an object does**, not *how* it does it.

Example in real life:

* You press the brake in a car → you don’t need to know *how* hydraulic pressure works.

---

## 2. Why Abstraction is Needed

### Key Benefits:

1. **Hides Complexity**
   User interacts only with essential behavior.

2. **Improves Security & Modularity**
   Internal data/functions are protected.

3. **Supports Loose Coupling**
   Code depends on abstract behavior rather than concrete implementations.

4. **Promotes Reusability & Scalability**
   Concrete implementations can change independently.

---

## 3. Abstraction Through Abstract Classes

An **abstract class** provides partial abstraction.
It can contain:

* Abstract methods (no body)
* Concrete (normal) methods
* Constructors
* Variables

### Syntax:

```java
abstract class Animal {
    abstract void sound();  // no body (must be implemented)
    
    void sleep() {
        System.out.println("Animal sleeps");
    }
}
```

### Child class must override abstract methods:

```java
class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}
```

### Usage:

```java
Animal a = new Dog();
a.sound();
a.sleep();
```

---

## 4. Rules of Abstract Classes

| Rule                                            | Description                      |
| ----------------------------------------------- | -------------------------------- |
| Cannot instantiate                              | `new Animal()` → error           |
| Can have constructors                           | Used to initialize values        |
| Can have variables                              | final, non-final, static allowed |
| Can have both abstract & concrete methods       | Partial abstraction              |
| Child class must implement all abstract methods | Unless child is abstract         |

---

### Example with Constructor in Abstract Class

```java
abstract class Shape {
    Shape() {
        System.out.println("Shape created");
    }

    abstract void draw();
}

class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing circle");
    }
}
```

---

## 5. Abstraction Through Interfaces

Before Java 8 → Interfaces provided **100% abstraction**.
Now with default/static methods, they can have partial implementation too.

### Syntax:

```java
interface Animal {
    void sound();  // public and abstract by default
}
```

### Implementing Class:

```java
class Dog implements Animal {
    public void sound() {
        System.out.println("Bark");
    }
}
```

### Usage:

```java
Animal a = new Dog();
a.sound();
```

---

## 6. Interface Capabilities

An interface can contain:

* Abstract methods
* **default methods** (Java 8+)
* **static methods** (Java 8+)
* **private methods** (Java 9+)
* Constants (`public static final`)

### Example:

```java
interface Car {
    void drive();  // abstract

    default void stop() {
        System.out.println("Car stopped");
    }

    static void service() {
        System.out.println("Car servicing");
    }
}
```

---

## 7. Abstract Class vs Interface

| Feature              | Abstract Class                     | Interface                                     |
| -------------------- | ---------------------------------- | --------------------------------------------- |
| Methods              | Abstract + concrete                | Abstract (default, static)                    |
| Variables            | Any type                           | public static final only                      |
| Constructors         | Allowed                            | Not allowed                                   |
| Multiple inheritance | Child can extend only one          | Class can implement multiple interfaces       |
| When to use          | When some implementation is shared | For full abstraction or contract-based design |

---

## 8. Real-World Example Using Abstraction

### Example: Payment System

**Abstract Layer:**

```java
abstract class Payment {
    abstract void pay(double amount);

    void generateReceipt() {
        System.out.println("Receipt generated.");
    }
}
```

**Concrete Implementations:**

```java
class UPI extends Payment {
    void pay(double amount) {
        System.out.println("Paid via UPI: " + amount);
    }
}

class CreditCard extends Payment {
    void pay(double amount) {
        System.out.println("Paid via Credit Card: " + amount);
    }
}
```

**Usage:**

```java
Payment p = new UPI();
p.pay(500);
p.generateReceipt();
```

---

## 9. Memory View of Abstraction

```
Reference Type: Payment (abstract)
Actual Object: UPI (concrete)

Allowed: p.pay()
         p.generateReceipt()

Not Allowed: Methods not defined in abstract class/interface
```

---

## 10. Common Interview Questions

### Q1: Can we create an object of an abstract class?

No. But you can create references of abstract type.

### Q2: Can an abstract class contain a constructor?

Yes, used for initialization.

### Q3: Can an interface contain a constructor?

No.

### Q4: Difference between abstract class and interface?

* Abstract class → partial abstraction
* Interface → full abstraction (mostly)

### Q5: Can abstract class have static methods?

Yes.

### Q6: Can the abstract method be private?

No. It must be overridden by child class.

---

## Key Takeaways

* Abstraction hides implementation and exposes only essential behavior.

* Achieved using **abstract classes** and **interfaces**.

* Abstract classes allow partial abstraction; interfaces allow full abstraction.

* Promotes modular, scalable, and loosely coupled design.

* Real-world Java frameworks (Spring, JDBC, Servlet API) heavily rely on abstraction.

---


