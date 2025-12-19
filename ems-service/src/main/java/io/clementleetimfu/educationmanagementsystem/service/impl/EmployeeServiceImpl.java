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
    public Boolean addEmployee(EmployeeAddRequestDTO employeeAddRequestDTO) {

        Employee employee = modelMapper.map(employeeAddRequestDTO, Employee.class);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setIsDeleted(false);
        Integer addEmployeeRowsAffected = employeeMapper.insertEmployee(employee);

        if (addEmployeeRowsAffected == 0) {
            log.warn("Failed to add employee:{}", employee);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_ADD_FAILED);
        }

        List<WorkExperienceAddDTO> workExperienceAddDTOList = employeeAddRequestDTO.getWorkExpList();
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
            Integer addWorkExperienceRowsAffected = workExperienceMapper.insertWorkExperienceByBatch(workExperienceList);

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

        Long workExperienceCount = workExperienceMapper.selectWorkExperienceCountByEmpIds(ids);

        if (workExperienceCount > 0) {
            Integer deleteWorkExperienceRowsAffected = workExperienceMapper.deleteWorkExperienceByEmpIds(ids);
            if (deleteWorkExperienceRowsAffected == 0) {
                log.warn("Failed to delete work experience with employee ids:{}", ids);
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED);
            }
        }

        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeFindByIdRequestDTO findEmployeeById(Integer id) {

        EmployeeFindByIdRequestDTO employeeFindByIdRequestDTO = employeeMapper.selectEmployeeById(id);
        if (employeeFindByIdRequestDTO == null) {
            log.warn("Employee with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        return employeeFindByIdRequestDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateEmployee(EmployeeUpdateRequestDTO employeeUpdateRequestDTO) {

        Employee employee = modelMapper.map(employeeUpdateRequestDTO, Employee.class);
        employee.setUpdateTime(LocalDateTime.now());
        Integer updateEmployeeRowsAffected = employeeMapper.updateEmployee(employee);

        if (updateEmployeeRowsAffected == 0) {
            log.warn("Failed to update employee:{}", employee);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_UPDATE_FAILED);
        }

        Long workExperienceCount = workExperienceMapper.selectWorkExperienceCountByEmpIds(Arrays.asList(employee.getId()));

        if (workExperienceCount > 0) {
            Integer deleteWorkExperienceRowsAffected = workExperienceMapper.deleteWorkExperienceByEmpIds(Arrays.asList(employee.getId()));

            if (deleteWorkExperienceRowsAffected == 0) {
                log.warn("Failed to delete work experience with employee id:{}", employee.getId());
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED);
            }
        }

        List<WorkExperienceUpdateDTO> workExperienceUpdateDTOList = employeeUpdateRequestDTO.getWorkExpList();
        if (!workExperienceUpdateDTOList.isEmpty()) {
            List<WorkExperience> workExperienceList = workExperienceUpdateDTOList.stream().map(workExperienceUpdateDTO -> {
                WorkExperience workExperience = modelMapper.map(workExperienceUpdateDTO, WorkExperience.class);
                workExperience.setEmpId(employee.getId());
                workExperience.setCreateTime(LocalDateTime.now());
                workExperience.setUpdateTime(LocalDateTime.now());
                workExperience.setIsDeleted(Boolean.FALSE);
                return workExperience;
            }).toList();

            Integer addWorkExperienceRowsAffected = workExperienceMapper.insertWorkExperienceByBatch(workExperienceList);

            if (addWorkExperienceRowsAffected == 0) {
                log.warn("Failed to add work experience:{}", employee);
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_ADD_FAILED);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public EmployeeJobTitleCountRequestDTO findEmployeeJobTitleCount() {
        List<Map<String, Object>> jobTitleCountMapList = employeeMapper.selectEmployeeJobTitleCount();

        if (jobTitleCountMapList.isEmpty()) {
            log.warn("Employee job title count list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        EmployeeJobTitleCountRequestDTO employeeJobTitleCountRequestDTO = new EmployeeJobTitleCountRequestDTO();
        employeeJobTitleCountRequestDTO
                .setJobTitleList(jobTitleCountMapList.stream().map(map -> (String) map.get("jobTitle")).toList());

        employeeJobTitleCountRequestDTO
                .setJobTitleCountList(jobTitleCountMapList.stream().map(map -> ((Number) map.get("count")).intValue()).toList());

        return employeeJobTitleCountRequestDTO;
    }

    @Override
    public List<Map<String, Object>> findEmployeeGenderCount() {
        List<Map<String, Object>> genderCountMapList = employeeMapper.selectEmployeeGenderCount();

        if (genderCountMapList.isEmpty()) {
            log.warn("Employee gender count list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        return genderCountMapList;
    }

    @Override
    public List<EmployeeFindClassTeachersDTO> findAllTeachers() {
        List<EmployeeFindClassTeachersDTO> employeeFindClassTeachersDTOList = employeeMapper.selectAllTeachers();
        if (employeeFindClassTeachersDTOList.isEmpty()) {
            log.warn("Class teacher list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }
        return employeeFindClassTeachersDTOList;
    }

    @Override
    public Boolean isEmployeeExistsInDepartment(Integer deptId) {
        return employeeMapper.selectEmployeeCountByDeptId(deptId) > 0;
    }
}