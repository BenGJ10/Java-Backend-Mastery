# ArrayList in Java

## 1. What Is ArrayList?

`ArrayList` is a **resizable, dynamic array** implementation of the `List` interface in Java.
It allows **duplicate elements**, maintains **insertion order**, and provides **fast random access**.

Located in:

```java
java.util.ArrayList
```

---

## 2. Why Use ArrayList?

Compared to traditional arrays:

| Feature     | Array              | ArrayList             |
| ----------- | ------------------ | --------------------- |
| Size        | Fixed              | Dynamic               |
| Type        | Primitives/Objects | Objects only          |
| Flexibility | Low                | High                  |
| Methods     | None               | Many built-in methods |

ArrayList is preferred when:

* Size is unknown
* Fast access is needed
* Random access is important

---

## 3. Internal Working of ArrayList (Important)

ArrayList internally uses a **dynamic array** (Object[] array).

### Default Capacity:

* Initially: **10**
* When full: Capacity grows by **1.5x**
  Formula: `newCapacity = oldCapacity + oldCapacity/2`

### Example:

Old capacity = 10
New capacity = 10 + 5 = **15**

---

## 3.1 How Expansion Works?

1. Array becomes full
2. New array (1.5x size) is created
3. Old elements copied → new array
4. The old array is discarded

This copying is expensive → avoid excessive expansion.

---

## 4. Creating an ArrayList

```java
ArrayList<String> list = new ArrayList<>();
```

With initial capacity:

```java
ArrayList<String> list = new ArrayList<>(50);
```

---

## 5. ArrayList Methods (Most Important)

### Adding Elements

```java
list.add("A");
list.add("B");
list.add(1, "C"); // insert at index
```

### Accessing

```java
String x = list.get(0);
```

### Updating

```java
list.set(1, "Z");
```

### Removing

```java
list.remove(0);       // by index
list.remove("A");     // by object
```

### Checking

```java
list.contains("X");
list.isEmpty();
list.size();
```

### Convert to Array

```java
Object[] arr = list.toArray();
```

---

## 6. Traversing an ArrayList

### Using for loop

```java
for (int i = 0; i < list.size(); i++) {
    System.out.println(list.get(i));
}
```

### Using enhanced for

```java
for (String s : list) {
    System.out.println(s);
}
```

### Using Iterator

```java
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}
```

### Using forEach (Java 8+)

```java
list.forEach(System.out::println);
```

---

## 7. ArrayList and Generics

```java
ArrayList<Integer> nums = new ArrayList<>();
nums.add(10); // autoboxing
```

Ensures type safety.

---

## 8. Performance (Big-O Complexity)

| Operation     | Time Complexity  | Notes                |
| ------------- | ---------------- | -------------------- |
| get(index)    | O(1)             | Fast random access   |
| set(index)    | O(1)             |                      |
| add(element)  | O(1) (amortized) | Expansion costs O(n) |
| add at index  | O(n)             | Shifting needed      |
| remove(index) | O(n)             | Shifting needed      |
| contains()    | O(n)             | Linear search        |
| iteration     | O(n)             |                      |

So ArrayList is **fast for reading** but **slow for inserting/deleting** in the middle.

---

## 9. Drawbacks of ArrayList

* Slow for insert/remove at middle (shifting)

* Not thread-safe

* Increases capacity automatically (memory overhead)

* Can store only **objects**, not primitives

---

## 10. Synchronized ArrayList

ArrayList is **not thread-safe**, but we can make it synchronized:

```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
```

Or use CopyOnWriteArrayList (thread-safe):

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
```

---

## 11. ArrayList vs LinkedList

| Feature              | ArrayList       | LinkedList             |
| -------------------- | --------------- | ---------------------- |
| Internal Structure   | Dynamic Array   | Doubly Linked List     |
| Access by Index      | O(1)            | O(n)                   |
| Insert/Delete Middle | Slow            | Fast                   |
| Memory               | Less            | More                   |
| When to Use          | Frequent access | Frequent insert/delete |

---

## 12. ArrayList vs Vector

| Feature      | ArrayList | Vector |
| ------------ | --------- | ------ |
| Thread-safe? | No        | Yes    |
| Performance  | Faster    | Slower |
| Growth       | 1.5x      | 2x     |
| Legacy?      | No        | Yes    |

---

## 13. Example Program (Complete Usage)

```java
import java.util.*;

public class ArrayListDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        list.add("Java");
        list.add("Python");
        list.add("C++");

        list.add(1, "JavaScript");

        list.remove("Python");

        System.out.println("Size: " + list.size());
        System.out.println("Elements:");

        for (String s : list) {
            System.out.println(s);
        }
    }
}
```

---

## 14. Interview Questions

### Q1. Why is ArrayList faster than LinkedList?

Because it provides **O(1) random access** using index.

### Q2. How does ArrayList grow internally?

By **1.5x** capacity (from Java 8 onwards).

### Q3. Is ArrayList synchronized?

No, but can be made synchronized.

### Q4. Can ArrayList store null?

Yes.

### Q5. How to avoid unnecessary resizing?

Use correct **initial capacity**.

---

## Key Takeaways

* ArrayList is a dynamic array with fast random access.

* Allows duplicates and maintains insertion order.

* Expands automatically when full.

* Not good for frequent insert/delete in mid positions.

* Use `Collections.synchronizedList` or `CopyOnWriteArrayList` for thread safety.

* Internal working uses **Object[]** and grows by **1.5x**.

---
