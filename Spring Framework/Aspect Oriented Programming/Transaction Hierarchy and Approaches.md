# Transaction Hierarchy, Approaches, and Propagation Types

## 1. Overview

In Spring Boot, transaction handling is not just about using `@Transactional`.

To use transactions correctly, you should understand three things together:

* transaction hierarchy (who controls transaction flow)
* declarative vs programmatic transaction management
* propagation behavior when transactional methods call each other

This file connects all three concepts in one place.

---

## 2. Transaction Hierarchy in Spring

Transaction hierarchy means how transaction control is layered in the framework.

Think of it from top to bottom.

### 2.1 Business Layer (Service Methods)

Your service methods define business operations.

Example:

* place order
* transfer money
* create invoice

These are the methods where transaction boundaries are usually declared.

---

### 2.2 Transaction Metadata Layer

Spring reads metadata from annotations like `@Transactional`:

* propagation
* isolation
* read-only
* timeout
* rollback rules

This metadata becomes a transaction attribute model.

---

### 2.3 AOP Interceptor Layer

Spring AOP proxy intercepts method calls and delegates transaction handling to `TransactionInterceptor`.

This is where Spring decides:

* should a new transaction start?
* should an existing transaction be reused?
* should the method run without a transaction?

---

### 2.4 Transaction Manager Layer

`PlatformTransactionManager` performs real transaction operations.

Common implementations:

* `DataSourceTransactionManager` for JDBC
* `JpaTransactionManager` for JPA/Hibernate

This layer handles begin, commit, rollback, suspend, and resume.

---

### 2.5 Resource Layer

Actual resources are managed here:

* database connections
* JPA sessions/entity manager
* savepoints (for nested transactions where supported)

---

## 3. Visual Transaction Hierarchy

```text
Service Method (business operation)
        ↓
@Transactional metadata
        ↓
AOP Proxy + TransactionInterceptor
        ↓
PlatformTransactionManager
        ↓
Database / Persistence Resources
```

This is the core hierarchy behind Spring Boot transaction behavior.

---

## 4. Declarative Transaction Management

Declarative approach means you declare rules, and Spring manages transaction code automatically.

### Example

```java
@Service
public class AccountService {

    @Transactional
    public void transfer(Long from, Long to, BigDecimal amount) {
        // debit and credit logic
    }
}
```

### Why it is preferred

* clean business code
* less boilerplate
* centralized rules
* strong AOP integration

This is the default and recommended style in Spring Boot.

---

## 5. Programmatic Transaction Management

Programmatic approach means you control transaction boundaries manually in code.

Spring supports this using `TransactionTemplate` or direct transaction manager APIs.

### Example with `TransactionTemplate`

```java
transactionTemplate.execute(status -> {
    // business logic
    return null;
});
```

### When it is useful

* dynamic transaction rules at runtime
* partial rollback handling with custom logic
* advanced flows where annotation-based control is insufficient

### Tradeoff

More control, but more complexity and less readability.

---

## 6. Declarative vs Programmatic (Comparison)

| Aspect | Declarative | Programmatic |
| ------ | ----------- | ------------ |
| Setup | Annotation-based (`@Transactional`) | Manual API/Template |
| Boilerplate | Low | Medium/High |
| Readability | High | Lower for large flows |
| Flexibility | High for common use cases | Highest for custom flows |
| Recommended default | Yes | Only when needed |

---

## 7. Propagation in `@Transactional`

Propagation defines transaction behavior when one transactional method calls another.

It answers:

> Should this method join an existing transaction, create a new one, or run without one?

---

## 8. All Propagation Types

### 8.1 `REQUIRED` (default)

* join current transaction if it exists
* otherwise create a new transaction

Most commonly used mode.

---

### 8.2 `REQUIRES_NEW`

* always create a new transaction
* suspend current transaction if one exists

Useful when inner work must commit independently (for example, audit logging).

---

### 8.3 `SUPPORTS`

* use existing transaction if present
* otherwise execute non-transactionally

Used for read operations that can run in either mode.

---

### 8.4 `MANDATORY`

* must run inside an existing transaction
* throws exception if no transaction exists

Used to enforce strict calling discipline.

---

### 8.5 `NOT_SUPPORTED`

* run non-transactionally
* suspend existing transaction if present

Useful for operations that should avoid transaction overhead.

---

### 8.6 `NEVER`

* must run without a transaction
* throws exception if transaction exists

Used rarely, mostly for strict constraints.

---

### 8.7 `NESTED`

* run inside a nested transaction (savepoint-based)
* if inner part fails, rollback to savepoint
* outer transaction can still continue

Support depends on transaction manager and database capabilities.

---

## 9. Propagation Flow Examples

### `REQUIRED` calling `REQUIRED`

```text
Outer method starts Tx-1
Inner method joins Tx-1
Both share same commit/rollback outcome
```

### `REQUIRED` calling `REQUIRES_NEW`

```text
Outer method starts Tx-1
Inner suspends Tx-1 and starts Tx-2
Inner commits/rolls back independently
Outer resumes Tx-1
```

### `REQUIRED` calling `NOT_SUPPORTED`

```text
Outer method starts Tx-1
Inner suspends Tx-1 and runs without transaction
Outer resumes Tx-1
```

---

## 10. How Propagation Fits the Hierarchy

Propagation rules are defined in metadata (`@Transactional`), evaluated in the interceptor, and enforced by the transaction manager.

So propagation is not only an annotation feature; it is part of the full transaction hierarchy pipeline.

---

## 11. Best Practices

* keep transaction boundaries at service layer

* start with `REQUIRED` unless there is a clear reason otherwise

* use `REQUIRES_NEW` only for truly independent work

* avoid overusing `NESTED` unless savepoint behavior is understood

* prefer declarative approach for most business cases

* switch to programmatic approach only for special control requirements

---

## 12. Common Mistakes

* using `REQUIRES_NEW` everywhere and breaking business consistency

* not understanding suspend/resume behavior

* assuming `NESTED` works in all environments

* mixing declarative and programmatic styles carelessly in the same flow

* keeping very large transaction scopes

---

## 13. Interview Questions

### Q1. What is transaction hierarchy in Spring?

The layered flow from service method to transaction metadata, AOP interceptor, transaction manager, and actual resource handling.

---

### Q2. Which approach is preferred: declarative or programmatic?

Declarative is preferred for most use cases because it keeps business code clean.

---

### Q3. What is the difference between `REQUIRED` and `REQUIRES_NEW`?

`REQUIRED` joins existing transaction; `REQUIRES_NEW` always starts a new one.

---

### Q4. What does `MANDATORY` do?

Requires an active transaction and throws exception if none exists.

---

### Q5. Why is propagation important?

It controls transaction behavior across nested service calls and directly affects consistency, rollback scope, and performance.

---

## Key Takeaways

* Spring transactions work through a clear hierarchy of layers

* Declarative management is the default and most maintainable approach

* Programmatic management offers fine control for advanced cases

* Propagation rules define how transactions behave across method calls

* Correct propagation selection is critical for data consistency

---