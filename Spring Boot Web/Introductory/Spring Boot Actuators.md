# Spring Boot Actuator Endpoints

## 1. Overview

**Spring Boot Actuator** provides production-ready features to help you **monitor, manage, and observe** your Spring Boot applications.

It exposes operational information via:

* HTTP endpoints
* JMX endpoints

Actuator is widely used in backend systems for:

* Health monitoring
* Metrics collection
* Logging monitoring
* Traffic and performance analysis
* Integration with tools like Prometheus, Grafana, Kubernetes, AWS CloudWatch

---

## 2. Adding Spring Boot Actuator Dependency

For Maven:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

---

## 3. Enabling Actuator Endpoints

By default, **only a few endpoints are exposed**.

In `application.properties`:

```properties
management.endpoints.web.exposure.include=*
```

Or expose only specific ones:

```properties
management.endpoints.web.exposure.include=health,info,metrics
```

---

## 4. Types of Actuator Endpoints

Actuator provides two main categories:

1. **Core Monitoring Endpoints**
2. **Operational/Management Endpoints**

Endpoints can be accessed typically at:

```
http://localhost:8080/actuator
```

---

## 5. Important Spring Boot Actuator Endpoints

### 5.1 `/actuator/health`

Shows **application health status**.

```json
{
  "status": "UP"
}
```

Can include:

* database connectivity
* disk space
* redis or rabbitmq connectivity
* custom health indicators

To see more details:

```properties
management.endpoint.health.show-details=always
```

---

### 5.2 `/actuator/info`

Displays **application information** like:

* version
* build time
* description

Configure in `application.properties`:

```properties
info.app.name=Order Service
info.app.version=1.0.0
```

---

### 5.3 `/actuator/metrics`

Provides **application metrics**, such as:

* memory usage
* JVM stats
* HTTP request count
* CPU usage
* GC stats

Example metric name:

```
/actuator/metrics/http.server.requests
```

---

### 5.4 `/actuator/beans`

Displays all Spring beans created in application context.

---

### 5.5 `/actuator/mappings`

Shows MVC request mappings:

* controllers
* REST APIs
* handler methods

Useful for debugging routing issues.

---

### 5.6 `/actuator/env`

Shows **application environment properties** including:

* system properties
* environment variables
* configuration values

---

### 5.7 `/actuator/loggers`

View and modify log levels at runtime.

Example change log level via HTTP:

* Before:

```
/actuator/loggers/com.project.service
```

* Update level:

```json
{
  "configuredLevel": "DEBUG"
}
```

---

### 5.8 `/actuator/threaddump`

Returns thread dump — useful to analyze:

* deadlocks
* thread starvation
* performance bottlenecks

---

### 5.9 `/actuator/shutdown` (Disabled by default)

Gracefully shuts down application.

Enable explicitly:

```properties
management.endpoint.shutdown.enabled=true
```

Use with caution in production.

---

## 6. Real-World Backend Use Cases

### 6.1 Kubernetes / Docker Health Probes

Kubernetes uses:

* `/actuator/health/liveness`
* `/actuator/health/readiness`

Configuration:

```properties
management.endpoint.health.probes.enabled=true
```

Used for:

* container restart
* rolling deployment safety
* traffic routing readiness

---

### 6.2 API Gateway / Load Balancer Health Check

Cloud services (AWS ALB, GCP, Nginx) poll:

```
/actuator/health
```

If service returns `DOWN`, traffic is stopped.

---

### 6.3 Production Monitoring Dashboard

Combine Actuator with:

* Prometheus
* Micrometer
* Grafana

Pipeline:

```
Actuator → Micrometer → Prometheus → Grafana
```

Gives:

* request rate
* response latency
* error percentage
* JVM memory stats

---

## 7. Securing Actuator Endpoints

Never expose all endpoints publicly.

### Spring Security Example

```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.shutdown.enabled=false
```

Secure via basic auth:

```properties
spring.security.user.name=admin
spring.security.user.password=secret
```

Or restrict network access through API gateway.

---

## 8. Custom Actuator Endpoint

Create your own operational endpoint.

```java
@Component
@Endpoint(id = "featuretoggle")
public class FeatureToggleEndpoint {

    @ReadOperation
    public Map<String, Boolean> featureStatus() {
        return Map.of("paymentV2", true);
    }
}
```

Will be available at:

```
/actuator/featuretoggle
```

Used for:

* feature flags
* debugging toggles
* maintenance switches

---

## 9. Common Interview Questions

### Q1: What is Spring Boot Actuator?

A production-ready toolkit for monitoring and managing Spring Boot apps.

---

### Q2: Which actuator endpoint is most commonly used?

`/actuator/health`

---

### Q3: Is actuator safe to expose publicly?

No — secure endpoints with auth or network restrictions.

---

### Q4: What is `liveness` and `readiness`?

* Liveness → app alive or needs restart
* Readiness → app ready to receive traffic

---

### Q5: Can we add custom endpoints?

Yes, using `@Endpoint`.

---

## 10. Key Takeaways

* Actuator exposes **health, metrics, env, logs, mappings**

* Very important for **production microservices**

* Integrates with **Prometheus, Kubernetes, AWS**

* Supports **custom endpoints**

* Must be **secured carefully**

---
