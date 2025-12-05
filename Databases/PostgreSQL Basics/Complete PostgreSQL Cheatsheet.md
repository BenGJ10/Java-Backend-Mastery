# Complete PostgreSQL Cheat Sheet 

A clean, well-organized, exhaustive reference for developers, DBAs, and interview preparation.  
Covers everything from basic connection to advanced features like JSONB, partitioning, extensions, and performance tuning.


---

## 1. Connecting & psql Basics

| Command                              | Description                                      |
| ------------------------------------ | ------------------------------------------------ |
| `psql -U user -d dbname`             | Connect to database                              |
| `psql -h host -p 5432 -U user -d db` | Connect with host/port                           |
| `psql "postgres://user:pass@host:5432/db"` | Connection string (URI)                     |
| `\l`                                  | List databases                                   |
| `\c dbname`                           | Switch database                                  |
| `\du`                                 | List roles/users                                 |
| `\dt`                                 | List tables                                      |
| `\d name`                             | Describe table/view/index/sequence               |
| `\d+ name`                            | Describe with size & description                 |
| `\di` / `\dv` / `\df` / `\ds`         | List indexes / views / functions / sequences     |
| `\x`                                  | Toggle expanded display                         |
| `\?`                                  | Help for psql commands                           |
| `\q`                                  | Quit psql                                        |

---

## 2. Database Operations

```sql
CREATE DATABASE mydb;
CREATE DATABASE mydb WITH OWNER = admin ENCODING = 'UTF8';

DROP DATABASE mydb;

ALTER DATABASE old_name RENAME TO new_name;
ALTER DATABASE mydb OWNER TO new_owner;
```

---

## 3. Roles & Privileges

```sql
-- Create login user
CREATE ROLE ben WITH LOGIN PASSWORD 'secret';

-- Create non-login role
CREATE ROLE readonly;

-- Grant privileges
GRANT CONNECT ON DATABASE mydb TO ben;
GRANT USAGE ON SCHEMA public TO ben;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO ben;
GRANT ALL PRIVILEGES ON DATABASE mydb TO admin;

-- Default privileges for future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO readonly;

-- Revoke
REVOKE ALL ON DATABASE mydb FROM ben;

-- Change password
ALTER USER ben WITH PASSWORD 'newpass';
```

---

## 4. Schema Management

| Command                                      | Purpose                                                                      |
|----------------------------------------------|------------------------------------------------------------------------------|
| `CREATE SCHEMA sales;`                       | Create a new namespace for tables/views/functions                            |
| `DROP SCHEMA sales CASCADE;`                 | Delete schema and everything inside it                                       |
| `ALTER SCHEMA sales RENAME TO marketing;`    | Rename schema                                                                |
| `SET search_path TO marketing, public;`      | Change default schema search order (affects unqualified table names)         |

---

## 5. Table Operations

### Create Table
```sql
CREATE TABLE users (
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    name          TEXT,
    is_active     BOOLEAN DEFAULT true,
    metadata      JSONB,
    created_at    TIMESTAMPTZ DEFAULT NOW(),
    updated_at    TIMESTAMPTZ DEFAULT NOW()
);
```

### Common ALTER TABLE Commands

| Command                                                                     | Explanation                                                                 |
|-----------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| `CREATE TABLE users (id BIGSERIAL PRIMARY KEY, email TEXT UNIQUE NOT NULL);`| Creates table with auto-increment ID and unique email                       |
| `ALTER TABLE users ADD COLUMN phone VARCHAR(20);`                           | Add a new column                                                            |
| `ALTER TABLE users DROP COLUMN phone;`                                      | Remove a column                                                             |
| `ALTER TABLE users RENAME COLUMN name TO full_name;`                        | Rename column                                                               |
| `ALTER TABLE users ALTER COLUMN email SET NOT NULL;`                        | Add NOT NULL constraint                                                     |
| `ALTER TABLE users ADD CONSTRAINT positive_balance CHECK (balance >= 0);`   | Add check constraint                                                        |
| `TRUNCATE TABLE users RESTART IDENTITY CASCADE;`                            | Empty table, reset auto-increment, and cascade to foreign keys              |
| `DROP TABLE users CASCADE;`                                                 | Completely delete table and dependent objects                               |

