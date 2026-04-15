# How Pointcut Works in Spring AOP

## 1. Overview

Pointcuts are the filter that decides **which methods a Spring aspect should apply to**.

This file explains how pointcuts participate in the AOP flow, from the client call to the final advice execution.

---

## 2. High-Level AOP Flow

```text
Client → Proxy → Pointcut Match → Advice → Target Method
```

That flow is the heart of Spring AOP.

The pointcut is checked first. If the method matches, the advice runs around the target method.

---

## 3. Step-by-Step Flow

### Step 1: A bean is created

Spring creates the target bean as usual.

### Step 2: Spring creates a proxy

If the bean has aspects applied to it, Spring returns a proxy instead of the raw object.

### Step 3: Client calls the method

The method call goes to the proxy.

### Step 4: Proxy checks the pointcut

The proxy compares the method against the pointcut expression.

### Step 5: If it matches, advice runs

The advice executes before, after, or around the target method depending on the annotation used.

### Step 6: The target method executes

The business method runs if the advice allows it.

---

## 4. What Happens When the Pointcut Does Not Match?

If the method does not match the pointcut:

* no advice is applied
* the proxy passes the call straight through
* the target method executes normally

This is why pointcuts are important: they decide the scope of the aspect.

---

## 5. Example Flow with a Logging Aspect

```java
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore() {
        System.out.println("Before service method");
    }
}
```

When a service method is called:

1. Proxy receives the call
2. Pointcut checks whether the method is in `com.example.service`
3. If yes, `@Before` advice runs
4. Target method executes

---

## 6. Matching by Pattern

Pointcuts work by pattern matching.

Examples:

* class and package patterns
* method name patterns
* argument patterns
* annotation-based matching

This means the pointcut is essentially a rule engine for method selection.

---

## 7. How Advice and Pointcut Fit Together

The pointcut decides **where**.

The advice decides **what to do**.

Example:

```java
@Before("serviceMethods()")
public void log() {
    System.out.println("Running before service call");
}
```

Here:

* `serviceMethods()` is the pointcut
* `log()` is the advice

They work together inside one aspect.

---

## 8. Why Spring Uses Proxies

Spring does not usually modify your classes directly.

Instead, it wraps them in a proxy so it can intercept method calls and check whether the pointcut matches.

That is why AOP in Spring is:

* lightweight
* runtime-based
* method-oriented

---

## 9. Self-Invocation Limitation

If one method inside a class calls another method in the same class, the call may bypass the proxy.

Example:

```java
public void outer() {
    inner();
}

public void inner() {
}
```

If `outer()` calls `inner()` directly, the pointcut may not be applied to `inner()` because the call does not pass through the proxy.

This is one of the most common Spring AOP gotchas.

---

## 10. AOP Flow and Transaction Example

Spring transactions often work through the same flow.

```text
Call service method
→ proxy checks transaction-related pointcut
→ transaction begins
→ method executes
→ commit or rollback
```

This is how `@Transactional` works conceptually.

---

## 11. Mental Model

Think of the proxy as a gatekeeper.

The pointcut asks:

> Does this method qualify?

If yes, the advice is allowed to run.

If no, the call goes directly to the target method.

---

## 12. Common Mistakes

* expecting pointcuts to run on every method automatically

* forgetting the proxy layer exists

* assuming self-invocation is intercepted

* using pointcuts that are too broad

* mixing the pointcut logic with business code

---

## 13. Interview Questions

### Q1. What is the role of a pointcut in AOP?

It selects which methods should be intercepted.

---

### Q2. What happens if a method does not match the pointcut?

The advice does not run, and the method executes normally.

---

### Q3. Why does Spring use proxies in AOP?

To intercept method calls and apply advice at runtime.

---

### Q4. What is self-invocation in Spring AOP?

When one method in the same class calls another method directly, bypassing the proxy.

---

## Key Takeaways

* Pointcuts decide where advice applies

* Spring AOP uses proxies to intercept calls

* The flow is client → proxy → pointcut check → advice → target method

* If the pointcut does not match, the method runs normally

* Self-invocation can bypass Spring AOP interception

---