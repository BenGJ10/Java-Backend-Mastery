# `@Autowired` Annotation in Spring

## 1. Overview

`@Autowired` tells Spring to **inject a dependency automatically** from the application context.

Instead of creating objects manually, Spring finds a matching bean and supplies it to the target class.

This annotation is one of the most important parts of Spring's dependency injection model because it reduces boilerplate and keeps classes loosely coupled.

---

## 2. What `@Autowired` Does

When Spring sees `@Autowired`, it tries to resolve a bean by:

1. **Type**
2. **Qualifier name** if more than one candidate exists
3. **Primary bean** if configured

In simple terms:

> Spring looks at the dependency you need and tries to provide the correct bean automatically.

---

## 3. Where `@Autowired` Can Be Used

`@Autowired` can be applied on:

* Constructor
* Setter method
* Field
* Method parameters

The most preferred form is **constructor injection**.

---

## 4. Constructor Injection with `@Autowired`

```java
@Component
public class Car {

    private final Engine engine;

    @Autowired
    public Car(Engine engine) {
        this.engine = engine;
    }
}
```

### Why this is preferred

* Dependencies are clearly visible
* Object is always fully initialized
* Works well with immutability
* Easy to test

### Spring behavior

* Spring creates an `Engine` bean
* Spring calls the `Car(Engine engine)` constructor
* The dependency is injected automatically

In Spring 4.3 and above, `@Autowired` is optional if there is only one constructor.

---

## 5. Setter Injection with `@Autowired`

```java
@Component
public class Car {

    private Engine engine;

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

Setter injection is useful when the dependency is optional or can be changed later.

---

## 6. Field Injection with `@Autowired`

```java
@Component
public class Car {

    @Autowired
    private Engine engine;
}
```

This works, but it is usually **not recommended** because:

* dependencies are hidden
* testing is harder
* immutability is not supported
* it depends on reflection

Use it only for quick demos or simple samples.

---

## 7. Optional Dependencies

By default, `@Autowired` expects a matching bean to exist.

If a bean is missing, Spring fails during startup.

You can make a dependency optional:

```java
@Autowired(required = false)
public void setEmailService(EmailService emailService) {
    this.emailService = emailService;
}
```

This is useful when a component should work even if one dependency is not present.

---

## 8. Multiple Beans of the Same Type

If Spring finds more than one bean of the same type, it cannot choose automatically.

Example:

```java
@Component
class DevEngine implements Engine { }

@Component
class TestEngine implements Engine { }
```

In such cases, use `@Qualifier` or `@Primary`.

### Example with `@Qualifier`

```java
@Autowired
public Car(@Qualifier("devEngine") Engine engine) {
    this.engine = engine;
}
```

---

## 9. `@Autowired` vs Manual Object Creation

```java
Car car = new Car(new Engine());
```

This is manual creation.

```java
@Autowired
public Car(Engine engine) {
    this.engine = engine;
}
```

This is Spring-managed dependency injection.

The second approach is preferred because Spring controls bean creation and wiring.

---

## 10. Common Mistakes

* using field injection everywhere

* forgetting that multiple beans of the same type need `@Qualifier`

* assuming `@Autowired` creates the bean itself

* injecting too many dependencies into one class

* using optional injection when the dependency is actually required

---

## 11. Interview Questions

### Q1. What does `@Autowired` do?

It tells Spring to inject a matching bean automatically.

---

### Q2. Which injection style is recommended?

Constructor injection.

---

### Q3. Is `@Autowired` required on a single constructor?

No, in Spring 4.3+ it is optional.

---

### Q4. What happens if multiple beans match the same type?

Spring throws an ambiguity error unless `@Qualifier` or `@Primary` is used.

---

### Q5. Why is field injection discouraged?

Because it hides dependencies and makes testing harder.

---

## Key Takeaways

* `@Autowired` enables automatic dependency injection

* Spring resolves dependencies mainly by type

* Constructor injection is the best practice

* Setter injection is useful for optional dependencies

* Field injection should be avoided in production code

---