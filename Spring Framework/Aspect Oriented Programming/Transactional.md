# How `@Transactional` Works in Spring

## 1. What Is `@Transactional`?

`@Transactional` is a Spring annotation used to define a **transaction boundary** around a method (or class).

It tells Spring:

* start a transaction before method execution
* commit if method completes successfully
* roll back if a failure occurs (based on rollback rules)

In simple words:

> `@Transactional` helps keep database operations consistent and atomic.

---

## 2. Why Transactions Matter

A transaction is needed when a business operation includes multiple data changes that must succeed or fail together.

Example:

* debit from Account A
* credit to Account B

If debit succeeds but credit fails, data becomes inconsistent.

Transactions prevent this by ensuring **all-or-nothing** behavior.

---

## 3. What Problem `@Transactional` Solves

Without Spring transactions, developers manually manage:

* connection handling
* begin transaction
* commit
* rollback
* exception-to-rollback mapping

This is repetitive and error-prone.

`@Transactional` moves this infrastructure concern into Spring so business code stays focused on domain logic.

---

## 4. The AOP Connection

`@Transactional` works using **Spring AOP**.

At runtime, Spring wraps the target bean with a **proxy**.

When a `@Transactional` method is called:

1. call enters proxy
2. transaction interceptor checks transaction metadata
3. transaction starts
4. target method executes
5. interceptor decides commit or rollback

So transaction management is applied as a **cross-cutting concern**, not written inside every service method.

---

## 5. High-Level Flow

```text
Client → Transaction Proxy → Begin Tx → Business Method
									↓
							 Success? Commit : Rollback
```

This is classic AOP behavior:

* pointcut-like matching on `@Transactional`
* advice/interceptor around method execution

---

## 6. Under the Hood (Core Components)

### `TransactionInterceptor`

Main AOP interceptor that surrounds transactional method execution.

### `PlatformTransactionManager`

Strategy interface used by Spring to manage actual transaction operations.

Common implementations:

* `DataSourceTransactionManager` (JDBC)
* `JpaTransactionManager` (JPA/Hibernate)
* `HibernateTransactionManager`

### `TransactionAttribute`

Metadata extracted from `@Transactional` (propagation, isolation, rollback rules, etc.).

---

## 7. Method-Level vs Class-Level Usage

### Method-level

```java
@Transactional
public void transferMoney(...) {
	// business logic
}
```

Applies only to that method.

### Class-level

```java
@Transactional
public class AccountService {
}
```

Applies to all public methods in the class unless overridden.

---

## 8. Commit and Rollback Rules

By default, Spring rolls back on:

* `RuntimeException`
* `Error`

By default, Spring does **not** roll back on checked exceptions unless configured.

Example:

```java
@Transactional(rollbackFor = Exception.class)
public void process() throws Exception {
}
```

Now checked exceptions also trigger rollback.

---

## 9. Propagation (Transaction Behavior Across Method Calls)

Propagation defines what should happen if a transactional method calls another transactional method.

Common modes:

### `REQUIRED` (default)

Join existing transaction, or create a new one if none exists.

### `REQUIRES_NEW`

Always start a new transaction, suspending any existing transaction.

### `SUPPORTS`

Use current transaction if present, otherwise run non-transactionally.

### `MANDATORY`

Must run inside an existing transaction, otherwise exception.

### `NOT_SUPPORTED`

Run without transaction, suspending current one.

### `NEVER`

Must run without transaction; fail if one exists.

### `NESTED`

Run inside nested transaction (savepoint behavior, manager dependent).

---

## 10. Isolation (Read/Write Concurrency Behavior)

Isolation controls visibility of uncommitted data between concurrent transactions.

Typical levels:

* `READ_UNCOMMITTED`
* `READ_COMMITTED`
* `REPEATABLE_READ`
* `SERIALIZABLE`

Higher isolation gives stronger consistency but can reduce throughput.

---

## 11. Read-Only Transactions

```java
@Transactional(readOnly = true)
public List<Order> getOrders() {
}
```

`readOnly = true` is a hint for optimization in some providers.

It does not mean the database physically blocks all writes in every setup, but it communicates intent and can improve performance.

---

## 12. Timeout and Rollback Control

```java
@Transactional(timeout = 5)
public void longRunningProcess() {
}
```

```java
@Transactional(noRollbackFor = IllegalArgumentException.class)
public void process() {
}
```

These options let you tune transaction behavior for specific use cases.

---

## 13. Why `@Transactional` Is a Perfect AOP Example

`@Transactional` demonstrates AOP principles clearly:

* concern is cross-cutting (transactions in many services)
* logic is centralized (interceptor + transaction manager)
* business methods remain clean
* behavior is applied declaratively via annotation

So rather than writing transaction boilerplate in each method, AOP injects it automatically.

---

## 14. Most Important Limitation: Self Invocation

Because Spring uses proxies, this call pattern can bypass transaction interception:

```java
public void outer() {
	innerTransactional(); // same class direct call
}

@Transactional
public void innerTransactional() {
}
```

`innerTransactional()` may not run inside a transaction when called internally like this, because the call does not pass through the proxy.

---

## 15. Best Practices

* apply `@Transactional` at service layer

* keep transaction scope as small as practical

* use `readOnly = true` for read use cases

* choose propagation deliberately, do not guess

* define rollback rules for checked exceptions when needed

* avoid large transactional methods with external I/O calls

---

## 16. Common Mistakes

* placing `@Transactional` on private methods and expecting proxy interception

* assuming checked exceptions always trigger rollback

* using one huge transaction for many unrelated operations

* ignoring self-invocation behavior

* mixing business logic and transaction plumbing manually

---

## 17. Interview Questions

### Q1. How does `@Transactional` work internally?

Spring AOP proxy intercepts method call, starts transaction using `PlatformTransactionManager`, then commits or rolls back based on outcome.

---

### Q2. Is `@Transactional` compile-time or runtime?

In standard Spring usage, it is runtime proxy-based.

---

### Q3. Does Spring roll back for checked exceptions by default?

No. Default rollback is for unchecked exceptions (`RuntimeException`) and `Error`.

---

### Q4. Why can self-invocation break transaction behavior?

Because internal calls bypass the proxy and therefore bypass transaction interception.

---

### Q5. Why is `@Transactional` considered AOP?

Because transaction handling is a cross-cutting concern applied around method execution via interceptor/proxy.

---

## Key Takeaways

* `@Transactional` defines declarative transaction boundaries

* Spring applies it through AOP proxies and interceptors

* commit/rollback decisions are driven by method outcome and rollback rules

* propagation and isolation control advanced transaction semantics

* understanding proxy flow explains both power and limitations

---
