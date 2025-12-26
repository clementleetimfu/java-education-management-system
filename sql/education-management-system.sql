CREATE DATABASE education_management_system;

USE `education_management_system`;

-- Department
CREATE TABLE department
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, Primary key',
    name        VARCHAR(50) NOT NULL COMMENT 'Department name',
    create_time DATETIME    NOT NULL COMMENT 'Creation time',
    update_time DATETIME    NOT NULL COMMENT 'Update time',
    is_deleted  TINYINT(1)  NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',
    active_name VARCHAR(50) AS (CASE WHEN is_deleted = 0 THEN name ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) department name',

    UNIQUE INDEX idx_active_name (active_name),
    INDEX idx_name (name),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)

) COMMENT ='Department Table';

INSERT INTO department (name, create_time, update_time, is_deleted)
VALUES ('Student Affairs', '2024-01-15 10:23:45', '2024-05-12 14:37:21', 0),
       ('Curriculum and Instruction', '2024-02-28 09:12:33', '2024-09-20 16:45:10', 0),
       ('Academic Advising', '2024-03-10 08:45:12', '2024-11-01 11:22:55', 0),
       ('Career Services', '2024-04-05 15:17:50', '2024-07-14 09:55:33', 0),
       ('Human Resources', '2024-06-22 12:34:11', '2024-10-18 17:20:05', 0),
       ('Administration', '2024-07-30 20:56:37', '2024-12-28 20:56:37', 0);

-- Employee
CREATE TABLE employee
(
    id              INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    username        VARCHAR(50)      NOT NULL COMMENT 'Username',
    password        VARCHAR(128)     NOT NULL DEFAULT '$2a$10$EPvtiNWUunW9w.zGmC9DSudn2Hcc/UDC6MTUX7QTZOuNz7XLrLFEm' COMMENT 'Password - abc123',
    name            VARCHAR(50)      NOT NULL COMMENT 'Full Name',
    gender          TINYINT UNSIGNED NOT NULL COMMENT 'Gender, 1: Male, 2: Female',
    phone           VARCHAR(15)      NOT NULL COMMENT 'Phone Number',
    job_title       TINYINT UNSIGNED NOT NULL COMMENT 'Job Title, refer job_title table',
    salary          BIGINT UNSIGNED  NOT NULL COMMENT 'Salary',
    image           VARCHAR(255) COMMENT 'Avatar',
    hire_date       DATE             NOT NULL COMMENT 'Hire Date',
    dept_id         INT UNSIGNED     NOT NULL COMMENT 'Department ID',
    is_first_logged TINYINT(1)       NOT NULL DEFAULT 0 CHECK (is_first_logged IN (0, 1)) COMMENT 'First logged flag, 0: never logged, 1: first login completed',
    role_id         INT UNSIGNED     NOT NULL COMMENT 'Role ID',
    create_time     DATETIME         NOT NULL COMMENT 'Creation Time',
    update_time     DATETIME         NOT NULL COMMENT 'Update Time',
    is_deleted      TINYINT(1)       NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',
    active_username VARCHAR(50) AS (CASE WHEN is_deleted = 0 THEN username ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) username',
    active_phone    VARCHAR(15) AS (CASE WHEN is_deleted = 0 THEN phone ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) phone',

    UNIQUE INDEX idx_username (active_username),
    UNIQUE INDEX idx_phone (active_phone),
    INDEX idx_name (name),
    INDEX idx_gender (gender),
    INDEX idx_job_title (job_title),
    INDEX idx_salary (salary),
    INDEX idx_hire_date (hire_date),
    INDEX idx_dept_id (dept_id),
    INDEX idx_is_first_logged (is_first_logged),
    INDEX idx_role_id (role_id),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)

) COMMENT ='Employee Table';

-- Admin (role_id = 1)
INSERT INTO employee (username, password, name, gender, phone, job_title, salary, hire_date, dept_id, role_id, is_first_logged,
                      create_time, update_time, is_deleted)
VALUES ('admin', '$2a$10$rhiOHO4oLAN7rPZ1ObDimO2r6dcuunTtPhOM0wEmi8dUtXxojKl3G', 'Admin', 1, '0000000000', 0, 0, '2025-01-01', 0, 1, 1,
        NOW(), NOW(), 0);

