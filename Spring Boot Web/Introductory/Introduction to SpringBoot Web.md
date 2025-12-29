# Introduction to Spring Boot Web

Spring Boot Web is a part of the Spring Boot framework used for building **web applications and RESTful APIs** quickly with **minimal configuration**. 

It is built on top of the Spring Framework and provides auto-configuration, embedded servers, and production-ready features out of the box.

Spring Boot Web mainly uses:

* **Spring MVC** for web layer
* **Tomcat/Jetty/Undertow** as embedded servers
* **Jackson** for JSON serialization

---

## 1. Why Spring Boot Web?

Traditional Spring MVC required:

* XML configuration
* manual server deployment (WAR in Tomcat)
* boilerplate setup

Spring Boot simplifies web development by providing:

* auto configuration
* embedded web server
* production ready defaults
* simplified dependency management

### Key Benefits

* **No XML configuration**
* **Create executable JAR** (no external server needed)
* **REST API easy to build**
* **Production-ready features**: health checks, metrics, actuator
* **Spring ecosystem integration**: JPA, Security, Cloud, Kafka etc.

---

## 2. Required Dependency (Spring Boot Web Starter)

To build web apps/APIs, add:

### Maven

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

This starter includes:

* Spring MVC
* Embedded Tomcat
* Validation API
* JSON support (Jackson)

---

## 3. Embedded Web Servers

Spring Boot includes an **embedded** server by default:

* Tomcat (default)
* Jetty (optional)
* Undertow (optional)

Meaning:

> You do not deploy to a server;
> The **server lives inside your application JAR**.

Run with:

```bash
java -jar app.jar
```

Application starts at:

```
http://localhost:8080
```

---

## 4. Spring Boot Web Architecture Overview

Standard layered architecture:

```
Client (Browser / Postman / Mobile App)
        │
        ▼
Controller Layer (REST API)
        │
        ▼
Service Layer (Business Logic)
        │
        ▼
Repository Layer (Database Access)
```

Framework used internally:

* **DispatcherServlet** (front controller)
* Handler Mapping
* Controllers
* ViewResolvers / JSON Converters

---

## 5. Creating First REST Controller

### Step 1: Create Spring Boot Application

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

---

### Step 2: Create REST Controller

```java
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello Spring Boot Web";
    }
}
```

Run app → open:

```
http://localhost:8080/hello
```

Output:

```
Hello Spring Boot Web
```

---

## 6. Difference Between @Controller and @RestController

| @Controller                       | @RestController         |
| --------------------------------- | ----------------------- |
| Returns View (JSP/HTML/Thymeleaf) | Returns JSON/XML/String |
| Used in MVC web apps              | Used in REST APIs       |
| Needs @ResponseBody               | Implicit @ResponseBody  |

---

## 7. Handling Request Parameters

### Path Variable

```java
@GetMapping("/user/{id}")
public String getUser(@PathVariable int id) {
    return "User ID = " + id;
}
```

---

### Request Param

```java
@GetMapping("/search")
public String search(@RequestParam String keyword) {
    return "Searching: " + keyword;
}
```

---

## 8. Returning JSON Responses

Create DTO class:

```java
public class User {
    private int id;
    private String name;
}
```

Return object:

```java
@GetMapping("/user")
public User getUser() {
    return new User(1, "Ben");
}
```

Spring Boot automatically converts to JSON using **Jackson**.

---

## 9. HTTP Method Support

| HTTP Method | Use         |
| ----------- | ----------- |
| GET         | Read data   |
| POST        | Create data |
| PUT         | Update data |
| DELETE      | Delete data |

Example:

```java
@PostMapping("/users")
public String addUser() {
    return "User created";
}
```

---

## 10. Spring Boot Web vs Spring MVC

| Spring MVC             | Spring Boot Web   |
| ---------------------- | ----------------- |
| Requires XML setup     | Auto-configured   |
| No embedded server     | Embedded server   |
| Manual dependency mgmt | Starters simplify |
| WAR deployment         | JAR runnable      |

---

## 11. Exception Handling in Spring Boot Web (Overview)

Approaches:

* `@ExceptionHandler`
* `@ControllerAdvice`
* `ResponseStatusException`

Example:

```java
@ResponseStatus(HttpStatus.NOT_FOUND)
class UserNotFoundException extends RuntimeException { }
```

---

## 12. Content Negotiation

Spring Boot automatically supports:

* JSON
* XML
* plain text

Using **Accept header** or **produces attribute**.

---

## 13. Common Interview Questions

### Q1. What is Spring Boot Web?

Framework for building web applications and REST APIs with auto configuration and embedded servers.

---

### Q2. What is DispatcherServlet?

It is the **front controller** in Spring MVC that routes incoming HTTP requests to appropriate controllers.

---

### Q3. Why is Spring Boot preferred over Spring MVC?

* auto configuration
* embedded server
* less boilerplate
* quick setup for microservices

---

### Q4. What is difference between Spring Boot and Spring?

Spring = framework
Spring Boot = rapid development framework built on Spring

---

### Q5. Default embedded server in Spring Boot?

Tomcat.

---

### Q6. How does Spring Boot return JSON automatically?

Using Jackson library through **HttpMessageConverters**.

---

## Key Takeaways

* Spring Boot Web simplifies building APIs and web apps

* Uses embedded Tomcat server

* Uses Spring MVC internally

* REST controllers return JSON by default

* Minimal configuration required

* Production-ready and microservice friendly

---
