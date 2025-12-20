package io.clementleetimfu.educationmanagementsystem.pojo.vo.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSearchVO {
    private Integer id;
    private String name;
    private String gender;
    private String image;
    private String deptName;
    private String jobTitle;
    private LocalDate hireDate;
    private LocalDateTime updateTime;
}

