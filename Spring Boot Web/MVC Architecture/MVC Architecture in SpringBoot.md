# MVC in Java Spring Boot

* **Model** → data and business objects

* **View** → UI layer (HTML/JSP/Thymeleaf/JSON)

* **Controller** → handles HTTP requests and responses

This separation improves **testability, maintainability, and scalability**.

---

## 1. What Is MVC Architecture?

MVC divides the application as follows:

```
Client → Controller → Service/Model → View (Response)
```

* **Controller** handles incoming HTTP requests
* **Model** represents application data and business rules
* **View** displays the data to users

Spring MVC uses a **front controller** called `DispatcherServlet`.

---

## 2. Components in Spring Boot MVC

### 2.1 Model

Represents application **data** (domain objects, DTOs, entities).

Examples:

* `User`, `Order`, `Product`
* Data returned to View or REST client

Model can be:

* POJO (Plain Old Java Object)
* Entity (JPA/Hibernate)
* DTO (Data Transfer Object)

Example:

```java
public class User {
    private int id;
    private String name;
}
```

---

### 2.2 View

Responsible for **presentation/UI**.

Possible technologies:

* Thymeleaf (most common in Spring Boot)
* JSP
* FreeMarker
* JSON/XML output from REST API

Example: Thymeleaf template (`user.html`)

```html
<p>User Name: ${name}</p>
```

---

### 2.3 Controller

Controller:

* maps URL requests
* interacts with service/model
* returns response or view name

Annotations used:

* `@Controller`
* `@RestController`
* `@RequestMapping`, `@GetMapping`, `@PostMapping`

Example:

```java
@Controller
public class HomeController {

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("name", "Ben");
        return "home";   // logical view name → home.html
    }
}
```

---

## 3. How Spring MVC Works Internally

Spring MVC uses **DispatcherServlet**, which acts as **Front Controller**.

### Flow

```
Client Request
     │
     ▼
DispatcherServlet
     │
     ▼
Handler Mapping (finds controller)
     │
     ▼
Controller executes (calls service/model)
     │
     ▼
Returns Model + View name
     │
     ▼
ViewResolver selects actual view file
     │
     ▼
View renders response
     │
     ▼
Client receives HTTP response
```

Important components:

* **DispatcherServlet** – central request handler
* **HandlerMapping** – maps request to controller method
* **ViewResolver** – resolves view names to templates
* **Model** – carries data between controller and view

---

## 4. Building a Simple MVC App in Spring Boot

### Step 1: Add Web + Thymeleaf Dependencies

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

---

### Step 2: Controller Class

```java
@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String showWelcome(Model model) {
        model.addAttribute("message", "Welcome to Spring MVC");
        return "welcome";
    }
}
```

---

### Step 3: View Page (welcome.html)

Place under:

```
src/main/resources/templates/
```

File:

```html
<!DOCTYPE html>
<html>
<body>
<h1 th:text="${message}"></h1>
</body>
</html>
```

Run app → Open in browser:

```
http://localhost:8080/welcome
```

---

## 5. Spring MVC vs Spring Boot MVC

| Spring MVC                  | Spring Boot MVC         |
| --------------------------- | ----------------------- |
| Requires manual config      | Auto-configuration      |
| Configure DispatcherServlet | Pre-configured          |
| Requires server setup       | Embedded server         |
| XML configuration possible  | Mostly annotation-based |

---

## 6. @Controller vs @RestController

| @Controller              | @RestController          |
| ------------------------ | ------------------------ |
| Returns view template    | Returns JSON/XML/String  |
| Used in MVC web apps     | Used in REST APIs        |
| Works with Thymeleaf/JSP | Works with REST services |

Example REST controller:

```java
@RestController
public class ApiController {
    @GetMapping("/user")
    public User getUser() {
        return new User(1, "Ben");
    }
}
```

---

## 7. Validation in Spring MVC (Short Overview)

Use:

* `@Valid`
* `@NotNull`
* `@Size`
* `@Email`

Example:

```java
@PostMapping("/users")
public String create(@Valid @ModelAttribute User user, BindingResult result) {
    if(result.hasErrors()) return "form";
    return "success";
}
```

---

## 8. Advantages of MVC in Spring Boot

* separation of concerns
* easy testability
* scalable architecture
* reusable components
* parallel development possible
* better organization of large projects

---

## 9. Common Interview Questions

### Q1. What is MVC pattern?

MVC stands for Model–View–Controller; it separates business logic, UI, and request handling.

---

### Q2. What is DispatcherServlet?

Central front controller that routes HTTP requests to controllers.

---

### Q3. Difference between @Controller and @RestController?

* `@Controller` returns views
* `@RestController` returns JSON/XML response

---

### Q4. What is ViewResolver?

Component that maps logical view names to actual template pages.

---

### Q5. Where are MVC templates placed in Spring Boot?

```
src/main/resources/templates/
```

---

### Q6. Can we use Spring MVC without Spring Boot?

Yes, but requires manual configuration (Spring Boot simplifies setup).

---

## Key Takeaways

* MVC separates **Model, View, and Controller**

* Spring Boot auto-configures Spring MVC

* `DispatcherServlet` controls request flow

* Use `@Controller` for web apps and **Thymeleaf/JSP views**

* Use `@RestController` for REST APIs

* Cleaner, modular, and testable architecture

---
