# Role-Based Authorization and Method Security

## 1. Overview

**Authorization** in Spring Boot determines **what an authenticated user is allowed to do**.
The most common and practical authorization model is **Role-Based Access Control (RBAC)**, often combined with **method-level security**.

In real-world backend systems:

* endpoint-level security protects APIs
* **method-level security protects business logic**

Spring Security supports both and encourages **defense in depth**.

---

## 2. Authentication vs Authorization (Quick Recap)

| Concept        | Meaning                               |
| -------------- | ------------------------------------- |
| Authentication | Verifies *who* the user is            |
| Authorization  | Determines *what* the user can access |

Authorization always happens **after authentication**.

---

## 3. Role-Based Authorization (RBAC)

### 3.1 What is RBAC?

RBAC restricts access based on **roles assigned to users**.

Example roles:

* `ROLE_USER`
* `ROLE_ADMIN`
* `ROLE_MANAGER`

Each role grants access to specific resources or operations.

---

### 3.2 Roles vs Authorities

In Spring Security:

* **Role** → a special type of authority
* Roles are internally prefixed with `ROLE_`

Example:

```text
ROLE_ADMIN
ROLE_USER
```

Technically, Spring Security works with **authorities**, but roles are a convention.

---

## 4. Endpoint-Level Authorization

Endpoint-level security controls **which HTTP endpoints** can be accessed.

### Example: Securing Endpoints by Role

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
        )
        .httpBasic();

    return http.build();
}
```

### Behavior

* `/admin/**` → ADMIN only
* `/user/**` → USER or ADMIN
* `/auth/**` → public
* everything else → authenticated

This is coarse-grained authorization.

---

## 5. Why Endpoint Security Is Not Enough

Endpoint security has limitations:

* business logic may be reused internally
* multiple endpoints may call same service
* internal calls bypass controller-level checks

**This is why method-level security is critical.**

---

## 6. Method-Level Security

Method-level security enforces authorization **inside the service layer**, where business logic lives.

It ensures:

* consistent security
* protection even if controller is bypassed
* fine-grained access control

---

## 7. Enabling Method Security

In modern Spring Boot:

```java
@EnableMethodSecurity
@Configuration
public class MethodSecurityConfig {
}
```

This enables annotations like:

* `@PreAuthorize`
* `@PostAuthorize`
* `@Secured`
* `@RolesAllowed`

---

## 8. `@PreAuthorize` (Most Common)

### Purpose

Checks authorization **before method execution**.

### Example

```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) {
}
```

Only users with `ROLE_ADMIN` can execute this method.

---

### Using Multiple Roles

```java
@PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
public void approveOrder(Long orderId) {
}
```

---

### Checking Authorities

```java
@PreAuthorize("hasAuthority('READ_REPORTS')")
public Report getReport() {
}
```

Used when permissions are more granular than roles.

---

## 9. `@PostAuthorize`

### Purpose

Checks authorization **after method execution**, based on return value.

### Example

```java
@PostAuthorize("returnObject.owner == authentication.name")
public Document getDocument(Long id) {
}
```

Use cases:

* ownership checks
* data-level security

Less common, but very powerful.

---

## 10. `@Secured`

Older and simpler annotation.

```java
@Secured("ROLE_ADMIN")
public void shutdownSystem() {
}
```

Limitations:

* no expressions
* role-only checks
* less flexible

Still used in legacy systems.

---

## 11. `@RolesAllowed` (JSR-250)

```java
@RolesAllowed("ADMIN")
public void generateReport() {
}
```

* Java standard
* Less expressive than `@PreAuthorize`
* Requires JSR-250 support enabled

---

## 12. Real-World Backend Example

### Scenario: Order Management System

#### Roles

* USER → create and view own orders
* ADMIN → manage all orders

---

### Service Layer Security

```java
@Service
public class OrderService {

    @PreAuthorize("hasRole('USER')")
    public Order createOrder(OrderDTO dto) {
        return null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void cancelAnyOrder(Long orderId) {
    }

    @PreAuthorize("hasRole('USER') and #userId == authentication.name")
    public List<Order> getUserOrders(String userId) {
        return null;
    }
}
```

Security is enforced **at the business logic level**, not just HTTP.

---

## 13. Method Security Expression Language (SpEL)

Spring Security uses **SpEL** for authorization rules.

Common expressions:

| Expression                   | Meaning             |
| ---------------------------- | ------------------- |
| `hasRole('ADMIN')`           | User has ADMIN role |
| `hasAnyRole('USER','ADMIN')` | User has any role   |
| `authentication.name`        | Current username    |
| `principal`                  | Current user object |
| `#paramName`                 | Method parameter    |

Example:

```java
@PreAuthorize("#id == authentication.principal.id")
```

---

## 14. Combining Endpoint and Method Security

**Best practice** is to use both.

| Layer             | Responsibility                     |
| ----------------- | ---------------------------------- |
| Endpoint security | Coarse-grained access              |
| Method security   | Fine-grained, business-level rules |

This creates **defense in depth**.

---

## 15. Common Mistakes

* securing only controllers
* trusting frontend role checks
* putting authorization logic in controller
* using hardcoded role strings everywhere
* skipping method security

---

## 16. Testing Role-Based Security

Spring provides testing support:

```java
@WithMockUser(roles = "ADMIN")
@Test
void adminCanDelete() {
}
```

This allows testing authorization rules without real authentication.

---

## 17. Interview Questions

### Q1: Why method-level security is important?

Because business logic must be protected regardless of entry point.

---

### Q2: Difference between `hasRole` and `hasAuthority`?

`hasRole` adds `ROLE_` prefix automatically, `hasAuthority` does not.

---

### Q3: Which annotation is most flexible?

`@PreAuthorize`.

---

### Q4: Can method security replace endpoint security?

No. Both should be used together.

---

### Q5: Where should authorization rules live?

Primarily in the service layer.

---

## Key Takeaways

* Role-based authorization controls access using roles

* Spring Security treats roles as authorities

* Endpoint security protects URLs

* Method security protects business logic

* `@PreAuthorize` is the most powerful and common annotation

* Always use defense in depth

* Method-level security is critical for real-world backend systems

---
