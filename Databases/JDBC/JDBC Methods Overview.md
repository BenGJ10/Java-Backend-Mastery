# JDBC Methods Overview

## 1. Core JDBC Classes & Interfaces

JDBC operations mainly involve:

1. **Connection**
2. **Statement**
3. **PreparedStatement**
4. **CallableStatement**
5. **ResultSet**

Each provides a set of commonly used methods.

---

## 2. Connection Interface — Key Methods

| Method                  | Description                        |
| ----------------------- | ---------------------------------- |
| `createStatement()`     | Creates a Statement object         |
| `prepareStatement(sql)` | Creates a PreparedStatement        |
| `prepareCall(sql)`      | Calls stored procedures            |
| `setAutoCommit(false)`  | Enables manual transaction control |
| `commit()`              | Commits transaction                |
| `rollback()`            | Rolls back transaction             |
| `close()`               | Closes connection                  |

### Example:

```java
Connection con = DriverManager.getConnection(url, user, pass);
con.setAutoCommit(false);
```

---

## 3. Statement Interface — Key Methods

Used for **simple SQL queries** without parameters.

### 3.1 Create Statement

```java
Statement st = con.createStatement();
```

### 3.2 executeQuery()

Used for **SELECT** queries only.

```java
ResultSet rs = st.executeQuery("SELECT * FROM employees");
```

**Returns:** `ResultSet`


### 3.3 executeUpdate()

Used for **INSERT**, **UPDATE**, **DELETE**, and **DDL** commands.

```java
int rows = st.executeUpdate("UPDATE employees SET salary = 50000");
```

**Returns:** number of rows affected (int)

Works for:

* INSERT
* UPDATE
* DELETE
* CREATE TABLE
* DROP TABLE

### 3.4 execute()

Used when the SQL may return **either** ResultSet or row count.

Example for stored procedures:

```java
boolean result = st.execute("CALL getEmployees()");
```

**Returns:**

* `true` → if a `ResultSet` is returned
* `false` → if only update count is returned

Check output using:

```java
if (st.getResultSet() != null) { ... }
if (st.getUpdateCount() != -1) { ... }
```

---

## 4. PreparedStatement Interface — Key Methods

Used for **parameterized**, **precompiled**, **safe** SQL queries.

### 4.1 Creating PreparedStatement

```java
PreparedStatement ps = con.prepareStatement(
    "INSERT INTO users (name, age) VALUES (?, ?)"
);
```

### 4.2 Setting Parameters

| Method                     | Used For           |
| -------------------------- | ------------------ |
| `setInt(index, value)`     | Integer parameters |
| `setString(index, value)`  | String parameters  |
| `setDouble(index, value)`  | Floating values    |
| `setBoolean(index, value)` | Boolean values     |
| `setDate(index, sqlDate)`  | SQL Date           |
| `setObject(index, obj)`    | Any generic object |

Example:

```java
ps.setString(1, "Ben");
ps.setInt(2, 22);
```

### 4.3 executeQuery()

Used for **SELECT** queries.

```java
ResultSet rs = ps.executeQuery();
```

### 4.4 executeUpdate()

Used for **INSERT**, **UPDATE**, **DELETE**, **DDL**.

```java
int rows = ps.executeUpdate();
```

### 4.5 execute()

Used when results may vary.

```java
boolean res = ps.execute();
```

---

## 5. CallableStatement — Stored Procedure Methods

### Creating CallableStatement

```java
CallableStatement cs = con.prepareCall("{call getEmployee(?)}");
cs.setInt(1, 100);
```

### Methods:

| Method                              | Description             |
| ----------------------------------- | ----------------------- |
| `registerOutParameter(index, type)` | Registers output        |
| `getXXX(index)`                     | Fetch output parameters |

Example:

```java
cs.registerOutParameter(2, Types.VARCHAR);
cs.execute();
String name = cs.getString(2);
```

---

## 6. ResultSet Interface — Key Methods

