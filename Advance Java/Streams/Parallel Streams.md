# Parallel Streams in Java

## 1. Overview

**Parallel Streams** allow stream operations to run concurrently using multiple CPU cores.
Instead of processing elements sequentially, the work is divided into tasks and executed in parallel using the **Fork–Join Framework**.

Parallel streams can significantly improve performance for **large**, **CPU-intensive** operations — but must be used carefully.

---

## 2. How to Create Parallel Streams

### 2.1 From a Collection

```java
list.parallelStream()
```

### 2.2 Convert an existing stream

```java
stream.parallel()
```

### 2.3 From Stream.of()

```java
Stream.of(1, 2, 3).parallel();
```

---

## 3. How Parallel Streams Work Internally

Parallel streams use the **ForkJoinPool.commonPool** (a shared pool of worker threads).

### Steps:

1. The stream data is **split** into chunks.
2. Each chunk is processed by different threads.
3. Results are **combined** (reduced) into a final result.

---

## 4. When Parallel Streams Are Helpful

Parallel streams are effective when:

* Dataset is **large**
* Computation is **CPU-bound** (not I/O)
* Order is not important
* The work per element is non-trivial

### Example of heavy computation:

```java
int result = list.parallelStream()
                 .mapToInt(n -> heavyOperation(n))
                 .sum();
```

---

## 5. Example Usage

### Example 1: Parallel Filtering

```java
List<Integer> evens =
    list.parallelStream()
        .filter(n -> n % 2 == 0)
        .collect(Collectors.toList());
```

---

### Example 2: Parallel Sorting

```java
List<Integer> sorted =
    list.parallelStream()
        .sorted()
        .collect(Collectors.toList());
```

---

### Example 3: Parallel Reduce

```java
int sum =
    list.parallelStream()
        .reduce(0, Integer::sum);
```

---

## 6. Performance Comparison

### Sequential

```java
long start = System.currentTimeMillis();
list.stream().map(n -> n * n).count();
long end = System.currentTimeMillis();
```

### Parallel

```java
long start = System.currentTimeMillis();
list.parallelStream().map(n -> n * n).count();
long end = System.currentTimeMillis();
```

Parallel streams often outperform sequential ones **only** when:

* Work per element is expensive

* Dataset is large (thousands/millions of elements)

---

## 7. Limitations and When *NOT* to Use Parallel Streams

Parallel streams **should NOT** be used in:



### 7.1 Small Data Sets

Parallel overhead > benefit for small lists.

### 7.2 When Order Matters

Operations like `forEachOrdered()` kill parallelism.

### 7.3 I/O-bound tasks

Parallelism does not help for:

* Reading files
* Database queries
* Network calls

Threads become blocked.

### 7.4 Shared Mutable State (Dangerous)

This is unsafe:

```java
int[] sum = {0}; // shared mutable state
list.parallelStream().forEach(n -> sum[0] += n);  // ❌ race condition
```

### 7.5 Using synchronized blocks

Synchronization kills parallelism.

### 7.6 In Web Servers / Multi-threaded Applications

Parallel streams use **ForkJoinPool.commonPool**, which may interfere with server threads.

Use a custom pool instead (advanced):

```java
ForkJoinPool pool = new ForkJoinPool(10);
pool.submit(() -> list.parallelStream().forEach(...));
```

---

## 8. parallelStream() vs stream().parallel()

| Expression                 | Meaning                            |
| -------------------------- | ---------------------------------- |
| `list.parallelStream()`    | Creates a parallel stream directly |
| `list.stream().parallel()` | Converts sequential → parallel     |

Both produce the same type of stream.

---

## 9. Combining Parallel and Sequential

Stream pipelines cannot switch back and forth improperly.

This is valid:

```java
stream.parallel().filter(...).map(...).sequential().forEach(...);
```

The **last call** (`parallel()` or `sequential()`) determines final behavior.

---

## 10. Order in Parallel Streams

### forEach()

```java
list.parallelStream().forEach(System.out::println); 
```

→ Output may be unordered.

### forEachOrdered()

```java
list.parallelStream().forEachOrdered(System.out::println);
```

→ Maintains order but kills performance.

---

## 11. Spliterator – Backbone of Parallel Streams

A **Spliterator** splits data into chunks for parallel processing.

Key methods:

* `trySplit()`
* `tryAdvance()`
* `estimateSize()`

Used internally by collections to divide work efficiently.

---

## 12. Real-World Examples

### Example: Sum of large list

```java
long sum = LongStream.rangeClosed(1, 1_000_000)
                     .parallel()
                     .sum();
```

---

### Example: Processing expensive computations

```java
List<Integer> results =
    ids.parallelStream()
       .map(id -> compute(id)) // CPU-heavy
       .collect(Collectors.toList());
```

---

### Example: Using custom thread pool

```java
ForkJoinPool pool = new ForkJoinPool(5);
pool.submit(() ->
    list.parallelStream().forEach(System.out::println)
).join();
```

---

## 13. Interview Questions

### Q1: When should you use parallel streams?

Use when tasks are:

* CPU-bound
* Large data sets
* Independent (no shared mutable state)

---

### Q2: Why are parallel streams slow for small lists?

Thread creation + task splitting overhead > processing time.

---

### Q3: What is the biggest danger of parallel streams?

**Race conditions due to shared mutable state.**

---

### Q4: Difference between parallelStream() and parallel()?

Both create parallel streams; only the starting point differs.

---

### Q5: Which thread pool runs parallel streams?

`ForkJoinPool.commonPool`.

---

### Q6: How to use a custom thread pool?

Use `ForkJoinPool.submit()` to wrap the parallel stream.

---

## Key Takeaways

* Parallel streams process data concurrently using the Fork–Join framework.

* Best suited for **large, CPU-heavy** workloads.

* Avoid them for **I/O tasks**, **small datasets**, and **shared mutable state**.

* Order-sensitive operations reduce performance.

* Understand overhead before using in production systems.

---
