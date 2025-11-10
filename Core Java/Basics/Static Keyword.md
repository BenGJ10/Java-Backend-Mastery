# static Keyword in Java — Complete Guide

Java’s `static` keyword is used for **class-level members**.
It means the **member belongs to the class**, not to individual objects.

Static can be applied to:

* ✅ Variables (Static Variables)

* ✅ Methods (Static Methods)

* ✅ Blocks (Static Initialization Blocks)

* ❌ Constructors (Not allowed)

* ❌ Local variables inside methods (Not allowed)

---

## 1. Static Variable (Class Variable)

A `static` variable:

* Belongs to the **class**, not to objects

* Shared by **all objects** of the class

* Memory allocated **once** in the class area

* Useful for **common properties**


## Example

```java
class Student {
    String name;
    static String college = "NIT";  // static variable

    Student(String name) {
        this.name = name;
    }

    void display() {
        System.out.println(name + " - " + college);
    }
}

public class StaticVarDemo {
    public static void main(String[] args) {
        Student s1 = new Student("Ben");
        Student s2 = new Student("Ryan");

        s1.display();
        s2.display();
    }
}
```

**Output:**

```
Ben - NIT
Ryan - NIT
```

✅ Both objects share the same `college`.

---

## Properties of static variables

| Feature       | Description                              |
| ------------- | ---------------------------------------- |
| Memory        | Allocated once                           |
| Access        | Via class name or object                 |
| Default value | Like instance variables (0, null, false) |
| Lifecycle     | Exists until program ends                |
| Scope         | Class-level                              |

---

## Accessing static variables

```java
Student.college = "IIT";   // preferred
s1.college = "IIT";        // allowed but not recommended
```

---

## 2. Static Method

## What is a static method?

A `static` method:

* Can be called **without object creation**

* Belongs to the class

* Can access only:

  * Static variables ✅
  * Static methods ✅
  * Non-static members ❌ (requires an object)

---

## Example

```java
class MathUtil {
    static int square(int x) {    // static method
        return x * x;
    }
}

public class StaticMethodDemo {
    public static void main(String[] args) {
        int result = MathUtil.square(5);  // no object needed
        System.out.println(result);
    }
}
```


### ❌ static method cannot access non-static data directly

```java
class Test {
    int a = 10;             // non-static
    static int b = 20;      // static

    static void show() {
        // System.out.println(a);  // ❌ ERROR
        System.out.println(b);     // ✅ static allowed
    }
}
```

To access `a`, you need an object:

```java
static void show() {
    Test t = new Test();
    System.out.println(t.a);   // ✅ allowed
}
```

---

### Why static methods?

Common utility functions like:

* Math operations (e.g., `Math.max()`, `Math.sqrt()`)

* Helper methods

* Factory methods

---

## 3. Static Block

A **static block** is used to:

* Initialize static variables

* Run code **once** when the class is loaded

Syntax:

```java
static {
    // initialization code
}
```

## Example

```java
class Demo {
    static int num;

    static {
        num = 100;
        System.out.println("Static block executed");
    }

    Demo() {
        System.out.println("Constructor executed");
    }
}

public class Test {
    public static void main(String[] args) {
        Demo d = new Demo(); // static block runs before constructor
    }
}
```

**Output:**

```
Static block executed
Constructor executed
```

---

### Multiple static blocks

```java
class Test {
    static {
        System.out.println("Block 1");
    }
    static {
        System.out.println("Block 2");
    }
}
```

**Output:**

```
Block 1
Block 2
```

Execution order = top to bottom.

---

## When static members are loaded?

* When the class is loaded into JVM

* Before creating any objects

* Before the `main()` method



## Static VS Instance Members

| Feature                | Static   | Instance       |
| ---------------------- | -------- | -------------- |
| Memory                 | One copy | One per object |
| Access through class?  | ✅        | ❌              |
| Access through object? | ✅        | ✅              |
| Belongs to             | Class    | Object         |
| Needs object?          | ❌        | ✅              |

---

## Real World Example (Object Counting)

```java
class Counter {
    static int count = 0;  // shared

    Counter() {
        count++;           // increases for every object
    }
}

public class Test {
    public static void main(String[] args) {
        new Counter();
        new Counter();
        new Counter();
        System.out.println(Counter.count);
    }
}
```

**Output:**

```
3
```

---
## static method in main Java method

The `main` method is always `static` because:

* JVM calls it without creating an object

* It serves as the program entry point  

```java
public static void main(String[] args) {
    // program starts here
}
```     

---

## Interview Questions

**Q1: Why can't static methods access non-static variables?**

Static methods don’t belong to any object — non-static variables belong to objects.

**Q2: Can static blocks throw exceptions?**

Yes, but only unchecked exceptions or wrapped exceptions inside try–catch.

**Q3: Can we overload static methods?**

Yes ✅
(Overloading is compile-time polymorphism)

**Q4: Can we override static methods?**

No ❌
Static methods belong to the class, not the object → method hiding happens instead.

**Q5: When is a static block executed?**

When the class is loaded into memory.

---

## Summary Table

| Concept         | Purpose                | Runs/Exists           | Example              |
| --------------- | ---------------------- | --------------------- | -------------------- |
| static variable | shared data            | once                  | `static int count;`  |
| static method   | utility logic          | called without object | `static void show()` |
| static block    | initialize static data | class load time       | `static { … }`       |

---

## Key Takeaways

* Static members belong to **class**, not objects

* Use static when data/method is **common for all objects**

* Static blocks run **before main()**

* Static methods cannot access non-static variables directly

---