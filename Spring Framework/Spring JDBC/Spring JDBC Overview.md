# Spring JDBC

**Spring JDBC** is a module in the Spring Framework that simplifies working with relational databases using **plain JDBC** while removing boilerplate code such as:

* opening/closing connections
* handling exceptions
* managing statements and result sets

Spring JDBC is built around the core class **`JdbcTemplate`**.

---

## 1. What is Spring JDBC?

Spring JDBC is Spring’s abstraction over traditional JDBC API.

It provides:

* simplified database access
* consistent exception handling
* automatic resource management
* minimal configuration
* readable code

> It keeps the **power of JDBC** but removes **its complexity and verbosity**.

---

## 2. Problems with Traditional JDBC

Traditional JDBC requires you to:

* load driver manually
* create & close connection
* create statements
* handle checked SQL exceptions
* manage transactions manually

Example (traditional JDBC):

```java
Connection conn = DriverManager.getConnection(url, user, pass);
PreparedStatement ps = conn.prepareStatement("select * from users");
ResultSet rs = ps.executeQuery();
while (rs.next()) {
    ...
}
rs.close();
ps.close();
conn.close();
```

Problems:

* Too much boilerplate
* Error-prone resource closing
* Exception handling everywhere

---

## 3. Spring JDBC Components

Spring JDBC provides high-level components:

| Component                    | Purpose                      |
| ---------------------------- | ---------------------------- |
| `JdbcTemplate`               | Core utility class           |
| `NamedParameterJdbcTemplate` | Supports named parameters    |
| `SimpleJdbcInsert`           | Simplifies insert operations |
| `SimpleJdbcCall`             | Calls stored procedures      |
| `RowMapper`                  | Maps rows to objects         |
| `ResultSetExtractor`         | Extracts complex results     |

---

## 4. JdbcTemplate – The Core of Spring JDBC

`JdbcTemplate` handles:

* opening connection
* preparing statement
* executing SQL
* mapping results
* closing connection
* translating exceptions

---

### Configuration Example (Spring Boot)

Add dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

Add DB configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/testdb
spring.datasource.username=root
spring.datasource.password=admin
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Inject JdbcTemplate:

```java
@Autowired
private JdbcTemplate jdbcTemplate;
```

---

## 5. Basic CRUD Operations Using JdbcTemplate

---

### 5.1 Insert Record

```java
String sql = "INSERT INTO users(name, email) VALUES(?, ?)";
jdbcTemplate.update(sql, "Ben", "ben@gmail.com");
```

---

### 5.2 Update Record

```java
String sql = "UPDATE users SET email=? WHERE id=?";
jdbcTemplate.update(sql, "new@gmail.com", 1);
```

---

### 5.3 Delete Record

```java
String sql = "DELETE FROM users WHERE id=?";
jdbcTemplate.update(sql, 1);
```

---

### 5.4 Fetch Single Value

```java
String sql = "SELECT COUNT(*) FROM users";
int count = jdbcTemplate.queryForObject(sql, Integer.class);
```

---

### 5.5 Fetch Single Row → Object Mapping

Use `RowMapper`.

```java
public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email")
        );
    }
}
```

Fetch:

```java
String sql = "SELECT * FROM users WHERE id=?";
User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), 1);
```

---

### 5.6 Fetch Multiple Rows → List of Objects

```java
String sql = "SELECT * FROM users";
List<User> users = jdbcTemplate.query(sql, new UserRowMapper());
```

---

## 6. NamedParameterJdbcTemplate

Uses **named parameters** instead of `?`

### Example

```java
String sql = "INSERT INTO users(name, email) VALUES(:name, :email)";

MapSqlParameterSource params = new MapSqlParameterSource()
        .addValue("name", "John")
        .addValue("email", "john@gmail.com");

namedParameterJdbcTemplate.update(sql, params);
```

Advantage:

* better readability
* avoids parameter index mismatch

---

## 7. Transactions in Spring JDBC

Enable transaction management:

```java
@EnableTransactionManagement
```

Use:

```java
@Transactional
public void transferMoney() {
    ...
}
```

Spring handles:

* commit on success
* rollback on exception

---

## 8. Exception Handling in Spring JDBC

Spring converts checked SQLExceptions into **runtime exceptions**:

* `DataAccessException` (root)

  * `DuplicateKeyException`
  * `EmptyResultDataAccessException`
  * `DataIntegrityViolationException`

Advantage:

* no need for try–catch everywhere
* cleaner code

---

## 9. SimpleJdbcInsert

Simplifies insert operation without SQL.

```java
SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
        .withTableName("users");

Map<String, Object> params = Map.of(
        "name", "Ben",
        "email", "ben@gmail.com"
);

insert.execute(params);
```

---

## 10. SimpleJdbcCall (Stored Procedures)

```java
SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
        .withProcedureName("getUser");

Map<String, Object> result =
        call.execute(Map.of("userId", 1));
```

---

## 11. Advantages of Spring JDBC

* Less boilerplate
* Easy exception handling
* Automatic resource cleanup
* Declarative transaction management
* Easy integration with Spring MVC and Boot
* Works without Hibernate/JPA

---

## 12. Disadvantages

* SQL must still be written manually
* No ORM mapping automatically
* No entity lifecycle management
* More low-level than Hibernate/JPA

---

## 13. When to Use Spring JDBC?

Use when:

* performance critical applications
* simple database structure
* full SQL control required
* batch operations heavy
* no need for ORM overhead
* fine-grained DB tuning required

---

## 14. Spring JDBC vs JPA/Hibernate

| Aspect               | Spring JDBC | Hibernate/JPA   |
| -------------------- | ----------- | --------------- |
| Level                | Low-level   | High-level ORM  |
| SQL writing          | Required    | Optional        |
| Performance          | Very high   | Slight overhead |
| Mapping              | Manual      | Automatic       |
| Learning curve       | Low         | Medium/High     |
| Control over queries | Full        | Limited         |

---

## 15. Common Interview Questions

### Q1. What is Spring JDBC?

Spring abstraction over JDBC that simplifies database programming.

---

### Q2. What is JdbcTemplate?

Central class used to execute SQL and handle JDBC resource management.

---

### Q3. Difference between JdbcTemplate and NamedParameterJdbcTemplate?

Second supports named parameters instead of `?`.

---

### Q4. Why is Spring JDBC faster than Hibernate sometimes?

Because it avoids ORM overhead.

---

### Q5. What is RowMapper?

Interface used to map result set rows to Java objects.

---

## Key Takeaways

* Spring JDBC removes boilerplate and simplifies JDBC

* `JdbcTemplate` is the core API

* Supports CRUD, batch operations, stored procedures

* Integrates seamlessly with Spring Boot

* Still requires writing SQL manually

* Preferred when performance and control are top priority

---