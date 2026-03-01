# AGENTS.md - Agentic Coding Guidelines

## Project Overview
- Spring Boot 3.5.7, Java 17, Maven 3.9+
- Multi-module: ems-parent, ems-model, ems-common, ems-service
- MySQL 8.x with MyBatis 3.0.5

## Module Structure
```
ems-parent/
├── ems-model/    # Entities, DTOs, VOs, Result wrappers
├── ems-common/   # Utils, Constants, Exceptions, Security
└── ems-service/  # Controllers, Services, Mappers, AOP, Config
```

## Build Commands
```bash
# Full build
cd ems-parent && mvn clean package

# Run tests
cd ems-parent && mvn test
mvn test -Dtest=PermissionAspectTest
mvn test -Dtest=PermissionAspectTest#testCheckPermissionAdminRoleAuthorized

# Module tests
cd ems-common && mvn test
cd ems-service && mvn test

# Skip tests
mvn clean package -DskipTests

# Run app
cd ems-service && mvn spring-boot:run
```

## Code Style Guidelines

### Principles
- Java 17: records, switch expressions, text blocks
- Lombok: `@Data`, `@Builder`, `@Slf4j`, `@AllArgsConstructor`, `@NoArgsConstructor`
- Timestamps: `LocalDateTime`
- Entities: soft delete (`is_deleted`)
- Services: `@Transactional(readOnly=true)` for queries, `@Transactional` for mutations

### Naming
| Classes   | PascalCase   | `StudentController` |
| Methods   | camelCase    | `findStudentById` |
| Variables | camelCase    | `studentService` |
| Constants | UPPER_SNAKE  | `SUCCESS_CODE` |
| Packages  | lowercase    | `io.clementleetimfu.educationmanagementsystem` |
| Tables    | snake_case   | `t_student` |

### Import Order: Java/Jakarta → Spring → Third-party → Internal

### Package Structure (ems-service)
```
io.clementleetimfu.educationmanagementsystem/
├── annotation/  # @Permission, @AddActivityLog
├── aop/        # PermissionAspect, ActivityLogAspect
├── config/     # Configuration classes
├── controller/  # REST controllers
├── exception/   # GlobalExceptionHandler
├── filter/     # TokenFilter (JWT)
├── mapper/     # MyBatis mappers
└── service/
    ├── Service interfaces
    └── impl/    # Service implementations
```

### Model Package Structure (ems-model)
```
pojo/
├── dto/        # Request DTOs
├── entity/     # Database entities
└── vo/         # Response VOs
    └── result/  # Result, PageResult wrappers
```

## Common Patterns

### Controller
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

### Service
```java
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Transactional(readOnly = true)
    public PageResult<ResourceVO> search(...) { ... }

    @Transactional
    public Boolean addResource(...) { ... }
}
```

### Response: `Result.success(data)` / `Result.fail(message)` / `Result.success()`

### Exception: `BusinessException(ErrorCodeEnum.XXX)`, GlobalExceptionHandler handles all

### Security
- JWT: 1-hour expiration, BCrypt with pepper (cost 10, env `AUTH_BCRYPT_PEPPER`)
- Redis blacklist: `blacklistToken:{employeeId}`
- TokenFilter excludes: `/auth/login`, `/auth/update-password`
- Permissions: `@Permission(role = RoleEnum.ROLE_ADMIN)`
- Activity logging: `@AddActivityLog`
- Thread-Local: `CurrentEmployee.get()`, `CurrentRole.get()`

## Testing Guidelines

- JUnit 5 (Jupiter) with Mockito 5.17.0
- `@ExtendWith(MockitoExtension.class)`, `@DisplayName`
- `@Mock` for dependencies, `@InjectMocks` for class under test
- `MockedStatic` for Thread-Local (`CurrentEmployee`, `CurrentRole`)
- Naming: `{ClassName}Test`, methods: `test{MethodName}{Scenario}`
- Location: Same package under `src/test/java`
- Cleanup: Always close `MockedStatic` in `@AfterEach`

```java
@ExtendWith(MockitoExtension.class)
@DisplayName("Permission Aspect Tests")
class PermissionAspectTest {
    @Mock
    private ProceedingJoinPoint pjp;

    @InjectMocks
    private PermissionAspect permissionAspect;

    private MockedStatic<CurrentRole> mockedCurrentRole;

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
    void testCheckPermissionAdminRoleAuthorized() throws Throwable { ... }
}
```

## Database Conventions
- Tables: `is_deleted` (soft delete), `create_time`, `update_time`
- Generated columns: `active_username`, `active_name` (unique constraints)
- IDs: `INT UNSIGNED`, `TINYINT UNSIGNED`
- Naming: `t_{entity_name}`

## Configuration
- Sensitive config: Environment variables (see README.md)
- Application config: `ems-service/src/main/resources/application.yml`
- Database: MySQL 8.x, Redis 8.4
- Cloud storage: Cloudflare R2 (S3-compatible)

## Key Files
| File                  | Purpose                  |
|-----------------------|--------------------------|
| `ErrorCodeEnum.java`   | Error codes (1001-13001) |
| `RoleEnum.java`       | ROLE_ADMIN, ROLE_EMPLOYEE |
| `BusinessException.java` | Custom exception     |
| `TokenFilter.java`     | JWT authentication   |
| `PermissionAspect.java` | Authorization AOP   |
| `ActivityLogAspect.java` | Audit logging AOP |
| `GlobalExceptionHandler.java` | Exception handling |

## Default Credentials (Development)
- URL: `http://localhost:8080`
- Username: `admin`
- Password: `admin123`
