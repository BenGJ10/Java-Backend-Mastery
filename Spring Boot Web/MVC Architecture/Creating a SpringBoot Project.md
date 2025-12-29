# Creating a Spring Boot Web Project

This guide walks you through building a simple Spring Boot web application using the MVC architecture. You will learn how to set up the project, create controllers, views, and handle web requests.

## 1. What You Need Before Starting

Install the following:

* Java 8+ (preferably 17 or 21)
* IDE (IntelliJ / STS / Eclipse)
* Maven or Gradle (Maven considered here)
* Web browser

Confirm Java:

```bash
java -version
```

---

## 2. Create Spring Boot Project (Spring Initializr)

### Option 1 — Using Website (Recommended)

Go to:

```
https://start.spring.io
```

Fill form:

* **Project:** Maven
* **Language:** Java
* **Spring Boot Version:** stable latest
* **Project Metadata**

  * Group: `com.example`
  * Artifact: `springboot-demo`
  * Packaging: `Jar`
* **Java Version:** 17 (or your LTS version)

### Add Dependencies

Select:

* **Spring Web**
* **Thymeleaf** (for web pages support)

Click **Generate**, download the ZIP → extract → open in IDE.

---

## 3. Understanding Project Structure

```
src/
 └── main/
      ├── java/
      │    └── com.example.demo/
      │          ├── DemoApplication.java
      │          └── controller/
      │                └── HomeController.java
      └── resources/
           ├── static/
           │     ├── css/
           │     └── js/
           ├── templates/
           │     └── home.html
           └── application.properties
```

### Meaning of folders:

* `templates/` → HTML (Thymeleaf Views)
* `static/` → CSS, JavaScript, images
* `application.properties` → configuration
* `controller/` → controller classes
* `DemoApplication.java` → main entry point

---

## 4. The Main Spring Boot Application Class

`DemoApplication.java` is auto-generated:

```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

Explanation:

* `@SpringBootApplication` enables:

  * component scanning
  * auto-configuration
  * configuration properties
* `SpringApplication.run()` starts:

  * Spring Context
  * Embedded Tomcat server
  * DispatcherServlet

Default app runs on port **8080**

---

## 5. Add a Controller (Handles Web Requests)

Create package:

```
com.example.demo.controller
```

Create file:

```java
@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("title", "Spring Boot Web Project");
        model.addAttribute("message", "Welcome! This is your first Spring Boot web page.");
        return "home";
    }
}
```

### What this does

* `@Controller` → marks as MVC controller
* `@GetMapping("/")` → maps root URL
* `Model` → carries data to view
* returns **view name** → `home`

---

## 6. Create Web Page (View Layer)

Go to:

```
src/main/resources/templates/
```

Create file:

```
home.html
```

Add code:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title th:text="${title}">Home</title>
</head>
<body>
    <h1 th:text="${message}"></h1>
    <p>This page is rendered using Spring Boot + Thymeleaf MVC.</p>
</body>
</html>
```

Spring automatically uses **Thymeleaf** to render this.

No JSP needed.

---

## 7. Run the Project

### Using IDE

Run `DemoApplication.java`

### Or Terminal

```bash
mvn spring-boot:run
```

Open in browser:

```
http://localhost:8080/
```

You will see your webpage rendered 🎯

---

## 8. Adding More Controller Mappings

### Path Variable

```java
@GetMapping("/user/{name}")
public String greetUser(@PathVariable String name, Model model) {
    model.addAttribute("message", "Hello " + name);
    return "home";
}
```

URL:

```
/user/Ben
```

---

### Request Parameter

```java
@GetMapping("/search")
public String search(@RequestParam String query, Model model) {
    model.addAttribute("message", "Searching for: " + query);
    return "home";
}
```

URL:

```
/search?query=java
```

---

## 9. POST Request Example (Form Submission)

### Step 1 — Add HTML Form

Add to `home.html`:

```html
<form action="/submit" method="post">
    <input type="text" name="name" placeholder="Enter Name">
    <button type="submit">Submit</button>
</form>
```

### Step 2 — Controller Method

```java
@PostMapping("/submit")
public String submit(@RequestParam String name, Model model) {
    model.addAttribute("message", "Form submitted by: " + name);
    return "home";
}
```

This handles form submissions gracefully.

---

## 10. Servlet & DispatcherServlet Behind the Scenes

Spring Boot internally:

* configures **DispatcherServlet**
* registers it in embedded Tomcat
* routes every incoming HTTP request through it

### Flow:

```
Browser request
      ↓
DispatcherServlet
      ↓
HandlerMapping
      ↓
Controller method
      ↓
Returns Model + View
      ↓
ViewResolver selects template
      ↓
HTML generated → Browser
```

---

## 11. application.properties Configurations (Important)

Located at:

```
src/main/resources/application.properties
```

### Change Server Port

```properties
server.port=9090
```

### Disable Whitelabel Error Page

```properties
server.error.whitelabel.enabled=false
```

### Set application name

```properties
spring.application.name=SpringBootWebApp
```

---

## 12. Static Resources Handling

Place:

* CSS → `static/css`
* JS → `static/js`
* images → `static/images`

Spring Boot serves them automatically.

Example:

```
<link rel="stylesheet" href="/css/style.css">
```

---

## 13. Directory Structure Summary

```
controllers   → handle HTTP requests
models        → hold data
services      → business logic
repositories  → DB operations (later with JPA)
templates     → HTML UI
static        → CSS/JS/images
```

---

## 14. Common Errors & Fixes

| Issue                 | Fix                                 |
| --------------------- | ----------------------------------- |
| Port already in use   | Stop previous server or change port |
| Template not found    | check `/templates/filename.html`    |
| Whitelabel Error Page | invalid controller or mapping       |
| 404 Not Found         | mapping incorrect                   |
| Method Not Allowed    | using wrong HTTP method             |

---

## Key Takeaways

* Spring Boot simplifies web app creation

* MVC = Model + View + Controller

* DispatcherServlet handles routing

* Thymeleaf used for UI rendering

* Minimal configuration required

* Embedded server handles HTTP

---
