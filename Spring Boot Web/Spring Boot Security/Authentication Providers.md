# Authentication Providers in Spring Security

## 1. Overview

In Spring Security, **AuthenticationProvider** is a core extension point that performs **actual authentication logic**.

It answers the question:

> *Given these credentials, can this user be authenticated?*

Spring Security is designed so that **authentication is pluggable**.
Different authentication mechanisms (JDBC, JWT, LDAP, OAuth2) are implemented using **different AuthenticationProviders**.

Understanding this is essential for:

* designing custom authentication flows
* debugging login issues
* interviews on Spring Security internals

---

## 2. Where AuthenticationProvider Fits in the Flow

High-level authentication flow:

```
HTTP Request
   ↓
Authentication Filter
   ↓
AuthenticationManager
   ↓
AuthenticationProvider
   ↓
UserDetailsService / External System
   ↓
Authentication Result
```

Key point:

* **Filters do not authenticate users**
* **AuthenticationProviders do**

---

## 3. AuthenticationManager and ProviderManager

### AuthenticationManager

```java
public interface AuthenticationManager {
    Authentication authenticate(Authentication authentication);
}
```

* central entry point for authentication
* delegates authentication to providers

---

### ProviderManager (Default Implementation)

`ProviderManager` is the default `AuthenticationManager`.

Responsibilities:

* holds a list of AuthenticationProviders
* tries each provider in order
* stops when authentication succeeds
* throws exception if all providers fail

```
ProviderManager
   ├── DaoAuthenticationProvider
   ├── JwtAuthenticationProvider
   └── LdapAuthenticationProvider
```

---

## 4. AuthenticationProvider Interface

```java
public interface AuthenticationProvider {

    Authentication authenticate(Authentication authentication)
            throws AuthenticationException;

    boolean supports(Class<?> authentication);
}
```

### Methods Explained

* `authenticate()` → performs authentication logic
* `supports()` → tells whether provider can handle a specific token type

---

## 5. Authentication vs Authentication Token

Spring Security uses **Authentication tokens** to represent credentials.

Examples:

| Token                               | Used For          |
| ----------------------------------- | ----------------- |
| UsernamePasswordAuthenticationToken | Form / JDBC login |
| BearerTokenAuthenticationToken      | JWT               |
| AnonymousAuthenticationToken        | Anonymous users   |

AuthenticationProviders work on **specific token types**.

---

## 6. Built-in Authentication Providers

Spring Security ships with many providers.

---

### 6.1 DaoAuthenticationProvider (Most Common)

Used for:

* database-backed authentication
* JDBC
* JPA
* UserDetailsService

#### How It Works

1. receives `UsernamePasswordAuthenticationToken`
2. loads user using `UserDetailsService`
3. compares password using `PasswordEncoder`
4. returns authenticated token

```java
DaoAuthenticationProvider provider =
        new DaoAuthenticationProvider();

provider.setUserDetailsService(userDetailsService);
provider.setPasswordEncoder(passwordEncoder);
```

---

### 6.2 JdbcAuthenticationProvider (Legacy Style)

* uses predefined database schema
* less flexible
* mostly replaced by `DaoAuthenticationProvider`

---

### 6.3 LdapAuthenticationProvider

Used when:

* authentication is handled by LDAP / Active Directory

Common in enterprise environments.

---

### 6.4 JwtAuthenticationProvider (Token-Based)

Used for:

* stateless REST APIs
* microservices

Responsibilities:

* validate JWT signature
* check expiration
* extract roles / authorities

Usually paired with a custom filter.

---

### 6.5 OAuth2AuthenticationProvider

Used in:

* OAuth2 login
* social login
* identity federation

Authentication is delegated to an external provider.

---

## 7. Multiple AuthenticationProviders (Very Important)

Spring Security can use **multiple providers simultaneously**.

Example:

* form login for admin
* JWT for APIs

```java
ProviderManager manager =
        new ProviderManager(
            daoProvider,
            jwtProvider
        );
```

Spring will:

1. try DAO provider
2. if unsupported → try JWT provider

---

## 8. Custom AuthenticationProvider

Used when:

* authentication logic is non-standard
* API key authentication
* custom token format
* legacy system integration

---

### 8.1 Custom Provider Example

```java
@Component
public class ApiKeyAuthenticationProvider
        implements AuthenticationProvider {

    @Override
    public Authentication authenticate(
            Authentication authentication) {

        String apiKey = (String) authentication.getCredentials();

        if (!isValid(apiKey)) {
            throw new BadCredentialsException("Invalid API key");
        }

        return new UsernamePasswordAuthenticationToken(
                "api-user",
                null,
                List.of(new SimpleGrantedAuthority("ROLE_API"))
        );
    }

    @Override
    public boolean supports(Class<?> authType) {
        return ApiKeyAuthenticationToken.class.isAssignableFrom(authType);
    }
}
```

---

## 9. supports() Method (Interview Favorite)

`supports()` determines:

* whether a provider can handle a given authentication token

Example:

```java
supports(UsernamePasswordAuthenticationToken.class)
```

If it returns `false`, the provider is skipped.

This is how Spring decides **which provider to use**.

---

## 10. Authentication Failure Handling

If authentication fails:

* provider throws `AuthenticationException`
* flow stops
* filter returns:

  * 401 Unauthorized
  * or redirects to login page

Common exceptions:

* `BadCredentialsException`
* `DisabledException`
* `LockedException`

---

## 11. Real-World Backend Example

### Scenario: REST API + Admin Panel

| Area         | Authentication            |
| ------------ | ------------------------- |
| Admin UI     | Form login (DAO provider) |
| Public API   | JWT provider              |
| Internal API | API key provider          |

All coexist using multiple AuthenticationProviders.

---

## 12. AuthenticationProvider vs UserDetailsService

| AuthenticationProvider  | UserDetailsService         |
| ----------------------- | -------------------------- |
| Performs authentication | Loads user data            |
| Validates credentials   | Does not validate password |
| Part of security flow   | Helper component           |

`UserDetailsService` alone does **not authenticate users**.

---

## 13. Common Mistakes

* thinking filters authenticate users
* writing password comparison manually
* bypassing AuthenticationManager
* implementing logic in filters instead of providers
* ignoring supports() method

---

## 14. Interview Questions

### Q1: What is AuthenticationProvider?

A component that performs authentication logic in Spring Security.

---

### Q2: Can Spring Security use multiple providers?

Yes, via ProviderManager.

---

### Q3: What does supports() do?

Determines whether a provider can handle a given authentication token.

---

### Q4: Which provider is most commonly used?

DaoAuthenticationProvider.

---

### Q5: Where should custom authentication logic go?

Inside a custom AuthenticationProvider.

---

## Key Takeaways

* AuthenticationProvider performs actual authentication

* Filters only extract credentials

* AuthenticationManager delegates to providers

* ProviderManager can manage multiple providers

* DaoAuthenticationProvider is most common

* Custom providers enable advanced authentication flows

* Understanding providers is key to mastering Spring Security internals

---
