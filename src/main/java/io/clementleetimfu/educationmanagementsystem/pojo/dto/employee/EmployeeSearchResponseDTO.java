package io.clementleetimfu.educationmanagementsystem.pojo.dto.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchResponseDTO {
    private Integer id;
    private String name;
    private Integer gender;
    private String image;
    private String deptName;
    private Integer jobTitle;
    private LocalDate hireDate;
    private LocalDateTime updateTime;
}

