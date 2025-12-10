package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindAllResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {

    List<DepartmentFindAllResponseDTO> selectAllDepartment();

    Integer deleteDepartmentById(@Param("id") Integer id);

    Integer insertDepartment(Department department);

    Integer updateDepartmentName(Department department);
}