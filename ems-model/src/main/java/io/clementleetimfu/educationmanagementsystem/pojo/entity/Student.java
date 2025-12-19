package io.clementleetimfu.educationmanagementsystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String name;
    private String no;
    private Integer gender;
    private LocalDate birthdate;
    private String phone;
    private String email;
    private String address;
    private Integer educationLevel;
    private LocalDate graduationDate;
    private Integer clazzId;
    private LocalDate intakeDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;
}
