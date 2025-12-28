package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.SubjectMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.subject.SubjectFindAllVO;
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
@DisplayName("Subject Service Implementation Tests")
class SubjectServiceImplTest {

    @Mock
    private SubjectMapper subjectMapper;

    @InjectMocks
    private SubjectServiceImpl subjectServiceImpl;

    @Test
    @DisplayName("Test Find All Subjects Returns List")
    void testFindAllSubjectsReturnsList() {

        List<SubjectFindAllVO> subjectList = new ArrayList<>();
        SubjectFindAllVO vo1 = new SubjectFindAllVO();
        vo1.setId(1);
        vo1.setName("Java");

        SubjectFindAllVO vo2 = new SubjectFindAllVO();
        vo2.setId(2);
        vo2.setName("Python");

        subjectList.add(vo1);
        subjectList.add(vo2);

        when(subjectMapper.selectAllSubjects()).thenReturn(subjectList);

        List<SubjectFindAllVO> result = subjectServiceImpl.findAllSubjects();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(subjectMapper, times(1)).selectAllSubjects();
    }

    @Test
    @DisplayName("Test Find All Subjects Empty Throws Exception")
    void testFindAllSubjectsEmptyThrowsException() {
        when(subjectMapper.selectAllSubjects()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> subjectServiceImpl.findAllSubjects());
        assertEquals(ErrorCodeEnum.SUBJECT_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.SUBJECT_NOT_FOUND.getMessage(), exception.getMessage());
    }
}