-- Employee (role_id = 2)
INSERT INTO employee (username, name, gender, phone, job_title, salary, hire_date, dept_id, role_id, create_time,
                      update_time, is_deleted)
VALUES ('john.doe', 'John Doe', 1, '01012345678', 1, 5000, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 1,
        2, NOW(), NOW(), 0),
       ('jane.smith', 'Jane Smith', 2, '01023456789', 2, 5500, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY),
        2, 2, NOW(), NOW(), 0),
       ('michael.jones', 'Michael Jones', 1, '01034567890', 3, 6000,
        DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 3, 2, NOW(), NOW(), 0),
       ('emily.wang', 'Emily Wang', 2, '01045678901', 2, 5200, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY),
        2, 2, NOW(), NOW(), 0),
       ('david.lee', 'David Lee', 1, '01056789012', 1, 5800, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 1,
        2, NOW(), NOW(), 0),
       ('sophia.kim', 'Sophia Kim', 2, '01067890123', 3, 5300, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY),
        3, 2, NOW(), NOW(), 0),
       ('daniel.chen', 'Daniel Chen', 1, '01078901234', 2, 6100,
        DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 2, 2, NOW(), NOW(), 0),
       ('olivia.li', 'Olivia Li', 2, '01089012345', 1, 5400, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 1,
        2, NOW(), NOW(), 0),
       ('james.ho', 'James Ho', 1, '01090123456', 3, 5700, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 3,
        2, NOW(), NOW(), 0),
       ('lily.zhang', 'Lily Zhang', 2, '01001234567', 2, 5600, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY),
        2, 2, NOW(), NOW(), 0),
       ('alex.yang', 'Alex Yang', 1, '01011223344', 1, 5900, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 1,
        2, NOW(), NOW(), 0),
       ('chloe.tan', 'Chloe Tan', 2, '01022334455', 2, 5500, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 2,
        2, NOW(), NOW(), 0),
       ('ryan.huang', 'Ryan Huang', 1, '01033445566', 3, 6200, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY),
        3, 2, NOW(), NOW(), 0),
       ('mia.fang', 'Mia Fang', 2, '01044556677', 1, 5100, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 1,
        2, NOW(), NOW(), 0),
       ('ethan.wu', 'Ethan Wu', 1, '01055667788', 2, 6000, DATE_ADD('2025-01-01', INTERVAL FLOOR(RAND() * 90) DAY), 2,
        2, NOW(), NOW(), 0);


-- Work Experience
CREATE TABLE work_experience
(
    id           INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    emp_id       INT UNSIGNED NOT NULL COMMENT 'Employee ID',
    start_date   DATE         NOT NULL COMMENT 'Start date',
    end_date     DATE         NOT NULL COMMENT 'End date',
    company_name VARCHAR(50)  NOT NULL COMMENT 'Company name',
    job_title    VARCHAR(50)  NOT NULL COMMENT 'Position / Job title',
    create_time  DATETIME     NOT NULL COMMENT 'Creation Time',
    update_time  DATETIME     NOT NULL COMMENT 'Update Time',
    is_deleted   TINYINT(1)   NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    INDEX idx_emp_id (emp_id),
    INDEX idx_start_date (start_date),
    INDEX idx_end_date (end_date),
    INDEX idx_company_name (company_name),
    INDEX idx_job_title (job_title),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT 'Work experience';

-- Activity Log
CREATE TABLE activity_log
(
    id             INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, Primary Key',
    operate_emp_id INT UNSIGNED    NOT NULL COMMENT 'Operator Employee ID',
    operate_time   DATETIME        NOT NULL COMMENT 'Operation Time',
    class_name     VARCHAR(255)    NOT NULL COMMENT 'Class Name',
    method_name    VARCHAR(255)    NOT NULL COMMENT 'Method Name',
    method_params  TEXT COMMENT 'Method Parameters (serialized)',
    return_value   TEXT COMMENT 'Method Return Value (serialized)',
    duration       BIGINT UNSIGNED NOT NULL COMMENT 'Execution Duration in ms',
    create_time    DATETIME        NOT NULL COMMENT 'Creation Time',
    update_time    DATETIME        NOT NULL COMMENT 'Update Time',
    is_deleted     TINYINT(1)      NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    INDEX idx_operate_emp_id (operate_emp_id),
    INDEX idx_operate_time (operate_time),
    INDEX idx_class_name (class_name),
    INDEX idx_method_name (method_name),
    FULLTEXT INDEX idx_method_params_fulltext (method_params),
    FULLTEXT INDEX idx_return_value_fulltext (return_value),
    INDEX idx_duration (duration),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT ='Activity Log';

-- Class
CREATE TABLE clazz
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    name        VARCHAR(50)      NOT NULL COMMENT 'Class name',
    start_date  DATE             NOT NULL COMMENT 'Start date',
    end_date    DATE             NOT NULL COMMENT 'End date',
    teacher_id  INT UNSIGNED     NOT NULL COMMENT 'Class teacher ID, link to employee table',
    subject     TINYINT UNSIGNED NOT NULL COMMENT 'Subject, refer subject table',
    create_time DATETIME         NOT NULL COMMENT 'Creation time',
    update_time DATETIME         NOT NULL COMMENT 'Update time',
    is_deleted  TINYINT(1)       NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag (0: active, 1: deleted)',

    active_name VARCHAR(50) AS (CASE WHEN is_deleted = 0 THEN name ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) class name',

    UNIQUE INDEX idx_active_name (active_name),
    INDEX idx_start_date (start_date),
    INDEX idx_end_date (end_date),
    INDEX idx_teacher_id (teacher_id),
    INDEX idx_subject (subject),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)

) COMMENT ='Class table';

