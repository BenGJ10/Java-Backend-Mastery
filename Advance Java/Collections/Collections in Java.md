# Collections in Java

## 1. What Is the Java Collections Framework?

The **Java Collections Framework (JCF)** is a set of **interfaces**, **classes**, and **algorithms** that provide ready-made data structures for storing and manipulating groups of objects.

It includes:

* **Interfaces** (List, Set, Map, Queue, etc.)

* **Implementations** (ArrayList, HashSet, HashMap, LinkedList, etc.)

* **Utility Classes** (Collections, Arrays)

Collections help with:

* Storing data
* Searching
* Sorting
* Insertion & deletion
* Manipulating dynamic data

---

## 2. Why Collections? (Advantages)

Before Collections (Java 1.2), only arrays existed — fixed size and primitive-friendly only.

Collections provide:

* **Dynamic size growth**
* **Built-in algorithms** (sorting, searching)
* **Thread-safe variants**
* **Better performance structures**
* **Consistent interfaces across data structures**

---

## 3. Collection vs Collections (Important)

| Term            | Meaning                                                         |
| --------------- | --------------------------------------------------------------- |
| **Collection**  | Root interface for all collection types                         |
| **Collections** | Utility class with static methods (sorting, reversing, syncing) |

Example:

```java
Collections.sort(list);
```

---

## 4. Collection Hierarchy

![Collections in Java](images/collections_in_java.png)

---

## 5. List Interface (Ordered, Allows Duplicates)

Stores elements in insertion order and allows duplicates.

### Common Implementations:

| Class          | Internal Structure | Allows Duplicates | Thread Safe | Best Use                  |
| -------------- | ------------------ | ----------------- | ----------- | ------------------------- |
| **ArrayList**  | Dynamic array      | Yes               | No          | Fast read, random access  |
| **LinkedList** | Doubly linked list | Yes               | No          | Frequent inserts/removals |
| **Vector**     | Dynamic array      | Yes               | Yes         | Legacy, thread-safe       |
| **Stack**      | Vector-based       | Yes               | Yes         | Legacy stack operations   |

---

### 5.1 ArrayList Example

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("A"); // duplicates allowed
```

---

### 5.2 LinkedList Example

```java
LinkedList<Integer> ll = new LinkedList<>();
ll.add(10);
ll.addFirst(5);
ll.addLast(20);
```

---

## 6. Set Interface (No Duplicates)

Set does not allow duplicate values.

### Implementations:

| Class             | Order                     | Allows Null | Best Use              |
| ----------------- | ------------------------- | ----------- | --------------------- |
| **HashSet**       | No order                  | Yes         | Fast lookup           |
| **LinkedHashSet** | Maintains insertion order | Yes         | Predictable iteration |
| **TreeSet**       | Sorted (ascending)        | No          | Sorted data           |

---

### 6.1 HashSet Example

```java
Set<Integer> set = new HashSet<>();
set.add(10);
set.add(20);
set.add(10); // ignored
```

---

### 6.2 TreeSet Example

```java
Set<String> ts = new TreeSet<>();
ts.add("Banana");
ts.add("Apple");
ts.add("Mango");
```

Output (sorted):

```
Apple, Banana, Mango
```

---

## 7. Queue Interface

Used for FIFO (first-in-first-out) and other queue behaviors.

### Common Implementations:

| Class             | Description                     |
| ----------------- | ------------------------------- |
| **LinkedList**    | Can act as Queue & Deque        |
| **PriorityQueue** | Elements ordered by priority    |
| **ArrayDeque**    | Fast stack/queue implementation |

---

### 7.1 PriorityQueue Example

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(30);
pq.add(10);
pq.add(20);

System.out.println(pq.poll()); // 10 (lowest)
```

---

## 8. Map Interface (Key–Value Pairs)

Maps store data as `key → value`.
Keys must be unique.

### Implementations:

