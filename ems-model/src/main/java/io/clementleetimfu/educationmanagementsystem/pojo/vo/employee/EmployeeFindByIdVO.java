package io.clementleetimfu.educationmanagementsystem.pojo.vo.employee;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.workExperience.WorkExperienceFindByEmpIdVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFindByIdVO {
    private Integer id;
    private String username;
    private String name;
    private Integer gender;
    private Integer jobTitle;
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger salary;
    private String image;
    private LocalDate hireDate;
    private Integer deptId;
    private String phone;
    private List<WorkExperienceFindByEmpIdVO> workExpList;
}