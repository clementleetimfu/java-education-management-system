package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeQueryRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeQueryResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;

import java.util.List;

public interface EmployeeMapper {
    List<EmployeeQueryResponseDTO> queryEmployee(EmployeeQueryRequestDTO employeeQueryRequestDTO);

    Integer addEmployee(Employee employee);
}
