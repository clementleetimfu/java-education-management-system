package io.clementleetimfu.educationmanagementsystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private Integer id;
    private String username;
    private String password;
    private String name;
    // 1: Male, 2: Female
    private Integer gender;
    private String phone;
    // 1: Class Teacher, 2: Lecturer, 3: Student Affairs Manager, 4: Academic Research Manager, 5: Advisor
    private Integer jobTitle;
    private Integer salary;
    private String image;
    private LocalDate hireDate;
    private Integer deptId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;
}