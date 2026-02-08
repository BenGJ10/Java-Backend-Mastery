# Password Security and Encoders in Spring Security

## 1. Overview

Password security is one of the **most critical responsibilities** in backend development.
Improper password storage is a leading cause of **major security breaches**.

Spring Security provides a robust framework for:

* hashing passwords
* verifying credentials securely
* supporting multiple encoding algorithms
* upgrading encryption strategies over time

**Golden Rule:**

> Passwords must NEVER be stored in plain text.

---

## 2. Why Password Security Matters

If attackers gain database access and passwords are not properly protected:

* all user accounts are compromised
* credential stuffing attacks become possible
* regulatory violations may occur
* brand trust is damaged

Password security is a **first line of defense**.

---

## 3. Encryption vs Hashing (Critical Distinction)

Many developers confuse these terms.

| Feature                | Encryption | Hashing |
| ---------------------- | ---------- | ------- |
| Reversible             | Yes        | No      |
| Key required           | Yes        | No      |
| Suitable for passwords | ❌ No       | ✅ Yes   |

### Why Not Encryption?

If encryption keys leak → passwords can be decrypted.

### Why Hashing?

* one-way transformation
* original password cannot be recovered

---

## 4. What Is a Password Encoder?

A **PasswordEncoder** is responsible for:

1. hashing raw passwords during registration
2. verifying passwords during login

Spring Security interface:

```java
public interface PasswordEncoder {

    String encode(CharSequence rawPassword);

    boolean matches(
        CharSequence rawPassword,
        String encodedPassword
    );
}
```

---

## 5. How Password Verification Works

```
User enters password
       ↓
Raw password is hashed
       ↓
Compared with stored hash
       ↓
Match → authenticated
```

Passwords are **never decrypted**.

---

## 6. BCryptPasswordEncoder (Most Recommended)

### Why BCrypt?

BCrypt is designed specifically for password hashing.

Key features:

* built-in salting
* adaptive strength
* slow by design
* resistant to brute-force attacks

---

### Creating Encoder Bean

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

---

### Encoding Password

```java
String hash = passwordEncoder.encode("myPassword");
```

Example hash:

```
$2a$10$7sdf8sdf87sdf...
```

Even the same password generates **different hashes** due to salting.

---

## 7. Password Matching

```java
passwordEncoder.matches(rawPassword, storedHash);
```

Internally:

* extracts salt
* hashes raw password
* compares safely

No manual comparison is required.

---

## 8. Strength Factor (Work Factor)

BCrypt allows configurable strength:

```java
new BCryptPasswordEncoder(12);
```

Default is typically **10**.

### Higher Strength Means:

* slower hashing
* stronger protection
* more CPU usage

Choose based on system performance.

Typical production range:

```
10 – 14
```

---

## 9. Salting Explained

### What Is a Salt?

A random value added to the password before hashing.

Without salt:

```
password123 → same hash everywhere
```

With salt:

```
password123 → different hash per user
```

This prevents:

* rainbow table attacks
* hash lookup attacks

BCrypt handles salting automatically.

---

## 10. Other Password Encoders

Spring Security supports multiple encoders.

### Argon2PasswordEncoder

* modern algorithm
* memory-hard
* extremely resistant to GPU attacks

Considered one of the strongest options.

---

### Pbkdf2PasswordEncoder

* widely trusted
* NIST recommended
* slower than SHA

---

### Deprecated / Unsafe Algorithms

Never use:

* MD5
* SHA-1
* plain SHA-256 (without proper salting/iterations)

These are **too fast**, making brute-force attacks easier.

---

## 11. DelegatingPasswordEncoder (Highly Recommended)

Spring Security recommends:

```java
PasswordEncoderFactories
        .createDelegatingPasswordEncoder();
```

### Why Use It?

Supports multiple algorithms simultaneously.

Example stored password:

```
{bcrypt}$2a$10$...
```

Prefix identifies the encoder.

---

### Major Advantage

You can upgrade algorithms without breaking existing users.

Example migration:

```
Old users → bcrypt  
New users → argon2
```

System handles both seamlessly.

---

## 12. Password Encoding in Authentication Flow

```
User Login
   ↓
AuthenticationProvider
   ↓
UserDetailsService → fetch hashed password
   ↓
PasswordEncoder.matches()
   ↓
Authentication success/failure
```

PasswordEncoder is used **inside AuthenticationProvider**.

You should NEVER compare passwords manually.

---

## 13. Real-World Backend Best Practices

### Always Hash During Registration

```java
user.setPassword(
    passwordEncoder.encode(dto.getPassword())
);
```

Never store raw password.

---

### Never Return Password in APIs

Even hashed passwords should not be exposed.

Use DTOs carefully.

---

### Avoid Logging Passwords

Disable request-body logging for auth endpoints.

---

## 14. Interview Questions

### Q1: Why is hashing preferred over encryption?

Because hashing is one-way and cannot be reversed.

---

### Q2: Why is BCrypt recommended?

It is salted, adaptive, and slow — making brute-force attacks difficult.

---

### Q3: Can two identical passwords have different hashes?

Yes, due to salting.

---

### Q4: Where is PasswordEncoder used?

Inside AuthenticationProvider during credential verification.

---

### Q5: What is DelegatingPasswordEncoder?

A flexible encoder that supports multiple hashing algorithms.

---

## 18. Key Takeaways

* Never store plain-text passwords

* Always hash using strong algorithms

* BCrypt is the industry standard

* Argon2 is a strong modern alternative

* PasswordEncoder handles hashing and verification

* DelegatingPasswordEncoder supports algorithm upgrades

* Password security is foundational to backend safety

---