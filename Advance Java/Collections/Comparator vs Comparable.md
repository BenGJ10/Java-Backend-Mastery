# Comparable vs Comparator in Java


## 1. What Are Comparable and Comparator?

Both **Comparable** and **Comparator** are used to define sorting logic for objects, but they differ in where and how the comparison logic is written.

| Feature          | Comparable                               | Comparator                           |
| ---------------- | ---------------------------------------- | ------------------------------------ |
| Package          | `java.lang`                              | `java.util`                          |
| Purpose          | Natural ordering                         | Custom ordering                      |
| Method           | `compareTo()`                            | `compare()`                          |
| Sorting Logic    | Inside the class                         | Outside the class                    |
| Affects Class?   | Yes (modifies source)                    | No (kept separate)                   |
| Number of Orders | Only ONE                                 | Multiple possible                    |
| Used By          | `Collections.sort()` / TreeSet / TreeMap | `Collections.sort(list, comparator)` |

---

## 2. Comparable (Natural Ordering)

A class implements `Comparable` to define its **default sorting logic**.

### Syntax:

```java
class ClassName implements Comparable<ClassName> {
    public int compareTo(ClassName other) { ... }
}
```

### Return Values:

* `0` → objects are equal
* negative → current object < other
* positive → current object > other

---

### Example: Sorting Students by Roll Number (Natural Order)

```java
class Student implements Comparable<Student> {
    int roll;
    String name;

    Student(int roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    @Override
    public int compareTo(Student s) {
        return this.roll - s.roll;
    }
}
```

Usage:

```java
List<Student> list = new ArrayList<>();
Collections.sort(list);
```

---

## 3. Comparator (Custom Ordering)

Used when:

* You cannot modify the class
* You want **multiple sorting strategies**

### Syntax:

```java
Comparator<ClassName> comp = (a, b) -> { ... };
```

Or:

```java
class MyComparator implements Comparator<ClassName> {
    public int compare(ClassName a, ClassName b) { ... }
}
```

---

## Example: Sorting Students by Name (Custom Order)

```java
Comparator<Student> nameComparator = (s1, s2) ->
        s1.name.compareTo(s2.name);
```

Usage:

```java
Collections.sort(list, nameComparator);
```

---

## 4. More Examples of Comparator

### Sort Students by Descending Roll Number

```java
Comparator<Student> rollDesc = (s1, s2) -> s2.roll - s1.roll;
```

### Sort by Name Length

```java
Comparator<Student> nameLength = (s1, s2) ->
        s1.name.length() - s2.name.length();
```

### Sort multiple conditions

```java
Comparator<Student> multi = Comparator
        .comparing(Student::getName)
        .thenComparing(Student::getRoll);
```

---

## 5. Comparable vs Comparator — Detailed Comparison

| Aspect                  | Comparable                           | Comparator                        |
| ----------------------- | ------------------------------------ | --------------------------------- |
| Interface               | `Comparable<T>`                      | `Comparator<T>`                   |
| Method                  | `compareTo(T o)`                     | `compare(T o1, T o2)`             |
| Ordering                | Natural                              | Custom                            |
| Number of Sort Orders   | 1                                    | Multiple                          |
| Affects Original Class? | Yes                                  | No                                |
| Lambda Support          | No (method inside class)             | Yes (commonly used with lambdas)  |
| Used by                 | TreeSet, TreeMap, Collections.sort() | Collections.sort(), Arrays.sort() |
| Ideal For               | One natural ordering                 | Flexible sort logic               |

---

## 6. When to Use What?

### Use Comparable when:

* You want **default sorting logic**
* Sorting is **intrinsic** to the object
  (e.g., sorting Employees by their ID)

### Use Comparator when:

* You need multiple sorting combinations
* You cannot modify the source class
* Sorting should be **externalized**

---

## 7. Real-World Example: Sorting Employee Objects

### Class:

```java
class Employee implements Comparable<Employee> {
    int id;
    double salary;

    @Override
    public int compareTo(Employee e) {
        return this.id - e.id; // natural order by ID
    }
}
```

### Custom Comparator (Sort by Salary):

```java
Comparator<Employee> salaryComparator =
        (e1, e2) -> Double.compare(e1.salary, e2.salary);
```

Usage:

```java
Collections.sort(list, salaryComparator);
```

---

## 8. Common Interview Questions

### Q1. Why does String implement Comparable?

So that strings have natural alphabetical ordering.

### Q2. Can a class implement both Comparable and Comparator?

Yes — Comparable for natural order + Comparators for alternative orders.

### Q3. What happens if compare() returns inconsistent values?

Data structures like TreeSet/TreeMap may behave unpredictably.

### Q4. Which one is used by TreeSet and TreeMap?

* Comparable (default)
* Comparator (if provided during object creation)

Example:

```java
TreeSet<Student> set = new TreeSet<>(nameComparator);
```

---

## Key Takeaways

* **Comparable** defines natural order **inside the class**.

* **Comparator** defines custom orders **outside the class**.

* Comparable → `compareTo()`; Comparator → `compare()`.

* Use Comparator for multiple sorting criteria.

* TreeSet, TreeMap, Collections.sort() leverage both.

---

