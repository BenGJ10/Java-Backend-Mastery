# Annotations in Java

## 1. What Are Annotations?

Annotations in Java are **metadata** added to code.
They do **not directly affect program logic**, but provide information to:

* **Compiler**
* **JVM**
* **Frameworks** (Spring, Hibernate, JPA)
* **Build tools** (Maven, Gradle)

Annotations are written using `@`.

Example:

```java
@Override
public String toString() { ... }
```

---

## 2. Why Are Annotations Used?

### Key Purposes:

1. **Provide metadata**
   e.g., `@Deprecated`, `@SuppressWarnings`

2. **Compiler instructions**
   e.g., `@Override`, `@FunctionalInterface`

3. **Runtime processing with Reflection**
   Used by Spring, JPA, Hibernate, Jackson

4. **Code generation**
   Lombok (`@Getter`, `@Setter`)

5. **Validation**
   e.g., Jakarta Validation (`@NotNull`, `@Min`)

---

## 3. Built-In Java Annotations

### 3.1 `@Override`

Ensures that a method overrides a parent method.

```java
@Override
void run() { }
```

If method signature doesn't match parent method → compiler error.

---

### 3.2 `@Deprecated`

Marks API as outdated.

```java
@Deprecated
void oldMethod() { }
```

Warnings appear if used.

---

### 3.3 `@SuppressWarnings`

Suppress compiler warnings.

```java
@SuppressWarnings("unchecked")
void test() { }
```

---

### 3.4 `@FunctionalInterface`

Ensures interface has exactly **one abstract method**.

```java
@FunctionalInterface
interface Operation {
    int apply(int x, int y);
}
```

---

### 3.5 `@SafeVarargs`

Suppresses unchecked warnings for varargs of generics.

```java
@SafeVarargs
static <T> void print(T... items) { }
```

---

### 3.6 `@Retention`

Specifies how long annotation is retained.

```java
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnno { }
```

Types:

| Retention Policy | Meaning                               |
| ---------------- | ------------------------------------- |
| `SOURCE`         | Removed during compilation            |
| `CLASS`          | Stored in .class file (default)       |
| `RUNTIME`        | Available during runtime (reflection) |

---

### 3.7 `@Target`

Specifies where annotation can be applied.

```java
@Target(ElementType.METHOD)
@interface MyAnno { }
```

Targets include:

* TYPE
* METHOD
* FIELD
* PARAMETER
* CONSTRUCTOR
* LOCAL_VARIABLE
* PACKAGE

---

### 3.8 `@Documented`

Marks that an annotation should appear in Javadoc.

---

### 3.9 `@Inherited`

Child classes inherit the annotation.

---

## 4. Custom Annotations

You can create your own annotations using `@interface`.

### Example:

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface RunTest {
    int times() default 1;
}
```

Usage:

```java
class Demo {
    @RunTest(times = 3)
    void test() { }
}
```

---

## 5. Processing Custom Annotations (Reflection)

Example of reading annotation at runtime:

```java
Method[] methods = Demo.class.getDeclaredMethods();

for (Method m : methods) {
    if (m.isAnnotationPresent(RunTest.class)) {
        RunTest rt = m.getAnnotation(RunTest.class);
        System.out.println("Run " + m.getName() + " " + rt.times() + " times");
    }
}
```

---

## 6. Meta-Annotations (Annotations that apply to other annotations)

| Annotation    | Purpose                                       |
| ------------- | --------------------------------------------- |
| `@Retention`  | Specifies how long annotation stays           |
| `@Target`     | Where annotation can be applied               |
| `@Documented` | Included in Javadoc                           |
| `@Inherited`  | Inherited by subclasses                       |
| `@Repeatable` | Same annotation can be applied multiple times |

Example of repeatable:

```java
@Repeatable(Tags.class)
@interface Tag {
    String name();
}

@interface Tags {
    Tag[] value();
}
```

---

## 7. Annotations in Popular Frameworks

### Spring

```java
@Component
@Service
@RestController
@Autowired
@RequestMapping("/api")
```

Used for dependency injection and component scanning.

---

### Hibernate/JPA

```java
@Entity
@Id
@GeneratedValue
@Column(name = "user_id")
```

Used for ORM mapping.

---

## 8. Runtime vs Compile-Time Annotations

| Annotation   | Retention          | Example                    |
| ------------ | ------------------ | -------------------------- |
| Compile-time | `SOURCE` / `CLASS` | `@Override`, `@Deprecated` |
| Runtime      | `RUNTIME`          | `@Entity`, `@Autowired`    |

Frameworks require annotations available at **runtime**.

---

## 9. Important Interview Questions

### Q1: What is an annotation?

Metadata that provides information to compiler/JVM/tools.

### Q2: Difference between `@Override` and `@FunctionalInterface`?

* `@Override` checks method overriding.
* `@FunctionalInterface` ensures single abstract method.

### Q3: What is RetentionPolicy?

Specifies how long annotation is kept (source, class, runtime).

### Q4: Can an annotation have methods?

Yes—called **annotation elements**.

### Q5: Can annotations have default values?

Yes.

```java
int value() default 10;
```

### Q6: Can annotations extend other annotations?

No. But they can use `@Repeatable` and `@Inherited`.

---

## Key Takeaways

* Annotations add metadata to code.

* They influence **compilers, tools, and frameworks**.

* Built-in annotations: `@Override`, `@Deprecated`, `@SuppressWarnings`.

* Meta-annotations: `@Retention`, `@Target`, `@Inherited`.

* Custom annotations are created with `@interface`.

* Widely used in Spring, JPA, Jackson, and Lombok.

---
