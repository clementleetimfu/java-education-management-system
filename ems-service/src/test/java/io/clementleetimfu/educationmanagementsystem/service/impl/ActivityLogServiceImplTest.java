package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.ActivityLogMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.activityLog.FindActivityLogVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Activity Log Service Impl Test")
class ActivityLogServiceImplTest {

    @Mock
    private ActivityLogMapper activityLogMapper;

    @InjectMocks
    private ActivityLogServiceImpl activityLogServiceImpl;

    @Test
    @DisplayName("Add activity Log Success")
    void addActivityLogSuccess() {
        ActivityLog activityLog = new ActivityLog();
        when(activityLogMapper.addActivityLog(any(ActivityLog.class))).thenReturn(1);

        Boolean result = activityLogServiceImpl.addActivityLog(activityLog);

        assertTrue(result);
        verify(activityLogMapper, times(1)).addActivityLog(activityLog);
    }

    @Test
    @DisplayName("Find Activity Log Success")
    void findActivityLogSuccess() {
        FindActivityLogDTO dto = new FindActivityLogDTO();

        Page<FindActivityLogVO> mockPage = new Page<>(1, 10);
        mockPage.setTotal(1);
        mockPage.add(new FindActivityLogVO());

        when(activityLogMapper.findActivityLog()).thenReturn(mockPage);

        PageResult<FindActivityLogVO> result = activityLogServiceImpl.findActivityLog(dto);

        assertNotNull(result);
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getRows().size());

        verify(activityLogMapper, times(1)).findActivityLog();
    }

    @Test
    @DisplayName("Find Activity Log Empty Throws Exception")
    void findActivityLogEmptyThrowsException() {
        FindActivityLogDTO dto = new FindActivityLogDTO();

        when(activityLogMapper.findActivityLog()).thenReturn(Collections.emptyList());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            activityLogServiceImpl.findActivityLog(dto);
        });

        assertEquals(ErrorCodeEnum.ACTIVITY_LOG_NOT_FOUND.getCode(), exception.getCode());
        assertEquals(ErrorCodeEnum.ACTIVITY_LOG_NOT_FOUND.getMessage(), exception.getMessage());

        verify(activityLogMapper).findActivityLog();
    }
}