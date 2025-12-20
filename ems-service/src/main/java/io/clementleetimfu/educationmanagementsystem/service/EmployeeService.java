package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindClassTeachersVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeJobTitleCountVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.*;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    PageResult<EmployeeSearchVO> searchEmployee(EmployeeSearchDTO employeeSearchDTO);

    Boolean addEmployee(EmployeeAddDTO employeeAddDTO);

    Boolean deleteEmployeeByIds(List<Integer> ids);

    EmployeeFindByIdVO findEmployeeById(Integer id);

    Boolean updateEmployee(EmployeeUpdateDTO employeeUpdateDTO);

    EmployeeJobTitleCountVO findEmployeeJobTitleCount();

    List<Map<String, Object>> findEmployeeGenderCount();

    List<EmployeeFindClassTeachersVO> findAllTeachers();

    Boolean isEmployeeExistsInDepartment(Integer deptId);
}