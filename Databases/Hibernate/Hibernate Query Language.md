# Hibernate Query Language (HQL)

## 1. What Is HQL?

**Hibernate Query Language (HQL)** is an **object-oriented query language** used to query Hibernate-managed entities.

Key idea:

> HQL works with **Java classes and fields**, not **database tables and columns**.

Hibernate translates HQL into **database-specific SQL** internally.

---

## 2. Why HQL Exists

HQL was designed to solve problems of raw SQL in ORM-based applications.

### Problems with SQL in ORM:

* Tightly coupled to table names
* Database-specific syntax
* Manual mapping of result sets to objects

### HQL advantages:

* Database independent
* Uses entity names and properties
* Automatic object mapping
* Integrates with Hibernate caching
* Cleaner and maintainable

---

## 3. SQL vs HQL

| Aspect           | SQL                    | HQL                    |
| ---------------- | ---------------------- | ---------------------- |
| **Works on**         | Tables & columns       | Entities & fields      |
| **Result**          | Rows                   | Objects                |
| **DB Dependency**    | Yes                    | No                     |
| **Object Mapping**   | Manual                 | Automatic              |
| **Case Sensitivity** | Column/table dependent | Entity/field sensitive |

---

## Example Comparison

### SQL

```sql
SELECT * FROM student WHERE age > 20;
```

### HQL

```sql
FROM Student WHERE age > 20
```

* `Student` → Entity class name
* `age` → Entity field

---

## 4. Basic HQL Syntax

General format:

```sql
FROM EntityName [WHERE condition]
```

or

```sql
SELECT alias FROM EntityName alias [WHERE condition]
```

---

## 5. Basic HQL Queries

### 5.1 Fetch All Records

```java
Query<Student> query = session.createQuery("FROM Student", Student.class);
List<Student> students = query.getResultList();
```

Generated SQL internally:

```sql
SELECT * FROM student;
```

---

### 5.2 Fetch With WHERE Clause

```java
Query<Student> query =
    session.createQuery("FROM Student WHERE age > 20", Student.class);

List<Student> list = query.getResultList();
```

---

### 5.3 Fetch Single Record

```java
Student s = session
    .createQuery("FROM Student WHERE id = 1", Student.class)
    .uniqueResult();
```

---

## 6. Parameterized Queries 

Always use parameters to prevent SQL injection.


### 6.1 Named Parameters

```java
Query<Student> query =
    session.createQuery("FROM Student WHERE age > :age", Student.class);

query.setParameter("age", 20);
List<Student> list = query.list();
```

---

### 6.2 Positional Parameters

```java
Query<Student> query =
    session.createQuery("FROM Student WHERE age > ?1", Student.class);

query.setParameter(1, 20);
```

Named parameters are preferred for readability.

---

## 7. Select Clause

HQL supports selecting specific fields.

### 7.1 Selecting Single Column

```java
Query<String> query =
    session.createQuery("SELECT name FROM Student", String.class);

List<String> names = query.list();
```

---

### 7.2 Selecting Multiple Columns

```java
Query<Object[]> query =
    session.createQuery("SELECT name, age FROM Student");

List<Object[]> list = query.list();

for (Object[] row : list) {
    String name = (String) row[0];
    int age = (int) row[1];
}
```

---

### 7.3 DTO Projection 

**(DTO = Data Transfer Object)**

```java
Query<StudentDTO> query =
    session.createQuery(
        "SELECT new com.dto.StudentDTO(name, age) FROM Student",
        StudentDTO.class
    );
```

This avoids Object[] handling and improves clarity.

---

## 8. HQL with ORDER BY

```java
FROM Student ORDER BY age DESC
```

Multiple fields:

```java
FROM Student ORDER BY age DESC, name ASC
```

---

## 9. HQL with GROUP BY & HAVING


### 9.1 GROUP BY

```java
SELECT department, COUNT(*) FROM Student GROUP BY department
```

---

### 9.2 HAVING

```java
SELECT department, COUNT(*) 
FROM Student 
GROUP BY department 
HAVING COUNT(*) > 5
```

---

