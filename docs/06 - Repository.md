# 06 - Repository

---

# 1. Objective

This document explains the Repository layer in the PlacementPro AI Platform.

It covers what a Repository is, why it is needed, how Spring Data JPA works internally, and how the UserRepository is implemented in this project.

By the end of this document, you should understand how Spring Boot communicates with the database without writing SQL manually.

---

# 2. What is a Repository?

A Repository is a component responsible for communicating with the database.

It performs CRUD (Create, Read, Update, Delete) operations and retrieves data from the database.

The Repository does **not** contain business logic.

It only performs database operations.

---

# 3. Why Do We Need a Repository?

Imagine writing SQL queries inside every Service.

Example:

SELECT * FROM users WHERE email = ?

INSERT INTO users (...)

UPDATE users (...)

DELETE FROM users (...)

This would make the application difficult to maintain.

Instead, Spring Data JPA provides Repository interfaces that automatically generate these database operations.

---

# 4. Responsibilities of a Repository

A Repository should only:

- Save data
- Retrieve data
- Update data
- Delete data
- Execute database queries

It should **not**:

- Validate passwords
- Check business rules
- Decide user roles
- Send emails

Those responsibilities belong to the Service layer.

---

# 5. Repository in PlacementPro

Current Repository:

```java
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
```

This Repository manages the User entity.

---

# 6. Understanding JpaRepository

```java
JpaRepository<User, Long>
```

Explanation:

User

↓

Entity Class

Long

↓

Primary Key Type

This tells Spring Boot:

"I want to manage User entities whose primary key is Long."

---

# 7. CRUD Methods Provided by JpaRepository

Without writing any code, JpaRepository provides:

```java
save(entity)
```

Insert or Update data.

---

```java
findById(id)
```

Find one record.

---

```java
findAll()
```

Retrieve all records.

---

```java
delete(entity)
```

Delete a record.

---

```java
existsById(id)
```

Check whether a record exists.

---

```java
count()
```

Count total records.

These methods are available automatically.

---

# 8. Custom Query Methods

Spring Boot can generate SQL queries simply by reading method names.

Example:

```java
Optional<User> findByEmail(String email);
```

Spring understands:

Find

↓

By

↓

Email

Generated SQL (conceptually):

```sql
SELECT * FROM users
WHERE email = ?
```

No SQL needs to be written manually.

---

# 9. Why Does findByEmail() Return Optional?

Possible situations:

Case 1

User exists.

Return:

```java
Optional<User>
```

containing the User.

---

Case 2

User does not exist.

Return:

```java
Optional.empty()
```

instead of null.

Benefits:

- Avoids NullPointerException.
- Forces developers to handle missing data explicitly.

---

# 10. How Repository Works Internally

This is one of the most important concepts.

You only wrote:

```java
public interface UserRepository extends JpaRepository<User, Long> {

}
```

Where is the implementation?

You never created:

```java
UserRepositoryImpl
```

So who implements it?

Answer:

Spring Data JPA.

Application Starts

↓

Component Scan

↓

Spring Finds UserRepository

↓

Spring Creates a Proxy Implementation

↓

Registers It as a Bean

↓

Injects It Wherever Needed

This happens automatically at runtime.

---

# 11. Internal Flow of save()

When you write:

```java
userRepository.save(user);
```

Internally:

```
Service

↓

UserRepository

↓

Spring Proxy

↓

Hibernate

↓

JDBC

↓

MySQL
```

Hibernate generates the required SQL automatically.

---

# 12. Internal Flow of findByEmail()

```
findByEmail()

↓

Spring Reads Method Name

↓

Creates SQL Query

↓

Executes Query

↓

Returns Optional<User>
```

This feature is called **Query Method Derivation**.

---

# 13. Why Don't We Write SQL?

Because Spring Data JPA already understands common operations.

Instead of:

```sql
SELECT *
FROM users
WHERE email = ?
```

We simply write:

```java
findByEmail(email);
```

This increases productivity and reduces boilerplate code.

---

# 14. Repository vs Service

Repository

- Talks to the database.
- Executes queries.
- No business logic.

Service

- Contains business logic.
- Validates rules.
- Calls Repository methods.

Example:

Registration Process

Service

↓

findByEmail()

↓

Repository

↓

Database

↓

Return Result

↓

Service Decides

↓

Register User or Throw Exception

---

# 15. Best Practices

✔ Keep Repository focused on data access.

✔ Return Optional when data may not exist.

✔ Prefer method name queries before custom SQL.

✔ Keep business logic in the Service layer.

✔ Use JpaRepository whenever possible.

---

# 16. Common Mistakes

❌ Writing business logic in Repository.

❌ Returning null instead of Optional.

❌ Writing unnecessary native SQL.

❌ Mixing validation with database operations.

---

# 17. What I Learned

After implementing UserRepository, I learned:

- What a Repository is.
- Why JpaRepository is used.
- How Spring generates Repository implementations.
- What Optional is.
- How query methods work.
- Difference between Repository and Service.
- Why we don't write SQL for common operations.

---

# 18. Interview Questions

Q1. What is a Repository?

Q2. What is JpaRepository?

Q3. Difference between CrudRepository and JpaRepository?

Q4. Who implements UserRepository?

Q5. What is a Spring Proxy?

Q6. Why does findByEmail() return Optional?

Q7. How does Spring generate SQL from method names?

Q8. Why shouldn't business logic be placed in Repository?

Q9. What happens internally when save() is called?

Q10. Explain the flow from Service to Database.

---

# 19. Revision Summary

```
Service
    │
    ▼
UserRepository
    │
    ▼
Spring Proxy
    │
    ▼
Hibernate
    │
    ▼
JDBC
    │
    ▼
MySQL Database
```

---

# Conclusion

The Repository layer is responsible only for database communication. Spring Data JPA automatically creates repository implementations, generates SQL queries from method names, and simplifies CRUD operations. Keeping business logic in the Service layer and database operations in the Repository layer results in clean, maintainable, and scalable applications.