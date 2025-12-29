package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.ClazzMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzSearchDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Clazz;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindAllVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.service.StudentService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Clazz Service Implementation Tests")
class ClazzServiceImplTest {

    @Mock
    private ClazzMapper clazzMapper;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private ClazzServiceImpl clazzServiceImpl;

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
    @DisplayName("Test Search Clazz With Pagination")
    void testSearchClazzWithPagination() {
        ClazzSearchDTO searchDTO = new ClazzSearchDTO();

        List<ClazzSearchVO> clazzList = createClazzSearchVOList();
        Page<ClazzSearchVO> page = new Page<>(1, 10);
        page.addAll(clazzList);
        page.setTotal(10);

        when(clazzMapper.searchClazz(any(ClazzSearchDTO.class))).thenReturn(page);

        PageResult<ClazzSearchVO> result = clazzServiceImpl.searchClazz(searchDTO);

        assertNotNull(result);
        assertEquals(10, result.getTotal());
        assertEquals(10, result.getRows().size());

        verify(clazzMapper, times(1)).searchClazz(any(ClazzSearchDTO.class));
    }

    @Test
    @DisplayName("Test Search Clazz Empty Result Throws Exception")
    void testSearchClazzEmptyResultThrowsException() {
        ClazzSearchDTO searchDTO = new ClazzSearchDTO();

        when(clazzMapper.searchClazz(any(ClazzSearchDTO.class))).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> clazzServiceImpl.searchClazz(searchDTO));
        assertEquals(ErrorCodeEnum.CLAZZ_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.CLAZZ_NOT_FOUND.getMessage(), exception.getMessage());

