# Transaction Isolation Levels in Spring and Databases

## 1. What Is Transaction Isolation?

**Transaction isolation** defines how and when changes made by one transaction become visible to other concurrent transactions.

In simple words:

> Isolation controls how safely multiple transactions can run at the same time without corrupting consistency.

Isolation is one of the ACID properties, where **I = Isolation**.

---

## 2. Why Isolation Levels Are Needed

When many users access data concurrently, these issues can happen:

* one transaction reads uncommitted data from another
* a value read once changes when read again
* repeated query returns extra/missing rows

Isolation levels exist to balance:

* **data correctness**
* **concurrency/performance**

Higher isolation gives stronger consistency, but often reduces throughput.

---

## 3. Common Concurrency Problems

### 3.1 Dirty Read

Transaction B reads data written by Transaction A before A commits.

If A rolls back, B read invalid data.

### 3.2 Non-Repeatable Read

Transaction A reads the same row twice and gets different values because Transaction B updated and committed in between.

### 3.3 Phantom Read

Transaction A runs the same query twice and gets different row sets because Transaction B inserted/deleted matching rows.

### 3.4 Lost Update (Important Practical Issue)

Two transactions read same row and both update it. One update overwrites the other without conflict handling.

---

## 4. Isolation Level Types

Most relational databases support these standard levels.

### 4.1 `READ_UNCOMMITTED`

Lowest isolation.

Allows:

* dirty reads
* non-repeatable reads
* phantom reads

Use rarely in production because data can be inconsistent.

---

### 4.2 `READ_COMMITTED`

A transaction can only read committed data.

Prevents:

* dirty reads

Still allows:

* non-repeatable reads
* phantom reads

This is the default in many databases and a common practical baseline.

---

### 4.3 `REPEATABLE_READ`

Ensures rows read once by a transaction cannot change during that transaction.

Prevents:

* dirty reads
* non-repeatable reads

Phantom behavior depends on database implementation and locking model.

In MySQL InnoDB, this is typically the default and can prevent many phantom-like scenarios via MVCC/next-key locks.

---

### 4.4 `SERIALIZABLE`

Highest isolation.

Transactions behave as if executed one by one (serial order).

Prevents:

* dirty reads
* non-repeatable reads
* phantom reads

Most correct but usually most expensive in terms of locking/contention.

---

## 5. Quick Comparison Table

| Isolation Level | Dirty Read | Non-Repeatable Read | Phantom Read | Concurrency |
| --- | --- | --- | --- | --- |
| `READ_UNCOMMITTED` | Possible | Possible | Possible | Highest |
| `READ_COMMITTED` | Prevented | Possible | Possible | High |
| `REPEATABLE_READ` | Prevented | Prevented | DB-dependent | Medium |
| `SERIALIZABLE` | Prevented | Prevented | Prevented | Lowest |

---

## 6. Isolation in Spring `@Transactional`

Spring lets you declare isolation level at method/class level.

```java
@Transactional(isolation = Isolation.READ_COMMITTED)
public void processOrder() {
    // business logic
}
```

Available Spring isolation enums:

* `Isolation.DEFAULT`
* `Isolation.READ_UNCOMMITTED`
* `Isolation.READ_COMMITTED`
* `Isolation.REPEATABLE_READ`
* `Isolation.SERIALIZABLE`

`Isolation.DEFAULT` means: use the database default.

---

## 7. Database Defaults (Important)

Isolation behavior is partly database-specific.

Typical defaults:

* PostgreSQL: `READ_COMMITTED`
* MySQL InnoDB: `REPEATABLE_READ`
* SQL Server: `READ_COMMITTED`

Always verify your actual DB default and workload behavior.

---

## 8. How to Choose the Right Level

### Use `READ_COMMITTED` when

* you need good balance of consistency and performance
* typical CRUD workloads

### Use `REPEATABLE_READ` when

* same-row consistency is important within one transaction
* financial/inventory reads need stable values

### Use `SERIALIZABLE` when

* correctness is critical and anomalies are unacceptable
* operation volume is moderate and contention is manageable

Avoid `READ_UNCOMMITTED` unless there is a very specific reason.

---

## 9. Isolation vs Propagation (Do Not Confuse)

* **Isolation**: visibility rules across concurrent transactions
* **Propagation**: how nested method calls share or create transactions

Both are configured in `@Transactional` but solve different problems.

---

## 10. Practical Example

```java
@Service
public class TransferService {

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        // read balances
        // debit and credit
        // save updates
    }
}
```

This reduces the chance of reading changing row values in the same transaction.

---

## 11. Common Mistakes

* using `SERIALIZABLE` everywhere and causing avoidable lock contention

* assuming isolation behavior is identical across all databases

* ignoring transaction duration (long transactions increase lock problems)

* confusing isolation with propagation

* setting isolation in annotation but not understanding DB default/fallback

---

## 12. Interview Questions

### Q1. What is transaction isolation?

Rules that control visibility of data changes between concurrent transactions.

---

### Q2. Which issues does `READ_COMMITTED` prevent?

It prevents dirty reads.

---

### Q3. Which isolation level is strongest?

`SERIALIZABLE`.

---

### Q4. Difference between isolation and propagation?

Isolation controls concurrent visibility; propagation controls transaction sharing across method calls.

---

### Q5. Why not always use `SERIALIZABLE`?

Because stronger isolation can reduce concurrency and increase contention.

---

## Key Takeaways

* Isolation levels manage consistency vs concurrency tradeoff

* Four standard levels: `READ_UNCOMMITTED`, `READ_COMMITTED`, `REPEATABLE_READ`, `SERIALIZABLE`

* Spring configures isolation through `@Transactional(isolation = ...)`

* Database defaults and engine behavior matter in real systems

* Choose the lowest level that safely satisfies your business correctness needs

---