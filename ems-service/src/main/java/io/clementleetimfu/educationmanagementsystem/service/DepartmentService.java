package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.department.DepartmentFindAllVO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateDTO;

import java.util.List;

public interface DepartmentService {

    List<DepartmentFindAllVO> findAllDepartment();

    Boolean deleteDepartmentById(Integer id);

    Boolean addDepartment(DepartmentAddDTO departmentAddDTO);

    Boolean updateDepartmentName(DepartmentUpdateDTO departmentUpdateDTO);
}
