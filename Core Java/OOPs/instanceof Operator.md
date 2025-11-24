# instanceof Operator in Java

## 1. What is the instanceof Operator?

The **`instanceof` operator** in Java is used to test whether an object is an **instance of a specific class, subclass, or interface**.

It returns a **boolean** (`true` or `false`).

### Syntax:

```java
objectReference instanceof ClassName
```

### Example:

```java
String s = "Hello";
System.out.println(s instanceof String);  // true
```

---

## 2. Why is instanceof Used?

### Main purposes:

1. **Runtime type checking**
2. **Safe downcasting** (prevents `ClassCastException`)
3. **Checking polymorphic references**
4. **Validating interface implementations**

---

## 3. instanceof with Classes

### Example:

```java
class Animal { }
class Dog extends Animal { }

Animal a = new Dog();

System.out.println(a instanceof Dog);     // true
System.out.println(a instanceof Animal);  // true
System.out.println(a instanceof Object);  // true
```

---

## 4. instanceof with Interfaces

```java
interface Shape { }
class Circle implements Shape { }

Shape s = new Circle();
System.out.println(s instanceof Circle);  // true
System.out.println(s instanceof Shape);   // true
```

---

## 5. Safe Downcasting with instanceof

Downcasting without checking can cause runtime errors:

```java
Animal a = new Animal();
Dog d = (Dog) a;  // ClassCastException
```

### Proper way using instanceof:

```java
Animal a = new Dog();

if (a instanceof Dog) {
    Dog d = (Dog) a;
    d.bark();
}
```

---

## 6. instanceof with null

If the reference is **null**, `instanceof` always returns **false**.

```java
String s = null;
System.out.println(s instanceof String);  // false
```

Because null is not an instance of anything.

---

## 7. instanceof in Inheritance Hierarchy

Consider:

```java
class A { }
class B extends A { }
class C extends B { }
```

If:

```java
A obj = new C();
```

Then:

```java
obj instanceof A   → true
obj instanceof B   → true
obj instanceof C   → true
```

Because a `C` is also a `B` and an `A`.

---

## 8. instanceof with Pattern Matching (Java 16+)

Modern Java introduced **pattern matching for instanceof**:

Instead of:

```java
if (obj instanceof String) {
    String s = (String) obj;
    System.out.println(s.toLowerCase());
}
```

You can write:

```java
if (obj instanceof String s) {
    System.out.println(s.toLowerCase());
}
```

Cleaner and avoids explicit casting.

---

## 9. instanceof and Final Classes

You cannot check instances of classes that cannot be subclassed, but instanceof still works:

```java
final class FinalClass { }

FinalClass f = new FinalClass();
System.out.println(f instanceof FinalClass);  // true
```

---

## 10. instanceof and Arrays

Arrays are also objects; you can check them:

```java
int[] arr = new int[5];
System.out.println(arr instanceof int[]);   // true
System.out.println(arr instanceof Object);  // true
```

---

## 11. Common Mistakes

### Mistake 1 — Using instanceof incorrectly in downcasting

```java
Parent p = new Parent();
Child c = (Child) p; // Wrong
```

Correct:

```java
if (p instanceof Child) {
    Child c = (Child) p;
}
```

---

### Mistake 2 — Checking superclass type incorrectly

```java
Dog d = new Dog();
System.out.println(d instanceof Animal); // true (correct)
```

---

### Mistake 3 — Using instanceof for control flow instead of polymorphism (anti-pattern)

Better to use overridden methods rather than instanceof chains.

---

## 12. Common Interview Questions

### Q1: What does instanceof check?

It checks whether an object is compatible with a given type at runtime.

### Q2: Can instanceof be used with interfaces?

Yes.

### Q3: What will `null instanceof Something` return?

Always false.

### Q4: Why use instanceof before downcasting?

To avoid `ClassCastException`.

### Q5: Does instanceof support pattern matching?

Yes, from Java 16+.

---

## Key Takeaways

* `instanceof` is used to test type compatibility at runtime.

* Essential for **safe downcasting**.

* Works with classes, interfaces, arrays, and inheritance.

* Returns false for null references.

* Modern Java allows cleaner pattern matching with instanceof.

---

