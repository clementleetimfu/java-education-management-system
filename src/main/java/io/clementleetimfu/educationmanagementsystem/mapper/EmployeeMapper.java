package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.LoginResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    List<EmployeeSearchResponseDTO> searchEmployee(EmployeeSearchRequestDTO employeeSearchRequestDTO);

    Integer insertEmployee(Employee employee);
    
    Integer deleteEmployeeByIds(List<Integer> ids);

    EmployeeFindByIdDTO selectEmployeeById(@Param("id") Integer id);

    Integer updateEmployee(Employee employee);

    LoginResponseDTO selectEmployeeByUsernameAndPassWord(@Param("username") String username, @Param("password") String password);
}