package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.auth.LoginVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindClassTeachersVO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    List<EmployeeSearchVO> searchEmployee(EmployeeSearchDTO employeeSearchDTO);

    Integer insertEmployee(Employee employee);

    Integer deleteEmployeeByIds(List<Integer> ids);

    EmployeeFindByIdVO selectEmployeeById(@Param("id") Integer id);

    Integer updateEmployee(Employee employee);

    LoginVO selectEmployeeByUsername(@Param("username") String username);

    @MapKey("jobTitle")
    List<Map<String, Object>> selectEmployeeJobTitleCount();

    @MapKey("gender")
    List<Map<String, Object>> selectEmployeeGenderCount();

    List<EmployeeFindClassTeachersVO> selectAllTeachers();

    Long selectEmployeeCountByDeptId(@Param("deptId") Integer deptId);
}