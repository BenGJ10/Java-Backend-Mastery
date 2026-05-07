# Overview of Interceptors in Spring

## 1. What Is an Interceptor?

An **interceptor** is a mechanism that allows custom logic to execute:

* before an operation
* after an operation
* around an operation

without modifying the original implementation.

In Spring, interceptors are used to handle:

* request preprocessing
* logging
* authentication
* transaction handling
* auditing
* metrics
* security
* method monitoring

---

## 2. Why Interceptors Exist

Applications often require repeated functionality across many modules.

Examples:

* checking authentication on every request
* logging every API call
* measuring execution time
* validating tokens
* adding audit logs

Without interceptors, this logic gets duplicated everywhere.

Interceptors solve this by centralizing reusable behavior.

---

## 3. Types of Important Interceptors in Spring

Spring provides interception mechanisms at multiple layers:

| Layer               | Interceptor Type           |
| ------------------- | -------------------------- |
| Servlet Layer       | Filter                     |
| Spring MVC Layer    | HandlerInterceptor         |
| Application Layer   | Spring AOP (`@Aspect`)     |
| Security Layer      | Spring Security Filters    |
| Outgoing HTTP Layer | Client Interceptors        |
| Database Layer      | JPA/Hibernate Interceptors |

---

## 4. Servlet Filters

Servlet Filters operate at the **lowest web layer**.

They intercept requests before Spring MVC processes them.

### 4.1 Flow

```text
Client Request
      ↓
Servlet Filter
      ↓
DispatcherServlet
      ↓
Controller
```

### 4.2 Example

```java
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Before request");

        chain.doFilter(request, response);

        System.out.println("After request");
    }
}
```

### 4.3 Common Use Cases

* CORS handling
* request logging
* JWT validation
* request/response wrapping
* compression
* security preprocessing


### 4.4 Important Characteristics

* Works before Spring MVC
* Part of Servlet API
* Can modify raw request/response objects
* Works for every request

---

# 5. Spring MVC HandlerInterceptor

`HandlerInterceptor` works inside the Spring MVC request lifecycle.

It intercepts requests before and after controller execution.


### 5.1 Flow

```text
Client Request
      ↓
DispatcherServlet
      ↓
HandlerInterceptor
      ↓
Controller
      ↓
Response
```

### 5.2 Example

```java
@Component
public class MyCustomInterceptor
        implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        System.out.println("Inside preHandle");

        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) {

        System.out.println("Inside postHandle");
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        System.out.println("Inside afterCompletion");
    }
}
```

### 5.3 Registering the Interceptor

```java
@Configuration
public class AppConfig
        implements WebMvcConfigurer {

    @Autowired
    private MyCustomInterceptor interceptor;

    @Override
    public void addInterceptors(
            InterceptorRegistry registry) {

        registry.addInterceptor(interceptor);
    }
}
```

### 5.4 Lifecycle Methods

| Method              | Purpose                           |
| ------------------- | --------------------------------- |
| `preHandle()`       | before controller                 |
| `postHandle()`      | after controller, before response |
| `afterCompletion()` | after request completes           |

### 5.5 Common Use Cases

* authentication
* authorization
* request logging
* locale handling
* API tracking
* rate limiting

---

## 6. Spring AOP Interceptors (`@Aspect`)

Spring AOP intercepts **method executions** inside Spring beans.

This is different from request interception.


### 6.1 Flow

```text
Method Call
     ↓
Spring Proxy
     ↓
Aspect Advice
     ↓
Actual Method
```


### 6.2 Example

```java
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(MyCustomAnnotation)")
    public Object intercept(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        System.out.println("Before method");

        Object result = joinPoint.proceed();

        System.out.println("After method");

        return result;
    }
}
```

### 6.3 What `joinPoint.proceed()` Means

```java
joinPoint.proceed();
```

means:

> continue execution of the actual method.

Without it, the target method never executes.

### 6.4 Common Use Cases

* transaction management
* logging
* performance timing
* auditing
* retries
* security
* monitoring

