package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.*;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    PageResult<EmployeeSearchResponseDTO> searchEmployee(EmployeeSearchRequestDTO employeeSearchRequestDTO);

    Boolean addEmployee(EmployeeAddRequestDTO employeeAddRequestDTO);

    Boolean deleteEmployeeByIds(List<Integer> ids);

    EmployeeFindByIdRequestDTO findEmployeeById(Integer id);

    Boolean updateEmployee(EmployeeUpdateRequestDTO employeeUpdateRequestDTO);

    EmployeeJobTitleCountRequestDTO findEmployeeJobTitleCount();

    List<Map<String, Object>> findEmployeeGenderCount();

    List<EmployeeFindClassTeachersDTO> findAllTeachers();

    Boolean isEmployeeExistsInDepartment(Integer deptId);
}