# `@Profile` and `@Conditional` Annotations in Spring

## 1. Overview

`@Profile` and `@Conditional` are used when Spring should create beans **only in specific situations**.

They help you control bean registration based on:

* environment
* active profile
* classpath contents
* properties
* custom rules

This is useful for separating development, testing, and production behavior.

---

## 2. `@Profile`

`@Profile` tells Spring to load a bean only when a matching profile is active.

### Example

```java
@Configuration
public class AppConfig {

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
        return new DevDataSource();
    }

    @Bean
    @Profile("prod")
    public DataSource prodDataSource() {
        return new ProdDataSource();
    }
}
```

If the active profile is `dev`, Spring creates the development bean.

If the active profile is `prod`, Spring creates the production bean.

---

## 3. Why Profiles Are Useful

Profiles help when you need different beans for different environments.

Common use cases:

* database configuration
* logging settings
* mock beans for testing
* external service endpoints

Instead of changing code manually, you activate the required profile.

---

## 4. Activating a Profile

### application.properties

```properties
spring.profiles.active=dev
```

### Or via JVM argument

```text
-Dspring.profiles.active=prod
```

Only beans matching the active profile are created.

---

## 5. Class-Level `@Profile`

`@Profile` can also be placed directly on a class.

```java
@Service
@Profile("test")
public class MockPaymentService implements PaymentService {
}
```

This bean exists only when the `test` profile is active.

---

## 6. `@Conditional`

`@Conditional` gives Spring more flexible control over bean creation.

A bean is created only if a custom condition returns `true`.

```java
@Bean
@Conditional(MyCondition.class)
public FeatureService featureService() {
    return new FeatureService();
}
```

`MyCondition` decides whether the bean should be registered.

---

## 7. How `@Conditional` Works

A condition class implements Spring's `Condition` interface.

```java
public class MyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return true;
    }
}
```

If `matches(...)` returns `true`, the bean is created.

If it returns `false`, Spring skips it.

---

## 8. When to Use `@Conditional`

Use it when bean creation depends on:

* a property value
* a class existing on the classpath
* an environment flag
* a custom application rule

This is more flexible than profiles.

---

## 9. `@Profile` vs `@Conditional`

| Aspect            | `@Profile`                   | `@Conditional`                     |
| ----------------- | ---------------------------- | ---------------------------------- |
| Purpose           | Environment-specific beans   | Rule-based bean registration       |
| Simplicity        | Simple                       | More flexible                      |
| Typical use       | dev/test/prod                | feature flags, custom logic        |
| Control mechanism | Active profile                | Condition implementation          |

A good rule:

* use `@Profile` for environment separation
* use `@Conditional` for custom logic

---

## 10. Real-World Examples

### Development vs Production

```java
@Bean
@Profile("dev")
public Logger devLogger() {
    return new ConsoleLogger();
}
```

```java
@Bean
@Profile("prod")
public Logger prodLogger() {
    return new FileLogger();
}
```

### Feature Toggle

```java
@Bean
@Conditional(FeatureEnabledCondition.class)
public AnalyticsService analyticsService() {
    return new AnalyticsService();
}
```

---

## 11. Common Mistakes

* forgetting to activate the correct profile

* using profiles for logic that should be handled by conditions

* placing conditional code where the rule is hard to understand

* assuming a bean exists without checking the active environment

* mixing too many environment-specific rules in one class

---

## 12. Interview Questions

### Q1. What is `@Profile` used for?

It registers beans only for a specific environment profile.

---

### Q2. What is `@Conditional` used for?

It registers beans only when a custom condition is satisfied.

---

### Q3. Which is simpler, `@Profile` or `@Conditional`?

`@Profile` is simpler.

---

### Q4. When should you use `@Conditional`?

When bean creation depends on logic beyond simple environment selection.

---

## Key Takeaways

* `@Profile` is for environment-based bean selection

* `@Conditional` is for rule-based bean selection

* Both help Spring decide whether to register a bean

* They are useful for separating dev, test, and production behavior

* They reduce manual configuration and environment-specific code

---