# Spring Data REST

## 1. Overview

**Spring Data REST** is a Spring project that automatically exposes **RESTful APIs** for Spring Data repositories.

Instead of manually writing:

* controllers
* CRUD endpoints
* request mappings

Spring Data REST generates REST APIs **directly from repository interfaces**.

Core idea:

> *“Expose repositories as REST resources with minimal code.”*

It is mainly used for:

* rapid prototyping
* internal tools
* admin dashboards
* simple CRUD-based services

---

## 2. What Problem Does Spring Data REST Solve?

Without Spring Data REST, a typical CRUD flow requires:

* Entity
* Repository
* Service
* Controller

With Spring Data REST:

* Entity
* Repository

That’s it.

CRUD endpoints are auto-generated.

---

## 3. How Spring Data REST Works

Spring Data REST:

1. Scans Spring Data repositories
2. Exposes them as REST endpoints
3. Uses:

   * HAL (Hypermedia Application Language)
   * JSON
4. Supports:

   * pagination
   * sorting
   * searching
   * associations

---

## 4. Adding Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-rest</artifactId>
</dependency>
```

This builds on top of Spring Data JPA.

---

## 5. Basic Example

### Entity

```java
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String email;
}
```

---

### Repository

```java
@RepositoryRestResource
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

No controller is written.

---

## 6. Auto-Generated REST Endpoints

Once the application starts, Spring Data REST exposes:

| HTTP Method | Endpoint      |
| ----------- | ------------- |
| GET         | `/users`      |
| GET         | `/users/{id}` |
| POST        | `/users`      |
| PUT         | `/users/{id}` |
| PATCH       | `/users/{id}` |
| DELETE      | `/users/{id}` |

All standard CRUD operations are available.

---

## 7. Sample GET Response

```json
{
  "_embedded": {
    "users": [
      {
        "name": "John",
        "email": "john@gmail.com",
        "_links": {
          "self": { "href": "/users/1" }
        }
      }
    ]
  },
  "_links": {
    "self": { "href": "/users" }
  }
}
```

Spring Data REST follows **HATEOAS** principles.

---

## 8. Pagination and Sorting (Out of the Box)

### Pagination

```
/users?page=0&size=5
```

### Sorting

```
/users?sort=name,asc
```

No extra code required.

---

## 9. Query Methods as REST Endpoints

Derived query methods are exposed automatically.

### Repository

```java
List<User> findByEmail(String email);
```

### REST Endpoint

```
/users/search/findByEmail?email=john@gmail.com
```

This is very powerful, but must be controlled carefully.

---

## 10. Customizing Endpoint Paths

```java
@RepositoryRestResource(
    path = "members",
    collectionResourceRel = "members"
)
public interface UserRepository
        extends JpaRepository<User, Long> {
}
```

Now endpoints become:

```
/members
/members/{id}
```

---

## 11. Projections (DTO-like Views)

Spring Data REST supports **projections** to limit exposed fields.

### Projection Interface

```java
@Projection(name = "basic", types = User.class)
public interface UserProjection {
    String getName();
}
```

### Usage

```
/users?projection=basic
```

This avoids exposing full entity data.

---

## 12. Handling Entity Relationships

Spring Data REST exposes relationships automatically.

Example:

```java
@ManyToOne
private Role role;
```

REST endpoints:

```
/users/1/role
/users/1/role/1
```

This is convenient but can easily expose too much.

---

## 13. Validation Support

Standard Bean Validation works:

```java
@NotBlank
private String email;
```

Validation errors are returned automatically with proper HTTP status.

---

## 14. Customizing Behavior with Events

Spring Data REST provides repository events:

* `@HandleBeforeCreate`
* `@HandleAfterCreate`
* `@HandleBeforeSave`
* `@HandleBeforeDelete`

Example:

```java
@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    @HandleBeforeCreate
    public void beforeCreate(User user) {
        // custom logic
    }
}
```

---

## 15. Security Considerations

By default, Spring Data REST:

* exposes **all repositories**
* exposes **all CRUD operations**

This is dangerous in production.

Best practices:

* use Spring Security
* disable unwanted methods
* restrict endpoints

Example:

```java
@RepositoryRestResource(exported = false)
public interface InternalRepository
        extends JpaRepository<InternalEntity, Long> {
}
```

---

## 16. Spring Data REST vs Traditional REST Controllers

| Aspect           | Spring Data REST | Traditional Controllers |
| ---------------- | ---------------- | ----------------------- |
| Code required    | Minimal          | More                    |
| Control          | Limited          | Full                    |
| Custom logic     | Harder           | Easy                    |
| Security control | Complex          | Clear                   |
| Use case         | Simple CRUD      | Real-world APIs         |

---

## 17. When to Use Spring Data REST

Use it when:

* APIs are CRUD-heavy
* minimal customization required
* rapid development needed
* internal tools or admin panels

---

## 18. When NOT to Use Spring Data REST

Avoid it when:

* complex business logic exists
* strict API contracts are needed
* custom request/response formats required
* public APIs are exposed
* versioning is required

Most production APIs prefer **explicit controllers**.

---

## 19. Common Interview Questions

### Q1: What is Spring Data REST?

A framework that exposes Spring Data repositories as REST APIs automatically.

---

### Q2: Does Spring Data REST replace controllers?

Yes for simple CRUD, no for complex APIs.

---

### Q3: How are queries exposed?

Via `/search` endpoints.

---

### Q4: Is Spring Data REST production-ready?

Yes, but requires strong security and careful exposure.

---

### Q5: Is Spring Data REST the same as Spring MVC?

No. It builds on top of Spring Data, not MVC controllers.

---

## Key Takeaways

* Spring Data REST minimizes CRUD controller code

* Repositories are exposed as REST resources

* Supports pagination, sorting, search automatically

* Uses HATEOAS by default

* Powerful but risky if misused

* Best suited for internal or rapid CRUD applications

* Not a replacement for custom REST controllers in complex systems

---