        verify(clazzMapper, times(1)).searchClazz(any(ClazzSearchDTO.class));
    }

    @Test
    @DisplayName("Test Add Clazz Success")
    void testAddClazzSuccess() {
        ClazzAddDTO clazzAddDTO = new ClazzAddDTO();

        Clazz mappedClazz = new Clazz();

        when(modelMapper.map(any(ClazzAddDTO.class), eq(Clazz.class))).thenReturn(mappedClazz);
        when(clazzMapper.insertClazz(any(Clazz.class))).thenReturn(1);

        Boolean result = clazzServiceImpl.addClazz(clazzAddDTO);

        assertTrue(result);
        verify(clazzMapper, times(1)).insertClazz(any(Clazz.class));
    }

    @Test
    @DisplayName("Test Add Clazz Failed Throws Exception")
    void testAddClazzFailedThrowsException() {
        ClazzAddDTO clazzAddDTO = new ClazzAddDTO();

        Clazz mappedClazz = new Clazz();

        when(modelMapper.map(any(ClazzAddDTO.class), eq(Clazz.class))).thenReturn(mappedClazz);
        when(clazzMapper.insertClazz(any(Clazz.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> clazzServiceImpl.addClazz(clazzAddDTO));
        assertEquals(ErrorCodeEnum.CLAZZ_ADD_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.CLAZZ_ADD_FAILED.getMessage(), exception.getMessage());

        verify(clazzMapper, times(1)).insertClazz(any(Clazz.class));
    }

    @Test
    @DisplayName("Test Find Clazz By Id Found")
    void testFindClazzByIdFound() {
        Integer id = 1;
        ClazzFindByIdVO clazz = new ClazzFindByIdVO();
        clazz.setId(id);
        clazz.setName("Test Class");

        when(clazzMapper.selectClazzById(id)).thenReturn(clazz);

        ClazzFindByIdVO result = clazzServiceImpl.findClazzById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test Class", result.getName());

        verify(clazzMapper, times(1)).selectClazzById(id);
    }

    @Test
    @DisplayName("Test Find Clazz By Id Not Found Throws Exception")
    void testFindClazzByIdNotFoundThrowsException() {
        Integer id = 99999;
        when(clazzMapper.selectClazzById(id)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> clazzServiceImpl.findClazzById(id));
        assertEquals(ErrorCodeEnum.CLAZZ_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.CLAZZ_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Update Clazz Name Success")
    void testUpdateClazzNameSuccess() {
        ClazzUpdateDTO clazzUpdateDTO = new ClazzUpdateDTO();
        clazzUpdateDTO.setId(1);
        clazzUpdateDTO.setName("Updated Class");

        Clazz mappedClazz = new Clazz();

        when(modelMapper.map(any(ClazzUpdateDTO.class), eq(Clazz.class))).thenReturn(mappedClazz);
        when(clazzMapper.updateClazz(any(Clazz.class))).thenReturn(1);

        Boolean result = clazzServiceImpl.updateClazzName(clazzUpdateDTO);

        assertTrue(result);
        verify(clazzMapper, times(1)).updateClazz(any(Clazz.class));
    }

    @Test
    @DisplayName("Test Update Clazz Name Failed Throws Exception")
    void testUpdateClazzNameFailedThrowsException() {
        ClazzUpdateDTO clazzUpdateDTO = new ClazzUpdateDTO();
        clazzUpdateDTO.setId(1);
        clazzUpdateDTO.setName("Updated Class");

        Clazz mappedClazz = new Clazz();

        when(modelMapper.map(any(ClazzUpdateDTO.class), eq(Clazz.class))).thenReturn(mappedClazz);
        when(clazzMapper.updateClazz(any(Clazz.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> clazzServiceImpl.updateClazzName(clazzUpdateDTO));
        assertEquals(ErrorCodeEnum.CLAZZ_UPDATE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.CLAZZ_UPDATE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Delete Clazz By Id Success")
    void testDeleteClazzByIdSuccess() {
        Integer id = 1;
        when(studentService.isStudentExistsInClazz(id)).thenReturn(false);
        when(clazzMapper.deleteClazzById(id)).thenReturn(1);

        Boolean result = clazzServiceImpl.deleteClazzById(id);

        assertTrue(result);
        verify(clazzMapper, times(1)).deleteClazzById(id);
    }

    @Test
    @DisplayName("Test Delete Clazz By Id Not Allowed Throws Exception")
    void testDeleteClazzByIdNotAllowedThrowsException() {
        Integer id = 1;
        when(studentService.isStudentExistsInClazz(id)).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () -> clazzServiceImpl.deleteClazzById(id));
        assertEquals(ErrorCodeEnum.CLAZZ_DELETE_NOT_ALLOWED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.CLAZZ_DELETE_NOT_ALLOWED.getMessage(), exception.getMessage());

        verify(clazzMapper, never()).deleteClazzById(anyInt());
    }

    @Test
    @DisplayName("Test Delete Clazz By Id Failed Throws Exception")
    void testDeleteClazzByIdFailedThrowsException() {
        Integer id = 1;
        when(studentService.isStudentExistsInClazz(id)).thenReturn(false);
        when(clazzMapper.deleteClazzById(id)).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> clazzServiceImpl.deleteClazzById(id));
        assertEquals(ErrorCodeEnum.CLAZZ_DELETE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.CLAZZ_DELETE_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Find All Clazz Returns List")
    void testFindAllClazzReturnsList() {
        List<ClazzFindAllVO> clazzList = createClazzFindAllVOList();
        when(clazzMapper.selectAllClazz()).thenReturn(clazzList);

        List<ClazzFindAllVO> result = clazzServiceImpl.findAllClazz();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(clazzMapper, times(1)).selectAllClazz();
    }

    @Test
    @DisplayName("Test Find All Clazz Empty Throws Exception")
    void testFindAllClazzEmptyThrowsException() {
        when(clazzMapper.selectAllClazz()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> clazzServiceImpl.findAllClazz());
        assertEquals(ErrorCodeEnum.CLAZZ_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.CLAZZ_NOT_FOUND.getMessage(), exception.getMessage());
    }

    private List<ClazzSearchVO> createClazzSearchVOList() {
        List<ClazzSearchVO> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ClazzSearchVO vo = new ClazzSearchVO();
            vo.setId(i);
            list.add(vo);
        }
        return list;
    }

    private List<ClazzFindAllVO> createClazzFindAllVOList() {
        List<ClazzFindAllVO> list = new ArrayList<>();
        ClazzFindAllVO vo1 = new ClazzFindAllVO();
        vo1.setId(1);
        vo1.setName("Class 1");

        ClazzFindAllVO vo2 = new ClazzFindAllVO();
        vo2.setId(2);
        vo2.setName("Class 2");

        list.add(vo1);
        list.add(vo2);
        return list;
    }
}
