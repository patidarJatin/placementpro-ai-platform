# 05 - Entity

---

# 1. Objective

This document explains the Entity layer in the PlacementPro AI Platform.

It covers what an Entity is, why it is used, how Spring Boot manages entities, and the annotations used in the User entity.

By the end of this document, you should understand how Java objects are mapped to database tables using JPA.

---

# 2. What is an Entity?

An Entity is a Java class that represents a table in the database.

Each object of an Entity represents one row (record) in that table.

Example:

User.java

↓

users Table

| id | full_name | email | password |
|----|-----------|-------|----------|
| 1 | Jatin | abc@gmail.com | ******** |

One object = One row in the database.

---

# 3. Why Do We Need an Entity?

Without an Entity:

- We would write SQL manually.
- We would manually convert database records into Java objects.
- Code would become lengthy and difficult to maintain.

With an Entity:

Spring Boot automatically maps Java objects to database tables using JPA and Hibernate.

Instead of writing:

INSERT INTO users (...)

We simply write:

userRepository.save(user);

Hibernate generates the SQL automatically.

---

# 4. Real-World Example

Imagine a school.

Student Admission Form

↓

Student Object

↓

Student Database Table

Similarly,

User Object

↓

users Database Table

The Entity acts as a bridge between Java objects and the database.

---

# 5. Entity Lifecycle

```
Java Object

↓

JPA

↓

Hibernate

↓

SQL Query

↓

MySQL Database
```

When retrieving data:

```
MySQL Database

↓

Hibernate

↓

JPA

↓

Java Object
```

---

# 6. User Entity in PlacementPro

The User entity represents the users table.

Fields:

- id
- fullName
- email
- password
- role
- createdAt
- updatedAt

Each field becomes a column in the database.

---

# 7. Understanding Every Annotation

## @Entity

Purpose:

Marks the class as a JPA Entity.

Example:

```java
@Entity
public class User {

}
```

Without @Entity:

Spring Boot ignores the class.

---

## @Table(name = "users")

Purpose:

Maps the entity to the users table.

Example:

```java
@Table(name = "users")
```

If omitted:

Hibernate uses the class name by default.

---

## @Id

Purpose:

Defines the Primary Key.

Every Entity must have one unique identifier.

Example:

```java
@Id
private Long id;
```

---

## @GeneratedValue(strategy = GenerationType.IDENTITY)

Purpose:

Allows MySQL to generate IDs automatically.

Example:

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

Database:

1

2

3

4

...

Developers do not assign IDs manually.

---

## @Column

Purpose:

Customizes the database column.

Examples:

```java
@Column(nullable = false)

@Column(unique = true)

@Column(length = 100)
```

Common Attributes:

nullable = false

Prevents NULL values.

unique = true

Prevents duplicate values.

length = 100

Defines maximum column length.

---

## @Enumerated(EnumType.STRING)

Purpose:

Stores enum values as text.

Example:

```java
public enum Role {

    STUDENT,

    ADMIN
}
```

Database:

Correct:

```
ADMIN

STUDENT
```

Wrong (Ordinal):

```
0

1
```

Using STRING makes the database readable and prevents issues if enum order changes.

---

## @PrePersist

Purpose:

Runs automatically before inserting a new record.

Used for:

createdAt

updatedAt

Example:

```java
@PrePersist
public void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
}
```

---

## @PreUpdate

Purpose:

Runs automatically before updating an existing record.

Example:

```java
@PreUpdate
public void onUpdate() {
    updatedAt = LocalDateTime.now();
}
```

This keeps timestamps updated automatically.

---

# 8. Why Did We Use LocalDateTime?

Instead of:

String

We used:

LocalDateTime

Reasons:

- Stores date and time together.
- Easy to compare.
- Easy to format.
- Supported by JPA.
- Industry standard.

---

# 9. Why Did We Use Lombok?

Without Lombok:

Developers manually write:

- Getters
- Setters
- Constructors

With Lombok:

```java
@Getter
@Setter
@NoArgsConstructor
```

This reduces boilerplate code.

---

# 10. Why Didn't We Use @Data?

@Data generates:

- Getter
- Setter
- toString()
- equals()
- hashCode()
- Required constructor

For JPA Entities this may cause problems because:

- equals() and hashCode() may behave unexpectedly before the entity has an ID.
- toString() can accidentally access lazy-loaded relationships and hurt performance or cause recursion in complex entities.

Industry practice:

Use:

@Getter

@Setter

@NoArgsConstructor

instead of @Data for Entities.

---

# 11. Why Is Validation Used?

Example:

```java
@NotBlank

@Email

@Size
```

Purpose:

Prevent invalid data before it reaches the database.

Example:

Email cannot be empty.

Password must contain at least 8 characters.

Name must have a minimum length.

---

# 12. Why Not Put Business Logic Inside Entity?

Entity responsibility:

Represent database structure.

Business logic belongs in:

Service Layer

Example:

Wrong:

```
if(password.length()<8)
```

inside Entity.

Correct:

Validation and business decisions happen before saving, typically in DTO validation and the Service layer.

---

# 13. Entity vs DTO

Entity

- Represents database table.
- Used internally.
- Managed by JPA.

DTO

- Represents API request/response.
- Used between client and server.
- Not stored in the database.

---

# 14. Best Practices

✔ One Entity = One Table

✔ Use Long for IDs.

✔ Keep Entity focused on persistence.

✔ Use EnumType.STRING.

✔ Use LocalDateTime for timestamps.

✔ Use constructor injection in services, not in entities.

✔ Use Lombok carefully.

✔ Keep sensitive fields protected from API responses by using DTOs.

---

# 15. Common Mistakes

❌ Using @Data on Entities.

❌ Returning Entity directly from APIs.

❌ Storing passwords in plain text.

❌ Using EnumType.ORDINAL.

❌ Writing business logic inside Entity.

❌ Forgetting @NoArgsConstructor.

❌ Missing @Id.

---

# 16. Our User Entity

Current Fields:

- id
- fullName
- email
- password
- role
- createdAt
- updatedAt

Future Fields:

- isVerified
- profileImage
- phoneNumber
- refreshToken

These will be added as the project grows.

---

# 17. What I Learned

After creating the User Entity, I learned:

- What an Entity is.
- How JPA maps Java objects to database tables.
- Purpose of every JPA annotation.
- Difference between Entity and DTO.
- Why @Data is avoided.
- Why timestamps are automated.
- Why enums are stored as STRING.
- Why Entities should not contain business logic.

---

# 18. Interview Questions

Q1. What is an Entity?

Q2. Why do we use @Entity?

Q3. Difference between Entity and DTO?

Q4. Why use @Table?

Q5. What is @GeneratedValue?

Q6. Why use GenerationType.IDENTITY?

Q7. Why use EnumType.STRING?

Q8. Why avoid @Data on Entities?

Q9. Difference between @PrePersist and @PreUpdate?

Q10. Why use LocalDateTime?

Q11. Why use @Column?

Q12. What is the role of Hibernate in Entity mapping?

---

# 19. Revision Summary

```
Java Class
      │
      ▼
@Entity
      │
      ▼
JPA
      │
      ▼
Hibernate
      │
      ▼
users Table
      │
      ▼
Database Record
```

---

# Conclusion

The Entity layer is the foundation of data persistence in Spring Boot. It maps Java objects to database tables and allows developers to work with objects instead of SQL statements. By keeping Entities focused on database representation and moving business logic to the Service layer, applications become cleaner, easier to maintain, and aligned with industry best practices.