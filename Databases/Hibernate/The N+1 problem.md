# Hibernate N+1 Problem

## 1. What Is the Hibernate N+1 Problem?

The **N+1 problem** is a **performance issue** in Hibernate where:

* **1 query** is executed to fetch parent entities
* **N additional queries** are executed to fetch child entities
* Total queries = **N + 1**

This happens due to **lazy loading** combined with **iterative access** to relationships.

---

## 2. Why It Is Called “N+1”?

Assume:

* You fetch **N parent records**
* For each parent, Hibernate fires **one extra query** to fetch children

Example:

* 1 query → fetch all departments
* N queries → fetch students for each department

If N = 10
→ Total queries = **11**

---

## 3. Typical Scenario That Causes N+1

### Entities

```java
@Entity
public class Department {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Student> students;
}
```

```java
@Entity
public class Student {

    @Id
    private int id;

    private String name;

    @ManyToOne
    private Department department;
}
```

---

## 4. Code That Triggers N+1

```java
List<Department> depts =
    session.createQuery("FROM Department", Department.class).list();

for (Department d : depts) {
    System.out.println(d.getStudents().size());
}
```

---

## 5. SQL Queries Generated (Behind the Scenes)

### Step 1: Fetch departments (1 query)

```sql
SELECT * FROM department;
```

### Step 2: Fetch students for each department (N queries)

```sql
SELECT * FROM student WHERE dept_id = 1;
SELECT * FROM student WHERE dept_id = 2;
SELECT * FROM student WHERE dept_id = 3;
...
```

Total queries = **1 + N**

---

## 6. Why this is a serious problem

* Massive number of DB round-trips
* Slow response times
* Increased DB load
* Poor scalability
* Common cause of production performance issues

In large datasets, this can result in **hundreds or thousands of queries**.

---

## 7. Root Causes of the N+1 Problem

### 1. Lazy fetching (default for collections)

```java
@OneToMany(fetch = FetchType.LAZY)
```

### 2. Accessing child collections inside loops

```java
for (Parent p : parents) {
    p.getChildren().size();
}
```

### 3. Hibernate trying to be efficient per object, not per use-case

Hibernate loads children **only when accessed**, but does not know you’ll access them for all parents.

---

## 8. Solutions to the N+1 Problem

### 8.1 Solution 1: JOIN FETCH (Best & Most Common)

Override lazy loading **at query level**.

### HQL with JOIN FETCH

```java
SELECT d FROM Department d JOIN FETCH d.students
```

### Java Code

```java
List<Department> depts =
    session.createQuery(
        "SELECT d FROM Department d JOIN FETCH d.students",
        Department.class
    ).list();
```

### SQL Generated

```sql
SELECT d.*, s.*
FROM department d
JOIN student s ON d.id = s.dept_id;
```

✔ Only **1 query**
✔ No N+1 problem

---

### 8.2 Solution 2: LEFT JOIN FETCH

Use when some parents may not have children.

```java
SELECT d FROM Department d LEFT JOIN FETCH d.students
```

Prevents missing parent records.

---

### 8.3 Solution 3: Batch Fetching (`@BatchSize`)

Hibernate loads children in **batches**, not one by one.

### Annotation-based

```java
@OneToMany(mappedBy = "department")
@BatchSize(size = 5)
private List<Student> students;
```

### Effect

Instead of:

```
N queries → 1 per department
```

Hibernate executes:

```
N / batchSize queries
```

Example:

* 20 departments
* batch size = 5
  → Only 4 queries

---

### 8.4 Solution 4: Global Batch Size Configuration

In `hibernate.cfg.xml`:

```xml
<property name="hibernate.default_batch_fetch_size">10</property>
```

Applies batching automatically to all lazy collections.

---

### 8.5 Solution 5: EntityGraph (JPA Standard)

Allows defining fetch plan without changing entity mappings.

```java
EntityGraph<Department> graph =
    session.createEntityGraph(Department.class);
graph.addAttributeNodes("students");

List<Department> depts =
    session.createQuery("FROM Department", Department.class)
           .setHint("javax.persistence.fetchgraph", graph)
           .getResultList();
```

---

### 8.6 Solution 6: DTO Projection (Best for Read-Heavy APIs)

Avoid entity graph entirely.

```java
SELECT new com.dto.DepartmentDTO(d.name, s.name)
FROM Department d JOIN d.students s
```

* No lazy loading
* No N+1
* Very fast

---

## 9. Why NOT Just Use EAGER Fetch?

```java
@OneToMany(fetch = FetchType.EAGER)
```

❌ Bad idea because:

* Loads children even when not needed
* Causes large joins
* Multiple eager relationships → Cartesian explosion
* Hard to control per query

Best practice:

> Keep mappings LAZY and control fetching in queries.

---

## 10. Detecting the N+1 Problem

### 1. Enable SQL logging

```xml
<property name="hibernate.show_sql">true</property>
```

### 2. Watch repeated SELECT statements

### 3. Use tools:

* Hibernate Statistics
* SQL logs
* APM tools (New Relic, Datadog)

---

## 11. N+1 vs Cartesian Product 

| N+1 Problem                      | Cartesian Product                   |
| -------------------------------- | ----------------------------------- |
| Too many queries                 | Too many rows                       |
| Lazy loading issue               | Eager fetching multiple collections |
| DB hit overhead                  | Memory explosion                    |
| Fixed with JOIN FETCH / batching | Fixed with query redesign           |

---

## 12. Best Practices to Avoid N+1

* Always assume LAZY relationships can cause N+1
* Use `JOIN FETCH` for known use-cases
* Use `@BatchSize` for safety
* Avoid EAGER on collections
* Prefer DTO projections for APIs
* Review SQL logs regularly

---

## 13. Interview Questions

### Q1. What is the Hibernate N+1 problem?

When Hibernate executes one query for parent entities and N additional queries for child entities.

---

### Q2. Why does N+1 occur?

Due to lazy loading combined with iterative access of relationships.

---

### Q3. How do you fix the N+1 problem?

Using JOIN FETCH, batch fetching, EntityGraph, or DTO projections.

---

### Q4. Is N+1 caused only by LAZY fetching?

Yes, but switching to EAGER is **not** the correct fix.

---

### Q5. Why is JOIN FETCH preferred?

It fetches parent and child data in a single SQL query.

---

### Q6. Can caching solve N+1?

Caching reduces DB hits but **does not eliminate the N+1 pattern**.

---

### Q7. Which solution is best in real applications?

JOIN FETCH for entity use-cases, DTO projection for read-heavy APIs.

---

## Key Takeaways

* N+1 is one of the **most common Hibernate performance problems**

* Caused by lazy loading + loops

* Results in excessive database queries

* Best fixes:

  * JOIN FETCH
  * Batch fetching
  * EntityGraphs
  * DTO projections

* Never blindly use EAGER fetching

---
