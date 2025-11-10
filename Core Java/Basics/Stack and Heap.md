# Stack and Heap in Java

## 1. What is the Stack?

The **Stack** is a memory area used for **method execution and local variable storage**.
Each thread in Java has its **own stack**, making it thread-safe.

### Characteristics:

* Stores **method frames**

* Stores **local variables**

* Stores **reference variables** (not actual objects)

* Grows and shrinks automatically with method calls

* Faster access

* Thread-specific (each thread has its own stack)

* Items stored are removed in **LIFO** order (Last-In-First-Out)

### Example:

```java
public class Demo {
    public static void main(String[] args) {
        int x = 10;               // stored in stack
        Demo obj = new Demo();    // reference stored in stack
    }
}
```

---

## 2. What is the Heap?

The **Heap** is a memory area used for **storing objects** and **object fields**.
It is shared among all threads.

### Characteristics:

* Stores **objects** and **instance variables**

* Shared across the entire application

* Requires garbage collection

* Slower compared to stack

* Large memory area compared to stack

### Example:

```java
public class Demo {
    int value = 20;   // stored in heap as part of object

    public static void main(String[] args) {
        Demo obj = new Demo();  // object stored in heap
    }
}
```

---

## 3. Stack vs Heap — Key Differences

| Feature       | Stack Memory                           | Heap Memory                         |
| ------------- | -------------------------------------- | ----------------------------------- |
| Purpose       | Stores method frames & local variables | Stores objects & instance variables |
| Access Speed  | Fast                                   | Slower                              |
| Memory Size   | Limited                                | Large                               |
| Thread Access | Thread-specific                        | Shared across all threads           |
| Storage Type  | Primitives & references                | Objects & their data                |
| Lifetime      | Based on scope                         | Until garbage collected             |
| Management    | Automatically managed                  | Managed by Garbage Collector        |
| Order         | LIFO                                   | No specific order                   |

---

## 4. How Method Calls Use Stack

Each method call pushes a **stack frame** onto the stack.

Example:

```java
public class Example {
    public static void main(String[] args) {
        fun1();    // new frame created
    }

    static void fun1() {
        int a = 100;  // stored in stack
        fun2();       // new frame created
    }

    static void fun2() {
        int b = 200;  // stored in stack
    }
}
```

**Stack structure:**

```
fun2() frame
fun1() frame
main() frame
```

After returning from a method → frame is popped.

---

## 5. How Objects Use Heap

Example:

```java
class Student {
    String name;   // stored in heap
}

public class Test {
    public static void main(String[] args) {
        Student s1 = new Student();  // object goes to heap
        Student s2 = new Student();  // another heap object
    }
}
```

**Memory layout:**

```
Stack:
s1 → reference
s2 → reference

Heap:
Object1 (name)
Object2 (name)
```

---

## 6. Garbage Collection and Heap

Garbage Collector (GC) removes objects that are **no longer referenced**.

Example:

```java
Student s = new Student();
s = null;  // eligible for GC
```

---

## 7. Common Interview Questions

### Q1: Where are static variables stored?

Static variables are stored in the **Method Area** (part of heap logically).

---

### Q2: Are objects stored in stack?

No, **only references** are stored in the stack — objects are in the heap.

---

### Q3: Are local variables stored in heap?

No, local variables always go to the **stack**.

---

### Q4: Why is stack faster than heap?

Because stack follows **LIFO**, smaller memory area, and managed automatically without GC.

---

### Q5: Why does heap need GC?

Because objects have unpredictable lifetime. GC frees unused objects.

---

## Key Takeaways

* **Stack** stores method frames, local variables, and references.

* **Heap** stores actual objects and instance variables.

* **Stack** is fast and thread-local.

* **Heap** is large, shared, and uses garbage collection.

* Objects always live in the **heap**, and references in the **stack**.

---
