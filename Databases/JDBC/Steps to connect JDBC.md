# Steps to Connect to PostgreSQL Using JDBC

JDBC connectivity always follows a **fixed set of steps**, regardless of which database you use (MySQL, PostgreSQL, Oracle, SQL Server).

## 1. Add JDBC Driver JAR File

Every database requires a **JDBC driver JAR file**.

### What is a JDBC Driver JAR?

It is a **library provided by database vendors** that implements JDBC interfaces and enables Java to communicate with the database.

### Example Driver JAR Files:

| Database       | JAR Name                   | Maven Artifact                    |
| -------------- | -------------------------- | --------------------------------- |
| **PostgreSQL** | `postgresql-<version>.jar` | `org.postgresql:postgresql`       |
| **MySQL**      | `mysql-connector-j.jar`    | `mysql:mysql-connector-j`         |
| **Oracle**     | `ojdbc8.jar`               | `com.oracle.database.jdbc:ojdbc8` |

### Where to Place the JAR?

#### If using Maven:

Add dependency inside `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.7.3</version>
</dependency>
```

#### If using pure Java (no Maven):

Add the JAR to classpath:

```
javac -cp .:postgresql.jar MyApp.java  
java -cp .:postgresql.jar MyApp
```

---

## 2. Load the Database Driver (optional but recommended)

Older JDBC versions required explicit loading using `Class.forName()`.

```java
Class.forName("org.postgresql.Driver");  // PostgreSQL
// Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL
```

Modern JDBC drivers auto-register, but interviewers **always expect this step**.

---

## 3. Create the Connection URL

Each database has a specific URL format.

### PostgreSQL URL:

```
jdbc:postgresql://host:port/database
```

Example:

```java
String url = "jdbc:postgresql://localhost:5432/schooldb";
```

### Common URL Parameters:

| Parameter              | Meaning       |
| ---------------------- | ------------- |
| `ssl=true`             | Enable SSL    |
| `currentSchema=public` | Select schema |

---

## 4. Establish the Connection

Use `DriverManager.getConnection()`:

```java
Connection con = DriverManager.getConnection(
    url,
    "username",
    "password"
);
```

If connection fails → throws `SQLException`.

---

## 5. Create a Statement / PreparedStatement

### Using Statement (static SQL)

```java
Statement st = con.createStatement();
```

### Using PreparedStatement (recommended)

```java
PreparedStatement ps = con.prepareStatement(
    "SELECT * FROM students WHERE age > ?"
);
ps.setInt(1, 18);
```

PreparedStatement:

* Prevents SQL Injection
* Faster
* Supports parameter binding

---

## 6. Execute SQL Queries

### SELECT Query

```java
ResultSet rs = ps.executeQuery();
```

### INSERT / UPDATE / DELETE

```java
int rows = ps.executeUpdate();
```

---

## 7. Process the ResultSet

```java
while (rs.next()) {
    System.out.println(
        rs.getInt("id") + " | " +
        rs.getString("name")
    );
}
```

Useful `ResultSet` methods:

| Method              | Purpose           |
| ------------------- | ----------------- |
| `getInt(column)`    | Retrieve integer  |
| `getString(column)` | Retrieve string   |
| `next()`            | Move cursor       |
| `close()`           | Release ResultSet |

---

## 8. Close All Resources (Very Important)

Closing resources prevents connection leaks.

```java
rs.close();
ps.close();
con.close();
```

If using modern Java → use try-with-resources:

```java
try (Connection con = DriverManager.getConnection(url, user, pass);
     PreparedStatement ps = con.prepareStatement("SELECT * FROM students");
     ResultSet rs = ps.executeQuery()) {

    while (rs.next()) {
        System.out.println(rs.getString("name"));
    }

} catch (SQLException e) {
    e.printStackTrace();
}
```

---

## Complete Example: JDBC Connection with PostgreSQL

```java
import java.sql.*;

public class TestJDBC {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/schooldb";
        String user = "postgres";
        String password = "admin";

        try {
            // 1. Load driver (optional)
            Class.forName("org.postgresql.Driver");

            // 2. Establish connection
            Connection con = DriverManager.getConnection(url, user, password);

            // 3. Create PreparedStatement
            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM students WHERE age > ?"
            );
            ps.setInt(1, 18);

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## Summary

| Step  | Description                                |
| ----- | ------------------------------------------ |
| **1** | Add JDBC Driver JAR to project             |
| **2** | Load JDBC driver (Class.forName)           |
| **3** | Create Connection URL                      |
| **4** | Establish connection using `DriverManager` |
| **5** | Create `Statement` or `PreparedStatement`  |
| **6** | Execute SQL queries                        |
| **7** | Process the results (ResultSet)            |
| **8** | Close all resources                        |

---
