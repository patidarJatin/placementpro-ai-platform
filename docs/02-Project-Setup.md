# 02 - Project Setup

---

# 1. Objective

The objective of this document is to explain how the PlacementPro AI Platform was created from scratch. It serves as a setup guide so that the project can be recreated at any time without referring to external tutorials.

This document also records the tools, technologies, configurations, and best practices used during the initial project setup.

---

# 2. Development Environment

| Component | Technology |
|-----------|------------|
| Operating System | Windows 10 |
| Backend IDE | IntelliJ IDEA Community Edition |
| Frontend IDE | Visual Studio Code |
| Programming Language | Java 21 |
| Build Tool | Maven |
| Backend Framework | Spring Boot |
| Database | MySQL 8.x |
| API Testing Tool | Postman |
| Version Control | Git |
| Remote Repository | GitHub |

---

# 3. Backend Setup

The backend was created using **Spring Initializr**.

### Spring Initializr Configuration

| Property | Value |
|----------|-------|
| Project | Maven |
| Language | Java |
| Spring Boot | (Current Version) |
| Group | com.jatinpatidar |
| Artifact | placementpro |
| Name | placementpro |
| Package Name | com.jatinpatidar.placementpro |
| Packaging | Jar |
| Java Version | 21 |

---

## Dependencies Used

### Spring Web

Used to build REST APIs.

---

### Spring Data JPA

Used to communicate with the MySQL database using Java objects.

---

### MySQL Driver

Allows Spring Boot to connect with the MySQL database.

---

### Validation

Provides annotations such as:

- @NotBlank
- @Email
- @Size
- @Pattern

for validating client requests.

---

### Lombok

Reduces boilerplate code by generating:

- Getters
- Setters
- Constructors
- Other utility methods

during compilation.

---

# 4. Frontend Setup

The frontend is developed separately from the backend.

### Frontend Technology

- React
- JavaScript
- Vite
- VS Code

Keeping frontend and backend separate follows modern software architecture and allows both applications to be developed and deployed independently.

---

# 5. Database Setup

### Database

```
Database Name : placementpro
Username      : root
Password      : ********
Database      : MySQL
```

Spring Boot connects to MySQL using the configuration in `application.properties`.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/placementpro
spring.datasource.username=root
spring.datasource.password=********

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

# 6. Git Repository Setup

The project uses Git for version control and GitHub for remote storage.

### Git Commands

```bash
git init

git add .

git commit -m "Initial Spring Boot project setup"

git branch -M main

git remote add origin <repository-url>

git push -u origin main
```

---

# 7. Initial Folder Structure

```
PlacementPro/
│
├── backend/
│
├── frontend/
│
├── docs/
│
├── README.md
│
└── .gitignore
```

---

# 8. Why These Technologies?

## Why Spring Boot?

- Rapid application development
- Production-ready features
- Large community support
- Industry standard for Java backend development

---

## Why Maven?

- Dependency management
- Easy project build process
- Standard project structure
- Widely used in enterprise applications

---

## Why MySQL?

- Open-source
- Reliable
- Easy integration with Spring Boot
- Popular relational database

---

## Why React?

- Component-based architecture
- Fast rendering
- Industry standard frontend library
- Easy integration with REST APIs

---

## Why Git & GitHub?

- Version control
- Collaboration
- Code backup
- Portfolio showcase

---

# 9. Project Verification

The following checks were performed after setup:

- Spring Boot application starts successfully.
- MySQL connection is established.
- Hibernate connects successfully.
- Database tables are created or updated.
- Application runs on port **8080**.
- Git repository is initialized.
- Project is successfully pushed to GitHub.

---

# 10. Common Errors Encountered

## Error 1

### Failed to configure DataSource

Reason

- Database URL missing
- MySQL not running
- Missing MySQL dependency

Solution

- Configure datasource properties.
- Start MySQL service.
- Verify database exists.

---

## Error 2

### Wrong Git Directory

Reason

Git was initialized inside the wrong folder.

Solution

Initialize Git from the project root folder.

---

## Error 3

### target Folder Added to Git

Reason

Missing `.gitignore` configuration.

Solution

Add:

```gitignore
target/
```

and remove the cached files if necessary.

---

## Error 4

### Password Visible in application.properties

Reason

Database credentials stored directly in source code.

Solution

Move sensitive values to environment variables or external configuration for production environments.

---

# 11. Best Practices

- Keep backend and frontend in separate folders.
- Use meaningful Git commit messages.
- Never commit sensitive credentials.
- Keep documentation updated.
- Follow layered architecture.
- Use constructor injection instead of field injection.
- Push code regularly to GitHub.

---

# 12. What I Learned

After completing the project setup, I learned:

- How to create a Spring Boot project using Spring Initializr.
- How to configure MySQL with Spring Boot.
- How Maven manages dependencies.
- Why backend and frontend are separated.
- How to initialize a Git repository.
- How to push a project to GitHub.
- How to solve common setup issues.
- Why documentation is important from the beginning of a project.

---

# 13. Interview Questions

### Q1. Why did you choose Spring Boot?

### Q2. Why did you choose Maven instead of Gradle?

### Q3. Why separate frontend and backend?

### Q4. Why use MySQL?

### Q5. Explain the setup process of your project.

### Q6. What is the purpose of `application.properties`?

### Q7. Why is `.gitignore` important?

### Q8. What is the role of Maven in a Spring Boot project?

### Q9. How do you verify that Spring Boot is connected to MySQL?

### Q10. What are some common project setup issues and how did you resolve them?

---

# 14. Revision Summary

```
Project Idea
        │
        ▼
Spring Initializr
        │
        ▼
Maven Project
        │
        ▼
Configure MySQL
        │
        ▼
Run Spring Boot
        │
        ▼
Initialize Git
        │
        ▼
Push to GitHub
        │
        ▼
Ready for Development
```

---

# Conclusion

The initial setup establishes a strong foundation for the PlacementPro AI Platform. By documenting every configuration, tool, dependency, and decision, future development becomes easier to understand, maintain, and reproduce. This document serves as both a setup guide and a revision resource for learning Spring Boot and full-stack development.