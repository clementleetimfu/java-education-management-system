package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;

import java.util.List;

public interface EmployeeMapper {
    List<EmployeeSearchResponseDTO> searchEmployee(EmployeeSearchRequestDTO employeeSearchRequestDTO);

    Integer addEmployee(Employee employee);
    
    Integer deleteEmployeeByIds(List<Integer> ids);

    EmployeeFindByIdDTO findEmployeeById(Integer id);

    Integer updateEmployee(Employee employee);
}
