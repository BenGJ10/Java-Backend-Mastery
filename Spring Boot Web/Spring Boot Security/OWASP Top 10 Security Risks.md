# OWASP Top 10 Security Risks

## 1. Overview

The **OWASP Top 10** is a globally recognized list of the **most critical web application security risks**.
It serves as a **baseline security checklist** for backend developers, architects, and security engineers.

OWASP focuses on **real-world attack patterns**, not theoretical issues.

For Spring Boot / Java backend developers, understanding these risks is essential for:

* secure API design
* production readiness
* security interviews
* compliance and audits

---

## 2. OWASP Top 10 (Latest Model – Conceptual)

The OWASP Top 10 categories evolve, but the **core risks remain consistent**.

High-level list:

1. Broken Access Control
2. Cryptographic Failures
3. Injection
4. Insecure Design
5. Security Misconfiguration
6. Vulnerable and Outdated Components
7. Identification and Authentication Failures
8. Software and Data Integrity Failures
9. Security Logging and Monitoring Failures
10. Server-Side Request Forgery (SSRF)

---

## 3. A01 – Broken Access Control

### What It Is

Occurs when users can:

* access resources they should not
* perform actions beyond their permissions

This is the **most critical OWASP risk**.

---

### Examples

* User accessing `/admin/users`
* Modifying another user’s data by changing ID
* Missing role checks in service layer

---

### Backend Causes

* authorization only at controller level
* missing method-level security
* trusting frontend role checks

---

### Prevention

* enforce role-based access control
* use method-level security (`@PreAuthorize`)
* validate resource ownership
* deny-by-default strategy

---

## 4. A02 – Cryptographic Failures

### What It Is

Sensitive data is:

* improperly encrypted
* weakly hashed
* exposed in transit or storage

---

### Examples

* storing plain-text passwords
* using MD5 / SHA1 for passwords
* no HTTPS
* hardcoded secrets

---

### Prevention

* use BCrypt / Argon2 for passwords
* enforce HTTPS/TLS
* encrypt sensitive data at rest
* store secrets securely (vaults, env vars)

---

## 5. A03 – Injection

### What It Is

Untrusted input is interpreted as code.

Includes:

* SQL Injection
* JPQL Injection
* Command Injection

---

### Example

```sql
SELECT * FROM users WHERE username = 'admin' OR '1'='1';
```

---

### Prevention

* use prepared statements
* use ORM frameworks (JPA/Hibernate)
* validate and sanitize inputs
* avoid string concatenation in queries

---

## 6. A04 – Insecure Design

### What It Is

Security flaws introduced at **design level**, not coding level.

---

### Examples

* no rate limiting
* no account lockout
* missing abuse-case analysis
* trusting internal services blindly

---

### Prevention

* threat modeling
* design for abuse cases
* security requirements early
* defense-in-depth architecture

---

## 7. A05 – Security Misconfiguration

### What It Is

Application is deployed with **unsafe defaults**.

---

### Examples

* exposed actuator endpoints
* debug mode enabled in production
* default credentials
* open CORS configuration

---

### Prevention

* secure Spring Actuator
* disable unused features
* environment-specific configs
* principle of least privilege

---

## 8. A06 – Vulnerable and Outdated Components

### What It Is

Using libraries or frameworks with known vulnerabilities.

---

### Examples

* outdated Spring Boot version
* vulnerable Log4j dependency
* unpatched database drivers

---

### Prevention

* keep dependencies updated
* use dependency scanning tools
* remove unused libraries
* track CVEs regularly

---

## 9. A07 – Identification and Authentication Failures

### What It Is

Failures in authentication mechanisms.

---

### Examples

* weak password policies
* missing MFA
* session fixation
* predictable tokens

---

### Prevention

* strong password policies
* secure session management
* proper logout handling
* token expiration and rotation

---

## 10. A08 – Software and Data Integrity Failures

### What It Is

Untrusted software or data is executed.

---

### Examples

* unsigned updates
* deserialization vulnerabilities
* loading code from untrusted sources

---

### Prevention

* verify integrity of dependencies
* avoid unsafe deserialization
* use signed artifacts
* restrict dynamic code execution

---

## 11. A09 – Security Logging and Monitoring Failures

### What It Is

Attacks happen but go **undetected**.

---

### Examples

* no audit logs
* no alerting on failures
* logs without user context

---

### Prevention

* centralized logging
* audit authentication events
* monitor abnormal behavior
* integrate alerts (SIEM)

---

## 12. A10 – Server-Side Request Forgery (SSRF)

### What It Is

Backend server is tricked into making requests to unintended destinations.

---

### Example

```
GET /fetch?url=http://localhost:8080/admin
```

---

### Prevention

* validate and whitelist URLs
* block internal IP ranges
* disable unnecessary outbound requests
* network-level restrictions

---

## 13. OWASP Top 10 in Spring Boot Context

| OWASP Risk             | Spring Boot Mitigation            |
| ---------------------- | --------------------------------- |
| Broken Access Control  | Spring Security + method security |
| Injection              | JPA + parameter binding           |
| Cryptographic Failures | BCrypt, TLS                       |
| Misconfiguration       | Secure Actuator, profiles         |
| Auth Failures          | Spring Security                   |
| Logging Failures       | Centralized logging               |

---

## 14. Common Interview Questions

### Q1: What is the OWASP Top 10?

A list of the most critical web application security risks.

---

### Q2: Which OWASP risk is most common?

Broken Access Control.

---

### Q3: Does using Spring Security automatically fix OWASP issues?

No. Correct configuration and design are required.

---

### Q4: Is SQL Injection still relevant with JPA?

Yes, if queries are built incorrectly.

---

### Q5: Why is OWASP important for backend developers?

It defines real-world attack vectors and defense strategies.

---

## Key Takeaways

* OWASP Top 10 is a **security baseline**

* Most attacks exploit basic design and configuration flaws

* Security must be considered at:

  * design
  * development
  * deployment

* Spring Boot provides tools, but developers must use them correctly

* Knowing OWASP Top 10 is essential for **production readiness and interviews**

---