Returned after executing SELECT queries.

### 6.1 Navigation Methods

| Method          | Description          |
| --------------- | -------------------- |
| `next()`        | Move to next row     |
| `previous()`    | Move to previous row |
| `first()`       | Move to first row    |
| `last()`        | Move to last row     |
| `absolute(row)` | Jump to specific row |
| `beforeFirst()` | Reset cursor         |
| `afterLast()`   | Move beyond last row |

### 6.2 Data Retrieval Methods

| Method                 | Returns       |
| ---------------------- | ------------- |
| `getInt(column)`       | int           |
| `getString(column)`    | String        |
| `getBoolean(column)`   | boolean       |
| `getDouble(column)`    | double        |
| `getDate(column)`      | java.sql.Date |
| `getTimestamp(column)` | Timestamp     |
| `getObject(column)`    | Object        |

Example:

```java
int id = rs.getInt("id");
String name = rs.getString("name");
```

### 6.3 Metadata Methods

Useful for dynamic result processing.

```java
ResultSetMetaData meta = rs.getMetaData();
int count = meta.getColumnCount();
```

---

## 7. Important Metadata Classes

### 7.1 DatabaseMetaData

Provides information **about the database**.

Example:

```java
DatabaseMetaData dm = con.getMetaData();
System.out.println(dm.getDatabaseProductName());
```

### 7.2 ResultSetMetaData

Provides information **about columns in a ResultSet**.

Example:

```java
ResultSetMetaData rsmd = rs.getMetaData();
System.out.println(rsmd.getColumnName(1));
```

---

## 8. Common JDBC Exception Methods (SQLException)

| Method               | Description           |
| -------------------- | --------------------- |
| `getMessage()`       | Error message         |
| `getSQLState()`      | SQL error code        |
| `getErrorCode()`     | Vendor-specific error |
| `getNextException()` | Chain of exceptions   |

---

## 9. Summary Table of JDBC Methods

### Statement Methods

| Method            | Use                  |
| ----------------- | -------------------- |
| `executeQuery()`  | SELECT               |
| `executeUpdate()` | INSERT/UPDATE/DELETE |
| `execute()`       | General queries      |
| `close()`         | Close statement      |

---

### PreparedStatement Methods

| Method            | Use                  |
| ----------------- | -------------------- |
| `setXXX()`        | Bind parameters      |
| `executeQuery()`  | SELECT               |
| `executeUpdate()` | INSERT/UPDATE/DELETE |
| `execute()`       | Mixed-result queries |

---

### ResultSet Methods

| Method              | Use                |
| ------------------- | ------------------ |
| `next()`            | Move cursor        |
| `getXXX()`          | Retrieve data      |
| `last()`, `first()` | Navigation         |
| `getMetaData()`     | Column information |
| `close()`           | Close resultset    |

---

### Connection Methods

| Method               | Use                  |
| -------------------- | -------------------- |
| `createStatement()`  | Statement creation   |
| `prepareStatement()` | PreparedStatement    |
| `prepareCall()`      | CallableStatement    |
| `commit()`           | Commit transaction   |
| `rollback()`         | Rollback transaction |

---

## 10. Complete Example Using All Methods

```java
Connection con = DriverManager.getConnection(url, user, pass);

PreparedStatement ps = con.prepareStatement(
    "SELECT id, name FROM students WHERE age > ?"
);

ps.setInt(1, 18);

ResultSet rs = ps.executeQuery();

while (rs.next()) {
    System.out.println(rs.getInt(1) + " - " + rs.getString(2));
}

rs.close();
ps.close();
con.close();
```

---

## Key Takeaways

* `executeQuery()` → SELECT

* `executeUpdate()` → INSERT/UPDATE/DELETE/DDL

* `execute()` → mixed results (ResultSet or update count)

* `PreparedStatement` is preferred for performance & security

* `ResultSet` contains navigational + data retrieval methods

* Connection manages transactions and statement creation

---
