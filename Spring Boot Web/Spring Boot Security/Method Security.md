# Method-Level Security in Spring Boot

## 1. Overview

**Method-level security** is a mechanism in Spring Security that allows you to enforce **authorization rules directly on methods**, typically within the **service layer**.

Instead of relying only on endpoint protection, method security ensures:

* business logic is protected
* internal method calls remain secure
* authorization rules are centralized
* defense-in-depth is achieved

This is considered a **production-grade security practice**.

---

## 2. Why Method-Level Security Is Important

Relying only on controller security is risky because:

* methods may be reused internally
* APIs may evolve
* multiple controllers may call the same service
* developers might accidentally expose endpoints

### Example Risk

```
Controller secured → Service not secured
```

Another internal service calls it → **Unauthorized access occurs.**

Method-level security eliminates this risk.

---

## 3. Where Method Security Fits in the Security Flow

```
User Request
   ↓
Authentication
   ↓
SecurityContext Created
   ↓
Authorization Begins
   ↓
Method Security Check
   ↓
Business Logic Executes
```

Method security happens **after authentication** but **before method execution**.

---

## 4. Enabling Method-Level Security

Modern Spring Boot configuration:

```java
@Configuration
@EnableMethodSecurity
public class SecurityConfig {
}
```

This enables annotations such as:

* `@PreAuthorize`
* `@PostAuthorize`
* `@Secured`
* `@RolesAllowed`

Without this annotation, method security **does not work**.

---

## 5. How Method-Level Security Works Internally

Spring uses **AOP (Aspect-Oriented Programming)**.

Process:

1. Spring creates a proxy around the bean.
2. Calls are intercepted.
3. Authorization logic is evaluated.
4. Access is granted or denied.

```
Client → Proxy → Security Check → Method
```

---

## 6. @PreAuthorize (Most Important Annotation)

### Purpose

Checks authorization **before the method executes**.

### Example

```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) {
}
```

Only users with `ROLE_ADMIN` can execute it.

---

### Multiple Roles

```java
@PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
public void approveTransaction(Long id) {
}
```

---

### Using Authorities

```java
@PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
public void createReport() {
}
```

Authorities provide finer control than roles.

---

## 7. Expression-Based Access Control (SpEL)

Spring Security uses **Spring Expression Language (SpEL)**.

Common expressions:

| Expression                   | Meaning                    |
| ---------------------------- | -------------------------- |
| `hasRole('ADMIN')`           | User has ADMIN role        |
| `hasAnyRole('USER','ADMIN')` | User has any role          |
| `authentication.name`        | Current username           |
| `principal`                  | Current authenticated user |
| `#param`                     | Method parameter           |

---

### Example: Parameter-Based Authorization

```java
@PreAuthorize("#userId == authentication.name")
public User getUser(String userId) {
}
```

Users can only access their own data.

This is extremely common in production systems.

---

## 8. @PostAuthorize

### Purpose

Authorization is evaluated **after the method returns**.

Used when decision depends on returned object.

### Example

```java
@PostAuthorize("returnObject.owner == authentication.name")
public Document getDocument(Long id) {
}
```

Useful for:

* ownership validation
* object-level security

Less common than `@PreAuthorize`.

---

## 9. @Secured (Legacy Annotation)

Simpler but less powerful.

```java
@Secured("ROLE_ADMIN")
public void shutdownSystem() {
}
```

Limitations:

* no expressions
* role-only checks
* less flexible

Mostly seen in older systems.

---

## 10. @RolesAllowed (JSR-250)

Java-standard annotation:

```java
@RolesAllowed("ADMIN")
public void generateReport() {
}
```

Requires enabling JSR-250 support.

Less expressive than `@PreAuthorize`.

---

## 11. Real-World Backend Example

### Scenario: Banking Application

Rules:

* USER → view own account
* ADMIN → view all accounts

---

### Service Layer

```java
@Service
public class AccountService {

    @PreAuthorize("#accountId == principal.id")
    public Account getAccount(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
```

Even if an endpoint is misconfigured, business logic remains secure.

---

## 12. Method Security vs Endpoint Security

| Feature     | Endpoint Security | Method Security     |
| ----------- | ----------------- | ------------------- |
| Scope       | URL level         | Business logic      |
| Granularity | Coarse            | Fine                |
| Protection  | External calls    | Internal + external |
| Recommended | Yes               | Mandatory           |

Best practice → **Use both.**

---

## 13. Advanced: Combining Conditions

```java
@PreAuthorize(
    "hasRole('ADMIN') or #userId == authentication.name"
)
```

Allows admins full access while restricting normal users.

---

## 14. Method Security with Custom UserDetails

You can reference custom fields:

```java
@PreAuthorize("#order.userId == principal.id")
```

This enables **domain-level authorization**.

Highly valued in system design interviews.

---

## 15. Common Pitfall — Self Invocation

Method security works via proxies.

Calling a secured method internally:

```java
this.securedMethod();
```

bypasses the proxy → security is skipped.

Solution:

* call via another bean
* avoid self-invocation

---

## 16. Testing Method-Level Security

Spring provides testing support:

```java
@WithMockUser(roles = "ADMIN")
@Test
void adminShouldDeleteUser() {
}
```

Allows authorization testing without real authentication.

---

## 17. Common Mistakes

* securing only controllers
* forgetting `@EnableMethodSecurity`
* using roles without `ROLE_` prefix
* placing business logic in controllers
* ignoring ownership checks

---

## 18. Interview Questions

### Q1: Why is method-level security important?

Because business logic must be protected regardless of entry point.

---

### Q2: Which annotation is most commonly used?

`@PreAuthorize`.

---

### Q3: When should @PostAuthorize be used?

When authorization depends on returned data.

---

### Q4: Does method security replace endpoint security?

No — both should be used together.

---

### Q5: What enables method security internally?

Spring AOP proxies.

---

## Key Takeaways

* Method-level security protects business logic

* Runs after authentication

* Uses AOP proxies

* `@PreAuthorize` is the most powerful annotation

* Supports expression-based access control

* Prevents privilege escalation

* Essential for production-grade backend systems

---
