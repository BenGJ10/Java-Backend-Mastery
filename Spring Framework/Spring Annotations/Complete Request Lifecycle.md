# Complete Spring Boot Request Lifecycle

## 1. Overview

One of the most important concepts in Spring Boot is understanding:

> **How an HTTP request travels internally through the framework**

Every incoming request passes through multiple layers before reaching the database and then returns back to the client.

Understanding this flow helps explain:

* authentication
* authorization
* logging
* AOP
* request interception
* controller processing
* service execution
* database interaction

---

## 2. Complete Request Flow

```text
Client Request
      ↓
Servlet Filter
      ↓
Spring Security Filters
      ↓
DispatcherServlet
      ↓
HandlerInterceptor
      ↓
Controller
      ↓
Service Layer
      ↓
AOP Aspect
      ↓
Repository
      ↓
Database
```

After processing, the response travels back through the framework and returns to the client.

---

## 3. Client Request

Everything begins when a client sends an HTTP request.

Examples:

```http
GET /api/users/10
POST /api/orders
```

The request may come from:

* browser
* mobile application
* frontend application
* Postman
* another backend service

Example request:

```http
GET /api/users/10
Authorization: Bearer jwt-token
```

This request first reaches the embedded web server inside Spring Boot.

---

### 3.1 Embedded Web Server

Spring Boot applications internally run an embedded server such as:

* Tomcat (default)
* Jetty
* Undertow

The server receives the raw HTTP request and forwards it into the Spring application pipeline.

---

## 4. Servlet Filter Layer

The first interception layer is the Servlet Filter layer.

Filters belong to the:

```text
Servlet API
```

and execute before Spring MVC processing begins.

---

### 4.1 Filter Flow

```text
Client Request
      ↓
Servlet Filter
      ↓
Next Layer
```

---

### 4.2 Purpose of Filters

Filters are used for:

* request logging
* CORS handling
* compression
* JWT preprocessing
* request wrapping
* response modification
* low-level security

---

### 4.3 Filter Example

```java
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req =
                (HttpServletRequest) request;

        System.out.println(
                "Incoming Request: "
                + req.getRequestURI());

        chain.doFilter(request, response);

        System.out.println("Response Sent");
    }
}
```

---

### 4.4 Important Concept

This line is critical:

```java
chain.doFilter(request, response);
```

It means:

> Continue the request flow to the next layer.

Without this line, request processing stops completely.

---

## 5. Spring Security Filter Chain

If Spring Security is configured, the request enters the Security Filter Chain.

---

### 5.1 Security Flow

```text
Request
      ↓
Spring Security Filters
      ↓
Authentication
      ↓
Authorization
```

---

### 5.2 Why Security Uses Filters

Security must execute before controllers.

If authentication fails:

* controllers should never run
* business logic should never execute

This is why Spring Security is implemented using filters.

---

### 5.3 JWT Filter Example

```java
@Component
public class JwtFilter
        extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        System.out.println("Validating JWT");

        filterChain.doFilter(request, response);
    }
}
```

---

### 5.4 Common Responsibilities

Security filters typically handle:

* JWT validation
* username/password authentication
* role-based authorization
* CSRF protection
* session management

---

### 5.5 Important Concept

If authentication fails:

```java
response.sendError(401);
return;
```

The request stops immediately.

Controller execution never happens.

---

## 6. DispatcherServlet

After filters complete, the request reaches:

```text
DispatcherServlet
```

This is the core of Spring MVC.

---

### 6.1 What Is DispatcherServlet?

DispatcherServlet is called the:

```text
Front Controller
```

because every request passes through it.

---

### 6.2 Responsibilities

DispatcherServlet:

* receives requests
* finds matching controllers
* invokes interceptors
* handles exceptions
* processes responses
* coordinates MVC flow

---

### 6.3 Flow

```text
Request
      ↓
DispatcherServlet
      ↓
Matching Controller Found
```

---

### 6.4 Example

For request:

```http
GET /api/users/10
```

DispatcherServlet searches for:

```java
@GetMapping("/api/users/{id}")
```

---

## 7. HandlerInterceptor Layer

Before controller execution, Spring MVC interceptors execute.

---

### 7.1 Flow

```text
DispatcherServlet
      ↓
HandlerInterceptor
      ↓
Controller
```

---

### 7.2 Purpose

HandlerInterceptors are commonly used for:

