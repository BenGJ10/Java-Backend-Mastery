# Streams in Java


## 1. Overview

The **Stream API** (introduced in Java 8) provides a **functional, declarative** way to process data.
A Stream represents a **sequence of elements** supporting **aggregate operations** like:

* filtering
* mapping
* reducing
* sorting
* collecting

Streams do **not** store data; they operate **on top of collections, arrays, or I/O channels**.

---

## 2. Key Characteristics of Streams

| Property                            | Description                                                     |
| ----------------------------------- | --------------------------------------------------------------- |
| **Not a data structure**            | Does not store elements; works on source data                   |
| **Functional style**                | Uses lambdas; concise, readable                                 |
| **Lazy evaluation**                 | Intermediate operations do not execute until terminal operation |
| **Can be parallel**                 | Use `.parallelStream()`                                         |
| **Once consumed, cannot be reused** | Streams cannot be traversed twice                               |

---

## 3. Creating Streams

### 3.1 From Collections

```java
List<Integer> list = List.of(1, 2, 3);
Stream<Integer> stream = list.stream();
```

### 3.2 From Arrays

```java
int[] arr = {1, 2, 3};
IntStream s = Arrays.stream(arr);
```

### 3.3 From Stream.of()

```java
Stream<String> stream = Stream.of("A", "B", "C");
```

### 3.4 Infinite Streams

```java
Stream<Integer> s = Stream.iterate(1, n -> n + 1);
```

---

## 4. Stream Operations (Two Types)

---

### 4.1 Intermediate Operations (Lazy)

They transform a stream into another stream.

| Operation    | Description                        |
| ------------ | ---------------------------------- |
| `filter()`   | Select elements based on condition |
| `map()`      | Transform elements                 |
| `sorted()`   | Sorting                            |
| `distinct()` | Remove duplicates                  |
| `limit()`    | Take first N elements              |
| `skip()`     | Skip first N elements              |
| `flatMap()`  | Flatten nested structures          |

**Intermediate operations do not trigger execution.**

---

### 4.2 Terminal Operations (Trigger Execution)

| Operation                                 | Description                      |
| ----------------------------------------- | -------------------------------- |
| `forEach()`                               | Iterate and perform action       |
| `collect()`                               | Gather results (List, Set, etc.) |
| `reduce()`                                | Reduce to single value           |
| `count()`                                 | Count elements                   |
| `findFirst()` / `findAny()`               | Get element                      |
| `allMatch()`, `anyMatch()`, `noneMatch()` | Match conditions                 |

Once a terminal operation runs, the stream is **consumed**.

---

## 5. Common Stream Examples

---

### Example 1: Filtering

```java
List<Integer> nums = List.of(10, 15, 20, 25);

List<Integer> even = nums.stream()
                         .filter(n -> n % 2 == 0)
                         .collect(Collectors.toList());
```

---

### Example 2: Mapping

```java
List<String> names = List.of("java", "spring", "docker");

List<String> upper = names.stream()
                          .map(String::toUpperCase)
                          .collect(Collectors.toList());
```

---

### Example 3: Sorting

```java
List<Integer> sorted = nums.stream()
                           .sorted()
                           .collect(Collectors.toList());
```

Custom comparator:

```java
names.stream().sorted((a, b) -> b.compareTo(a));
```

---

### Example 4: Reduce Operation

```java
int sum = nums.stream()
              .reduce(0, (a, b) -> a + b);
```

Or:

```java
int sum = nums.stream().mapToInt(n -> n).sum();
```

---

### Example 5: Distinct + Limit

```java
List<Integer> result = List.of(1, 2, 2, 3, 4, 4, 5).stream()
                           .distinct()
                           .limit(3)
                           .collect(Collectors.toList());
```

---

### Example 6: flatMap()

Useful when dealing with nested structures.

```java
List<List<Integer>> list = List.of(
    List.of(1,2),
    List.of(3,4)
);

List<Integer> flat = list.stream()
                         .flatMap(l -> l.stream())
                         .collect(Collectors.toList());
```

---

## 6. Parallel Streams

Stream operations can run in parallel:

```java
list.parallelStream()
    .filter(n -> n % 2 == 0)
    .forEach(System.out::println);
```

### Use when:

* Large dataset
* CPU-heavy operations
* Order is not critical

### Avoid when:

* Small collections
* I/O operations
* Shared mutable state

---

## 7. Collectors (Collecting Results)

### 7.1 To List

```java
List<Integer> list = stream.collect(Collectors.toList());
```

### 7.2 To Set

```java
Set<Integer> set = stream.collect(Collectors.toSet());
```

### 7.3 To Map

```java
Map<Integer, String> map = users.stream()
                                .collect(Collectors.toMap(u -> u.id, u -> u.name));
```

### 7.4 Grouping

```java
Map<String, List<Employee>> grouped =
    employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
```

### 7.5 Counting

```java
long count = stream.collect(Collectors.counting());
```

---

## 8. Primitive Streams

For performance, Java provides:

* `IntStream`
* `LongStream`
* `DoubleStream`

Example:

```java
int sum = IntStream.of(1,2,3,4).sum();
```

---

## 9. Stream Pipeline Structure

A stream pipeline has three steps:

1. **Source** → Collection, array, IO
2. **Intermediate operations** → filter, map, sorted
3. **Terminal operation** → collect, reduce, forEach

Example:

```java
List<Integer> result =
    nums.stream()           // source
        .filter(n -> n > 5) // intermediate
        .map(n -> n * 2)    // intermediate
        .collect(Collectors.toList()); // terminal
```

---

## 10. Stream vs Loop

| Feature          | Stream     | Loop             |
| ---------------- | ---------- | ---------------- |
| Style            | Functional | Imperative       |
| Parallel support | Easy       | Complex          |
| Mutability       | Avoided    | Mutations common |
| Readability      | Concise    | Verbose          |
| Reusability      | No         | Yes              |

---

## 11. Common Mistakes with Streams

### 11.1 Modifying external state (bad practice)

```java
int sum = 0;
list.stream().forEach(n -> sum += n); // ❌ not thread-safe
```

Use reduce instead.

---

### 11.2 Reusing Streams

```java
Stream<Integer> s = list.stream();
s.forEach(System.out::println);
s.forEach(System.out::println);  // ❌ error — stream already consumed
```

---

## 12. Interview Questions

### Q1: Difference between map() and flatMap()?

* `map()` → transforms each element
* `flatMap()` → flattens nested streams into one stream

---

### Q2: Why are streams lazy?

Intermediate operations run only when a terminal operation exists → better performance.

---

### Q3: Can streams modify the original collection?

No, streams are **non-mutating**.

---

### Q4: Difference between stream() and parallelStream()?

* `stream()` → sequential execution
* `parallelStream()` → parallel execution using ForkJoinPool

---

### Q5: Can we reuse a stream?

No, stream is consumed after terminal operation.

---

## Key Takeaways

* Streams provide a **clean functional approach** to processing data.

* Support filtering, mapping, reducing, sorting, and collecting.

* Intermediate operations are **lazy**.

* Terminal operations **trigger computation**.

* Streams are **not data structures**.

* Great for parallel processing but must be used wisely.

---