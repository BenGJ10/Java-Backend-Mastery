# Reflection in Java (In-Depth)

## 1. What is Reflection?

**Reflection** is a Java feature that allows a program to inspect and manipulate classes, methods, fields, constructors, and annotations **at runtime**.

Normally, Java code is resolved at compile time.
With reflection, you can do things dynamically, such as:

* inspect class metadata
* list methods and fields
* invoke methods by name
* create objects dynamically
* read/write fields (even private ones with permission)

Reflection API is mainly present in:

* `java.lang`
* `java.lang.reflect`

---

## 2. Why Reflection is Needed

Reflection is powerful when type or behavior is not fully known at compile time.

Common use cases:

1. Frameworks (Spring, Hibernate, JUnit, Jackson)
2. Dependency injection and IoC containers
3. Serialization/deserialization
4. Annotation processing at runtime
5. Plugin systems
6. Testing private/internal components

Without reflection, many modern Java frameworks would require huge manual boilerplate.

---

## 3. Core Reflection Classes

| Type | Purpose |
| ---- | ------- |
| `Class<?>` | Holds class metadata |
| `Field` | Represents class member variables |
| `Method` | Represents methods |
| `Constructor<?>` | Represents constructors |
| `Modifier` | Utility to inspect access modifiers |
| `Array` | Create/read arrays dynamically |
| `Proxy` | Create dynamic proxy classes |

---

## 4. Getting `Class` Object

Reflection starts with a `Class<?>` object.

### 4.1 Using `.class`

```java
Class<String> c1 = String.class;
```

### 4.2 Using object instance

```java
String s = "hello";
Class<?> c2 = s.getClass();
```

### 4.3 Using fully qualified class name

```java
Class<?> c3 = Class.forName("java.util.ArrayList");
```

---

## 5. Inspecting Class Metadata

```java
Class<?> clazz = Class.forName("java.util.ArrayList");

System.out.println(clazz.getName());          // java.util.ArrayList
System.out.println(clazz.getSimpleName());    // ArrayList
System.out.println(clazz.getPackageName());   // java.util
System.out.println(clazz.getSuperclass());    // class java.util.AbstractList
```

Check interfaces:

```java
for (Class<?> i : clazz.getInterfaces()) {
	System.out.println(i.getName());
}
```

---

## 6. Inspecting Fields

### Difference

* `getFields()` -> only `public` fields (including inherited)
* `getDeclaredFields()` -> all fields declared in the class (including `private`)

Example:

```java
class User {
	private String name;
	public int age;
}

Class<User> clazz = User.class;

for (Field f : clazz.getDeclaredFields()) {
	System.out.println(f.getName() + " : " + f.getType().getSimpleName());
}
```

Read/Write private field:

```java
User u = new User();
Field nameField = User.class.getDeclaredField("name");
nameField.setAccessible(true);
nameField.set(u, "Ben");
System.out.println(nameField.get(u));
```

---

## 7. Inspecting Methods

### Difference

* `getMethods()` -> public methods including inherited ones
* `getDeclaredMethods()` -> all methods declared in class

Example:

```java
class Calculator {
	private int square(int x) { return x * x; }
}

Method m = Calculator.class.getDeclaredMethod("square", int.class);
m.setAccessible(true);

Calculator c = new Calculator();
Object result = m.invoke(c, 5);
System.out.println(result); // 25
```

---

## 8. Inspecting Constructors and Dynamic Object Creation

```java
class Student {
	private String name;

	public Student() {
		this.name = "Unknown";
	}

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

Constructor<Student> ctor = Student.class.getConstructor(String.class);
Student s = ctor.newInstance("Ava");
System.out.println(s.getName());
```

Note:

* `Class.newInstance()` is old style and discouraged
* prefer `getDeclaredConstructor(...).newInstance()`

---

## 9. Access Modifiers and `Modifier` Utility

```java
Field field = Student.class.getDeclaredField("name");
int mod = field.getModifiers();

System.out.println(Modifier.isPrivate(mod));
System.out.println(Modifier.toString(mod)); // private
```

You can check whether a class/member is:

* `public`
* `private`
* `protected`
* `static`
* `final`
* `abstract`

---

## 10. Reflection with Annotations

Reflection is often combined with runtime annotations.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface JsonField {
	String value();
}

class Product {
	@JsonField("product_name")
	private String name;
}
```

Read annotation:

```java
Field f = Product.class.getDeclaredField("name");
if (f.isAnnotationPresent(JsonField.class)) {
	JsonField ann = f.getAnnotation(JsonField.class);
	System.out.println(ann.value());
}
```

This is the basic mechanism used by frameworks for mapping and configuration.

---

## 11. Reflection and Generics (Important Limitation)

Java generics use **type erasure**, so runtime type details are limited.

Example:

```java
List<String> a = new ArrayList<>();
List<Integer> b = new ArrayList<>();

