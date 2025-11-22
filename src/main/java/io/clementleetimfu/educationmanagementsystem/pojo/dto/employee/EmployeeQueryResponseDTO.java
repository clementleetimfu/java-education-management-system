package io.clementleetimfu.educationmanagementsystem.pojo.dto.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeQueryResponseDTO {
    private Integer id;
    private String username;
    private String name;
    private Integer gender;
    private String image;
    private Integer jobTitle;
    private Integer salary;
    private LocalDate hireDate;
    private Integer deptId;
    private String deptName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

