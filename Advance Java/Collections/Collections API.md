# Collections API in Java

## 1. What Is the Collections API?

The **Collections API** is a set of **interfaces**, **classes**, and **utility methods** in `java.util` package that help store, manipulate, and manage groups of objects efficiently.

The core components include:

* **Interfaces** (Collection, List, Set, Queue, Map)

* **Implementations** (ArrayList, HashSet, HashMap, etc.)

* **Utility Classes** (Collections, Arrays)

* **Algorithms** (sorting, searching, shuffling, reversing)

The Collections API standardizes how data structures behave in Java.

---

## 2. Hierarchy of the Collections API

There are **two main branches**:

### Branch 1 → Collection (Interface)

```
Iterable
   └── Collection
         ├── List
         ├── Set
         └── Queue
```

### Branch 2 → Map (Separate hierarchy)

```
Map
   ├── HashMap
   ├── LinkedHashMap
   ├── TreeMap
   └── Hashtable
```

---

## 3. Core Interfaces in the Collections API

---

### 3.1 Collection Interface (Root Interface)

`Collection<E>` is the **superinterface** for List, Set, and Queue.

Common methods:

```java
add(E e)
remove(E e)
size()
clear()
contains(Object o)
isEmpty()
iterator()
```

---

### 3.2 List Interface (Ordered, allows duplicates)

Implementations:

| Class          | Characteristics                             |
| -------------- | ------------------------------------------- |
| **ArrayList**  | Dynamic array, fast random access           |
| **LinkedList** | Doubly linked list, fast insertion/deletion |
| **Vector**     | Legacy, synchronized                        |
| **Stack**      | Legacy LIFO structure                       |

---

### 3.3 Set Interface (No duplicates)

Implementations:

| Class             | Ordering        | Null Allowed | Notes                 |
| ----------------- | --------------- | ------------ | --------------------- |
| **HashSet**       | No order        | Yes          | Fastest               |
| **LinkedHashSet** | Insertion order | Yes          | Predictable iteration |
| **TreeSet**       | Sorted          | No           | Uses Red-Black Tree   |

---

### 3.4 Queue Interface (FIFO)

Implementations:

| Class             | Purpose                   |
| ----------------- | ------------------------- |
| **PriorityQueue** | Priority-based retrieval  |
| **ArrayDeque**    | Fast stack/queue          |
| **LinkedList**    | Can act as Queue or Deque |

---

### 3.5 Map Interface (Key → Value pairs)

Not a child of Collection.

Implementations:

| Class                 | Ordering  | Null Keys | Thread-safe?           |
| --------------------- | --------- | --------- | ---------------------- |
| **HashMap**           | No        | Yes       | No                     |
| **LinkedHashMap**     | Insertion | Yes       | No                     |
| **TreeMap**           | Sorted    | No        | No                     |
| **Hashtable**         | No        | No        | Yes                    |
| **ConcurrentHashMap** | No        | No        | Yes (High performance) |

---

## 4. Important Classes in the Collections API

---

## 4.1 ArrayList

```java
List<String> list = new ArrayList<>();
```

* Fast for random access
* Slow for insertions (shifts elements)

---

## 4.2 LinkedList

```java
LinkedList<Integer> ll = new LinkedList<>();
```

* Best for frequent insert/delete
* Slow for random access

---

## 4.3 HashSet

```java
Set<Integer> set = new HashSet<>();
```

* Stores unique elements
* Backed by **HashMap**

---

## 4.4 HashMap

```java
Map<Integer, String> map = new HashMap<>();
```

* Key–value storage
* Very fast (O(1) average)

---

## 5. Iterator and ListIterator

### Iterator (for all collections)

```java
Iterator<String> it = list.iterator();
while(it.hasNext()) {
    System.out.println(it.next());
}
```

### ListIterator (for List only, bidirectional)

```java
ListIterator<String> it = list.listIterator();
```

Supports `previous()`, `add()`, `set()`.

---

## 6. Fail-Fast vs Fail-Safe Iterators

### Fail-Fast

* Throw **ConcurrentModificationException**
* Used in: ArrayList, HashMap, HashSet

```java
for(String s : list) {
    list.add("x"); // causes ConcurrentModificationException
}
```

### Fail-Safe

* Do NOT throw exception
* Work on clone
* Used in: ConcurrentHashMap, CopyOnWriteArrayList

---

## 7. Collections Utility Class (Important)

`java.util.Collections` provides helper methods.

### Common Methods:

```java
Collections.sort(list);
Collections.reverse(list);
Collections.shuffle(list);
Collections.max(list);
Collections.min(list);
Collections.binarySearch(list, key);
Collections.synchronizedList(list);
```

---

## 8. Comparable vs Comparator (Used in Collections API)

### Comparable (natural ordering)

Implement inside the class:

```java
class Student implements Comparable<Student> {
    public int compareTo(Student s) {
        return this.age - s.age;
    }
}
```

### Comparator (custom ordering)

```java
Comparator<Student> byName = (a, b) -> a.name.compareTo(b.name);
Collections.sort(list, byName);
```

---

## 9. Generics in the Collections API

Collections use **generics** for type safety.

```java
List<String> names = new ArrayList<>();
```

Prevents runtime `ClassCastException`.

---

## 10. Differences Between Major Implementations

---

### List Implementations

| Feature       | ArrayList | LinkedList   |
| ------------- | --------- | ------------ |
| Access        | Fast      | Slow         |
| Insert/delete | Slow      | Fast         |
| Structure     | Array     | Linked nodes |

---

### Set Implementations

| Feature | HashSet | LinkedHashSet | TreeSet |
| ------- | ------- | ------------- | ------- |
| Order   | No      | Insertion     | Sorted  |
| Speed   | Fastest | Medium        | Slowest |

---

### Map Implementations

| Feature | HashMap | LinkedHashMap | TreeMap |
| ------- | ------- | ------------- | ------- |
| Order   | None    | Insertion     | Sorted  |
| Speed   | Fast    | Medium        | Slow    |

---

## 11. Real-World Examples

---

### Example 1: Word Count Using HashMap

```java
Map<String, Integer> count = new HashMap<>();

for (String word : words) {
    count.put(word, count.getOrDefault(word, 0) + 1);
}
```

---

### Example 2: Sorting a List

```java
List<Integer> list = Arrays.asList(5, 2, 8, 1);
Collections.sort(list);
```

---

### Example 3: Using PriorityQueue

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(30);
pq.add(10);
pq.add(20);

System.out.println(pq.poll()); // 10
```

---

## 12. When to Use What? (Quick Guide)

| Requirement           | Use                       |
| --------------------- | ------------------------- |
| Fast search           | HashMap / HashSet         |
| Maintain order        | ArrayList / LinkedHashMap |
| Remove/add frequently | LinkedList                |
| Sorted data           | TreeSet / TreeMap         |
| Thread-safe list      | CopyOnWriteArrayList      |
| Thread-safe map       | ConcurrentHashMap         |
| Queue/stack           | ArrayDeque                |

---

## Key Takeaways

* Collections API = interfaces + classes + algorithms.

* List, Set, Queue extend **Collection**, but Map is separate.

* Choose implementation based on performance and ordering needs.

* Use `Collections` utility class for sorting, reversing, searching.

* Understand differences between HashMap, TreeMap, LinkedHashMap.

* Iterator vs ListIterator vs fail-fast vs fail-safe is important for interviews.

* Generics give type safety to collections.

---