# Spring Boot Exception Handling

## 1. Overview

**Exception handling** in Spring Boot is a critical backend concern that ensures:

* consistent API responses
* proper HTTP status codes
* clean separation of error-handling logic
* better debugging and observability

Spring Boot provides **centralized and declarative mechanisms** to handle exceptions instead of scattering `try-catch` blocks across controllers.

---

## 2. Why Centralized Exception Handling is Needed

Problems without proper exception handling:

* inconsistent error responses
* leaked stack traces or sensitive details
* duplicated try-catch logic
* hard-to-maintain controllers

Centralized handling solves this by moving all error logic to a **single place**.

---

## 3. Default Exception Handling in Spring Boot

Out of the box, Spring Boot:

* converts exceptions to HTTP responses
* returns generic error JSON
* exposes `/error` endpoint internally

Example default response:

```json
{
  "timestamp": "2026-01-18T10:20:30.123",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/users/1"
}
```

This is **not ideal for production APIs**.

---

## 4. Types of Exceptions in Spring Applications

### 4.1 Checked Exceptions

* Compile-time checked
* Rarely used in Spring REST APIs

### 4.2 Runtime Exceptions

* Most common in Spring Boot
* Examples:

  * `NullPointerException`
  * `IllegalArgumentException`
  * `EntityNotFoundException`

### 4.3 Spring-Specific Exceptions

* `MethodArgumentNotValidException`
* `HttpMessageNotReadableException`
* `DataIntegrityViolationException`

---

## 5. Local Exception Handling (`@ExceptionHandler`)

Exception handling inside a single controller.

```java
@RestController
@RequestMapping("/users")
public class UserController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handle(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
```

### Limitations

* applies only to one controller
* not scalable
* leads to duplication

Used only for very small or isolated cases.

---

## 6. Global Exception Handling (`@ControllerAdvice`)

The **recommended approach** for real-world applications.

### What is `@ControllerAdvice`?

A global component that intercepts exceptions thrown by controllers.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
}
```

---

## 7. Handling Custom Exceptions

### 7.1 Custom Exception

```java
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

---

### 7.2 Global Handler

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
```

---

## 8. Standard Error Response Structure (Best Practice)

Instead of plain strings, return a structured error object.

### Error Response DTO

```java
public class ApiError {
    private int status;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
```

### Handler Example

```java
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ApiError> handleNotFound(
        ResourceNotFoundException ex,
        HttpServletRequest request) {

    ApiError error = new ApiError(
            404,
            ex.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
}
```

---

## 9. Handling Validation Errors

### Validation Exception

Occurs when `@Valid` fails.

```java
@PostMapping("/users")
public void create(@Valid @RequestBody UserDTO dto) {
}
```

Spring throws:

```
MethodArgumentNotValidException
```

---

### Global Validation Handler

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidation(
        MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getFieldErrors()
            .forEach(error ->
                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    )
            );

    return ResponseEntity.badRequest().body(errors);
}
```

---

## 10. Handling JSON Parsing Errors

### Example Scenarios

* invalid JSON format
* wrong data types
* malformed request body

Exception thrown:

```
HttpMessageNotReadableException
```

Handler:

```java
@ExceptionHandler(HttpMessageNotReadableException.class)
public ResponseEntity<String> handleJsonError() {
    return ResponseEntity
            .badRequest()
            .body("Invalid JSON request");
}
```

---

## 11. Database Exception Handling

### Common Database Exceptions

* `DataIntegrityViolationException`
* `ConstraintViolationException`

Example:

```java
@ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<String> handleDBError() {
    return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body("Database constraint violated");
}
```

---

## 12. Exception Handling Flow in Spring Boot

```
Controller
   ↓
Service
   ↓
Exception thrown
   ↓
@ControllerAdvice
   ↓
HTTP Response
```

Controller remains clean and focused on request handling.

---

## 13. Logging Exceptions (Important)

Always log exceptions in handlers.

```java
log.error("Exception occurred", ex);
```

Best practice:

* log once at global handler
* avoid logging in controller and service layers

---

## 14. Do Not Use try-catch in Controllers

Bad practice:

```java
try {
    service.call();
} catch (Exception e) {
    return ResponseEntity.badRequest().build();
}
```

Why?

* breaks separation of concerns
* hides real error cause
* unmaintainable

---

## 15. Exception Handling Best Practices

* Use global handler (`@RestControllerAdvice`)
* Return consistent error format
* Use meaningful HTTP status codes
* Do not expose stack traces to clients
* Log internally
* Create domain-specific exceptions
* Handle validation and JSON errors separately

---

## 16. Common Interview Questions

### Q1: What is `@ControllerAdvice`?

A global exception handling component in Spring.

---

### Q2: Difference between `@ControllerAdvice` and `@RestControllerAdvice`?

* `@ControllerAdvice` → works for MVC views
* `@RestControllerAdvice` → returns JSON responses

---

### Q3: Should exceptions be handled in controller or service?

Handled globally, thrown from service.

---

### Q4: How to handle validation errors?

Using `MethodArgumentNotValidException`.

---

### Q5: What happens if no handler matches?

Spring Boot returns default error response.

---

## Key Takeaways

* Exception handling is essential for stable REST APIs

* Use centralized global handlers

* Avoid try-catch in controllers

* Use custom exceptions for domain errors

* Always return consistent error responses

* Proper handling improves maintainability, security, and debugging

---
