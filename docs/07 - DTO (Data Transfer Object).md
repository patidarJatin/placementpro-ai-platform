# 07 - DTO (Data Transfer Object)

---

# 1. Objective

This document explains the Data Transfer Object (DTO) layer used in the PlacementPro AI Platform.

It covers what a DTO is, why it is needed, how it differs from an Entity, and why modern Spring Boot applications use DTOs instead of exposing entities directly.

By the end of this document, you should understand how DTOs improve security, maintainability, and API design.

---

# 2. What is a DTO?

DTO stands for **Data Transfer Object**.

A DTO is a simple Java class used to transfer data between different layers of an application, especially between the client (frontend) and the backend.

Unlike an Entity, a DTO is **not mapped to a database table**.

Its only responsibility is to carry data.

---

# 3. Why Do We Need DTOs?

Imagine a User entity.

```java
User

id
fullName
email
password
role
createdAt
updatedAt
```

When a user registers, does the frontend need all these fields?

No.

The frontend only sends:

```text
fullName

email

password
```

Therefore, we create a DTO:

```java
RegisterRequest
```

which contains only the required fields.

---

# 4. Real-World Example

Imagine ordering food.

Customer fills an order form.

↓

Restaurant receives only the order details.

↓

Kitchen prepares the food.

The customer never sees the kitchen's internal system.

Similarly,

Frontend

↓

DTO

↓

Service

↓

Entity

↓

Database

The DTO acts like an order form.

---

# 5. DTO vs Entity

| Entity | DTO |
|---------|-----|
| Represents a database table | Represents request/response data |
| Managed by JPA | Not managed by JPA |
| Stored in database | Not stored in database |
| Contains persistence-related fields | Contains only required fields |
| Used internally | Used between client and server |

---

# 6. Why Not Send Entity Directly?

Suppose our User entity contains:

```java
private String password;

private Role role;

private LocalDateTime createdAt;
```

If we expose the Entity directly,

the client could receive unnecessary or sensitive information.

Problems:

- Password exposed
- Internal fields exposed
- Database structure leaked
- Difficult to change database design

DTO solves all these problems.

---

# 7. Types of DTOs

## Request DTO

Used when the client sends data.

Example:

```java
RegisterRequest
```

Fields:

- fullName
- email
- password

---

## Response DTO

Used when the server sends data.

Example:

```java
UserResponse
```

Fields:

- id
- fullName
- email
- role

Notice:

Password is NOT included.

---

# 8. RegisterRequest Used in PlacementPro

Current DTO:

```java
public class RegisterRequest {

    private String fullName;

    private String email;

    private String password;

}
```

Purpose:

Collect registration information from the frontend.

---

# 9. Validation in DTO

Validation ensures that invalid data never reaches the Service layer.

Example:

```java
@NotBlank

@Email

@Size
```

Example:

```java
@NotBlank(message="Email is required")

@Email(message="Enter a valid email")
```

This validates user input before business logic executes.

---

# 10. Why Validation is Placed in DTO

Entity represents the database.

DTO represents client input.

Validation belongs where data enters the application.

Flow:

Client

↓

DTO Validation

↓

Controller

↓

Service

↓

Repository

↓

Database

This prevents invalid requests from entering the application.

---

# 11. DTO Mapping

A DTO cannot be saved directly.

It must first be converted into an Entity.

Example:

```
RegisterRequest

↓

User Entity

↓

Database
```

This conversion is called **Mapping**.

---

# 12. Mapping Techniques

### Manual Mapping

```java
User user = new User();

user.setFullName(request.getFullName());

user.setEmail(request.getEmail());

user.setPassword(request.getPassword());
```

Advantages:

- Easy to understand
- No additional library
- Full control

Used while learning and in small projects.

---

### ModelMapper

Automatically maps fields with matching names.

Advantages:

- Less code

Disadvantages:

- Reflection-based
- Slightly slower

---

### MapStruct

Generates mapping code during compilation.

Advantages:

- High performance
- Type-safe
- Preferred in many enterprise applications

---

# 13. Current Mapping Strategy

PlacementPro currently uses:

Manual Mapping

Reason:

Understanding the mapping process is more important than using automation libraries during learning.

Future versions may use MapStruct.

---

# 14. Industry Best Practices

✔ Use separate DTOs for Request and Response.

✔ Never expose Entity directly.

✔ Validate incoming data.

✔ Keep DTOs simple.

✔ Use MapStruct in large projects.

✔ Do not place business logic inside DTOs.

---

# 15. Common Mistakes

❌ Returning Entity directly from Controller.

❌ Putting database annotations inside DTO.

❌ Adding business logic inside DTO.

❌ Reusing one DTO for every API.

❌ Exposing passwords in Response DTO.

---

# 16. DTO Flow in PlacementPro

```
Frontend

↓

RegisterRequest DTO

↓

Controller

↓

UserService

↓

User Entity

↓

Repository

↓

Database
```

---

# 17. What I Learned

After implementing RegisterRequest, I learned:

- What a DTO is.
- Why DTOs are used.
- Difference between Entity and DTO.
- Why validation belongs in DTO.
- Difference between Request DTO and Response DTO.
- Different mapping techniques.
- Why manual mapping is useful while learning.

---

# 18. Interview Questions

Q1. What is a DTO?

Q2. Why do we need DTOs?

Q3. Difference between Entity and DTO?

Q4. Why should we not expose Entities directly?

Q5. What is Request DTO?

Q6. What is Response DTO?

Q7. Why is validation placed in DTO?

Q8. What is DTO Mapping?

Q9. Difference between Manual Mapping, ModelMapper, and MapStruct?

Q10. Which mapping technique would you use in a production application and why?

---

# 19. Revision Summary

```
Frontend
      │
      ▼
Request DTO
      │
      ▼
Controller
      │
      ▼
Service
      │
      ▼
Entity
      │
      ▼
Repository
      │
      ▼
Database
```

---

# Conclusion

DTOs provide a clean separation between the client and the database model. They improve security, simplify API design, and make applications easier to maintain. By using Request DTOs for incoming data and Response DTOs for outgoing data, Spring Boot applications remain flexible, secure, and aligned with industry best practices.