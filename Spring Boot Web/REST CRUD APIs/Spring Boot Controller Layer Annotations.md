# Spring Boot Controller Layer Annotations

## 1. Overview

In Spring Boot, the controller layer handles incoming HTTP requests and returns responses.

Controller annotations help define:

* which class handles web/API requests
* which URL pattern maps to which method
* what data comes from path, query params, or request body
* what response format and status should be returned

This topic covers the most important controller annotations used in real-world REST APIs.

---

## 2. Application Entry Annotation: `@SpringBootApplication`

`@SpringBootApplication` is placed on the main application class.

It is a combination of:

* `@Configuration`
* `@EnableAutoConfiguration`
* `@ComponentScan`

Example:

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

Why it matters for controllers:

* component scanning detects controller classes
* Spring Boot auto-configures Spring MVC
* DispatcherServlet gets initialized automatically

---

## 3. `@Controller` vs `@RestController`

### 3.1 `@Controller`

Used for MVC applications where methods usually return view names (JSP/Thymeleaf).

```java
@Controller
@RequestMapping("/web")
public class WebController {

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
```

### 3.2 `@RestController`

Used for REST APIs.

It is equivalent to:

* `@Controller`
* plus `@ResponseBody` on all methods

```java
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
```

---

## 4. Base URL Mapping: `@RequestMapping`

`@RequestMapping` maps a class or method to a URL path, and optionally HTTP method, consumes, produces, etc.

Common use: define base path at class level.

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public List<String> getAllUsers() {
        return List.of("Ben", "Alex");
    }
}
```

Now all methods inside this class are under `/api/users`.

---

## 5. HTTP Method Mapping Annotations

These are specialized shortcut annotations over `@RequestMapping(method = ...)`.

### 5.1 `@GetMapping`

Used for fetching data.

```java
@GetMapping("/{id}")
public String getUser(@PathVariable Long id) {
    return "User id = " + id;
}
```

### 5.2 `@PostMapping`

Used for creating data.

```java
@PostMapping
public String createUser(@RequestBody String body) {
    return "Created: " + body;
}
```

### 5.3 `@PutMapping`

Used for full update.

```java
@PutMapping("/{id}")
public String updateUser(@PathVariable Long id, @RequestBody String body) {
    return "Updated user " + id;
}
```

### 5.4 `@DeleteMapping`

Used for deletion.

```java
@DeleteMapping("/{id}")
public String deleteUser(@PathVariable Long id) {
    return "Deleted user " + id;
}
```

---

## 6. Response Annotations

### 6.1 `@ResponseBody`

Used with `@Controller` methods to return raw response data (JSON/String) instead of view name.

```java
@Controller
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/text")
    @ResponseBody
    public String plainText() {
        return "Hello from @ResponseBody";
    }
}
```

### 6.2 `@ResponseEntity`

Used to control:

* response body
* HTTP status code
* headers

```java
@GetMapping("/{id}")
public ResponseEntity<String> getById(@PathVariable Long id) {
    if (id == 1) {
        return ResponseEntity.ok("User found");
    }
    return ResponseEntity.status(404).body("User not found");
}
```

---

## 7. Request Data Binding Annotations

### 7.1 `@RequestParam`

Reads query parameters from URL.

Request example:

```text
GET /api/users/search?name=ben
```

Controller:

```java
@GetMapping("/search")
public String searchUser(@RequestParam String name) {
    return "Searching for " + name;
}
```

With default value and optional flag:

```java
@GetMapping("/filter")
public String filter(@RequestParam(required = false, defaultValue = "all") String role) {
    return "Role: " + role;
}
```

### 7.2 `@RequestBody`

Maps request JSON body to Java object.

DTO:

```java
public class UserRequest {
    private String name;
    private String email;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

Controller:

```java
@PostMapping
public ResponseEntity<String> create(@RequestBody UserRequest request) {
    return ResponseEntity.ok("Created user: " + request.getName());
}
```

### 7.3 `@PathVariable`

Reads values from URI path.

Request example:

```text
GET /api/users/101
```

Controller:

```java
@GetMapping("/{id}")
public String getUserById(@PathVariable Long id) {
    return "User id = " + id;
}
```

---

## 8. All-in-One Example

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping
    public ResponseEntity<String> getAll(
            @RequestParam(required = false, defaultValue = "1") int page) {
        return ResponseEntity.ok("Products page: " + page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable Long id) {
        return ResponseEntity.ok("Product id: " + id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody String payload) {
        return ResponseEntity.status(201).body("Created: " + payload);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody String payload) {
        return ResponseEntity.ok("Updated " + id + " with " + payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ResponseEntity.noContent().build();
    }
}
```

---

## 9. Common Mistakes to Avoid

* using `@Controller` for REST APIs and forgetting `@ResponseBody`
* not validating `@RequestBody` payloads
* returning only String messages instead of proper status codes
* exposing inconsistent URL patterns
* not using `ResponseEntity` when status/header control is needed

---

## 10. Common Interview Questions

### Q1. Difference between `@Controller` and `@RestController`?

`@Controller` is for MVC views, while `@RestController` is for REST APIs and returns response body directly.

---

### Q2. What is `@RequestMapping` used for?

To map class or method to URL path, and optionally HTTP method and media type.

---

### Q3. When do we use `@RequestParam` vs `@PathVariable`?

* `@RequestParam` -> query string (`?page=1`)
* `@PathVariable` -> URI segment (`/users/101`)

---

### Q4. Why use `ResponseEntity`?

To control response body, status code, and headers in a clean way.

---

### Q5. Is `@ResponseBody` required with `@RestController`?

No. `@RestController` already includes `@ResponseBody` behavior by default.

---

## Key Takeaways

* `@SpringBootApplication` bootstraps app and component scanning

* `@Controller` is for views, `@RestController` is for APIs

* `@RequestMapping` sets base mappings, method annotations handle HTTP verbs

* `@RequestParam`, `@PathVariable`, and `@RequestBody` bind request data

* `@ResponseEntity` gives full control over HTTP responses

---