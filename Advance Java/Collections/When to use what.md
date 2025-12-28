# When to Use Each Java Collection

## 1. List – Ordered Collection (Allows Duplicates)

A **List** maintains insertion order and allows duplicate elements.

Common implementations:

* `ArrayList`
* `LinkedList`
* `Vector`
* `CopyOnWriteArrayList` (thread-safe)

---

### 1.1 ArrayList

**Backed by:** dynamic array

**Ordering:** maintains insertion order

**Duplicates:** allowed

**Thread-safe:** ❌ not thread-safe

#### When to Use ArrayList

Use when:

* frequent **random access / index lookup**
* **mostly read operations**
* **few insert/delete in middle**
* dynamic size required instead of array

#### Avoid when:

* frequent insert/delete at beginning or middle
* frequent shifting of elements

#### Time Complexity

| Operation                 | Time Complexity |
| ------------------------- | --------------- |
| Access by index           | O(1)            |
| Insert at end (amortized) | O(1)            |
| Insert/delete at middle   | O(n)            |
| Search element            | O(n)            |

#### Example

```java
List<Integer> list = new ArrayList<>();
list.add(10);
list.add(20);
```

---

### 1.2 LinkedList

**Backed by:** Doubly linked list

**Ordering:** insertion order

**Duplicates:** allowed

#### When to Use LinkedList

Use when:

* frequent insert/delete at **beginning or middle**
* used as **Queue or Deque**
* you need **bidirectional traversal**

#### Avoid when:

* frequent random access by index (slow)

#### Time Complexity

| Operation                         | Time Complexity |
| --------------------------------- | --------------- |
| Access by index                   | O(n)            |
| Insert/delete at beginning/middle | O(1)            |
| Search element                    | O(n)            |

#### Example

```java
List<Integer> list = new LinkedList<>();
list.add(10);
list.add(20);
```

---

### 1.3 Vector (Legacy)

**Backed by:** dynamic array

**Thread-safe:** ✅ yes (synchronized methods)

#### When to Use Vector

* old legacy projects only
* when explicit synchronization required

> Modern alternative → **`Collections.synchronizedList(new ArrayList<>())`**
> or **`CopyOnWriteArrayList`**

#### Time Complexity

Same as `ArrayList`

---

### 1.4 CopyOnWriteArrayList (Thread-Safe List)

Used in **multithreading** where:

* reads are frequent
* writes are rare

Writes copy whole list → **costly**
But read is **lock-free**

---

## 2. Set – Unique Elements (No Duplicates)

Set ensures **no duplicate values**.

Implementations:

* `HashSet`
* `LinkedHashSet`
* `TreeSet`

---

### 2.1 HashSet

**Backed by:** Hash table

**Ordering:** ❌ no guarantee

**Duplicates:** ❌ not allowed

#### When to Use HashSet

* fast **existence checking**
* need unique elements
* don’t care about order

#### Time Complexity

| Operation | Time Complexity |
| --------- | --------------- |
| Add       | O(1) average    |
| Remove    | O(1) average    |
| Search    | O(1) average    |

Worst case O(n) due to hash collision.

---

### 2.2 LinkedHashSet

**Backed by:** Hash table + doubly linked list

**Ordering:** maintains **insertion order**

#### When to Use

* need uniqueness
* must maintain insertion order

#### Time Complexity

Same as `HashSet`.

---

### 2.3 TreeSet

**Backed by:** Red-Black Tree (self-balancing BST)

**Ordering:** Sorted

**Null elements:** not allowed

#### When to Use

* need **sorted order**
* need range queries like
  `headSet()`, `tailSet()`, `subSet()`

#### Time Complexity

| Operation | Time Complexity |
| --------- | --------------- |
| Add       | O(log n)        |
| Remove    | O(log n)        |
| Search    | O(log n)        |

---

## 3. Map – Key-Value Pairs

Map stores **key → value** pairs

No duplicate keys allowed

Implementations:

* `HashMap`
* `LinkedHashMap`
* `TreeMap`
* `ConcurrentHashMap`
* `Hashtable` (legacy)

---

### 3.1 HashMap

**Backed by:** Hash table

**Ordering:** ❌ none

**Nulls:** 1 null key allowed

#### When to Use HashMap

* fast lookup using key
* caching
* dictionary lookups
* frequency counting

#### Time Complexity

| Operation | Time Complexity |
| --------- | --------------- |
| Put       | O(1) avg        |
| Get       | O(1) avg        |
| Remove    | O(1) avg        |

Worst case O(n) due to collisions.

---

### 3.2 LinkedHashMap

Maintains **insertion order**

Supports **LRU cache** via access-order mode

#### Use when

* order must be preserved

---

### 3.3 TreeMap

**Backed by:** Red-Black Tree

**Ordering:** Sorted by key

#### When to Use

* need sorted keys
* need range queries (floor, ceiling, higher, lower)

#### Time Complexity

| Operation | Time Complexity |
| --------- | --------------- |
| Put       | O(log n)        |
| Get       | O(log n)        |
| Remove    | O(log n)        |

---

### 3.4 ConcurrentHashMap

**Thread-safe map (high performance)**

* segmentation based locking
* used in multithreading

Use instead of `Hashtable`.

---

## 4. Queue and Deque

Used for **FIFO/LIFO operations**

---

### 4.1 PriorityQueue

Implements **min-heap / max-heap**

#### Use when

* need highest / lowest priority retrieval

| Operation   | Time     |
| ----------- | -------- |
| Insert      | O(log n) |
| Remove root | O(log n) |
| Peek        | O(1)     |

---

### 4.2 ArrayDeque

* double ended queue
* faster than `Stack` and `LinkedList`

Supports:

* stack operations (push/pop)
* queue operations (offer/poll)

---

## 5. Quick Decision Guide

| Requirement                 | Choose               |
| --------------------------- | -------------------- |
| Random access list          | ArrayList            |
| Frequent insertion/deletion | LinkedList           |
| Thread-safe list            | CopyOnWriteArrayList |
| Unique values               | HashSet              |
| Unique + ordered values     | LinkedHashSet        |
| Unique + sorted values      | TreeSet              |
| Fast key-value lookup       | HashMap              |
| Sorted key-value map        | TreeMap              |
| Thread-safe map             | ConcurrentHashMap    |
| Priority ordering           | PriorityQueue        |
| LIFO / FIFO fast            | ArrayDeque           |

---

## 6. Legacy Collections – Avoid

| Collection | Replacement                      |
| ---------- | -------------------------------- |
| Vector     | ArrayList / CopyOnWriteArrayList |
| Hashtable  | ConcurrentHashMap                |
| Stack      | ArrayDeque                       |

---

## 7. Common Interview Questions

### Q1. Which is faster, ArrayList or LinkedList?

ArrayList for access
LinkedList for insertion/deletion in middle

---

### Q2. Why HashMap is faster than TreeMap?

HashMap O(1) vs TreeMap O(log n)

---

### Q3. Difference between HashSet and TreeSet?

| HashSet    | TreeSet  |
| ---------- | -------- |
| Not sorted | Sorted   |
| O(1)       | O(log n) |

---

### Q4. Why Vector is legacy?

Because it uses **synchronized methods** → slow and outdated design.

---

### Q5. Why ConcurrentHashMap instead of Hashtable?

Because ConcurrentHashMap:

* uses segmented locking
* better concurrency
* higher performance

---

## Key Takeaways

* Choose collection based on **requirements**

* Know **ordering**, **uniqueness**, **thread-safety**

* Understand **time complexities**

* Prefer **modern thread-safe collections**

* Avoid legacy types unless maintaining old code

---
