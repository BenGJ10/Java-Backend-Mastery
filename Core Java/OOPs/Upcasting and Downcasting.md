# Upcasting and Downcasting in Java

## 1. Overview

**Upcasting** and **Downcasting** are related to type conversion in inheritance.
They determine how references behave when pointing to parent or child class objects.

They are important for:

* Runtime polymorphism

* Safe type conversions

* Accessing overridden methods

---

## 2. What is Upcasting?

**Upcasting** means assigning a child class object to a parent class reference.

```
Child → Parent
```

It is **safe**, **implicit**, and always allowed.

### Syntax:

```java
Parent p = new Child();  // Upcasting
```

### Example:

```java
class Parent {
    void show() {
        System.out.println("Parent show");
    }
}

class Child extends Parent {
    void show() {
        System.out.println("Child show");
    }

    void extra() {
        System.out.println("Child extra");
    }
}

public class Demo {
    public static void main(String[] args) {
        Parent p = new Child();   // Upcasting
        p.show();                 // Calls Child's show() → runtime polymorphism
    }
}
```

Output:

```
Child show
```

### Key Points About Upcasting:

* Always **safe**.

* JVM calls **overridden methods** from the child.

* Parent reference **cannot access child-specific methods**:

```java
p.extra(); // error
```

Because reference type decides accessible members at compile time.

---

## 3. Why Upcasting is Useful?

1. **Achieves Runtime Polymorphism**

2. **Design flexible & extensible systems**

3. **Allows generic code using parent references**

Example:

```java
Animal a = new Dog();
Animal b = new Cat();
```

Same reference type → different implementations at runtime.

---

## 4. What is Downcasting?

**Downcasting** means assigning a parent class reference to a child class reference.

```
Parent → Child
```

This is **not always safe**, must be **explicit**, and can cause `ClassCastException` if invalid.

### Syntax:

```java
Child c = (Child) p;  // Downcasting
```

### Valid Downcasting Example:

```java
Parent p = new Child();  // Upcasting
Child c = (Child) p;     // Downcasting
c.extra();               // Now accessible
```

Output:

```
Child extra
```

Here, downcasting is safe because the actual object is **Child**.

---

## 5. Invalid Downcasting (Leads to Runtime Exception)

```java
Parent p = new Parent();
Child c = (Child) p;  // Runtime error
```

This compiles but fails at runtime:

```
Exception: ClassCastException
```

Because actual object is **not** a Child.

---

## 6. Type Checking Before Downcasting (Using instanceof)

To avoid `ClassCastException`, always check:

```java
if (p instanceof Child) {
    Child c = (Child) p;
    c.extra();
}
```

If the object is not an instance of Child, downcasting is not performed.

---

## 7. Memory Behavior in Upcasting and Downcasting

### Upcasting:

```
Reference type: Parent
Object type: Child (in heap)
Accessible: Only Parent methods (and overridden ones)
```

### Downcasting:

```
Reference type: Child
Object type: Child
Accessible: All Child + Parent methods
```

Diagram:

```
Parent p = new Child();

Stack:
p → reference

Heap:
[ Child Object ]
   ↓ inherits Parent members
```

When downcasted:

```
Child c = (Child) p;

Stack:
p → Child Object
c → Child Object
```

---

## 8. Practical Example — Polymorphic Array

```java
Parent[] arr = new Parent[3];
arr[0] = new Child();
arr[1] = new Child();
arr[2] = new Parent();

for (Parent p : arr) {
    if (p instanceof Child) {
        Child c = (Child) p;
        c.extra();
    }
}
```

Shows safe downcasting in real use.

---

## 9. Overriding + Upcasting + Downcasting Together

```java
class Parent {
    void show() { System.out.println("Parent show"); }
}

class Child extends Parent {
    void show() { System.out.println("Child show"); }
    void extra() { System.out.println("Extra feature"); }
}

public class Demo {
    public static void main(String[] args) {

        Parent p = new Child();   // upcasting
        p.show();                 // child show

        Child c = (Child) p;      // downcasting
        c.extra();                // child method
    }
}
```

Output:

```
Child show
Extra feature
```

---

## 10. Key Differences — Upcasting vs Downcasting

| Comparison     | Upcasting            | Downcasting                       |
| -------------- | -------------------- | --------------------------------- |
| Direction      | Child → Parent       | Parent → Child                    |
| Safety         | Safe                 | Unsafe if incorrect               |
| Syntax         | Implicit             | Explicit                          |
| Access         | Only parent methods  | Child + parent methods            |
| Used for       | Runtime polymorphism | Accessing child-specific features |
| Runtime error? | No                   | Yes (ClassCastException)          |

---

## 11. Common Interview Questions

### Q1: Why is downcasting risky?

Because the reference might not be pointing to a child object.

### Q2: Which casting supports runtime polymorphism?

Upcasting.

### Q3: What happens when overriding methods are called using upcasting?

Child method executes (dynamic dispatch).

### Q4: Why do we need instanceof?

To ensure safe downcasting.

### Q5: Can we downcast without upcasting first?

Technically yes, but it will fail unless the object is actually a child.

---

## Key Takeaways

* Upcasting is safe and automatic.

* Downcasting is explicit and must be validated.

* Runtime method execution depends on **object type**, not reference type.

* Upcasting is used for polymorphism; downcasting is used for accessing child-specific features.

* Always use `instanceof` before downcasting.

---
