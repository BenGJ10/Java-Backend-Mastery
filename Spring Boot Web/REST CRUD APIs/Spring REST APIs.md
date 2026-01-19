# Spring REST CRUD APIs

## 1. Overview

In Spring Boot, **RESTful CRUD APIs** are the foundation of backend services.
CRUD represents the four core operations on resources:

* **Create**
* **Read**
* **Update**
* **Delete**

Spring Boot provides strong support for building REST APIs using:

* Spring MVC
* Jackson (JSON serialization)
* Validation
* Exception handling
* HTTP semantics

These APIs are heavily used in **microservices, monolith backends, and cloud-native systems**.

---

## 2. REST Resource Design

REST APIs are designed around **resources**, not actions.

Example resource:

```
/users
```

Each resource is manipulated using standard HTTP methods.

| Operation    | HTTP Method | Endpoint    |
| ------------ | ----------- | ----------- |
| Create       | POST        | /users      |
| Read (all)   | GET         | /users      |
| Read (by id) | GET         | /users/{id} |
| Update       | PUT / PATCH | /users/{id} |
| Delete       | DELETE      | /users/{id} |

---

## 3. Layered Architecture for CRUD APIs

Typical Spring Boot backend flow:

```
Controller → Service → Repository/DAO → Database
```

Each layer has a single responsibility.

---

## 4. Entity Design

```java
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
}
```

Entity represents database structure.

---

## 5. DTO for API Communication

DTO prevents exposing entity directly.

```java
public class UserDTO {
    private Long id;
    private String name;
    private String email;
}
```

---

## 6. Repository Layer

```java
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

Spring generates implementation automatically.

---

## 7. Service Layer

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO create(UserDTO dto) {
        User user = new User(dto.getName(), dto.getEmail());
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }
}
```

Service layer holds business logic.

---

## 8. REST Controller

```java
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }
}
```

Controller handles HTTP requests only.

---

## 9. Update Operations

### Full Update (PUT)

```java
@PutMapping("/{id}")
public UserDTO update(
        @PathVariable Long id,
        @RequestBody UserDTO dto) {

    return userService.update(id, dto);
}
```

PUT replaces the entire resource.

---

### Partial Update (PATCH)

```java
@PatchMapping("/{id}")
public UserDTO partialUpdate(
        @PathVariable Long id,
        @RequestBody Map<String, Object> updates) {

    return userService.partialUpdate(id, updates);
}
```

PATCH updates specific fields.

---

## 10. Delete Operation

```java
@DeleteMapping("/{id}")
public void delete(@PathVariable Long id) {
    userService.delete(id);
}
```

HTTP 204 (No Content) is usually returned.

---

## 11. HTTP Status Codes (Important)

| Scenario           | Status                    |
| ------------------ | ------------------------- |
| Resource created   | 201 Created               |
| Successful fetch   | 200 OK                    |
| Successful delete  | 204 No Content            |
| Invalid request    | 400 Bad Request           |
| Resource not found | 404 Not Found             |
| Server error       | 500 Internal Server Error |

Best practice: Use `ResponseEntity`.

---

## 12. Validation in CRUD APIs

```java
public class UserDTO {

    @NotBlank
    private String name;

    @Email
    private String email;
}
```

```java
@PostMapping
public UserDTO create(@Valid @RequestBody UserDTO dto) {
    return userService.create(dto);
}
```

Validation prevents bad data from reaching database.

---

## 13. Exception Handling

Centralized exception handling using `@ControllerAdvice`.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
```

Improves API consistency.

---

## 14. Real-World Considerations

### Pagination

```java
@GetMapping
public Page<UserDTO> getAll(Pageable pageable) {
    return userRepository.findAll(pageable)
            .map(this::mapToDTO);
}
```

### Sorting

```
/users?sort=name,asc
```

### Filtering

Handled via query params or specification APIs.

---

## 15. REST API Best Practices

* Use nouns, not verbs
* Proper HTTP methods
* Proper status codes
* Do not expose entities
* Use DTOs
* Centralized error handling
* Version APIs if required

---

## 16. Common Interview Questions

### Q1: Difference between PUT and PATCH?

PUT replaces entire resource, PATCH partially updates.

---

### Q2: Should controller talk to repository directly?

No — always go through service layer.

---

### Q3: Why DTO is needed?

To decouple API contract from database schema.

---

### Q4: Where should validation be placed?

At API boundary (DTO).

---

## Key Takeaways

* CRUD APIs form the backbone of backend systems

* Spring Boot simplifies REST API development

* Follow layered architecture

* Use DTOs and service layer

* Handle validation and exceptions centrally

* Align APIs with REST principles

---