INSERT INTO clazz (name, start_date, end_date, teacher_id, subject, create_time, update_time, is_deleted)
VALUES ('Java Spring Boot Developer Bootcamp - Batch 01', '2025-03-05', '2027-03-07', 3, 1, NOW(), NOW(), 0),
       ('Java Spring Boot Developer Bootcamp - Batch 02', '2025-03-15', '2025-03-18', 5, 1, NOW(), NOW(), 0),
       ('Java Spring Boot Developer Bootcamp - Batch 03', '2025-04-02', '2025-04-05', 8, 1, NOW(), NOW(), 0),
       ('Front End Web Development - Batch 01', '2025-03-20', '2025-03-23', 11, 2, NOW(), NOW(), 0),
       ('Front End Web Development - Batch 02', '2025-04-10', '2025-04-12', 13, 2, NOW(), NOW(), 0),
       ('Front End Web Development - Batch 03', '2025-05-02', '2025-05-06', 16, 2, NOW(), NOW(), 0),
       ('Front End Web Development - Batch 04', '2025-06-01', '2025-06-03', 3, 2, NOW(), NOW(), 0),
       ('Data Science & Machine Learning - Batch 01', '2025-03-12', '2025-03-15', 5, 3, NOW(), NOW(), 0),
       ('Data Science & Machine Learning - Batch 02', '2025-04-05', '2025-04-07', 8, 3, NOW(), NOW(), 0),
       ('Data Science & Machine Learning - Batch 03', '2025-05-08', '2025-05-11', 11, 3, NOW(), NOW(), 0),
       ('Data Science & Machine Learning - Batch 04', '2025-05-20', '2025-05-23', 13, 3, NOW(), NOW(), 0),
       ('Java Spring Boot Developer Bootcamp - Batch 04', '2025-06-05', '2025-06-09', 16, 1, NOW(), NOW(), 0),
       ('Front End Web Development - Batch 05', '2025-06-10', '2025-06-12', 3, 2, NOW(), NOW(), 0),
       ('Data Science & Machine Learning - Batch 05', '2025-06-15', '2025-06-18', 5, 3, NOW(), NOW(), 0),
       ('Java Spring Boot Developer Bootcamp - Batch 05', '2027-06-20', '2028-06-22', 8, 1, NOW(), NOW(), 0);

-- Job Title
CREATE TABLE job_title
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    name        VARCHAR(50) NOT NULL COMMENT 'Job title name',
    create_time DATETIME    NOT NULL COMMENT 'Creation Time',
    update_time DATETIME    NOT NULL COMMENT 'Update Time',
    is_deleted  TINYINT(1)  NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    INDEX idx_name (name),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT = 'Job Title Table';

