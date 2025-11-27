package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.DepartmentMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Department;
import io.clementleetimfu.educationmanagementsystem.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentFindAllDTO> findAllDepartment() {
        List<DepartmentFindAllDTO> departmentListDTOFindAll = departmentMapper.selectAllDepartment();
        if (departmentListDTOFindAll.isEmpty()) {
            log.warn("Department list is empty");
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_NOT_FOUND);
        }
        return departmentListDTOFindAll;
    }

    @Override
    public Boolean deleteDepartmentById(Integer id) {
        Integer rowsAffected = departmentMapper.deleteDepartmentById(id);
        if (rowsAffected == 0) {
            log.warn("Failed to delete department with id:{}", id);
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_DELETE_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean addDepartment(DepartmentAddDTO departmentAddDTO) {
        Department department = modelMapper.map(departmentAddDTO, Department.class);
        department.setCreateTime(LocalDateTime.now());
        department.setUpdateTime(LocalDateTime.now());
        department.setIsDeleted(Boolean.FALSE);
        Integer rowsAffected = departmentMapper.insertDepartment(department);
        if (rowsAffected == 0) {
            log.warn("Failed to add department:{}", department);
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_ADD_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public DepartmentFindByIdDTO findDepartmentById(Integer id) {
        DepartmentFindByIdDTO departmentFindByIdDTO = departmentMapper.selectDepartmentById(id);
        if (departmentFindByIdDTO == null) {
            log.warn("Department with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_NOT_FOUND);
        }
        return departmentFindByIdDTO;
    }

    @Override
    public Boolean updateDepartmentName(DepartmentUpdateDTO departmentUpdateDTO) {
        Department department = modelMapper.map(departmentUpdateDTO, Department.class);
        department.setUpdateTime(LocalDateTime.now());
        Integer rowsAffected = departmentMapper.updateDepartmentName(department);
        if (rowsAffected == 0) {
            log.warn("Failed to update department:{}", department);
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_UPDATE_FAILED);
        }
        return Boolean.TRUE;
    }

}
