# Encapsulation in Java

## 1. What is Encapsulation?

Encapsulation is the **process of wrapping data (variables) and methods (functions)** into a single unit — a **class**.
It is one of the fundamental pillars of OOP.

It also ensures **data hiding**, meaning internal details of an object are hidden from the outside world.

In simple terms:

* Keep **data private**
* Provide **public getters and setters** to access/modify it safely

---

## 2. Why Encapsulation is Needed

### Key Reasons:

1. **Data Protection**
   Prevents direct access to sensitive fields.

2. **Controlled Access**
   You can validate values before setting them.

3. **Loose Coupling**
   Internal implementation can change without affecting other code.

4. **Better Maintainability**
   Focus on clean, modular design.

---

## 3. How Encapsulation Works in Java

Encapsulation is implemented using:

1. **Private variables**
2. **Public getters and setters**

### Example:

```java
class BankAccount {
    private double balance;     // hidden data
    private String accountHolder;

    public double getBalance() {          // getter
        return balance;
    }

    public void setBalance(double balance) {  // setter with validation
        if (balance >= 0) {
            this.balance = balance;
        }
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String name) {
        if (name != null && !name.isEmpty()) {
            this.accountHolder = name;
        }
    }
}
```

Usage:

```java
BankAccount acc = new BankAccount();
acc.setBalance(5000);
System.out.println(acc.getBalance());
```

---

## 4. Data Hiding vs Encapsulation

These concepts are related but not identical.

| Concept           | Meaning                                                     |
| ----------------- | ----------------------------------------------------------- |
| **Data Hiding**   | Restricting access to internal data using private modifiers |
| **Encapsulation** | Grouping data + operations inside a class                   |

Encapsulation *achieves* data hiding.

---

## 5. Encapsulation Memory Flow

```
+----------------------------+
|        BankAccount         |
+----------------------------+
| private double balance     |   ← Hidden from outside
| private String holderName  |
+----------------------------+
| getBalance()               |
| setBalance()               |   ← Controlled access
+----------------------------+
```

External classes cannot directly modify variables:

```java
acc.balance = -500;   // ❌ Not allowed
```

They must use setters:

```java
acc.setBalance(1000); // ✔ Controlled access
```

---

## 6. Real-World Example

### Example: Student Details

```java
class Student {
    private String name;
    private int age;

    public void setAge(int age) {
        if (age > 0 && age < 100) {    // validation
            this.age = age;
        }
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        if (name != null && !name.isBlank()) {
            this.name = name;
        }
    }

    public String getName() {
        return name;
    }
}
```

By using getters and setters, the class maintains internal consistency.

---

## 7. Advantages of Encapsulation

| Advantage           | Description                                   |
| ------------------- | --------------------------------------------- |
| **Security**        | Sensitive data cannot be accessed directly    |
| **Flexibility**     | Change implementation without affecting users |
| **Validation**      | Enforce restrictions on data                  |
| **Maintainability** | Clean and organized code                      |
| **Reusability**     | Encapsulated components can be reused easily  |

---

## 8. Encapsulation in Java API

Java uses encapsulation heavily:

* `String` class → private `value[]`

* `ArrayList` → private `elementData[]`

* `HashMap` → private internal table

Example (from JDK source):

```java
public class String {
    private final byte[] value;
}
```

Operations are performed through public methods — not by modifying internal fields.

---

## 9. Common Interview Questions

### Q1: What is Encapsulation?

Encapsulation is the wrapping of data and methods into a single unit, and restricting direct access to data using access modifiers.

### Q2: How does encapsulation increase security?

By keeping fields private and exposing only safe operations.

### Q3: Is encapsulation possible without getters/setters?

Yes, technically — any class with private fields is encapsulated.
Getters/setters simply *provide controlled access*.

### Q4: What is the difference between encapsulation and abstraction?

* Encapsulation → *How* data is protected
* Abstraction → *What* details are shown/hidden from user

### Q5: Why do we need private variables?

To prevent unauthorized or invalid modifications.

---

## Key Takeaways

* Encapsulation hides internal data and exposes only needed details.

* It is achieved using private fields + public getters & setters.

* It ensures security, validation, and cleaner code.

* Major Java classes internally rely on encapsulation.

* Encapsulation does not mean only data hiding — it includes bundling data + behavior.

---

