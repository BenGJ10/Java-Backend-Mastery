# Hibernate Annotations

Hibernate uses **Java annotations** to map Java classes and fields to database tables and columns.
Annotations eliminate the need for XML mapping files and make your code more readable and maintainable.

## 1. Entity-Level Annotations

### 1.1 `@Entity`

Marks a class as a persistent entity (mapped to a table).

```java
@Entity
public class Student { ... }
```

Notes:

* Required for every Hibernate-managed class.
* Must have a no-argument constructor.

---

### 1.2 `@Table`

Customizes the table name and configurations.

```java
@Entity
@Table(name = "students_table")
public class Student { ... }
```

Options:

| Property  | Usage       |
| --------- | ----------- |
| `name`    | Table name  |
| `schema`  | Schema name |
| `catalog` | DB catalog  |

---

## 2. Primary Key Annotations

### 2.1 `@Id`

Marks a field as the primary key.

```java
@Id
private int id;
```

---

### 2.2 `@GeneratedValue`

Defines key generation strategy.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
```

Strategies:

| Strategy   | Behavior                           |
| ---------- | ---------------------------------- |
| `IDENTITY` | Auto-increment (MySQL, PostgreSQL) |
| `SEQUENCE` | Uses DB sequence                   |
| `TABLE`    | Uses a separate table to store ids |
| `AUTO`     | Hibernate selects best option      |

---

### 2.3 `@SequenceGenerator`

For `GenerationType.SEQUENCE`:

```java
@Id
@SequenceGenerator(name = "stu_seq", sequenceName = "student_seq", allocationSize = 1)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stu_seq")
private int id;
```

---

## 3. Column-Level Annotations

### 3.1 `@Column`

Maps a field to a specific column.

```java
@Column(name = "student_name", nullable = false, length = 50)
private String name;
```

Options:

| Property    | Meaning             |
| ----------- | ------------------- |
| `name`      | Column name         |
| `nullable`  | Allows null or not  |
| `unique`    | Enforces uniqueness |
| `length`    | Column length       |
| `precision` | Decimal precision   |
| `scale`     | Decimal scale       |

---

### 3.2 `@Transient`

Field is **not persisted** in the database.
This will makesure Hibernate ignores this field when creating the table schema.

```java
@Transient
private int tempValue;
```

---

### 3.3 `@Lob`

Maps large objects (CLOB/BLOB).

```java
@Lob
private String description;
```

---

### 3.4 `@Temporal`

Used for Date/Time mapping.

```java
@Temporal(TemporalType.TIMESTAMP)
private Date createdAt;
```

Types:

* `TemporalType.DATE`
* `TemporalType.TIME`
* `TemporalType.TIMESTAMP`

---

## 4. Relationship Annotations

Hibernate supports:

* One-to-One
* One-to-Many
* Many-to-One
* Many-to-Many

---

### 4.1 `@OneToOne`

```java
@OneToOne
@JoinColumn(name = "address_id")
private Address address;
```

Options:

* `mappedBy` → for bidirectional mapping
* `cascade` → cascade operations
* `fetch` → lazy/eager loading

---

### 4.2 `@ManyToOne`

Most common relationship (many students → one department)

```java
@ManyToOne
@JoinColumn(name = "dept_id")
private Department department;
```

Fetch Type:

* Default: **EAGER**

---

### 4.3 `@OneToMany`

```java
@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
private List<Student> students;
```

Notes:

* Default fetch type: **LAZY**
* Must specify `mappedBy` to avoid join table

---

### 4.4 `@ManyToMany`

```java
@ManyToMany
@JoinTable(
    name = "student_course",
    joinColumns = @JoinColumn(name = "student_id"),
    inverseJoinColumns = @JoinColumn(name = "course_id")
)
private List<Course> courses;
```

Hibernate automatically creates a join table.

---

## 5. Fetch Type Annotations

Used with relationship annotations.

```java
@OneToMany(fetch = FetchType.LAZY)
```

Fetch types:

| Type    | Meaning                      |
| ------- | ---------------------------- |
| `LAZY`  | Load only when accessed      |
| `EAGER` | Load immediately with parent |

LAZY is recommended for performance.

---

## 6. Cascade Types

Specify which operations propagate to related entities.

```java
@OneToMany(cascade = CascadeType.ALL)
private List<Student> students;
```

Cascade options:

* `PERSIST`
* `MERGE`
* `REMOVE`
* `REFRESH`
* `DETACH`
* `ALL`

---

## 7. Embedded Objects

### 7.1 `@Embeddable`

Marks a class whose fields will be embedded in another table.

```java
@Embeddable
public class Address {
    private String city;
    private String state;
}
```

---

### 7.2 `@Embedded`

```java
@Embedded
private Address address;
```

Creates columns like:

```
city
state
```

inside the same table.

---

## 8. Inheritance Mapping Annotations

Hibernate supports:

* Single Table
* Joined Table
* Table Per Class


### 8.1 `@Inheritance(strategy = ...)`

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person { ... }
```

---

### 8.2 `@DiscriminatorColumn`

Used in **Single Table Strategy**:

```java
@DiscriminatorColumn(name = "type")
```

---

## 9. Lifecycle Callbacks

Hibernate provides annotations to intercept entity events.

### 9.1 `@PrePersist`

Before INSERT

### 9.2 `@PostPersist`

After INSERT

### 9.3 `@PreUpdate`

Before UPDATE

### 9.4 `@PostLoad`

After loading entity

---

### Example:

```java
@PrePersist
public void onCreate() {
    System.out.println("Entity is being created");
}
```

---

## 10. Validation Annotations

Hibernate Validator provides JSR-380 validation.

```java
@NotNull
@Size(min = 3, max = 30)
private String name;

@Min(1)
@Max(150)
private int age;
```

---

## 11. Putting It All Together

### Example Complete Entity with All Important Annotations

```java
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private Department department;

    @Embedded
    private Address address;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    public void setTimestamp() {
        createdAt = new Date();
    }
}
```

---

## 12. Interview Questions

### Q1. What is `@Entity`?

Marks a Java class as a Hibernate-managed persistent entity.

---

### Q2. Difference between `@Table` and `@Column`?

* `@Table` → applies at class level
* `@Column` → applies to individual fields

---

### Q3. What is `fetch = FetchType.LAZY`?

Hibernate loads related data **only when accessed**.

---

### Q4. What does `cascade = CascadeType.ALL` mean?

All operations (save, update, delete) propagate to child entities.

---

### Q5. Difference between `@OneToMany` and `@ManyToOne`?

* OneToMany → parent has list of children
* ManyToOne → child references parent

---

### Q6. Why use `@Embedded`?

To reuse a value object (Address, Coordinates) in multiple entities.

---

### Q7. What is `@Transient`?

Prevents a field from being persisted.

---

### Q8. Which inheritance strategy is fastest?

Single Table (`InheritanceType.SINGLE_TABLE`) because it avoids joins.

---

## Key Takeaways

* Hibernate annotations provide a clean way to map Java objects to relational tables.

* Entity mappings include primary keys, columns, relationships, and embeddable types.

* Fetch strategies and cascade types significantly affect performance.

* Lifecycle callbacks allow executing logic during entity persistence.

* Annotations eliminate XML mapping files and make code highly maintainable.

---
