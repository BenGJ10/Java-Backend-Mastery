# JDK, JRE, JVM in Java

## 1. What is JVM?

The **Java Virtual Machine (JVM)** is an abstract machine that provides a runtime environment to execute Java bytecode.

JVM is responsible for:

* Converting bytecode into machine-specific instructions

* Memory management (heap, stack)

* Garbage collection

* Ensuring platform independence

```java
// JVM executes compiled bytecode (.class file)
public class Demo {
    public static void main(String[] args) {
        System.out.println("Hello JVM");
    }
}
```

### Key Responsibilities of JVM

| Responsibility         | Description                              |
| ---------------------- | ---------------------------------------- |
| Class Loader           | Loads classes into memory                |
| Bytecode Verifier      | Checks for security and correctness      |
| Interpreter/JIT        | Converts bytecode to native instructions |
| Garbage Collector      | Manages memory automatically             |
| Runtime Memory Manager | Allocates JVM memory (Heap, Stack, etc.) |

---

## 2. What is JRE?

The **Java Runtime Environment (JRE)** contains everything required to run Java applications.

JRE = **JVM + Core Libraries + Supporting Files**

It does NOT contain development tools.
It is used only for **running** Java programs.

```plaintext
JRE
 ├─ JVM (Compiler, Interpreter, JIT)
 ├─ Core Libraries (java.*, javax.*)
 └─ Other runtime components
```

### Components of JRE

| Component        | Description                              |
| ---------------- | ---------------------------------------- |
| JVM              | Executes bytecode                        |
| Core Libraries   | Predefined Java class libraries          |
| Deployment Tools | Java Web Start, browser plugins (legacy) |

---

## 3. What is JDK?

The **Java Development Kit (JDK)** is a full development and runtime environment for Java.

JDK = **JRE + Development Tools**

JDK includes everything needed to **develop, compile, debug, and run** Java applications.

```plaintext
JDK
 ├─ JRE
 │   ├─ JVM
 │   └─ Core Libraries
 ├─ Compiler (javac)
 ├─ Tools (javadoc, jar)
 ├─ Debuggers
 └─ Java Development APIs
```

---

### Components of JDK

| Component        | Purpose                          |
| ---------------- | -------------------------------- |
| `javac`          | Compiles Java source to bytecode |
| `java`           | Executes bytecode                |
| `javadoc`        | Generates documentation          |
| `jar`            | Packages classes into JAR files  |
| Development APIs | Provides additional libraries    |

---

## 4. Relationship Between JVM, JRE, and JDK

```plaintext
JDK > JRE > JVM
```

* JDK includes JRE
* JRE includes JVM
* JVM is the core execution engine

---

### Diagram Representation

```plaintext
+-------------------------------+
|             JDK               |
|  +-------------------------+  |
|  |          JRE            |  |
|  |  +-------------------+  |  |
|  |  |       JVM         |  |  |
|  |  +-------------------+  |  |
|  +-------------------------+  |
+-------------------------------+
```

---

## 5. Key Differences: JDK vs JRE vs JVM

| Feature/Aspect      | JVM                                | JRE                              | JDK                                  |
| ------------------- | ---------------------------------- | -------------------------------- | ------------------------------------ |
| Full Form           | Java Virtual Machine               | Java Runtime Environment         | Java Development Kit                 |
| Purpose             | Executes bytecode                  | Provides environment to run Java | Used to develop, compile, debug Java |
| Contains Compiler?  | ❌ No                               | ❌ No                             | ✅ Yes (javac)                        |
| Contains JRE?       | ❌ No                               | ✅ Includes JVM                   | ✅ Includes JRE                       |
| Platform Dependent  | ✅ Yes                              | ✅ Yes                            | ✅ Yes                                |
| Includes Libraries? | ⚠️ Only core libraries used by JVM | ✅ Yes                            | ✅ Yes + dev tools                    |
| Used For            | Running Java Programs              | Running Java Programs            | Developing + Running Java Programs   |

---

## 6. Execution Flow

1. Developer writes `.java` file

2. JDK compiles using `javac` → `.class` (bytecode)

3. JRE loads `.class` into JVM

4. JVM verifies and executes bytecode

```plaintext
.java → javac → .class → JVM executes
```

---

## 7. Interview Nuggets

* JVM is platform-dependent; bytecode is 
platform-independent.

* JRE is used to run a program; JDK is used to develop and run it.

* JVM performs Just-In-Time compilation (JIT) for performance.

* JDK contains both compiler (`javac`) and runtime (`java`) tools.

* JVM uses different memory areas: Heap, Stack, Method Area, PC register.

---

## Key Takeaways

* **JVM** executes Java bytecode.

* **JRE** provides libraries + JVM needed to run Java.

* **JDK** provides complete tools needed for Java development + JRE.

---

