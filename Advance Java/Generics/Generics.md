# Generics in Java

## 1. What Are Generics?

**Generics** allow Java classes, interfaces, and methods to operate on **a specific data type** without losing type safety.

Before generics (Java 1.4), collections stored **Object**, requiring manual casting and risking runtime errors.
Generics provide **compile-time type checking** and **eliminate casting**.

Example without generics:

```java
ArrayList list = new ArrayList();
list.add("Java");
String s = (String) list.get(0); // manual cast
```

With generics:

```java
ArrayList<String> list = new ArrayList<>();
String s = list.get(0);  // no cast needed
```

---

## 2. Why Use Generics? (Advantages)

### 1. Compile-time Type Safety

Prevents inserting wrong types.

```java
ArrayList<Integer> list = new ArrayList<>();
// list.add("Hello"); // compile error
```

### 2. Avoids ClassCastException

### 3. Code Reusability

One class works for multiple data types.

### 4. Cleaner, more readable code

No need for explicit casting.

---

## 3. Generic Classes

A class can be made generic using type parameters.

```java
class Box<T> {
    private T value;

    public void set(T value) { this.value = value; }
    public T get() { return value; }
}
```

Usage:

```java
Box<Integer> b1 = new Box<>();
b1.set(10);

Box<String> b2 = new Box<>();
b2.set("Hello");
```

---

## 4. Generic Methods

Methods can declare their own type parameters.

```java
public <T> void print(T value) {
    System.out.println(value);
}
```

Usage:

```java
print(10);
print("Java");
print(5.6);
```

---

## 5. Multiple Type Parameters

```java
class Pair<K, V> {
    K key;
    V value;
}
```

Usage:

```java
Pair<Integer, String> p = new Pair<>();
```

---

## 6. Bounded Generics (Upper Bound)

Used to restrict type parameters.

### Upper bound (`extends`)

```java
public <T extends Number> void printNumber(T n) {
    System.out.println(n);
}
```

Now T can be: `Integer`, `Double`, `Float`, etc.

---

## 7. Bounded Generics (Lower Bound)

### Lower bound (`super`)

```java
public void addNumbers(List<? super Integer> list) {
    list.add(10); // allowed
}
```

Means: list can be `List<Integer>`, `List<Object>`, but **not** `List<Double>`.

---

## 8. Wildcards (`?`)

A wildcard represents an unknown type.

---

## 8.1 Unbounded Wildcard

```java
void printList(List<?> list) {
    for (Object o : list)
        System.out.println(o);
}
```

Can accept any type of list.

---

## 8.2 Upper Bounded Wildcard

```java
List<? extends Number>
```

Means the list contains Number or subclasses.
Used for **reading** values.

---

## 8.3 Lower Bounded Wildcard

```java
List<? super Integer>
```

Means list contains Integer or its superclasses.
Used for **writing** values.

---

### Summary of Wildcards

| Wildcard        | Meaning      | Best For     |
| --------------- | ------------ | ------------ |
| `<?>`           | unknown type | reading only |
| `<? extends T>` | T or child   | reading      |
| `<? super T>`   | T or parent  | writing      |

---

## 9. Type Erasure (Important for Interviews)

Generics exist only during **compile time**.
At runtime, Java removes generic type information — this is called **type erasure**.

Example:

```java
ArrayList<Integer> a = new ArrayList<>();
ArrayList<String> b = new ArrayList<>();
```

At runtime, both are treated as `ArrayList`.

Because of type erasure:

* Cannot create generic arrays: `T[] arr = new T[5];` ❌
* Cannot use primitives as type parameters: `ArrayList<int>` ❌
* Cannot check type with `instanceof`: `if (obj instanceof List<String>)` ❌

---

## 10. Restrictions on Generics

### 1. Cannot Instantiate Type Parameter

```java
T obj = new T(); // ❌ not allowed
```

### 2. Cannot Create Generic Arrays

```java
List<String>[] arr = new List<String>[10]; // ❌
```

### 3. Primitives Not Allowed

```java
List<int> list; // ❌
```

Use wrapper classes:

```java
List<Integer> list;
```

---

## 11. Generic Interface

```java
interface Container<T> {
    T process(T item);
}
```

---

## 12. Generic Constructor

```java
class Demo {
    <T> Demo(T value) {
        System.out.println(value);
    }
}
```

---

## 13. Use of Generics in Collections API

| Class     | Generic Form    |
| --------- | --------------- |
| ArrayList | `ArrayList<E>`  |
| HashMap   | `HashMap<K, V>` |
| HashSet   | `HashSet<E>`    |
| TreeMap   | `TreeMap<K, V>` |

Example:

```java
HashMap<Integer, String> map = new HashMap<>();
```

---

## 14. Real World Examples

### 14.1 Custom Box

```java
Box<String> box = new Box<>();
```

### 14.2 Sorting with Comparator

```java
Comparator<String> comp = Comparator.naturalOrder();
```

### 14.3 Stack of custom object

```java
Stack<Employee> stack = new Stack<>();
```

---

## 15. Interview Questions

### Q1: What are generics?

A way to write type-safe, reusable code using type parameters.

### Q2: Difference between `<? extends T>` and `<? super T>`?

* `extends` → upper bound (read-only)
* `super` → lower bound (write allowed)

### Q3: Why are generics not available at runtime?

Due to **type erasure** for backward compatibility.

### Q4: Why can't we use primitives in generics?

Because generics work only with objects, not primitives.

### Q5: Can a class be both generic class and have generic methods?

Yes.

---

## Key Takeaways

* Generics allow type-safe programming with compile-time checking.

* Eliminates the need for casting.

* Wildcards (`?`, extends, super) allow flexible type usage.

* Type erasure removes generic info at runtime.

* Collections heavily rely on generics.

* Helps build reusable classes and methods.

---
