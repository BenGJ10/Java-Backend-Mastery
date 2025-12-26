# Spring Java-Based Configuration

Java-based configuration is the **modern and recommended** way to configure Spring applications.

Instead of using XML files, we use **pure Java classes and annotations** to define beans, enable component scanning, and configure the Spring container.

---

## 1. What Is Java-Based Configuration?

Java-based configuration means:

> Spring beans and dependencies are configured using **Java classes annotated with `@Configuration` and `@Bean`**, instead of XML.

Spring introduced this style in **Spring 3+** to provide:

* type safety
* IDE refactoring support
* less boilerplate
* better readability

---

## 2. Core Annotations in Java-Based Configuration

Java-based configuration mainly uses:

| Annotation        | Purpose                                   |
| ----------------- | ----------------------------------------- |
| `@Configuration`  | Marks class as Spring configuration class |
| `@Bean`           | Declares a bean                           |
| `@ComponentScan`  | Enables component scanning                |
| `@Import`         | Imports another configuration class       |
| `@PropertySource` | Loads properties from file                |

---

## 3. `@Configuration` Annotation

### What it does

* tells Spring that the class contains **bean definitions**
* acts as **replacement for XML `<beans>`**
* Spring enhances it using **CGLIB proxy** to maintain singleton semantics

### Example

```java
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
}
```

This class now becomes a **Spring configuration class**.

---

## 4. `@Bean` Annotation

### What it does

* equivalent to XML `<bean>` tag
* tells Spring to **create, manage, and inject** this object

### Example

```java
@Configuration
public class AppConfig {

    @Bean
    public Engine engine() {
        return new Engine();
    }
}
```

Spring will register `engine()` method return object as a **Spring bean**.

---

### Using Bean inside another Bean

```java
@Configuration
public class AppConfig {

    @Bean
    public Engine engine() {
        return new Engine();
    }

    @Bean
    public Car car() {
        return new Car(engine());
    }
}
```

Because of `@Configuration` proxying, `engine()` is called **once**, preserving singleton semantics.

---

## 5. Loading Java-Based Configuration

### Using AnnotationConfigApplicationContext

```java
ApplicationContext context =
        new AnnotationConfigApplicationContext(AppConfig.class);

Car car = context.getBean(Car.class);
```

This replaces:

```xml
<beans>...</beans>
```

---

## 6. `@ComponentScan`

Instead of declaring each bean manually, Spring can **scan packages automatically**.

### Example

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

Now Spring detects:

```java
@Component
@Service
@Repository
@Controller
```

automatically.

---

## 7. Combining `@Bean` and `@ComponentScan`

This is the **most common real-world setup**.

```java
@Configuration
@ComponentScan("com.example")
public class AppConfig {

    @Bean
    public Engine engine() {
        return new Engine();
    }
}
```

* manually declared beans using `@Bean`
* auto-detected beans using `@ComponentScan`

---

## 8. Injecting Dependencies in Java Config

### Constructor Injection Example

```java
@Component
class Car {
    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }
}
```

No XML, no explicit bean wiring needed.

Spring resolves dependency using type-matching.

---

## 9. `@Import` — Splitting Configuration

Large applications split configuration across files.

### Example

```java
@Configuration
public class DBConfig { }
```

```java
@Configuration
public class ServiceConfig { }
```

```java
@Configuration
@Import({DBConfig.class, ServiceConfig.class})
public class AppConfig { }
```

This avoids **huge monolithic configuration classes**.

---

## 10. Reading Properties in Java Config

### Load properties file

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
}
```

### Inject value

```java
@Value("${app.name}")
private String name;
```

---

## 11. Java Config vs XML Config

| Feature      | Java Config | XML Config |
| ------------ | ----------- | ---------- |
| Type safety  | High        | None       |
| Refactoring  | Easy        | Hard       |
| Readability  | High        | Verbose    |
| Boilerplate  | Low         | High       |
| IDE support  | Excellent   | Limited    |
| Modern usage | Recommended | Legacy     |

---

## 12. Java Config in Spring Boot

Spring Boot **internally uses Java config everywhere**.

Typical Boot app:

```java
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
```

`@SpringBootApplication` includes:

* `@Configuration`
* `@EnableAutoConfiguration`
* `@ComponentScan`

So **Spring Boot is built fully on Java-based configuration**.

---

## 13. Advanced Topic — `@Configuration` vs `@Component`

| Aspect                   | @Configuration        | @Component             |
| ------------------------ | --------------------- | ---------------------- |
| Purpose                  | Define bean methods   | Register class as bean |
| Proxy created            | Yes                   | No                     |
| Bean method interception | Yes                   | No                     |
| Use case                 | Central configuration | Business components    |

---

## 14. Common Mistakes

* Forgetting `@Configuration`
* Using `new` instead of letting Spring create bean
* Mixing XML and Java without understanding precedence
* Overusing `@Bean` instead of component scanning
* Creating multiple instances accidentally when removing `@Configuration`

---

## 15. Interview Questions

### Q1. What is Java-based configuration?

Configuration of Spring beans using Java classes instead of XML.

---

### Q2. Why prefer Java configuration over XML?

* type safety
* readability
* refactor support
* less boilerplate

---

### Q3. Difference between `@Configuration` and `@Component`?

`@Configuration` is specialized `@Component` that supports **bean method proxying**.

---

### Q4. What is `@Bean` used for?

To declare and register a method result as a Spring bean.

---

### Q5. What annotaiton enables component scanning?

`@ComponentScan`.

---

## Key Takeaways

* Java-based configuration is **modern Spring standard**

* `@Configuration` + `@Bean` replace XML

* Supports component scanning and dependency injection

* Used internally by Spring Boot

* Offers refactoring, IDE help, and strong typing

* Cleaner and more maintainable approach

---
