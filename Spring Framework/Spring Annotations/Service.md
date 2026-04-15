# `@Service` Annotation in Spring

## 1. Overview

`@Service` is a Spring stereotype annotation used for the **service layer**.

It marks a class that contains **business logic**.

Technically, `@Service` works the same as `@Component` for bean registration, but it adds **semantic meaning** that makes the code easier to understand.

---

## 2. Why `@Service` Exists

Spring applications are usually organized into layers:

* Controller layer
* Service layer
* Repository layer

The service layer sits in the middle and coordinates business operations.

Using `@Service` makes it clear that a class is part of that layer.

---

## 3. Basic Example

```java
@Service
public class OrderService {

    public void placeOrder() {
        System.out.println("Processing order");
    }
}
```

Spring detects this class during component scanning and creates a bean for it.

---

## 4. What Belongs in a Service Class

A service class usually contains:

* business rules
* orchestration logic
* validation logic related to the use case
* coordination between repositories and other components
* transactional boundaries

It should **not** contain HTTP-specific logic or SQL-specific logic.

---

## 5. Service Layer Example

```java
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void processPayment(Payment payment) {
        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        paymentRepository.save(payment);
    }
}
```

Here the service:

* validates the request
* applies business logic
* delegates persistence to the repository

---

## 6. `@Service` vs `@Component`

Technically, both register a Spring bean.

But `@Service` is better when the class performs business logic because it communicates intent.

| Aspect            | `@Component` | `@Service` |
| ----------------- | ------------ | ---------- |
| Bean registration | Yes          | Yes        |
| Semantic meaning  | Generic      | Business layer |
| Readability       | Medium       | High       |
| Best use          | General bean | Service logic |

---

## 7. `@Service` and Transactions

Service classes are often the right place for transactional behavior.

```java
@Service
public class AccountService {

    @Transactional
    public void transferMoney(Long fromId, Long toId, BigDecimal amount) {
        // business logic
    }
}
```

This works well because the service layer represents a business operation boundary.

---

## 8. Service Layer Best Practices

* keep business rules in services

* keep controllers thin

* keep repositories focused on persistence

* avoid putting HTTP request code in services

* avoid putting SQL code directly in services

A good service class should be easy to test without the web layer.

---

## 9. Common Mistakes

* using `@Service` on classes that are not business logic

* putting too much controller logic in the service layer

* letting service classes become giant god classes

* directly mixing database queries with business workflows

* forgetting that `@Service` is still a Spring bean and can be injected normally

---

## 10. Interview Questions

### Q1. What is `@Service` used for?

It marks a class as part of the business/service layer.

---

### Q2. Is `@Service` different from `@Component` technically?

No, both register beans, but `@Service` carries semantic meaning.

---

### Q3. Where should business logic be placed?

In the service layer.

---

### Q4. Why is `@Service` useful in Spring applications?

It improves readability and makes the application structure clearer.

---

## Key Takeaways

* `@Service` is a specialized stereotype annotation

* It is used for business logic classes

* It behaves like `@Component` but is more expressive

* Services sit between controllers and repositories

* Transactional workflows often belong here

---