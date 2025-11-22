package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentListDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateDTO;

import java.util.List;

public interface DepartmentService {

    List<DepartmentListDTO> findAllDepartment();

    Boolean deleteDepartmentById(Integer id);

    Boolean addDepartment(DepartmentAddDTO departmentAddDTO);

    DepartmentFindByIdDTO findDepartmentById(Integer id);

    Boolean updateDepartment(DepartmentUpdateDTO departmentUpdateDTO);
}
