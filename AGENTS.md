# AGENTS.md - Agentic Coding Guidelines

This document provides guidelines for agents working on the Education Management System (EMS) codebase.

## Project Overview

- **Framework**: Spring Boot 3.5.7 with Java 17
- **Build Tool**: Maven 3.9+
- **Architecture**: Multi-module Maven project (ems-parent, ems-model, ems-common, ems-service)
- **Database**: MySQL 8.x with MyBatis 3.0.5

## Module Structure

```
ems-parent/           # Parent POM with dependency management
├── ems-model/        # Entities, DTOs, VOs, Response wrappers
├── ems-common/       # Utilities, Constants, Exceptions, Security
└── ems-service/      # Controllers, Services, Mappers, AOP, Config
```

## Build Commands

### Full Build
```bash
cd ems-parent
mvn clean package
```

### Run Tests
```bash
mvn test
```

### Run Single Test Class
```bash
mvn test -Dtest=PermissionAspectTest
```

### Run Single Test Method
```bash
mvn test -Dtest=PermissionAspectTest#testCheckPermissionAdminRoleAuthorized
```

### Run Tests for Specific Module
```bash
cd ems-common && mvn test
cd ems-service && mvn test
```

### Skip Tests
```bash
mvn clean package -DskipTests
```

### Run Application
```bash
cd ems-service
mvn spring-boot:run
```

## Code Style Guidelines

### General Principles
- Use Java 17 features (records, switch expressions, text blocks where appropriate)
- Follow SOLID principles
- Use Lombok to reduce boilerplate (@Data, @Builder, @Slf4j, etc.)
- All timestamps use `LocalDateTime`
- All entities use soft delete (`is_deleted` boolean)

### Naming Conventions

| Element | Convention | Example |
|---------|------------|---------|
| Classes | PascalCase | `StudentController`, `AuthServiceImpl` |
| Methods | camelCase | `findStudentById`, `addDepartment` |
| Variables | camelCase | `studentService`, `employeeId` |
| Constants | UPPER_SNAKE_CASE | `SUCCESS_CODE`, `BLACKLIST_TOKEN_PREFIX` |
| Packages | lowercase | `io.clementleetimfu.educationmanagementsystem` |
| Database Tables | snake_case | `t_student`, `t_employee` |

### Package Structure

```
io.clementleetimfu.educationmanagementsystem/
├── annotation/       # Custom annotations (@Permission, @AddActivityLog)
├── aop/             # Aspect classes (PermissionAspect, ActivityLogAspect)
├── config/          # Configuration classes
├── controller/      # REST controllers
├── exception/       # Exception handling
├── filter/          # Request filters (JWT)
├── mapper/          # MyBatis mappers
├── service/         # Service interfaces
│   └── impl/        # Service implementations
```

### Model Package Structure (ems-model)

```
pojo/
├── dto/             # Request DTOs (Add, Update, Search)
├── entity/          # Database entities
└── vo/              # Response VOs
    └── result/      # Result wrappers (Result, PageResult)
```

### Import Organization

Order imports as follows:
1. Java/Javax imports
2. Spring framework imports
3. Third-party libraries (Lombok, MyBatis, etc.)
4. Internal project imports

```java
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.clementleetimfu.educationmanagementsystem.annotation.Permission;
import io.clementleetimfu.educationmanagementsystem.constants.RoleEnum;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
```

### DTO/VO Design

- **DTOs**: Request objects with validation annotations
- **VOs**: Response objects exposing only necessary fields
- **Entities**: Database representation with all fields
- Use `PageResult<T>` for paginated responses
- Use `Result<T>` wrapper for all API responses

### Controller Guidelines

```java
@RestController
@RequestMapping("/resource-name")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/search")
    public Result<PageResult<ResourceVO>> search(...) { ... }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @PostMapping
    public Result<Boolean> addResource(...) { ... }
}
```

- Always use `@RestController`
- Use standard REST endpoints (GET/POST/PUT/DELETE)
- Apply `@Permission` annotation for admin-only endpoints
- Apply `@AddActivityLog` for audit logging on mutations
- Return `Result<T>` for all responses

### Service Guidelines

- Use interfaces (`ResourceService`) with implementations (`ResourceServiceImpl`)
- Mark read-only methods with `@Transactional(readOnly = true)`
- Mark mutation methods with `@Transactional`
- Throw `BusinessException` for business logic errors

### Exception Handling

- Use `BusinessException(ErrorCodeEnum.XXX)` for business errors
- Use `GlobalExceptionHandler` with `@RestControllerAdvice` for centralized handling
- Error codes defined in `ErrorCodeEnum` (1001-13001 range)

### Security Implementation

- JWT tokens with 1-hour expiration
- BCrypt hashing with pepper (environment variable `AUTH_BCRYPT_PEPPER`)
- Token blacklist stored in Redis
- Use `CurrentEmployee` and `CurrentRole` Thread-Local for request context

### Testing Guidelines

- Use JUnit 5 with Mockito
- Use `@ExtendWith(MockitoExtension.class)`
- Use `MockedStatic` for mocking Thread-Local utilities
- Place tests in same package under `src/test/java`
- Test naming: `{ClassName}Test`, method: `test{MethodName}{Scenario}`

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("Permission Aspect Tests")
class PermissionAspectTest {

    @Mock
    private ProceedingJoinPoint pjp;

    @InjectMocks
    private PermissionAspect permissionAspect;

    @Test
    void testCheckPermissionAdminRoleAuthorized() throws Throwable { ... }
}
```

### Database Conventions

- All tables have `is_deleted` (soft delete), `create_time`, `update_time`
- Use generated columns for unique constraints (`active_username`)
- Use unsigned integers for IDs and foreign keys
- Follow naming: `t_{entity_name}`

### Configuration

- All sensitive config via environment variables
- See README.md for required environment variables
- Application config in `application.yml`

### Common Patterns

1. **AOP Permissions**: `@Permission(role = RoleEnum.ROLE_ADMIN)`
2. **Activity Logging**: `@AddActivityLog`
3. **Thread-Local Context**: `CurrentEmployee.get()`, `CurrentRole.get()`
4. **Standard Response**: `Result.success(data)` / `Result.fail(message)`

### Key Files Reference

| File | Purpose |
|------|---------|
| `ErrorCodeEnum.java` | Error codes and messages |
| `RoleEnum.java` | Role definitions (ROLE_ADMIN, ROLE_EMPLOYEE) |
| `BusinessException.java` | Custom exception class |
| `TokenFilter.java` | JWT authentication filter |
| `PermissionAspect.java` | Authorization AOP |
| `ActivityLogAspect.java` | Audit logging AOP |
| `GlobalExceptionHandler.java` | Centralized exception handling |

### Default Credentials (Development)

- URL: `http://localhost:8080`
- Username: `admin`
- Password: `admin123`
