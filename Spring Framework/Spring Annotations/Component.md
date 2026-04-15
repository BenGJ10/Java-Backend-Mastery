# `@Component` Annotation in Spring

## 1. Overview

`@Component` is a **stereotype annotation** used to tell Spring that a class should be managed as a bean.

When Spring scans the classpath, it detects `@Component` classes and registers them inside the application context.

This is one of the simplest ways to create Spring-managed objects without writing explicit XML or `@Bean` methods.

---

## 2. What `@Component` Means

`@Component` marks a class as a **generic Spring bean**.

In simple words:

> “Spring, create an object of this class and manage its lifecycle.”

Spring can then inject that bean into other classes using `@Autowired`.

---

## 3. Basic Example

```java
@Component
public class Engine {

    public void start() {
        System.out.println("Engine started");
    }
}
```

```java
@Component
public class Car {

    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }
}
```

If component scanning is enabled, Spring creates both beans automatically.

---

## 4. Component Scanning

`@Component` works only when Spring is told to scan the package.

### Java configuration

```java
@Configuration
@ComponentScan("com.example")
public class AppConfig {
}
```

### Spring Boot

Spring Boot usually enables component scanning automatically through `@SpringBootApplication`.

---

## 5. `@Component` and Stereotype Annotations

`@Component` is the base annotation for other specialized annotations.

These annotations are built on top of it:

* `@Service`
* `@Repository`
* `@Controller`

They all register beans, but each one expresses a different application role.

---

## 6. `@Component` vs `@Bean`

### `@Component`

Used on the class itself.

```java
@Component
public class Engine {
}
```

### `@Bean`

Used on a method inside a `@Configuration` class.

```java
@Bean
public Engine engine() {
    return new Engine();
}
```

### Main difference

* `@Component` is **discovered automatically**
* `@Bean` is **declared explicitly**

---

## 7. When to Use `@Component`

Use `@Component` when:

* the class is a regular application component
* the object should be discovered automatically
* you want less configuration
* there is no need for special semantic meaning like service or repository

---

## 8. Bean Name of `@Component`

By default, Spring creates a bean name from the class name.

```java
@Component
public class EmailSender {
}
```

Default bean name:

```text
emailSender
```

You can also specify a custom name:

```java
@Component("mailSender")
public class EmailSender {
}
```

---

## 9. Scope Note

By default, a `@Component` bean is a **singleton**.

That means Spring creates one instance for the application context unless another scope is configured.

Example:

```java
@Component
@Scope("prototype")
public class TaskProcessor {
}
```

---

## 10. Common Mistakes

* forgetting to enable component scanning

* placing components outside the scanned package

* using `@Component` when a more specific stereotype would be clearer

* assuming `@Component` automatically makes a class a service or repository

* mixing manual object creation with Spring-managed beans

---

## 11. Interview Questions

### Q1. What does `@Component` do?

It marks a class as a Spring-managed bean.

---

### Q2. What is component scanning?

The process by which Spring searches for classes annotated with `@Component` and registers them.

---

### Q3. How is `@Component` different from `@Bean`?

`@Component` is class-based and discovered automatically, while `@Bean` is method-based and declared explicitly.

---

### Q4. What annotations are built on top of `@Component`?

`@Service`, `@Repository`, and `@Controller`.

---

## Key Takeaways

* `@Component` turns a class into a Spring bean

* It depends on component scanning

* It is the base stereotype annotation in Spring

* Prefer specific stereotypes when the class has a clear role

* Default scope is singleton

---