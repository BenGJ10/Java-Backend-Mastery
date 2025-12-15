# Introduction to Hibernate

## 1. What Is Hibernate?

**Hibernate** is a popular **ORM (Object-Relational Mapping)** framework in Java.
ORM allows developers to map Java objects to relational database tables, eliminating the need to write repetitive SQL.

Hibernate handles:

* Database CRUD operations
* Mapping Java classes ↔ database tables
* Managing relationships between objects
* Transactions
* Connection handling
* Caching
* Lazy loading

Hibernate is part of the **JPA ecosystem**, but is more feature-rich and widely used.

---

## 2. Why Hibernate?

Hibernate simplifies database operations. Here are key benefits:

### 1. **No SQL Needed for Basic Operations**

Hibernate auto-generates SQL for:

* Insert
* Update
* Delete
* Select

```java
session.save(entity);
```

### 2. **Database Independence**

Switching from MySQL → PostgreSQL → Oracle requires almost no code change.

### 3. **Automatic Table Mapping**

Java classes map to tables using annotations like:

```java
@Entity
@Table(name = "users")
```

### 4. **Lazy Loading Support**

Hibernate loads only required data; rest loads when needed.

### 5. **Caching**

Built-in caching improves performance significantly.

### 6. **Transaction Management**

Integrates with JTA and Spring for transaction control.

### 7. **HQL (Hibernate Query Language)**

Supports object-oriented queries:

```java
Query q = session.createQuery("from User where age > 20");
```

---

## 3. What Is ORM?

**ORM**(Object-Relational Mapping) is a programming technique for converting data between incompatible type systems (OOP and RDBMS).

ORM bridges the gap between:

| OOP Concept | RDBMS Concept   |
| ----------- | --------------- |
| Object      | Row             |
| Class       | Table           |
| Field       | Column          |
| Association | FK Relationship |

Hibernate generates SQL behind the scenes to synchronize objects with database rows.

---

## 4. How Hibernate Works

Hibernate internally uses multiple components:

```
Application
     ↓
Hibernate API (Session/SessionFactory)
     ↓
Hibernate Engine
     ↓
JDBC
     ↓
Database
```

#### Workflow:

1. Configure Hibernate (XML or Annotations)
2. Create `SessionFactory` (heavy, created once)
3. Open a `Session` (lightweight)
4. Begin transaction
5. Perform CRUD (`save`, `update`, `delete`, `get`)
6. Commit transaction
7. Close session

---

## 5. Core Hibernate Components

### 5.1 Configuration

Defines:

* Database URL
* Credentials
* Dialect
* Mapped classes
* Caching setup

Using `hibernate.cfg.xml` or Java configuration.

### 5.2 SessionFactory

* A **singleton** for the entire application
* Thread-safe
* Heavy object (created once at startup)

Creates `Session` instances.

### 5.3 Session

* Represents a connection with the database
* Manages CRUD operations
* Not thread-safe

Example:

```java
Session session = sessionFactory.openSession();
```

### 5.4 Transaction

Ensures atomicity:

```java
Transaction tx = session.beginTransaction();
tx.commit();
```

### 5.5 Query Engine

Supports:

* HQL
* Criteria API
* Native SQL

---

## 6. Hibernate Annotations (Basics)

### Example Entity Class:

```java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_name")
    private String name;

    private int age;

    // getters and setters
}
```

### Annotation Breakdown:

| Annotation        | Meaning                   |
| ----------------- | ------------------------- |
| `@Entity`         | Marks class as persistent |
| `@Table`          | Maps to table             |
| `@Id`             | Primary key               |
| `@GeneratedValue` | Auto-increment            |
| `@Column`         | Maps field to column      |

---

## 7. Basic Hibernate CRUD Example

```java
Session session = sessionFactory.openSession();
Transaction tx = session.beginTransaction();

Student s = new Student();
s.setName("John");
s.setAge(22);

session.save(s); // INSERT operation

tx.commit();
session.close();
```

---

## 8. Advantages and Disadvantages

| Advantages                     | Disadvantages                  |
| ------------------------------ | ------------------------------ |
| Reduces boilerplate SQL        | Learning curve                 |
| Database independence          | Performance overhead           |
| Caching & lazy loading         | Debugging complexity           |
| Transaction management         | Less control over SQL          |
| Rich query options (HQL, etc.) | May not suit simple use cases  |

---

## 9. When Should You Use Hibernate?

**Use Hibernate when**:

* Working with relational databases
* You want to avoid SQL boilerplate
* You need mapping between Java objects & tables
* You need caching & lazy loading
* You want database portability

**Avoid Hibernate when**:

* Extreme performance tuning is required
* Using NoSQL databases
* SQL queries are highly optimized and handwritten

---

## 10. Interview Questions

### Q1. What is Hibernate?

An ORM framework that maps Java objects to database tables and provides CRUD operations without writing SQL.

---

### Q2. Difference between Hibernate and JDBC?

| JDBC                          | Hibernate                |
| ----------------------------- | ------------------------ |
| Manual connection mgmt        | Automatic                |
| SQL required                  | No SQL for basic ops     |
| No caching                    | Built-in caching         |
| Converts result sets manually | Auto converts to objects |

---

### Q3. What is Hibernate Session?

A single-threaded object that represents a connection and handles CRUD operations.

---

### Q4. What is SessionFactory?

A heavyweight, thread-safe object that creates session instances.

---

### Q5. What is lazy loading?

Loads the related data only when accessed.

---

### Q6. What are entity annotations?

`@Entity`, `@Table`, `@Id`, `@Column`, etc.

---

### Q7. What is HQL?

Hibernate Query Language — object-oriented alternative to SQL.

---

### Q8. What is the difference between HQL and SQL?

HQL uses entity class names, not table names.

---

### Q9. What is the purpose of `@Id`?

Marks a field as the primary key in the table.

---

### Q10. Is Hibernate better than JDBC?

It depends on use case. Hibernate improves productivity and maintainability but JDBC can be faster for simple SQL-heavy operations.

---

## Key Takeaways

* Hibernate is a powerful **ORM** framework for mapping Java objects to database tables.

* Eliminates SQL boilerplate and provides high-level CRUD operations.

* Supports annotations, lazy loading, caching, HQL, Criteria API, and transaction management.

* Integrates with JPA and Spring for enterprise development.

---
