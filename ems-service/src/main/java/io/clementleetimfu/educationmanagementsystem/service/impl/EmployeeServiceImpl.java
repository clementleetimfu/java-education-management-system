package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.mapper.WorkExperienceMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindClassTeachersVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeJobTitleCountVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
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
    public PageResult<EmployeeSearchVO> searchEmployee(EmployeeSearchDTO employeeSearchDTO) {

        PageHelper.startPage(employeeSearchDTO.getPage(), employeeSearchDTO.getPageSize());

        List<EmployeeSearchVO> employeeSearchVOList = employeeMapper.searchEmployee(employeeSearchDTO);

        if (employeeSearchVOList.isEmpty()) {
            log.warn("Employee list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        Page<EmployeeSearchVO> page = (Page<EmployeeSearchVO>) employeeSearchVOList;
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
    public EmployeeFindByIdVO findEmployeeById(Integer id) {

        EmployeeFindByIdVO employeeFindByIdVO = employeeMapper.selectEmployeeById(id);
        if (employeeFindByIdVO == null) {
            log.warn("Employee with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        return employeeFindByIdVO;
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

        Long workExperienceCount = workExperienceMapper.selectWorkExperienceCountByEmpIds(Arrays.asList(employee.getId()));

        if (workExperienceCount > 0) {
            Integer deleteWorkExperienceRowsAffected = workExperienceMapper.deleteWorkExperienceByEmpIds(Arrays.asList(employee.getId()));

            if (deleteWorkExperienceRowsAffected == 0) {
                log.warn("Failed to delete work experience with employee id:{}", employee.getId());
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED);
            }
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

            Integer addWorkExperienceRowsAffected = workExperienceMapper.insertWorkExperienceByBatch(workExperienceList);

            if (addWorkExperienceRowsAffected == 0) {
                log.warn("Failed to add work experience:{}", employee);
                throw new BusinessException(ErrorCodeEnum.WORK_EXPERIENCE_ADD_FAILED);
            }
        }
        return Boolean.TRUE;
    }

    @Override
    public EmployeeJobTitleCountVO findEmployeeJobTitleCount() {
        List<Map<String, Object>> jobTitleCountMapList = employeeMapper.selectEmployeeJobTitleCount();

        if (jobTitleCountMapList.isEmpty()) {
            log.warn("Employee job title count list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        EmployeeJobTitleCountVO employeeJobTitleCountVO = new EmployeeJobTitleCountVO();
        employeeJobTitleCountVO
                .setJobTitleList(jobTitleCountMapList.stream().map(map -> (String) map.get("jobTitle")).toList());

        employeeJobTitleCountVO
                .setJobTitleCountList(jobTitleCountMapList.stream().map(map -> ((Number) map.get("count")).intValue()).toList());

        return employeeJobTitleCountVO;
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
    public List<EmployeeFindClassTeachersVO> findAllTeachers() {
        List<EmployeeFindClassTeachersVO> employeeFindClassTeachersVOList = employeeMapper.selectAllTeachers();
        if (employeeFindClassTeachersVOList.isEmpty()) {
            log.warn("Class teacher list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }
        return employeeFindClassTeachersVOList;
    }

    @Override
    public Boolean isEmployeeExistsInDepartment(Integer deptId) {
        return employeeMapper.selectEmployeeCountByDeptId(deptId) > 0;
    }
}