# Data Access Objects (DAO)

## 1. Overview

**DAO (Data Access Object)** is a **design pattern** used to separate **persistence logic** from **business logic**.

The DAO pattern provides an abstraction layer between:

* Application / Service Layer
* Database / Persistence Layer

This separation is extremely important in **enterprise backend systems**, especially when working with **JDBC, JPA, Hibernate, or Spring Data**.

---

## 2. Why DAO Pattern is Needed

Without DAO, database logic gets mixed with business logic, leading to:

* tightly coupled code
* difficult testing
* poor maintainability
* hard database migrations

DAO solves this by **encapsulating all data access operations**.

---

## 3. DAO Pattern Structure

Typical layered architecture:

```
Controller
   ↓
Service Layer
   ↓
DAO Layer
   ↓
Database
```

DAO acts as the **single point of interaction** with the database.

---

## 4. Core Responsibilities of DAO

A DAO is responsible for:

* CRUD operations
* Query execution
* Mapping database records to objects
* Handling persistence-specific exceptions

It should **not** contain business logic.

---

## 5. DAO Pattern Components

### 5.1 Entity / Model

Represents a database table.

```java
class User {
    private Long id;
    private String email;
    private String name;
}
```

---

### 5.2 DAO Interface

Defines **what operations are allowed**, not how.

```java
public interface UserDao {
    User findById(Long id);
    void save(User user);
    void delete(Long id);
}
```

This provides **abstraction** and **loose coupling**.

---

### 5.3 DAO Implementation

Contains actual database logic.

#### JDBC-based DAO Example

```java
public class UserDaoImpl implements UserDao {

    private final DataSource dataSource;

    public UserDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User findById(Long id) {
        // JDBC logic
        return null;
    }

    public void save(User user) {
        // insert SQL
    }

    public void delete(Long id) {
        // delete SQL
    }
}
```

---

## 6. DAO with JPA / Hibernate

In JPA-based applications, DAO usually uses **EntityManager**.

```java
@Repository
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public void save(User user) {
        entityManager.persist(user);
    }

    public void delete(User user) {
        entityManager.remove(user);
    }
}
```

Spring manages transactions and lifecycle.

---

## 7. DAO vs Repository (Important Interview Topic)

| Aspect      | DAO                      | Repository                |
| ----------- | ------------------------ | ------------------------- |
| Pattern     | Design Pattern           | Spring Data abstraction   |
| Scope       | Persistence logic        | Domain-driven persistence |
| Boilerplate | High                     | Very low                  |
| Technology  | JDBC, JPA, Hibernate     | Spring Data JPA           |
| Control     | Full                     | Limited                   |
| Common in   | Legacy / complex systems | Modern Spring Boot apps   |

In Spring Boot, **JpaRepository often replaces DAO**, but DAO pattern still exists conceptually.

---

## 8. Real-World Backend Example

### Scenario: Order Management System

Service layer should not know:

* SQL queries
* table joins
* database vendor

#### DAO Interface

```java
public interface OrderDao {
    Order findOrder(Long id);
}
```

#### DAO Implementation

```java
@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Order findOrder(Long id) {
        return entityManager.find(Order.class, id);
    }
}
```

#### Service Layer

```java
@Service
public class OrderService {

    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
}
```

Database can change (MySQL → PostgreSQL) without touching service logic.

---

## 9. DAO and Transaction Management

DAO methods usually execute inside transactions managed by:

* Spring `@Transactional`
* JTA in enterprise systems

Best practice:

* Transactions at **service layer**
* DAO remains persistence-focused

```java
@Transactional
public void createOrder(Order order) {
    orderDao.save(order);
}
```

---

## 10. DAO Testing Advantage

DAO abstraction allows:

* mocking database layer
* in-memory databases (H2)
* repository stubbing

Example:

```java
UserDao userDao = mock(UserDao.class);
```

Service logic can be tested independently.

---

## 11. Common Mistakes with DAO

* Putting business logic inside DAO
* Exposing EntityManager directly to services
* Large, unmaintainable DAO classes
* Mixing transaction boundaries inside DAO

---

## 12. DAO vs Service Layer

| DAO                 | Service                |
| ------------------- | ---------------------- |
| Handles data access | Handles business logic |
| Talks to database   | Talks to DAO           |
| No business rules   | Applies business rules |

Never combine both.

---

## 13. DAO in Modern Spring Boot

In modern Spring Boot:

* `JpaRepository` acts as DAO
* Custom DAO used for complex queries
* EntityManager used for fine control

Hybrid approach is common in production systems.

---

## 14. Interview Questions

### Q1: What is DAO?

A pattern that separates persistence logic from business logic.

---

### Q2: Is DAO still used with Spring Data JPA?

Yes — conceptually. JpaRepository is a DAO abstraction.

---

### Q3: Where should transactions be placed?

Service layer.

---

### Q4: Can DAO contain business logic?

No.

---

### Q5: DAO vs Repository?

DAO is generic; Repository is domain-driven and Spring-specific.

---

## Key Takeaways

* DAO abstracts database access

* Promotes loose coupling and clean architecture

* Simplifies testing and maintenance

* Works with JDBC, JPA, Hibernate

* Still relevant in modern backend systems

* Often replaced by JpaRepository in Spring Boot, but concept remains essential

---
