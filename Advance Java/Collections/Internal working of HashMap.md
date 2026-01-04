# How HashMap Works Internally in Java

## 1. Overview

`HashMap` is the most widely used data structure in Java Collections. It stores data as **key-value pairs** and provides **average O(1)** time complexity for insertion, deletion, and lookup.

Defined in:

```java
java.util.HashMap
```

A `HashMap` is based on:

* Hashing
* Array + LinkedList + Red-Black Tree hybrid structure
* `hashCode()` and `equals()` contract

It allows:

* One null key
* Multiple null values
* Non-synchronized operations (not thread-safe)

---

## 2. Internal Data Structure of HashMap

Internally, `HashMap` uses:

```
Array of buckets
Each bucket → LinkedList or Tree (Java 8+)
```

Conceptually:

```
Index 0  →  Entry → Entry → ...
Index 1  →  Entry
Index 2  →  null
Index 3  →  Entry → Entry → ...
```

Each entry is stored as a **Node**:

```java
static class Node<K,V> {
    final int hash;
    final K key;
    V value;
    Node<K,V> next;
}
```

---

## 3. Role of hashCode() and equals()

HashMap heavily depends on two methods from `Object` class:

```java
public int hashCode();
public boolean equals(Object obj);
```

### 3.1 hashCode()

* Returns an integer hash of the key
* Used to compute bucket index
* Same key must produce same hashcode

### 3.2 equals()

* Used to resolve collisions
* Confirms if two objects are actually equal

### 3.3 Contract (Very Important for Interviews)

1. If `a.equals(b)` is true → `a.hashCode() == b.hashCode()` must be true
2. If `hashCode()` is same → objects may or may not be equal

Breaking this contract leads to:

* Duplicate keys
* Unpredictable behavior
* Lookup failure

---

## 4. How HashMap Computes Bucket Index

Steps:

1. Take `hashCode()`
2. Apply `hash()` function to reduce collisions
3. Compute index using modulo:

```
index = hash & (n - 1)
```

Where `n` is table capacity.

This bitwise operation is faster than `%`.

---

## 5. Insertion Process (put operation)

### Example

```java
map.put("A", 10);
```

### Steps Internally

1. Compute hash from `"A".hashCode()`
2. Compute bucket index
3. Check if bucket is empty

#### Case 1: No collision

* Node inserted directly

#### Case 2: Collision occurs

Collision happens when multiple keys map to same index

HashMap handles collisions using:

* Linked List (before Java 8)
* Linked List + Tree (after Java 8)

#### Collision resolution steps

1. Compare hash
2. If hash equal → check `equals()`
3. If key exists → value replaced
4. Else → append node to list

### Treeification (Java 8+)

When bucket linked list size exceeds **8**, structure converts into **Red-Black Tree** improving performance from:

```
O(n) → O(log n)
```

Tree converts back to list if size falls below 6.

---

## 6. Lookup Operation (get)

### Example

```java
map.get("A");
```

### Steps Internally

1. Compute hash
2. Compute bucket index
3. Traverse bucket
4. Compare:

* First by hash
* Then by equals()

If found → return value
If not found → return null

Time complexity:

* Average: O(1)
* Worst case (all keys same bucket): O(n)
* With tree structure: O(log n)

---

## 7. Deletion Operation (remove)

### Example

```java
map.remove("A");
```

### Internal Process

1. Compute hash
2. Compute index
3. Locate bucket
4. Traverse linked list / tree
5. Match key using equals()
6. Remove node
7. Re-link neighbors

Time complexity:

* Average: O(1)
* Worst-case: O(n)
* Tree bucket: O(log n)

---

## 8. Why HashMap Operations are O(1)

Reason:

* Direct index access using hash
* No need to scan full collection
* Constant-time bucket addressing

Mathematically:

```
O(1) expected because hash distributes keys uniformly
```

Becomes **O(n)** only when:

* Poor hash function
* All keys land in one bucket
* High collision rate

---

## 9. Load Factor and Rehashing

### Load Factor

Default:

```
0.75
```

Formula:

```
resize threshold = capacity * load factor
```

Example:

* default capacity = 16
* threshold = 16 * 0.75 = 12

When size exceeds 12 → HashMap resizes to **32** buckets

### Rehashing

* Creates bigger array
* Recomputes bucket indices
* Expensive operation

Interview tip: Avoid resizing by initializing map with expected size.

---

## 10. Handling of Null Keys and Values

### Null Key

Stored in bucket 0

Reason:

* null has no hashCode
* JVM uses fixed bucket

### Null Values

Allowed multiple times

---

## 11. Fail-Fast Behavior

Iterators throw:

```
ConcurrentModificationException
```

If HashMap is modified during iteration except through iterator methods.

HashMap is **not thread-safe**. Use:

* `ConcurrentHashMap`
* or `Collections.synchronizedMap`

for concurrency.

---

## 12. Important Methods Used Internally

| Method       | Purpose                      |
| ------------ | ---------------------------- |
| hashCode()   | Generates hash value         |
| equals()     | Confirms key equality        |
| resize()     | Expands capacity             |
| putVal()     | Inserts key-value mapping    |
| getNode()    | Retrieves node for key       |
| treeifyBin() | Converts bucket list to tree |

---

## 13. Example Demonstrating hashCode() and equals()

```java
class Student {
    int id;

    Student(int id) {
        this.id = id;
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object obj) {
        Student s = (Student) obj;
        return this.id == s.id;
    }
}
```

Usage:

```java
Map<Student, String> map = new HashMap<>();
map.put(new Student(1), "John");

System.out.println(map.get(new Student(1))); // John
```

Without overriding these methods, get() would return null.

---

## 14. Common Interview Questions

### Q1: How does HashMap achieve O(1)?

* Index calculated through hashing
* Direct bucket access

### Q2: When does HashMap become O(n)?

* Severe collision
* Poor hash function
* Single bucket chain growth

### Q3: Why both hashCode() and equals() are needed?

* hashCode() → bucket location
* equals() → key equality check inside bucket

### Q4: How many null keys are allowed?

* Only one

### Q5: What is treeification threshold in Java 8?

* 8 nodes per bucket

### Q6: Is HashMap thread-safe?

* No
* Use ConcurrentHashMap instead

---

## Key Takeaways

* HashMap uses hashing to locate buckets

* Operations are O(1) on average

* Buckets use LinkedList and Red-Black Trees

* hashCode() and equals() define key uniqueness

* Resizing happens after load factor threshold

* Supports one null key and multiple null values

* Not thread-safe

---
