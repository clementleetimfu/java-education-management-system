# Education Management System

A comprehensive backend system for education management built with Spring Boot, MyBatis, and MySQL.

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-green)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue)
![MyBatis](https://img.shields.io/badge/MyBatis-3.x-red)
![Maven](https://img.shields.io/badge/Maven-3.x-orange)
![JWT](https://img.shields.io/badge/JWT-black)
![Docker](https://img.shields.io/badge/Docker-blue)
![License](https://img.shields.io/badge/license-MIT-blue)

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Multi-Module Structure](#multi-module-structure)
- [API Endpoints](#api-endpoints)
  - [Authentication](#authentication)
  - [Students](#students)
  - [Employees](#employees)
  - [Classes](#classes)
  - [Departments](#departments)
  - [Subjects](#subjects)
  - [Job Titles](#job-titles)
  - [Education Levels](#education-levels)
  - [Activity Logs](#activity-logs)
  - [Upload](#upload)
- [Database Schema](#database-schema)
- [Authentication & Authorization](#authentication--authorization)
- [Environment Variables](#environment-variables)
- [Deployment](#deployment)
- [Getting Started](#getting-started)
- [Docker Support](#docker-support)
- [License](#license)

## Overview

The Education Management System is a RESTful API designed to manage various aspects of an educational institution including student records, employee information, class management, and administrative functions. The system provides secure authentication, data validation, and maintains detailed logs for certain activities.

This project follows a multi-module Maven structure to separate concerns and improve maintainability:

- `ems-parent`: Parent Maven project containing common configurations
- `ems-common`: Shared utilities and helper classes
- `ems-model`: Data models, entities, DTOs, and response wrappers
- `ems-service`: Main business logic with controllers, services, and data mappers

## Features

- **User Authentication**: JWT-based authentication
- **Student Management**: CRUD operations for student records with search capabilities
- **Employee Management**: CRUD operations for employee records with search capabilities
- **Class Management**: CRUD operations for class records with search capabilities
- **Department Management**: CRUD operations for department records
- **Subject Management**: CRUD operations for subject records
- **Activity Logging**: Automatic logging of important operations using AOP
- **File Upload**: Integration with Cloudflare R2 for file storage
- **Data Analytics**: Statistical reports on students and employees
- **Security**: Password hashing with BCrypt and JWT token validation

## Technology Stack

- **Backend Framework**: Spring Boot 3.5.7
- **Language**: Java 17
- **Build Tool**: Maven
- **Database**: MySQL
- **ORM**: MyBatis
- **Security**: JWT, BCrypt with Pepper
- **Storage**: Cloudflare R2 (AWS S3 compatible)
- **Utilities**: Lombok, ModelMapper, PageHelper

## Architecture

The system follows a layered architecture pattern with multi-module organization:

```
Client <-> Controller <-> Service <-> Mapper <-> Database
                 ↕
            Validation & DTOs
                 ↕
             Security Layer (JWT, Filter)
```

### Design Patterns

- **Layered Architecture**: Clear separation between Controllers, Services, and Data Access layers
- **Aspect-Oriented Programming**: Used for automatic activity logging
- **DTO Pattern**: Separation of internal entities and external API representations
- **Singleton Pattern**: Configuration beans and utility classes

## Multi-Module Structure

```
education-management-system/
├── ems-parent/                 # Parent Maven project
│   └── pom.xml                 # Parent POM with common configurations
├── ems-common/                 # Shared utilities
│   ├── src/
│   │   └── main/java/          
│   │       └── io/clementleetimfu/educationmanagementsystem/utils/
│   │           ├── bcrypt/     # BCrypt password hashing utilities
│   │           ├── jwt/        # JWT token utilities
│   │           └── thread/     # Thread utilities
│   └── pom.xml
├── ems-model/                  # Data models and POJOs
│   ├── src/
│   │   └── main/java/
│   │       └── io/clementleetimfu/educationmanagementsystem/pojo/
│   │           ├── dto/        # Data Transfer Objects
│   │           ├── entity/     # Entity classes representing DB tables
│   │           ├── PageResult.java  # Pagination result wrapper
│   │           └── Result.java      # Standard API response wrapper
│   └── pom.xml
├── ems-service/                # Main application with business logic
│   ├── src/
│   │   ├── main/java/
│   │   │   └── io/clementleetimfu/educationmanagementsystem/
│   │   │       ├── annotation/      # Custom annotations
│   │   │       ├── aop/             # Aspect-oriented programming components
│   │   │       ├── config/          # Configuration classes
│   │   │       ├── controller/      # REST controllers
│   │   │       ├── exception/       # Exception handling
│   │   │       ├── filter/          # Servlet filters
│   │   │       ├── mapper/          # MyBatis mappers
│   │   │       ├── service/         # Business logic services
│   │   │       └── EducationManagementSystemApplication.java
│   │   └── resources/
│   │       ├── mapper/              # MyBatis XML mapper files
│   │       ├── sql/                 # SQL scripts
│   │       ├── application.yml      # Application configuration
│   │       └── logback.xml          # Logging configuration
│   └── pom.xml
└── Dockerfile
```

## API Endpoints

### Authentication

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/auth/login` | POST | User login with username/password |
| `/auth/update-password` | POST | Update user password |

### Students

| Endpoint | Method | Description |
|----------|--------|-------------|
|`GET /students/search` | GET | Search students with pagination |
| `POST /students` | POST | Add a new student |
| `GET /students/{id}` | GET | Get student by ID |
| `PUT /students` | PUT | Update student information |
| `DELETE /students` | DELETE| Delete students by IDs |
| `GET /students/clazz/count` | GET | Get student count by class |
| `GET /students/edu-level/count` | GET | Get student count by education level |

### Employees

| Endpoint | Method | Description |
|----------|--------|-------------|
| `GET /emps/search` | GET | Search employees with pagination |
| `POST /emps` | POST | Add a new employee |
| `GET /emps/{id}` | GET | Get employee by ID |
| `PUT /emps` | PUT | Update employee information |
| `DELETE /emps` |DELETE | Delete employees by IDs |
| `GET /emps/jobTitle/count` | GET | Get employee count by job title |
| `GET /emps/gender/count` | GET | Get employee count by gender |
| `GET /emps/teachers` | GET | Get all teachers |

### Classes

| Endpoint | Method | Description |
|----------|--------|-------------|
| `GET /clazz/search` | GET | Search classes with pagination |
| `POST /clazz` | POST | Add a new class |
| `GET /clazz/{id}` | GET | Get class by ID |
| `PUT /clazz`| PUT | Update class information |
| `DELETE /clazz` | DELETE | Delete classes by IDs |
| `GET /clazz/student/count` | GET | Get student count by class |

### Departments

| Endpoint | Method | Description |
|----------|--------|-------------|
| `GET /depts` | GET |Get all departments |
| `POST /depts` | POST | Add a new department |
| `PUT /depts` | PUT | Update department information |
| `DELETE /depts` | DELETE | Delete departments by IDs |

### Subjects

| Endpoint | Method | Description |
|----------|--------|-------------|
| `GET /subjects` | GET | Get all subjects |
| `POST /subjects` | POST | Add a new subject |
| `PUT /subjects` | PUT | Update subject information |
| `DELETE /subjects` | DELETE | Delete subjects by IDs |

### Job Titles

| Endpoint | Method | Description |
|----------|--------|-------------|
| `GET /job-titles` | GET | Get all job titles |

### Education Levels

| Endpoint | Method | Description |
|----------|--------|-------------|
| `GET /edu-levels` | GET | Get all education levels |

### Activity Logs

| Endpoint | Method| Description |
|----------|--------|-------------|
| `GET /activity-logs/search` | GET | Search activity logs with pagination |

### Upload

| Endpoint | Method | Description |
|----------|--------|-------------|
| `POST /upload` | POST | Upload files to Cloudflare R2 |

## Database Schema
The system uses MySQL as the primary database with the following main entities:

- **Students**: Student personal and academic information
- **Employees**: Staff information
- **Classes**: Academic classes with assigned teachers and subjects
- **Departments**: Organizational departments
- **Subjects**: Academic subjects offered
- **Job Titles**: Employee positions
- **Education Levels**: Student education levels
- **Activity Logs**: Record of system operations
- **Work Experience**: Employee work history

## Authentication & Authorization

The system implements JWT-based authentication:

1. Users log in with username and password
2. Passwords are hashed using BCrypt with a pepper before saving to the database
3. Upon successful authentication, a JWT token is generated
4. Tokens expire after 1 hour
5. All protected endpoints require a valid JWT token in the Authorization header

Example request header:
```
Authorization: Bearer <token>
```

## Environment Variables

The following environment variables need to be configured:

```bash
# Database Configuration
MYSQL_HOST=
MYSQL_PORT=
MYSQL_DB=
MYSQL_USER=
MYSQL_PASSWORD=

# Cloudflare R2 Configuration
CLOUDFLARE_R2_BUCKET_NAME=
CLOUDFLARE_R2_ACCOUNT_ID=
CLOUDFLARE_R2_ACCESS_KEY=
CLOUDFLARE_R2_SECRET_KEY=
CLOUDFLARE_R2_PUBLIC_URL=

# Authentication
AUTH_JWT_SECRET_KEY=
AUTH_BCRYPT_PEPPER=
```

## Deployment

### Prerequisites

- Java 17
- MySQL 8.0+
- Maven 3.6+

### Steps

1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```

2. Configure environment variables

3. Build the project:
   ```bash
   ./mvnw clean package
   ```

4. Run the application:
   ```bash
   java -jar ems-service/target/education-management-system-0.0.1-SNAPSHOT.jar
   ```

## Getting Started

1. Set up MySQL database
2. Configure environment variables
3. Run the application using one of the deployment methods
4. Access the API at `http://localhost:8080`

## Docker Support

The project includes a Dockerfile for containerization:

```bash
# Build the Docker image
docker build -t education-management-system .

# Run the container
docker run -p 8080:8080 education-management-system
```

Configuration through environment variables is recommended when using Docker.

## License

This project is licensed under the MIT License - see the LICENSE file for details.