# Different Types of Spring Beans

In Spring, **bean types** refer to beans that differ in **creation timing**, **selection priority**, **scope behavior**, or **conditional loading**.

These are **not different classes**, but **different behaviors applied to beans** using annotations or configuration.

---

## 1. Default Spring Bean (Baseline)

Before understanding special bean types, it is important to know the **default behavior**.

```java
@Component
class UserService { }
```

By default, this bean is:

* **Singleton scoped**
* **Eagerly initialized**
* **Selected by type**
* **Fully managed by Spring**

All other bean types are **modifications of this default behavior**.

---

## 2. Lazy Bean (`@Lazy`)

### What Is a Lazy Bean?

A **lazy bean** is **not created at application startup**.

> It is created **only when it is first requested** from the container.

---

### Default vs Lazy Initialization

| Aspect        | Default Bean | Lazy Bean    |
| ------------- | ------------ | ------------ |
| Creation time | Startup      | First usage  |
| Startup time  | Slower       | Faster       |
| First access  | Fast         | Slight delay |
| Memory usage  | Immediate    | Deferred     |

---

### Basic Example

```java
@Component
@Lazy
class ReportService {

    public ReportService() {
        System.out.println("ReportService created");
    }
}
```

If `ReportService` is never used, **it is never created**.

---

### Lazy Dependency Injection

You can make **only a dependency lazy**, not the entire bean.

```java
@Autowired
@Lazy
private ReportService reportService;
```

Spring injects a **proxy**, and the real bean is created only when accessed.

---

### When to Use Lazy Beans

* Heavy initialization logic
* Rarely used features
* Improving startup time
* Optional components

---

## 3. Primary Bean (`@Primary`)

### What Is a Primary Bean?

When **multiple beans of the same type exist**, Spring cannot decide which one to inject.

`@Primary` tells Spring:

> “Use this bean by default unless explicitly overridden.”

---

### Problem Scenario

```java
interface PaymentService { }

@Component
class CardPaymentService implements PaymentService { }

@Component
class UpiPaymentService implements PaymentService { }
```

Injection causes ambiguity.

---

### Using @Primary

```java
@Primary
@Component
class CardPaymentService implements PaymentService { }
```

Now Spring injects `CardPaymentService` automatically.

---

### Important Rule

* `@Primary` is **global**
* `@Qualifier` always **overrides @Primary**

---

## 4. Qualified Bean (`@Qualifier`)

### What Is a Qualified Bean?

A **qualified bean** is selected **explicitly by name or custom qualifier**, instead of relying on defaults.

---

### Basic Example

```java
@Autowired
@Qualifier("upiPaymentService")
private PaymentService paymentService;
```

Spring resolves:

1. By type
2. Then by qualifier

---

### Custom Qualifier (Advanced)

```java
@Qualifier
@Retention(RUNTIME)
@Target({FIELD, PARAMETER, TYPE})
public @interface OnlinePayment { }
```

```java
@OnlinePayment
@Component
class UpiPaymentService implements PaymentService { }
```

Used in **large applications** to avoid string-based qualifiers.

---

### When to Use Qualifiers

* Multiple implementations exist
* Precise control is required
* Avoid accidental bean injection

---

## 5. Prototype Bean (`@Scope("prototype")`)

### What Is a Prototype Bean?

A **prototype bean** creates a **new instance every time it is requested**.

```java
@Component
@Scope("prototype")
class TaskContext { }
```

---

### Behavior

```java
TaskContext t1 = context.getBean(TaskContext.class);
TaskContext t2 = context.getBean(TaskContext.class);

System.out.println(t1 == t2); // false
```

---

### Lifecycle Limitation (Very Important)

Spring:

* Creates the bean
* Injects dependencies
* Calls init methods
* **Stops tracking the bean**

> Destroy methods are **not called** for prototype beans.

---

### When to Use Prototype Beans

* Stateful objects
* Per-task data
* Thread-isolated logic
* Temporary helpers

---

## 6. Scoped Beans (Web-Aware Beans)

These beans are tied to **web lifecycles**, not application lifecycle.

### Request Scoped Bean

```java
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class RequestContext { }
```

* One instance per HTTP request
* Destroyed after request completes

---

### Session Scoped Bean

```java
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
class UserSession { }
```

* One instance per user session
* Shared across requests of same user

---

### Application Scoped Bean

```java
@Component
@Scope(value = "application", proxyMode = ScopedProxyMode.TARGET_CLASS)
class AppStats { }
```

* One instance per web application
* Lives until application shuts down

---

## 7. Profile-Based Beans (`@Profile`)

### What Is a Profile Bean?

A **profile bean** is created **only when a specific environment is active**.

---

### Example

```java
@Component
@Profile("dev")
class DevDataSource { }
```

```java
@Component
@Profile("prod")
class ProdDataSource { }
```

Activated via:

```properties
spring.profiles.active=dev
```

Used heavily for:

* Dev / QA / Prod separation
* Environment-specific configuration

---

## 8. Conditional Beans (`@Conditional`)

### What Is a Conditional Bean?

A bean that is created **only when certain conditions are met**.

---

### Example

```java
@Bean
@ConditionalOnProperty(name = "feature.enabled", havingValue = "true")
FeatureService featureService() {
    return new FeatureService();
}
```

Used internally by **Spring Boot auto-configuration**.

---

## 9. FactoryBean (`FactoryBean<T>`)

### What Is a FactoryBean?

A **FactoryBean** does **not expose itself**.

Instead, it exposes the **object it creates**.

```java
class MyFactory implements FactoryBean<MyObject> {

    public MyObject getObject() {
        return new MyObject();
    }

    public Class<?> getObjectType() {
        return MyObject.class;
    }
}
```

Injecting this bean gives `MyObject`, not `MyFactory`.

---

### When to Use FactoryBean

* Complex object creation
* Framework-level abstractions
* Proxy creation
* Integration libraries

---

## 10. Depends-On Bean (`@DependsOn`)

### What Is @DependsOn?

Forces **initialization order**.

```java
@Component
@DependsOn("databaseInitializer")
class UserService { }
```

Used when:

* Startup order matters
* External resources are involved

---

## 11. Summary Table

| Bean Type                       | Purpose                             |
| ------------------------------- | ----------------------------------- |
| Default Bean                    | Standard Spring bean                |
| Lazy Bean                       | Created on first use                |
| Primary Bean                    | Default choice among multiple beans |
| Qualified Bean                  | Explicit bean selection             |
| Prototype Bean                  | New instance per request            |
| Request / Session / Application | Web lifecycle beans                 |
| Profile Bean                    | Environment-specific                |
| Conditional Bean                | Created based on conditions         |
| FactoryBean                     | Produces other beans                |
| DependsOn Bean                  | Controls initialization order       |

---

## 12. Interview Questions

### Q1. What is a lazy bean?

A bean created only when it is first requested.

---

### Q2. Difference between @Primary and @Qualifier?

`@Primary` sets a default, `@Qualifier` explicitly selects a bean.

---

### Q3. Does Spring manage prototype bean destruction?

No.

---

### Q4. What is FactoryBean used for?

To create and expose complex objects instead of itself.

---

### Q5. Why use @Profile?

To load beans conditionally based on environment.

---

## Key Takeaways

* Spring bean types control **creation, selection, and lifecycle**

* Default beans are singleton and eager

* Lazy beans improve startup performance

* Primary and Qualifier solve ambiguity

* Prototype beans are not fully managed

* Profile and conditional beans enable environment-specific behavior

---