### Truncate vs Drop
```sql
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
DROP TABLE users CASCADE;
```

---

## 6. Data Manipulation (DML)

| Operation       | Example                                                                 |
|-----------------|-------------------------------------------------------------------------|
| INSERT          | `INSERT INTO users (email, name) VALUES ('a@x.com', 'Alice');`          |
| Multi INSERT    | `INSERT INTO users (email) VALUES ('a@x.com'), ('b@x.com');`             |
| UPSERT (16+)    | `INSERT INTO users (id, email) VALUES (1,'a@x.com') ON CONFLICT (id) DO UPDATE SET email = EXCLUDED.email;` |
| UPDATE          | `UPDATE users SET is_active = false, updated_at = NOW() WHERE id = 1;`  |
| DELETE          | `DELETE FROM users WHERE is_active = false;`                            |
| Returning       | `DELETE FROM users WHERE id = 1 RETURNING *;`                           |

---

## 7. SELECT Mastery

```sql
SELECT name, email FROM users
WHERE is_active = true
  AND created_at >= '2025-01-01'
ORDER BY created_at DESC
LIMIT 50 OFFSET 100;

-- Distinct on
SELECT DISTINCT ON (category_id) * FROM products ORDER BY category_id, price DESC;

-- Case
SELECT name,
       CASE WHEN age < 18 THEN 'Minor'
       WHEN age < 65 THEN 'Adult'
       ELSE 'Senior' END AS age_group
FROM users;
```

---
## 8. JOIN Types

| Join Type           | Keeps                                      | Use Case                                           |
|---------------------|--------------------------------------------|----------------------------------------------------|
| INNER JOIN          | Only matching rows                         | Default when you want exact matches                |
| LEFT JOIN           | All rows from left table                   | Include all customers even if no orders            |
| RIGHT JOIN          | All rows from right table                  | Rare                                               |
| FULL OUTER JOIN     | All rows from both                         | Find mismatches in both directions                 |
| CROSS JOIN          | Cartesian product                          | Generating test data, combinations                 |
| LATERAL JOIN        | Subquery per row (like a loop)             | Get latest order per user                        |

---

## 9. Aggregations & Window Functions

```sql
SELECT 
    department,
    COUNT(*) AS emp_count,
    AVG(salary) AS avg_salary,
    MAX(salary) AS max_salary
FROM employees
GROUP BY department
HAVING COUNT(*) > 5
ORDER BY avg_salary DESC;

-- Window functions
SELECT 
    name, salary, department,
    RANK() OVER (PARTITION BY department ORDER BY salary DESC) AS rank_in_dept,
    NTILE(4) OVER (ORDER BY salary DESC) AS quartile
FROM employees;
```

---

## 10. Indexing Strategies

```sql
CREATE INDEX idx_users_email ON users(email);
CREATE UNIQUE INDEX idx_users_email_unique ON users(LOWER(email));

-- Composite
CREATE INDEX idx_orders_user_created ON orders(customer_id, created_at DESC);

-- Partial
CREATE INDEX idx_users_active ON users(email) WHERE is_active = true;

-- GIN for JSONB / arrays
CREATE INDEX idx_users_metadata ON users USING GIN (metadata);

-- BRIN for large sorted tables
CREATE INDEX idx_logs_timestamp ON logs USING BRIN(created_at);
```

Drop: `DROP INDEX CONCURRENTLY idx_name;`


---

## 11. Views & Materialized Views

```sql
-- Regular view
CREATE OR REPLACE VIEW active_customers AS
SELECT * FROM customers WHERE is_active = true;

-- Materialized view
CREATE MATERIALIZED VIEW daily_sales AS
SELECT date_trunc('day', created_at)::date AS day, SUM(amount) AS total
FROM orders
GROUP BY 1;

REFRESH MATERIALIZED VIEW CONCURRENTLY daily_sales;
```

---

## 12. JSON / JSONB Power Tools

