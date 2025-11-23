# Inner Class and Anonymous Class

## 1. What Are Inner Classes?

An **inner class** is a class defined **inside another class**.
They are used to logically group classes, increase encapsulation, and access outer class members easily.

General syntax:

```java
class Outer {
    class Inner {
        // inner class content
    }
}
```

Inner classes can access **all members** (including private) of the outer class.

---

## 2. Types of Inner Classes in Java

Java supports **four** types of inner classes:

| Type                      | Description                       |
| ------------------------- | --------------------------------- |
| **Member Inner Class**    | Class inside another class        |
| **Static Inner Class**    | Static nested class               |
| **Local Inner Class**     | Class inside a method             |
| **Anonymous Inner Class** | Class without name (one-time use) |

---

## 3. Member Inner Class

A class declared inside another class **without static keyword**.

```java
class Outer {
    private int x = 10;

    class Inner {
        void show() {
            System.out.println("x = " + x);    // accesses private variable
        }
    }
}
```

### Creating Object of Inner Class

```java
Outer o = new Outer();
Outer.Inner i = o.new Inner();
i.show();
```

### Key Points

* Has access to all members of outer class.
* Cannot have static members unless they are final constants.

---

## 4. Static Inner Class (Nested Static Class)

Declared using `static` keyword inside another class.

```java
class Outer {
    static class Nested {
        void display() {
            System.out.println("Inside static nested class");
        }
    }
}
```

### How to Create the Object

```java
Outer.Nested n = new Outer.Nested();
n.display();
```

### Key Points

* Does **not** require outer class object.
* Can access only **static** members of outer class.
* Used like a normal top-level class but logically grouped.

---

## 5. Local Inner Class

Defined **inside a method, constructor, or block**.

```java
class Outer {
    void test() {
        class LocalInner {
            void show() {
                System.out.println("Inside local inner class");
            }
        }

        LocalInner obj = new LocalInner();
        obj.show();
    }
}
```

### Key Points

* Accessible only within the block/method where it is defined.
* Can access final or effectively final local variables prior to Java 8.
* Mostly used for event handling and encapsulation of small helper classes.

---

## 6. Anonymous Class

An **anonymous class** is a class **without a name** and defined **in a single expression**.
It is used to provide **one-time implementation** of an interface or a class.

### Example 1 — Implementing an Interface

```java
Runnable r = new Runnable() {
    public void run() {
        System.out.println("Anonymous class implementation of Runnable");
    }
};
```

### Example 2 — Extending a Class

```java
Thread t = new Thread() {
    public void run() {
        System.out.println("Anonymous subclass of Thread");
    }
};
```

### Key Points

* You cannot write constructors (no class name).
* Used when a class is needed only once.
* Commonly used in GUI, event listeners, threading.

---

## 7. Anonymous Inner Class (Combined Concept)

An **anonymous inner class** is simply an anonymous class that is also an inner class.
It is both:

* Inner (defined inside another expression/block)
* Anonymous (no name)

It is the most commonly used form of anonymous classes.

### Example:

```java
Outer obj = new Outer() {
    @Override
    void show() {
        System.out.println("Anonymous inner class overriding show()");
    }
};
```

### Example — Inside Method

```java
public void demo() {
    Runnable r = new Runnable() {
        public void run() {
            System.out.println("Anonymous inner class inside method");
        }
    };
}
```

### Essential Characteristics

* No class name
* Instantiated immediately
* Overrides method(s) from parent
* Used for **short-lived customization**

---

## 8. Differences Between Inner Class, Anonymous Class, and Anonymous Inner Class

| Feature                 | Inner Class | Anonymous Class              | Anonymous Inner Class                        |
| ----------------------- | ----------- | ---------------------------- | -------------------------------------------- |
| Name                    | Has a name  | No name                      | No name                                      |
| Defined Inside          | Class       | Expression                   | Expression/method                            |
| Usage                   | Reusable    | One-time use                 | One-time use, associated with outer instance |
| Can extend a class      | Yes         | Yes                          | Yes                                          |
| Can implement interface | Yes         | Yes                          | Yes                                          |
| Object creation         | Standard    | Simultaneous with definition | Simultaneous with definition                 |

---

## 9. Real-World Use Cases

### Member Inner Class

* When inner class needs full access to outer class members.

### Static Inner Class

* When inner class does NOT need outer class instance
* Used in Builder Patterns (e.g., `Map.Entry`, `Thread.State`)

### Anonymous Inner Class

* Event listeners (Swing, Android)
* Runnable/Thread
* Small temporary implementations

---

## 10. Practical Example: Using All Types Together

```java
class Outer {

    private int x = 5;

    // Member Inner Class
    class Inner {
        void print() { System.out.println("Inner: " + x); }
    }

    // Static Nested Class
    static class Nested {
        void show() { System.out.println("Inside static nested class"); }
    }

    void createLocalClass() {
        // Local Inner Class
        class Local {
            void test() { System.out.println("Local: " + x); }
        }
        new Local().test();
    }

    void createAnonymousClass() {
        Runnable r = new Runnable() {
            public void run() {
                System.out.println("Anonymous inner class running");
            }
        };
        r.run();
    }
}
```

---

## Key Takeaways

* Inner classes are declared inside another class.

* Anonymous classes have no name and are used for one-time implementations.

* Anonymous inner classes are anonymous classes used as inner classes.

* Member inner classes have full access to outer class members.

* Static inner classes behave like top-level classes.

* Local inner classes are used inside methods and scopes.

---
