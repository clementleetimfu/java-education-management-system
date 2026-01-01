# Education Management System (Backend)

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
11. [Design Patterns & Best Practices](#design-patterns--best-practices)
12. [Error Code Reference](#error-code-reference)
13. [Deployment](#deployment)

---

## Project Overview

The Education Management System (EMS) is a comprehensive Spring Boot-based backend application designed for managing educational institution operations. The system provides robust functionality for student management, employee administration, class organization, departmental hierarchy, and audit logging with enterprise-grade security features.

### Key Characteristics

- **Multi-Module Maven Architecture**: Clean separation of concerns across three modules
- **Role-Based Access Control**: Two-tier permission system (Admin/Employee)
- **JWT Authentication**: Stateless authentication with Redis-based token blacklisting
- **AOP-Driven Cross-Cutting Concerns**: Custom annotations for permissions and activity logging
- **Cloudflare R2 Integration**: S3-compatible file storage for scalable asset management
- **Comprehensive Testing**: Unit test coverage with JUnit 5 and Mockito

### Frontend Integration

**Frontend Repository:** [Vue3 Frontend](https://github.com/clementleetimfu/vue-education-management-system)

![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.7-green)
![MySQL](https://img.shields.io/badge/MySQL-8.x-blue)
![MyBatis](https://img.shields.io/badge/MyBatis-3.0.5-red)
![Maven](https://img.shields.io/badge/Maven-3.x-orange)
![JWT](https://img.shields.io/badge/JWT-black)
![Docker](https://img.shields.io/badge/Docker-blue)
![License](https://img.shields.io/badge/license-MIT-blue)

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
ems-common
    ├── depends on: None
    └── provides: Utilities, Constants, Exceptions

ems-model
    ├── depends on: None
    └── provides: Entities, DTOs, VOs

ems-service
    ├── depends on: ems-model
    ├── depends on: ems-common
    └── provides: REST APIs
```

### Detailed Directory Structure

```
education-management-system/
├── ems-parent/                 # Parent Maven project
│   └── pom.xml                 # Parent POM with common configurations
├── ems-common/                 # Shared utilities
│   ├── src/main/java/io/clementleetimfu/educationmanagementsystem/
│   │   ├── constants/          # Error codes, Redis keys, Role enums
│   │   ├── exception/          # Business exception
│   │   └── utils/              # BCrypt, JWT, Redis, ThreadLocal utilities
│   └── src/test/java/          # Unit tests for constants and utilities
├── ems-model/                  # Data models and POJOs
│   ├── src/main/java/io/clementleetimfu/educationmanagementsystem/pojo/
│   │   ├── dto/                # Request DTOs (activityLog, auth, clazz, department, employee, student, workExperience)
│   │   ├── entity/             # Database entities (10 entities)
│   │   └── vo/                 # Response VOs (activityLog, auth, clazz, department, educationLevel, employee, jobTitle, student, subject, workExperience, result, student, subject, workExperience)
│   │       └── result/         # Standard API response wrappers (PageResult, Result)
│   └── pom.xml
├── ems-service/                # Main application with business logic
│   ├── src/main/java/io/clementleetimfu/educationmanagementsystem/
│   │   ├── annotation/         # @Permission, @AddActivityLog
│   │   ├── aop/                # PermissionAspect, ActivityLogAspect
│   │   ├── config/             # CloudflareR2, ModelMapper, Redis, S3 configs
│   │   ├── controller/         # 10 REST controllers
│   │   ├── exception/          # Global exception handler
│   │   ├── filter/             # JWT token filter
│   │   ├── mapper/             # 11 MyBatis mappers
│   │   ├── service/            # Service interfaces
│   │   ├── service/impl/       # Service implementations
│   │   └── EducationManagementSystemApplication.java
│   ├── src/main/resources/
│   │   ├── io/clementleetimfu/educationmanagementsystem/mapper/  # MyBatis XML mappers
│   │   ├── application.yml     # Application configuration
│   │   └── logback.xml         # Logging configuration
│   └── src/test/java/          # Integration tests
├── sql/                        # SQL scripts
│   └── education-management-system.sql
├── Dockerfile                  # Container configuration
└── README.md                   # Project overview
```

---

## Technology Stack

### Core Technologies

| Component | Technology | Version           |
|-----------|------------|-------------------|
| **Language** | Java | 17 (LTS)          |
| **Framework** | Spring Boot | 3.5.7             |
| **Build Tool** | Maven | 3.9.1              |
| **Database** | MySQL Connector/J | (via Spring Boot) |
| **ORM** | MyBatis Spring Boot Starter | 3.0.5             |
| **Pagination** | PageHelper Spring Boot Starter | 2.1.1             |
| **Caching** | Spring Data Redis | (via Spring Boot) |
| **Security** | Spring Security Crypto | 7.0.0             |
| **JWT** | JJWT (JSON Web Token) | 0.13.0            |
| **Object Mapping** | ModelMapper | 3.2.6             |
| **Cloud Storage** | AWS SDK for Java | 2.39.2            |
| **Boilerplate** | Lombok | 1.18.42           |
| **Testing** | JUnit 5 + Mockito | (via Spring Boot) |

### Maven Dependency Management

```xml
<!-- Parent POM Properties -->
<properties>
<maven.compiler.source>17</maven.compiler.source>
<maven.compiler.target>17</maven.compiler.target>
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
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
| `/emps/jobTitle/count` | GET | Employee count by job title | All Roles |
| `/emps/gender/count` | GET | Employee count by gender | All Roles |
| `/emps/teachers` | GET | Retrieve all class teachers | All Roles |

### 4. Class Management

**Location**: `ems-service/controller/ClazzController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/clazz/search` | GET | Search classes with filters | All Roles |
| `/clazz` | GET | Get all classes | All Roles |
| `/clazz/{id}` | GET | Get class by ID | All Roles |
| `/clazz` | POST | Create new class | Admin Only |
| `/clazz` | PUT | Update class | Admin Only |
| `/clazz/{id}` | DELETE | Delete class by ID | Admin Only |

### 5. Department Management

**Location**: `ems-service/controller/DepartmentController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/depts` | GET | Get all departments | All Roles |
| `/depts` | POST | Create new department | Admin Only |
| `/depts` | PUT | Update department | Admin Only |
| `/depts/{id}` | DELETE | Delete department by ID | Admin Only |

### 6. Activity Logging

**Location**: `ems-service/controller/ActivityLogController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/logs` | GET | Search activity logs with pagination | Admin Only |

### 7. File Upload

**Location**: `ems-service/controller/UploadController.java`

| Endpoint | Method | Description | Access Level |
|----------|--------|-------------|--------------|
| `/upload` | POST | Upload file to Cloudflare R2 | Admin Only   |

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
    if (rawPassword == null) {
        throw new BusinessException(ErrorCodeEnum.RAW_PASSWORD_IS_NULL);
    }
    return passwordEncoder.encode(rawPassword + pepper);
}

// Verification
public boolean verify(String rawPassword, String hashedPassword) {
    if (rawPassword == null || hashedPassword == null) {
        return false;
    }
    return passwordEncoder.matches(rawPassword + pepper, hashedPassword);
}
```

**Key Features**:
- BCrypt cost factor of 10 for optimal security/performance balance
- Pepper value injected from environment variable (`AUTH_BCRYPT_PEPPER`)
- Each hash operation generates a unique salt automatically

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
            .claims(claims)
            .signWith(key)
            .compact();
}

// Token Validation
public boolean validateToken(String token) {
    try {
        parseToken(token);
        return true;
    } catch (Exception e) {
        log.warn("Invalid token:{}", token, e);
        return false;
    }
}
```

**Token Payload Structure**:
```json
{
  "id": 1,
  "username": "john.doe",
  "roleName": "ROLE_ADMIN",
  "iat": 1234567890,
  "exp": 1234571490
}
```

### 3. Token Blacklisting (Redis)

**Location**: `ems-common/src/main/java/io/clementleetimfu/educationmanagementsystem/utils/redis/RedisUtil.java`

```java
// Blacklist Check
public Boolean isTokenBlacklisted(String token, Integer employeeId) {
    if (token == null || token.isBlank()) {
        return Boolean.FALSE;
    }

    String key = RedisEnum.BLACKLIST_TOKEN_PREFIX.getValue() + employeeId;

    if (redisTemplate.hasKey(key)) {
        return token.equals(redisTemplate.opsForValue().get(key));
    }

    return Boolean.FALSE;
}
```

**Implementation Details**:
- **Storage** (in `AuthServiceImpl.logout()`): Key = `blacklistToken:{employeeId}`, Value = `{token_value}`
- **Check** (in `TokenFilter`): Compares provided token with stored token value
- **TTL**: Token expiration time calculated from JWT claims

### 4. JWT Filter Chain

**Location**: `ems-service/src/main/java/io/clementleetimfu/educationmanagementsystem/filter/TokenFilter.java`

**Request Flow**:
1. Extract `Authorization` header
2. Validate Bearer token
3. Check Redis blacklist
4. Extract user claims (employeeId, roleName)
5. Store in Thread-Local context (`CurrentEmployee`, `CurrentRole`)
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

    MethodSignature signature = (MethodSignature) pjp.getSignature();
    Method method = signature.getMethod();

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

```java
@Around("@annotation(io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog)")
public Object addActivityLog(ProceedingJoinPoint pjp) throws Throwable {

    long duration = 0;
    Object result = null;
    try {
        long startTime = System.currentTimeMillis();

        result = pjp.proceed();

        long endTime = System.currentTimeMillis();
        duration = endTime - startTime;

    } finally {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setOperateEmpId(CurrentEmployee.get());
        activityLog.setOperateTime(LocalDateTime.now());
        activityLog.setClassName(pjp.getTarget().getClass().getName());
        activityLog.setMethodName(pjp.getSignature().getName());
        activityLog.setMethodParams(Arrays.toString(pjp.getArgs()));
        activityLog.setReturnValue(result != null ? result.toString() : "void");
        activityLog.setDuration(duration);
        activityLog.setCreateTime(LocalDateTime.now());
        activityLog.setUpdateTime(LocalDateTime.now());
        activityLog.setIsDeleted(Boolean.FALSE);
        activityLogService.addActivityLog(activityLog);
    }
    return result;
}
```

**Logged Information**:
- Employee ID (from Thread-Local `CurrentEmployee`)
- Timestamp
- Class name and method name
- Method parameters
- Return value
- Execution duration (milliseconds)

**Example Usage**:
```java
@Permission(role = RoleEnum.ROLE_ADMIN)
@AddActivityLog
@PostMapping
public Result<Boolean> addDepartment(@RequestBody DepartmentAddDTO dto) {
    return Result.success(departmentService.addDepartment(dto));
}
```

---

## Testing Strategy

### Test Framework

- **Unit Testing**: JUnit 5 (Jupiter)
- **Mocking**: Mockito 5.17.0
- **Static Mocking**: MockedStatic for Thread-Local utilities

### Test Organization

```
ems-service/src/test/java/io/clementleetimfu/educationmanagementsystem/
├── aop/                          # AOP Aspect tests
│   ├── PermissionAspectTest.java
│   └── ActivityLogAspectTest.java
├── config/                       # Configuration tests
│   ├── CloudflareR2ClientConfigTest.java
│   ├── ModelMapperConfigTest.java
│   ├── RedisConfigTest.java
│   └── S3ConfigTest.java
├── exception/                    # Exception handling tests
│   └── GlobalExceptionHandlerTest.java
├── filter/                       # Filter tests
│   └── TokenFilterTest.java
└── service/impl/                 # Service implementation tests
    ├── ActivityLogServiceImplTest.java
    ├── AuthServiceImplTest.java
    ├── ClazzServiceImplTest.java        
    ├── DepartmentServiceImplTest.java
    ├── EducationLevelServiceImplTest.java
    ├── EmployeeServiceImplTest.java    
    ├── JobTitleServiceImplTest.java
    ├── StudentNumberSequenceServiceImplTest.java
    ├── StudentServiceImplTest.java
    ├── SubjectServiceImplTest.java
    └── UploadServiceImplTest.java

ems-common/src/test/java/io/clementleetimfu/educationmanagementsystem/
├── constants/                    # Enum tests
│   ├── ErrorCodeEnumTest.java
│   ├── RedisEnumTest.java
│   └── RoleEnumTest.java
└── utils/                        # Utility tests
    ├── bcrypt/BcryptUtilTest.java
    ├── jwt/JwtUtilTest.java
    ├── redis/RedisUtilTest.java
    └── threadLocal/
        ├── CurrentEmployeeTest.java
        └── CurrentRoleTest.java
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

    private static final String DUMMY_METHOD_NAME = "dummyMethod";

    @BeforeEach
    void setUp() {
        mockedCurrentRole = mockStatic(CurrentRole.class);
    }

    @AfterEach
    void tearDown() {
        if (mockedCurrentRole != null) {
            mockedCurrentRole.close();
        }
    }

    @Test
    @DisplayName("Test Check Permission Admin Role Authorized")
    void testCheckPermissionAdminRoleAuthorized() throws Throwable {
        String currentRole = "ROLE_ADMIN";
        String returnType = "Object";

        when(pjp.getSignature()).thenReturn(methodSignature);

        Method dummyMethod = this.getClass().getDeclaredMethod(DUMMY_METHOD_NAME);
        when(methodSignature.getMethod()).thenReturn(dummyMethod);

        mockedCurrentRole.when(CurrentRole::get).thenReturn(currentRole);

        when(pjp.proceed()).thenReturn(returnType);

        Object result = permissionAspect.checkPermission(pjp);

        assertEquals(returnType, result);
        verify(pjp, times(1)).proceed();
    }

    @Test
    @DisplayName("Test Check Permission Employee Role Denied")
    void testCheckPermissionEmployeeRoleDenied() throws Throwable {
        String currentRole = "ROLE_EMPLOYEE";

        when(pjp.getSignature()).thenReturn(methodSignature);

        Method dummyMethod = this.getClass().getDeclaredMethod(DUMMY_METHOD_NAME);
        when(methodSignature.getMethod()).thenReturn(dummyMethod);

        mockedCurrentRole.when(CurrentRole::get).thenReturn(currentRole);

        BusinessException exception = assertThrows(BusinessException.class, () -> permissionAspect.checkPermission(pjp));
        assertEquals(ErrorCodeEnum.PERMISSION_DENIED.getCode(), exception.getCode());

        verify(pjp, never()).proceed();
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @SuppressWarnings("unused")
    private void dummyMethod() {
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

    assertNotEquals(hash1, hash2, "BCrypt should generate different hashes for the same password");
    assertTrue(hash1.startsWith("$2a$10$"), "Hash should start with BCrypt identifier");
    assertTrue(hash2.startsWith("$2a$10$"), "Hash should start with BCrypt identifier");
}
```
---

## Installation & Setup

### Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.9.11
- **MySQL**: 8
- **Redis**: 8.4
- **Cloudflare R2**: Account with R2 bucket (for file uploads)

### Environment Variables

Create a `.env` file or set the following environment variables:

```bash
# Database Configuration
MYSQL_HOST=your_mysql_host
MYSQL_PORT=your_mysql_port
MYSQL_DB=your_mysql_database
MYSQL_USER=your_mysql_user
MYSQL_PASSWORD=your_password

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=your_redis_port
REDIS_DATABASE=your_redis_database
REDIS_PASSWORD=your_redis_password

# Cloudflare R2 Configuration
CLOUDFLARE_R2_BUCKET_NAME=your_bucket_name
CLOUDFLARE_R2_ACCOUNT_ID=your_account_id
CLOUDFLARE_R2_ACCESS_KEY=your_access_key
CLOUDFLARE_R2_SECRET_KEY=your_secret_key
CLOUDFLARE_R2_PUBLIC_URL=your_bucket_url

# Security Configuration
AUTH_JWT_SECRET_KEY=your_jwt_secret_key
AUTH_BCRYPT_PEPPER=your_pepper_value
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

# Run tests for specific module
cd ems-common
mvn test

cd ../ems-service
mvn test

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
- Password: `admin123`

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

#### Update Password
```http
POST /auth/update-password
Content-Type: application/json

{
  "id": "1",
  "password": "abc1234567",
}

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

| Parameter | Type | Default |
|-----------|------|---------|
| page | Integer | 1 |
| pageSize | Integer | 10 |

---

## Database Schema

### Common Patterns

1. **Soft Delete**: All tables use `is_deleted` flag instead of physical deletion
2. **Audit Timestamps**: `create_time` and `update_time` on all tables
3. **Generated Columns**: Active columns for filtering unique constraints (e.g., `active_username`, `active_name`)
4. **Unsigned Types**: All ID and foreign key columns use `INT UNSIGNED` or `TINYINT UNSIGNED`

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

**Benefits**:
- Decouples API layer from database schema
- Enables selective field exposure
- Facilitates validation at API boundaries

### 2. AOP Cross-Cutting Concerns

- `@Permission`: Declarative role-based access control
- `@AddActivityLog`: Automatic audit logging

**Benefits**:
- Eliminates boilerplate code in controllers
- Centralized security and logging logic
- Easy to maintain and extend

### 3. Thread-Local Context

User context stored per-request:
- `CurrentEmployee`: Employee ID
- `CurrentRole`: Role name

**Benefits**:
- No need to pass user context through method parameters
- Automatic cleanup via TokenFilter's finally block
- Thread-safe in containerized environments

### 4. Global Exception Handling

Centralized error handling via `@RestControllerAdvice`:
- `BusinessException`: Mapped to specific error codes
- `DuplicateKeyException`: Formatted constraint violation messages
- Generic `Exception`: Fallback error handling

**Benefits**:
- Consistent error response format
- Centralized error message management
- Easy to add custom error handling

### 5. Transaction Management

Service layer methods use `@Transactional` with:
- Rollback for specific exceptions
- Read-only optimization for query methods

### 6. Soft Delete Pattern

All entities use logical deletion:
- `is_deleted` flag instead of physical deletion
- Preserves data integrity and audit trail
- Enables data recovery

---

## Error Code Reference

| Code  | Message | Category |
|-------|---------|----------|
| 1001  | Invalid username or password | Authentication |
| 2001  | Department not found | Department |
| 2002  | Department delete failed | Department |
| 2003  | Department add failed | Department |
| 2004  | Department update failed | Department |
| 2005  | Department still has employees | Department |
| 3001  | Employee not found | Employee |
| 3002  | Employee add failed | Employee |
| 3003  | Employee delete failed | Employee |
| 3004  | Employee update failed | Employee |
| 4001  | Work experience add failed | Work Experience |
| 4002  | Work experience delete failed | Work Experience |
| 5001  | Activity log not found | Activity Log |
| 6001  | Class not found | Class |
| 6002  | Class add failed | Class |
| 6003  | Class update failed | Class |
| 6004  | Class delete failed | Class |
| 6005  | Class still has students | Class |
| 7001  | Job title not found | Job Title |
| 8001  | Subject not found | Subject |
| 9001  | Student not found | Student |
| 9002  | Student add failed | Student |
| 9003  | Student delete failed | Student |
| 9004  | Student update failed | Student |
| 10001 | Student number sequence add failed | Student Number |
| 10002 | Student number sequence update failed | Student Number |
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
# Build the JAR first
cd ems-parent
mvn clean package

# Build Docker image
cd ..
docker build -t education-management-system:latest .
```

### Run Container

```bash
docker run -d -p 8080:8080 --name ems-app education-management-system:latest
```
---

## Document Version

- **Version**: 2.0
- **Last Updated**: 2026-01-01