package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.EducationLevelMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.educationLevel.EduLevelFindAllVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Education Level Service Implementation Tests")
class EducationLevelServiceImplTest {

    @Mock
    private EducationLevelMapper educationLevelMapper;

    @InjectMocks
    private EducationLevelServiceImpl educationLevelServiceImpl;

    @Test
    @DisplayName("Test Find All Edu Level Returns List")
    void testFindAllEduLevelReturnsList() {
        List<EduLevelFindAllVO> eduLevelList = new ArrayList<>();
        EduLevelFindAllVO vo1 = new EduLevelFindAllVO();
        vo1.setId(1);
        vo1.setName("Bachelor");

        EduLevelFindAllVO vo2 = new EduLevelFindAllVO();
        vo2.setId(2);
        vo2.setName("Master");

        eduLevelList.add(vo1);
        eduLevelList.add(vo2);

        when(educationLevelMapper.selectAllEduLevel()).thenReturn(eduLevelList);

        List<EduLevelFindAllVO> result = educationLevelServiceImpl.findAllEduLevel();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(educationLevelMapper, times(1)).selectAllEduLevel();
    }

    @Test
    @DisplayName("Test Find All Edu Level Empty Throws Exception")
    void testFindAllEduLevelEmptyThrowsException() {
        when(educationLevelMapper.selectAllEduLevel()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> educationLevelServiceImpl.findAllEduLevel());
        assertEquals(ErrorCodeEnum.EDUCATION_LEVEL_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.EDUCATION_LEVEL_NOT_FOUND.getMessage(), exception.getMessage());
    }

}
