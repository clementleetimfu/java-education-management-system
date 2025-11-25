package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.mapper.WorkExperienceMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.*;
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
        Integer addEmployeeResult = employeeMapper.addEmployee(employee);

        Integer addWorkExperienceResult = null;
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
            addWorkExperienceResult = workExperienceMapper.addWorkExperienceByBatch(workExperienceList);
        } else {
            addWorkExperienceResult = 1;
        }

        return addEmployeeResult > 0 && addWorkExperienceResult > 0;
    }

    @Override
    public Boolean deleteEmployeeByIds(List<Integer> ids) {
        Integer deleteEmployeeResult = employeeMapper.deleteEmployeeByIds(ids);
        Integer deleteWorkExperienceResult = workExperienceMapper.deleteWorkExperienceByEmpIds(ids);
        return deleteEmployeeResult > 0 && deleteWorkExperienceResult > 0;
    }

    @Override
    public EmployeeFindByIdDTO findEmployeeById(Integer id) {
        return employeeMapper.findEmployeeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateEmployee(EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = modelMapper.map(employeeUpdateDTO, Employee.class);
        employee.setUpdateTime(LocalDateTime.now());
        Integer updateEmployeeResult = employeeMapper.updateEmployee(employee);
        Integer deleteWorkExperienceResult = workExperienceMapper.deleteWorkExperienceByEmpIds(Arrays.asList(employee.getId()));

        Integer updateWorkExperienceResult = null;
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
            updateWorkExperienceResult = workExperienceMapper.addWorkExperienceByBatch(workExperienceList);
        } else {
            updateWorkExperienceResult = 1;
        }
        return updateEmployeeResult > 0 && deleteWorkExperienceResult > 0 && updateWorkExperienceResult > 0;
    }
}