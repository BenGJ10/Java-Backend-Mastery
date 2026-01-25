# Spring Boot Security – Overview

## 1. Overview

**Spring Boot Security** is built on top of **Spring Security**, a powerful and highly customizable framework for securing Java applications.

It provides comprehensive support for:

* authentication
* authorization
* protection against common security attacks
* integration with REST APIs, microservices, and cloud platforms

Spring Security is **industry-standard** and widely used in **enterprise and production-grade backend systems**.

---

## 2. What Problems Does Spring Security Solve?

Spring Security protects applications from:

* unauthorized access
* broken authentication
* insecure endpoints
* session hijacking
* CSRF attacks
* privilege escalation

It ensures that **only authenticated and authorized users** can access protected resources.

---

## 3. Core Concepts in Spring Security

Spring Security revolves around a few fundamental concepts.

---

### 3.1 Authentication

**Authentication** answers:

> *Who are you?*

It verifies the identity of a user.

Common authentication mechanisms:

* username & password
* JWT token
* OAuth2 (Google, GitHub, etc.)
* API keys

Example:

```text
User provides credentials → system verifies identity
```

---

### 3.2 Authorization

**Authorization** answers:

> *What are you allowed to do?*

It determines whether an authenticated user has permission to access a resource.

Examples:

* ROLE_ADMIN can delete users
* ROLE_USER can only read data

---

### 3.3 Principal

The **Principal** represents the currently authenticated user.

In Spring Security, this is usually represented by a `UserDetails` object.

---

### 3.4 Granted Authority

Represents permissions or roles assigned to a user.

Examples:

* ROLE_USER
* ROLE_ADMIN

---

## 4. Spring Security Architecture (High-Level)

Spring Security is based on a **filter chain**.

```
HTTP Request
   ↓
Spring Security Filter Chain
   ↓
Authentication Filter
   ↓
Authorization Filter
   ↓
Controller
```

Every incoming request passes through multiple security filters **before reaching the controller**.

---

## 5. Default Spring Boot Security Behavior

When Spring Security is added to a Spring Boot application:

* all endpoints are secured by default
* a default login form is generated
* a default user is created
* CSRF protection is enabled

Default credentials are printed in the application logs.

This behavior is **not suitable for production**, but useful for quick testing.

---

## 6. Authentication Mechanisms in Spring Boot

### 6.1 Form-Based Authentication

* default login page
* session-based authentication
* commonly used in web applications

---

### 6.2 Basic Authentication

* credentials sent in HTTP headers
* simple but not recommended without HTTPS
* commonly used for internal APIs

---

### 6.3 Token-Based Authentication (JWT)

* stateless
* scalable
* commonly used in REST APIs and microservices

Flow:

```
Login → JWT issued → JWT sent with each request
```

---

### 6.4 OAuth2 / OpenID Connect

* third-party login (Google, GitHub, etc.)
* widely used in modern applications
* suitable for public-facing systems

---

## 7. Authorization Models

Spring Security supports:

### Role-Based Access Control (RBAC)

```text
USER → read access
ADMIN → full access
```

---

### Method-Level Security

```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) {
}
```

This secures business logic, not just endpoints.

---

## 8. Securing REST APIs (High-Level)

Typical REST API security setup includes:

* disabling form login
* disabling CSRF (for stateless APIs)
* enabling JWT-based authentication
* securing endpoints by role

Example:

```text
POST /auth/login → public
GET /users → authenticated
DELETE /users/{id} → ADMIN only
```

---

## 9. CSRF Protection

**CSRF (Cross-Site Request Forgery)** protection is enabled by default.

* required for session-based authentication
* usually disabled for stateless REST APIs

Understanding when to enable or disable CSRF is important.

---

## 10. Password Handling

Spring Security enforces **secure password storage**.

* passwords are never stored in plain text
* uses hashing algorithms (e.g., BCrypt)

Example:

```java
BCryptPasswordEncoder
```

This protects against database leaks.

---

## 11. Security Configuration Approaches

### Legacy (Deprecated)

* `WebSecurityConfigurerAdapter` (deprecated)

---

### Modern Approach (Recommended)

* Security configuration using `SecurityFilterChain`
* Lambda-based DSL
* More explicit and flexible

This is the standard in modern Spring Boot versions.

---

## 12. Spring Security in Real-World Applications

Spring Security is commonly used to secure:

* REST APIs
* microservices
* admin panels
* internal dashboards
* cloud-native applications

Typical integrations:

* Spring Boot + JWT
* Spring Boot + OAuth2
* Spring Boot + AWS Cognito / Auth0

---

## 13. Common Security Mistakes

* exposing all endpoints publicly
* storing passwords in plain text
* using weak hashing algorithms
* missing role-based authorization
* disabling security entirely

Security should be **designed, not added later**.

---

## 14. Spring Boot Security vs Custom Security

| Aspect            | Spring Security | Custom Security |
| ----------------- | --------------- | --------------- |
| Maturity          | Very high       | Low             |
| Attack protection | Built-in        | Manual          |
| Flexibility       | High            | Depends         |
| Maintenance       | Easier          | Hard            |
| Industry adoption | Very high       | Low             |

Spring Security should almost always be preferred.

---

## 15. Interview Questions

### Q1: What is Spring Security?

A framework for authentication, authorization, and protection against common security threats.

---

### Q2: Difference between authentication and authorization?

* Authentication → who you are
* Authorization → what you can access

---

### Q3: What is the Spring Security filter chain?

A sequence of filters that intercept HTTP requests for security checks.

---

### Q4: Is Spring Security stateful or stateless?

It supports both, depending on configuration.

---

### Q5: Why is password hashing important?

To prevent credential leaks even if the database is compromised.

---

## Key Takeaways

* Spring Boot security is powered by Spring Security

* Handles authentication and authorization

* Based on filter chain architecture

* Supports multiple authentication mechanisms

* Essential for production-grade backend systems

* Must be configured properly for REST APIs

* Widely used and industry-standard

---

