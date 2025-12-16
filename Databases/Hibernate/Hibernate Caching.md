# Hibernate Caching

Hibernate improves performance by reducing the number of database hits through **caching**.
It stores frequently accessed data in memory, avoiding redundant SELECT queries.

Hibernate has **three caching layers**:

1. **First-Level Cache** (Session Cache — mandatory, built-in)
2. **Second-Level Cache** (SessionFactory-level — optional)
3. **Query Cache** (Optional, depends on second-level cache)

---

## 1. Why Do We Need Caching in Hibernate?

Without caching, every time you call:

```java
session.get(Student.class, 1);
```

Hibernate would execute a **SELECT** query even if the data hasn’t changed.

Caching helps:

* Minimize database round-trips
* Improve performance
* Reduce latency
* Reduce load on the database

---

## 2. First-Level Cache (L1 Cache)

**Scope:** Per Session
**Enabled:** Always enabled (cannot disable)
**Purpose:** Ensures that within a session, the same entity is not fetched repeatedly from DB.

---

### 2.1 How It Works

Example:

```java
Session session = sessionFactory.openSession();

Student s1 = session.get(Student.class, 1); // Hits DB (1st time)
Student s2 = session.get(Student.class, 1); // Served from cache (no DB hit)
```

### Output:

* Only **1 SQL SELECT** is executed.

Hibernate stores the entity in the **Session cache** after the first fetch.

---

### 2.2 When First-Level Cache Clears?

* When `session.close()` is called
* When `session.clear()` is called
* When `session.evict(entity)` is called
* On transaction commit (depending on flush mode)

---

### 2.3 Evicting Cache Manually

```java
session.evict(s1); // removes single object from cache
```

---

## 3. Second-Level Cache (L2 Cache)

**Scope:** Per SessionFactory (shared among sessions)
**Enabled:** Optional
**Purpose:** Allows caching across multiple sessions (application-level caching)

Used for:

* Entities
* Collections (OneToMany lists)
* Queries (if enabled)

Second-level caching must be explicitly configured and requires an external cache provider.

---

### 3.1 Popular Second-Level Cache Providers

| Provider       | Description                          |
| -------------- | ------------------------------------ |
| **Ehcache**    | Most commonly used; simple, powerful |
| **Hazelcast**  | Distributed cache                    |
| **Infinispan** | JBoss caching framework              |
| **OSCache**    | Older option                         |
| **Redis**      | Used via integration libraries       |

---

### 3.2 How L2 Cache Works

```
Session1           Session2
   ↓                 ↓
   L1 Cache          L1 Cache
         ↘         ↙
        Second-Level Cache
               ↓
           Database
```

* First time entity loads → stored in **L1 + L2**
* Next session loads same entity → fetched from **L2**, not DB

---

### 3.3 Enabling Second-Level Cache

#### Step 1: Enable Cache in `hibernate.cfg.xml`

```xml
<property name="hibernate.cache.use_second_level_cache">true</property>
<property name="hibernate.cache.region.factory_class">
    org.hibernate.cache.ehcache.EhCacheRegionFactory
</property>
```

#### Step 2: Enable Query Cache (optional)

```xml
<property name="hibernate.cache.use_query_cache">true</property>
```

---

#### Step 3: Annotate Entity Classes

```java
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Student {
    @Id
    private int id;

    private String name;
}
```

---

### 3.4 Cache Concurrency Strategies

| Strategy                 | Use Case                                  |
| ------------------------ | ----------------------------------------- |
| **READ_ONLY**            | Data never changes (lookup tables)        |
| **READ_WRITE**           | Data changes and requires synchronization |
| **NONSTRICT_READ_WRITE** | Slightly stale data acceptable            |
| **TRANSACTIONAL**        | Uses JTA transaction caching              |

---

### 3.5 Example of Second-Level Cache Behavior

#### Session 1:

```java
Student s1 = session1.get(Student.class, 1); 
// Hits DB → stored in L2 cache
session1.close();
```

#### Session 2:

```java
Student s2 = session2.get(Student.class, 1); 
// Loaded from Second-Level Cache (no DB hit)
```

---

## 4. Query Cache (Optional)

Query cache stores:

* SQL query results (list of identifiers)
* Uses second-level cache internally

### Must be enabled explicitly:

```xml
<property name="hibernate.cache.use_query_cache">true</property>
```

---

### 4.1 Example

```java
Query q = session.createQuery("from Student");
q.setCacheable(true);
List<Student> list = q.list();
```

### First execution:

* SELECT executed
* Result stored in query cache

### Second execution:

* Returned from query cache **without SQL execution**

> Note: Entities referenced in query results must also be cacheable.

---

## 5. Caching Summary Table

| Cache Level     | Scope              | Default   | Stores                 | Provider Required?  | Purpose                                 |
| --------------- | ------------------ | --------- | ---------------------- | ------------------- | --------------------------------------- |
| **L1 Cache**    | Per Session        | Always On | Entities               | No                  | Avoid duplicate queries in same session |
| **L2 Cache**    | Per SessionFactory | Optional  | Entities + Collections | Yes                 | Share cache across sessions             |
| **Query Cache** | Application-wide   | Optional  | Query results          | Yes (depends on L2) | Cache result lists                      |

---

## 6. Which Cache Should You Use?

| Requirement                            | Best Option                      |
| -------------------------------------- | -------------------------------- |
| Avoid repeated DB hits in same session | First-Level Cache                |
| Cache objects that rarely change       | Second-Level Cache (READ_ONLY)   |
| Cache frequently queried objects       | Second-Level + Query Cache       |
| Improve scalability in microservices   | Hazelcast, Redis-backed L2 Cache |

---


## 7. Interview Questions

### Q1. What is the difference between first-level and second-level cache?

| 1st Level                 | 2nd Level                |
| ------------------------- | ------------------------ |
| Per-session               | Per SessionFactory       |
| Always enabled            | Optional                 |
| Cannot disable            | Must configure           |
| Lives only during session | Survives across sessions |
| No provider               | Needs provider           |

---

### Q2. Is first-level cache thread-safe?

No. Because a Session is not shared across threads.

---

### Q3. How does Hibernate know when to invalidate cache?

Based on:

* Cache concurrency strategy
* Entity updates (automatic eviction)

---

### Q4. When to use READ_ONLY cache?

For:

* Country list
* Currency codes
* Static lookup tables

---

### Q5. Does second-level cache store queries?

No → Query cache stores query results.

---

### Q6. What happens after session.clear()?

* First-level cache clears
* Second-level cache remains intact

---

## Key Takeaways

* Hibernate provides 3 levels of caching to reduce DB load.
* First-Level Cache is automatic and Session-scoped.
* Second-Level Cache is application-level and needs configuration.
* Query Cache stores result sets, not entities.
* Choosing the right caching strategy drastically boosts performance.

---