INSERT INTO job_title (name, create_time, update_time, is_deleted)
VALUES ('Student Affairs Coordinator', NOW(), NOW(), 0),
       ('Class Teacher', NOW(), NOW(), 0),
       ('Academic Advisor', NOW(), NOW(), 0),
       ('Career Counselor', NOW(), NOW(), 0),
       ('HR Manager', NOW(), NOW(), 0),
       ('Administrative Officer', NOW(), NOW(), 0);

-- Subject
CREATE TABLE subject
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    name        VARCHAR(50) NOT NULL COMMENT 'Subject name',
    create_time DATETIME    NOT NULL COMMENT 'Creation Time',
    update_time DATETIME    NOT NULL COMMENT 'Update Time',
    is_deleted  TINYINT(1)  NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    INDEX idx_name (name),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT = 'Subject Table';

INSERT INTO subject (name, create_time, update_time, is_deleted)
VALUES ('Java', NOW(), NOW(), 0),
       ('JavaScript', NOW(), NOW(), 0),
       ('Python', NOW(), NOW(), 0),
       ('C++', NOW(), NOW(), 0),
       ('Go', NOW(), NOW(), 0);

-- Student
CREATE TABLE student
(
    id              INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    name            VARCHAR(50)      NOT NULL COMMENT 'Name',
    no              CHAR(15)         NOT NULL COMMENT 'Student number',
    gender          TINYINT UNSIGNED NOT NULL COMMENT 'Gender: 1 = Male, 2 = Female',
    birthdate       DATE             NOT NULL COMMENT 'Birthdate',
    phone           VARCHAR(15)      NOT NULL COMMENT 'Phone number',
    email           VARCHAR(50)      NOT NULL COMMENT 'Email address',
    address         VARCHAR(100)     NOT NULL COMMENT 'Address',
    education_level TINYINT UNSIGNED COMMENT 'Education level, refer education_level table',
    graduation_date DATE COMMENT 'Graduation date',
    clazz_id        INT UNSIGNED     NOT NULL COMMENT 'Class ID, link to clazz table',
    intake_date     DATE             NOT NULL COMMENT 'Student intake date',
    create_time     DATETIME         NOT NULL COMMENT 'Creation Time',
    update_time     DATETIME         NOT NULL COMMENT 'Update Time',
    is_deleted      TINYINT(1)       NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    active_no       CHAR(15) AS (CASE WHEN is_deleted = 0 THEN no ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) student number',
    active_phone    VARCHAR(15) AS (CASE WHEN is_deleted = 0 THEN phone ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) phone',
    active_email    VARCHAR(50) AS (CASE WHEN is_deleted = 0 THEN email ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) email',

    UNIQUE INDEX idx_active_no (active_no),
    UNIQUE INDEX idx_active_phone (active_phone),
    UNIQUE INDEX idx_active_email (active_email),

    INDEX idx_name (name),
    INDEX idx_no (no),
    INDEX idx_gender (gender),
    INDEX idx_birthdate (birthdate),
    INDEX idx_phone (phone),
    INDEX idx_email (email),
    INDEX idx_address (address),
    INDEX idx_education_level (education_level),
    INDEX idx_graduation_date (graduation_date),
    INDEX idx_clazz_id (clazz_id),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT 'Student table';

INSERT INTO student (name, no, gender, birthdate, phone, email, address, education_level, clazz_id, intake_date,
                     graduation_date, create_time, update_time, is_deleted)
VALUES ('Aaron Tan', '2025060700001', 1, '1999-05-14', '01012345678', 'aaron.tan@example.com',
        '123 Jalan A, Kuala Lumpur', 1, 3, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Bella Lim', '2025060700002', 2, '2000-03-22', '01023456789', 'bella.lim@example.com',
        '456 Jalan B, Kuala Lumpur', 2, 7, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Caleb Ong', '2025060700003', 1, '1998-11-09', '01034567890', 'caleb.ong@example.com',
        '789 Jalan C, Kuala Lumpur', 3, 2, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Diana Goh', '2025060700004', 2, '2001-07-30', '01045678901', 'diana.goh@example.com',
        '321 Jalan D, Kuala Lumpur', 1, 3, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Evan Chua', '2025060700005', 1, '1999-12-18', '01056789012', 'evan.chua@example.com',
        '654 Jalan E, Kuala Lumpur', 2, 7, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Fiona Neo', '2025060700006', 2, '2000-08-05', '01067890123', 'fiona.neo@example.com',
        '987 Jalan F, Kuala Lumpur', 3, 5, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Gavin Tan', '2025060700007', 1, '1998-02-27', '01078901234', 'gavin.tan@example.com',
        '159 Jalan G, Kuala Lumpur', 2, 2, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Hannah Lim', '2025060700008', 2, '2001-06-11', '01089012345', 'hannah.lim@example.com',
        '753 Jalan H, Kuala Lumpur', 1, 3, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Isaac Lee', '2025060700009', 1, '1999-09-19', '01090123456', 'isaac.lee@example.com',
        '852 Jalan I, Kuala Lumpur', 3, 5, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Jasmine Koh', '2025060700010', 2, '2000-01-03', '01001234567', 'jasmine.koh@example.com',
        '951 Jalan J, Kuala Lumpur', 2, 7, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Kyle Wong', '2025060700011', 1, '1998-10-21', '01011223344', 'kyle.wong@example.com',
        '357 Jalan K, Kuala Lumpur', 1, 3, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Leona Teo', '2025060700012', 2, '2001-03-15', '01022334455', 'leona.teo@example.com',
        '258 Jalan L, Kuala Lumpur', 2, 2, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Mason Quek', '2025060700013', 1, '1999-04-07', '01033445566', 'mason.quek@example.com',
        '147 Jalan M, Kuala Lumpur', 3, 5, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Nadia Sim', '2025060700014', 2, '2000-12-29', '01044556677', 'nadia.sim@example.com',
        '369 Jalan N, Kuala Lumpur', 1, 7, '2025-06-07', '2026-06-07', NOW(), NOW(), 0),
       ('Owen Tan', '2025060700015', 1, '1998-06-16', '01055667788', 'owen.tan@example.com',
        '741 Jalan O, Kuala Lumpur', 2, 2, '2025-06-07', '2026-06-07', NOW(), NOW(), 0);

-- Education Level
CREATE TABLE education_level
(
    id          INT UNSIGNED PRIMARY KEY COMMENT 'ID, primary key',
    name        VARCHAR(50) NOT NULL COMMENT 'Education level name',
    create_time DATETIME    NOT NULL COMMENT 'Creation Time',
    update_time DATETIME    NOT NULL COMMENT 'Update Time',
    is_deleted  TINYINT(1)  NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    INDEX idx_name (name),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
);

INSERT INTO education_level (id, name, create_time, update_time, is_deleted)
VALUES (1, 'Middle School', NOW(), NOW(), 0),
       (2, 'High School', NOW(), NOW(), 0),
       (3, 'Diploma', NOW(), NOW(), 0),
       (4, 'Bachelor', NOW(), NOW(), 0),
       (5, 'Master', NOW(), NOW(), 0),
       (6, 'Doctorate', NOW(), NOW(), 0),
       (7, 'Other', NOW(), NOW(), 0);

-- Student Number Sequence
CREATE TABLE student_number_sequence
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    intake_date DATE         NOT NULL COMMENT 'Student intake date',
    last_seq    INT UNSIGNED NOT NULL COMMENT 'Last sequence number used for this intake',
    create_time DATETIME     NOT NULL COMMENT 'Creation Time',
    update_time DATETIME     NOT NULL COMMENT 'Update Time',
    is_deleted  TINYINT(1)   NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT ='Tracks the latest student number sequence per intake date';

INSERT INTO student_number_sequence (intake_date, last_seq, create_time, update_time, is_deleted)
VALUES ('2025-06-07', 15, NOW(), NOW(), 0);

-- Role
CREATE TABLE role
(
    id          INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    name        ENUM ('ROLE_ADMIN', 'ROLE_EMPLOYEE') NOT NULL COMMENT 'Role name',
    create_time DATETIME                             NOT NULL COMMENT 'Creation Time',
    update_time DATETIME                             NOT NULL COMMENT 'Update Time',
    is_deleted  TINYINT(1)                           NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

    INDEX idx_name (name),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT ='Role table';

INSERT INTO role (name, create_time, update_time, is_deleted)
VALUES ('ROLE_ADMIN', NOW(), NOW(), 0),
       ('ROLE_EMPLOYEE', NOW(), NOW(), 0);