# Maven and Build Lifecycle in Spring Boot

## 1. Overview

**Maven** is a build automation and dependency management tool used in Java projects.

In Spring Boot, Maven helps you:

* manage dependencies (`spring-boot-starter-web`, `spring-boot-starter-jpa`, etc.)
* compile and package code
* run tests
* create runnable JAR/WAR artifacts
* standardize builds across teams and CI/CD pipelines

Maven works using a project descriptor file named:

```xml
pom.xml
```

---

## 2. Why Maven is Important in Spring Boot

Without Maven, you would manually:

* download JAR files
* resolve transitive dependencies
* set classpath
* compile and package the app

Maven automates all of this through declarative configuration in `pom.xml`.

Spring Boot adds extra convenience with:

* starter dependencies
* dependency version management via parent BOM
* plugins for running and packaging applications

---

## 3. Standard Maven Project Structure

Maven follows convention over configuration.

```text
project-root/
 ├── pom.xml
 ├── src/
 │   ├── main/
 │   │   ├── java/
 │   │   └── resources/
 │   └── test/
 │       └── java/
 └── target/
```

* `src/main/java` -> application source code
* `src/main/resources` -> config files like `application.properties`
* `src/test/java` -> test classes
* `target` -> generated build output

---

## 4. Understanding `pom.xml`

`pom.xml` means **Project Object Model**.

It defines:

* project coordinates (`groupId`, `artifactId`, `version`)
* dependencies
* plugins
* build configuration

Basic Spring Boot Maven file:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
		 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
		 http://maven.apache.org/xsd/maven-4.0.0.xsd">

	<modelVersion>4.0.0</modelVersion>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.3.0</version>
		<relativePath/>
	</parent>

	<groupId>com.example</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo</name>

	<properties>
		<java.version>21</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

---

## 5. Maven Coordinates and Dependency Resolution

Each dependency is uniquely identified by:

* `groupId`
* `artifactId`
* `version`

Example:

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

Maven downloads libraries into local repository:

```text
~/.m2/repository
```

If one dependency depends on others, Maven automatically fetches those too (transitive dependencies).

---

## 6. What is Maven Build Lifecycle?

A **lifecycle** is a sequence of build phases.

Maven has 3 built-in lifecycles:

1. **default** (build and deploy)
2. **clean** (remove previous build artifacts)
3. **site** (project documentation)

Most daily work uses the `default` and `clean` lifecycles.

---

## 7. Important Phases in Default Lifecycle

Common phases in order:

1. `validate` -> checks project is correct
2. `compile` -> compiles source code
3. `test` -> runs unit tests
4. `package` -> creates JAR/WAR
5. `verify` -> runs additional checks
6. `install` -> installs artifact into local `.m2`
7. `deploy` -> publishes artifact to remote repository

Key rule:

> When you run a phase, all previous phases run automatically.

Example:

```bash
mvn package
```

This executes: `validate -> compile -> test -> package`

---

## 8. Common Maven Commands in Spring Boot

### 8.1 Compile code

```bash
mvn compile
```

### 8.2 Run tests

```bash
mvn test
```

### 8.3 Create JAR

```bash
mvn package
```

Generated file appears in:

```text
target/
```

### 8.4 Install to local repository

```bash
mvn install
```

### 8.5 Clean old build output

```bash
mvn clean
```

### 8.6 Clean + package

```bash
mvn clean package
```

### 8.7 Run Spring Boot app directly

```bash
mvn spring-boot:run
```

---

## 9. Lifecycle in Action (Spring Boot Flow)

When you run:

```bash
mvn clean package
```

Maven does:

1. deletes old `target` directory
2. validates project configuration
3. compiles Java code
4. executes tests
5. creates JAR using Spring Boot plugin

Then you can run:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## 10. Maven Plugins and Why They Matter

Maven core provides lifecycle; plugins provide actual behavior.

Important plugins:

* `maven-compiler-plugin` -> compiles Java
* `maven-surefire-plugin` -> runs tests
* `spring-boot-maven-plugin` -> repackages executable JAR and provides `spring-boot:run`

Spring Boot plugin example:

```xml
<plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

---

## 11. Dependency Scopes (Important)

Common scopes:

* `compile` -> default, available everywhere
* `test` -> only for test classpath
* `provided` -> needed for compile but supplied at runtime by container
* `runtime` -> not required at compile time, needed at runtime

Example:

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>
```

---

## 12. Maven vs Gradle (Quick Comparison)

| Maven                    | Gradle                      |
| ------------------------ | --------------------------- |
| XML configuration        | Groovy/Kotlin DSL           |
| Convention-heavy         | Flexible scripting          |
| Easy for beginners       | More customizable           |
| Very common in enterprise| Popular in modern projects  |

Both are great; Maven is often chosen for consistency and clarity in large teams.

---

## 13. Common Interview Questions

### Q1. What is Maven?

A build and dependency management tool for Java projects.

---

### Q2. What is the purpose of `pom.xml`?

It defines project metadata, dependencies, plugins, and build configuration.

---

### Q3. What happens when we run `mvn package`?

Maven runs all phases up to `package` and creates a distributable artifact.

---

### Q4. Difference between `mvn package` and `mvn install`?

* `package` -> creates artifact in `target`
* `install` -> also publishes artifact to local `.m2` repository

---

### Q5. What is `mvn spring-boot:run`?

A Spring Boot Maven plugin goal to start the application directly from source without manually running the JAR.

---

### Q6. Why is `mvn clean` used before build?

To remove stale artifacts and avoid issues caused by old compiled files.

---

## Key Takeaways

* Maven manages dependencies, build, testing, and packaging in Spring Boot

* `pom.xml` is the heart of Maven configuration

* Build lifecycle phases execute in order and are cumulative

* `mvn clean package` is the most common production build command

* Spring Boot Maven plugin makes apps easy to run and package as executable JARs

---