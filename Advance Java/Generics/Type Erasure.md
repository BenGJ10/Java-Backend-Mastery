# Type Erasure in Java

## 1. What Is Type Erasure?

**Type Erasure** is the process by which the Java compiler removes all **generic type information** during compilation.
This means generics exist only at **compile-time**, not at **runtime**.

After compilation, all generic types (`<T>`, `<K,V>`, `<E>`, wildcards) are replaced with:

* Their **upper bound** (if specified), or
* **Object** (if no bound exists)

This ensures **backward compatibility** with older (pre-Java 5) JVMs.

---

## 2. Why Does Java Use Type Erasure?

### 1. **Backward Compatibility**

Java 5 introduced generics, but older JVMs didn’t understand them.
Type erasure allows compiled bytecode to run on older JVMs.

### 2. **No Runtime Overhead**

JVM doesn’t need additional logic for generics.

---

## 3. What Happens During Type Erasure?

### Example Generic Class

```java
class Box<T> {
    T value;
    void set(T value) { this.value = value; }
    T get() { return value; }
}
```

### After Type Erasure:

```java
class Box {
    Object value;
    void set(Object value) { this.value = value; }
    Object get() { return value; }
}
```

* `T` → replaced with `Object`
* Type checks enforced at compile-time only

---

## 4. Type Erasure with Bounded Types

### Example:

```java
class NumberBox<T extends Number> {
    T value;
    void set(T value) { this.value = value; }
}
```

After type erasure, **T is replaced with its upper bound `Number`**.

```java
class NumberBox {
    Number value;
    void set(Number value) { this.value = value; }
}
```

---

## 5. Type Erasure in Methods with Generics

```java
public <T> void display(T value) {
    System.out.println(value);
}
```

After erasure:

```java
public void display(Object value) {
    System.out.println(value);
}
```

---

## 6. Bridge Methods (Important Interview Concept)

Due to type erasure, Java sometimes generates **bridge methods** to maintain polymorphism.

### Example:

```java
class Parent<T> {
    T show(T t) { return t; }
}

class Child extends Parent<String> {
    @Override
    String show(String s) { return s; }
}
```

After erasure:

`Parent`'s method becomes:

```java
Object show(Object o);
```

`Child`'s method becomes:

```java
String show(String s); // actual override
```

JVM also generates:

```java
Object show(Object o) { return show((String) o); } // bridge method
```

---

## 7. Type Erasure Leads to Restrictions

Because generics are erased at runtime, the following things are **not allowed**.

---

### 7.1 Cannot Create Generic Arrays

```java
List<String>[] arr = new List<String>[10];  // ❌ error
```

Reason: JVM cannot track real type of array elements at runtime.

---

### 7.2 Cannot Use Primitive Types in Generics

```java
List<int> list; // ❌
```

Reason: Generics require reference types after erasure.

Use wrappers:

```java
List<Integer> list;
```

---

### 7.3 Cannot Check Generic Types with instanceof

```java
if (obj instanceof List<String>) { }  // ❌ not allowed
```

Because at runtime, type becomes simply `List`.

Correct:

```java
if (obj instanceof List<?>) { }
```

---

### 7.4 Cannot Overload Methods Differing Only by Generic Parameters

```java
void test(List<String> l) {}
void test(List<Integer> l) {}  // ❌ compile error
```

After erasure both become:

```java
void test(List l)
```

---

## 8. Real Example Showing Type Erasure

```java
ArrayList<Integer> list1 = new ArrayList<>();
ArrayList<String> list2 = new ArrayList<>();
```

At runtime:

```java
list1.getClass() == list2.getClass()  // true
```

Because type info is erased, both are just `ArrayList`.

---

## 9. Summary of Type Erasure Effects

| Concept                          | Behavior After Erasure             |
| -------------------------------- | ---------------------------------- |
| Generic Types                    | Converted to Object or upper bound |
| Generic Methods                  | Converted to method taking Object  |
| Bounded Generics                 | Type replaced with upper bound     |
| Runtime                          | No generic info available          |
| Arrays of Generics               | Not allowed                        |
| instanceof with generics         | Not allowed                        |
| Method overloading with generics | Conflicts after erasure            |

---

## 10. Why Type Erasure Matters 

* JVM does not know about generics → only compiler does.
* Ensures backward compatibility with older JVMs.
* Causes limitations (generic arrays, instanceof, overload conflicts).
* Leads to **bridge methods** for preserving polymorphism.

---

## Key Takeaways

* Generics exist **only at compile-time**; erased at runtime.

* Type parameters are replaced with **Object** or **their upper bounds**.

* Because of erasure:

  * Cannot create generic arrays
  * Cannot use primitives in generics
  * Cannot use instanceof with generic types
  * Overloading by generic type alone is impossible
* Bridge methods are auto-created to maintain method overriding compatibility.

---

