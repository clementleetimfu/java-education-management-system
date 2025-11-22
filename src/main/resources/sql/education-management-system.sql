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
    password        VARCHAR(128)     NOT NULL DEFAULT 'abc123' COMMENT 'Password',
    name            VARCHAR(50)      NOT NULL COMMENT 'Full Name',
    gender          TINYINT UNSIGNED NOT NULL COMMENT 'Gender, 1: Male, 2: Female',
    phone           VARCHAR(15)      NOT NULL COMMENT 'Phone Number',
    job_title       TINYINT UNSIGNED COMMENT 'Job Title, 1: Class Teacher, 2: Lecturer, 3: Student Affairs Manager, 4: Academic Research Manager, 5: Advisor',
    salary          INT UNSIGNED     NOT NULL COMMENT 'Salary',
    image           VARCHAR(255) COMMENT 'Avatar',
    hire_date       DATE             NOT NULL COMMENT 'Hire Date',
    dept_id         INT UNSIGNED COMMENT 'Department ID',
    create_time     DATETIME         NOT NULL COMMENT 'Creation Time',
    update_time     DATETIME         NOT NULL COMMENT 'Update Time',
    is_deleted      TINYINT(1)       NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',
    active_username VARCHAR(50) AS (CASE WHEN is_deleted = 0 THEN username ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) username',
    active_phone    VARCHAR(15) AS (CASE WHEN is_deleted = 0 THEN phone ELSE NULL END) STORED COMMENT 'Active (is_deleted = 0) phone',

    UNIQUE INDEX idx_username (active_username),
    UNIQUE INDEX idx_phone (phone),
    INDEX idx_name (name),
    INDEX idx_gender (gender),
    INDEX idx_job_title (job_title),
    INDEX idx_salary (salary),
    INDEX idx_hire_date (hire_date),
    INDEX idx_dept_id (dept_id),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)

) COMMENT ='Employee Table';


INSERT INTO employee (username, password, name, gender, phone, job_title, salary, image, hire_date, dept_id,
                      create_time, update_time, is_deleted)
