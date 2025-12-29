package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.StudentMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentSearchDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Student;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindCountByClazzVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentSearchVO;
import io.clementleetimfu.educationmanagementsystem.service.StudentNumberSequenceService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Student Service Implementation Tests")
class StudentServiceImplTest {

    @Mock
    private StudentMapper studentMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private StudentNumberSequenceService studentNumberSequenceService;

    @InjectMocks
    private StudentServiceImpl studentService;

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
    @DisplayName("Test Search Student With Pagination")
    void testSearchStudentWithPagination() {
        
        StudentSearchDTO searchDTO = new StudentSearchDTO();

        List<StudentSearchVO> studentList = createStudentSearchVOList();
        Page<StudentSearchVO> page = new Page<>(1, 10);
        page.addAll(studentList);
        page.setTotal(10);

        when(studentMapper.searchStudent(any(StudentSearchDTO.class))).thenReturn(page);

        PageResult<StudentSearchVO> result = studentService.searchStudent(searchDTO);

        assertNotNull(result);
        assertEquals(10, result.getTotal());
        assertEquals(10, result.getRows().size());

        verify(studentMapper, times(1)).searchStudent(any(StudentSearchDTO.class));
    }

    @Test
    @DisplayName("Test Search Student Empty Result Throws Exception")
    void testSearchStudentEmptyResultThrowsException() {
        StudentSearchDTO searchDTO = new StudentSearchDTO();

        when(studentMapper.searchStudent(any(StudentSearchDTO.class))).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.searchStudent(searchDTO));
        assertEquals(ErrorCodeEnum.STUDENT_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Add Student Success With Existing Sequence")
    void testAddStudentSuccessWithExistingSequence() {
        
        StudentAddDTO studentAddDTO = new StudentAddDTO();
        studentAddDTO.setName("John Doe");
        studentAddDTO.setIntakeDate(LocalDate.of(2024, 1, 1));

        Student mappedStudent = new Student();
        mappedStudent.setId(1);

        StudentNumberSequence existingSequence = new StudentNumberSequence();
        existingSequence.setLastSeq(1);

        when(modelMapper.map(any(StudentAddDTO.class), eq(Student.class))).thenReturn(mappedStudent);
        when(studentNumberSequenceService.findStudentNumberSequence(any(LocalDate.class))).thenReturn(existingSequence);
        when(studentMapper.insertStudent(any(Student.class))).thenReturn(1);

        Boolean result = studentService.addStudent(studentAddDTO);

        assertTrue(result);
        verify(studentMapper, times(1)).insertStudent(any(Student.class));
        verify(studentNumberSequenceService, times(1)).updateStudentNumberSequence(any(LocalDate.class), eq(2));
    }

    @Test
    @DisplayName("Test Add Student Success With New Sequence")
    void testAddStudentSuccessWithNewSequence() {
        
        StudentAddDTO studentAddDTO = new StudentAddDTO();
        studentAddDTO.setName("John Doe");
        studentAddDTO.setIntakeDate(LocalDate.of(2024, 1, 1));

        Student mappedStudent = new Student();
        mappedStudent.setId(1);

        when(modelMapper.map(any(StudentAddDTO.class), eq(Student.class))).thenReturn(mappedStudent);
        when(studentNumberSequenceService.findStudentNumberSequence(any(LocalDate.class))).thenReturn(null);
        when(studentNumberSequenceService.addStudentNumberSequence(any(LocalDate.class))).thenReturn(true);
        when(studentMapper.insertStudent(any(Student.class))).thenReturn(1);

        Boolean result = studentService.addStudent(studentAddDTO);
        
        assertTrue(result);
        verify(studentMapper, times(1)).insertStudent(any(Student.class));
        verify(studentNumberSequenceService, times(1)).addStudentNumberSequence(any(LocalDate.class));
    }

    @Test
    @DisplayName("Test Add Student Failed Throws Exception")
    void testAddStudentFailedThrowsException() {
        
        StudentAddDTO studentAddDTO = new StudentAddDTO();
        studentAddDTO.setName("John Doe");
        studentAddDTO.setIntakeDate(LocalDate.of(2024, 1, 1));

        Student mappedStudent = new Student();

        when(modelMapper.map(any(StudentAddDTO.class), eq(Student.class))).thenReturn(mappedStudent);
        when(studentNumberSequenceService.findStudentNumberSequence(any(LocalDate.class))).thenReturn(null);
        when(studentNumberSequenceService.addStudentNumberSequence(any(LocalDate.class))).thenReturn(true);
        when(studentMapper.insertStudent(any(Student.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.addStudent(studentAddDTO));
        assertEquals(ErrorCodeEnum.STUDENT_ADD_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_ADD_FAILED.getMessage(), exception.getMessage());

        verify(studentMapper, times(1)).insertStudent(any(Student.class));
        verify(studentNumberSequenceService, times(1)).findStudentNumberSequence(any(LocalDate.class));
        verify(studentNumberSequenceService, times(1)).addStudentNumberSequence(any(LocalDate.class));
    }

    @Test
    @DisplayName("Test Find Student By Id Found")
    void testFindStudentByIdFound() {
        Integer id = 1;
        StudentFindByIdVO student = new StudentFindByIdVO();
        student.setId(id);
        student.setName("John Doe");

        when(studentMapper.selectStudentById(id)).thenReturn(student);

        StudentFindByIdVO result = studentService.findStudentById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("John Doe", result.getName());

        verify(studentMapper, times(1)).selectStudentById(id);
    }

    @Test
    @DisplayName("Test Find Student By Id Not Found Throws Exception")
    void testFindStudentByIdNotFoundThrowsException() {
        
        Integer id = 99999;
        when(studentMapper.selectStudentById(id)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.findStudentById(id));
        assertEquals(ErrorCodeEnum.STUDENT_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Delete Student By Ids Success")
    void testDeleteStudentByIdsSuccess() {
        
        List<Integer> ids = List.of(1, 2, 3);
        when(studentMapper.deleteStudentByIds(ids)).thenReturn(3);

        Boolean result = studentService.deleteStudentByIds(ids);

        assertTrue(result);
        verify(studentMapper, times(1)).deleteStudentByIds(ids);
    }

    @Test
    @DisplayName("Test Delete Student By Ids Failed Throws Exception")
    void testDeleteStudentByIdsFailedThrowsException() {
        
        List<Integer> ids = List.of(1);
        when(studentMapper.deleteStudentByIds(ids)).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.deleteStudentByIds(ids));
        assertEquals(ErrorCodeEnum.STUDENT_DELETE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_DELETE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Update Student Success")
    void testUpdateStudentSuccess() {
        
        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO();
        studentUpdateDTO.setId(1);
        studentUpdateDTO.setName("Updated Name");

        Student mappedStudent = new Student();

        when(modelMapper.map(any(StudentUpdateDTO.class), eq(Student.class))).thenReturn(mappedStudent);
        when(studentMapper.updateStudent(any(Student.class))).thenReturn(1);

        Boolean result = studentService.updateStudent(studentUpdateDTO);

        assertTrue(result);
        verify(studentMapper, times(1)).updateStudent(any(Student.class));
    }

    @Test
    @DisplayName("Test Update Student Failed Throws Exception")
    void testUpdateStudentFailedThrowsException() {
        
        StudentUpdateDTO studentUpdateDTO = new StudentUpdateDTO();
        studentUpdateDTO.setId(1);
        studentUpdateDTO.setName("Updated Name");

        Student mappedStudent = new Student();

        when(modelMapper.map(any(StudentUpdateDTO.class), eq(Student.class))).thenReturn(mappedStudent);
        when(studentMapper.updateStudent(any(Student.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.updateStudent(studentUpdateDTO));
        assertEquals(ErrorCodeEnum.STUDENT_UPDATE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_UPDATE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Find Student Edu Level Count Returns List")
    void testFindStudentEduLevelCountReturnsList() {
        
        List<Map<String, Object>> eduLevelCountList = createEduLevelCountMapList();
        when(studentMapper.findStudentEduLevelCount()).thenReturn(eduLevelCountList);

        List<Map<String, Object>> result = studentService.findStudentEduLevelCount();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(studentMapper, times(1)).findStudentEduLevelCount();
    }

    @Test
    @DisplayName("Test Find Student Edu Level Count Empty Throws Exception")
    void testFindStudentEduLevelCountEmptyThrowsException() {
        when(studentMapper.findStudentEduLevelCount()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.findStudentEduLevelCount());
        assertEquals(ErrorCodeEnum.STUDENT_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Is Student Exists In Clazz True")
    void testIsStudentExistsInClazzTrue() {
        
        Integer clazzId = 1;
        when(studentMapper.selectStudentCountByClazzId(clazzId)).thenReturn(5L);

        Boolean result = studentService.isStudentExistsInClazz(clazzId);

        assertTrue(result);
        verify(studentMapper, times(1)).selectStudentCountByClazzId(clazzId);
    }

    @Test
    @DisplayName("Test Is Student Exists In Clazz False")
    void testIsStudentExistsInClazzFalse() {
        
        Integer clazzId = 999;
        when(studentMapper.selectStudentCountByClazzId(clazzId)).thenReturn(0L);

        Boolean result = studentService.isStudentExistsInClazz(clazzId);

        assertFalse(result);
        verify(studentMapper, times(1)).selectStudentCountByClazzId(clazzId);
    }

    @Test
    @DisplayName("Test Find Student Count By Clazz Returns Result")
    void testFindStudentCountByClazzReturnsResult() {
        
        List<Map<String, Object>> studentCountByClazzList = createStudentCountByClazzMapList();
        when(studentMapper.findStudentCountByClazz()).thenReturn(studentCountByClazzList);

        StudentFindCountByClazzVO result = studentService.findStudentCountByClazz();

        assertNotNull(result);
        assertEquals(2, result.getClazzNameList().size());
        assertEquals(2, result.getStudentCountList().size());

        verify(studentMapper, times(1)).findStudentCountByClazz();
    }

    @Test
    @DisplayName("Test Find Student Count By Clazz Empty Throws Exception")
    void testFindStudentCountByClazzEmptyThrowsException() {
        
        when(studentMapper.findStudentCountByClazz()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.findStudentCountByClazz());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EMPLOYEE_NOT_FOUND.getMessage(), exception.getMessage());
    }


    private List<StudentSearchVO> createStudentSearchVOList() {
        List<StudentSearchVO> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            StudentSearchVO vo = new StudentSearchVO();
            vo.setId(i);
            vo.setName("Student " + i);
            list.add(vo);
        }
        return list;
    }

    private List<Map<String, Object>> createEduLevelCountMapList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("eduLevel", "Bachelor");
        map1.put("count", 15);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("eduLevel", "Master");
        map2.put("count", 10);
        list.add(map2);
        return list;
    }

    private List<Map<String, Object>> createStudentCountByClazzMapList() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("clazzName", "Class A");
        map1.put("count", 25);
        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("clazzName", "Class B");
        map2.put("count", 30);
        list.add(map2);
        return list;
    }
}
