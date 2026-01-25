# CSRF and CORS in Spring Boot

## 1. Overview

**CSRF** and **CORS** are two frequently confused but **fundamentally different security concepts** in Spring Boot applications.

| Concept                              | Purpose                                         |
| ------------------------------------ | ----------------------------------------------- |
| CSRF (Cross-Site Request Forgery)    | Protects against forged state-changing requests |
| CORS (Cross-Origin Resource Sharing) | Controls cross-origin browser requests          |

Understanding **when to enable, disable, or configure** CSRF and CORS is critical for **secure REST APIs** and is a **common interview topic**.

---

## 2. CSRF (Cross-Site Request Forgery)

### 2.1 What is CSRF?

CSRF is an attack where:

* a malicious website tricks a user’s browser
* into sending authenticated requests
* to a trusted application without user consent

The browser automatically includes:

* cookies
* session identifiers

This makes CSRF possible in **cookie-based authentication**.

---

### 2.2 CSRF Attack Example

User is logged into:

```
bank.com
```

Malicious site triggers:

```
POST /transfer?amount=10000
```

Browser sends cookies automatically → request succeeds.

---

## 3. CSRF Protection in Spring Boot

Spring Security **enables CSRF protection by default**.

Protection mechanism:

* a CSRF token is generated per session
* token must be included in state-changing requests
* server validates the token

---

### 3.1 When CSRF Protection is Required

CSRF protection is needed when:

* authentication is **session-based**
* cookies are used
* browser clients are involved

Typical examples:

* traditional web apps
* server-side rendered apps
* form submissions

---

### 3.2 When CSRF Protection is NOT Required

CSRF is usually **not required** when:

* APIs are stateless
* authentication uses **JWT or OAuth2 tokens**
* tokens are sent via headers (Authorization)

---

## 4. Disabling CSRF for REST APIs

For most **stateless REST APIs**, CSRF is disabled.

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        );

    return http.build();
}
```

Disabling CSRF is **safe only if**:

* no session cookies are used
* authentication is token-based

---

## 5. Selective CSRF Configuration

You can selectively ignore CSRF for specific endpoints.

```java
http
    .csrf(csrf -> csrf
        .ignoringRequestMatchers("/api/**")
    );
```

Useful in hybrid applications.

---

## 6. CSRF Best Practices

* enable CSRF for session-based apps
* disable CSRF for stateless APIs
* never disable CSRF blindly
* understand authentication mechanism first

---

## 7. CORS (Cross-Origin Resource Sharing)

### 7.1 What is CORS?

CORS is a **browser-enforced security mechanism** that controls:

> Which origins (domains) can call your backend APIs.

It prevents malicious websites from reading responses of other sites.

---

### 7.2 Same-Origin Policy

By default, browsers block requests when:

* protocol differs
* domain differs
* port differs

Example:

```
Frontend: http://localhost:3000
Backend:  http://localhost:8080
```

This is a **cross-origin request**.

---

## 8. CORS Preflight Requests

For non-simple requests, browsers send a **preflight request**.

```
OPTIONS /api/users
```

Purpose:

* check allowed methods
* check allowed headers
* check allowed origins

Spring Boot must respond correctly.

---

## 9. Configuring CORS in Spring Boot

### 9.1 Controller-Level CORS

```java
@CrossOrigin(
    origins = "http://localhost:3000",
    methods = { RequestMethod.GET, RequestMethod.POST }
)
@RestController
public class UserController {
}
```

Simple but not scalable.

---

### 9.2 Global CORS Configuration (Recommended)

```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {

    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

    source.registerCorsConfiguration("/**", config);
    return source;
}
```

---

### 9.3 CORS with Spring Security

CORS must be enabled **inside security configuration**.

```java
http
    .cors()
    .and()
    .csrf(csrf -> csrf.disable());
```

If CORS is not enabled in Spring Security, browser requests may still fail.

---

## 10. CSRF vs CORS (Key Differences)

| Aspect           | CSRF               | CORS                             |
| ---------------- | ------------------ | -------------------------------- |
| Protects against | Forged requests    | Unauthorized cross-origin access |
| Enforced by      | Server             | Browser                          |
| Applies to       | Cookies / sessions | Browsers only                    |
| Affects APIs     | Sometimes          | Always (browser clients)         |

They solve **different problems**.

---

## 11. Real-World Backend Scenarios

### Scenario 1: SPA + REST API

* Frontend: React (localhost:3000)
* Backend: Spring Boot (localhost:8080)
* Auth: JWT

Configuration:

* CSRF → disabled
* CORS → enabled for frontend origin

---

### Scenario 2: Traditional Web App

* Server-rendered pages
* Session-based auth

Configuration:

* CSRF → enabled
* CORS → usually not needed

---

## 12. Common Mistakes

* disabling CSRF without understanding auth type
* confusing CORS errors with authentication errors
* allowing `*` origins with credentials
* forgetting to enable CORS in security config

---

## 13. Interview Questions

### Q1: Should CSRF be enabled for JWT-based APIs?

No, because JWT does not rely on cookies.

---

### Q2: Why does browser send OPTIONS request?

To perform CORS preflight checks.

---

### Q3: Can CORS be disabled?

No. It is enforced by browsers, not servers.

---

### Q4: Is CORS a security feature?

Yes, but it protects the **browser user**, not the server.

---

### Q5: What happens if CORS is misconfigured?

Browser blocks the request, even if backend is correct.

---

## Key Takeaways

* CSRF and CORS are different security concepts

* CSRF protects against forged requests in cookie-based auth

* CORS controls cross-origin browser requests

* Stateless REST APIs usually disable CSRF

* CORS must be configured explicitly for SPAs

* Both are critical for secure Spring Boot applications

---
