# Spring Configuration Types

Spring **configuration** defines **how beans are created, wired, and managed** by the Spring IoC container.

Over time, Spring has evolved from heavy XML-based configuration to **annotation-driven and Java-based configuration**, with Spring Boot pushing this even further through **auto-configuration**.

Understanding configuration types is important because it explains:

* How Spring knows *what beans exist*
* How dependencies are wired
* How modern Spring applications reduce boilerplate

---

## 1. What Is Spring Configuration?

Spring configuration is the process of **providing metadata** to the Spring container so that it can:

* Identify beans
* Instantiate them
* Inject dependencies
* Manage lifecycle

This metadata can be provided in **multiple ways**, called **configuration types**.

---

## 2. Major Types of Spring Configuration

Spring supports **four main configuration styles**:

1. **XML-Based Configuration (Legacy)**
2. **Annotation-Based Configuration**
3. **Java-Based Configuration (`@Configuration`)**
4. **Spring Boot Auto-Configuration**

These are not mutually exclusive — they can be **combined**, but modern Spring prefers **Java + annotations**.

---

## 3. XML-Based Configuration (Legacy Approach)

### 3.1 What Is XML Configuration?

In early Spring versions, all beans and dependencies were defined in **XML files**.

Spring reads these XML files and creates beans accordingly.

---

### 3.2 Example

### beans.xml

```xml
<beans>

    <bean id="engine" class="com.example.Engine"/>

    <bean id="car" class="com.example.Car">
        <property name="engine" ref="engine"/>
    </bean>

</beans>
```

### Loading XML Configuration

```java
ApplicationContext context =
    new ClassPathXmlApplicationContext("beans.xml");

Car car = context.getBean(Car.class);
```

---

### 3.3 Characteristics of XML Configuration

* Centralized configuration
* No annotations required in Java classes
* Verbose and hard to maintain
* No compile-time safety
* Errors discovered at runtime

---

### 3.4 When XML Was Useful

* Large enterprise applications
* Strict separation of config and code
* Legacy systems

In modern Spring, XML is **rarely used**.

---

## 4. Annotation-Based Configuration

### 4.1 What Is Annotation-Based Configuration?

In this approach, Spring uses **annotations inside Java classes** to:

* Identify beans
* Inject dependencies
* Define behavior

Spring scans classpath for annotations.

---

### 4.2 Common Stereotype Annotations

```java
@Component
@Service
@Repository
@Controller
@RestController
```

Example:

```java
@Service
public class UserService { }
```

This tells Spring:

> “Create and manage this class as a bean.”

---

### 4.3 Dependency Injection with Annotations

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

---

### 4.4 Enabling Annotation Scanning

### Java Configuration

```java
@ComponentScan("com.example")
```

### XML (Legacy)

```xml
<context:component-scan base-package="com.example"/>
```

---

### 4.5 Pros and Cons

### Advantages

* Less boilerplate
* Easy to read
* Faster development
* Works well with modern tooling

### Disadvantages

* Configuration mixed with code
* Over-annotation can clutter classes

---

## 5. Java-Based Configuration (`@Configuration`)

### 5.1 What Is Java-Based Configuration?

Java-based configuration uses **pure Java classes** to define beans explicitly.

This approach replaced most XML usage.

---

### 5.2 Basic Example

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

---

### 5.3 Key Annotations

* `@Configuration` → marks config class
* `@Bean` → defines a bean

Spring treats `@Configuration` classes specially to ensure:

* Singleton behavior
* Dependency consistency

---

### 5.4 How Spring Manages @Configuration Classes

* `@Configuration` classes are proxied using CGLIB
* Ensures that calling `engine()` multiple times returns the same bean instance

This avoids accidental multiple object creation.

---

### 5.5 Advantages of Java-Based Configuration

* Compile-time safety
* Refactoring-friendly
* No XML
* Clear bean definitions
* Works perfectly with Spring Boot

---

## 6. Hybrid Configuration (Annotations + Java Config)

Most real-world applications use a **hybrid approach**.

Example:

```java
@Configuration
@ComponentScan("com.example")
public class AppConfig { }
```

* `@ComponentScan` picks up annotated classes
* `@Bean` defines explicit beans

This is a **recommended approach**.

---

## 7. Spring Boot Auto-Configuration

### 7.1 What Is Auto-Configuration?

Spring Boot automatically configures beans based on:

* Classpath dependencies
* Application properties
* Environment

Example:

* Add `spring-boot-starter-web`
* Spring Boot configures:

  * DispatcherServlet
  * Embedded Tomcat
  * JSON converters
  * Error handling

No explicit bean definitions required.

---

### 7.2 How Auto-Configuration Works (High Level)

Spring Boot uses:

* `@EnableAutoConfiguration`
* `spring.factories` / `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`
* Conditional annotations

Example:

```java
@ConditionalOnClass(DataSource.class)
@ConditionalOnProperty("spring.datasource.url")
```

Beans are created **only if conditions match**.

---

### 7.3 Overriding Auto-Configuration

You can override auto-configured beans:

```java
@Bean
public DataSource dataSource() {
    return customDataSource();
}
```

Spring Boot backs off automatically.

---

## 8. Configuration Using Properties and YAML


### 8.1 application.properties

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://localhost/test
```

---

### 8.2 application.yml

```yaml
server:
  port: 8081

spring:
  datasource:
    url: jdbc:mysql://localhost/test
```

---

### 8.3 Binding Properties to Beans

```java
@ConfigurationProperties(prefix = "app")
@Component
public class AppConfigProps {
    private String name;
    private int timeout;
}
```

---

## 9. Comparison of Configuration Types

| Configuration Type | Style            | Usage Today         |
| ------------------ | ---------------- | ------------------- |
| XML                | Declarative      | Legacy              |
| Annotation-Based   | Declarative      | Common              |
| Java-Based         | Programmatic     | Very Common         |
| Auto-Configuration | Convention-based | Spring Boot Default |

---

## 10. Best Practices

* Prefer **Java-based + annotation configuration**
* Avoid XML unless maintaining legacy code
* Let Spring Boot auto-configure as much as possible
* Override only when necessary
* Keep configuration clean and modular
* Separate configuration from business logic

---

## 11. Interview Questions

### Q1. What are the different Spring configuration types?

XML, Annotation-based, Java-based, and Auto-configuration.

---

### Q2. Which configuration type is preferred today?

Java-based and annotation-based configuration.

---

### Q3. Why is XML configuration discouraged?

Verbose, error-prone, and not refactoring-friendly.

---

### Q4. What is the role of @Configuration?

Marks a class as a source of bean definitions.

---

### Q5. How does Spring Boot reduce configuration?

Through auto-configuration and sensible defaults.

---

## Key Takeaways

* Spring configuration defines how beans are created and wired

* XML was the original approach but is now legacy

* Annotation-based and Java-based configuration are modern standards

* Spring Boot auto-configuration minimizes manual setup

* Understanding configuration types is essential for debugging and customization

---
