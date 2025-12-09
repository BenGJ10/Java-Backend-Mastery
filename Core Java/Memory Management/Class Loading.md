# Java Class Loading

## 1. Introduction

Java uses a **dynamic class loading mechanism**, meaning classes are loaded into memory **only when required**.
This process is handled by a subsystem inside the JVM called the **Class Loader Subsystem**.

Class loading is essential because it:

* Converts `.class` bytecode into JVM runtime objects
* Ensures classes are loaded only once
* Provides security through class isolation
* Supports dynamic loading (plugins, frameworks, reflection)

---

## 2. JVM Class Loader Subsystem

The Class Loader Subsystem performs three major tasks:

1. **Loading**
2. **Linking**
3. **Initialization**

```
           +-----------------------------+
           |   Class Loader Subsystem    |
+----------+-----------------------------+----------+
| Loading | Linking (Verify, Prepare, Resolve) | Initialization |
+----------+------------------------------------+---------------+
```

Let’s go deep into each.

---

## 3. Class Loading Phases

### 3.1 Loading Phase

The JVM performs three tasks here:

1. **Finding the class file (.class)**
2. **Reading the bytecode**
3. **Creating a Class object in the Method Area**

Each loaded class is stored as a **java.lang.Class** object inside the JVM.

#### Example (Reflection):

```java
Class<?> clazz = Class.forName("com.example.Test");
```

This triggers class loading.

---

## 4. Linking Phase

Linking has **three subphases**:

### 4.1 Verification

Ensures bytecode is valid and safe:

* No illegal memory access
* No stack overflow
* Correct data types
* Follows JVM specifications

This ensures security and prevents JVM crashes.


### 4.2 Preparation

Allocates memory for:

* Static variables
* Default values (0, null, false)

Example:

```java
static int x = 10;
```

During Preparation:

* Memory allocated for `x`
* Default value given: `x = 0`

The actual value `10` is assigned in the **initialization phase**, not here.


### 4.3 Resolution (Optional)

Converts symbolic references into direct references.

Symbolic references appear in class files as strings:

```
java/lang/String     → resolved to actual class reference
methodName           → resolved to method pointer
```

---

## 5. Initialization Phase

This is when:

* Static blocks run
* Static variables get assigned final values

### Example:

```java
static int a = 10;

static {
    System.out.println("Static block executed");
}
```

Order of initialization:

1. Default assignment (Preparation)
2. Explicit assignment (Initialization)
3. Static block execution

---

## 6. Class Loaders in Java

Java uses multiple class loaders arranged hierarchically.

```
Bootstrap ClassLoader
        ↑
Extension (Platform) ClassLoader
        ↑
Application (System) ClassLoader
```

### 6.1 Bootstrap ClassLoader

* Part of JVM (native code, not Java code)
* Loads **core Java classes** (`rt.jar` or modules)

Examples:

* `java.lang.*`
* `java.util.*`
* `java.io.*`

### 6.2 Extension / Platform ClassLoader

* Loads classes from `JAVA_HOME/lib/ext`
* or JDK 9+: from platform modules

Examples:

* Java Cryptography Extensions
* Optional modules


### 6.3 Application / System ClassLoader

* Loads classes from classpath:

```
classpath → . , target/classes , lib/*.jar
```

This is the class loader that loads your project code.

Example:

```java
ClassLoader loader = MyClass.class.getClassLoader();
```

### 6.4 Custom Class Loaders

Developers can create custom loaders for:

* Plugin systems
* Web containers (Tomcat)
* OSGi bundles
* On-the-fly code generation

Example:

```java
class MyLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) {
        // custom logic
    }
}
```

---

## 7. Parent Delegation Model (Very Important)

Java’s class loaders follow a **parent delegation model**:

1. Child loader delegates request to parent
2. Parent tries to load
3. If parent fails → child tries

### Benefits:

* Prevents core Java classes from being overridden
* Ensures stability and security

Flow:

```
Application Loader
     ↓ delegates
Platform Loader
     ↓ delegates
Bootstrap Loader
```

If Bootstrap cannot load → moves upward.

---

## 8. Example – Understanding Delegation

```java
String s = "Java";
```

Which class loader loads it?

* `String` is part of `java.lang`
* Loaded by **Bootstrap ClassLoader**

To check:

```java
System.out.println(String.class.getClassLoader());
```

Output:

```
null
```

(Null means Bootstrap loader)

---

## 9. When Are Classes Loaded?

A class is loaded when:

* You create an object
* You access a static variable
* You call a static method
* Reflection is used
* Class.forName() is called

Example:

```java
Class.forName("com.example.Test");
```

Forces class loading + initialization.

---

## 10. Class Loading vs Static Initialization

```java
class A {
    static int x = 10;

    static {
        System.out.println("static block");
    }
}
```

* Class loading → class metadata created
* Linking → static memory allocated (default 0)
* Initialization → `x = 10`, then static block runs

---

## 11. Real-World Use Cases

### 11.1 Web Containers (Tomcat/Jetty)

Each web application has a **separate class loader**, so:

* Apps are isolated
* Hot reloading is possible

### 11.2 Frameworks (Spring, Hibernate)

They use class loading for:

* Proxy generation
* Reflection
* Dependency injection

### 11.3 Plugin Systems (IDE plugins)

Custom class loaders load JARs at runtime.

---

## 12. Interview Questions

### Q1: What is Class Loader in Java?

A component of JVM that loads `.class` files into memory.

---

### Q2: Explain the Class Loading Process.

1. **Loading**
2. **Linking** → Verify, Prepare, Resolve
3. **Initialization**

---

### Q3: What is Parent Delegation Model?

Child loader always delegates class loading to parent first.

---

### Q4: Difference Between Bootstrap and System Class Loader?

| Bootstrap Loader   | System Loader             |
| ------------------ | ------------------------- |
| Native code        | Java code                 |
| Loads core classes | Loads application classes |

---

### Q5: When is `Class.forName()` used?

To force class loading + initialization.

---

## Key Takeaways

* Class loading is **lazy and dynamic**.

* JVM uses a 3-phase process: **Loading → Linking → Initialization**.

* Java follows a **hierarchical class loader model**.

* Parent delegation ensures **security**, **consistency**, and **prevents overriding core classes**.

* `ClassLoader` is essential for frameworks, plugins, reflection, and dynamic loading.

---
