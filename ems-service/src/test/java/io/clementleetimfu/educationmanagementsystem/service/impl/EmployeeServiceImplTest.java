package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.constants.RoleEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.mapper.RoleMapper;
import io.clementleetimfu.educationmanagementsystem.mapper.WorkExperienceMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeSearchDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.workExperience.WorkExperienceAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.workExperience.WorkExperienceUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Role;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.WorkExperience;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindClassTeachersVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeJobTitleCountVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Implementation Tests")
class EmployeeServiceImplTest {

    @Mock
    private EmployeeMapper employeeMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private WorkExperienceMapper workExperienceMapper;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeServiceImpl;

    private MockedStatic<PageHelper> mockedPageHelper;

    @BeforeEach
    void setUp() {
        mockedPageHelper = mockStatic(PageHelper.class);
    }

    @AfterEach
    void tearDown() {
        if (mockedPageHelper != null) {
            mockedPageHelper.close();
        }
    }

    @Test
    @DisplayName("Test Search Employee With Pagination")
    void testSearchEmployeeWithPagination() {
        EmployeeSearchDTO searchDTO = new EmployeeSearchDTO();

        List<EmployeeSearchVO> employeeList = createEmployeeSearchVOList();
        Page<EmployeeSearchVO> page = new Page<>(1, 10);
        page.addAll(employeeList);
        page.setTotal(10);

        when(employeeMapper.searchEmployee(any(EmployeeSearchDTO.class))).thenReturn(page);

        PageResult<EmployeeSearchVO> result = employeeServiceImpl.searchEmployee(searchDTO);

        assertNotNull(result);
        assertEquals(10, result.getTotal());
        assertEquals(10, result.getRows().size());

        verify(employeeMapper, times(1)).searchEmployee(any(EmployeeSearchDTO.class));
    }

