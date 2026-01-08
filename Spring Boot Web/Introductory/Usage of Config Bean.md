# Usage of Java Config Bean in Spring Boot

## 1. Overview

In Spring Boot, **Java-based configuration** is the preferred way to define beans instead of XML.

Using Java Config:

* Configuration is **type-safe**
* Easier to refactor and maintain
* IDE-friendly (autocomplete, validation)
* Widely used in modern Spring Boot applications

Beans are defined using:

* `@Configuration`
* `@Bean`

This approach is heavily used when integrating **external services**, such as **AWS SDK clients**, database connections, messaging systems, etc.

---

## 2. What is a Bean in Spring?

A **bean** is an object that is:

* Created
* Managed
* Injected

by the **Spring IoC container**.

Spring controls the lifecycle of beans and injects them wherever required.

---

## 3. Why Use Java Config (`@Configuration` + `@Bean`)?

Java Config is commonly used when:

* You need to configure **third-party libraries**
* Auto-configuration is not sufficient
* You want fine-grained control over object creation
* Object creation involves credentials or environment-specific setup

AWS SDK clients are a perfect real-world example.

---

## 4. Basic Java Config Example

```java
@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
```

Now `ObjectMapper` can be injected anywhere:

```java
@Autowired
private ObjectMapper objectMapper;
```

---

## 5. Real-World AWS Example: Amazon S3 Client Configuration

In backend systems, AWS S3 is commonly used for:

* file uploads
* backups
* static asset storage
* log storage

Spring Boot does **not auto-configure** AWS SDK clients by default — we must define them as beans.

---

## 6. AWS S3 Java Config Bean

### 6.1 Configuration Class

```java
@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
```

Explanation:

* `@Configuration` → marks class as configuration source
* `@Bean` → registers `S3Client` in Spring context
* `DefaultCredentialsProvider` → reads credentials from:

  * environment variables
  * EC2 instance role
  * ECS task role
  * local AWS config

---

## 7. Injecting S3 Client Bean

```java
@Service
public class FileStorageService {

    private final S3Client s3Client;

    public FileStorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }
}
```

Spring automatically injects the configured S3 client.

---

## 8. Using S3 Bean in Service Layer

```java
public void uploadFile(String bucket, String key, Path filePath) {

    PutObjectRequest request = PutObjectRequest.builder()
            .bucket(bucket)
            .key(key)
            .build();

    s3Client.putObject(request, filePath);
}
```

Benefits:

* Service logic does not care about how S3 client is created
* Configuration is centralized
* Easy to swap credentials or region

---

## 9. Externalizing Configuration (Best Practice)

Instead of hardcoding region:

```properties
aws.region=ap-south-1
```

```java
@Configuration
public class S3Config {

    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
```

This allows environment-specific deployment:

* dev
* staging
* production

---

## 10. Bean Scope in Java Config

Default scope:

```
singleton
```

Same S3 client instance reused across application.

Other scopes:

* prototype
* request
* session

Singleton is ideal for AWS SDK clients.

---

## 11. Why Not Create S3 Client Using `new`?

Bad practice:

```java
S3Client s3 = S3Client.builder().build();
```

Problems:

* Not managed by Spring
* Hard to test
* Hard to mock
* No lifecycle control
* Configuration duplication

Using `@Bean` solves all of these.

---

## 12. Testing Advantage with Java Config

During testing:

```java
@TestConfiguration
public class TestS3Config {

    @Bean
    public S3Client s3Client() {
        return mock(S3Client.class);
    }
}
```

This allows mocking AWS without hitting real services.

---

## 13. Java Config vs Component Scanning

| Aspect                | Java Config (`@Bean`)   | Component (`@Component`) |
| --------------------- | ----------------------- | ------------------------ |
| Best for              | External libraries      | Application classes      |
| Control over creation | Full                    | Limited                  |
| Use case              | AWS clients, DB configs | Services, controllers    |

---

## 14. Common Interview Questions

### Q1: Why use `@Bean` instead of `@Component`?

`@Bean` is used when you don’t control the class source code.

---

### Q2: Is `@Configuration` mandatory?

Yes — it ensures proper proxying and singleton behavior.

---

### Q3: Where are Java Config beans used in real projects?

* AWS SDK clients
* Kafka producers
* RestTemplate / WebClient
* DataSource configuration

---

### Q4: What happens if multiple beans of same type exist?

Spring throws ambiguity error unless resolved using:

* `@Primary`
* `@Qualifier`

---

## Key Takeaways

* Java Config is the preferred configuration style in Spring Boot

* `@Configuration` + `@Bean` define managed objects

* Ideal for integrating AWS services like S3

* Promotes clean separation of configuration and business logic

* Makes applications testable and scalable

---
