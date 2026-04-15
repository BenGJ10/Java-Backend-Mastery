# Controller Layer Annotations in Spring

## 1. Overview

Controller layer annotations are used to build **web applications and REST APIs** with Spring.

They define:

* which class handles incoming requests
* which URL maps to which method
* how request parameters are read
* how responses are returned

This layer is the entry point for HTTP traffic.

---

## 2. `@Controller`

`@Controller` marks a class as a Spring MVC controller.

It is used mainly in traditional web applications where methods often return view names.

```java
@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
```

Spring treats the returned string as a **view name** unless `@ResponseBody` is used.

---

## 3. `@RestController`

`@RestController` is used for REST APIs.

It is equivalent to:

* `@Controller`
* plus `@ResponseBody`

on every method.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
```

Use `@RestController` when the goal is to return JSON or plain text, not a view.

---

## 4. `@RequestMapping`

`@RequestMapping` maps a class or method to a URL path.

It can also define HTTP method, headers, consumes, and produces.

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {
}
```

This class now handles all routes under `/api/products`.

---

## 5. HTTP Method Shortcut Annotations

Spring provides shortcut annotations for specific HTTP methods.

### 5.1 `@GetMapping`

Used to read data.

```java
@GetMapping("/{id}")
public String getProduct(@PathVariable Long id) {
    return "Product: " + id;
}
```

### 5.2 `@PostMapping`

Used to create data.

```java
@PostMapping
public String createProduct(@RequestBody ProductRequest request) {
    return "Created: " + request.getName();
}
```

### 5.3 `@PutMapping`

Used to replace or update data.

```java
@PutMapping("/{id}")
public String updateProduct(@PathVariable Long id, @RequestBody ProductRequest request) {
    return "Updated product " + id;
}
```

### 5.4 `@DeleteMapping`

Used to delete data.

```java
@DeleteMapping("/{id}")
public String deleteProduct(@PathVariable Long id) {
    return "Deleted product " + id;
}
```

---

## 6. Request Data Binding Annotations

### 6.1 `@RequestParam`

Reads query parameters from the URL.

```java
@GetMapping("/search")
public String search(@RequestParam String name) {
    return "Searching for " + name;
}
```

Example request:

```text
GET /api/users/search?name=ben
```

---

### 6.2 `@PathVariable`

Reads values from the URI path.

```java
@GetMapping("/{id}")
public String getUser(@PathVariable Long id) {
    return "User id = " + id;
}
```

Example request:

```text
GET /api/users/10
```

---

### 6.3 `@RequestBody`

Maps the HTTP request body to a Java object.

```java
@PostMapping
public String create(@RequestBody UserRequest request) {
    return "Created user: " + request.getName();
}
```

This is commonly used for JSON payloads.

---

## 7. Response Handling Annotations

### 7.1 `@ResponseBody`

Tells Spring to write the returned value directly to the HTTP response.

```java
@Controller
public class SampleController {

    @GetMapping("/text")
    @ResponseBody
    public String text() {
        return "Hello Spring";
    }
}
```

---

### 7.2 `ResponseEntity`

`ResponseEntity` is used when you need control over:

* response body
* HTTP status
* headers

```java
@GetMapping("/{id}")
public ResponseEntity<String> getById(@PathVariable Long id) {
    if (id == 1) {
        return ResponseEntity.ok("Found");
    }
    return ResponseEntity.status(404).body("Not found");
}
```

---

## 8. Example Controller

```java
@RestController
@RequestMapping("/api/books")
public class BookController {

    @GetMapping
    public List<String> getAllBooks() {
        return List.of("Spring in Action", "Effective Java");
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id) {
        return "Book id = " + id;
    }

    @PostMapping
    public ResponseEntity<String> createBook(@RequestBody String body) {
        return ResponseEntity.status(201).body("Created: " + body);
    }
}
```

---

## 9. Controller Layer Best Practices

* keep controllers thin

* delegate business logic to services

* use `@RestController` for APIs

* use `ResponseEntity` when status codes matter

* use DTOs instead of exposing entities directly

---

## 10. Common Mistakes

* returning a plain string from `@Controller` when a JSON response was expected

* putting business logic directly in the controller

* mixing view-based and REST-based patterns in the same controller without a clear reason

* forgetting `@ResponseBody` when using `@Controller`

* using `@RequestBody` without validating input

---

## 11. Interview Questions

### Q1. What is the difference between `@Controller` and `@RestController`?

`@Controller` is mainly for MVC views, while `@RestController` returns response data directly.

---

### Q2. What does `@RequestMapping` do?

It maps a controller or method to a URL path.

---

### Q3. When should `ResponseEntity` be used?

When you need to control HTTP status or headers.

---

### Q4. What is the purpose of `@RequestBody`?

It binds the HTTP request body to a Java object.

---

## Key Takeaways

* Controller annotations define HTTP request handling

* `@Controller` and `@RestController` are the main entry points

* `@RequestMapping` and method mappings define routes

* `@RequestParam`, `@PathVariable`, and `@RequestBody` bind request data

* `ResponseEntity` gives full response control

---