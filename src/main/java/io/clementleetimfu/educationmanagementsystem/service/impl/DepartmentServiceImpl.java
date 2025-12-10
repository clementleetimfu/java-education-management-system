package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.DepartmentMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindAllResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Department;
import io.clementleetimfu.educationmanagementsystem.service.DepartmentService;
import io.clementleetimfu.educationmanagementsystem.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<DepartmentFindAllResponseDTO> findAllDepartment() {

        List<DepartmentFindAllResponseDTO> departmentFindAllResponseDTOList = departmentMapper.selectAllDepartment();
        if (departmentFindAllResponseDTOList.isEmpty()) {
            log.warn("Department list is empty");
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_NOT_FOUND);
        }

        return departmentFindAllResponseDTOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteDepartmentById(Integer id) {

        if (employeeService.isEmployeeExistsInDepartment(id)) {
            log.warn("Department with id:{} still has employees", id);
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_DELETE_NOT_ALLOWED);
        }

        Integer rowsAffected = departmentMapper.deleteDepartmentById(id);
        if (rowsAffected == 0) {
            log.warn("Failed to delete department with id:{}", id);
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_DELETE_FAILED);
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean addDepartment(DepartmentAddRequestDTO departmentAddRequestDTO) {

        Department department = modelMapper.map(departmentAddRequestDTO, Department.class);
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
    public Boolean updateDepartmentName(DepartmentUpdateRequestDTO departmentUpdateRequestDTO) {

        Department department = modelMapper.map(departmentUpdateRequestDTO, Department.class);
        department.setUpdateTime(LocalDateTime.now());

        Integer rowsAffected = departmentMapper.updateDepartmentName(department);
        if (rowsAffected == 0) {
            log.warn("Failed to update department:{}", department);
            throw new BusinessException(ErrorCodeEnum.DEPARTMENT_UPDATE_FAILED);
        }

        return Boolean.TRUE;
    }

}
