# Map in Java

## 1. What Is a Map?

A **Map** in Java is a data structure that stores elements in **key–value pairs**.
Each **key is unique**, but values may be duplicate.

Defined in:

```java
java.util.Map
```

Maps are **not** part of the Collection hierarchy (List, Set, Queue), but they are a critical part of the Collections Framework.

---

## 2. Key Characteristics of Map

| Feature          | Description               |
| ---------------- | ------------------------- |
| Stores           | Key → Value pairs         |
| Duplicate keys   | ❌ Not allowed             |
| Duplicate values | ✔ Allowed                 |
| Null keys        | Depends on implementation |
| Null values      | Depends on implementation |
| Ordering         | Depends on implementation |

---

## 3. Map Methods (Core API)

```java
put(K key, V value)
get(Object key)
remove(Object key)
containsKey(Object key)
containsValue(Object value)
keySet()
values()
entrySet()
size()
clear()
isEmpty()
```

Example:

```java
Map<Integer, String> map = new HashMap<>();
map.put(1, "A");
map.put(2, "B");

System.out.println(map.get(1)); // A
```

---

## 4. Different Map Implementations

Java provides several Map types with different behaviors.

---

## 4.1 HashMap (Most Common)

* No ordering
* Allows **1 null key**, multiple null values
* Fast (O(1) average for get/put)
* Not synchronized (not thread-safe)

```java
Map<Integer, String> map = new HashMap<>();
map.put(101, "Java");
map.put(102, "Python");
```

---

## 4.2 LinkedHashMap (Maintains Insertion Order)

* Keeps elements in insertion order
* Slightly slower than HashMap
* Allows one null key

```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("A", 1);
map.put("B", 2);
```

Output:

```
{A=1, B=2}
```

---

## 4.3 TreeMap (Sorted Map)

* Stores keys in **sorted order** (ascending)
* No null key allowed
* Based on **Red-Black Tree**
* Slower (O(log n))

```java
Map<Integer, String> map = new TreeMap<>();
map.put(30, "C");
map.put(10, "A");
map.put(20, "B");
```

Output:

```
{10=A, 20=B, 30=C}
```

---

## 4.4 Hashtable (Legacy)

* Thread-safe (synchronized)
* No null keys / null values allowed
* Slower than HashMap

```java
Map<Integer, String> map = new Hashtable<>();
```

Mostly replaced by ConcurrentHashMap.

---

## 4.5 ConcurrentHashMap (Thread-Safe)

* Modern replacement for Hashtable
* Thread-safe and high performance
* No null key/value allowed
* Used in multithreading & backend systems

```java
Map<String, Integer> map = new ConcurrentHashMap<>();
```

---

## 5. Difference Between HashMap, LinkedHashMap, TreeMap, Hashtable, CHM

| Feature      | HashMap | LinkedHashMap     | TreeMap     | Hashtable   | ConcurrentHashMap |
| ------------ | ------- | ----------------- | ----------- | ----------- | ----------------- |
| Order        | None    | Insertion         | Sorted      | None        | None              |
| Null Key     | Yes     | Yes               | No          | No          | No                |
| Null Value   | Yes     | Yes               | Yes         | No          | No                |
| Thread Safe? | No      | No                | No          | Yes         | Yes               |
| Speed        | Fastest | Medium            | Slow        | Slow        | Fast under load   |
| Use Case     | General | Ordered iteration | Sorted data | Legacy code | High concurrency  |

---

## 6. Iterating Through a Map

### 6.1 Using keySet()

```java
for (Integer key : map.keySet()) {
    System.out.println(key + " => " + map.get(key));
}
```

---

### 6.2 Using entrySet() (Best Way)

```java
for (Map.Entry<Integer, String> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " = " + entry.getValue());
}
```

---

### 6.3 Using forEach (Java 8+)

```java
map.forEach((k, v) -> System.out.println(k + " -> " + v));
```

---

## 7. Methods Returning Collections

### 7.1 keySet()

Returns all keys:

```java
Set<K> keys = map.keySet();
```

---

### 7.2 values()

Returns all values:

```java
Collection<V> values = map.values();
```

---

### 7.3 entrySet()

Returns all key-value pairs:

```java
Set<Map.Entry<K, V>> entries = map.entrySet();
```

---

## 8. HashMap Internal Working (Important)

1. HashMap stores entries in **buckets** using an array of nodes
2. `hashCode()` determines the bucket index
3. Collision resolution:

   * Linked list (Java 7)
   * Linked list → Red-Black Tree (Java 8, threshold = 8)
4. `equals()` used to compare keys
5. Rehashing happens when size > capacity * load factor (default = **0.75**)

---

## 9. TreeMap Internal Working

* Uses **TreeMap.Entry** nodes
* Balanced using **Red-Black Tree**
* Keys must be **comparable**
* No null keys allowed
* Maintains sorted order

---

## 10. Common Use Cases

| Scenario                 | Best Map          |
| ------------------------ | ----------------- |
| General-purpose          | HashMap           |
| Maintain insertion order | LinkedHashMap     |
| Need sorted map          | TreeMap           |
| Thread-safe              | ConcurrentHashMap |
| Legacy systems           | Hashtable         |

---

## 11. Real-World Examples

---

### 11.1 Word Frequency Count

```java
Map<String, Integer> freq = new HashMap<>();

for (String word : words) {
    freq.put(word, freq.getOrDefault(word, 0) + 1);
}
```

---

### 11.2 Storing Student ID → Name

```java
Map<Integer, String> students = new HashMap<>();
students.put(101, "John");
students.put(102, "Sarah");
```

---

### 11.3 LRU Cache (uses LinkedHashMap)

```java
LinkedHashMap cache = new LinkedHashMap(16, 0.75f, true) {
    protected boolean removeEldestEntry(Map.Entry e) {
        return size() > 5;
    }
};
```

---

## 12. Interview Questions

### Q1: Why Map does not extend Collection?

Because Map deals with **pairs**, not individual elements.

---

### Q2: Why HashMap allows only one null key?

Because all null keys would hash to the same bucket.

---

### Q3: How does HashMap handle collisions?

Using **linked lists**, and **balanced trees** after Java 8.

---

### Q4: Why TreeMap does not allow null keys?

Because null cannot be compared during sorting.

---

### Q5: Difference between HashMap and Hashtable?

* HashMap not synchronized
* Hashtable synchronized and legacy

---

## Key Takeaways

* Map stores key–value pairs; keys must be unique.

* HashMap → fastest & most commonly used.

* LinkedHashMap → maintains insertion order.

* TreeMap → sorted map.

* Hashtable → legacy, synchronized.

* ConcurrentHashMap → modern thread-safe map.

* Use `entrySet()` for efficient iteration.

* Internal workings rely heavily on hashing, comparison, and trees.

---