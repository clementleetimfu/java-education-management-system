package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentListDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentMapper {

    List<DepartmentListDTO> findAllDepartment();

    Integer deleteDepartmentById(@Param("id") Integer id);

    Integer addDepartment(Department department);

    DepartmentFindByIdDTO findDepartmentById(@Param("id") Integer id);

    Integer updateDepartment(Department department);
}
