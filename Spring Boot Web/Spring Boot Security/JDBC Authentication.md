# JDBC Authentication in Spring Boot

## 1. Overview

**JDBC authentication** and **BCrypt password encryption** are foundational concepts in building **secure backend authentication systems**.

In real-world Spring Boot applications:

* user credentials are stored in a database
* passwords are **never stored in plain text**
* authentication is performed using **hashed passwords**
* Spring Security integrates with JDBC to handle this securely

This topic commonly appears in **backend interviews** and **security design discussions**.

---

## 2. Why Password Encryption Is Mandatory

Storing passwords in plain text is a **critical security flaw**.

Risks include:

* database leaks exposing all user credentials
* credential reuse attacks
* regulatory and compliance violations

Best practice:

> Always store **hashed passwords**, never raw passwords.

---

## 3. JDBC-Based Authentication (Concept)

**JDBC authentication** means:

* user credentials are stored in a relational database
* authentication logic queries the database using JDBC
* Spring Security validates credentials against stored hashes

This is different from:

* in-memory authentication
* LDAP authentication
* OAuth2 / external identity providers

---

## 4. Typical Authentication Flow

```
User Login Request
   ↓
Spring Security Authentication Filter
   ↓
UserDetailsService (JDBC)
   ↓
Database Query (users table)
   ↓
Password Hash Comparison (BCrypt)
   ↓
Authentication Success / Failure
```

---

## 5. Database Schema for Authentication

### Users Table

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(255),
    enabled BOOLEAN
);
```

### Authorities / Roles Table

```sql
CREATE TABLE authorities (
    username VARCHAR(50),
    authority VARCHAR(50)
);
```

Example role values:

```
ROLE_USER
ROLE_ADMIN
```

---

## 6. What Is BCrypt?

**BCrypt** is a **password hashing algorithm** designed specifically for secure password storage.

Key properties:

* one-way hashing
* salted by default
* adaptive (configurable strength)
* resistant to brute-force attacks

Spring Security strongly recommends BCrypt.

---

## 7. BCrypt vs Other Algorithms

| Algorithm | Secure | Salted | Recommended |
| --------- | ------ | ------ | ----------- |
| MD5       | No     | No     | ❌           |
| SHA-1     | Weak   | No     | ❌           |
| SHA-256   | Better | Manual | ⚠️          |
| BCrypt    | Yes    | Yes    | ✅           |
| Argon2    | Yes    | Yes    | ✅           |

Never use MD5 or SHA for passwords.

---

## 8. BCrypt Password Encoding

### Password Encoder Bean

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

Spring Security uses this encoder automatically.

---

### Hashing a Password

```java
String hashed = passwordEncoder.encode("password123");
```

Example output:

```
$2a$10$R9kFZz6XH2Q...
```

Each hash is **unique**, even for the same password.

---

## 9. Password Verification with BCrypt

```java
passwordEncoder.matches(rawPassword, storedHash);
```

* raw password is hashed internally
* hashes are compared securely
* original password cannot be recovered

---

## 10. JDBC Authentication with Spring Security

Spring Security provides **JDBC authentication support** out of the box.

### Basic Configuration

```java
@Bean
SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        )
        .formLogin()
        .and()
        .httpBasic();

    return http.build();
}
```

---

### Configure JDBC Authentication

```java
@Bean
public UserDetailsService userDetailsService(DataSource dataSource) {

    JdbcUserDetailsManager manager =
            new JdbcUserDetailsManager(dataSource);

    return manager;
}
```

Spring Security will automatically query:

* users table
* authorities table

---

## 11. Custom Queries for JDBC Authentication

Real-world databases often have custom schemas.

```java
manager.setUsersByUsernameQuery(
    "SELECT username, password, enabled FROM users WHERE username = ?"
);

manager.setAuthoritiesByUsernameQuery(
    "SELECT username, authority FROM authorities WHERE username = ?"
);
```

This allows full control over schema design.

---

## 12. Custom UserDetailsService (Most Common)

In modern applications, developers usually implement their own `UserDetailsService`.

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
        );
    }
}
```

This approach is flexible and production-ready.

---

## 13. Authentication Using BCrypt + Custom UserDetailsService

Full flow:

1. user submits login credentials
2. Spring Security calls `loadUserByUsername()`
3. hashed password fetched from DB
4. BCrypt compares hashes
5. authentication result returned

No manual password comparison is needed.

---

## 14. Why BCrypt Is Better Than SHA

SHA algorithms are **fast**, which is bad for password security.

BCrypt is:

* intentionally slow
* configurable via strength factor

Example:

```java
new BCryptPasswordEncoder(12);
```

Higher strength = more secure = slower hashing.

---

## 15. Other Password Encoders in Spring Security

Spring Security supports:

* BCryptPasswordEncoder
* Argon2PasswordEncoder
* Pbkdf2PasswordEncoder

Best practice:

```java
PasswordEncoderFactories.createDelegatingPasswordEncoder();
```

Allows algorithm upgrades without breaking existing users.

---

## 16. Common Security Mistakes

* storing plain-text passwords
* using MD5 or SHA for passwords
* manually comparing password strings
* exposing password hashes in APIs
* logging passwords

These are serious vulnerabilities.

---

## 17. Interview Questions

### Q1: Why should passwords be hashed and not encrypted?

Hashing is one-way; encryption is reversible.

---

### Q2: Why BCrypt is preferred?

Because it is salted, slow, and resistant to brute-force attacks.

---

### Q3: Can two identical passwords have different BCrypt hashes?

Yes.

---

### Q4: Where does password comparison happen?

Inside Spring Security using `PasswordEncoder`.

---

### Q5: Is JDBC authentication still used in modern apps?

Yes — widely used for internal and enterprise systems.

---

## Key Takeaways

* JDBC authentication uses database-backed credentials

* Passwords must never be stored in plain text

* BCrypt is the recommended hashing algorithm

* Spring Security integrates BCrypt seamlessly

* Custom `UserDetailsService` is most common in real projects

* Proper authentication design is critical for backend security

---

