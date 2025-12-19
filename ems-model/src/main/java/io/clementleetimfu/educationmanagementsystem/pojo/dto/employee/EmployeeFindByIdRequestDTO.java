package io.clementleetimfu.educationmanagementsystem.pojo.dto.employee;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.workExperience.WorkExperienceFindByEmpIdDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFindByIdRequestDTO {
    private Integer id;
    private String username;
    private String name;
    private Integer gender;
    private Integer jobTitle;
    private Integer salary;
    private String image;
    private LocalDate hireDate;
    private Integer deptId;
    private String phone;
    private List<WorkExperienceFindByEmpIdDTO> workExpList;
}