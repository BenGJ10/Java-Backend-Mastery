# Filters and Filter Chain in Spring Boot

## 1. Overview

In Spring Boot applications, **filters** and the **filter chain** play a crucial role in:

* request preprocessing
* security enforcement
* logging and auditing
* cross-cutting concerns

Every incoming HTTP request passes through a **chain of filters** *before* reaching the controller, and every response passes back through the same chain.

This concept is **fundamental** to understanding **Spring Security**, **request lifecycle**, and **real-world backend behavior**.

---

## 2. What Is a Filter?

A **filter** is a component that:

* intercepts HTTP requests and responses
* executes **before and/or after** controller logic
* can modify request, response, or both

Filters are part of the **Servlet specification**, not Spring-specific.

---

## 3. Request Lifecycle with Filters

High-level flow:

```
Client
 ↓
Servlet Filters
 ↓
Spring DispatcherServlet
 ↓
Controller
 ↓
Service / Repository
 ↓
Response
 ↑
Servlet Filters (reverse order)
 ↑
Client
```

Filters run **before controllers** and **after responses are generated**.

---

## 4. What Is a Filter Chain?

A **filter chain** is an ordered sequence of filters.

Each filter:

* performs its logic
* decides whether to:

  * continue the chain
  * block the request

This is implemented using the **Chain of Responsibility** design pattern.

---

## 5. Core Filter Interface

```java
public interface Filter {

    void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException;
}
```

Key point:

* calling `chain.doFilter()` passes control to the **next filter**
* not calling it **stops request processing**

---

## 6. Simple Custom Filter Example

```java
@Component
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        System.out.println("Incoming request: " + req.getRequestURI());

        chain.doFilter(request, response);

        System.out.println("Outgoing response");
    }
}
```

This filter:

* logs request before controller
* logs response after controller

---

## 7. Filter Ordering

Order matters.

Filters execute:

* **request** → top to bottom
* **response** → bottom to top

You can control order using:

```java
@Order(1)
@Component
public class FirstFilter implements Filter {
}
```

Lower number → higher priority.

---

## 8. Servlet Filters vs Spring Interceptors

| Aspect                        | Filter            | Interceptor   |
| ----------------------------- | ----------------- | ------------- |
| Level                         | Servlet container | Spring MVC    |
| Runs before DispatcherServlet | Yes               | No            |
| Access to Spring context      | Limited           | Full          |
| Use cases                     | Security, logging | Handler logic |

Filters are **lower-level** and more powerful.

---

## 9. Spring Security Filter Chain

Spring Security is built **entirely on filters**.

### Key Concept

Spring Security registers a special filter:

```
DelegatingFilterProxy
```

which delegates to:

```
SecurityFilterChain
```

---

## 10. Spring Security Filter Chain Flow

```
HTTP Request
 ↓
DelegatingFilterProxy
 ↓
SecurityFilterChain
 ↓
Authentication Filters
 ↓
Authorization Filters
 ↓
Controller
```

Every secured request must pass through this chain.

---

## 11. Important Spring Security Filters (High-Level)

Some commonly discussed filters:

| Filter                               | Responsibility              |
| ------------------------------------ | --------------------------- |
| UsernamePasswordAuthenticationFilter | Handles login               |
| BasicAuthenticationFilter            | HTTP Basic auth             |
| BearerTokenAuthenticationFilter      | JWT authentication          |
| SecurityContextHolderFilter          | Stores authentication       |
| ExceptionTranslationFilter           | Handles security exceptions |
| FilterSecurityInterceptor            | Authorization decision      |

Not all filters run for every request.

---

## 12. Authentication in Filter Chain

Authentication happens **before controller execution**.

Steps:

1. Filter extracts credentials (JWT, username/password)
2. AuthenticationManager validates credentials
3. SecurityContext is populated
4. Request proceeds if authenticated

If authentication fails → request is blocked.

---

## 13. Authorization in Filter Chain

Authorization checks:

* user roles
* permissions
* access rules

Happens **after authentication**, before controller.

Example:

```text
hasRole('ADMIN')
```

If authorization fails → 403 Forbidden.

---

## 14. Custom Security Filter Example (JWT)

```java
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null) {
            // validate token
            // set authentication in SecurityContext
        }

        filterChain.doFilter(request, response);
    }
}
```

Used for:

* JWT validation
* API key authentication
* request-level security checks

---

## 15. Registering Custom Filters in Spring Security

```java
http
    .addFilterBefore(
        jwtFilter,
        UsernamePasswordAuthenticationFilter.class
    );
```

This defines **exact position** in the security filter chain.

---

## 16. OncePerRequestFilter

Spring provides `OncePerRequestFilter` to ensure:

* filter runs **once per request**
* avoids duplicate execution during forwards or errors

Recommended base class for custom security filters.

---

## 17. When to Use Filters

Filters are best suited for:

* authentication (JWT, API keys)
* logging and auditing
* request tracing
* CORS handling
* compression
* security enforcement

---

## 18. Common Mistakes

* forgetting `chain.doFilter()`
* placing filters in wrong order
* performing business logic in filters
* confusing filters with interceptors
* logging sensitive data

---

## 19. Interview Questions

### Q1: What is a filter?

A component that intercepts HTTP requests before controllers.

---

### Q2: What is a filter chain?

An ordered sequence of filters applied to a request.

---

### Q3: How does Spring Security work internally?

Using a chain of servlet filters.

---

### Q4: Difference between filter and interceptor?

Filter runs before DispatcherServlet; interceptor runs inside Spring MVC.

---

### Q5: Why order matters in filters?

Because authentication and authorization depend on execution order.

---

## Key Takeaways

* Filters intercept requests before controllers

* Filter chain follows chain-of-responsibility pattern

* Spring Security is implemented using filters

* Order of filters is critical

* Custom filters are common for JWT and API security

* Filters handle cross-cutting concerns, not business logic

* Understanding filter chain is essential for backend security

---

