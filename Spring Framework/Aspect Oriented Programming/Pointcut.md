# Pointcut in Spring AOP

## 1. What Is a Pointcut?

A **pointcut** is an expression that tells Spring **which methods should be intercepted** by an aspect.

In simple words:

> A pointcut selects the join points where advice should run.

The pointcut does **not** contain the logic itself. It only defines the **matching rule**.

---

## 2. Why Pointcuts Are Needed

Without pointcuts, an aspect would apply everywhere, which is not useful.

Pointcuts let you target only the methods that matter.

For example, you may want to apply logging only to:

* methods in the service package
* methods with a certain annotation
* methods whose names start with `get`
* methods that accept certain arguments

This keeps aspects focused and prevents accidental interception.

---

## 3. Pointcut Basic Idea

```text
Pointcut = selection rule
Advice = action to execute
Aspect = pointcut + advice
```

The pointcut answers:

> Where should the advice run?

---

## 4. Pointcut Expression Example

```java
@Pointcut("execution(* com.example.service.*.*(..))")
public void serviceMethods() {}
```

This means:

* match any return type
* in the `com.example.service` package
* any class
* any method
* with any number of arguments

---

## 5. Anatomy of an `execution(...)` Pointcut

The most common Spring AOP pointcut designator is `execution`.

Example:

```java
execution(* com.example.service.*.*(..))
```

Breakdown:

* `*` → any return type
* `com.example.service` → package
* `*` → any class name
* `*` → any method name
* `(..)` → any number of arguments

---

## 6. Common Pointcut Patterns

### Match all methods in a package

```java
execution(* com.example.service.*.*(..))
```

### Match a specific method name

```java
execution(* com.example.service.OrderService.placeOrder(..))
```

### Match methods starting with a prefix

```java
execution(* com.example.service.*.get*(..))
```

### Match methods with no arguments

```java
execution(* com.example.service.*.*())
```

---

## 7. Named Pointcuts

Spring allows you to define a reusable pointcut method.

```java
@Aspect
public class LoggingAspect {

    @Pointcut("execution(* com.example.service.*.*(..))")
    public void serviceLayer() {}

    @Before("serviceLayer()")
    public void logBefore() {
        System.out.println("Service method called");
    }
}
```

Named pointcuts improve:

* readability
* reuse
* maintainability

---

## 8. Multiple Pointcut Conditions

You can combine pointcuts.

### Example with `&&`

```java
@Pointcut("execution(* com.example.service.*.*(..))")
public void serviceMethods() {}

@Pointcut("within(com.example..*)")
public void inApplication() {}

@Before("serviceMethods() && inApplication()")
public void beforeServiceCall() {
    System.out.println("Intercepted service call");
}
```

Other operators:

* `&&` → and
* `||` → or
* `!` → not

---

## 9. Common Pointcut Designators

### `execution`

Matches method execution patterns.

### `within`

Matches all join points inside a type or package.

### `this`

Matches proxy object type.

### `target`

Matches actual target object type.

### `args`

Matches method arguments.

### `@annotation`

Matches methods annotated with a specific annotation.

These are the most useful pointcut designators in Spring AOP.

---

## 10. Pointcut by Annotation

You can target methods marked with a custom annotation.

```java
@Pointcut("@annotation(com.example.LogExecution)")
public void loggableMethods() {}
```

This is useful when you want to opt in only specific methods.

---

## 11. Good Pointcut Practices

* keep pointcuts narrow and intentional

* use named pointcuts for reuse

* prefer package- or annotation-based targeting when possible

* avoid extremely broad expressions unless you really need them

Good pointcuts make aspects predictable.

---

## 12. Common Mistakes

* confusing the pointcut with the advice

* writing expressions that match too many methods

* duplicating the same long expression in many places

* forgetting that Spring AOP works mainly with method execution

* assuming pointcuts intercept internal self-invocation automatically

---

## 13. Interview Questions

### Q1. What is a pointcut?

A pointcut selects which methods should be intercepted by advice.

---

### Q2. What is the most common pointcut designator in Spring AOP?

`execution(...)`.

---

### Q3. Why use named pointcuts?

To improve reuse and readability.

---

### Q4. Can pointcuts be combined?

Yes, using `&&`, `||`, and `!`.

---

## Key Takeaways

* A pointcut is a method selection rule

* It tells Spring where advice should run

* `execution(...)` is the most common style

* Named pointcuts make aspects cleaner

* Good pointcuts keep AOP precise and maintainable

---