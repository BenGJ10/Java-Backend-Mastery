# Custom Annotations and Interceptors in Spring

## 1. Overview

Custom annotations allow you to create reusable, declarative markers for your code.

Interceptors intercept requests and responses to add cross-cutting concerns like logging, authentication, and validation.

Together, they form a powerful pattern for adding behavior to your application without modifying business logic.

---

## 2. Introduction to Custom Annotations

### 2.1 What Are Custom Annotations?

Custom annotations are user-defined annotations that extend Java's annotation framework.

They are typically used with Spring to:

* mark classes or methods for special processing
* provide metadata to the Spring framework
* enable aspect-oriented programming (AOP)
* reduce boilerplate code

### 2.2 Why Create Custom Annotations?

Custom annotations provide:

* **declarative configuration** - use `@MyAnnotation` instead of XML
* **readability** - the code intent is clear
* **reusability** - apply the same annotation across methods and classes
* **separation of concerns** - business logic stays separate from cross-cutting concerns

---

## 3. Creating a Custom Annotation

### 3.1 Basic Syntax

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
```

This creates a simple custom annotation with no members.

### 3.2 Annotation Components

A custom annotation consists of:

* `@interface` keyword - declares it as an annotation
* `@Target` - specifies where the annotation can be used
* `@Retention` - specifies how long the annotation is retained

---

## 4. Meta-Annotations

Meta-annotations are annotations that apply to other annotations.

### 4.1 `@Target`

Specifies the locations where an annotation can be used.

Common values:

* `ElementType.TYPE` - classes, interfaces, enums
* `ElementType.METHOD` - methods
* `ElementType.FIELD` - fields
* `ElementType.PARAMETER` - method parameters
* `ElementType.CONSTRUCTOR` - constructors
* `ElementType.LOCAL_VARIABLE` - local variables
* `ElementType.ANNOTATION_TYPE` - annotations

Example:

```java
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
```

This annotation can be used on classes and methods.

### 4.2 `@Retention`

Specifies how long the annotation is retained.

Values:

* `RetentionPolicy.SOURCE` - discarded by compiler
* `RetentionPolicy.CLASS` - retained in class file, not at runtime (default)
* `RetentionPolicy.RUNTIME` - retained at runtime and accessible via reflection

For Spring processing, use `RUNTIME`:

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
```

### 4.3 `@Documented`

Includes the annotation in generated Javadoc.

```java
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
```

### 4.4 `@Inherited`

Allows the annotation to be inherited by subclasses.

```java
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
}
```

---

## 5. Annotation Members

Annotations can have members (attributes) that accept values.

### 5.1 Basic Members

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    String message() default "Executing method";

    int level() default 1;

    boolean enabled() default true;
}
```

Members are declared like methods with no parameters.

### 5.2 Using Annotation Members

```java
@Log(message = "Creating user", level = 2)
public void createUser(String name) {
    // method body
}
```

### 5.3 Member Rules

* member names cannot conflict with reserved words
* method declarations cannot have parameters
* return types must be valid (primitives, String, Class, enums, other annotations, or arrays of these)
* default values are optional but recommended

---

## 6. Types of Custom Annotations

### 6.1 Marker Annotations

Annotations with no members.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Admin {
}
```

Used to mark methods that require admin access:

```java
@Admin
public void deleteUser(Long userId) {
    // method body
}
```

### 6.2 Single-Value Annotations

Annotations with one member called `value`.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Timeout {
    long value();
}
```

Can be used without the member name:

```java
@Timeout(5000)
public void fetchData() {
    // method body
}
```

### 6.3 Full-Featured Annotations

Annotations with multiple members.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {

    String key();

    long ttl() default 3600;

    boolean enabled() default true;
}
```

---

## 7. Processing Custom Annotations

### 7.1 Processing at Runtime

Custom annotations are typically processed using reflection or Spring AOP.

#### Using Reflection

```java
public class AnnotationProcessor {

    public static void processAnnotation(Object bean) {
        Class<?> clazz = bean.getClass();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                Log log = method.getAnnotation(Log.class);
                System.out.println("Method: " + method.getName());
                System.out.println("Message: " + log.message());
                System.out.println("Level: " + log.level());
            }
        }
    }
}
```

### 7.2 Processing with Spring AOP

Aspect-Oriented Programming with annotations:

```java
@Aspect
@Component
public class LoggingAspect {

    @Before("@annotation(com.example.Log)")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Executing: " + joinPoint.getSignature());
    }
}
```

---

## 8. Introduction to Interceptors

