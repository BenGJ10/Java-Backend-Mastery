# Controller, Service, Repository Layer

In real Spring/Spring Boot applications, code is organized into **layers**.
This layered architecture improves **separation of concerns, testability, maintainability, and scalability**.

The three most important layers are:

1. **Controller Layer** – handles HTTP/API requests
2. **Service Layer** – contains business logic
3. **Repository Layer** – handles database interaction

Together they form a clean flow:

```
Client → Controller → Service → Repository → Database
```

---

## 1. Controller Layer

### What is the Controller Layer?

The **controller layer** handles:

* HTTP requests (GET, POST, PUT, DELETE)
* request validation
* response building (JSON/XML)
* API endpoint exposure

It **does NOT contain business logic or database code**.

> Its job: **Take input → Call service → Return response**

---

### Key Annotations

| Annotation        | Meaning                            |
| ----------------- | ---------------------------------- |
| `@Controller`     | MVC controller (returns views)     |
| `@RestController` | REST API controller (returns JSON) |
| `@RequestMapping` | Base URL mapping                   |
| `@GetMapping`     | HTTP GET                           |
| `@PostMapping`    | HTTP POST                          |
| `@PutMapping`     | HTTP PUT                           |
| `@DeleteMapping`  | HTTP DELETE                        |

---

### Example Controller

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
        return userService.getUserById(id);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }
}
```

### Responsibilities of Controller Layer

* Map URLs to methods
* Parse request body and path variables
* Validate input
* Convert objects to JSON responses
* Handle HTTP status codes (optionally)

### What Controller Should NOT Do

* ❌ No database calls
* ❌ No core business logic
* ❌ No transactions

---

## 2. Service Layer

### What is the Service Layer?

Service layer contains **business logic** of the application.

It answers:

* **How** data should be processed?
* **What rules apply?**
* **What validations exist?**
* **What workflows exist?**

This layer coordinates repositories and applies logic.

---

### Key Annotation

| Annotation | Meaning                    |
| ---------- | -------------------------- |
| `@Service` | Marks business logic class |

---

### Example Service Layer

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getId(), user.getName());
    }

    public UserDTO createUser(UserDTO dto) {
        User user = new User(dto.getName());
        User saved = userRepository.save(user);
        return new UserDTO(saved.getId(), saved.getName());
    }
}
```

---

### Responsibilities of Service Layer

* Business logic
* Calculations / rules / workflows
* Transaction management
* Calling multiple repositories
* Calling external services (APIs, queues, etc.)
* Security checks (in many cases)

---

### What Service Should NOT Do

* ❌ Handle HTTP request/response
* ❌ Direct SQL query execution (repository job)
* ❌ UI logic

---

## 3. Repository Layer

### What is the Repository Layer?

This layer communicates with the **database**.

It performs:

* CRUD operations
* Running queries
* Managing persistence
* Mapping entities

---

### Key Annotations

| Annotation    | Meaning                       |
| ------------- | ----------------------------- |
| `@Repository` | Marks DAO / persistence layer |

---

### Repository Example (Spring Data JPA)

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
}
```

Spring auto-implements this — no need to write SQL.

---

### Responsibilities of Repository Layer

* Perform DB operations
* Provide abstraction over persistence
* Wrap exceptions into Spring DataAccessException
* Handle entity state (persist, merge, delete)

---

### What Repository Should NOT Do

* ❌ Business logic
* ❌ HTTP handling
* ❌ Response creation

---

## 4. Putting It All Together

```
Client (Postman / Browser / Mobile App)
        │
        ▼
Controller Layer (API Layer)
        │
        ▼
Service Layer (Business Logic)
        │
        ▼
Repository Layer (Database Access)
        │
        ▼
Database (MySQL / PostgreSQL / MongoDB etc.)
```

---

## 5. Full Working Example

### Entity Class

```java
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public User() {}
    public User(String name) { this.name = name; }
}
```

---

### Repository

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> { }
```

---

### Service

```java
@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User saveUser(String name) {
        return repo.save(new User(name));
    }
}
```

---

### Controller

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/{name}")
    public User createUser(@PathVariable String name) {
        return service.saveUser(name);
    }
}
```

---

## 6. Why Layered Architecture?

### Benefits

* Clear separation of concerns
* Easy unit testing
* Reusable services
* Replaceable database layer
* Easier debugging
* Maintainable and scalable codebase
* Follows clean architecture principles

---

## 7. Common Interview Questions

### Q1. Why do we need separate service layer?

To isolate **business logic** from controllers and repositories.

---

### Q2. Can controller directly access repository?

Yes technically — but **bad practice** and breaks layered architecture.

---

### Q3. Difference between @Service and @Repository?

* `@Service` → business logic
* `@Repository` → persistence logic & exception translation

---

### Q4. Why use @RestController instead of @Controller?

`@RestController` = `@Controller + @ResponseBody`
Automatically returns JSON instead of view.

---

### Q5. Can service call another service?

Yes, for modular business workflows.

---

## Key Takeaways

* Controller → handles HTTP/API

* Service → contains business logic

* Repository → handles database operations

* Separation of layers → clean, testable architecture

s* Standard structure used in **Spring Boot microservices & enterprise apps**

---

