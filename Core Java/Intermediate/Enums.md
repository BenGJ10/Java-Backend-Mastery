# Enums in Java

## 1. What is an enum?

An **enum (enumeration)** in Java is a special data type used to define a **fixed set of constants**.

Example uses:

* Days of the week
* Directions
* Order status
* User roles

Enums provide:

* Type safety
* Readability
* Organized grouping of constant values

---

## 2. Basic Syntax

```java
enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

Using the enum:

```java
Direction d = Direction.NORTH;
System.out.println(d);
```

Output:

```
NORTH
```

---

## 3. Why Use Enums?

### Advantages:

1. **Type-safe constants**
   You cannot assign invalid values.

2. **More powerful than `static final` variables**
   Enums can have:

   * Fields
   * Methods
   * Constructors

3. **Prevents bugs**
   Unlike integers/strings used as constants.

4. **Readable and maintainable code**

---

## 4. Enum is a Class (Special Type)

Internally, every enum extends:

```java
java.lang.Enum
```

Hence, enum values are **objects**, not primitive values.

Example:

```java
Direction.NORTH
```

is actually an **instance** of enum `Direction`.

---

## 5. Adding Fields and Methods to Enums

Enums can contain:

* Instance variables
* Constructors
* Methods

### Example: Enum with Constructor and Field

```java
enum Status {
    SUCCESS(200),
    ERROR(500),
    PENDING(102);

    private int code;

    Status(int code) {       // constructor
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
```

Usage:

```java
System.out.println(Status.SUCCESS.getCode()); // 200
```

---

## 6. Enum Methods

Enums come with built-in methods:

### 6.1 `values()`

Returns all enum constants as an array.

```java
for (Direction d : Direction.values()) {
    System.out.println(d);
}
```

---

### 6.2 `valueOf(String name)`

Converts a String to an enum constant.

```java
Direction d = Direction.valueOf("EAST");
```

---

### 6.3 `ordinal()`

Returns position (index) of constant.

```java
Direction.SOUTH.ordinal();  // 1
```

Index starts from 0.

---

## 7. Using enum in switch-case

Enums work in switch statements.

```java
Direction d = Direction.EAST;

switch(d) {
    case EAST:  System.out.println("Go Right"); break;
    case WEST:  System.out.println("Go Left"); break;
}
```

---

## 8. Enum with Abstract Methods

You can define abstract methods and let each constant implement it.

```java
enum Operation {
    ADD {
        int calculate(int a, int b) { return a + b; }
    },
    SUB {
        int calculate(int a, int b) { return a - b; }
    };

    abstract int calculate(int a, int b);
}
```

Usage:

```java
System.out.println(Operation.ADD.calculate(5, 3)); // 8
```

---

## 9. Enum Implementing Interfaces

Enums cannot extend classes (already extends `Enum`),
but **can implement interfaces**.

```java
interface Printable {
    void print();
}

enum Color implements Printable {
    RED, GREEN, BLUE;

    public void print() {
        System.out.println(this.name());
    }
}
```

---

## 10. Enum in Switch (More Complex Example)

```java
enum Level {
    LOW, MEDIUM, HIGH
}
```

```java
void check(Level l) {
    switch(l) {
        case LOW:    System.out.println("Low level"); break;
        case MEDIUM: System.out.println("Medium level"); break;
        case HIGH:   System.out.println("High level"); break;
    }
}
```

---

## 11. EnumSet (Highly Efficient Set for Enums)

Java provides **EnumSet** for fast operations on enums.

```java
EnumSet<Direction> set = EnumSet.of(Direction.NORTH, Direction.SOUTH);
```

* Faster than HashSet
* Uses bit-vector internally

---

## 12. EnumMap (Enum-Based Map)

```java
EnumMap<Direction, String> map = new EnumMap<>(Direction.class);
map.put(Direction.NORTH, "Up");
map.put(Direction.SOUTH, "Down");
```

* Very efficient
* Keys must be enum type

---

## 13. Enum vs static final constants

| Feature                     | enum     | static final |
| --------------------------- | -------- | ------------ |
| Type Safe                   | ✔        | ❌            |
| Can have methods            | ✔        | ❌            |
| Can group related constants | ✔        | ✔            |
| Compile-time checking       | ✔        | ❌            |
| Switch-case friendly        | ✔        | ✔            |
| Extensible                  | Moderate | Low          |

Enums are far superior for representing fixed sets.

---

## 14. Real-World Examples

### HTTP Status Codes

```java
enum HttpStatus {
    OK(200), NOT_FOUND(404), SERVER_ERROR(500);

    private int code;

    HttpStatus(int c) { code = c; }
    int getCode() { return code; }
}
```

---

### Order Status

```java
enum OrderStatus {
    PLACED, SHIPPED, DELIVERED, CANCELLED
}
```

---

### Directions

```java
enum Direction {
    NORTH, SOUTH, EAST, WEST
}
```

---

## 15. Common Interview Questions

### Q1. Can an enum extend a class?

No. All enums implicitly extend `java.lang.Enum`.

### Q2. Can enum implement interfaces?

Yes.

### Q3. Can we create our own constructor in enum?

Yes, but it must be **private**.

### Q4. Can enums have abstract methods?

Yes — each constant must implement them.

### Q5. Why use enum over constants?

Type-safety, clarity, and richer functionality.

---

## Key Takeaways

* Enums are special classes with fixed constants.

* They provide type safety and better structure.

* They can contain fields, constructors, methods.

* They can implement interfaces but cannot extend classes.

* Used widely in modern Java applications (Spring, JPA, APIs).

---

