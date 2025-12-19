package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeFindByIdRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeFindClassTeachersDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    List<EmployeeSearchResponseDTO> searchEmployee(EmployeeSearchRequestDTO employeeSearchRequestDTO);

    Integer insertEmployee(Employee employee);

    Integer deleteEmployeeByIds(List<Integer> ids);

    EmployeeFindByIdRequestDTO selectEmployeeById(@Param("id") Integer id);

    Integer updateEmployee(Employee employee);

    LoginResponseDTO selectEmployeeByUsername(@Param("username") String username);

    @MapKey("jobTitle")
    List<Map<String, Object>> selectEmployeeJobTitleCount();

    @MapKey("gender")
    List<Map<String, Object>> selectEmployeeGenderCount();

    List<EmployeeFindClassTeachersDTO> selectAllTeachers();

    Long selectEmployeeCountByDeptId(@Param("deptId") Integer deptId);
}