## 10. Aggregate Functions in HQL

Hibernate supports SQL-like aggregate functions:

| Function  | Usage             |
| --------- | ----------------- |
| `COUNT()` | Number of records |
| `SUM()`   | Sum of values     |
| `AVG()`   | Average           |
| `MIN()`   | Minimum           |
| `MAX()`   | Maximum           |

---

### Example

```java
Long count =
    session.createQuery("SELECT COUNT(*) FROM Student", Long.class)
           .uniqueResult();
```

---

## 11. UPDATE & DELETE using HQL 

Bulk operations **bypass Hibernate session cache**.


### 11.1 UPDATE Query

```java
Transaction tx = session.beginTransaction();

Query query =
    session.createQuery("UPDATE Student SET age = 25 WHERE id = 1");

int rows = query.executeUpdate();

tx.commit();
```

---

### 11.2 DELETE Query

```java
Transaction tx = session.beginTransaction();

Query query =
    session.createQuery("DELETE FROM Student WHERE age < 18");

int rows = query.executeUpdate();

tx.commit();
```

---

## 12. JOINs in HQL

HQL supports **object-oriented joins**.

---

### 12.1 INNER JOIN

```java
FROM Student s JOIN s.department d
```

---

### 12.2 LEFT JOIN

```java
FROM Student s LEFT JOIN s.department d
```

---

### 12.3 FETCH JOIN 

```java
SELECT d FROM Department d JOIN FETCH d.students
```

Purpose:

* Fetch parent + child in **single query**
* Avoid N+1 problem

---

## 13. Pagination in HQL

Hibernate provides built-in pagination support.

```java
Query<Student> query =
    session.createQuery("FROM Student", Student.class);

query.setFirstResult(0);   // offset
query.setMaxResults(10);  // limit

List<Student> list = query.list();
```

Equivalent SQL:

```sql
LIMIT 10 OFFSET 0
```

---

## 14. HQL and Caching

### Enable Query Cache

```java
Query query = session.createQuery("FROM Student");
query.setCacheable(true);
```

* Requires **Second-Level Cache**
* Stores query result IDs
* Entities fetched from cache if cacheable

---

## 15. HQL vs Criteria API vs Native SQL

| Feature             | HQL          | Criteria API    | Native SQL                  |
| ------------------- | ------------ | --------------- | --------------------------- |
| Type                | String-based | Type-safe       | SQL                         |
| DB Independent      | Yes          | Yes             | No                          |
| Readability         | High         | Medium          | High                        |
| Compile-time Safety | No           | Yes             | No                          |
| Use Case            | Most common  | Dynamic queries | Complex DB-specific queries |

---

## 16. Complete Example

```java
Session session = HibernateUtil.getSessionFactory().openSession();

Query<Student> query =
    session.createQuery(
        "FROM Student WHERE age > :age ORDER BY name",
        Student.class
    );

query.setParameter("age", 20);

List<Student> students = query.getResultList();

session.close();
```

---

## 18. Interview Questions

### Q1. What is HQL?

An object-oriented query language that works on Hibernate entities, not tables.

---

### Q2. Difference between HQL and SQL?

HQL uses entity names and fields; SQL uses tables and columns.

---

### Q3. Is HQL database independent?

Yes. Hibernate converts HQL into DB-specific SQL.

---

### Q4. Does HQL support joins?

Yes, object-oriented joins using associations.

---

### Q5. Can we perform update/delete using HQL?

Yes, using bulk queries (`executeUpdate()`).

---

### Q6. What is FETCH JOIN?

A join that loads related entities in the same query to avoid N+1 problem.

---

### Q7. Does HQL support pagination?

Yes, via `setFirstResult()` and `setMaxResults()`.

---

### Q8. Which is better: HQL or SQL?

HQL for ORM-based queries; SQL for DB-specific optimizations.

---

## Key Takeaways

* HQL is Hibernate’s object-oriented alternative to SQL

* Uses entity names and fields, not tables

* Automatically maps results to objects

* Supports joins, aggregation, pagination, caching

* Essential for real-world Hibernate applications

---
