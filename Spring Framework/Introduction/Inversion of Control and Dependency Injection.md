# Dependency Injection and Inversion of Control Principles

## 1. The Core Problem Before Spring

In traditional Java applications, objects **create their own dependencies**.

### Example (Tightly Coupled Code)

```java
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    private Engine engine = new Engine(); // tightly coupled

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

### Problems with this approach:

* Tight coupling (`Car` depends directly on `Engine`)
* Hard to change implementations
* Difficult to test (cannot mock `Engine`)
* Violates **SOLID principles** (especially Dependency Inversion)

Spring solves this using **IoC** and **DI**.

---

## 2. Inversion of Control (IoC)

**Inversion of Control (IoC)** is a design principle where:

> The control of object creation and dependency management is transferred
> from the application code to a framework/container.

In simple words:

* **You do not create objects**
* **The framework creates and manages them**

Before IoC:

```
Your code → creates dependencies
```

After IoC:

```
Spring Container → creates & injects dependencies
```

---

### What Is Being “Inverted”?

The inversion happens in **control flow**:

| Before IoC                    | After IoC                          |
| ----------------------------- | ---------------------------------- |
| Code controls object creation | Framework controls object creation |
| `new` keyword everywhere      | Container-managed objects          |
| Tight coupling                | Loose coupling                     |

IoC is a **concept**, not a tool.

---

## 3. Dependency Injection (DI)

**Dependency Injection (DI)** is a **design pattern** used to implement IoC.

> Dependencies are **provided (injected)** to an object
> instead of the object creating them itself.

DI answers the question:

> *How does IoC actually happen?*

---

### IoC vs DI (Very Important)

| IoC              | DI                            |
| ---------------- | ----------------------------- |
| Design principle | Design pattern                |
| What (concept)   | How (implementation)          |
| Who controls     | How dependencies are supplied |

Spring implements **IoC using DI**.

---

## 4. Dependency Injection Without Spring

### Constructor Injection (Manual)

```java
class Car {
    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }
}
```

```java
Engine engine = new Engine();
Car car = new Car(engine);
```

This is **DI**, but **not IoC** (you still control object creation).

Spring does both DI **and** IoC.

---

## 5. Dependency Injection Using Spring

Spring Container:

* Creates objects (beans)
* Injects dependencies
* Manages lifecycle

---

### Basic Spring DI Example

### Engine.java

```java
@Component
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}
```

### Car.java

```java
@Component
class Car {

    private Engine engine;

    @Autowired
    Car(Engine engine) {
        this.engine = engine;
    }

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

### What Happens Internally?

1. Spring scans classes
2. Creates `Engine` bean
3. Creates `Car` bean
4. Injects `Engine` into `Car`
5. Manages lifecycle

Your code never calls `new Engine()`.

---

## 6. Types of Dependency Injection in Spring

Spring supports **three types** of DI.

### 6.1 Constructor Injection

```java
@Component
class Car {

    private final Engine engine;

    @Autowired
    Car(Engine engine) {
        this.engine = engine;
    }
}
```

### Why Constructor Injection Is Best:

* Makes dependencies mandatory
* Ensures immutability
* Easier testing
* Prevents partially initialized objects

This is the **recommended approach**.

---

### 6.2 Setter Injection

```java
@Component
class Car {

    private Engine engine;

    @Autowired
    void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

### Use When:

* Dependency is optional
* You need to change dependency later

---

### 6.3 Field Injection (Not Recommended)

```java
@Component
class Car {

    @Autowired
    private Engine engine;
}
```

### Why Avoid Field Injection:

* Hard to test
* Hides dependencies
* Breaks immutability
* Requires reflection

Use only for quick demos, not production.

---

## 7. Loose Coupling with Interfaces

DI works best with **interfaces**.

### Example Without Interface (Tight Coupling)

```java
class Car {
    private PetrolEngine engine;
}
```



### Example With Interface (Loose Coupling)

```java
interface Engine {
    void start();
}
```

```java
@Component
class PetrolEngine implements Engine {
    public void start() {
        System.out.println("Petrol engine started");
    }
}
```

```java
@Component
class Car {
    private final Engine engine;

    @Autowired
    Car(Engine engine) {
        this.engine = engine;
    }
}
```

Now you can switch implementations without changing `Car`.

---

## 8. IoC Container in Spring

The **IoC Container** is responsible for:

* Instantiating beans
* Injecting dependencies
* Managing bean lifecycle

Common container:

```java
ApplicationContext
```

Example:

```java
ApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class);

Car car = context.getBean(Car.class);
```

---

## 9. Real-World Benefits of DI + IoC

* Clean architecture
* Testable code (easy mocking)
* Loose coupling
* Scalable systems
* Easy refactoring
* Better maintainability

Frameworks relying heavily on DI:

* Spring Boot
* Spring Security
* Spring Data
* Spring Cloud

---

## 10. Interview Questions

### Q1. What is IoC?

A principle where control of object creation is inverted from application to framework.

---

### Q2. What is DI?

A design pattern where dependencies are injected into an object rather than created by it.

---

### Q3. Difference between IoC and DI?

IoC is the concept; DI is the implementation.

---

### Q4. Which DI type is best?

Constructor Injection.

---

### Q5. Why is Spring DI better than manual DI?

Spring handles object creation, lifecycle, and dependency resolution automatically.

---

### Q6. Why avoid field injection?

Hard to test, hides dependencies, breaks immutability.

---

### Q7. How does Spring resolve dependencies?

By type, qualifier, and primary bean.

---

## Key Takeaways

* IoC shifts control of object creation to Spring

* DI is the mechanism that enables IoC

* Spring manages object lifecycle and dependencies

* Constructor injection is the best practice

* Interfaces + DI = loose coupling

* IoC + DI are the foundation of Spring Framework

---
