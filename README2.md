# Technical Documentation: Education Management System

## Table of Contents

1. [Project Overview](#project-overview)
2. [System Architecture](#system-architecture)
3. [Technology Stack](#technology-stack)
4. [Core Features](#core-features)
5. [Security Implementation](#security-implementation)
6. [Testing Strategy](#testing-strategy)
7. [Installation & Setup](#installation--setup)
8. [API Reference](#api-reference)
9. [Database Schema](#database-schema)
10. [Configuration Management](#configuration-management)

---

## Project Overview

The Education Management System (EMS) is a comprehensive Spring Boot-based backend application designed for managing educational institution operations. The system provides robust functionality for student management, employee administration, class organization, departmental hierarchy, and audit logging with enterprise-grade security features.

### Key Characteristics

- **Multi-Module Maven Architecture**: Clean separation of concerns across three modules
- **Role-Based Access Control**: Two-tier permission system (Admin/Employee)
- **JWT Authentication**: Stateless authentication with Redis-based token blacklisting
- **AOP-Driven Cross-Cutting Concerns**: Custom annotations for permissions and activity logging
- **Cloudflare R2 Integration**: S3-compatible file storage for scalable asset management
- **Comprehensive Testing**: Full unit test coverage with JUnit 5 and Mockito

---

## System Architecture

### Module Structure (Maven)

```
ems-parent/
├── ems-model/          # Data models, DTOs, VOs, and response wrappers
├── ems-common/         # Shared utilities (security, Redis, thread-local storage)
└── ems-service/        # Main application (controllers, services, mappers, aspects)
```

### Layered Architecture

```
┌─────────────────────────────────────────────────────┐
│                  Presentation Layer                  │
│              (Controllers + Filters)                 │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│                  Cross-Cutting Layer                 │
│         (AOP Aspects: Permission, Activity Log)      │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│                   Business Layer                     │
│              (Services + DTOs)                       │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│                Data Access Layer                     │
│         (MyBatis Mappers + XML Mappers)              │
└────────────────────┬────────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────────┐
│              Database Layer (MySQL 8.x)              │
└─────────────────────────────────────────────────────┘
```

### Dependency Graph

```
ems-service
    ├── depends on: ems-model
    ├── depends on: ems-common
    └── provides: REST APIs

ems-model
    ├── depends on: ems-common
    └── provides: Entities, DTOs, VOs

ems-common
    ├── depends on: None (foundational module)
    └── provides: Utilities, Constants, Exceptions
```

---

## Technology Stack

### Core Technologies

| Component | Technology | Version |
|-----------|------------|---------|
| **Language** | Java | 17 (LTS) |
| **Framework** | Spring Boot | 3.5.7 |
| **Build Tool** | Maven | 3.x |
| **Database** | MySQL Connector/J | (via Spring Boot) |
| **ORM** | MyBatis Spring Boot Starter | 3.0.5 |
| **Pagination** | PageHelper Spring Boot Starter | 2.1.1 |
| **Caching** | Spring Data Redis | (via Spring Boot) |
| **Security** | Spring Security Crypto | 7.0.0 |
| **JWT** | JJWT (JSON Web Token) | 0.13.0 |
| **Object Mapping** | ModelMapper | 3.2.6 |
| **Cloud Storage** | AWS SDK for Java | 2.39.2 |
| **Boilerplate** | Lombok | 1.18.42 |
| **Testing** | JUnit 5 + Mockito | (via Spring Boot) |

### Maven Dependency Management

```xml
<!-- Parent POM Properties -->
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
    <jjwt.version>0.13.0</jjwt.version>
    <lombok.version>1.18.42</lombok.version>
    <spring.security.crypto.version>7.0.0</spring.security.crypto.version>
    <mybatis.version>3.0.5</mybatis.version>
    <modelmapper.version>3.2.6</modelmapper.version>
    <pagehelper.version>2.1.1</pagehelper.version>
    <aws.sdk.version>2.39.2</aws.sdk.version>
</properties>
```

---

## Core Features

### 1. Authentication & Authorization

**Location**: `ems-service/controller/AuthController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/auth/login` | POST | User authentication with JWT token generation | Public |
| `/auth/update-password` | POST | Password update for authenticated users | Public |
| `/auth/logout` | POST | Logout with token blacklisting | Authenticated |

### 2. Student Management

**Location**: `ems-service/controller/StudentController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/students/search` | GET | Search students with pagination and filters | All Roles |
| `/students/{id}` | GET | Retrieve student details by ID | All Roles |
| `/students` | POST | Create new student record | Admin Only |
| `/students` | PUT | Update existing student | Admin Only |
| `/students` | DELETE | Soft delete students by IDs | Admin Only |
| `/students/clazz/count` | GET | Get student count by class | All Roles |
| `/students/edu-level/count` | GET | Get student count by education level | All Roles |

### 3. Employee Management

**Location**: `ems-service/controller/EmployeeController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/emps/search` | GET | Search employees with pagination | All Roles |
| `/emps/{id}` | GET | Retrieve employee details | All Roles |
| `/emps` | POST | Create new employee | Admin Only |
| `/emps` | PUT | Update employee | Admin Only |
| `/emps` | DELETE | Delete employees | Admin Only |
| `/emps/job-title/count` | GET | Employee count by job title | All Roles |
| `/emps/class-teachers` | GET | Retrieve all class teachers | All Roles |

### 4. Class Management

**Location**: `ems-service/controller/ClazzController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/clazz/search` | GET | Search classes with filters | All Roles |
| `/clazz` | GET | Get all classes | All Roles |
| `/clazz/{id}` | GET | Get class by ID | All Roles |
| `/clazz` | POST | Create new class | Admin Only |
| `/clazz` | PUT | Update class | Admin Only |
| `/clazz` | DELETE | Delete class | Admin Only |

### 5. Department Management

**Location**: `ems-service/controller/DepartmentController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/depts` | GET | Get all departments | All Roles |
| `/depts/{id}` | GET | Get department by ID | All Roles |
| `/depts` | POST | Create new department | Admin Only |
| `/depts` | PUT | Update department | Admin Only |
| `/depts` | DELETE | Delete department | Admin Only |

### 6. Activity Logging

**Location**: `ems-service/controller/ActivityLogController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/logs/search` | GET | Search activity logs with pagination | Admin Only |

### 7. File Upload

**Location**: `ems-service/controller/UploadController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/upload` | POST | Upload file to Cloudflare R2 | Authenticated |

---

## Security Implementation

### Authentication Flow

```
┌─────────────┐     ┌─────────────┐     ┌─────────────┐
│    Client   │────▶│ TokenFilter │────▶│ Controller  │
└─────────────┘     └─────────────┘     └─────────────┘
                          │
                          ├─── Validate JWT
                          ├─── Check Redis Blacklist
                          ├─── Extract Claims
                          └─── Set Thread-Local Context
```

### 1. Password Hashing (BCrypt with Pepper)

**Location**: `ems-common/src/main/java/io/clementleetimfu/educationmanagementsystem/utils/bcrypt/BcryptUtil.java`

```java
// Configuration
private static final int STRENGTH = 10;  // BCrypt cost factor
private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(STRENGTH);

// Hashing with Pepper Enhancement
public String hash(String rawPassword) {
    return passwordEncoder.encode(rawPassword + pepper);
}

// Verification
public boolean verify(String rawPassword, String hashedPassword) {
    return passwordEncoder.matches(rawPassword + pepper, hashedPassword);
}
```

**Key Features**:
- BCrypt cost factor of 10 for optimal security/performance balance
- Pepper value injected from environment variable (`AUTH_BCRYPT_PEPPER`)
- Each hash operation generates a unique salt automatically
- 60-character fixed-length hash output

### 2. JWT Token Management

**Location**: `ems-common/src/main/java/io/clementleetimfu/educationmanagementsystem/utils/jwt/JwtUtil.java`

```java
// Token Configuration
private static final long EXPIRATION_MS = 60 * 60 * 1000;  // 1 hour

// Token Generation
public String generateToken(Map<String, Object> claims) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + EXPIRATION_MS);
    return Jwts.builder()
            .issuedAt(now)
            .expiration(expiryDate)
            .claims(claims)  // Contains: id, roleName
            .signWith(key)   // HMAC-SHA signed
            .compact();
}

// Token Validation
public boolean validateToken(String token) {
    try {
        parseToken(token);
        return true;
    } catch (Exception e) {
        return false;
    }
}
```

**Token Payload Structure**:
```json
{
  "id": 1,
  "roleName": "ROLE_ADMIN",
  "iat": 1234567890,
  "exp": 1234571490
}
```

### 3. Token Blacklisting (Redis)

**Location**: `ems-common/src/main/java/io/clementleetimfu/educationmanagementsystem/utils/redis/RedisUtil.java`

```java
// Blacklist Check
public Boolean isTokenBlacklisted(String token) {
    String key = RedisEnum.BLACKLIST_TOKEN_PREFIX.getValue() + token;
    return redisTemplate.hasKey(key);
}
```

**Blacklist Key Pattern**: `BLACKLIST_TOKEN:{token_value}`

### 4. JWT Filter Chain

**Location**: `ems-service/src/main/java/io/clementleetimfu/educationmanagementsystem/filter/TokenFilter.java`

**Request Flow**:
1. Extract `Authorization` header
2. Validate Bearer token format
3. Check Redis blacklist
4. Validate JWT signature and expiration
5. Extract user claims (id, roleName)
6. Store in Thread-Local context
7. Proceed to controller
8. Cleanup Thread-Local in finally block

**Excluded Endpoints**:
- `/auth/login`
- `/auth/update-password`

### 5. Role-Based Access Control (AOP)

**Annotation**: `@Permission(role = RoleEnum.ROLE_ADMIN)`

**Location**: `ems-service/src/main/java/io/clementleetimfu/educationmanagementsystem/aop/PermissionAspect.java`

```java
@Around("@annotation(io.clementleetimfu.educationmanagementsystem.annotation.Permission)")
public Object checkPermission(ProceedingJoinPoint pjp) throws Throwable {
    Permission annotation = method.getAnnotation(Permission.class);
    RoleEnum requiredRole = annotation.role();
    RoleEnum currentRole = RoleEnum.valueOf(CurrentRole.get());

    if (!requiredRole.equals(currentRole)) {
        throw new BusinessException(ErrorCodeEnum.PERMISSION_DENIED);
    }
    return pjp.proceed();
}
```

**Role Hierarchy**:
- `ROLE_ADMIN`: Full access to all operations
- `ROLE_EMPLOYEE`: Read-only access to most resources

### 6. Activity Logging (AOP)

**Annotation**: `@AddActivityLog`

**Location**: `ems-service/src/main/java/io/clementleetimfu/educationmanagementsystem/aop/ActivityLogAspect.java`

**Logged Information**:
- Employee ID (from Thread-Local)
- Timestamp
- Class name and method name
- Method parameters
- Return value
- Execution duration (ms)

---

## Testing Strategy

### Test Framework

- **Unit Testing**: JUnit 5 (Jupiter)
- **Mocking**: Mockito 5.x
- **Spring Integration**: Spring Boot Test

### Test Organization

```
ems-service/src/test/java/io/clementleetimfu/educationmanagementsystem/
├── aop/                          # AOP Aspect tests
│   ├── PermissionAspectTest.java
│   └── ActivityLogAspectTest.java
├── config/                       # Configuration tests
│   ├── ModelMapperConfigTest.java
│   ├── RedisConfigTest.java
│   ├── S3ConfigTest.java
│   └── CloudflareR2ClientConfigTest.java
├── controller/                   # Controller tests
├── exception/                    # Exception handling tests
│   └── GlobalExceptionHandlerTest.java
├── filter/                       # Filter tests
│   └── TokenFilterTest.java
├── mapper/                       # MyBatis mapper tests
│   ├── ActivityLogMapperTest.java
│   ├── ClazzMapperTest.java
│   ├── DepartmentMapperTest.java
│   ├── EducationLevelMapperTest.java
│   ├── EmployeeMapperTest.java
│   ├── JobTitleMapperTest.java
│   ├── RoleMapperTest.java
│   ├── StudentMapperTest.java
│   ├── StudentNumberSequenceMapperTest.java
│   ├── SubjectMapperTest.java
│   └── WorkExperienceMapperTest.java
└── service/impl/                 # Service implementation tests
    ├── ActivityLogServiceImplTest.java
    ├── AuthServiceImplTest.java
    ├── ClazzServiceImplTest.java
    ├── DepartmentServiceImplTest.java
    ├── EducationLevelServiceImplTest.java
    ├── EmployeeServiceImplTest.java
    ├── JobTitleServiceImplTest.java
    ├── StudentServiceImplTest.java
    ├── StudentNumberSequenceServiceImplTest.java
    ├── SubjectServiceImplTest.java
    └── UploadServiceImplTest.java
```

### Test Patterns

#### 1. AOP Testing (Mockito with MockedStatic)

**Example**: `PermissionAspectTest.java`

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("Permission Aspect Tests")
class PermissionAspectTest {

    @Mock
    private ProceedingJoinPoint pjp;

    @Mock
    private MethodSignature methodSignature;

    @InjectMocks
    private PermissionAspect permissionAspect;

    private MockedStatic<CurrentRole> mockedCurrentRole;

    @Test
    @DisplayName("Test Check Permission Admin Role Authorized")
    void testCheckPermissionAdminRoleAuthorized() throws Throwable {
        // Arrange
        mockedCurrentRole.when(CurrentRole::get).thenReturn("ROLE_ADMIN");

        // Act
        Object result = permissionAspect.checkPermission(pjp);

        // Assert
        assertEquals(expectedResult, result);
        verify(pjp, times(1)).proceed();
    }
}
```

#### 2. Utility Testing (BcryptUtil)

**Example**: `BcryptUtilTest.java`

```java
@Test
@DisplayName("Test hash generates different values for same password")
void testHashGeneratesDifferentValues() {
    String password = "password123";
    String hash1 = bcryptUtil.hash(password);
    String hash2 = bcryptUtil.hash(password);

    assertNotEquals(hash1, hash2);
    assertTrue(hash1.startsWith("$2a$10$"));
}
```

### Test Coverage Areas

| Component | Test Type | Coverage Focus |
|-----------|-----------|----------------|
| Security Utils | Unit | BCrypt hashing, JWT generation/validation, Redis operations |
| AOP Aspects | Unit | Permission enforcement, activity logging |
| Mappers | Integration | CRUD operations, soft delete, pagination |
| Services | Integration | Business logic, transaction management |
| Controllers | Integration | Endpoint behavior, response formatting |
| Exception Handler | Unit | Error code mapping, response structure |

---

## Installation & Setup

### Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.6+ or use Maven wrapper
- **MySQL**: 8.x
- **Redis**: 6.x+
- **Cloudflare R2**: Account with R2 bucket (for file uploads)

### Environment Variables

Create a `.env` file or set the following environment variables:

```bash
# Database Configuration
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_DB=education_management_system
MYSQL_USER=root
MYSQL_PASSWORD=your_password

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379
REDIS_DATABASE=0
REDIS_PASSWORD=your_redis_password

# Cloudflare R2 Configuration
CLOUDFLARE_R2_BUCKET_NAME=your-bucket-name
CLOUDFLARE_R2_ACCOUNT_ID=your-account-id
CLOUDFLARE_R2_ACCESS_KEY=your-access-key
CLOUDFLARE_R2_SECRET_KEY=your-secret-key
CLOUDFLARE_R2_PUBLIC_URL=https://your-bucket.r2.dev

# Security Configuration
AUTH_JWT_SECRET_KEY=your-base64-encoded-secret-key
AUTH_BCRYPT_PEPPER=your-pepper-value
```

### Build Commands

```bash
# Navigate to parent directory
cd ems-parent

# Clean and build entire project
mvn clean package

# Run tests
mvn test

# Run specific test class
mvn test -Dtest=PermissionAspectTest

# Skip tests during build
mvn clean package -DskipTests
```

### Run Application

```bash
# After successful build
cd ems-service/target
java -jar ems-service-0.0.1-SNAPSHOT.jar

# Or use Spring Boot Maven plugin
cd ems-service
mvn spring-boot:run
```

### Default Access

**URL**: `http://localhost:8080`

**Default Admin Credentials**:
- Username: `admin`
- Password: `abc123`

---

## API Reference

### Standard Response Format

**Success Response**:
```json
{
  "code": 0,
  "message": "success",
  "data": { ... }
}
```

**Error Response**:
```json
{
  "code": 1001,
  "message": "Invalid username or password",
  "data": null
}
```

### Authentication Endpoints

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "abc123"
}

Response:
{
  "code": 0,
  "message": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "id": 1,
    "name": "Administrator",
    "roleName": "ROLE_ADMIN",
    "isFirstLogged": false
  }
}
```

#### Logout
```http
POST /auth/logout
Authorization: Bearer {token}

Response:
{
  "code": 0,
  "message": "success",
  "data": true
}
```

### Authenticated Request Pattern

```http
GET /students/search?page=1&pageSize=10
Authorization: Bearer {jwt_token}
```

### Pagination Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| page | Integer | 1 | Page number (1-based) |
| pageSize | Integer | 10 | Items per page |

---

## Database Schema

### Core Tables

#### employee
```sql
CREATE TABLE employee (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender INT COMMENT '1: Male, 2: Female',
    phone VARCHAR(20),
    job_title INT COMMENT '1: Class Teacher, 2: Lecturer, 3: Student Affairs Manager, 4: Academic Research Manager, 5: Advisor',
    salary BIGINT,
    image VARCHAR(255),
    hire_date DATE,
    dept_id INT,
    is_first_logged BOOLEAN DEFAULT TRUE,
    role_id INT,
    is_deleted BOOLEAN DEFAULT FALSE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### student
```sql
CREATE TABLE student (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    no VARCHAR(50) UNIQUE NOT NULL COMMENT 'Student number',
    gender INT,
    birthdate DATE,
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255),
    education_level INT COMMENT 'Education level code',
    graduation_date DATE,
    clazz_id INT COMMENT 'Foreign key to clazz',
    intake_date DATE,
    is_deleted BOOLEAN DEFAULT FALSE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### clazz
```sql
CREATE TABLE clazz (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    room VARCHAR(50),
    major VARCHAR(100),
    supervisor_id INT COMMENT 'Class teacher employee ID',
    is_deleted BOOLEAN DEFAULT FALSE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### department
```sql
CREATE TABLE department (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    is_deleted BOOLEAN DEFAULT FALSE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### activity_log
```sql
CREATE TABLE activity_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    operate_emp_id INT NOT NULL,
    operate_time DATETIME NOT NULL,
    class_name VARCHAR(255),
    method_name VARCHAR(100),
    method_params TEXT,
    return_value TEXT,
    duration BIGINT COMMENT 'Execution time in milliseconds',
    is_deleted BOOLEAN DEFAULT FALSE,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

### Common Patterns

1. **Soft Delete**: All tables use `is_deleted` flag instead of physical deletion
2. **Audit Timestamps**: `create_time` and `update_time` on all tables
3. **Generated Columns**: Active name/phone columns for filtering (e.g., `active_name`)

---

## Configuration Management

### application.yml Structure

```yaml
spring:
  application:
    name: education-management-system

  datasource:
    url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/${MYSQL_DB}
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: ${MYSQL_USER}
    password: ${MYSQL_PASSWORD}

  data:
    redis:
      host: ${REDIS_HOST}
      port: ${REDIS_PORT}
      database: ${REDIS_DATABASE}
      password: ${REDIS_PASSWORD}

mybatis:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

cloudflare:
  r2:
    bucketName: ${CLOUDFLARE_R2_BUCKET_NAME}
    accountId: ${CLOUDFLARE_R2_ACCOUNT_ID}
    accessKey: ${CLOUDFLARE_R2_ACCESS_KEY}
    secretKey: ${CLOUDFLARE_R2_SECRET_KEY}
    publicUrl: ${CLOUDFLARE_R2_PUBLIC_URL}

logging:
  level:
    org.springframework.jdbc.support.JdbcTransactionManager: debug

auth:
  jwt:
    secretKey: ${AUTH_JWT_SECRET_KEY}
  bcrypt:
    pepper: ${AUTH_BCRYPT_PEPPER}
```

### Key Configuration Classes

| Class | Purpose |
|-------|---------|
| `ModelMapperConfig` | ModelMapper bean configuration |
| `RedisConfig` | RedisTemplate serialization setup |
| `S3Config` | AWS S3 client for Cloudflare R2 |
| `CloudflareR2ClientConfig` | R2-specific client configuration |

---

## Design Patterns & Best Practices

### 1. DTO Pattern

Separation of internal entities and API contracts:
- **DTO** (Data Transfer Object): Request/input data
- **VO** (Value Object): Response/output data
- **Entity**: Database representation

### 2. AOP Cross-Cutting Concerns

- `@Permission`: Declarative role-based access control
- `@AddActivityLog`: Automatic audit logging

### 3. Thread-Local Context

User context stored per-request:
- `CurrentEmployee`: Employee ID
- `CurrentRole`: Role name

### 4. Global Exception Handling

Centralized error handling via `@RestControllerAdvice`:
- `BusinessException`: Mapped to specific error codes
- `DuplicateKeyException`: Formatted constraint violation messages
- Generic `Exception`: Fallback error handling

### 5. Transaction Management

Service layer methods use `@Transactional` with:
- Rollback for specific exceptions
- Read-only optimization for query methods

---

## Error Code Reference

| Code | Message | Category |
|------|---------|----------|
| 1001 | Invalid username or password | Authentication |
| 2001-2004 | Department operations | Department |
| 3001-3002 | Employee operations | Employee |
| 4002 | Work experience operations | Work Experience |
| 5001 | Activity log not found | Activity Log |
| 6001-6005 | Class operations | Class |
| 7001 | Job title not found | Job Title |
| 8001 | Subject not found | Subject |
| 9001-9004 | Student operations | Student |
| 10001-10002 | Student number sequence | Student Number |
| 11001 | Education level not found | Education Level |
| 12001 | Permission denied | Authorization |
| 13001 | Raw password is null | Validation |

---

## Deployment

### Docker Configuration

The application includes Docker support with:

- **Base Image**: CentOS 7
- **Java Version**: JDK 17.0.12
- **Exposed Port**: 8080
- **Environment Variables**: All configuration via Docker environment variables

### Build Docker Image

```bash
docker build -t education-management-system:latest .
```

### Run Container

```bash
docker run -d \
  -p 8080:8080 \
  -e MYSQL_HOST=your_mysql_host \
  -e MYSQL_PORT=3306 \
  -e MYSQL_DB=education_management_system \
  -e MYSQL_USER=root \
  -e MYSQL_PASSWORD=your_password \
  -e REDIS_HOST=your_redis_host \
  -e REDIS_PORT=6379 \
  -e REDIS_DATABASE=0 \
  -e REDIS_PASSWORD=your_redis_password \
  education-management-system:latest
```

---

## License

This project is proprietary software. All rights reserved.

---

## Document Version

- **Version**: 1.0
- **Last Updated**: 2025-12-28