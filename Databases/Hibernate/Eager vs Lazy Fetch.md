# Eager vs Lazy Fetch in Hibernate

## 1. What Is Fetching in Hibernate?

Fetching refers to **when** and **how** Hibernate loads related entities from the database.

Hibernate supports two fetch strategies:

| Fetch Type | When Data Is Loaded?                      | Performance                   |
| ---------- | ----------------------------------------- | ----------------------------- |
| **LAZY**   | Loaded **only when accessed**             | Fast, recommended             |
| **EAGER**  | Loaded **immediately** with parent entity | Slower, risk of over-fetching |

Fetching applies to relationships like:

* `@OneToOne`
* `@OneToMany`
* `@ManyToOne`
* `@ManyToMany`

---

## 2. Lazy Fetching (Default for “Collections”)

Lazy fetching delays loading related objects until you **explicitly access them**.

### Example:

```java
@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
private List<Student> students;
```

### Behavior:

```java
Department dept = session.get(Department.class, 1);  
// Only department data loaded

dept.getStudents().size();
// At this moment, Hibernate hits DB → loads students
```

### Advantages:

* Faster initial query
* Saves memory
* More scalable
* Reduces unnecessary queries
* Avoids retrieving thousands of child records unless needed

### Disadvantages:

* Requires session to still be open when accessing lazy fields
* Can cause `LazyInitializationException` if accessed outside a session

---

## 3. Eager Fetching (Default for @ManyToOne, @OneToOne)

Eager fetching loads related entity **immediately** with the parent entity.

### Example:

```java
@ManyToOne(fetch = FetchType.EAGER)
private Department department;
```

### Behavior:

```java
Student student = session.get(Student.class, 1);
// Student + Department are loaded together
```

### Advantages:

* No LazyInitializationException
* Useful when related data is always required

### Disadvantages:

* Heavy initial query
* Loads unnecessary data
* Can cause **N+1 query problem**
* Can slow down application significantly

---

## 4. Fetch Type Default Values (Important)

| Relationship Type | Default Fetch |
| ----------------- | ------------- |
| `@ManyToOne`      | **EAGER**     |
| `@OneToOne`       | **EAGER**     |
| `@OneToMany`      | **LAZY**      |
| `@ManyToMany`     | **LAZY**      |

In real-world applications:
→ Best practice is to **override EAGER defaults** and use LAZY almost everywhere.

---

## 5. Example: One-to-Many with Lazy + Eager

### Lazy Example

```java
@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
private List<Student> students;
```

DB activity:

```
SELECT * FROM department WHERE id=1;
-- Students not loaded yet
```

When accessing:

```
SELECT * FROM student WHERE dept_id=1;
```

---

### Eager Example

```java
@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
private List<Student> students;
```

DB activity:

```
SELECT * FROM department d 
LEFT JOIN student s ON d.id = s.dept_id
WHERE d.id = 1;
```

Eager loading performs JOIN → loads all students immediately.

---

## 6. EAGER vs LAZY in Different Relationships

### 6.1 @ManyToOne (Default EAGER)

```java
@ManyToOne(fetch = FetchType.EAGER)
private Department department;
```

Hibernate loads parent entity automatically.


### 6.2 @OneToMany (Default LAZY)

```java
@OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
private List<Student> students;
```

Collection loaded only when accessed.


### 6.3 @OneToOne

Default EAGER, but often better to use:

```java
@OneToOne(fetch = FetchType.LAZY)
```

### 6.4 @ManyToMany (Default LAZY)

```java
@ManyToMany(fetch = FetchType.LAZY)
private List<Course> courses;
```

---

## 7. The N+1 Select Problem

Occurs mostly with **EAGER** or unoptimized LAZY fetching inside loops.

Example:

```java
List<Department> depts = session.createQuery("from Department").list();

for (Department d : depts) {
    d.getStudents().size();   // triggers a query for each department
}
```

If there are 50 departments:

* 1 query for dept list
* 50 queries for students
* **Total: 51 queries** → very slow

Solutions:

* Use `JOIN FETCH`
* Use `batch-size`
* Use `EntityGraph` (JPA)

---

## 8. JOIN FETCH (Override Fetch Mode in Queries)

Even if a field is LAZY, you can explicitly fetch it:

```java
String hql = "SELECT d FROM Department d JOIN FETCH d.students";
List<Department> list = session.createQuery(hql).list();
```

Benefit:

* Loads parent + child in single query
* Avoids N+1 problem
* Still keeps mapping LAZY for normal usage

---

## 9. Best Practices

✔ Use **LAZY** for almost all associations
✔ Override defaults of `@ManyToOne` and `@OneToOne`:

```java
@ManyToOne(fetch = FetchType.LAZY)
```

✔ Use `JOIN FETCH` for cases where you need related data
✔ Never use EAGER on large collections → big performance hit
✔ Avoid EAGER on multiple associations → exponential joins
✔ Use DTO projections for optimized queries

---

## 10. Interview Questions

### Q1. Difference between Lazy and Eager fetch?

| Lazy                                | Eager                      |
| ----------------------------------- | -------------------------- |
| Loads data on demand                | Loads immediately          |
| Improves performance                | May degrade performance    |
| Risk of LazyInitializationException | No exception               |
| Best for collections                | OK for single object fetch |

---

### Q2. Why is @OneToMany LAZY by default?

Because collections can be huge → loading them eagerly would be expensive.

---

### Q3. Why is @ManyToOne EAGER by default?

A single parent entity is usually small and safe to load.

---

### Q4. What is LazyInitializationException?

Thrown when a lazy-loaded field is accessed after the Hibernate session is closed.

---

### Q5. How to solve LazyInitializationException?

* Keep session open (Open Session in View pattern)
* Use JOIN FETCH query
* Convert to DTO before closing session

---

### Q6. Can we override default fetch types?

Yes:

```java
@OneToMany(fetch = FetchType.EAGER)
```

---

### Q7. Which fetch type is recommended?

**Always prefer LAZY** unless you have a strong reason to use EAGER.

---

## Key Takeaways

* LAZY = loads related data only when needed → efficient

* EAGER = loads related data immediately → costly

* Hibernate defaults are not always ideal → override to LAZY

* Use `JOIN FETCH` for optimized multi-table loading

* Avoid EAGER on collections to prevent performance bottlenecks

* LazyInitializationException happens due to accessing LAZY data outside session

---