System.out.println(a.getClass() == b.getClass()); // true
```

Both become `ArrayList` at runtime.

You can inspect generic signatures in some places (like fields and method parameters) using:

* `getGenericType()`
* `getGenericReturnType()`

---

## 12. Dynamic Proxies (Built on Reflection)

Java can generate proxy objects at runtime for interfaces.

Commonly used in:

* Spring AOP
* transaction management
* logging wrappers

Example:

```java
interface Greeting {
	String hello(String name);
}

Greeting g = (Greeting) Proxy.newProxyInstance(
		Greeting.class.getClassLoader(),
		new Class<?>[]{Greeting.class},
		(proxy, method, args) -> "Hello " + args[0]
);

System.out.println(g.hello("Ben"));
```

---

## 13. Real-World Framework Usage

### Spring

* scans classes for `@Component`, `@Service`, `@Controller`
* creates and injects beans dynamically
* invokes methods reflectively where needed

### Hibernate/JPA

* reads `@Entity`, `@Table`, `@Column`
* accesses entity fields/getters to map database rows

### JUnit

* detects methods annotated with `@Test`
* invokes test methods dynamically

### Jackson

* reads fields/getters/setters to map JSON <-> Java objects

---

## 14. Advantages of Reflection

* very flexible and dynamic
* enables framework-level automation
* reduces boilerplate code
* supports runtime discovery and plugin architectures

---

## 15. Drawbacks and Risks

1. Slower than direct invocation
2. Breaks encapsulation when forcing private access
3. Harder to read, debug, and refactor safely
4. Runtime errors instead of compile-time safety
5. Security concerns in restricted environments

Use reflection where needed, not everywhere.

---

## 16. Performance Considerations

Reflection has overhead due to metadata lookup and dynamic dispatch.

Tips:

* cache `Method`, `Field`, `Constructor` objects
* avoid reflection inside tight loops
* prefer direct calls for hot paths
* use `MethodHandle` API for high-performance dynamic invocation when appropriate

---

## 17. Security and Modern Java Notes

Calling `setAccessible(true)` may fail under strong encapsulation rules (especially with Java modules).

In Java 9+, module boundaries can restrict deep reflection unless modules are opened appropriately.

In production code:

* avoid unnecessary reflective access to internals
* prefer public APIs
* use module `opens` settings when truly required

---

## 18. Best Practices

* keep reflective logic centralized in utility/helper layer
* validate names and signatures before invocation
* handle checked exceptions carefully
* prefer annotations + reflection for declarative behavior
* document reflective code clearly for maintainability

---

## 19. Common Exceptions in Reflection

| Exception | When it happens |
| --------- | --------------- |
| `ClassNotFoundException` | class name not found in classpath |
| `NoSuchMethodException` | method/constructor does not exist |
| `NoSuchFieldException` | field does not exist |
| `IllegalAccessException` | insufficient access permissions |
| `InvocationTargetException` | underlying invoked method threw exception |
| `InstantiationException` | cannot instantiate abstract/interface class |

---

## 20. Reflection vs Direct Access

| Aspect | Direct Code | Reflection |
| ------ | ----------- | ---------- |
| Type safety | Compile-time strong | Runtime checks |
| Speed | Fast | Slower |
| Flexibility | Low | Very high |
| Readability | High | Medium/low |
| Framework use | Limited | Essential |

---

## 21. Interview Questions

### Q1. What is reflection in Java?

A runtime mechanism to inspect and manipulate class metadata, fields, methods, constructors, and annotations.

---

### Q2. Difference between `getMethods()` and `getDeclaredMethods()`?

* `getMethods()` -> public methods including inherited
* `getDeclaredMethods()` -> all methods declared in current class only

---

### Q3. Is reflection slow?

Yes, generally slower than direct calls due to runtime lookup and invocation overhead, but acceptable for framework/setup paths.

---

### Q4. Can reflection access private fields?

Yes, using `setAccessible(true)`, subject to security and module restrictions.

---

### Q5. Where is reflection used in real projects?

Spring IoC, Hibernate ORM, JUnit test discovery, Jackson serialization, plugin systems, and AOP proxies.

---

## Key Takeaways

* Reflection enables runtime inspection and dynamic behavior in Java.

* It is foundational for major frameworks like Spring, Hibernate, JUnit, and Jackson.

* Powerful but should be used carefully due to performance, safety, and maintainability trade-offs.

* Combine annotations + reflection for clean declarative designs.

* Prefer direct code for performance-critical paths and reflection for infrastructure/framework logic.

---