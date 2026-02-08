# Custom Filters in Spring Boot

## 1. Overview

A **custom filter** in Spring Boot is used to intercept HTTP requests and responses to perform **cross-cutting concerns** such as:

* authentication (JWT, API keys)
* logging
* request validation
* rate limiting
* header manipulation
* auditing

Filters operate at a **very low level** in the request lifecycle — before the request reaches the controller.

Understanding custom filters is essential for:

* advanced Spring Security design
* stateless authentication systems
* microservices security
* interview-level backend knowledge

---

## 2. Where Custom Filters Fit in the Request Flow

```
Client Request
   ↓
Security Filter Chain
   ↓
Custom Filter
   ↓
Authentication / Authorization Filters
   ↓
DispatcherServlet
   ↓
Controller
```

Custom filters usually execute **before authentication** or **alongside it**.

---

## 3. Why Create a Custom Filter?

Spring provides many built-in filters, but custom filters are needed when:

### Common Real-World Use Cases

* JWT token validation
* API key authentication
* request correlation IDs
* security header enforcement
* tenant resolution (multi-tenant apps)
* request logging
* IP whitelisting

Custom filters are extremely common in production systems.

---

## 4. Filter vs OncePerRequestFilter (Important)

Spring recommends extending:

```
OncePerRequestFilter
```

instead of implementing `Filter` directly.

### Why?

It guarantees the filter runs **only once per request**, preventing duplicate execution during:

* request forwarding
* async dispatch
* error handling

---

## 5. Creating a Custom Filter

### Example: JWT Authentication Filter

```java
@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String header =
                request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            // validate token
            // extract username
            // set authentication
        }

        filterChain.doFilter(request, response);
    }
}
```

### Key Rule

Always call:

```java
filterChain.doFilter(request, response);
```

Otherwise the request stops.

---

## 6. Setting Authentication Inside a Filter

After validating credentials:

```java
Authentication auth =
        new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

SecurityContextHolder.getContext()
        .setAuthentication(auth);
```

Now the user is considered authenticated.

---

## 7. Registering Custom Filters in Spring Security

Custom filters must be inserted into the **Security Filter Chain**.

### Example

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http)
        throws Exception {

    http
        .addFilterBefore(
            jwtFilter,
            UsernamePasswordAuthenticationFilter.class
        );

    return http.build();
}
```

---

## 8. Why Filter Order Matters

Security filters execute in sequence.

If order is incorrect:

* authentication may fail
* authorization may run too early
* SecurityContext may be empty

---

### Common Placement

| Goal                 | Place Filter                                |
| -------------------- | ------------------------------------------- |
| JWT validation       | Before UsernamePasswordAuthenticationFilter |
| Logging              | Very early                                  |
| Authorization tweaks | After authentication                        |

---

## 9. addFilterBefore vs addFilterAfter

### addFilterBefore

Runs filter earlier.

```java
.addFilterBefore(jwtFilter,
                 UsernamePasswordAuthenticationFilter.class)
```

Used for:

* token extraction
* authentication

---

### addFilterAfter

Runs filter later.

Useful for:

* auditing
* response manipulation

---

## 10. Should Filters Contain Business Logic?

**No.**

Filters should only handle:

* security
* request metadata
* cross-cutting concerns

Never put:

* database logic
* domain rules
* heavy processing

Keep filters lightweight.

---

## 11. Custom Filter for API Key Authentication (Example)

```java
public class ApiKeyFilter
        extends OncePerRequestFilter {

    private static final String API_KEY = "X-API-KEY";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        String key = request.getHeader(API_KEY);

        if (!isValid(key)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }
}
```

Common in:

* internal microservices
* gateway authentication

---

## 12. Filter vs HandlerInterceptor

| Feature                       | Filter    | Interceptor |
| ----------------------------- | --------- | ----------- |
| Level                         | Servlet   | Spring MVC  |
| Runs before DispatcherServlet | Yes       | No          |
| Security use                  | Excellent | Limited     |
| Access to Spring beans        | Possible  | Native      |

Security should generally use filters.

---

## 13. Common Mistakes

### ❌ Forgetting `doFilter()`

Stops request entirely.

---

### ❌ Wrong Filter Order

Breaks authentication flow.

---

### ❌ Writing Authentication Logic in Controller

Security belongs in filters/providers.

---

### ❌ Heavy Processing

Filters should remain fast.

---

## 14. Advanced Insight — DelegatingFilterProxy

Spring Security uses:

```
DelegatingFilterProxy
```

to bridge:

```
Servlet Container → Spring Beans
```

Your custom filters ultimately become part of this chain.

---

## 15. When NOT to Use Custom Filters

Avoid filters when:

* logic belongs to service layer
* validation is domain-specific
* operation is not request-wide

Use filters only for **cross-cutting concerns**.

---

## 16. Real-World Security Architecture Example

Typical stateless REST API:

```
Request
 ↓
CORS Filter
 ↓
JWT Filter
 ↓
Authentication
 ↓
Authorization
 ↓
Controller
```

Custom filters are central to modern API security.

---

## 17. Interview Questions

### Q1: Why extend OncePerRequestFilter?

To guarantee a filter runs once per request.

---

### Q2: Where should JWT validation occur?

Inside a custom security filter.

---

### Q3: What happens if doFilter is not called?

Request processing stops.

---

### Q4: Why does filter order matter?

Because authentication must occur before authorization.

---

### Q5: Filters vs Interceptors?

Filters are lower-level and better suited for security.

---

## Key Takeaways

* Custom filters intercept HTTP requests early

* Used for authentication, logging, and security

* Extend `OncePerRequestFilter` for reliability

* Always call `filterChain.doFilter()`

* Register filters in the SecurityFilterChain

* Filter order is critical

* Keep filters lightweight

* Essential for JWT and stateless security architectures

---
