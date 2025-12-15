# Complete Hibernate Workflow for Saving Objects

## 1. Hibernate Overview

Hibernate is an **ORM (Object Relational Mapping) framework** that:

* Manages database connections internally
* Converts Java objects into SQL operations
* Maps database rows back into Java objects
* Handles transactions, caching, and object states

Instead of writing repetitive JDBC code, Hibernate provides a structured workflow:

```
Configuration → SessionFactory → Session → Transaction → CRUD → Commit → Close
```

**Every Hibernate operation strictly follows this lifecycle**.

---

## 2. Hibernate Configuration

The **configuration file** defines how Hibernate connects to the database and how entities are mapped.

### Responsibilities:

* Database connection details
* JDBC driver
* SQL dialect
* Schema generation strategy
* SQL logging
* Entity registration

---

### Complete `hibernate.cfg.xml` (Hibernate 6)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>

        <!-- Database Connection -->
        <property name="hibernate.connection.driver_class">
            org.postgresql.Driver
        </property>
        <property name="hibernate.connection.url">
            jdbc:postgresql://localhost:5432/mydb
        </property>
        <property name="hibernate.connection.username">postgres</property>
        <property name="hibernate.connection.password">root</property>

        <!-- Hibernate Core Settings -->
        <property name="hibernate.dialect">
            org.hibernate.dialect.PostgreSQLDialect
        </property>
        <property name="hibernate.hbm2ddl.auto">update</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.format_sql">true</property>

        <!-- Entity Mapping -->
        <mapping class="com.example.Student"/>

    </session-factory>
</hibernate-configuration>
```

---

### 2.1 Property Explanation (Concise & Exact)

| Property            | Purpose                    |
| ------------------- | -------------------------- |
| `driver_class`      | JDBC driver                |
| `url`               | Database connection URL    |
| `username/password` | DB credentials             |
| `dialect`           | DB-specific SQL generation |
| `hbm2ddl.auto`      | Schema strategy            |
| `show_sql`          | Prints executed SQL        |
| `format_sql`        | Readable SQL               |
| `mapping`           | Registers entity           |

---

## 3. Entity Class (JPA-Compliant)

Hibernate 6 uses **Jakarta Persistence API**.

### `Student.java`

```java
import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;

    // getters and setters
}
```

### Key Points

* `@Entity` → Hibernate-managed class
* `@Id` → Primary key
* `@GeneratedValue` → Auto-generated ID
* Fields map to columns automatically

Hibernate reads this metadata to generate SQL dynamically.

---

## 4. Bootstrapping Hibernate

### `SessionFactory` Characteristics

* Heavyweight
* Thread-safe
* Created **once**
* Stores metadata, SQL templates, mappings

---

### `HibernateUtil` (Standard Pattern)

```java
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
```

---

## 5. Hibernate Session

A **Session** represents a **unit of work** with the database.

### Responsibilities

* Opens DB connection
* Creates transactions
* Manages entity states
* Performs CRUD operations
* Tracks changes (dirty checking)

> ❗ Not thread-safe

> ❗ One session per request / operation

---

## 6. Hibernate Transaction

Hibernate write operations **must** run inside a transaction.

```java
Transaction tx = session.beginTransaction();
```

### Why Transactions Matter

* Guarantees atomicity
* Prevents partial updates
* Required for INSERT / UPDATE / DELETE

Hibernate internally delegates to JDBC transactions.

---

## 7. Complete Hibernate Saving Workflow

### 7.1 Saving an Object (INSERT)

1. Open Session
2. Begin Transaction
3. Create entity
4. Call `persist()`
5. Commit transaction
6. Close session

---

#### Code Example

```java
Session session = HibernateUtil.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();

Student s = new Student();
s.setName("John");
s.setAge(22);

session.persist(s);   // JPA-compliant

tx.commit();
session.close();
```

---

### What Hibernate Does Internally

| Step           | Action                    |
| -------------- | ------------------------- |
| Mapping lookup | Reads entity metadata     |
| SQL generation | Builds INSERT statement   |
| Flush          | Executes SQL on commit    |
| ID assignment  | Sets generated ID         |
| State change   | Entity becomes Persistent |

---

### 7.2 Fetching an Object (SELECT)

#### `get()` – Immediate Fetch

```java
Student s = session.get(Student.class, 1);
```

* Executes SQL immediately
* Returns `null` if not found

#### `load()` – Lazy Proxy

```java
Student s = session.load(Student.class, 1);
```

* Returns proxy
* Hits DB only on property access
* Throws exception if record doesn’t exist

**❗️❗️Both of these methods are deprecated in favor of `find()` in JPA.**

#### `find` – Recommended Fetch

```java
Student s = session.find(Student.class, 1);
```

* JPA-compliant
* Immediate fetch

---

### 7.3 Updating an Object (UPDATE)

```java
Session session = HibernateUtil.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();

Student s = session.get(Student.class, 1);
s.setAge(30);   // Hibernate tracks change

tx.commit();
session.close();
```

#### Why `update()` is Optional

Hibernate automatically:

* Detects modified fields
* Generates UPDATE SQL at commit

**❗️ update() method is deprecated in favor of automatic dirty checking.**

---

### 7.4 Updating Detached Objects (`merge()`)

```java
Student detachedStudent = new Student();
detachedStudent.setId(1);
detachedStudent.setAge(35);

Session session = HibernateUtil.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();

session.merge(detachedStudent);

tx.commit();
session.close();
```

#### `merge()` Use Case

* Detached entities
* REST / UI data
* Session already closed

---

### 7.5 Deleting an Object (DELETE)

```java
Session session = HibernateUtil.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();

Student s = session.get(Student.class, 1);
session.remove(s);   // JPA-compliant delete

tx.commit();
session.close();
```

---

## 8. Hibernate Entity States

### 1. Transient

```java
Student s = new Student();
```

* Not tracked
* No DB relation

### 2. Persistent

```java
session.persist(s);
```

* Tracked by Hibernate
* Auto updates via dirty checking

### 3. Detached

```java
session.close();
```

* No longer tracked
* Changes not saved unless merged

---

## 9. Hibernate Internal Execution Flow

```
Application Code
      ↓
Session.persist(entity)
      ↓
Hibernate ORM Engine
      ↓
SQL Generation
      ↓
JDBC Driver
      ↓
Database
      ↓
Result Mapping
```

Hibernate **uses JDBC internally**, it does not replace it.

---

## JDBC vs Hibernate Saving Procedure

| Step           | JDBC                     | Hibernate                 |
| -------------- | ------------------------ | ------------------------- |
| Configuration  | Manual driver load + URL | hibernate.cfg.xml         |
| Connection     | DriverManager            | SessionFactory            |
| Session        | Connection object        | Session object            |
| SQL Query      | You write SQL            | Hibernate generates SQL   |
| Execution      | PreparedStatement        | session.save(), update()  |
| Transaction    | Manual                   | Automatic or manual       |
| Object Mapping | Manual                   | Automatic via annotations |

---

## Summary of Key Components

| Component           | Role                              |
| ------------------- | --------------------------------- |
| `hibernate.cfg.xml` | DB & ORM configuration            |
| `SessionFactory`    | Heavy, thread-safe                |
| `Session`           | Unit of work                      |
| `Transaction`       | Required for writes               |
| `persist()`         | INSERT                            |
| `merge()`           | Update detached entities          |
| `get()` / `load()`  | SELECT                            |
| `remove()`          | DELETE                            |
| Entity States       | Transient → Persistent → Detached |

---
