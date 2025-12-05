package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.mapper.WorkExperienceMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.*;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.workExperience.WorkExperienceAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.workExperience.WorkExperienceUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.WorkExperience;
import io.clementleetimfu.educationmanagementsystem.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WorkExperienceMapper workExperienceMapper;

    @Override
    public PageResult<EmployeeSearchResponseDTO> searchEmployee(EmployeeSearchRequestDTO employeeSearchRequestDTO) {

        PageHelper.startPage(employeeSearchRequestDTO.getPage(), employeeSearchRequestDTO.getPageSize());

        List<EmployeeSearchResponseDTO> employeeSearchResponseDTOList = employeeMapper.searchEmployee(employeeSearchRequestDTO);

        if (employeeSearchResponseDTOList.isEmpty()) {
            log.warn("Employee list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        Page<EmployeeSearchResponseDTO> page = (Page<EmployeeSearchResponseDTO>) employeeSearchResponseDTOList;
        return new PageResult<>(page.getTotal(), page.getResult());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addEmployee(EmployeeAddDTO employeeAddDTO) {
        Employee employee = modelMapper.map(employeeAddDTO, Employee.class);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setIsDeleted(false);
        Integer addEmployeeRowsAffected = employeeMapper.insertEmployee(employee);

        if (addEmployeeRowsAffected == 0) {
            log.warn("Failed to add employee:{}", employee);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_ADD_FAILED);
        }

        List<WorkExperienceAddDTO> workExperienceAddDTOList = employeeAddDTO.getWorkExpList();
        if (!workExperienceAddDTOList.isEmpty()) {
            List<WorkExperience> workExperienceList = workExperienceAddDTOList.stream()
                    .map(workExperienceAddDTO -> {
                        WorkExperience workExperience = modelMapper.map(workExperienceAddDTO, WorkExperience.class);
                        workExperience.setEmpId(employee.getId());
                        workExperience.setCreateTime(LocalDateTime.now());
                        workExperience.setUpdateTime(LocalDateTime.now());
                        workExperience.setIsDeleted(Boolean.FALSE);
                        return workExperience;
                    }).toList();
            Integer addWorkExperienceRowsAffected = workExperienceMapper.addWorkExperienceByBatch(workExperienceList);

            if (addWorkExperienceRowsAffected == 0) {
                log.warn("Failed to add work experience:{}", employee);
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_ADD_FAILED);
            }
        }

        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteEmployeeByIds(List<Integer> ids) {
        Integer deleteEmployeeRowsAffected = employeeMapper.deleteEmployeeByIds(ids);
        if (deleteEmployeeRowsAffected == 0) {
            log.warn("Failed to delete employee with ids:{}", ids);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_DELETE_FAILED);
        }

        Long workExperienceCount = workExperienceMapper.countWorkExperienceByEmpIds(ids);

        if (workExperienceCount > 0) {
            Integer deleteWorkExperienceRowsAffected = workExperienceMapper.deleteWorkExperienceByEmpIds(ids);
            if (deleteWorkExperienceRowsAffected == 0) {
                log.warn("Failed to delete work experience with employee ids:{}", ids);
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED);
            }
        }

        return Boolean.TRUE;
    }

    @Override
    public EmployeeFindByIdDTO findEmployeeById(Integer id) {
        EmployeeFindByIdDTO employeeFindByIdDTO = employeeMapper.selectEmployeeById(id);
        if (employeeFindByIdDTO == null) {
            log.warn("Employee with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }
        return employeeFindByIdDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateEmployee(EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = modelMapper.map(employeeUpdateDTO, Employee.class);
        employee.setUpdateTime(LocalDateTime.now());
        Integer updateEmployeeRowsAffected = employeeMapper.updateEmployee(employee);

        if (updateEmployeeRowsAffected == 0) {
            log.warn("Failed to update employee:{}", employee);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_UPDATE_FAILED);
        }

        Integer deleteWorkExperienceRowsAffected = workExperienceMapper.deleteWorkExperienceByEmpIds(Arrays.asList(employee.getId()));

        if (deleteWorkExperienceRowsAffected == 0) {
            log.warn("Failed to delete work experience with employee id:{}", employee.getId());
            throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED);
        }

        List<WorkExperienceUpdateDTO> workExperienceUpdateDTOList = employeeUpdateDTO.getWorkExpList();
        if (!workExperienceUpdateDTOList.isEmpty()) {
            List<WorkExperience> workExperienceList = workExperienceUpdateDTOList.stream().map(workExperienceUpdateDTO -> {
                WorkExperience workExperience = modelMapper.map(workExperienceUpdateDTO, WorkExperience.class);
                workExperience.setEmpId(employee.getId());
                workExperience.setCreateTime(LocalDateTime.now());
                workExperience.setUpdateTime(LocalDateTime.now());
                workExperience.setIsDeleted(Boolean.FALSE);
                return workExperience;
            }).toList();

            Integer addWorkExperienceRowsAffected = workExperienceMapper.addWorkExperienceByBatch(workExperienceList);

            if (addWorkExperienceRowsAffected == 0) {
                log.warn("Failed to add work experience:{}", employee);
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_ADD_FAILED);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public EmployeeJobTitleCountDTO findEmployeeJobTitleCount() {
        List<Map<String, Object>> jobTitleCountMapList = employeeMapper.findEmployeeJobTitleCount();

        if (jobTitleCountMapList.isEmpty()) {
            log.warn("Employee job title count list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        EmployeeJobTitleCountDTO employeeJobTitleCountDTO = new EmployeeJobTitleCountDTO();
        employeeJobTitleCountDTO
                .setJobTitleList(jobTitleCountMapList.stream().map(map -> (String) map.get("jobTitle")).toList());

        employeeJobTitleCountDTO
                .setJobTitleCountList(jobTitleCountMapList.stream().map(map -> ((Number) map.get("count")).intValue()).toList());

        return employeeJobTitleCountDTO;
    }

    @Override
    public List<Map<String, Object>> findEmployeeGenderCount() {
        List<Map<String, Object>> genderCountMapList = employeeMapper.findEmployeeGenderCount();

        if (genderCountMapList.isEmpty()) {
            log.warn("Employee gender count list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        return genderCountMapList;
    }
}