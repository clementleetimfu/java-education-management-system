package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.DepartmentMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Department;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.department.DepartmentFindAllVO;
import io.clementleetimfu.educationmanagementsystem.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Department Service Implementation Tests")
class DepartmentServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;

    @Test
    @DisplayName("Test Find All Department Returns List")
    void testFindAllDepartmentReturnsList() {
        List<DepartmentFindAllVO> departmentList = new ArrayList<>();
        DepartmentFindAllVO vo1 = new DepartmentFindAllVO();
        vo1.setId(1);
        vo1.setName("Department 1");

        DepartmentFindAllVO vo2 = new DepartmentFindAllVO();
        vo2.setId(2);
        vo2.setName("Department 2");

        departmentList.add(vo1);
        departmentList.add(vo2);

        when(departmentMapper.selectAllDepartment()).thenReturn(departmentList);

        List<DepartmentFindAllVO> result = departmentServiceImpl.findAllDepartment();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(departmentMapper, times(1)).selectAllDepartment();
    }

    @Test
    @DisplayName("Test Find All Department Empty Throws Exception")
    void testFindAllDepartmentEmptyThrowsException() {
        when(departmentMapper.selectAllDepartment()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> departmentServiceImpl.findAllDepartment());
        assertEquals(ErrorCodeEnum.DEPARTMENT_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.DEPARTMENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Delete Department By Id Success")
    void testDeleteDepartmentByIdSuccess() {
        Integer id = 1;
        when(employeeService.isEmployeeExistsInDepartment(id)).thenReturn(false);
        when(departmentMapper.deleteDepartmentById(id)).thenReturn(1);

        Boolean result = departmentServiceImpl.deleteDepartmentById(id);

        assertTrue(result);
        verify(departmentMapper, times(1)).deleteDepartmentById(id);
    }

    @Test
    @DisplayName("Test Delete Department By Id Not Allowed Throws Exception")
    void testDeleteDepartmentByIdNotAllowedThrowsException() {
        Integer id = 1;
        when(employeeService.isEmployeeExistsInDepartment(id)).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> departmentServiceImpl.deleteDepartmentById(id));
        assertEquals(ErrorCodeEnum.DEPARTMENT_DELETE_NOT_ALLOWED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.DEPARTMENT_DELETE_NOT_ALLOWED.getMessage(), exception.getMessage());

        verify(departmentMapper, never()).deleteDepartmentById(anyInt());
    }

    @Test
    @DisplayName("Test Delete Department By Id Failed Throws Exception")
    void testDeleteDepartmentByIdFailedThrowsException() {
        Integer id = 1;
        when(employeeService.isEmployeeExistsInDepartment(id)).thenReturn(false);
        when(departmentMapper.deleteDepartmentById(id)).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> departmentServiceImpl.deleteDepartmentById(id));
        assertEquals(ErrorCodeEnum.DEPARTMENT_DELETE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.DEPARTMENT_DELETE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Add Department Success")
    void testAddDepartmentSuccess() {
        DepartmentAddDTO departmentAddDTO = new DepartmentAddDTO();
        departmentAddDTO.setName("Test Department");

        Department mappedDepartment = new Department();

        when(modelMapper.map(any(DepartmentAddDTO.class), eq(Department.class))).thenReturn(mappedDepartment);
        when(departmentMapper.insertDepartment(any(Department.class))).thenReturn(1);

        Boolean result = departmentServiceImpl.addDepartment(departmentAddDTO);

        assertTrue(result);
        verify(departmentMapper, times(1)).insertDepartment(any(Department.class));
    }

    @Test
    @DisplayName("Test Add Department Failed Throws Exception")
    void testAddDepartmentFailedThrowsException() {
        DepartmentAddDTO departmentAddDTO = new DepartmentAddDTO();
        departmentAddDTO.setName("Test Department");

        Department mappedDepartment = new Department();

        when(modelMapper.map(any(DepartmentAddDTO.class), eq(Department.class))).thenReturn(mappedDepartment);
        when(departmentMapper.insertDepartment(any(Department.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> departmentServiceImpl.addDepartment(departmentAddDTO));
        assertEquals(ErrorCodeEnum.DEPARTMENT_ADD_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.DEPARTMENT_ADD_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Update Department Name Success")
    void testUpdateDepartmentNameSuccess() {
        DepartmentUpdateDTO departmentUpdateDTO = new DepartmentUpdateDTO();
        departmentUpdateDTO.setId(1);
        departmentUpdateDTO.setName("Updated Department");

        Department mappedDepartment = new Department();

        when(modelMapper.map(any(DepartmentUpdateDTO.class), eq(Department.class))).thenReturn(mappedDepartment);
        when(departmentMapper.updateDepartmentName(any(Department.class))).thenReturn(1);

        Boolean result = departmentServiceImpl.updateDepartmentName(departmentUpdateDTO);

        assertTrue(result);
        verify(departmentMapper, times(1)).updateDepartmentName(any(Department.class));
    }

    @Test
    @DisplayName("Test Update Department Name Failed Throws Exception")
    void testUpdateDepartmentNameFailedThrowsException() {
        DepartmentUpdateDTO departmentUpdateDTO = new DepartmentUpdateDTO();
        departmentUpdateDTO.setId(1);
        departmentUpdateDTO.setName("Updated Department");

        Department mappedDepartment = new Department();

        when(modelMapper.map(any(DepartmentUpdateDTO.class), eq(Department.class))).thenReturn(mappedDepartment);
        when(departmentMapper.updateDepartmentName(any(Department.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> departmentServiceImpl.updateDepartmentName(departmentUpdateDTO));
        assertEquals(ErrorCodeEnum.DEPARTMENT_UPDATE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.DEPARTMENT_UPDATE_FAILED.getMessage(), exception.getMessage());
    }

}