### 6.5 Important Characteristics

* Proxy-based
* Method-level interception
* Works on Spring beans
* Not tied to HTTP requests

---

## 7. Spring Security Filters

Spring Security internally uses a chain of filters.

These filters handle:

* authentication
* authorization
* session management
* CSRF protection

### 7.1 Security Flow

```text
Client Request
      ↓
Security Filter Chain
      ↓
Authentication
      ↓
Authorization
      ↓
Controller
```

### 7.2 Custom JWT Filter Example

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

        System.out.println("JWT validation");

        filterChain.doFilter(request, response);
    }
}
```

### 7.3 Common Use Cases

* JWT authentication
* token validation
* role-based access
* session handling
* security auditing

---

## 8. Client HTTP Interceptors

These intercept outgoing HTTP requests made by your application.

# 8.1 RestTemplate Interceptor

```java
public class LoggingInterceptor
        implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution)
            throws IOException {

        System.out.println("Outgoing request");

        return execution.execute(request, body);
    }
}
```

### 8.2 Flow

```text
Your Application
        ↓
Client Interceptor
        ↓
External API
```

### 8.3 Common Use Cases

* adding headers
* logging API calls
* retry mechanisms
* tracing
* API monitoring

---

## 9. Hibernate/JPA Interceptors

These intercept database entity lifecycle events.

### 9.1 JPA Entity Listener Example

```java
@EntityListeners(UserListener.class)
@Entity
public class User {
}
```

### 9.2 Listener

```java
public class UserListener {

    @PrePersist
    public void beforeSave(User user) {
        System.out.println("Before save");
    }

    @PostPersist
    public void afterSave(User user) {
        System.out.println("After save");
    }
}
```

### 9.3 Common Use Cases

* audit logging
* timestamps
* soft deletes
* tracking changes
* entity validation

---

## 10. Complete Request Flow in Spring

A real Spring Boot request typically flows like this:

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

---

## 11. Filter vs Interceptor vs AOP

| Feature                 | Filter        | HandlerInterceptor | AOP                  |
| ----------------------- | ------------- | ------------------ | -------------------- |
| Layer                   | Servlet       | Spring MVC         | Application          |
| Intercepts              | HTTP request  | Controller request | Method execution     |
| Works Before Spring MVC | Yes           | No                 | No                   |
| Can modify raw request  | Yes           | Limited            | No                   |
| Works on methods        | No            | No                 | Yes                  |
| HTTP-specific           | Yes           | Yes                | No                   |
| Common Use              | Security/CORS | Auth/logging       | Transactions/logging |

---

## 12. When To Use What

| Requirement               | Best Choice        |
| ------------------------- | ------------------ |
| Raw request handling      | Filter             |
| Controller-level checks   | HandlerInterceptor |
| Business method logging   | AOP                |
| JWT authentication        | Security Filter    |
| Outgoing API logging      | Client Interceptor |
| Entity lifecycle tracking | JPA Listener       |

---

## 13. Important Interview Questions

### Q1. Difference between Filter and HandlerInterceptor?

* Filter works at Servlet level
* HandlerInterceptor works at Spring MVC level

---

### Q2. Difference between AOP and Interceptor?

* AOP intercepts methods
* Interceptors mainly intercept requests

---

### Q3. Why use AOP?

To separate cross-cutting concerns like logging and transactions from business logic.

---

### Q4. What is `joinPoint.proceed()`?

It continues execution of the target method in `@Around` advice.

---

### Q5. Which interceptor is best for JWT validation?

Usually:

* Security Filters
  or
* HandlerInterceptor

depending on application architecture.

---

## 14. Key Takeaways

* Spring supports interception at multiple layers

* Filters work at the Servlet level

* HandlerInterceptors work at the MVC level

* AOP intercepts method execution

* Security filters manage authentication and authorization

* Client interceptors handle outgoing HTTP requests

* JPA listeners intercept entity lifecycle events

* Choosing the correct interceptor depends on what needs to be intercepted

---

