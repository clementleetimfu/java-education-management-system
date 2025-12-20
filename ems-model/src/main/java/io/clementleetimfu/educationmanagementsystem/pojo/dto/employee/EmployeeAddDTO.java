package io.clementleetimfu.educationmanagementsystem.pojo.dto.employee;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.workExperience.WorkExperienceAddDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAddDTO {
    private String image;
    private String username;
    private String name;
    private Integer gender;
    private Integer jobTitle;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hireDate;
    private Integer deptId;
    private String phone;
    private BigInteger salary;
    private List<WorkExperienceAddDTO> workExpList;
}
