# Working of LinkedHashMap and TreeMap

## 1. Overview

`LinkedHashMap` and `TreeMap` are two important **Map implementations** in Java that extend the capabilities of `HashMap` by providing ordering guarantees.

They are part of:

```java
java.util
```

Both store data as **key–value pairs**, but they differ in:

* ordering behavior
* internal data structure
* performance
* use cases

These maps are frequently used in **backend systems**, especially in caching, LRU design, sorting data, and ordered API responses.

---

## 2. LinkedHashMap

### 2.1 What is LinkedHashMap?

`LinkedHashMap` is a HashMap with a **doubly linked list** running through the entries.

It maintains:

* **Insertion order** by default
* Optional **access order** (used in LRU cache)

Defined as:

```java
class LinkedHashMap<K,V> extends HashMap<K,V>
```

---

### 2.2 Key Characteristics

| Feature            | Behavior                                     |
| ------------------ | -------------------------------------------- |
| Ordering           | Maintains insertion order (or access order)  |
| Null keys/values   | Allowed (one null key, multiple null values) |
| Performance        | O(1) average for operations                  |
| Internal structure | HashMap + doubly linked list                 |

---

### 2.3 Internal Working

It internally uses:

* HashMap for storage
* Doubly linked list for order tracking

Each entry node contains:

```java
Entry<K,V> before;
Entry<K,V> after;
```

This preserves order during iteration.

---

### 2.4 Example

```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("Java", 1);
map.put("Python", 2);
map.put("C++", 3);

System.out.println(map);
```

Output:

```
{Java=1, Python=2, C++=3}
```

Order is preserved.

---

### 2.5 Access-Order Mode (LRU Cache Behavior)

LinkedHashMap can maintain **access order** instead of insertion order:

```java
Map<Integer, String> lru = new LinkedHashMap<>(16, 0.75f, true);
```

Now most recently accessed entry moves to the end.

This is used to implement **LRU Cache**.

---

### 2.6 Real-World Backend Use Case

#### Implementing Simple LRU Cache

```java
class LRUCache<K,V> extends LinkedHashMap<K,V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
        return size() > capacity;
    }
}
```

Usage:

```java
LRUCache<Integer, String> cache = new LRUCache<>(3);
cache.put(1, "A");
cache.put(2, "B");
cache.put(3, "C");
cache.get(1);
cache.put(4, "D"); // removes least recently used
```

This is used in:

* API rate limiters
* session cache
* database query caching
* token stores

---

## 3. TreeMap

### 3.1 What is TreeMap?

`TreeMap` stores keys in **sorted order**.

Sorting is based on:

* natural ordering
* or custom Comparator

Internally uses:

* **Red-Black Tree**

Defined as:

```java
class TreeMap<K,V> implements NavigableMap<K,V>
```

---

### 3.2 Key Characteristics

| Feature            | Behavior                      |
| ------------------ | ----------------------------- |
| Ordering           | Sorted (ascending by default) |
| Null key           | Not allowed                   |
| Null values        | Allowed                       |
| Internal structure | Red-Black Tree                |
| Performance        | O(log n)                      |

---

### 3.3 Example

```java
Map<Integer, String> map = new TreeMap<>();
map.put(3, "C");
map.put(1, "A");
map.put(2, "B");

System.out.println(map);
```

Output:

```
{1=A, 2=B, 3=C}
```

---

### 3.4 Custom Comparator Example

```java
Map<String, Integer> map =
        new TreeMap<>(Comparator.reverseOrder());

map.put("Apple", 1);
map.put("Banana", 2);
map.put("Cherry", 3);
```

Order becomes descending:

```
{Cherry=3, Banana=2, Apple=1}
```

---

### 3.5 Real-World Backend Use Case

#### Example: Sorted leaderboard / rankings

```java
TreeMap<Integer, String> leaderboard = new TreeMap<>();

leaderboard.put(90, "John");
leaderboard.put(95, "Emma");
leaderboard.put(85, "Liam");
```

We can fetch:

* Top scorer
* Students in score range
* Next higher score

```java
leaderboard.lastEntry();   // highest score
leaderboard.firstEntry();  // lowest score
leaderboard.subMap(80, 95);
```

Used in:

* ranking systems
* financial order books
* scheduling
* priority ranking services

---

## 4. Performance Comparison

| Operation            | HashMap    | LinkedHashMap            | TreeMap        |
| -------------------- | ---------- | ------------------------ | -------------- |
| get/put/remove       | O(1) avg   | O(1) avg                 | O(log n)       |
| Maintains order?     | No         | Yes                      | Yes (sorted)   |
| Null keys            | 1 allowed  | 1 allowed                | Not allowed    |
| Underlying structure | Hash table | Hash table + linked list | Red-black tree |

---

## 5. When to Use What

### Use LinkedHashMap when:

* order of keys matters
* predictable iteration required
* implementing LRU cache
* preserving insertion order in API responses

### Use TreeMap when:

* you need sorted keys
* range queries are required
* floor/ceiling navigation required

TreeMap supports navigation methods like:

```
higherKey()
lowerKey()
ceilingEntry()
floorEntry()
subMap()
headMap()
tailMap()
```

---

## 6. Internal Working Summary

### LinkedHashMap

* hash table + doubly linked list
* O(1) operations
* preserves order

### TreeMap

* self-balancing red-black tree
* O(log n) operations
* keeps sorted order

---

## 7. Interview Questions

### Q1: Difference between LinkedHashMap and TreeMap?

* LinkedHashMap maintains insertion/access order
* TreeMap maintains sorted order
* TreeMap gives O(log n)
* LinkedHashMap gives O(1)

---

### Q2: Can TreeMap contain null keys?

No — because sorting requires comparison.

---

### Q3: Which is best for LRU cache?

LinkedHashMap (access-order mode).

---

### Q4: Which map is fastest?

HashMap ≥ LinkedHashMap >> TreeMap

---

### Q5: Why TreeMap is slower?

Because it uses **Red-Black Tree rotations** during insert, delete, lookup.

---

## Key Takeaways

* LinkedHashMap preserves order of insertion or access.

* TreeMap stores keys in sorted order.

* LinkedHashMap suitable for caches.

* TreeMap suitable for range queries and sorted data.

* TreeMap operations are O(log n), LinkedHashMap are O(1).

* Both are widely used in backend systems.

---