* request tracking
* logging
* API monitoring
* authentication checks
* rate limiting
* locale configuration

---

### 7.3 Interceptor Example

```java
@Component
public class RequestInterceptor
        implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        System.out.println(
                "Before Controller Execution");

        return true;
    }
}
```

---

### 7.4 Important Concept

```java
return true;
```

means:

> Continue request execution.

Whereas:

```java
return false;
```

means:

> Stop request processing.

---

### 7.5 Interceptor Lifecycle Methods

| Method              | Purpose                   |
| ------------------- | ------------------------- |
| `preHandle()`       | before controller         |
| `postHandle()`      | after controller          |
| `afterCompletion()` | after response completion |

---

## 8. Controller Layer

Now the actual controller endpoint executes.

---

### 8.1 Example Controller

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User getUser(
            @PathVariable Long id) {

        return userService.getUser(id);
    }
}
```

---

### 8.2 Responsibilities of Controllers

Controllers should:

* receive HTTP requests
* validate request input
* return HTTP responses
* delegate business logic to services

Controllers should NOT contain complex business logic.

---

## 9. Service Layer

The Service Layer contains core business logic.

---

### 9.1 Example Service

```java
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUser(Long id) {

        System.out.println(
                "Business Logic Running");

        return repository.findById(id)
                .orElseThrow();
    }
}
```

---

### 9.2 Responsibilities

Services handle:

* business rules
* validation
* calculations
* orchestration
* transactions
* application workflows

---

## 10. AOP Aspect Layer

Spring AOP intercepts method executions.

This is different from request interception.

---

### 10.1 Important Concept

AOP intercepts:

```text
METHOD EXECUTION
```

not HTTP requests.

---

### 10.2 Flow

```text
Controller
      ↓
Service Method Call
      ↓
AOP Proxy
      ↓
Actual Method
```

---

### 10.3 Example Aspect

```java
@Aspect
@Component
public class LoggingAspect {

    @Around(
      "execution(* com.example.service.*.*(..))")
    public Object log(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        System.out.println(
                "Before Service Method");

        Object result =
                joinPoint.proceed();

        System.out.println(
                "After Service Method");

        return result;
    }
}
```

---

### 10.4 Important Concept

```java
joinPoint.proceed();
```

means:

> Execute the actual target method.

Without this line, the original method never runs.

---

### 10.5 Common AOP Use Cases

* logging
* transaction management
* retry mechanisms
* performance monitoring
* auditing
* metrics

---


## 11. Complete Internal Lifecycle

```text
1. Client Request
      ↓
2. Servlet Filter
      ↓
3. Spring Security Filter Chain
      ↓
4. DispatcherServlet
      ↓
5. HandlerInterceptor preHandle()
      ↓
6. Controller
      ↓
7. Service Layer
      ↓
8. AOP Aspect / Proxy
      ↓
9. Repository
      ↓
10. Hibernate/JPA
      ↓
11. Database

--------------------------------

12. Database Response
      ↓
13. Repository
      ↓
14. Service
      ↓
15. Controller
      ↓
16. postHandle()
      ↓
17. afterCompletion()
      ↓
18. HTTP Response
```

---

## 12. Responsibilities of Each Layer

| Layer              | Responsibility                 |
| ------------------ | ------------------------------ |
| Filter             | Raw HTTP preprocessing         |
| Security Filter    | Authentication & authorization |
| DispatcherServlet  | MVC coordination               |
| HandlerInterceptor | Request interception           |
| Controller         | API handling                   |
| Service            | Business logic                 |
| AOP                | Cross-cutting concerns         |
| Repository         | Data access                    |
| Database           | Data persistence               |

---

## 13. Architectural Importance

Spring separates responsibilities into layers to achieve:

* loose coupling
* maintainability
* scalability
* clean architecture
* easier testing
* modular design

---

## 14. Common Mistakes

* putting business logic in controllers

* accessing database directly from controllers

* handling HTTP concerns inside services

* mixing security logic into repositories

* using AOP for core business rules

---

## Key Takeaways

* Every request passes through multiple Spring layers

* Filters operate before Spring MVC

* Spring Security uses filters internally

* DispatcherServlet coordinates request handling

* HandlerInterceptors work at MVC level

* Controllers handle HTTP logic

* Services contain business logic

* AOP intercepts method executions

* Repositories manage persistence

* Spring architecture separates responsibilities cleanly

---