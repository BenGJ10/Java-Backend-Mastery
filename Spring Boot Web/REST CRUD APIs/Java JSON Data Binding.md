# Java JSON Data Binding

## Serialization and Deserialization

## 1. Overview

**JSON data binding** in Java is the process of converting:

* **Java objects → JSON** (**Serialization**)
* **JSON → Java objects** (**Deserialization**)

This is a **core backend concept**, heavily used in:

* REST APIs
* Microservices communication
* Request/response payloads
* Message queues
* File-based data exchange

In Spring Boot applications, JSON binding is primarily handled by **Jackson**, which is integrated by default.

---

## 2. What is Serialization?

**Serialization** is the process of converting a Java object into JSON format.

### Why Serialization is Needed

* To send data over HTTP
* To store structured data
* To make data language-agnostic

### Example

Java Object:

```java
class User {
    private Long id;
    private String name;
    private String email;
}
```

Serialized JSON:

```json
{
  "id": 1,
  "name": "John",
  "email": "john@gmail.com"
}
```

---

## 3. What is Deserialization?

**Deserialization** is the reverse process:

> Converting JSON data into a Java object.

### Example

JSON Input:

```json
{
  "name": "John",
  "email": "john@gmail.com"
}
```

Java Object after deserialization:

```java
User user;
```

This happens automatically in Spring REST controllers.

---

## 4. Jackson in Spring Boot

Spring Boot uses **Jackson ObjectMapper** internally.

No configuration is needed for basic use.

### Internal Flow in REST API

```
Client JSON
   ↓
Jackson Deserialization
   ↓
Java DTO / Object
   ↓
Service Logic
   ↓
Java Object
   ↓
Jackson Serialization
   ↓
JSON Response
```

---

## 5. Manual Serialization and Deserialization

### Using ObjectMapper

```java
ObjectMapper mapper = new ObjectMapper();
```

### Serialization Example

```java
User user = new User(1L, "John", "john@gmail.com");

String json = mapper.writeValueAsString(user);
```

Output:

```json
{"id":1,"name":"John","email":"john@gmail.com"}
```

---

### Deserialization Example

```java
String json = "{\"id\":1,\"name\":\"John\",\"email\":\"john@gmail.com\"}";

User user = mapper.readValue(json, User.class);
```

---

## 6. JSON Binding in Spring REST Controllers

### Deserialization (Request Body)

```java
@PostMapping("/users")
public UserDTO create(@RequestBody UserDTO dto) {
    return userService.create(dto);
}
```

JSON request body is automatically deserialized into `UserDTO`.

---

### Serialization (Response Body)

```java
@GetMapping("/users/{id}")
public UserDTO getUser(@PathVariable Long id) {
    return userService.getById(id);
}
```

Returned object is automatically serialized into JSON.

---

## 7. Common Jackson Annotations (Very Important)

### `@JsonProperty`

Custom JSON field name.

```java
@JsonProperty("user_name")
private String name;
```

---

### `@JsonIgnore`

Exclude field from JSON.

```java
@JsonIgnore
private String password;
```

Used for security-sensitive data.

---

### `@JsonInclude`

Exclude null values.

```java
@JsonInclude(JsonInclude.Include.NON_NULL)
```

---

### `@JsonFormat`

Format dates.

```java
@JsonFormat(pattern = "yyyy-MM-dd")
private LocalDate dob;
```

---

### `@JsonIgnoreProperties`

Ignore unknown JSON fields.

```java
@JsonIgnoreProperties(ignoreUnknown = true)
```

Prevents deserialization errors.

---

## 8. Handling Nested Objects

### Java Object

```java
class Order {
    private Long id;
    private User user;
}
```

### JSON

```json
{
  "id": 10,
  "user": {
    "id": 1,
    "name": "John"
  }
}
```

Jackson handles nested binding automatically.

---

## 9. Collections Binding

### List Example

```java
class Order {
    private List<Item> items;
}
```

JSON:

```json
{
  "items": [
    {"name": "Book"},
    {"name": "Pen"}
  ]
}
```

---

## 10. Enum Serialization and Deserialization

### Default Behavior

```java
enum Status {
    ACTIVE, INACTIVE
}
```

JSON:

```json
"ACTIVE"
```

---

### Custom Enum Mapping

```java
@JsonValue
public String getValue() {
    return "active";
}
```

---

## 11. Date and Time Serialization (Java 8+)

Best practice:

* Use `LocalDate`, `LocalDateTime`
* Register JavaTimeModule

```java
@Bean
public ObjectMapper objectMapper() {
    return JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();
}
```

---

## 12. Error Scenarios in JSON Binding

### Common Problems

* Missing default constructor
* Mismatched field names
* Invalid date format
* Unknown JSON fields
* Missing getters/setters

### Example Error

```
HttpMessageNotReadableException
```

---

## 13. Best Practices

* Use **DTOs**, not entities
* Never expose sensitive fields
* Use validation annotations
* Use `@JsonIgnoreProperties`
* Keep JSON contract stable
* Use consistent naming strategy

---

## 14. Serialization vs Deserialization Summary

| Aspect            | Serialization  | Deserialization |
| ----------------- | -------------- | --------------- |
| Direction         | Java → JSON    | JSON → Java     |
| Used in           | Response body  | Request body    |
| Common annotation | @JsonIgnore    | @JsonProperty   |
| Failure type      | Invalid output | Bad request     |

---

## 15. Interview Questions

### Q1: What is JSON serialization?

Converting Java objects into JSON.

---

### Q2: What library does Spring Boot use for JSON?

Jackson.

---

### Q3: How does Spring automatically bind JSON?

Using HttpMessageConverters with ObjectMapper.

---

### Q4: Why DTO is preferred for JSON binding?

To avoid exposing entities and persistence details.

---

### Q5: How to ignore unknown JSON fields?

Using `@JsonIgnoreProperties(ignoreUnknown = true)`.

---

## Key Takeaways

* JSON data binding is essential for REST APIs

* Serialization converts Java objects to JSON

* Deserialization converts JSON to Java objects

* Jackson handles binding in Spring Boot

* Annotations control JSON structure

* DTOs should be used for API contracts

* Proper binding improves security and stability

---
