# Set in Java

## 1. What Is a Set?

A **Set** is a collection that **does not allow duplicate elements**.
It models the mathematical set abstraction.

Defined in:

```java
java.util.Set
```

The Set interface extends `Collection` and provides implementations that differ in:

* Ordering
* Performance
* Sorting behavior
* Null allowance

---

## 2. Key Characteristics of Set

| Feature                      | Description                                        |
| ---------------------------- | -------------------------------------------------- |
| Duplicates                   | ❌ Not allowed                                      |
| Null values                  | Allowed in HashSet & LinkedHashSet, not in TreeSet |
| Order maintained?            | Depends on implementation                          |
| Index-based access?          | ❌ No                                               |
| Uses hashing/tree structures | Yes (depending on implementation)                  |

---

## 3. Set Implementations

The main implementations are:

1. **HashSet**
2. **LinkedHashSet**
3. **TreeSet**

---

## 4. HashSet (Most Used)

### 4.1 Overview

* Stores elements in **un-ordered** form
* Uses **hashing** internally
* Allows **1 null** value
* Best performance among all Set types

### 4.2 Internal Working

* Uses **HashMap** internally
* Elements stored as **keys**
* Dummy value is `PRESENT`

### 4.3 Example

```java
Set<String> set = new HashSet<>();
set.add("Apple");
set.add("Banana");
set.add("Apple"); // ignored
```

### Output:

```
[Banana, Apple]  (order may vary)
```

### 4.4 Time Complexity

| Operation | Time         |
| --------- | ------------ |
| add       | O(1) average |
| remove    | O(1)         |
| contains  | O(1)         |
| iteration | O(n)         |

---

## 5. LinkedHashSet (Insertion Order Maintained)

### 5.1 Overview

* Maintains **insertion order**
* Uses **LinkedHashMap** internally
* Allows **1 null** value
* Slightly slower than HashSet

### 5.2 Example

```java
Set<String> set = new LinkedHashSet<>();
set.add("Java");
set.add("Python");
set.add("C++");
```

### Output:

```
[Java, Python, C++]
```

### 5.3 Use When:

* Order matters
* You need HashSet performance with predictable traversal

---

## 6. TreeSet (Sorted Set)

### 6.1 Overview

* Sorted in **ascending (natural) order**
* Uses **TreeMap (Red-Black Tree)** internally
* Does **not** allow null
* Slower than HashSet & LinkedHashSet

### 6.2 Example

```java
Set<Integer> set = new TreeSet<>();
set.add(30);
set.add(10);
set.add(20);
```

### Output:

```
[10, 20, 30]
```

### 6.3 Custom Comparator

```java
Set<String> set = new TreeSet<>(Comparator.reverseOrder());
```

---

## 7. Differences Between HashSet, LinkedHashSet, TreeSet

| Feature            | HashSet     | LinkedHashSet         | TreeSet            |
| ------------------ | ----------- | --------------------- | ------------------ |
| Order              | No          | Insertion order       | Sorted order       |
| Performance        | Fastest     | Medium                | Slowest            |
| Null allowed       | Yes         | Yes                   | No                 |
| Internal Structure | HashMap     | LinkedHashMap         | TreeMap            |
| Use When           | Fast lookup | Fast + maintain order | Sorted data needed |

---

## 8. Common Set Methods

```java
add(E e)
remove(Object o)
contains(Object o)
isEmpty()
size()
clear()
iterator()
```

### Example:

```java
Set<Integer> set = new HashSet<>();
set.add(1);
set.add(2);

System.out.println(set.contains(2));   // true
```

---

## 9. Iterating Over a Set

### Using Iterator

```java
Iterator<String> it = set.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}
```

### Enhanced for-loop

```java
for (String s : set) {
    System.out.println(s);
}
```

### Using forEach (Java 8+)

```java
set.forEach(System.out::println);
```

---

## 10. Removing Duplicates from a List Using Set

```java
List<Integer> list = Arrays.asList(1, 2, 2, 3, 1);
Set<Integer> set = new HashSet<>(list);

System.out.println(set);  // [1, 2, 3]
```

---

## 11. Set Operations (Union, Intersection, Difference)

### Union

```java
set1.addAll(set2);
```

### Intersection

```java
set1.retainAll(set2);
```

### Difference

```java
set1.removeAll(set2);
```

---

## 12. HashSet Internal Working (Important)

1. HashSet uses `hashCode()` to compute bucket index

2. If collision happens → stored in a **LinkedList** or **TreeNode**

3. Java 8+ converts long collision chains to **balanced trees**

4. `equals()` is used to check duplicates

---

## 13. Common Interview Questions

### Q1: Why Set does not allow duplicates?

Because it checks equality using `equals()` and `hashCode()`.

---

### Q2: Which Set is ordered?

`LinkedHashSet` and `TreeSet`.

---

### Q3: Why is HashSet fastest?

Because hashing provides **O(1)** operations on average.

---

### Q4: Can TreeSet store null?

No, because it needs to compare elements.

---

### Q5: Which Set maintains sorted order?

`TreeSet`.

---

### Q6: What happens if you add duplicate elements?

They are ignored.

---

## Key Takeaways

* Set does **not** allow duplicates.

* Use `HashSet` for fast access.

* Use `LinkedHashSet` for predictable iteration.

* Use `TreeSet` for sorted data.

* Internal workings depend on HashMap/TreeMap.

* No indexing → must use iterators for traversal.

---