### 8.1 What Are Interceptors?

Interceptors are objects that intercept requests and responses.

They allow you to:

* preprocess requests before they reach the handler
* postprocess responses before sending them to the client
* handle cross-cutting concerns like logging, authentication, timing

### 8.2 HandlerInterceptor in Spring

Spring provides the `HandlerInterceptor` interface for web requests.

It has three methods:

* `preHandle()` - called before handler execution
* `postHandle()` - called after handler execution
* `afterCompletion()` - called after response is complete

---

## 9. Creating Custom Interceptors

### 9.1 Implementing HandlerInterceptor

```java
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        logger.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                          Object handler, ModelAndView modelAndView) {
        logger.info("Response Status: {}", response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                               Object handler, Exception ex) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        logger.info("Request completed in {} ms", duration);
    }
}
```

### 9.2 Registering Interceptors

Interceptors must be registered in a configuration class.

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/public/**");
    }
}
```

---

## 10. Common Interceptor Patterns

### 10.1 Authentication Interceptor

```java
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        if (!validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    private boolean validateToken(String token) {
        // token validation logic
        return true;
    }
}
```

### 10.2 Timing Interceptor

```java
@Component
public class TimingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                               Object handler, Exception ex) {
        long startTime = (long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;

        if (duration > 1000) {
            System.out.println("SLOW REQUEST: " + duration + "ms for " + request.getRequestURI());
        }
    }
}
```

### 10.3 Request/Response Logging Interceptor

```java
@Component
public class RequestResponseLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("Request: {} {} | Headers: {}", 
                   request.getMethod(), 
                   request.getRequestURI(),
                   getHeaders(request));
        return true;
    }

    private String getHeaders(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            sb.append(header).append("=").append(request.getHeader(header)).append("; ");
        }

        return sb.toString();
    }
}
```

---

## 11. Combining Annotations and Interceptors

### 11.1 Creating a Custom Annotation for Interceptor Use

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireAuth {
    String role() default "USER";
}
```

### 11.2 Using the Annotation in a Controller

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    @RequireAuth(role = "ADMIN")
    public List<User> getAllUsers() {
        return new ArrayList<>();
    }

    @PostMapping
    @RequireAuth(role = "ADMIN")
    public User createUser(@RequestBody User user) {
        return user;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User();
    }
}
```

### 11.3 Processing the Annotation in an Interceptor

```java
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                            Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireAuth annotation = handlerMethod.getMethodAnnotation(RequireAuth.class);

        if (annotation == null) {
            return true;
        }

        String userRole = getUserRoleFromRequest(request);

        if (!userRole.equals(annotation.role())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Forbidden: Required role " + annotation.role());
            return false;
        }

        return true;
    }

    private String getUserRoleFromRequest(HttpServletRequest request) {
        // extract role from token or session
        return "USER";
    }
}
```

---

## 12. Practical Example: Request Validation Annotation

### 12.1 The Annotation

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateRequest {
    String[] requiredFields() default {};
}
```

### 12.2 The Interceptor

```java
@Component
public class ValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                            Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ValidateRequest annotation = handlerMethod.getMethodAnnotation(ValidateRequest.class);

        if (annotation == null) {
            return true;
        }

        for (String field : annotation.requiredFields()) {
            if (request.getParameter(field) == null || request.getParameter(field).isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing required field: " + field);
                return false;
            }
        }

        return true;
    }
}
```

### 12.3 Using the Annotation

```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @PostMapping
    @ValidateRequest(requiredFields = {"name", "price"})
    public String createProduct(@RequestParam String name, @RequestParam String price) {
        return "Product created: " + name;
    }
}
```

---

## Summary

| Concept                    | Purpose                                          | Usage                                    |
| -------------------------- | ------------------------------------------------ | ---------------------------------------- |
| Custom Annotations         | Add metadata and enable declarative configuration | Mark classes, methods, or fields         |
| `@Target`                  | Define where annotation can be used              | Restrict annotation usage                |
| `@Retention`               | Define how long annotation is kept               | Specify SOURCE, CLASS, or RUNTIME        |
| HandlerInterceptor         | Intercept web requests and responses             | Handle cross-cutting concerns            |
| `preHandle()`              | Execute before handler                           | Validate, authenticate, log              |
| `postHandle()`             | Execute after handler                            | Modify response                          |
| `afterCompletion()`        | Execute after response complete                  | Cleanup, timing, logging                 |
| Combining both             | Use annotations to control interceptor behavior  | Powerful, reusable pattern               |

---
