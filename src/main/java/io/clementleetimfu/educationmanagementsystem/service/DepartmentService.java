package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindAllResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateRequestDTO;

import java.util.List;

public interface DepartmentService {

    List<DepartmentFindAllResponseDTO> findAllDepartment();

    Boolean deleteDepartmentById(Integer id);

    Boolean addDepartment(DepartmentAddRequestDTO departmentAddRequestDTO);

    Boolean updateDepartmentName(DepartmentUpdateRequestDTO departmentUpdateRequestDTO);
}
