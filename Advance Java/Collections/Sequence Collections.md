# Sequenced Collections in Java

## 1. What Are Sequenced Collections?

Introduced in **Java 21**, **Sequenced Collections** provide a **unified and consistent API** for working with data structures that maintain a **well-defined encounter order**.

Before Java 21:

* `List` had ordered access
* `LinkedHashMap` had insertion order but no uniform API
* `Set` implementations behaved differently
* No standard way to get **first**, **last**, **reversed** views consistently

Sequenced Collections solve this.

They introduce **three new interfaces**:

1. `SequencedCollection`
2. `SequencedSet`
3. `SequencedMap`

These interfaces ensure that all ordered collections expose a **common set of operations**.

---

## 2. Why Sequenced Collections?

### Problems with Pre–Java 21 Collections:

| Issue                                   | Example                             |
| --------------------------------------- | ----------------------------------- |
| No universal `getFirst()` / `getLast()` | Only Deque had it                   |
| Inconsistent reverse operations         | Lists vs LinkedHashSet vs Deques    |
| No easy view of reversed data           | Need manual loops                   |
| No uniform insertion at start           | `addFirst()` not available for List |

### Solution:

**Sequenced Collections provide a uniform API** regardless of whether it's a:

* List
* LinkedHashSet
* LinkedHashMap

All expose first, last, reverse views, and positional operations.

---

## 3. SequencedCollection Interface

Super-interface of all ordered collections.

```java
public interface SequencedCollection<E> extends Collection<E> {
    E getFirst();
    E getLast();
    void addFirst(E e);
    void addLast(E e);
    E removeFirst();
    E removeLast();
    SequencedCollection<E> reversed();
}
```

### Features

* Access first and last elements
* Insert at first or last
* Remove from first or last
* Get a **reversed view** (not a copy)

### Example

```java
SequencedCollection<String> sc = new ArrayList<>();

sc.addFirst("Java");
sc.addLast("Python");

System.out.println(sc.getFirst()); // Java
System.out.println(sc.getLast());  // Python
```

---

## 4. SequencedSet Interface

Extends both `Set` and `SequencedCollection`.

```java
public interface SequencedSet<E> extends Set<E>, SequencedCollection<E> { }
```

Used by:

* `LinkedHashSet` (now implements SequencedSet)
* `SortedSet` (as a sequenced set)
* `NavigableSet`

### Example

```java
SequencedSet<Integer> set = new LinkedHashSet<>();
set.addFirst(1);
set.addLast(2);
```

---

## 5. SequencedMap Interface

Adds sequencing to Maps (key–value pairs).

```java
public interface SequencedMap<K, V> extends Map<K, V> {
    Map.Entry<K, V> firstEntry();
    Map.Entry<K, V> lastEntry();
    SequencedMap<K, V> reversed();
    V putFirst(K key, V value);
    V putLast(K key, V value);
}
```

### What It Enables:

* Get **first** or **last** key-value entry
* Insert at the beginning or end of map
* Get a reversed view of the map

### Implemented by:

* `LinkedHashMap`
* `TreeMap` (after Java 21)

---

## 6. Example Using SequencedMap

```java
SequencedMap<Integer, String> map = new LinkedHashMap<>();

map.putFirst(1, "One");
map.putLast(2, "Two");

System.out.println(map.firstEntry()); // 1=One
System.out.println(map.lastEntry());  // 2=Two

SequencedMap<Integer, String> rev = map.reversed();
System.out.println(rev); // {2=Two, 1=One}
```

---

## 7. reversed() View (Important)

`reversed()` returns a **live view** of the collection — not a new copy.

### Example:

```java
SequencedCollection<String> sc = new ArrayList<>();
sc.addLast("A");
sc.addLast("B");

SequencedCollection<String> rev = sc.reversed();

System.out.println(rev); // [B, A]

sc.addLast("C");
System.out.println(rev); // [C, B, A]
```

Both reflect changes to each other.

---

## 8. Which Collections Now Implement Sequenced APIs?

| Collection Type | Old Interface  | New Interface                |
| --------------- | -------------- | ---------------------------- |
| ArrayList       | List           | List + SequencedCollection   |
| LinkedList      | List, Deque    | Now also SequencedCollection |
| LinkedHashSet   | Set            | Set + SequencedSet           |
| LinkedHashMap   | Map            | Map + SequencedMap           |
| TreeMap         | Map, SortedMap | Map + SequencedMap           |

Plain `HashSet` and `HashMap` do NOT implement sequenced interfaces because they do not preserve order.

---

## 9. Advantages of Sequenced Collections

### 1. Uniform API for ordered operations

No more mixing:

* List methods
* Deque methods
* LinkedHashSet hacks

### 2. Reversible collections

Efficient `reversed()` view without copying.

### 3. Consistent insertion order access

Works across List, Set, and Map.

### 4. Clean and predictable operations

Better reading and writing at ends.

---

## 10. Example Summary Code

```java
public class SequencedDemo {
    public static void main(String[] args) {

        SequencedCollection<String> sc = new ArrayList<>();
        sc.addFirst("X");
        sc.addLast("Y");

        System.out.println(sc.getFirst()); // X
        System.out.println(sc.getLast());  // Y

        SequencedCollection<String> reversed = sc.reversed();
        System.out.println(reversed); // [Y, X]
    }
}
```

---

## Key Takeaways

* Introduced in **Java 21** to unify ordered collections.

* `SequencedCollection`, `SequencedSet`, and `SequencedMap` offer:

  * `getFirst()`, `getLast()`
  * `addFirst()`, `addLast()`
  * `removeFirst()`, `removeLast()`
  * `reversed()` live view
* Implemented by `ArrayList`, `LinkedList`, `LinkedHashSet`, `LinkedHashMap`, `TreeMap`.

* Makes ordered operations consistent across all major collection types.

---