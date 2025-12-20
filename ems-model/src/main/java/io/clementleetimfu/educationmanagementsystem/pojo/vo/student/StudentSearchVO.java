package io.clementleetimfu.educationmanagementsystem.pojo.vo.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSearchVO {
    private Integer id;
    private String name;
    private String no;
    private String gender;
    private String educationLevel;
    private String clazzName;
    private LocalDate intakeDate;
    private LocalDateTime updateTime;
}