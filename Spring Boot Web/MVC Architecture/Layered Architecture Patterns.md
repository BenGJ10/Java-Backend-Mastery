# Layered Architecture Patterns in Spring Boot

## 1. Overview

Modern Java backend applications rely heavily on **layered architectural patterns** to achieve:

* separation of concerns
* scalability
* maintainability
* testability

Among the most important and commonly asked patterns are:

* **MVC (Model–View–Controller)**
* **DAO (Data Access Object)**
* **DTO (Data Transfer Object)**
* **Service Layer**
* **Repository**
* **Mapper (Assembler)**
* **Facade**

These patterns are often **used together**, not in isolation.

---

## 2. MVC (Model–View–Controller)

### 2.1 What is MVC?

MVC is an architectural pattern that separates application responsibilities into three layers:

```
Controller → Service → DAO → Database
```

(Modern Spring MVC adapts this structure slightly.)

### 2.2 MVC Components

| Component  | Responsibility                   |
| ---------- | -------------------------------- |
| Model      | Domain data and business state   |
| View       | Presentation layer (JSON / HTML) |
| Controller | Handles requests and responses   |

---

### 2.3 MVC in Spring Boot (REST Example)

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
```

Key points:

* Controller handles **HTTP**
* No business logic
* No database access

---

### 2.4 Why MVC is Important

* Clean separation of concerns
* Enables REST APIs and web apps
* Easier debugging and testing
* Industry-standard pattern

---

## 3. DAO (Data Access Object)

### 3.1 What is DAO?

DAO abstracts **database interaction logic**.

Service layer never talks directly to the database.

```
Service → DAO → Database
```

---

### 3.2 DAO Example

```java
public interface UserDao {
    User findById(Long id);
    void save(User user);
}
```

```java
@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }
}
```

---

### 3.3 Why DAO Exists

* Isolates persistence logic
* Supports database switching
* Improves testability
* Reduces coupling

In Spring Boot, **JpaRepository often replaces DAO**, but the pattern still exists conceptually.

---

## 4. DTO (Data Transfer Object)

### 4.1 What is DTO?

DTO is used to **transfer data between layers or over the network**.

It prevents:

* exposing internal entities
* over-fetching data
* security leaks

---

### 4.2 Why DTO is Necessary

Problems with exposing entities directly:

* lazy loading issues
* circular references
* security risks
* tight coupling between API and DB schema

---

### 4.3 DTO Example

```java
public class UserDTO {
    private Long id;
    private String email;
}
```

Service returns DTO, not entity.

```java
public UserDTO getUser(Long id) {
    User user = userDao.findById(id);
    return UserMapper.toDTO(user);
}
```

---

## 5. Mapper / Assembler Pattern

### 5.1 What is Mapper?

Mapper converts:

```
Entity ↔ DTO
```

Keeps transformation logic **out of service and controller**.

---

### 5.2 Mapper Example

```java
public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
```

In real projects, libraries like **MapStruct** are often used.

---

## 6. Service Layer Pattern

### 6.1 What is Service Layer?

Service layer contains **business logic** and orchestrates:

* validation
* transactions
* multiple DAOs
* external services

---

### 6.2 Service Example

```java
@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public UserDTO getUser(Long id) {
        User user = userDao.findById(id);
        return UserMapper.toDTO(user);
    }
}
```

---

### 6.3 Why Service Layer Matters

* Centralizes business rules
* Transaction boundary control
* Reusable logic
* Cleaner controllers

---

## 7. Repository Pattern

### 7.1 Repository vs DAO

| Aspect      | DAO               | Repository           |
| ----------- | ----------------- | -------------------- |
| Focus       | Persistence logic | Domain-driven access |
| Abstraction | Low-level         | High-level           |
| Framework   | Generic           | Spring Data          |
| Boilerplate | High              | Low                  |

Example:

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
```

Repository acts as a **DAO abstraction** in Spring Boot.

---

## 8. Facade Pattern (Service Aggregation)

### 8.1 What is Facade?

Facade provides a **simplified interface** over complex subsystems.

Used in:

* orchestration services
* microservice gateways
* complex workflows

---

### 8.2 Facade Example

```java
@Service
public class OrderFacade {

    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    public void placeOrder(OrderDTO order) {
        inventoryService.reserve(order);
        paymentService.pay(order);
    }
}
```

Controller talks to Facade instead of multiple services.

---

## 9. How These Patterns Work Together

### Typical Spring Boot Flow

```
Client
 ↓
Controller (MVC)
 ↓
Service Layer
 ↓
DAO / Repository
 ↓
Database
```

DTO used between:

* Controller ↔ Service
* Service ↔ Client

Mapper handles transformations.

---

## 10. Common Anti-Patterns to Avoid

* Controller accessing DAO directly
* Exposing Entity in API response
* Business logic inside DAO
* No service layer
* Fat controllers

---

## 11. Interview Questions

### Q1: Why use DTO instead of Entity?

To avoid exposing internal database structure and for security.

---

### Q2: Is DAO still relevant with Spring Data JPA?

Yes, conceptually. JpaRepository is a DAO abstraction.

---

### Q3: Where should transactions be handled?

Service layer.

---

### Q4: Can Controller contain business logic?

No.

---

### Q5: Which pattern improves loose coupling the most?

Service + DAO + DTO combination.

---

## 12. Key Takeaways

* MVC separates request handling from business logic

* DAO abstracts persistence layer

* DTO protects API contracts

* Service layer contains business rules

* Mapper converts between layers

* Repository simplifies data access

* Patterns are **used together**, not individually

* These patterns form the backbone of real-world Java backend systems

---

