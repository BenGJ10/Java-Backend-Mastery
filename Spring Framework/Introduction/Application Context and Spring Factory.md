# ApplicationContext and BeanFactory

## 1. Spring IoC Containers

The **Spring IoC Container** is responsible for:

* Creating beans
* Injecting dependencies
* Managing bean lifecycle

Spring provides **two main container types**:

1. **BeanFactory**
2. **ApplicationContext**

Both perform dependency injection, but **ApplicationContext is more powerful and commonly used**.

---

## 2. BeanFactory

### 2.1 What Is BeanFactory?

`BeanFactory` is the **basic and lowest-level** IoC container in Spring.

It provides:

* Basic bean instantiation
* Dependency injection

It follows a **lazy-loading** approach by default.

---

### 2.2 Characteristics of BeanFactory

* Lightweight container
* Beans are created **only when requested**
* Minimal memory usage
* Limited enterprise features

---

### 2.3 Example: BeanFactory

```java
Resource resource = new ClassPathResource("beans.xml");
BeanFactory factory = new XmlBeanFactory(resource);

MyService service = factory.getBean(MyService.class);
```

### Behavior

* Beans are **not created at startup**
* Bean is created **only when `getBean()` is called**

---

## 2.4 When to Use BeanFactory?

* Memory-constrained environments
* Simple applications
* Legacy systems

In modern Spring applications, BeanFactory is **rarely used directly**.

---

## 3. ApplicationContext

## 3.1 What Is ApplicationContext?

`ApplicationContext` is an **advanced IoC container** built on top of `BeanFactory`.

It provides:

* All BeanFactory features
* Plus enterprise-level services

ApplicationContext is the **default choice** for almost all Spring applications.

---

### 3.2 Characteristics of ApplicationContext

* Pre-instantiates singleton beans (eager loading)
* Supports internationalization (i18n)
* Supports event propagation
* Supports AOP
* Supports annotation-based configuration
* Better integration with Spring ecosystem

---

### 3.3 Example: ApplicationContext

```java
ApplicationContext context =
    new ClassPathXmlApplicationContext("beans.xml");

MyService service = context.getBean(MyService.class);
```

### Behavior

* All singleton beans are created **at startup**
* Errors in configuration are detected early

---

## 4. Key Differences

| Feature              | BeanFactory      | ApplicationContext |
| -------------------- | ---------------- | ------------------ |
| Container level      | Basic            | Advanced           |
| Bean loading         | Lazy (on demand) | Eager (startup)    |
| Memory usage         | Low              | Higher             |
| Enterprise features  | ❌ Not supported  | ✅ Supported        |
| AOP support          | ❌ Limited        | ✅ Full             |
| Event handling       | ❌ No             | ✅ Yes              |
| Internationalization | ❌ No             | ✅ Yes              |
| Annotation support   | ❌ Limited        | ✅ Full             |
| Typical usage        | Rare             | Default choice     |

---

## 5. Lazy vs Eager Loading

### BeanFactory (Lazy Loading)

```java
BeanFactory factory = ...
```

* Bean is created only when requested
* Slower first access
* Lower startup time

---

## ApplicationContext (Eager Loading)

```java
ApplicationContext context = ...
```

* All singleton beans created at startup
* Faster runtime access
* Higher startup time
* Fails fast on configuration issues

---

## 6. Lifecycle & Resource Management

### BeanFactory:

* No automatic lifecycle callbacks
* Limited post-processing
* Manual management required

### ApplicationContext:

* Full lifecycle management
* Automatic invocation of:

  * BeanPostProcessor
  * BeanFactoryPostProcessor
* Graceful shutdown support

---

## 7. Event Handling Capability

ApplicationContext supports **application events**.

```java
ApplicationEventPublisher publisher;
publisher.publishEvent(new CustomEvent(this));
```

BeanFactory does **not** support events.

---

## 8. Internationalization (i18n)

ApplicationContext supports message sources:

```java
context.getMessage("welcome.message", null, Locale.US);
```

BeanFactory has **no support** for i18n.

---

## 9. Integration with Spring Modules

| Module          | Requires ApplicationContext |
| --------------- | --------------------------- |
| Spring MVC      | ✅                           |
| Spring AOP      | ✅                           |
| Spring Security | ✅                           |
| Spring Data     | ✅                           |
| Spring Boot     | ✅                           |

BeanFactory is insufficient for these modules.

---

## 10. Real-World Recommendation

> **Always use ApplicationContext** unless you have a very specific reason not to.

Spring Boot internally uses **ApplicationContext**, not BeanFactory.

---

## 11. Interview Questions

### Q1. What is the difference between BeanFactory and ApplicationContext?

BeanFactory is a basic IoC container with lazy loading, while ApplicationContext is an advanced container with enterprise features and eager loading.

---

### Q2. Which container is used in Spring Boot?

ApplicationContext.

---

### Q3. Which container supports AOP?

ApplicationContext.

---

### Q4. Which container uses lazy initialization by default?

BeanFactory.

---

### Q5. Which container detects configuration errors early?

ApplicationContext (due to eager initialization).

---

### Q6. Is BeanFactory deprecated?

No, but it is rarely used directly.

---

## Key Takeaways

* BeanFactory is the **core IoC container**, but very minimal

* ApplicationContext extends BeanFactory with enterprise capabilities

* ApplicationContext eagerly initializes beans and fails fast

* ApplicationContext is the **default and recommended** container

* BeanFactory is mainly of historical or niche importance

---