VALUES ('johnsmith', '123456', 'John Smith', 1, '13309090001', 4, 15000, '5.png', '2000-01-01', 2,
        '2025-01-10 10:10:10', '2025-01-15 12:12:12', 0),
       ('michaeljones', '123456', 'Michael Jones', 1, '13309090002', 2, 8600, '01.png', '2015-01-01', 2,
        '2025-01-11 09:15:23', '2025-02-01 14:20:33', 0),
       ('davidlee', '123456', 'David Lee', 1, '13309090003', 2, 8900, '01.png', '2008-05-01', 2, '2025-02-05 08:35:12',
        '2025-03-01 11:30:45', 0),
       ('williambrown', '123456', 'William Brown', 1, '13309090004', 2, 9200, '01.png', '2007-01-01', 2,
        '2025-02-15 15:10:50', '2025-04-10 09:40:33', 0),
       ('jameswilson', '123456', 'James Wilson', 1, '13309090005', 2, 9500, '01.png', '2012-12-05', 2,
        '2025-03-10 12:45:11', '2025-05-12 17:15:05', 0),
       ('emilyclark', '123456', 'Emily Clark', 2, '13309090006', 3, 6500, '01.png', '2013-09-05', 1,
        '2025-03-20 20:25:33', '2025-06-10 16:35:45', 0),
       ('chrisjohnson', '123456', 'Chris Johnson', 1, '13309090007', 1, 4700, '01.png', '2005-08-01', 1,
        '2025-04-01 16:35:33', '2025-07-12 16:35:47', 0),
       ('robertmiller', '123456', 'Robert Miller', 1, '13309090008', 1, 4800, '01.png', '2014-11-09', 1,
        '2025-04-05 16:35:33', '2025-07-15 16:35:49', 0),
       ('josephmoore', '123456', 'Joseph Moore', 1, '13309090009', 1, 4900, '01.png', '2011-03-11', 1,
        '2025-04-10 16:35:33', '2025-08-01 16:35:51', 0),
       ('danielwhite', '123456', 'Daniel White', 1, '13309090010', 1, 5000, '01.png', '2013-09-05', 1,
        '2025-04-15 16:35:33', '2025-08-10 16:35:53', 0),
       ('anthonyharris', '123456', 'Anthony Harris', 1, '13309090011', 2, 9700, '01.png', '2007-02-01', 2,
        '2025-05-01 16:35:33', '2025-08-15 16:35:55', 0),
       ('stephenmartin', '123456', 'Stephen Martin', 1, '13309090012', 2, 10000, '01.png', '2008-08-18', 2,
        '2025-05-05 16:35:33', '2025-08-20 16:35:57', 0),
       ('brianthomas', '123456', 'Brian Thomas', 1, '13309090013', 1, 5300, '01.png', '2012-11-01', 1,
        '2025-05-10 16:35:33', '2025-08-25 16:35:59', 0),
       ('kevinjackson', '123456', 'Kevin Jackson', 1, '13309090014', 2, 10600, '01.png', '2002-08-01', 2,
        '2025-05-15 16:35:33', '2025-08-30 16:36:01', 0),
       ('sarahlee', '123456', 'Sarah Lee', 2, '13309090015', 2, 10900, '01.png', '2011-05-01', 2, '2025-06-01 16:35:33',
        '2025-09-05 16:36:03', 0),
       ('markallen', '123456', 'Mark Allen', 1, '13309090016', 2, 9600, '01.png', '2010-01-01', 2,
        '2025-06-05 16:35:33', '2025-09-10 16:36:05', 0),
       ('lindseyadams', '12345678', 'Lindsey Adams', 1, '13309090017', 1, 5800, '01.png', '2015-03-21', 1,
        '2025-06-10 16:35:33', '2025-09-15 16:36:07', 0),
       ('nathanroberts', '123456', 'Nathan Roberts', 1, '13309090018', 2, 10200, '01.png', '2015-01-01', 2,
        '2025-06-15 16:35:33', '2025-09-20 16:36:09', 0),
       ('carolynking', '123456', 'Carolyn King', 2, '13309090019', 2, 10500, '01.png', '2008-01-01', 2,
        '2025-07-01 16:35:33', '2025-09-25 16:36:11', 0),
       ('edwardhill', '123456', 'Edward Hill', 1, '13309090020', 2, 10800, '01.png', '2018-01-01', 2,
        '2025-07-05 16:35:33', '2025-09-30 16:36:13', 0),
       ('frankyoung', '123456', 'Frank Young', 1, '13309090021', 5, 5200, '01.png', '2015-01-01', 3,
        '2025-07-10 16:35:33', '2025-10-05 16:36:15', 0),
       ('garyscott', '123456', 'Gary Scott', 1, '13309090022', 5, 5500, '01.png', '2016-01-01', 3,
        '2025-07-15 16:35:33', '2025-10-10 16:36:17', 0),
       ('henrycooper', '123456', 'Henry Cooper', 1, '13309090023', 5, 5800, '01.png', '2012-01-01', 3,
        '2025-07-20 16:35:33', '2025-10-15 16:36:19', 0),
       ('ianward', '123456', 'Ian Ward', 1, '13309090024', 5, 5000, '01.png', '2006-01-01', 3, '2025-07-25 16:35:33',
        '2025-10-20 16:36:21', 0),
       ('jackbaker', '123456', 'Jack Baker', 1, '13309090025', 5, 4800, '01.png', '2002-01-01', 3,
        '2025-07-30 16:35:33', '2025-10-25 16:36:23', 0),
       ('keithcollins', '123456', 'Keith Collins', 1, '13309090026', 5, 5400, '01.png', '2011-01-01', 3,
        '2025-08-01 16:35:33', '2025-11-01 22:12:46', 0),
       ('leoanderson', '123456', 'Leo Anderson', 1, '13309090027', 2, 6600, '8.png', '2004-01-01', 2,
        '2025-08-05 16:35:33', '2025-11-05 17:56:59', 0),
       ('matthewrobinson', '123456', 'Matthew Robinson', 1, '13309090028', 5, 5000, '6.png', '2007-01-01', 3,
        '2025-08-10 16:35:33', '2025-11-10 16:34:22', 0),
       ('olivertaylor', '123456', 'Oliver Taylor', 1, '13309090030', 2, 5000, '01.png', '2020-03-01', NULL,
        '2025-08-15 16:35:33', '2025-11-15 16:36:31', 0),
       ('patrickwhite', '123456', 'Patrick White', 1, '18809091212', 2, 6800, '1.png', '2023-10-19', 2,
        '2025-08-20 20:44:54', '2025-11-20 09:41:04', 0);

-- Work Experience
CREATE TABLE work_experience
(
    id           INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, primary key',
    emp_id       INT UNSIGNED COMMENT 'Employee ID',
    start_date   DATE COMMENT 'Start date',
    end_date     DATE COMMENT 'End date',
    company_name VARCHAR(50) COMMENT 'Company name',
    job_title    VARCHAR(50) COMMENT 'Position / Job title',
    create_time  DATETIME   NOT NULL COMMENT 'Creation Time',
    update_time  DATETIME   NOT NULL COMMENT 'Update Time',
    is_deleted   TINYINT(1) NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',

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
    id           INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, Primary Key',
    operate_time DATETIME      NOT NULL COMMENT 'Operation Time',
    info         VARCHAR(2000) NOT NULL COMMENT 'Log Information',
    create_time  DATETIME      NOT NULL COMMENT 'Creation Time',
    update_time  DATETIME      NOT NULL COMMENT 'Update Time',
    is_deleted   TINYINT(1)    NOT NULL CHECK (is_deleted IN (0, 1)) COMMENT 'Soft delete flag, 0: active, 1: deleted',
    INDEX idx_operate_time (operate_time),
    INDEX idx_create_time (create_time),
    INDEX idx_update_time (update_time),
    INDEX idx_is_deleted (is_deleted)
) COMMENT = 'Activity Log';
