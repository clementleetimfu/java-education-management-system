# AGENTS.md - Agentic Coding Guidelines

## Project Overview
- Spring Boot 3.5.7, Java 17, Maven 3.9+
- Multi-module Maven project (modules are **siblings** to ems-parent):
  - `ems-parent/` - Parent aggregator POM with relative paths
  - `ems-model/` - Entities, DTOs, VOs, Result wrappers
  - `ems-common/` - Utils, Constants, Exceptions, Security
  - `ems-service/` - Controllers, Services, Mappers, AOP, Config
- MySQL 8.x with MyBatis 3.0.5, Redis for token blacklisting
- Cloudflare R2 for file storage (S3-compatible)

## Build/Lint/Test Commands

```bash
# Full build (from project root)
cd ems-parent && mvn clean package

# Run all tests
cd ems-parent && mvn test

# Run single test class
mvn test -Dtest=PermissionAspectTest

# Run single test method
mvn test -Dtest=PermissionAspectTest#testCheckPermissionAdminRoleAuthorized

# Run tests by module
cd ems-common && mvn test
cd ems-service && mvn test

# Skip tests during build
mvn clean package -DskipTests

# Run application
cd ems-service && mvn spring-boot:run
```

## Code Style Guidelines

### Core Principles
- **Java 17 features**: Use records, switch expressions, text blocks where appropriate
- **Lombok annotations**: `@Data`, `@Builder`, `@Slf4j`, `@AllArgsConstructor`, `@NoArgsConstructor`
- **Timestamps**: Always use `java.time.LocalDateTime` or `java.time.LocalDate`
- **Large numbers**: Use `java.math.BigInteger` for salary fields
- **Soft delete**: All entities have `is_deleted` boolean field
- **Transaction management**: Use `@Transactional(readOnly = true)` for queries, `@Transactional(rollbackFor = Exception.class)` for mutations

### Naming Conventions
| Type | Convention | Example |
|------|------------|---------|
| Classes | PascalCase | `StudentController`, `EmployeeServiceImpl` |
| Methods | camelCase | `findStudentById`, `searchEmployee` |
| Variables | camelCase | `studentService`, `employeeMapper` |
| Constants | UPPER_SNAKE | `SUCCESS_CODE`, `FAIL_CODE` |
| Packages | lowercase | `io.clementleetimfu.educationmanagementsystem` |
| Tables | snake_case | `employee`, `student` (without `t_` prefix) |

### Import Order
1. Java/Jakarta standard library
2. Spring Framework imports
3. Third-party libraries (Lombok, ModelMapper, MyBatis, etc.)
4. Internal project imports (`io.clementleetimfu.educationmanagementsystem.*`)

Example:
```java
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
```

### Package Structure
```
io.clementleetimfu.educationmanagementsystem/
├── annotation/       # Custom annotations (@Permission, @AddActivityLog)
├── aop/              # Aspect classes (PermissionAspect, ActivityLogAspect)
├── config/           # Configuration classes
├── controller/       # REST controllers
├── exception/        # GlobalExceptionHandler
├── filter/           # TokenFilter (JWT authentication)
├── mapper/           # MyBatis mapper interfaces
└── service/
    ├── Service interfaces
    └── impl/         # Service implementations

pojo/
├── dto/              # Request DTOs (Data Transfer Objects)
├── entity/           # Database entities
└── vo/               # Response VOs (Value Objects)
    └── result/       # Result, PageResult wrappers
```

## Common Patterns

### Controller Pattern
```java
@RestController
@RequestMapping("/resource-name")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping("/search")
    public Result<PageResult<ResourceVO>> search(@ModelAttribute ResourceSearchDTO dto) {
        return Result.success(resourceService.search(dto));
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @PostMapping
    public Result<Boolean> add(@RequestBody ResourceAddDTO dto) {
        return Result.success(resourceService.add(dto));
    }
}
```

### Service Pattern
```java
@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional(readOnly = true)
    public PageResult<ResourceVO> search(ResourceSearchDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        List<ResourceVO> list = resourceMapper.search(dto);
        if (list.isEmpty()) {
            throw new BusinessException(ErrorCodeEnum.RESOURCE_NOT_FOUND);
        }
        Page<ResourceVO> page = (Page<ResourceVO>) list;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean add(ResourceAddDTO dto) {
        // Business logic with error handling
    }
}
```