| Class                 | Order                     | Allows Null Keys | Allows Null Values | Best Use              |
| --------------------- | ------------------------- | ---------------- | ------------------ | --------------------- |
| **HashMap**           | No order                  | Yes (1 null key) | Yes                | Most common & fast    |
| **LinkedHashMap**     | Maintains insertion order | Yes              | Yes                | Cache implementations |
| **TreeMap**           | Sorted by key             | No               | No                 | Sorted dictionaries   |
| **Hashtable**         | No order, synchronized    | No               | No                 | Legacy                |
| **ConcurrentHashMap** | Thread-safe               | No               | No                 | High concurrency      |

---

### 8.1 HashMap Example

```java
Map<Integer, String> map = new HashMap<>();
map.put(1, "A");
map.put(2, "B");
map.put(1, "C"); // override
```

---

### 8.2 TreeMap Example

```java
Map<String, Integer> tm = new TreeMap<>();
tm.put("Banana", 2);
tm.put("Apple", 1);
```

Sorted automatically:

```
Apple=1, Banana=2
```

---

## 9. Collections Utility Class

`java.util.Collections` provides static helper methods.

### Common Methods:

| Method               | Purpose            |
| -------------------- | ------------------ |
| `sort()`             | Sort list          |
| `reverse()`          | Reverse list       |
| `shuffle()`          | Randomize list     |
| `min()`              | Smallest element   |
| `max()`              | Largest element    |
| `binarySearch()`     | Search sorted list |
| `synchronizedList()` | Make thread-safe   |

Example:

```java
Collections.sort(list);
Collections.reverse(list);
```

---

## 10. Iterator and ListIterator

### 10.1 Iterator

Used to traverse collection elements.

```java
Iterator<String> it = list.iterator();
while(it.hasNext()) {
    System.out.println(it.next());
}
```

---

### 10.2 ListIterator (List only)

Can traverse **forward and backward**.

```java
ListIterator<String> lit = list.listIterator();
```

---

## 11. Differences Between List, Set, Map

| Feature    | List        | Set         | Map                               |
| ---------- | ----------- | ----------- | --------------------------------- |
| Duplicates | Allowed     | Not allowed | Keys not allowed                  |
| Order      | Yes         | Depends     | No (unless LinkedHashMap/TreeMap) |
| Null       | Yes         | Yes/No      | Key: 1, Value: many               |
| Access     | Index-based | No index    | Key-based                         |

---

## 12. HashSet vs TreeSet vs LinkedHashSet

| Feature      | HashSet     | LinkedHashSet         | TreeSet     |
| ------------ | ----------- | --------------------- | ----------- |
| Ordering     | No          | Insertion order       | Sorted      |
| Speed        | Fastest     | Medium                | Slowest     |
| Null allowed | Yes         | Yes                   | No          |
| Use          | Fast lookup | Predictable iteration | Sorted data |

---

## 13. ArrayList vs LinkedList

| Feature       | ArrayList     | LinkedList         |
| ------------- | ------------- | ------------------ |
| Structure     | Dynamic array | Doubly linked list |
| Read access   | Fast          | Slow               |
| Insert/delete | Slow          | Fast               |
| Memory        | Compact       | More (pointers)    |

---

## 14. HashMap vs Hashtable vs ConcurrentHashMap

| Feature      | HashMap | Hashtable | ConcurrentHashMap      |
| ------------ | ------- | --------- | ---------------------- |
| Thread-safe  | No      | Yes       | Yes (better)           |
| Null allowed | Yes     | No        | No                     |
| Performance  | Fast    | Slow      | Fast under concurrency |

---

## 15. When to Use What?

| Requirement                   | Recommended Collection  |
| ----------------------------- | ----------------------- |
| Indexed access                | ArrayList               |
| Many insert/delete operations | LinkedList              |
| Unique elements (fast)        | HashSet                 |
| Unique elements (sorted)      | TreeSet                 |
| Key–value storage             | HashMap                 |
| Sorted key–value              | TreeMap                 |
| Thread-safe map               | ConcurrentHashMap       |
| FIFO queue                    | LinkedList / ArrayDeque |
| Priority-based                | PriorityQueue           |

---
