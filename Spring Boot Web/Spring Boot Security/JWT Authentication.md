# Overview of JWT Authentication

## 1. Overview

**JWT (JSON Web Token)** authentication is a **stateless, token-based authentication mechanism** widely used in modern REST APIs and microservices.

Instead of maintaining server-side sessions, JWT authentication works by:

* issuing a signed token after login
* sending that token with every request
* validating the token on the server

JWT is commonly used in:

* REST APIs
* microservices architectures
* mobile applications
* SPA (React/Angular) frontends

---

## 2. Why JWT Is Used

Traditional authentication uses **server-side sessions**:

```
User logs in
Server creates session
Session stored in memory/database
Client sends session ID (cookie)
```

Problems:

* not easily scalable
* requires shared session storage in distributed systems
* stateful architecture

JWT solves this by being **stateless**:

```
Server issues token
Client stores token
Client sends token with each request
Server validates token
```

No session storage required.

---

## 3. What Is a JWT?

A JWT is a compact, URL-safe token consisting of three parts:

```
Header.Payload.Signature
```

Example:

```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
.
eyJzdWIiOiJqb2huIiwiZXhwIjoxNzAwMDAwMDAwfQ
.
dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk
```

---

## 4. Structure of JWT

### 4.1 Header

Contains metadata:

```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

* algorithm used for signing
* token type

---

### 4.2 Payload (Claims)

Contains data about the user.

```json
{
  "sub": "john",
  "role": "ADMIN",
  "iat": 1700000000,
  "exp": 1700003600
}
```

Common claims:

| Claim | Meaning            |
| ----- | ------------------ |
| sub   | Subject (username) |
| exp   | Expiration time    |
| iat   | Issued at          |
| iss   | Issuer             |
| aud   | Audience           |

Payload is **not encrypted**, only encoded.

---

### 4.3 Signature

Created by:

```
HMACSHA256(
    base64UrlEncode(header) + "." +
    base64UrlEncode(payload),
    secretKey
)
```

Ensures:

* integrity
* authenticity
* token cannot be modified

---

## 5. JWT Authentication Workflow

### Step 1 — User Login

```
POST /login
username + password
```

* credentials validated
* JWT generated
* token returned to client

---

### Step 2 — Client Stores Token

Stored in:

* localStorage
* sessionStorage
* secure HTTP-only cookie

---

### Step 3 — Client Sends Token

```
Authorization: Bearer <token>
```

---

### Step 4 — Server Validates Token

JWT filter:

* extracts token
* validates signature
* checks expiration
* sets Authentication in SecurityContext

---

### Step 5 — Request Proceeds

If token valid → user is authenticated
If invalid → 401 Unauthorized

---

## 6. JWT vs Session Authentication

| Feature            | Session              | JWT          |
| ------------------ | -------------------- | ------------ |
| Stateful           | Yes                  | No           |
| Server storage     | Required             | Not required |
| Scalability        | Harder               | Easier       |
| Horizontal scaling | Needs shared session | Easy         |
| Suitable for       | Traditional apps     | REST APIs    |

---

## 7. Advantages of JWT

* Stateless
* Scalable
* Suitable for microservices
* No server-side session storage
* Cross-domain friendly
* Self-contained (contains user info)

---

## 8. Disadvantages of JWT

* Token revocation is difficult
* Larger request size (token sent each time)
* Payload not encrypted by default
* Must handle expiration carefully

JWT is powerful but must be used correctly.

---

## 9. JWT in Spring Security Architecture

JWT authentication uses:

* Custom filter (extends OncePerRequestFilter)
* AuthenticationManager
* AuthenticationProvider (optional)
* SecurityContextHolder

Flow:

```
Request
 ↓
JWT Filter
 ↓
Validate Token
 ↓
Set Authentication
 ↓
Authorization
 ↓
Controller
```

---

## 10. Stateless Configuration in Spring Boot

Typical settings:

```java
http
    .csrf(csrf -> csrf.disable())
    .sessionManagement(session ->
        session.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS
        )
    );
```

This ensures:

* no session is created
* authentication is token-based

---

## 11. Access Token vs Refresh Token

### Access Token

* short-lived (15–60 minutes)
* used in API calls

### Refresh Token

* long-lived
* used to generate new access token
* improves security

Modern systems use both.

---

## 12. Security Considerations

### Always:

* use HTTPS
* set short expiration times
* sign tokens securely
* validate signature and expiration

### Never:

* store sensitive data in payload
* trust unverified tokens
* use weak secret keys

---

## 13. Common JWT Attacks

* token tampering
* expired token reuse
* signature algorithm confusion
* token theft via XSS

Proper configuration mitigates these risks.

---

## 14. Typical Production Architecture

```
Client (React)
   ↓
Spring Boot API (JWT validation)
   ↓
Microservices (optional JWT validation)
```

In microservices:

* JWT validated at gateway
* forwarded to downstream services

---

## 15. Common Interview Questions

### Q1: What is JWT?

A compact, signed token used for stateless authentication.

---

### Q2: Is JWT encrypted?

No. It is signed, not encrypted.

---

### Q3: Why is JWT scalable?

Because no session storage is required.

---

### Q4: What happens if JWT is expired?

Authentication fails → 401.

---

### Q5: What is the purpose of refresh tokens?

To issue new access tokens without re-login.

---

## Key Takeaways

* JWT is a stateless authentication mechanism

* Token contains header, payload, signature

* Signature ensures integrity

* Used heavily in REST APIs

* Eliminates server-side sessions

* Must validate signature and expiration

* Works through custom security filters

* Scalable and microservice-friendly

---
