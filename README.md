# JavaWebMVC - Spring Boot 4

Assignment 05 - Java Web MVC using Spring Boot 4 and JSP/JSTL.

## Technologies

Java 17, Spring Boot 4, Spring MVC, Spring Security, Spring Data JPA, JSP/JSTL, Maven, H2 (optional SQL Server).

## Features

- User registration, login and logout.
- Role-based access control (ADMIN / USER).
- Admin CRUD for users and categories.
- Search, pagination and form validation.
- BCrypt password hashing.

## Run

```bash
mvn clean package
java -jar target/JavaWebMVC-SpringBoot4.war
```

Open http://localhost:8081. The default local database is H2.

Default local admin: `admin` / `Admin@123` (development only).

## Pages

- `/register` - Create account
- `/login` - Sign in
- `/admin/users` - Manage users (ADMIN)
- `/admin/categories` - Manage categories (ADMIN)
