# 04 - Database Configuration

---

# 1. Objective

This document explains how the PlacementPro AI Platform connects to the MySQL database using Spring Boot.

It covers database configuration, JPA, Hibernate, and the purpose of each configuration property used in the project.

---

# 2. Why Do We Need a Database?

A database is used to permanently store application data.

Without a database, data would be lost whenever the application stops.

Examples of data stored in PlacementPro:

- User Information
- Student Profiles
- Admin Accounts
- Placement Materials
- Quiz Questions
- Company Information
- Progress Reports

---

# 3. Why MySQL?

We selected MySQL because it is:

- Open-source
- Reliable
- Easy to integrate with Spring Boot
- Industry standard relational database
- Suitable for small and enterprise applications

---

# 4. Technologies Used

| Technology | Purpose |
|------------|---------|
| MySQL | Database |
| Spring Data JPA | Data Access Layer |
| Hibernate | ORM Framework |
| JDBC | Connects Java with MySQL |

---

# 5. What is JDBC?

JDBC (Java Database Connectivity) is a Java API used to connect Java applications with databases.

Without JDBC:

Java ❌ Database

With JDBC:

Java

↓

JDBC Driver

↓

MySQL Database

Spring Boot internally uses JDBC.

Developers usually don't write JDBC code directly because Spring Data JPA handles it automatically.

---

# 6. What is JPA?

JPA (Java Persistence API) is a Java specification that defines how Java objects are stored and retrieved from relational databases.

Instead of writing SQL manually:

```sql
INSERT INTO users (...)
```

We simply write:

```java
userRepository.save(user);
```

JPA performs the database operation automatically.

---

# 7. What is Hibernate?

Hibernate is the default implementation of JPA used by Spring Boot.

Responsibilities:

- Converts Java Objects into Database Records.
- Converts Database Records into Java Objects.
- Generates SQL queries.
- Manages Entity lifecycle.

Flow:

```
Java Object

↓

Hibernate

↓

SQL Query

↓

MySQL
```

---

# 8. Database Configuration File

Spring Boot stores database configuration inside:

```
src/main/resources/application.properties
```

Current configuration:

```properties
spring.application.name=placementpro

spring.datasource.url=jdbc:mysql://localhost:3306/placementpro
spring.datasource.username=root
spring.datasource.password=********

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

# 9. Understanding Each Property

## spring.application.name

```properties
spring.application.name=placementpro
```

Defines the application name.

---

## spring.datasource.url

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/placementpro
```

Breakdown:

```
jdbc

↓

Java Database Connectivity

↓

mysql

↓

Database Type

↓

localhost

↓

Current Computer

↓

3306

↓

MySQL Default Port

↓

placementpro

↓

Database Name
```

---

## spring.datasource.username

Database username.

Example:

```properties
spring.datasource.username=root
```

---

## spring.datasource.password

Database password.

Example:

```properties
spring.datasource.password=********
```

Never expose passwords in public repositories.

For production projects, use environment variables or external configuration.

---

## spring.jpa.hibernate.ddl-auto

```properties
spring.jpa.hibernate.ddl-auto=update
```

Purpose:

Controls how Hibernate manages database tables.

Available values:

### create

Deletes old tables and creates new ones every time.

Used only for testing.

---

### create-drop

Creates tables on startup.

Deletes tables when the application stops.

---

### update

Updates existing tables without deleting data.

Best for development.

(Current project uses this.)

---

### validate

Checks whether database tables match Entities.

Does not modify tables.

---

### none

Hibernate performs no schema management.

---

# 10. spring.jpa.show-sql

```properties
spring.jpa.show-sql=true
```

Displays generated SQL queries in the console.

Useful during development and debugging.

---

# 11. spring.jpa.properties.hibernate.format_sql

```properties
spring.jpa.properties.hibernate.format_sql=true
```

Formats SQL output to improve readability.

---

# 12. Database Connection Flow

```
Spring Boot

↓

application.properties

↓

DataSource

↓

JDBC Driver

↓

Hibernate

↓

MySQL Database
```

---

# 13. Common Errors

## Failed to configure DataSource

Reason:

Database configuration missing.

Solution:

Verify datasource properties.

---

## Database Does Not Exist

Reason:

Specified database has not been created.

Solution:

```sql
CREATE DATABASE placementpro;
```

---

## Wrong Password

Reason:

Incorrect MySQL credentials.

Solution:

Update username and password.

---

## MySQL Service Not Running

Reason:

Database server is stopped.

Solution:

Start MySQL service.

---

## Driver Not Found

Reason:

MySQL dependency missing.

Solution:

Add MySQL Driver dependency in Maven.

---

# 14. Best Practices

- Never commit production passwords.
- Use environment variables for sensitive information.
- Use `update` only during development.
- Use `validate` or migration tools (Flyway/Liquibase) in production.
- Keep database configuration outside source code for deployed applications.

---

# 15. What I Learned

After completing database configuration, I learned:

- How Spring Boot connects to MySQL.
- Purpose of JDBC.
- Difference between JPA and Hibernate.
- Meaning of each datasource property.
- Purpose of `ddl-auto`.
- How Hibernate generates SQL automatically.
- Common database connection errors and solutions.

---

# 16. Interview Questions

### Q1. What is JDBC?

### Q2. What is JPA?

### Q3. What is Hibernate?

### Q4. Difference between JPA and Hibernate?

### Q5. What is DataSource?

### Q6. Explain `spring.datasource.url`.

### Q7. Difference between `create`, `update`, `validate`, and `none`.

### Q8. Why use `show-sql=true`?

### Q9. What happens when Spring Boot starts?

### Q10. How does Spring Boot connect to MySQL?

---

# 17. Revision Summary

```
Application Starts
        │
        ▼
application.properties
        │
        ▼
DataSource Created
        │
        ▼
JDBC Driver
        │
        ▼
Hibernate
        │
        ▼
MySQL Database
```

---

# Conclusion

Database configuration is the foundation of every Spring Boot application. Spring Boot simplifies database connectivity through DataSource, JDBC, JPA, and Hibernate. By configuring a few properties, the application can automatically connect to MySQL, manage entities, and perform database operations with minimal boilerplate code.