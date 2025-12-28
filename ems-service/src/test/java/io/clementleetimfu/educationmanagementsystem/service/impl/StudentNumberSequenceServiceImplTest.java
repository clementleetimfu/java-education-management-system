package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.StudentNumberSequenceMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Student Number Sequence Service Implementation Tests")
class StudentNumberSequenceServiceImplTest {

    @Mock
    private StudentNumberSequenceMapper studentNumberSequenceMapper;

    @InjectMocks
    private StudentNumberSequenceServiceImpl studentNumberSequenceServiceImpl;

    @Test
    @DisplayName("Test Add Student Number Sequence Success")
    void testAddStudentNumberSequenceSuccess() {
        LocalDate intakeDate = LocalDate.of(2024, 1, 1);
        when(studentNumberSequenceMapper.insertStudentNumberSequence(any(StudentNumberSequence.class))).thenReturn(1);

        Boolean result = studentNumberSequenceServiceImpl.addStudentNumberSequence(intakeDate);

        assertTrue(result);
        verify(studentNumberSequenceMapper, times(1)).insertStudentNumberSequence(any(StudentNumberSequence.class));
    }

    @Test
    @DisplayName("Test Add Student Number Sequence Failed Throws Exception")
    void testAddStudentNumberSequenceFailedThrowsException() {
        LocalDate intakeDate = LocalDate.of(2024, 1, 1);
        when(studentNumberSequenceMapper.insertStudentNumberSequence(any(StudentNumberSequence.class))).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> studentNumberSequenceServiceImpl.addStudentNumberSequence(intakeDate));
        assertEquals(ErrorCodeEnum.STUDENT_NUMBER_SEQUENCE_ADD_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_NUMBER_SEQUENCE_ADD_FAILED.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("Test Find Student Number Sequence Returns Result")
    void testFindStudentNumberSequenceReturnsResult() {
        LocalDate intakeDate = LocalDate.of(2024, 1, 1);
        StudentNumberSequence sequence = new StudentNumberSequence();
        sequence.setIntakeDate(intakeDate);
        sequence.setLastSeq(1);

        when(studentNumberSequenceMapper.selectStudentNumberSequenceByIntakeDate(intakeDate)).thenReturn(sequence);

        StudentNumberSequence result = studentNumberSequenceServiceImpl.findStudentNumberSequence(intakeDate);

        assertNotNull(result);
        assertEquals(intakeDate, result.getIntakeDate());
        assertEquals(1, result.getLastSeq());
    }

    @Test
    @DisplayName("Test Find Student Number Sequence Returns Null")
    void testFindStudentNumberSequenceReturnsNull() {
        LocalDate intakeDate = LocalDate.of(2024, 1, 1);
        when(studentNumberSequenceMapper.selectStudentNumberSequenceByIntakeDate(intakeDate)).thenReturn(null);

        StudentNumberSequence result = studentNumberSequenceServiceImpl.findStudentNumberSequence(intakeDate);

        assertNull(result);
    }

    @Test
    @DisplayName("Test Update Student Number Sequence Success")
    void testUpdateStudentNumberSequenceSuccess() {
        LocalDate intakeDate = LocalDate.of(2024, 1, 1);
        Integer seq = 5;
        when(studentNumberSequenceMapper.updateStudentNumberSequence(intakeDate, seq)).thenReturn(1);

        Boolean result = studentNumberSequenceServiceImpl.updateStudentNumberSequence(intakeDate, seq);

        assertTrue(result);
        verify(studentNumberSequenceMapper, times(1)).updateStudentNumberSequence(intakeDate, seq);
    }

    @Test
    @DisplayName("Test Update Student Number Sequence Failed Throws Exception")
    void testUpdateStudentNumberSequenceFailedThrowsException() {
        LocalDate intakeDate = LocalDate.of(2024, 1, 1);
        Integer seq = 5;
        when(studentNumberSequenceMapper.updateStudentNumberSequence(intakeDate, seq)).thenReturn(0);

        BusinessException exception = assertThrows(BusinessException.class, () -> studentNumberSequenceServiceImpl.updateStudentNumberSequence(intakeDate, seq));
        assertEquals(ErrorCodeEnum.STUDENT_NUMBER_SEQUENCE_UPDATE_FAILED.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.STUDENT_NUMBER_SEQUENCE_UPDATE_FAILED.getMessage(), exception.getMessage());
    }
}
