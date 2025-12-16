# Hibernate Mapping Relationships

## 1. Introduction to Relationship Mapping

In relational databases:

* Entities are connected through **foreign keys**
* Hibernate/JPA maps these relationships into Java objects using annotations

Hibernate supports the following relationship types:

| Relationship     | Description                             |
| ---------------- | --------------------------------------- |
| **One-to-One**   | One entity relates to exactly one other |
| **One-to-Many**  | One parent has many children            |
| **Many-to-One**  | Many children refer to one parent       |
| **Many-to-Many** | Entities connected through a join table |

Each relationship has important concepts:

* **Owning side vs Inverse side**
* **`mappedBy` attribute**
* **Join columns / Join tables**
* **Fetch types (LAZY / EAGER)**
* **Cascade types**

This guide explains each relationship with clean examples.

---

## 2. One-to-One Relationship

A One-to-One relationship means:

```
One entity ↔ One entity
```

Example:
**User** ↔ **UserProfile**


## 2.1 Using Foreign Key Column

#### User.java

```java
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id")  // FK in user table
    private UserProfile profile;
}
```

#### UserProfile.java

```java
@Entity
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String address;
}
```

### What happens?

* `User` table contains `profile_id` foreign key.
* `User` is the **owning side**.
* `CascadeType.ALL` means saving User also saves UserProfile.

### 2.2 Bidirectional One-to-One

#### UserProfile.java

```java
@OneToOne(mappedBy = "profile")
private User user;
```

`mappedBy` means:

* This side is **inverse side**
* Does not own the foreign key
* Foreign key managed by `User.profile`

---

## 3. Many-to-One Relationship

```
Many children → One parent
```

Examples:

* Many Students belong to One Department
* Many Orders belong to One Customer

### 3.1 Many-to-One Mapping

#### Student.java

```java
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "dept_id")   // FK in student table
    private Department department;
}
```

#### Department.java

```java
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String deptName;
}
```

### What happens?

* `Student` table contains `dept_id`
* Many students → One department
* Owning side: **Student**


### 3.2 Important Notes

* Default fetch type: **EAGER** (but should be changed to LAZY)
* Use:

```java
@ManyToOne(fetch = FetchType.LAZY)
```

---

## 4. One-to-Many Relationship

```
One parent → Many children
```

Example:
One Department has many Students.

In SQL, this is represented using a foreign key in the child table.

---

### 4.1 Bidirectional One-to-Many (Recommended)

#### Department.java

```java
@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
private List<Student> students = new ArrayList<>();
```

#### Student.java

```java
@ManyToOne
@JoinColumn(name = "dept_id")
private Department department;
```

### Explanation

* Child (`Student`) owns the foreign key.
* Parent (`Department`) has `mappedBy = "department"` meaning Student manages FK.
* Hibernate will not create an extra join table (correct behavior).

### 4.2 Unidirectional One-to-Many (Not recommended)

If you write:

```java
@OneToMany
private List<Student> students;
```

Hibernate creates an **extra join table**, which is inefficient.

Avoid unless necessary.

---

## 5. Many-to-Many Relationship

```
Many ↔ Many
```

Example:

* Students take many Courses
* Courses have many Students

In relational DB, this requires a **join table**.


### 5.1 Many-to-Many Basic Mapping

#### Student.java

```java
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();
}
```

#### Course.java

```java
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();
}
```

### What happens?

* Hibernate creates a join table:

```
student_course (
    student_id,
    course_id
)
```

* Owning side: **Student**
* Inverse side: **Course** (`mappedBy`)

---

## 6. Cascade Types (Work With All Relationships)

Cascade operations define how related entities behave when the parent changes.

| Cascade Type | Description                         |
| ------------ | ----------------------------------- |
| `PERSIST`    | Save child when parent is saved     |
| `MERGE`      | Merge child when parent is merged   |
| `REMOVE`     | Delete child when parent is deleted |
| `REFRESH`    | Refresh child on parent refresh     |
| `DETACH`     | Detach children too                 |
| `ALL`        | All of the above                    |

### Example:

```java
@OneToMany(cascade = CascadeType.ALL)
private List<Student> students;
```

---

## 7. Fetch Types

Hibernate defines how related entities are loaded.

| Relationship  | Default Fetch Type |
| ------------- | ------------------ |
| `@OneToOne`   | EAGER              |
| `@ManyToOne`  | EAGER              |
| `@OneToMany`  | LAZY               |
| `@ManyToMany` | LAZY               |

### Use LAZY whenever possible:

```java
@OneToMany(fetch = FetchType.LAZY)
```

Why?

* Improves performance
* Avoids loading entire object graph unnecessarily

---

## 8. Owning Side vs Inverse Side (`mappedBy`)

### Owning Side

* Contains the foreign key column
* Responsible for updates/insert of relationship

### Inverse Side

* Uses `mappedBy`
* Does NOT own foreign key
* Only reflects relationship

### Example:

```java
@OneToMany(mappedBy = "department")
private List<Student> students;
```

Owning side: `Student.department`
Inverse side: `Department.students`

---

## 9. Relationship Summary Table

| Relationship | Where is FK stored? | Owning Side             | Example Annotation       |
| ------------ | ------------------- | ----------------------- | ------------------------ |
| One-to-One   | Either table        | Side with `@JoinColumn` | `@OneToOne @JoinColumn`  |
| Many-to-One  | Child table         | Many side               | `@ManyToOne`             |
| One-to-Many  | Child table         | Many side               | `@OneToMany(mappedBy)`   |
| Many-to-Many | Join table          | Side without `mappedBy` | `@ManyToMany @JoinTable` |

---

## 10. Complete Example: Department ↔ Student

### Department.java

```java
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
}
```

### Student.java

```java
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private Department department;
}
```

---

## 11. Interview Questions

### Q1. What is the difference between `mappedBy` and `JoinColumn`?

* `mappedBy` → inverse side (no FK)
* `JoinColumn` → owning side (contains FK)

---

### Q2. Why is @OneToMany default LAZY while @ManyToOne is EAGER?

* One-to-Many could load huge lists → expensive
* Many-to-One loads a single object → cheap

---

### Q3. How does Hibernate implement Many-to-Many?

Using a **join table**.

---

### Q4. Can One-to-One use a shared primary key?

Yes, using:

```java
@OneToOne
@MapsId
```

---

### Q5. Why avoid unidirectional One-to-Many?

Hibernate creates unnecessary join tables.

---

### Q6. Which side should own the relationship?

The side containing the **foreign key**.

---

## Key Takeaways

* Relationships define how objects map to database tables.

* `mappedBy` determines the inverse side; the opposite holds the foreign key.

* Use **LAZY** fetching for better performance.

* Use cascades carefully to avoid unintended deletes.

* Join tables are required for many-to-many relationships.

---
