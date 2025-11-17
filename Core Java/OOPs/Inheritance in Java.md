# Inheritance in Java

## 1. What is Inheritance?

**Inheritance** is an OOP mechanism in Java where one class (child/subclass) acquires the properties and behaviors of another class (parent/superclass).

It allows:

* Reusability of code

* Method overriding

* Extending or customizing parent functionality

Basic syntax:

```java
class Parent { }

class Child extends Parent { }
```

The `extends` keyword establishes the parent–child relationship.

---

## 2. Why Inheritance is Needed

### Key Reasons:

1. **Code Reusability**
   Common properties can be placed in a parent class.

2. **Reduced Redundancy**
   Avoids duplicating same methods/fields in multiple classes.

3. **Method Overriding (Polymorphism)**
   Child classes can implement their own version of the same method.

4. **Extensibility**
   New features can be added with minimal changes.

---

## 3. Types of Inheritance in Java

Java supports **four** types of inheritance:

| Type                         | Supported in Java?    | Example                                |
| ---------------------------- | --------------------- | -------------------------------------- |
| **Single Inheritance**       | ✔ Supported           | `Child extends Parent`                 |
| **Multilevel Inheritance**   | ✔ Supported           | `GrandChild → Child → Parent`          |
| **Hierarchical Inheritance** | ✔ Supported           | Multiple children extending one parent |
| **Hybrid Inheritance**       | Partially             | Achieved using interfaces              |
| **Multiple Inheritance**     | ❌ Not through classes | Allowed via interfaces                 |

---

### 3.1 Single Inheritance

```java
class A {
    void show() { System.out.println("A show"); }
}

class B extends A { }

public class Demo {
    public static void main(String[] args) {
        B obj = new B();
        obj.show();
    }
}
```

---

### 3.2 Multilevel Inheritance

```java
class A { }
class B extends A { }
class C extends B { }
```

---

### 3.3 Hierarchical Inheritance

```java
class Animal { }
class Dog extends Animal { }
class Cat extends Animal { }
```

---

### 3.4 Multiple Inheritance (Not Supported Through Classes)

Java avoids it to prevent **diamond problem**.

```
   A
  / \
 B   C
  \ /
   D  ← ambiguity (which A's method?)
```

But multiple inheritance is possible using **interfaces**.

```java
interface A { void m1(); }
interface B { void m1(); }

class C implements A, B {
    public void m1() { }
}
```

---

## 4. How Inheritance Works in Memory

```
Child Object (in Heap)
+----------------------+
| parent fields        | ← inherited
| child fields         |
| parent methods       | ← inherited
| child methods        |
+----------------------+
```

Memory flow:

```java
Child obj = new Child();
```

* One single object is created in heap

* It contains **both** parent and child members

* `obj` points to the entire combined structure

---

## 5. Constructor Execution in Inheritance

Constructor calls follow the **top-down** chain.

```java
class Parent {
    Parent() {
        System.out.println("Parent constructor");
    }
}

class Child extends Parent {
    Child() {
        System.out.println("Child constructor");
    }
}
```

Output:

```
Parent constructor
Child constructor
```

### Important:

* First `super()` is called (implicitly if not written)
* Then the child constructor executes

---

## 6. Method Overriding (Runtime Polymorphism)

Inheritance enables **overriding** — child modifies parent method.

```java
class Parent {
    void show() {
        System.out.println("Parent show");
    }
}

class Child extends Parent {
    @Override
    void show() {
        System.out.println("Child show");
    }
}
```

---

## 7. Upcasting in Inheritance

Parent reference pointing to child object:

```java
Parent p = new Child();
p.show();   // calls Child's show() → runtime polymorphism
```

This enables dynamic behavior and polymorphism.

---

## 8. Access Modifiers and Inheritance

Not all members are inherited.

| Modifier    | Inherited? | Accessible in Child?        | Notes                                          |
| ----------- | ---------- | --------------------------- | ---------------------------------------------- |
| `public`    | ✔          | ✔                           | Fully accessible                               |
| `protected` | ✔          | ✔                           | Accessible within same package or via subclass |
| `default`   | ✔          | ✔ (only if in same package) | Package-private                                |
| `private`   | ❌          | ❌                           | Not inherited                                  |

Private members are not inherited, but are part of object memory.

---

## 9. The `super` Keyword in Inheritance

`super` is used to:

1. Access parent class variables
2. Access parent class methods
3. Call parent class constructor

Example:

```java
class Parent {
    int x = 10;
}

class Child extends Parent {
    int x = 20;

    void display() {
        System.out.println(super.x);  // parent variable
        System.out.println(this.x);   // child variable
    }
}
```

---

## 10. Final Keyword and Inheritance Rules

### `final class`

```java
final class A { }
class B extends A { }  // error ❌
```

### `final method`

```java
class A {
    final void show() { }
}

class B extends A {
    void show() { }  // error ❌
}
```

---

## 11. Real-World Example

### Base Class (Vehicle)

```java
class Vehicle {
    int speed;

    void move() {
        System.out.println("Vehicle is moving");
    }
}
```

### Child Class (Car)

```java
class Car extends Vehicle {
    String brand;

    @Override
    void move() {
        System.out.println(brand + " car is moving");
    }
}
```

### Usage

```java
Car c = new Car();
c.brand = "Tesla";
c.move();
```

Output:

```
Tesla car is moving
```

---

## 12. Common Interview Questions

### Q1: Why doesn’t Java support multiple inheritance through classes?

To avoid ambiguity (diamond problem).

### Q2: Are constructors inherited?

No, but child class constructors call parent constructors using `super()`.

### Q3: Are private members inherited?

They exist in the memory of the child object but are **not accessible**.

### Q4: What is object slicing?

When child object is assigned to parent reference, child-specific members cannot be accessed.

### Q5: What is the difference between inheritance and composition?

* Inheritance = “is-a” relationship
* Composition = “has-a” relationship

---

## Key Takeaways

* Inheritance allows a child class to extend a parent class.

* Promotes code reusability and runtime polymorphism.

* Constructors are executed from parent to child.

* Private members are not accessible but remain part of object.

* Multiple inheritance is restricted to avoid ambiguity but supported through interfaces.

---

