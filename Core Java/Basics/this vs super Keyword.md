# `this` vs `super` in Java

## 1. What is `this`?

`this` refers to the **current object** of the class.
It is used to access:

* Current class variables

* Current class methods

* Current class constructors

* Pass current object as an argument

* Return current object

```java
class Student {
    String name;
    int age;

    Student(String name, int age) {
        this.name = name;    // using this to refer to instance variable
        this.age = age;
    }
}
```

---

## 2. Uses of `this` keyword

### 1. To differentiate between instance variables and local variables

```java
class Demo {
    int x;
    Demo(int x) {
        this.x = x;  // this.x refers to class variable
    }
}
```

---

### 2. To call current class method

```java
class Test {
    void display() { System.out.println("Display"); }

    void show() {
        this.display();  // same as display()
    }
}
```

---

### 3. To call current class constructor (constructor chaining)

```java
class Test {
    Test() {
        this(10);  // calls parameterized constructor
        System.out.println("Default Constructor");
    }

    Test(int a) {
        System.out.println("Parameterized Constructor: " + a);
    }
}
```

---

### 4. To pass current object as an argument

```java
class A {
    void m1(B obj) { System.out.println("In m1"); }

    void call() {
        B b = new B();
        b.m2(this);   // passing current object
    }
}

class B {
    void m2(A obj) { System.out.println("In m2"); }
}
```

---

### 5. To return current object (method chaining)

```java
class Builder {
    int value;

    Builder setValue(int value) {
        this.value = value;
        return this;  // returns current object
    }
}
```

---

## 3. What is `super`?

`super` refers to the **parent class** object.
Used in inheritance to access parent properties.

```java
class Parent {
    int a = 10;
}

class Child extends Parent {
    int a = 20;

    void show() {
        System.out.println(a);       // 20
        System.out.println(super.a); // 10
    }
}
```

---

## 4. Uses of `super` keyword

### 1. To access parent class variables

```java
class Parent { int x = 100; }
class Child extends Parent {
    int x = 200;
    void show() {
        System.out.println(super.x);  // parent x
    }
}
```

---

### 2. To call parent class method

```java
class Parent {
    void display() { System.out.println("Parent Method"); }
}

class Child extends Parent {
    void display() { System.out.println("Child Method"); }

    void show() {
        super.display();  // calls parent method
        display();        // calls child method
    }
}
```

---

### 3. To call parent class constructor

```java
class Parent {
    Parent() {
        System.out.println("Parent Constructor");
    }
}

class Child extends Parent {
    Child() {
        super();  // must be first statement
        System.out.println("Child Constructor");
    }
}
```

---

### 4. To call parent class parameterized constructor

```java
class Parent {
    Parent(int x) {
        System.out.println("Parent: " + x);
    }
}

class Child extends Parent {
    Child() {
        super(50); // calls specific parent constructor
        System.out.println("Child Constructor");
    }
}
```

---

## 5. Important Rules

### ❗ Rule 1: `this()` and `super()` must be FIRST statement

```java
Child() {
    super();    // correct
    System.out.println("Hello");
}
```

```java
Child() {
    System.out.println("Hello");
    super();  // ❌ Error
}
```

---

### ❗ Rule 2: `this()` and `super()` cannot be used together

```java
Child() {
    this();   // correct
    super();  // ❌ illegal
}
```

---

### ❗ Rule 3: Constructor calls follow inheritance chain

```
Child() → Parent() → Object()
```

---

## `this` vs `super`

| Feature                 | `this`                          | `super`                            |
| ----------------------- | ------------------------------- | ---------------------------------- |
| Refers to               | Current class object            | Parent class object                |
| Used to access          | Current class variables/methods | Parent class variables/methods     |
| Calls constructor       | `this()`                        | `super()`                          |
| Used in inheritance     | ❌ Not required                  | ✅ Only used in inheritance         |
| Access overridden items | No special purpose              | ✅ Access parent overridden methods |
| Must be first statement | `this()` ✅                      | `super()` ✅                        |

---


## Real-World Combined Example

```java
class Vehicle {
    int speed = 60;

    void message() {
        System.out.println("Vehicle is running");
    }
}

class Car extends Vehicle {
    int speed = 120;

    void message() {
        System.out.println("Car is running fast");
    }

    void show() {
        System.out.println("Child Speed: " + this.speed);
        System.out.println("Parent Speed: " + super.speed);
        this.message();       // child version
        super.message();      // parent version
    }
}
```

---

## Quick Interview Nuggets

* **Q:** Why use `this`?
  To avoid confusion between instance variables and parameters.

* **Q:** Why use `super`?
  To access parent class members.

* **Q:** What happens if no `super()` is written?
  Compiler adds `super()` by default.

* **Q:** Can static methods use `this` or `super`?
  No, because static does not refer to any object instance.

* **Q:** Can we override static methods?
  No, static methods are hidden, not overridden.

---