# Spring Bean Lifecycle

The **Spring Bean Lifecycle** describes the **complete journey of a bean** from the moment Spring becomes aware of it until the moment it is destroyed.

Understanding this topic is **extremely important** because it explains:

* When dependencies are injected
* When initialization logic should run
* When cleanup should happen
* How Spring internally manages beans

---

## 1. What Is a Spring Bean Lifecycle?

The **bean lifecycle** is the sequence of steps that Spring follows to:

1. Discover a bean
2. Create the bean object
3. Inject its dependencies
4. Initialize it
5. Make it available for use
6. Destroy it during shutdown

In short:

> **Instantiation → Dependency Injection → Initialization → Usage → Destruction**

---

## 2. High-Level Lifecycle Flow

For a typical **singleton bean**, the lifecycle looks like this:

```
1. Bean Definition Loaded
2. Bean Instantiated
3. Dependencies Injected
4. Aware Interfaces Called
5. BeanPostProcessor (Before Init)
6. Initialization Methods
7. BeanPostProcessor (After Init)
8. Bean Ready for Use
9. Application Shutdown
10. Destruction Callbacks
```

Each of these stages has a **specific purpose**.

---

## 3. Bean Definition Phase

Before any object is created, Spring first **reads bean metadata**.

Sources of bean definitions:

* `@Component`, `@Service`, `@Repository`
* `@Bean` methods
* Auto-configuration (Spring Boot)
* XML (legacy)

At this stage:

* No objects exist
* Spring only knows *what* beans need to be created and *how*

This information is stored internally as **BeanDefinition** objects.

---

## 4. Bean Instantiation

In this phase, Spring **creates the actual Java object**.

How instantiation happens:

* Constructor injection (preferred)
* Default constructor (if no explicit constructor)

Example:

```java
@Component
public class MyBean {

    public MyBean() {
        System.out.println("1. Bean instantiated");
    }
}
```

Important notes:

* Only object creation happens here
* No dependencies are injected yet
* Avoid heavy logic in constructors

---

## 5. Dependency Injection Phase

After instantiation, Spring **injects dependencies**.

Injection methods:

* Constructor injection
* Setter injection
* Field injection

Example:

```java
@Component
public class MyBean {

    @Autowired
    private Environment environment;
}
```

At this stage:

* All required dependencies are resolved
* Circular dependencies are detected (especially with constructors)
* Bean is now fully wired, but not initialized

---

## 6. Aware Interfaces Phase

If a bean implements certain **Aware interfaces**, Spring provides internal context information.

Common interfaces:

* `BeanNameAware` → gives bean name
* `BeanFactoryAware` → gives BeanFactory
* `ApplicationContextAware` → gives ApplicationContext
* `EnvironmentAware` → gives environment properties

Example:

```java
@Component
public class MyBean implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name: " + name);
    }
}
```

This phase allows beans to **interact with the container itself**.

---

## 7. BeanPostProcessor (Before Initialization)

Before initialization callbacks run, Spring invokes:

```
postProcessBeforeInitialization()
```

Purpose:

* Modify bean instances
* Prepare beans for initialization
* Process annotations like `@PostConstruct`

Important:

* This is an **advanced extension point**
* Used internally for:

  * Dependency injection
  * AOP
  * Proxy creation

You usually don’t implement this unless building frameworks.

---

## 8. Initialization Phase 

This is where **custom initialization logic** should be placed.

Spring provides **three mechanisms**, executed in this order:

---

### 8.1 @PostConstruct (Recommended)

```java
@Component
public class MyBean {

    @PostConstruct
    public void init() {
        System.out.println("Initialization logic");
    }
}
```

Characteristics:

* Called after dependency injection
* Modern and standard
* Preferred approach

---

### 8.2 InitializingBean Interface (Legacy)

```java
@Component
public class MyBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet()");
    }
}
```

Drawback:

* Couples your code to Spring API

---

### 8.3 initMethod in @Bean Definition

```java
@Bean(initMethod = "init")
public MyBean myBean() {
    return new MyBean();
}
```

Used mostly in Java configuration classes.

---

## 9. BeanPostProcessor (After Initialization)

After initialization completes, Spring invokes:

```
postProcessAfterInitialization()
```

At this stage:

* Bean may be wrapped with a **proxy**
* This is how:

  * `@Transactional`
  * `@Async`
  * `@Secured`

work internally.

The object returned here is the **actual bean instance** used by the application.

---

## 10. Bean Is Ready for Use

Now the bean is:

* Fully initialized
* Dependencies injected
* Proxies applied (if any)

The bean can safely be used in:

* Controllers
* Services
* Scheduled tasks
* Application logic

---

## 11. Destruction Phase (On Shutdown)

When the application shuts down:

* ApplicationContext is closed
* Singleton beans are destroyed in reverse order

This is where cleanup logic runs.

---

### 11.1 @PreDestroy (Recommended)

```java
@Component
public class MyBean {

    @PreDestroy
    public void cleanup() {
        System.out.println("Cleanup before destroy");
    }
}
```

Used for:

* Closing resources
* Releasing connections
* Stopping threads

---

### 11.2 DisposableBean Interface (Legacy)

```java
@Component
public class MyBean implements DisposableBean {

    @Override
    public void destroy() {
        System.out.println("destroy()");
    }
}
```

Again, couples code to Spring.

---

### 11.3 destroyMethod in @Bean

```java
@Bean(destroyMethod = "close")
public DataSource dataSource() {
    return new DataSource();
}
```

---

## 12. Full Lifecycle Example (Execution Order)

```java
@Component
public class LifeCycleDemo {

    public LifeCycleDemo() {
        System.out.println("1. Constructor");
    }

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        System.out.println("2. @PostConstruct");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("3. @PreDestroy");
    }
}
```

Output order:

```
1. Constructor
Dependency Injection
2. @PostConstruct
Application Running
Application Shutdown
3. @PreDestroy
```

---

## 13. Bean Lifecycle vs Scope

| Scope       | Full Lifecycle Managed?  |
| ----------- | ------------------------ |
| singleton   | Yes                      |
| prototype   | No (no destroy callback) |
| request     | Yes                      |
| session     | Yes                      |
| application | Yes                      |

Important:

> Spring does **not manage destruction** of prototype beans.

---

## 14. Common Mistakes

* Heavy logic in constructors
* Using field injection without understanding lifecycle
* Expecting `@PreDestroy` for prototype beans
* Opening resources without closing them
* Creating beans using `new`

---

## 15. Interview Questions

### Q1. What is the Spring Bean Lifecycle?

The sequence of steps from bean creation to destruction managed by Spring.

---

### Q2. When is @PostConstruct called?

After dependency injection but before the bean is ready for use.

---

### Q3. When is @PreDestroy called?

Before the bean is destroyed during ApplicationContext shutdown.

---

### Q4. What is BeanPostProcessor?

A hook that allows modification of beans before and after initialization.

---

### Q5. Does Spring manage prototype bean destruction?

No.

---

## Key Takeaways

* Bean lifecycle defines **how Spring manages objects**

* Lifecycle phases are well-defined and predictable

* Use `@PostConstruct` for initialization

* Use `@PreDestroy` for cleanup

* Avoid logic in constructors

* Understanding lifecycle prevents subtle bugs and memory leaks

---
