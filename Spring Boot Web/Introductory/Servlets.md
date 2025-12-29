# Servlets in Java

A **Servlet** is a Java program that runs on a **web server** and handles **HTTP requests and responses**.
Servlets are the **foundation of Java web development** and are the underlying technology behind **JSP, Spring MVC, Spring Boot, JSF**, etc.

---

## 1. What is a Servlet?

A **Servlet** is:

* a **server-side Java program**
* runs inside a **Servlet Container** (Tomcat, Jetty, etc.)
* processes **client requests** (usually HTTP)
* generates **dynamic web content** (HTML/JSON/XML)

In simple words:

```
Client (Browser / Postman) → Servlet → Response
```

Servlets follow **request–response model**.

---

## 2. Servlet Container / Web Container

A **Servlet Container** is part of the web server that:

* loads servlet class
* manages servlet lifecycle
* provides multithreading
* handles security and sessions

Examples:

* Apache Tomcat
* Jetty
* GlassFish
* WildFly

---

## 3. Life Cycle of a Servlet

Servlet lifecycle is controlled by the container.

Steps:

1. **Load Class**
2. **Create Object**
3. **Call init()** → initialization phase
4. **Call service() repeatedly** → for each request
5. **Call destroy()** → unloads servlet

---

### Methods in Lifecycle

| Method      | When It Runs                        |
| ----------- | ----------------------------------- |
| `init()`    | Called once when servlet is created |
| `service()` | Called for every request            |
| `destroy()` | Called before servlet is destroyed  |

---

### Lifecycle Diagram

```
Client Request
      │
      ▼
 Container loads servlet (if not loaded)
      │
 init()  → called once
      │
      ▼
Multiple Requests → service()
      │
      ▼
 destroy() → when shutting down or redeploy
```

---

## 4. Basic Servlet Example

### Extend HttpServlet

```java
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class HelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<h1>Hello from Servlet</h1>");
    }
}
```

---

## 5. web.xml Configuration (Traditional)

`web.xml` (Deployment Descriptor):

```xml
<web-app>

  <servlet>
      <servlet-name>HelloServlet</servlet-name>
      <servlet-class>HelloServlet</servlet-class>
  </servlet>

  <servlet-mapping>
      <servlet-name>HelloServlet</servlet-name>
      <url-pattern>/hello</url-pattern>
  </servlet-mapping>

</web-app>
```

Access in browser:

```
http://localhost:8080/app/hello
```

---

## 6. Annotation-Based Configuration (Modern Way)

Instead of `web.xml`

```java
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        res.getWriter().println("Hello Annotation Servlet");
    }
}
```

---

## 7. HttpServletRequest and HttpServletResponse

### HttpServletRequest

Represents the **incoming request**

contains:

* headers
* parameters
* cookies
* session info
* method (GET/POST)

Example:

```java
String name = req.getParameter("name");
```

---

### HttpServletResponse

Represents **response back to client**

Used to:

* set status code
* set headers
* write data

Example:

```java
res.setContentType("text/plain");
res.getWriter().println("Hello user");
```

---

## 8. Handling GET and POST

### GET request

```java
public void doGet(HttpServletRequest req, HttpServletResponse res)
```

### POST request

```java
public void doPost(HttpServletRequest req, HttpServletResponse res)
```

---

## 9. Servlet and Multithreading

Servlet container:

* creates **one servlet instance**
* multiple threads handle multiple requests

Meaning:

```
One Servlet Object
Multiple Threads
```

Therefore:

* avoid shared mutable data
* use synchronization if required

---

## 10. Advantages of Servlets

* platform independent (pure Java)
* efficient and scalable
* secure
* integrates with JSP & Spring
* robust exception handling

---

## 11. Limitations of Servlets

* large amount of boilerplate HTML writing
* manual session handling
* hard to maintain big projects
* business logic mixed with UI in early days

That is why **JSP and Spring MVC/Spring Boot** were built on top of Servlets.

---

## 12. Servlets vs JSP vs Spring MVC

| Technology | Purpose                            |
| ---------- | ---------------------------------- |
| Servlet    | Request/response processing        |
| JSP        | View/UI rendering                  |
| Spring MVC | Framework built on top of Servlets |

---

## 13. Common Interview Questions

### Q1. What is a Servlet?

Server-side Java program to handle HTTP requests and responses.

---

### Q2. What is Servlet Container?

Part of web server that manages servlet lifecycle.

---

### Q3. Difference between GenericServlet and HttpServlet?

| GenericServlet           | HttpServlet                   |
| ------------------------ | ----------------------------- |
| Protocol independent     | HTTP-specific                 |
| Belongs to javax.servlet | Belongs to javax.servlet.http |

---

### Q4. How many servlet instances are created?

Only one instance per servlet (by default).
Multiple threads serve requests.

---

### Q5. What is difference between doGet() and doPost()?

* GET → data visible in URL
* POST → data sent in body
* POST allows large payload

---

### Q6. Why are servlets faster than CGI?

Servlets are multithreaded.
CGI creates **new process per request**.

---

## Key Takeaways

* Servlets are **core Java web technology**

* Run inside servlet container (Tomcat etc.)

* Follow **request–response model**

* Lifecycle methods: `init()`, `service()`, `destroy()`

* Foundation for Spring MVC and Spring Boot

---
