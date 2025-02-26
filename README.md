# CRUD Spring Boot Project

This is a simple CRUD (Create, Read, Update, Delete) application built using Spring Boot. The project demonstrates how to implement RESTful APIs with Spring Boot, JPA, and MySQL.

## Features

- **User Management**: Create, read, update, and delete users.
- **Spring Boot REST API**: Exposes endpoints for CRUD operations.
- **Database Integration**: Uses MySQL and JPA for data persistence.
- **Swagger Documentation**: API documentation with Swagger UI.
- **Exception Handling**: Centralized error handling.
- **Logging**: Integrated logging using SLF4J and Logback.

## Tech Stack

- **Backend**: Spring Boot, Spring Data JPA
- **Database**: MySQL
- **API Documentation**: Swagger
- **Build Tool**: Maven
- **Authentication**: Spring Security (Optional)

## Installation

### Prerequisites

- Java 17+
- Maven
- MySQL

### Steps

1. Clone the repository:
   ```sh
   git clone https://github.com/kwesijay1/crud-springboot.git
   cd crud-springboot
   ```
2. Configure the application:
   - Update the `application.properties` file with your MySQL credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/crud_db
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     ```
3. Build the project:
   ```sh
   mvn clean install
   ```
4. Run the application:
   ```sh
   mvn spring-boot:run
   ```
5. Access the API at `http://localhost:8080/api/users`
6. View API documentation at `http://localhost:8080/swagger-ui.html`

## API Endpoints

| Method | Endpoint         | Description         |
|--------|-----------------|---------------------|
| GET    | `/api/users`    | Get all users      |
| GET    | `/api/users/{id}` | Get user by ID    |
| POST   | `/api/users`    | Create a new user  |
| PUT    | `/api/users/{id}` | Update user       |
| DELETE | `/api/users/{id}` | Delete user       |

## Contribution

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`feature-branch`).
3. Commit your changes.
4. Push to your fork and create a pull request.

## License

This project is licensed under the MIT License.