    @Test
    @DisplayName("Test Search Employee Empty Result Throws Exception")
    void testSearchEmployeeEmptyResultThrowsException() {
        EmployeeSearchDTO searchDTO = new EmployeeSearchDTO();

        when(employeeMapper.searchEmployee(any(EmployeeSearchDTO.class))).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.searchEmployee(searchDTO));
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Add Employee Success")
    void testAddEmployeeSuccess() {
        EmployeeAddDTO employeeAddDTO = new EmployeeAddDTO();
        employeeAddDTO.setUsername("john.doe");
        employeeAddDTO.setWorkExpList(Collections.emptyList());

        Role role = new Role();
        role.setId(1);

        Employee mappedEmployee = new Employee();

        when(roleMapper.selectRoleByName(RoleEnum.ROLE_EMPLOYEE.getValue())).thenReturn(role);
        when(modelMapper.map(any(EmployeeAddDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(employeeMapper.insertEmployee(any(Employee.class))).thenReturn(1);

        Boolean result = employeeServiceImpl.addEmployee(employeeAddDTO);

        assertTrue(result);
        verify(employeeMapper, times(1)).insertEmployee(any(Employee.class));
    }

    @Test
    @DisplayName("Test Add Employee Failed Throws Exception")
    void testAddEmployeeFailedThrowsException() {
        EmployeeAddDTO employeeAddDTO = new EmployeeAddDTO();
        employeeAddDTO.setUsername("john.doe");
        employeeAddDTO.setWorkExpList(Collections.emptyList());

        Role role = new Role();
        role.setId(1);

        Employee mappedEmployee = new Employee();

        when(roleMapper.selectRoleByName(RoleEnum.ROLE_EMPLOYEE.getValue())).thenReturn(role);
        when(modelMapper.map(any(EmployeeAddDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(employeeMapper.insertEmployee(any(Employee.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.addEmployee(employeeAddDTO));
        assertEquals(ErrorCodeEnum.EMPLOYEE_ADD_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_ADD_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Add Employee Success Add Work Experience Failed")
    void testAddEmployeeSuccessAddWorkExperienceFailed() {
        EmployeeAddDTO employeeAddDTO = new EmployeeAddDTO();
        employeeAddDTO.setUsername("john.doe");

        List<WorkExperienceAddDTO> workExperienceAddDTOList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            WorkExperienceAddDTO workExperienceAddDTO = new WorkExperienceAddDTO();
            workExperienceAddDTO.setCompanyName("test company " + i);
            workExperienceAddDTOList.add(workExperienceAddDTO);
        }
        employeeAddDTO.setWorkExpList(workExperienceAddDTOList);

        Role role = new Role();
        role.setId(1);

        Employee mappedEmployee = new Employee();
        mappedEmployee.setId(1);

        WorkExperience mappedWorkExperience = new WorkExperience();

        when(roleMapper.selectRoleByName(RoleEnum.ROLE_EMPLOYEE.getValue())).thenReturn(role);
        when(modelMapper.map(any(EmployeeAddDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(employeeMapper.insertEmployee(any(Employee.class))).thenReturn(1);
        when(workExperienceMapper.insertWorkExperienceByBatch(anyList())).thenReturn(0);
        when(modelMapper.map(any(WorkExperienceAddDTO.class), eq(WorkExperience.class))).thenReturn(mappedWorkExperience);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.addEmployee(employeeAddDTO));
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_ADD_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_ADD_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Add Employee Success Add Work Experience Success")
    void testAddEmployeeSuccessAddWorkExperienceSuccess() {
        EmployeeAddDTO employeeAddDTO = new EmployeeAddDTO();
        employeeAddDTO.setUsername("john.doe");

        List<WorkExperienceAddDTO> workExperienceAddDTOList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            WorkExperienceAddDTO workExperienceAddDTO = new WorkExperienceAddDTO();
            workExperienceAddDTO.setCompanyName("test company " + i);
            workExperienceAddDTOList.add(workExperienceAddDTO);
        }
        employeeAddDTO.setWorkExpList(workExperienceAddDTOList);

        Role role = new Role();
        role.setId(1);

        Employee mappedEmployee = new Employee();
        mappedEmployee.setId(1);

        WorkExperience mappedWorkExperience = new WorkExperience();

        when(roleMapper.selectRoleByName(RoleEnum.ROLE_EMPLOYEE.getValue())).thenReturn(role);
        when(modelMapper.map(any(EmployeeAddDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(employeeMapper.insertEmployee(any(Employee.class))).thenReturn(1);
        when(workExperienceMapper.insertWorkExperienceByBatch(anyList())).thenReturn(1);
        when(modelMapper.map(any(WorkExperienceAddDTO.class), eq(WorkExperience.class))).thenReturn(mappedWorkExperience);

        Boolean result = employeeServiceImpl.addEmployee(employeeAddDTO);

        assertTrue(result);
        verify(employeeMapper, times(1)).insertEmployee(any(Employee.class));
        verify(workExperienceMapper, times(1)).insertWorkExperienceByBatch(anyList());
    }

    @Test
    @DisplayName("Test Delete Employee By Ids Multiple Ids No Work Experience")
    void testDeleteEmployeeByIdsMultipleIdsNoWorkExperience() {

        List<Integer> ids = List.of(1, 2, 3);
        when(employeeMapper.deleteEmployeeByIds(ids)).thenReturn(3);
        when(workExperienceMapper.selectWorkExperienceCountByEmpIds(ids)).thenReturn(0L);

        Boolean result = employeeServiceImpl.deleteEmployeeByIds(ids);

        assertTrue(result);
        verify(employeeMapper, times(1)).deleteEmployeeByIds(ids);
    }

    @Test
    @DisplayName("Test Delete Employee By Ids Multiple Ids With Work Experience")
    void testDeleteEmployeeByIdsMultipleIdsWithWorkExperience() {

        List<Integer> ids = List.of(1, 2, 3);
        when(employeeMapper.deleteEmployeeByIds(ids)).thenReturn(3);
        when(workExperienceMapper.selectWorkExperienceCountByEmpIds(ids)).thenReturn(3L);
        when(workExperienceMapper.deleteWorkExperienceByEmpIds(ids)).thenReturn(3);

        Boolean result = employeeServiceImpl.deleteEmployeeByIds(ids);

        assertTrue(result);
        verify(employeeMapper, times(1)).deleteEmployeeByIds(ids);
        verify(workExperienceMapper, times(1)).selectWorkExperienceCountByEmpIds(ids);
        verify(workExperienceMapper, times(1)).deleteWorkExperienceByEmpIds(ids);
    }

    @Test
    @DisplayName("Test Delete Employee By Ids Failed Throws Exception")
    void testDeleteEmployeeByIdsFailedThrowsException() {

        List<Integer> ids = List.of(1);
        when(employeeMapper.deleteEmployeeByIds(ids)).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.deleteEmployeeByIds(ids));
        assertEquals(ErrorCodeEnum.EMPLOYEE_DELETE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_DELETE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Delete Employee By Ids Multiple Ids Delete Work Experience Failed")
    void testDeleteEmployeeByIdsMultipleIdsDeleteWorkExperienceFailed() {

        List<Integer> ids = List.of(1, 2, 3);
        when(employeeMapper.deleteEmployeeByIds(ids)).thenReturn(3);
        when(workExperienceMapper.selectWorkExperienceCountByEmpIds(ids)).thenReturn(3L);
        when(workExperienceMapper.deleteWorkExperienceByEmpIds(ids)).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.deleteEmployeeByIds(ids));
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED.getMessage(), exception.getMessage());

        verify(employeeMapper, times(1)).deleteEmployeeByIds(ids);
        verify(workExperienceMapper, times(1)).selectWorkExperienceCountByEmpIds(ids);
        verify(workExperienceMapper, times(1)).deleteWorkExperienceByEmpIds(ids);
    }

    @Test
    @DisplayName("Test Find Employee By Id Found")
    void testFindEmployeeByIdFound() {

        Integer id = 1;
        EmployeeFindByIdVO employee = new EmployeeFindByIdVO();
        employee.setId(id);
        employee.setName("John Doe");

        when(employeeMapper.selectEmployeeById(id)).thenReturn(employee);

        EmployeeFindByIdVO result = employeeServiceImpl.findEmployeeById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("John Doe", result.getName());

        verify(employeeMapper, times(1)).selectEmployeeById(id);
    }

    @Test
    @DisplayName("Test Find Employee By Id Not Found Throws Exception")
    void testFindEmployeeByIdNotFoundThrowsException() {

        Integer id = 99999;
        when(employeeMapper.selectEmployeeById(id)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.findEmployeeById(id));
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Update Employee Success ")
    void testUpdateEmployeeSuccessNoWorkExperience() {

        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setId(1);
        employeeUpdateDTO.setName("Updated Name");

        List<WorkExperienceUpdateDTO> workExperienceUpdateDTOList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            WorkExperienceUpdateDTO workExperienceUpdateDTO = new WorkExperienceUpdateDTO();
            workExperienceUpdateDTO.setCompanyName("test company " + i);
            workExperienceUpdateDTOList.add(workExperienceUpdateDTO);
        }
        employeeUpdateDTO.setWorkExpList(workExperienceUpdateDTOList);

        Employee mappedEmployee = new Employee();
        mappedEmployee.setId(1);

        WorkExperience mappedWorkExperience = new WorkExperience();

        when(modelMapper.map(any(EmployeeUpdateDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(modelMapper.map(any(WorkExperienceUpdateDTO.class), eq(WorkExperience.class))).thenReturn(mappedWorkExperience);
        when(employeeMapper.updateEmployee(any(Employee.class))).thenReturn(1);
        when(workExperienceMapper.selectWorkExperienceCountByEmpIds(anyList())).thenReturn(1L);
        when(workExperienceMapper.deleteWorkExperienceByEmpIds(anyList())).thenReturn(1);
        when(workExperienceMapper.insertWorkExperienceByBatch(anyList())).thenReturn(1);

        Boolean result = employeeServiceImpl.updateEmployee(employeeUpdateDTO);

        assertTrue(result);
        verify(employeeMapper, times(1)).updateEmployee(any(Employee.class));
        verify(workExperienceMapper, times(1)).selectWorkExperienceCountByEmpIds(anyList());
        verify(workExperienceMapper, times(1)).deleteWorkExperienceByEmpIds(anyList());
        verify(workExperienceMapper, times(1)).insertWorkExperienceByBatch(anyList());
    }

    @Test
    @DisplayName("Test Update Employee Failed Throws Exception")
    void testUpdateEmployeeFailedThrowsException() {

        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setId(1);
        employeeUpdateDTO.setWorkExpList(Collections.emptyList());

        Employee mappedEmployee = new Employee();

        when(modelMapper.map(any(EmployeeUpdateDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(employeeMapper.updateEmployee(any(Employee.class))).thenReturn(0);


        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.updateEmployee(employeeUpdateDTO));
        assertEquals(ErrorCodeEnum.EMPLOYEE_UPDATE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_UPDATE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Update Employee Failed Delete Work Experience Throws Exception")
    void testUpdateEmployeeFailedDeleteWorkExperienceThrowsException() {
        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setId(1);

        Employee mappedEmployee = new Employee();

        when(modelMapper.map(any(EmployeeUpdateDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(employeeMapper.updateEmployee(any(Employee.class))).thenReturn(1);
        when(workExperienceMapper.selectWorkExperienceCountByEmpIds(anyList())).thenReturn(1L);
        when(workExperienceMapper.deleteWorkExperienceByEmpIds(anyList())).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.updateEmployee(employeeUpdateDTO));
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED.getMessage(), exception.getMessage());

        verify(employeeMapper, times(1)).updateEmployee(any(Employee.class));
        verify(workExperienceMapper, times(1)).selectWorkExperienceCountByEmpIds(anyList());
        verify(workExperienceMapper, times(1)).deleteWorkExperienceByEmpIds(anyList());
    }

    @Test
    @DisplayName("Test Update Employee Failed Insert Work Experience Throws Exception")
    void testUpdateEmployeeFailedInsertWorkExperienceThrowsException() {
        EmployeeUpdateDTO employeeUpdateDTO = new EmployeeUpdateDTO();
        employeeUpdateDTO.setId(1);

        Employee mappedEmployee = new Employee();

        when(modelMapper.map(any(EmployeeUpdateDTO.class), eq(Employee.class))).thenReturn(mappedEmployee);
        when(employeeMapper.updateEmployee(any(Employee.class))).thenReturn(1);
        when(workExperienceMapper.selectWorkExperienceCountByEmpIds(anyList())).thenReturn(1L);
        when(workExperienceMapper.deleteWorkExperienceByEmpIds(anyList())).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.updateEmployee(employeeUpdateDTO));
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.WORK_EXPERIENCE_DELETE_FAILED.getMessage(), exception.getMessage());

        verify(employeeMapper, times(1)).updateEmployee(any(Employee.class));
        verify(workExperienceMapper, times(1)).selectWorkExperienceCountByEmpIds(anyList());
        verify(workExperienceMapper, times(1)).deleteWorkExperienceByEmpIds(anyList());
    }

    @Test
    @DisplayName("Test Find Employee Job Title Count Returns Statistics")
    void testFindEmployeeJobTitleCountReturnsStatistics() {

        List<Map<String, Object>> jobTitleCountMapList = createJobTitleCountMapList();
        when(employeeMapper.selectEmployeeJobTitleCount()).thenReturn(jobTitleCountMapList);

        EmployeeJobTitleCountVO result = employeeServiceImpl.findEmployeeJobTitleCount();

        assertNotNull(result);
        assertFalse(result.getJobTitleList().isEmpty());
        assertFalse(result.getJobTitleCountList().isEmpty());

        verify(employeeMapper, times(1)).selectEmployeeJobTitleCount();
    }

    @Test
    @DisplayName("Test Find Employee Job Title Count Empty Throws Exception")
    void testFindEmployeeJobTitleCountEmptyThrowsException() {

        when(employeeMapper.selectEmployeeJobTitleCount()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.findEmployeeJobTitleCount());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Find Employee Gender Count Returns Statistics")
    void testFindEmployeeGenderCountReturnsStatistics() {

        List<Map<String, Object>> genderCountMapList = createGenderCountMapList();
        when(employeeMapper.selectEmployeeGenderCount()).thenReturn(genderCountMapList);

        List<Map<String, Object>> result = employeeServiceImpl.findEmployeeGenderCount();

        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(employeeMapper, times(1)).selectEmployeeGenderCount();
    }

    @Test
    @DisplayName("Test Find Employee Gender Count Empty Throws Exception")
    void testFindEmployeeGenderCountEmptyThrowsException() {

        when(employeeMapper.selectEmployeeGenderCount()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.findEmployeeGenderCount());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Find All Teachers Returns Teachers Only")
    void testFindAllTeachersReturnsTeachersOnly() {

        List<EmployeeFindClassTeachersVO> teachers = createTeacherList();
        when(employeeMapper.selectAllTeachers()).thenReturn(teachers);

        List<EmployeeFindClassTeachersVO> result = employeeServiceImpl.findAllTeachers();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(employeeMapper, times(1)).selectAllTeachers();
    }

    @Test
    @DisplayName("Test Find All Teachers Empty Throws Exception")
    void testFindAllTeachersEmptyThrowsException() {

        when(employeeMapper.selectAllTeachers()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeServiceImpl.findAllTeachers());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Is Employee Exists In Department True")
    void testIsEmployeeExistsInDepartmentTrue() {

        Integer deptId = 1;
        when(employeeMapper.selectEmployeeCountByDeptId(deptId)).thenReturn(5L);

        Boolean result = employeeServiceImpl.isEmployeeExistsInDepartment(deptId);

        assertTrue(result);

        verify(employeeMapper, times(1)).selectEmployeeCountByDeptId(deptId);
    }

    @Test
    @DisplayName("Test Is Employee Exists In Department False")
    void testIsEmployeeExistsInDepartmentFalse() {

        Integer deptId = 999;
        when(employeeMapper.selectEmployeeCountByDeptId(deptId)).thenReturn(0L);

        Boolean result = employeeServiceImpl.isEmployeeExistsInDepartment(deptId);

        assertFalse(result);

        verify(employeeMapper, times(1)).selectEmployeeCountByDeptId(deptId);
    }


    private List<EmployeeSearchVO> createEmployeeSearchVOList() {
        List<EmployeeSearchVO> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            EmployeeSearchVO vo = new EmployeeSearchVO();
            vo.setId(i);
            list.add(vo);
        }
        return list;
    }

    private List<Map<String, Object>> createJobTitleCountMapList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("jobTitle", "Teacher");
        map1.put("count", 10);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("jobTitle", "Admin");
        map2.put("count", 5);
        list.add(map2);
        return list;
    }

    private List<Map<String, Object>> createGenderCountMapList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("gender", "Male");
        map1.put("count", 15);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("gender", "Female");
        map2.put("count", 10);
        list.add(map2);
        return list;
    }

    private List<EmployeeFindClassTeachersVO> createTeacherList() {
        List<EmployeeFindClassTeachersVO> list = new ArrayList<>();
        EmployeeFindClassTeachersVO teacher1 = new EmployeeFindClassTeachersVO();
        teacher1.setId(1);
        teacher1.setName("Teacher One");

        EmployeeFindClassTeachersVO teacher2 = new EmployeeFindClassTeachersVO();
        teacher2.setId(2);
        teacher2.setName("Teacher Two");

        list.add(teacher1);
        list.add(teacher2);
        return list;
    }
}
