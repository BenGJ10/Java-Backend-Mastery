# Static Class vs Non-Static Class in Java

## 1. Overview

In Java, **classes can be nested inside other classes**.
These nested classes are of two types:

1. **Static nested classes** → declared with `static`
2. **Non-static nested classes** → also called **inner classes**

Understanding the difference between them is crucial for writing clean, memory-efficient, and modular code.

---

## 2. Static Nested Class

A **static nested class** is a class declared inside another class using the `static` keyword.

```java
class Outer {
    static class StaticNested {
        void show() {
            System.out.println("Inside Static Nested Class");
        }
    }
}
```

### Characteristics

| Feature                          | Static Nested Class                                    |
| -------------------------------- | ------------------------------------------------------ |
| Associated With                  | Outer **class**, not its objects                       |
| Can access                       | Only **static members** of outer class                 |
| Requires Outer object to access? | ❌ No                                                   |
| Memory usage                     | More efficient (no implicit reference to outer object) |
| Instantiation                    | `Outer.StaticNested obj = new Outer.StaticNested();`   |

### Example: Usage

```java
public class Outer {
    static int x = 100;

    static class Nested {
        void display() {
            System.out.println("x = " + x); // allowed
        }
    }

    public static void main(String[] args) {
        Outer.Nested obj = new Outer.Nested();
        obj.display();
    }
}
```

---

## 3. Non-Static Nested Class (Inner Class)

A **non-static nested class**, or **inner class**, is tied to an instance of the outer class.

```java
class Outer {
    class Inner {
        void show() {
            System.out.println("Inside Inner Class");
        }
    }
}
```

### Characteristics

| Feature                | Inner Class                                      |
| ---------------------- | ------------------------------------------------ |
| Associated With        | Outer **object**                                 |
| Can access             | All members (static + non-static) of outer class |
| Requires Outer object? | ✔ Yes                                            |
| Implicit reference     | Has a hidden reference to outer object           |
| Memory usage           | Slightly heavier due to outer-object reference   |
| Instantiation          | `Outer.Inner obj = outer.new Inner();`           |

### Example: Usage

```java
public class Outer {
    int y = 50;

    class Inner {
        void display() {
            System.out.println("y = " + y); // allowed
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.display();
    }
}
```

---

## 4. Static vs Inner Class — Detailed Comparison

| Feature                                | Static Nested Class            | Inner (Non-static) Class                |
| -------------------------------------- | ------------------------------ | --------------------------------------- |
| Needs outer object?                    | ❌ No                           | ✔ Yes                                   |
| Can access outer's non-static members? | ❌ No                           | ✔ Yes                                   |
| Can access outer's static members?     | ✔ Yes                          | ✔ Yes                                   |
| Implicit reference to outer?           | ❌ No                           | ✔ Yes                                   |
| Memory footprint                       | Lower                          | Higher                                  |
| Typical Use Case                       | Helper classes, grouping logic | Closely tied behavior with outer object |

---

## 5. When to Use Static Nested Classes?

Use when:

* The nested class **does not require** access to outer class instance variables.
* To logically group related classes.
* To improve encapsulation by hiding the class from outside.
* To reduce memory usage.

### Example: Using static nested class for a Builder pattern

```java
class User {
    private String name;
    private int age;

    static class Builder {
        String name;
        int age;

        Builder setName(String name) { this.name = name; return this; }
        Builder setAge(int age) { this.age = age; return this; }

        User build() {
            return new User(name, age);
        }
    }
}
```

---

## 6. When to Use Inner (Non-Static) Classes?

Use when:

* You need access to outer class **instance fields**.
* The inner class behavior is tightly coupled with outer class objects.
* Useful for event listeners, callbacks, and GUI components.

### Example:

```java
class Car {
    private String engine = "V8";

    class EngineChecker {
        void check() {
            System.out.println("Engine: " + engine);
        }
    }
}
```

---

## 7. Practical Example Comparing Both

```java
class Computer {

    private String model = "Dell";

    // Static nested class
    static class USBPort {
        void connect() {
            System.out.println("USB Port Connected");
        }
    }

    // Inner class
    class Processor {
        void info() {
            System.out.println("Processor belongs to " + model);
        }
    }
}
```

### Usage:

```java
Computer.USBPort usb = new Computer.USBPort();  // no outer object needed
usb.connect();

Computer comp = new Computer();
Computer.Processor cpu = comp.new Processor();  // outer object required
cpu.info();
```

---

## 8. Summary Table

| Feature                              | Static Class                     | Inner Class                    |
| ------------------------------------ | -------------------------------- | ------------------------------ |
| Declared With                        | `static` keyword                 | No keyword                     |
| Belongs To                           | Class                            | Instance                       |
| Can access outer static members      | ✔ Yes                            | ✔ Yes                          |
| Can access outer instance members    | ❌ No                             | ✔ Yes                          |
| Outer instance required for creation | ❌ No                             | ✔ Yes                          |
| Common Use Case                      | Utility, builder, helper classes | Event handling, object binding |

---

## Key Takeaways

* Use **static nested classes** when inner class does **not** need outer instance data.

* Use **inner (non-static) classes** when the nested class **needs** direct access to outer object's fields.

* Inner classes carry an implicit outer object reference → more memory.

* Static classes are more modular and memory-efficient.

