package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.JobTitleMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.jobTitle.JobTitleFindAllVO;
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
@DisplayName("Job Title Service Implementation Tests")
class JobTitleServiceImplTest {

    @Mock
    private JobTitleMapper jobTitleMapper;

    @InjectMocks
    private JobTitleServiceImpl jobTitleServiceImpl;

    @Test
    @DisplayName("Test Find All Job Title Returns List")
    void testFindAllJobTitleReturnsList() {
        List<JobTitleFindAllVO> jobTitleList = new ArrayList<>();
        JobTitleFindAllVO vo1 = new JobTitleFindAllVO();
        vo1.setId(1);
        vo1.setName("Job title 1");

        JobTitleFindAllVO vo2 = new JobTitleFindAllVO();
        vo2.setId(2);
        vo2.setName("Job title 2");

        jobTitleList.add(vo1);
        jobTitleList.add(vo2);

        when(jobTitleMapper.selectAllJobTitle()).thenReturn(jobTitleList);

        List<JobTitleFindAllVO> result = jobTitleServiceImpl.findAllJobTitle();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jobTitleMapper, times(1)).selectAllJobTitle();
    }

    @Test
    @DisplayName("Test Find All Job Title Empty Throws Exception")
    void testFindAllJobTitleEmptyThrowsException() {
        when(jobTitleMapper.selectAllJobTitle()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> jobTitleServiceImpl.findAllJobTitle());
        assertEquals(ErrorCodeEnum.JOB_TITLE_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.JOB_TITLE_NOT_FOUND.getMessage(), exception.getMessage());
    }

}