### Entity Pattern
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String username;
    private String password;
    private Integer gender;      // 1: Male, 2: Female (use Integer for enums)
    private BigInteger salary;   // Use BigInteger for large numbers
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;   // Soft delete flag
}
```

### Response Pattern
```java
// Success with data
return Result.success(data);

// Success without data
return Result.success();

// Failure
return Result.fail(ErrorCodeEnum.XXX.getCode(), ErrorCodeEnum.XXX.getMessage());
throw new BusinessException(ErrorCodeEnum.XXX);
```

### Error Handling
- Use `BusinessException(ErrorCodeEnum.XXX)` for business errors
- GlobalExceptionHandler converts exceptions to Result responses
- Log with `@Slf4j`: `log.warn("Message:{}", value)`, `log.error("Message:{}", value, exception)`

## Security Implementation
- **JWT**: 1-hour expiration, stored in request headers
- **BCrypt**: Cost factor 10 with pepper from `AUTH_BCRYPT_PEPPER` env var
- **Redis blacklist**: Key pattern `blacklistToken:{employeeId}`
- **TokenFilter**: Excludes `/auth/login`, `/auth/update-password`
- **Authorization**: `@Permission(role = RoleEnum.ROLE_ADMIN)` for admin-only endpoints
- **Audit logging**: `@AddActivityLog` annotation for operation tracking
- **Thread-Local context**: Use `CurrentEmployee.get()` and `CurrentRole.get()` in services

## Testing Guidelines

### Test Structure
```java
@ExtendWith(MockitoExtension.class)
@DisplayName("Resource Service Tests")
class ResourceServiceImplTest {
    @Mock
    private ResourceMapper resourceMapper;

    @InjectMocks
    private ResourceServiceImpl resourceService;

    private MockedStatic<CurrentEmployee> mockedCurrentEmployee;

    @BeforeEach
    void setUp() {
        mockedCurrentEmployee = mockStatic(CurrentEmployee.class);
    }

    @AfterEach
    void tearDown() {
        if (mockedCurrentEmployee != null) {
            mockedCurrentEmployee.close();  // Always close MockedStatic
        }
    }

    @Test
    @DisplayName("Test method description")
    void testMethodName_Scenario() {
        // Arrange
        when(resourceMapper.findById(1)).thenReturn(Optional.of(entity));

        // Act
        ResourceVO result = resourceService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(resourceMapper, times(1)).findById(1);
    }
}
```

### Test Naming Convention
- Class: `{ClassName}Test`
- Method: `test{MethodName}_{Scenario}` (e.g., `testFindById_NotFound`)
- Location: Same package under `src/test/java`

### Test File Locations
- `ems-service/src/test/java/io/clementleetimfu/educationmanagementsystem/`
- `ems-common/src/test/java/io/clementleetimfu/educationmanagementsystem/`

## Database Conventions
- **Primary keys**: `INT UNSIGNED AUTO_INCREMENT`
- **Soft delete**: `is_deleted` `TINYINT(1)` with CHECK constraint
- **Timestamps**: `create_time`, `update_time` `DATETIME NOT NULL`
- **Generated columns**: `active_username`, `active_name` for unique constraints on non-deleted rows
- **Foreign keys**: Use `INT UNSIGNED`
- **Table naming**: Snake case without prefix (e.g., `employee`, `student`, not `t_employee`)

## Configuration
- Environment variables for sensitive config (see README.md)
- Application config: `ems-service/src/main/resources/application.yml`
- MyBatis XML mappers: `ems-service/src/main/resources/io/clementleetimfu/educationmanagementsystem/mapper/`

## Key Files Reference
| File | Purpose |
|------|---------|
| `ErrorCodeEnum.java` | Business error codes (1001-13001) |
| `RoleEnum.java` | ROLE_ADMIN, ROLE_EMPLOYEE |
| `BusinessException.java` | Custom exception for business errors |
| `TokenFilter.java` | JWT authentication filter |
| `PermissionAspect.java` | Role-based authorization AOP |
| `ActivityLogAspect.java` | Audit logging AOP |
| `GlobalExceptionHandler.java` | Centralized exception handling |
| `Result.java` | Standard API response wrapper |
| `PageResult.java` | Paginated response wrapper |

## Default Credentials (Development)
- URL: `http://localhost:8080`
- Username: `admin`
- Password: `admin123`