# Introduction to JDBC

## 1. Overview

**JDBC (Java Database Connectivity)** is a standard Java API used to connect Java applications to relational databases such as:

* MySQL
* PostgreSQL
* Oracle
* SQL Server
* MariaDB

JDBC allows Java programs to:

* Establish a database connection
* Send SQL queries and updates
* Retrieve and process results
* Handle transactions
* Interact with relational data in a uniform way

It is part of the **java.sql** package.

---

## 2. Why JDBC?

Before JDBC, every database had separate drivers with different APIs (inconsistent).
JDBC provides a **common interface** so Java applications can work with any database by simply changing the driver.

### Benefits:

* Database independence
* Standard API
* Supports both SQL queries & stored procedures
* Reliable and widely used in enterprise apps

---

## 3. JDBC Architecture

JDBC follows a **two-layer architecture**:

```
Java Application
        ↓
JDBC API (java.sql package)
        ↓
JDBC Driver
        ↓
Database (MySQL / Oracle / PostgreSQL ...)
```

---

## 4. JDBC Components (Core Interfaces)

| Component             | Description                             |
| --------------------- | --------------------------------------- |
| **DriverManager**     | Loads and manages drivers               |
| **Connection**        | Represents a connection to the database |
| **Statement**         | Executes SQL queries (simple SQL)       |
| **PreparedStatement** | Precompiled SQL, safer & faster         |
| **CallableStatement** | Executes stored procedures              |
| **ResultSet**         | Holds query results                     |

---

## 5. Steps to Work with JDBC

JDBC workflow always follows **5 basic steps**:


### Step 1: Load & Register the Driver

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

Modern JDBC drivers auto-register, but this is still common.

### Step 2: Establish Connection

```java
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/testdb",
    "root",
    "password"
);
```

The URL format:

```
jdbc:<database-type>://<host>:<port>/<db-name>
```

### Step 3: Create Statement

#### Using Statement:

```java
Statement st = con.createStatement();
```

#### Using PreparedStatement (preferred):

```java
PreparedStatement ps = con.prepareStatement(
    "SELECT * FROM employees WHERE id = ?"
);
```

### Step 4: Execute Query

#### For SELECT queries:

```java
ResultSet rs = ps.executeQuery();
```

#### For INSERT/UPDATE/DELETE:

```java
int rows = ps.executeUpdate();
```


### Step 5: Process Results

```java
while (rs.next()) {
    int id = rs.getInt("id");
    String name = rs.getString("name");
    System.out.println(id + " - " + name);
}
```


### Step 6: Close Resources

```java
rs.close();
ps.close();
con.close();
```

Always close connections to avoid memory leaks.

---

## 6. JDBC Statements

### 6.1 Statement

* Used for simple queries without parameters
* Not safe against SQL Injection

Example:

```java
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("SELECT * FROM students");
```


### 6.2 PreparedStatement (Recommended)

* Supports parameter placeholders (`?`)
* Prevents SQL Injection
* Faster because SQL is precompiled

Example:

```java
PreparedStatement ps = con.prepareStatement(
    "INSERT INTO users (name, age) VALUES (?, ?)"
);

ps.setString(1, "Ben");
ps.setInt(2, 21);

ps.executeUpdate();
```

### 6.3 CallableStatement (Stored Procedures)

```java
CallableStatement cs = con.prepareCall("{call getUsers()}");
ResultSet rs = cs.executeQuery();
```

---

## 7. JDBC Drivers

JDBC supports **4 types** of drivers:

| Driver Type | Description                       |
| ----------- | --------------------------------- |
| Type 1      | JDBC-ODBC Bridge (obsolete)       |
| Type 2      | Native API driver                 |
| Type 3      | Network Protocol driver           |
| Type 4      | **Pure Java driver** → used today |

Most modern databases (MySQL, Oracle, PostgreSQL) use **Type 4** drivers.

---

## 8. JDBC URL Formats

### MySQL

```
jdbc:mysql://localhost:3306/dbname
```

### Oracle

```
jdbc:oracle:thin:@localhost:1521:xe
```

### PostgreSQL

```
jdbc:postgresql://localhost:5432/dbname
```

---

## 9. Handling Transactions in JDBC

```java
con.setAutoCommit(false);

try {
    ps1.executeUpdate();
    ps2.executeUpdate();
    con.commit();
} catch (Exception e) {
    con.rollback();
}
```

Useful for multiple dependent queries.

---

## 10. Exception Handling in JDBC

All JDBC operations throw:

* `SQLException`
* `SQLTimeoutException`
* `SQLIntegrityConstraintViolationException`

Example:

```java
try {
    // JDBC logic
} catch (SQLException e) {
    e.printStackTrace();
}
```

---

## 11. Simple Complete JDBC Example

```java
import java.sql.*;

public class JDBCExample {
    public static void main(String[] args) throws Exception {

        // 1. Load driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2. Connect to DB
        Connection con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/testdb",
            "root",
            "password"
        );

        // 3. Create PreparedStatement
        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM employee WHERE salary > ?"
        );
        ps.setInt(1, 50000);

        // 4. Execute query
        ResultSet rs = ps.executeQuery();

        // 5. Process results
        while (rs.next()) {
            System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
        }

        // 6. Close resources
        rs.close();
        ps.close();
        con.close();
    }
}
```

---

## 12. Interview Questions

### Q1: What is JDBC?

A Java API that allows Java programs to interact with relational databases.

### Q2: What is the difference between Statement and PreparedStatement?

| Statement               | PreparedStatement            |
| ----------------------- | ---------------------------- |
| Executes static queries | Executes precompiled queries |
| No parameters           | Supports parameters (?)      |
| Prone to SQL Injection  | Safe from SQL Injection      |
| Slower                  | Faster                       |

---

### Q3: What is Connection pooling?

Reusing database connections to improve performance.

Used in Spring Boot (HikariCP), Tomcat, etc.

---

### Q4: Why do we close JDBC resources?

To prevent:

* Memory leaks
* Exhausting database connections
* Server slowdown

---

### Q5: What is ResultSet?

A cursor that holds data returned by a SELECT query.

---

# Key Takeaways

* JDBC is the standard Java API for SQL-based database operations.

* Core interfaces: Connection, Statement, PreparedStatement, ResultSet.

* Steps: Load driver → Connect → Execute → Process → Close.

* PreparedStatement is safer and faster than Statement.

* JDBC is foundational for frameworks like **Hibernate**, **JPA**, and **Spring JDBC**.

---