```sql
-- Query
SELECT data->>'name' AS name, data->'specs'->>'cpu' AS cpu FROM products;

-- Containment
SELECT * FROM products WHERE data @> '{"brand": "Apple"}';

-- Key existence
SELECT * FROM products WHERE data ? 'discount';

-- Path query (JSONB)
SELECT jsonb_path_query(data, '$.specs.memory') FROM products;

-- Update JSONB field
UPDATE products SET data = jsonb_set(data, '{price}', '999') WHERE id = 1;
UPDATE products SET data = data || '{"in_stock": false}' WHERE id = 1;
```

---

## 13. Transactions & Savepoints

```sql
BEGIN;
  UPDATE accounts SET balance = balance - 100 WHERE id = 1;
  SAVEPOINT withdraw;
  UPDATE accounts SET balance = balance + 100 WHERE id = 2;
  -- Oops
  ROLLBACK TO withdraw;
COMMIT;
```

---

## 14. Functions & Stored Procedures (PL/pgSQL)

```sql
CREATE OR REPLACE FUNCTION calculate_tax(subtotal NUMERIC)
RETURNS NUMERIC AS $$
BEGIN
    RETURN subtotal * 0.08;
END;
$$ LANGUAGE plpgsql IMMUTABLE;

-- Procedure (PostgreSQL 11+)
CREATE PROCEDURE nightly_cleanup()
LANGUAGE SQL
AS $$
  DELETE FROM logs WHERE created_at < NOW() - INTERVAL '30 days';
$$;

CALL nightly_cleanup();
```

---

## 15. Useful Extensions

| Extension             | Install Command                        | Purpose                                  |
|-----------------------|----------------------------------------|------------------------------------------|
| `pg_stat_statements`  | `CREATE EXTENSION pg_stat_statements;` | Query performance monitoring             |
| `uuid-ossp`           | `CREATE EXTENSION "uuid-ossp";`        | `uuid_generate_v4()`                     |
| `postgis`             | `CREATE EXTENSION postgis;`            | Geographic/spatial data                  |
| `pgcrypto`            | `CREATE EXTENSION pgcrypto;`           | `gen_random_uuid()`, `crypt()`           |
| `unaccent`            | `CREATE EXTENSION unaccent;`           | Accent-insensitive search                |
| `pg_trgm`             | `CREATE EXTENSION pg_trgm;`            | Trigram similarity (`%` operator)        |

List installed: `\dx`

---

## 16. Backup & Restore

```bash
# Full DB dump
pg_dump -U user -Fc dbname > db.dump

# SQL format
pg_dump -U user dbname > backup.sql

# Single table
pg_dump -t users dbname > users.sql

# Restore
pg_restore -U user -d dbname --clean --create db.dump
psql -U user -d dbname < backup.sql
```

Compressed: `pg_dump dbname | gzip > backup.sql.gz`

---

## 17. Performance & Monitoring

```sql
EXPLAIN (ANALYZE, BUFFERS, VERBOSE) SELECT ...;
EXPLAIN (FORMAT JSON) SELECT ...;

-- Current activity
SELECT pid, query, state, query_start, now() - query_start AS duration
FROM pg_stat_activity
WHERE state = 'active';

-- Terminate query
SELECT pg_terminate_backend(1234);

-- Cancel query (softer)
SELECT pg_cancel_backend(1234);

-- Index usage
SELECT * FROM pg_stat_user_indexes ORDER BY idx_scan ASC;
```

---

## Quick Reference Summary

| Category               | Key Commands / Features                                 |
|------------------------|----------------------------------------------------------|
| Connect                | `psql`, `\l`, `\c`                                       |
| Inspect                | `\dt`, `\d+`, `\di`, `\du`                            |
| Privileges             | `GRANT`, `REVOKE`, `ALTER DEFAULT PRIVILEGES`            |
| JSONB                  | `->`, `->>`, `@>`, `?`, `jsonb_set`, GIN index            |
| Upsert                 | `ON CONFLICT ... DO UPDATE / NOTHING`                    |
| Partitioning (10+)     | `CREATE TABLE ... PARTITION BY RANGE/LIST/HASH`          |
| Materialized Views     | `CREATE MATERIALIZED VIEW ... REFRESH CONCURRENTLY`      |
| Extensions             | `CREATE EXTENSION`, `\dx`                                |
| Backup                 | `pg_dump`, `pg_restore`                                  |

---