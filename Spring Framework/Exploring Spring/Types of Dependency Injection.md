# Types of Dependency Injection in Spring

## 1. Dependency Injection Recap

**Dependency Injection (DI)** means:

> An object does **not create** its dependencies.
> Dependencies are **provided to it** by the Spring IoC container.

Spring mainly supports **two practical DI types** used in real applications:

1. **Constructor Injection**
2. **Setter Injection**

(Field injection exists but is discouraged in production and interviews.)

---

## 2. Constructor Injection

In **constructor injection**, dependencies are provided through the **constructor** of the class.

> The object **cannot be created** without its required dependencies.

This makes dependencies **mandatory** and ensures the object is always in a valid state.

---

### Basic Example

#### Dependency

```java
@Component
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}
```

#### Dependent Class (Constructor Injection)

```java
@Component
class Car {

    private final Engine engine;

    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

#### What Spring Does

1. Creates `Engine` bean
2. Calls `Car(Engine engine)` constructor
3. Injects the dependency
4. Returns fully initialized `Car` bean

---

### @Autowired Is Optional (Spring 4.3+)

If a class has **only one constructor**, `@Autowired` is optional.

```java
@Component
class Car {

    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }
}
```

Spring automatically injects the dependency.

---

### Why Constructor Injection Is Recommended

#### Advantages

* Dependencies are **mandatory**
* Promotes **immutability** (`final` fields)
* Prevents partially initialized objects
* Easier unit testing
* Clear visibility of dependencies
* Best support for SOLID principles

#### Design Benefit

```java
public Car(Engine engine) {
    this.engine = engine;
}
```

Anyone reading the class immediately knows **what the class depends on**.

---

### Constructor Injection with Multiple Dependencies

```java
@Component
class OrderService {

    private final PaymentService paymentService;
    private final NotificationService notificationService;

    public OrderService(
            PaymentService paymentService,
            NotificationService notificationService) {
        this.paymentService = paymentService;
        this.notificationService = notificationService;
    }
}
```

Spring resolves and injects **all required beans**.

---

## 3. Setter Injection

### What Is Setter Injection?

In **setter injection**, dependencies are injected through **setter methods** after the object is created.

> Dependencies are **optional** and can be changed later.

---

### Basic Example

### Dependency

```java
@Component
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}
```

### Dependent Class (Setter Injection)

```java
@Component
class Car {

    private Engine engine;

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

### Injection Flow

1. Spring creates `Car` using default constructor
2. Spring creates `Engine`
3. Spring calls `setEngine(engine)`
4. Dependency is injected

---

### Characteristics of Setter Injection

* Dependency is **not mandatory**
* Object can exist without dependency
* Dependency can be changed later
* Requires setter method

---

### Optional Dependency Example

```java
@Component
class ReportService {

    private EmailService emailService;

    @Autowired(required = false)
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
```

If `EmailService` bean is not present, Spring **will not fail**.

---

## 4. Constructor vs Setter Injection

| Aspect            | Constructor Injection | Setter Injection             |
| ----------------- | --------------------- | ---------------------------- |
| Dependency nature | Mandatory             | Optional                     |
| Immutability      | Supported             | Not supported                |
| Object validity   | Always valid          | May be partially initialized |
| Readability       | High                  | Medium                       |
| Testability       | Excellent             | Good                         |
| Recommended       | ✅ Yes                 | ⚠️ Limited cases             |

---

## 5. When to Use Which?

### Use Constructor Injection When

* Dependency is **required**
* Class cannot function without it
* You want immutable design
* Writing business logic or services

This is the **default choice**.

---

### Use Setter Injection When

* Dependency is **optional**
* Dependency may change at runtime
* Circular dependencies must be resolved (rare case)
* Legacy codebases

---

## 6. Circular Dependency Note

Constructor injection **detects circular dependencies early**.

Example (problematic):

```java
class A {
    A(B b) { }
}

class B {
    B(A a) { }
}
```

Spring fails fast → good design feedback.

Setter injection can hide such problems, which is why constructor injection is preferred.

---

## 7. Common Mistakes

* Mixing constructor and setter injection unnecessarily
* Using setter injection for mandatory dependencies
* Too many setter-injected fields → unclear design
* Avoiding `final` with constructor injection

---

## 8. Interview Questions

### Q1. What are the types of Dependency Injection in Spring?

Constructor Injection and Setter Injection.

---

### Q2. Which DI type is recommended and why?

Constructor Injection, because it enforces mandatory dependencies, immutability, and better design.

---

### Q3. Can Spring inject dependencies without @Autowired?

Yes, if there is a single constructor.

---

### Q4. When should setter injection be used?

When a dependency is optional or configurable.

---

### Q5. How does constructor injection help with testing?

Dependencies can be passed directly without Spring context.

---

### Q6. Which DI type handles circular dependencies better?

Setter injection (but circular dependencies indicate bad design).

---

## Key Takeaways

* Spring mainly uses **Constructor Injection** and **Setter Injection**

* Constructor Injection is **default and recommended**
* Setter Injection is useful for **optional dependencies**
* Constructor Injection promotes:

  * Immutability
  * Clean design
  * Better testing
* Prefer clarity and strictness over flexibility

---
