# Apache Tomcat and Other Java Application Servers

Java web applications do **not** run directly like normal Java console programs.
They require a **server environment** that understands:

* HTTP protocol
* Servlets & JSP
* session management
* request routing
* thread handling
* deployment & security

That environment is called a:

> **Web Server / Servlet Container / Application Server**

Apache Tomcat is the most popular one. Others include Jetty, Undertow, JBoss/WildFly, WebLogic, WebSphere, etc.

---

## 1. Why Do We Need Web/Application Servers?

A normal Java program runs with:

```
java MyProgram.java
```

But a **web application** needs to:

* listen on port (80/8080 etc.)
* process HTTP requests
* create and manage threads
* map URL → servlet/controller
* manage sessions & cookies
* send HTTP responses
* serve static content (HTML/CSS/JS)
* deploy WAR/JAR apps

A **Servlet/JSP cannot run without container support**.
So we need servers like **Tomcat**.

---

## 2. Web Server vs Application Server (Important)

| Web Server              | Application Server                   |
| ----------------------- | ------------------------------------ |
| Handles HTTP requests   | Provides full enterprise services    |
| Supports Servlets/JSP   | Supports EJB, JMS, transactions      |
| Lightweight             | Heavyweight                          |
| Examples: Tomcat, Jetty | Examples: WebLogic, JBoss, WebSphere |
| No enterprise features  | Enterprise Java EE features          |

**Tomcat = Web Server + Servlet Container**

**JBoss/WebLogic/WebSphere = Full Java EE Application Servers**

---

## 3. Apache Tomcat

Apache Tomcat is the **most widely used Java web server**.

### Key features

* Implements **Servlet & JSP specification**
* Embedded HTTP web server
* Open-source, lightweight
* Used with Spring Boot by default

### What Tomcat Does Internally

* loads servlets
* manages servlet lifecycle
* maps URLs to servlets
* handles multithreading
* manages sessions & cookies
* converts HTTP request → `HttpServletRequest`
* converts output → HTTP response

### Deployment Types

* WAR file (`.war`)
* Embedded inside Spring Boot JAR
* Standalone server

---

## 4. Other Popular Java Servers

### 4.1 Jetty

* lightweight & embeddable
* used in **microservices**
* supports WebSockets
* preferred for **embedded systems**

Used by: Spark Java, Eclipse IDE.

---

### 4.2 Undertow

* very fast, non-blocking IO
* default server for **RedHat/WildFly**
* often used with microservices

---

### 4.3 JBoss / WildFly (Application Server)

* full **Jakarta EE / Java EE container**
* supports EJB, JPA, JMS, transactions
* used in large enterprise systems

---

### 4.4 GlassFish / Payara

* reference implementation of Jakarta EE
* supports advanced enterprise services
* Payara is production-ready fork

---

### 4.5 WebLogic (Oracle)

* enterprise server
* highly configurable
* used in banking/telecom

---

### 4.6 IBM WebSphere

* heavy duty application server
* large legacy enterprise systems
* advanced security and clustering

---

## 5. Why Spring Boot Bundles an Embedded Server?

Spring Boot uses:

* **Tomcat** (default)
* or Jetty / Undertow (optional)

When you run:

```bash
mvn spring-boot:run
```

Spring Boot:

* starts an **embedded server**
* runs your app inside it
* no WAR file deployment needed

So application becomes:

```
Application + Server inside same JAR
```

This is why Spring Boot apps are easy to deploy in **cloud & containers**.

---

## 6. What Happens When You Hit a URL in Tomcat?

Step-by-step:

1. client sends HTTP request
2. Tomcat listens on configured port (default 8080)
3. Tomcat receives the request
4. finds matching servlet/controller
5. creates request & response objects
6. calls `service()` → `doGet()` / `doPost()`
7. servlet processes request
8. response is sent back to browser
9. thread returned to pool

Tomcat also:

* handles thread pooling
* manages memory
* handles connection keep-alive
* supports HTTPS/SSL

---

## 7. When to Use Which Server?

### Use Tomcat / Jetty / Undertow when:

* building REST APIs
* Spring Boot microservices
* lightweight web app
* no heavy enterprise features needed

### Use JBoss/WildFly/WebLogic when:

* enterprise systems
* distributed transactions
* messaging (JMS)
* EJB required
* legacy Java EE stack

---

## 8. Common Interview Questions

### Q1. What is Apache Tomcat?

A servlet container & web server that implements Servlet and JSP specifications.

---

### Q2. Difference between Web Server and Application Server?

Web server only handles HTTP & Servlets
Application server provides **full enterprise features**.

---

### Q3. Why do we need servers like Tomcat?

Because servlets/JSP need a **container** for:

* lifecycle management
* threading
* request/response processing
* HTTP protocol support

---

### Q4. Why is Tomcat default in Spring Boot?

* lightweight
* easy embedding
* open source
* fast startup
* widely supported

---

### Q5. What is embedded server?

Server runs **inside your application JAR**, no external setup needed.

---

## Key Takeaways

* Tomcat is **Servlet Container + Web Server**

* Required to run servlets/JSP/Spring MVC apps

* Application servers provide **advanced enterprise features**

* Spring Boot embeds Tomcat → no manual deployment

* Jetty, Undertow are lightweight alternatives

* Web servers process HTTP requests and manage servlet lifecycle

---
