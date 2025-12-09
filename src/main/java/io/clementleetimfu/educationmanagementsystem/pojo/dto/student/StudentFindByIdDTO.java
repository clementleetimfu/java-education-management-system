package io.clementleetimfu.educationmanagementsystem.pojo.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFindByIdDTO {
    private Integer id;
    private String name;
    private Integer gender;
    private LocalDate birthdate;
    private String phone;
    private String email;
    private String address;
    private Integer highest_education;
    private LocalDate graduationDate;
    private Integer clazzId;
    private LocalDate intakeDate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;
}
