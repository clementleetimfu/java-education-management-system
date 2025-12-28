# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

This is a multi-module Maven project. Build from the `ems-parent` directory:

```bash
# Build entire project
cd ems-parent
mvn clean package

# Run tests
mvn test

# Run a specific test class
mvn test -Dtest=ClassName

# Run the application (after building)
cd ems-service/target
java -jar ems-service-0.0.1-SNAPSHOT.jar
```

The application runs on port 8080 by default.

## Project Architecture

This is a multi-module Spring Boot application with clear separation of concerns:

### Module Structure

- **`ems-parent`**: Parent POM managing dependency versions (Spring Boot 3.5.7, Java 17)
- **`ems-common`**: Shared utilities - bcrypt password hashing, JWT token handling, Redis integration, thread-local storage
- **`ems-model`**: Entity classes, DTOs, and response wrappers (`Result<T>`, `PageResult<T>`)
- **`ems-service`**: Main application - controllers, services, MyBatis mappers, AOP aspects

### Layered Architecture

```
Controller -> Service -> MyBatis Mapper -> MySQL
           |
        DTOs + Validation
           |
     Security (JWT Filter, AOP)
```

### Key Patterns

1. **Custom AOP Annotations** (in `ems-service/annotation/`):
   - `@Permission(role = RoleEnum.ROLE_ADMIN)` - Restricts endpoint access by role
   - `@AddActivityLog` - Automatically logs admin operations to `activity_log` table

2. **Role-Based Access Control**:
   - `ROLE_ADMIN`: Full access to all operations
   - `ROLE_EMPLOYEE`: Read-only access
   - Permissions enforced via AOP in `ems-service/aop/PermissionAspect.java`

3. **JWT Authentication Flow**:
   - BCrypt password hashing with pepper enhancement
   - JWT tokens expire after 1 hour
   - Redis-based token blacklisting on logout (`ems-common/utils/redis/`)
   - JWT filter validates tokens on protected endpoints

4. **MyBatis ORM**:
   - XML mappers in `ems-service/src/main/resources/mapper/`
   - Mapper interfaces in `ems-service/mapper/`
   - Uses PageHelper for pagination

## Environment Configuration

All configuration is externalized via environment variables in `application.yml`:

- **Database**: `MYSQL_HOST`, `MYSQL_PORT`, `MYSQL_DB`, `MYSQL_USER`, `MYSQL_PASSWORD`
- **Redis**: `REDIS_HOST`, `REDIS_PORT`, `REDIS_DATABASE`, `REDIS_PASSWORD`
- **Cloudflare R2**: `CLOUDFLARE_R2_BUCKET_NAME`, `CLOUDFLARE_R2_ACCOUNT_ID`, `CLOUDFLARE_R2_ACCESS_KEY`, `CLOUDFLARE_R2_SECRET_KEY`, `CLOUDFLARE_R2_PUBLIC_URL`
- **Auth**: `AUTH_JWT_SECRET_KEY`, `AUTH_BCRYPT_PEPPER`

## Database Schema

- SQL schema: `sql/education-management-system.sql`
- Default admin: username `admin`, password `abc123`
- Soft delete pattern used across all tables (`is_deleted`, `create_time`, `update_time`)
- Key tables: `student`, `employee`, `clazz` (class), `department`, `subject`, `activity_log`, `role`

## Testing

- JUnit 5 with Spring Boot Test
- Tests organized by module in `src/test/java/`
- Key test coverage: security utilities (bcrypt, JWT, Redis), AOP aspects, enums
