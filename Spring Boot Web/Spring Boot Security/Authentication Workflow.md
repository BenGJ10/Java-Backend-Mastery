# Authentication Workflow in Spring Security

## 1. Overview

Spring Security authentication follows a **well-defined, layered workflow**.

Each component has **one clear responsibility**, and authentication is achieved by collaboration between these components — not by any single class.

Understanding this flow is **mandatory** for:

* real-world backend development
* debugging authentication issues
* JWT / custom authentication implementations
* Spring Security interviews

---

## 2. High-Level Authentication Flow

```
HTTP Request
   ↓
Authentication Filter
   ↓
AuthenticationManager
   ↓
AuthenticationProvider
   ↓
UserDetailsService
   ↓
PasswordEncoder
   ↓
Authentication Result
   ↓
SecurityContext
```

**Important rule**

> Filters do not authenticate users.
> AuthenticationProviders do.

---

## 3. Step-by-Step Authentication Workflow

We will walk through a **username + password (JDBC)** authentication flow, which is the foundation for most systems.

---

## 4. Authentication Filter

### Role

* Intercepts incoming HTTP request
* Extracts credentials
* Creates an Authentication token
* Delegates authentication

### Example Filter

```java
UsernamePasswordAuthenticationFilter
```

### What It Does

1. Reads username and password from request
2. Creates an **unauthenticated Authentication object**

```java
UsernamePasswordAuthenticationToken authRequest =
        new UsernamePasswordAuthenticationToken(username, password);
```

3. Passes token to `AuthenticationManager`

```java
authenticationManager.authenticate(authRequest);
```

### Key Point

The filter **never validates credentials**.

---

## 5. Authentication Object (Token)

Spring Security represents credentials using `Authentication`.

### Before Authentication

```java
authenticated = false
principal = username
credentials = raw password
```

### After Authentication

```java
authenticated = true
principal = UserDetails
credentials = null
authorities = roles
```

The same object type is reused, but its state changes.

---

## 6. AuthenticationManager

### Role

* Central entry point for authentication
* Delegates authentication to providers

```java
Authentication authenticate(Authentication authentication);
```

### Default Implementation

```java
ProviderManager
```

### What It Does

* Holds a list of AuthenticationProviders
* Iterates through them
* Finds a provider that `supports()` the token type

---

## 7. AuthenticationProvider

### Role

This is where **actual authentication happens**.

```java
public interface AuthenticationProvider {
    Authentication authenticate(Authentication authentication);
    boolean supports(Class<?> authentication);
}
```

---

### Example: DaoAuthenticationProvider

Used for:

* database-backed authentication
* JPA / JDBC / UserDetailsService

---

### Internal Steps of AuthenticationProvider

1. Receives `UsernamePasswordAuthenticationToken`
2. Calls `supports()` to verify compatibility
3. Delegates user lookup to `UserDetailsService`
4. Delegates password comparison to `PasswordEncoder`
5. Returns authenticated token

---

## 8. UserDetailsService

### Role

Responsible for **loading user data**, not authentication.

```java
UserDetails loadUserByUsername(String username);
```

### Example

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(), // hashed password
                user.getAuthorities()
        );
    }
}
```

### Key Point

* Does **not** compare passwords
* Only fetches user details

---

## 9. PasswordEncoder

### Role

Handles secure password comparison.

```java
boolean matches(CharSequence raw, String encoded);
```

### Example

```java
BCryptPasswordEncoder
```

---

### Password Verification Flow

1. Raw password from request
2. Hashed password from database
3. Encoder hashes raw password
4. Compares hashes securely

```java
passwordEncoder.matches(rawPassword, storedHash);
```

**Passwords are never decrypted.**

---

## 10. Successful Authentication

If credentials are valid:

* AuthenticationProvider returns an **authenticated Authentication object**
* Authorities are attached
* Credentials are erased
* Authentication is stored in `SecurityContext`

```java
SecurityContextHolder.getContext()
        .setAuthentication(authentication);
```

---

## 11. SecurityContext and SecurityContextHolder

### Role

Stores authentication details for the current request/thread.

```java
SecurityContextHolder.getContext().getAuthentication();
```

Used by:

* authorization checks
* `@PreAuthorize`
* controllers and services

---

## 12. Failed Authentication Flow

If authentication fails:

* Provider throws `AuthenticationException`
* Filter catches exception
* Security returns:

  * `401 Unauthorized` (REST)
  * login error (web apps)

Common exceptions:

* `BadCredentialsException`
* `UsernameNotFoundException`
* `DisabledException`

---

## 13. Complete Workflow Diagram (Conceptual)

```
Client Request
   ↓
Authentication Filter
   ↓
AuthenticationManager (ProviderManager)
   ↓
AuthenticationProvider
   ↓
UserDetailsService ──► Database
   ↓
PasswordEncoder
   ↓
Authenticated Authentication
   ↓
SecurityContextHolder
   ↓
Controller / Service
```

---

## 14. JWT Authentication Workflow (Comparison)

| Component              | Username/Password | JWT          |
| ---------------------- | ----------------- | ------------ |
| Filter                 | Login filter      | JWT filter   |
| AuthenticationManager  | Yes               | Yes          |
| AuthenticationProvider | DAO provider      | JWT provider |
| UserDetailsService     | Yes               | Optional     |
| PasswordEncoder        | Yes               | No           |
| Session                | Stateful          | Stateless    |

Same architecture, different credentials.

---

## 15. Common Misconceptions (Interview Traps)

* Filters authenticate users ❌
* UserDetailsService validates passwords ❌
* Passwords are decrypted ❌
* AuthenticationManager contains logic ❌

Correct understanding:

* Filters extract data
* Providers authenticate
* Encoders compare hashes

---

## 16. Real-World Design Mapping

| Component              | Responsibility        |
| ---------------------- | --------------------- |
| Filter                 | Credential extraction |
| AuthenticationManager  | Delegation            |
| AuthenticationProvider | Authentication logic  |
| UserDetailsService     | User lookup           |
| PasswordEncoder        | Secure comparison     |
| SecurityContext        | Store result          |

---

## 17. Interview Questions

### Q1: Who authenticates the user?

AuthenticationProvider.

---

### Q2: Why is UserDetailsService needed?

To load user data from database.

---

### Q3: Why use PasswordEncoder?

To securely compare hashed passwords.

---

### Q4: Can we have multiple AuthenticationProviders?

Yes, ProviderManager supports multiple providers.

---

### Q5: Where should custom authentication logic go?

Inside a custom AuthenticationProvider.

---

## Key Takeaways

* Authentication is a **pipeline**, not a single class

* Filters extract credentials

* AuthenticationManager delegates

* AuthenticationProvider performs validation

* UserDetailsService loads user data

* PasswordEncoder handles hashing

* SecurityContext stores authentication

* This workflow underpins **all Spring Security authentication types**

---

