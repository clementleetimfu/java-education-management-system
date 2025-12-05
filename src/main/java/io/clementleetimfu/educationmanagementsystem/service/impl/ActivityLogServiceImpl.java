package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.ActivityLogMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;
import io.clementleetimfu.educationmanagementsystem.service.ActivityLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogMapper activityLogMapper;

    @Override
    public Boolean addActivityLog(ActivityLog activityLog) {
        return activityLogMapper.addActivityLog(activityLog) > 0;
    }

    @Override
    public PageResult<FindActivityLogResponseDTO> findActivityLog(FindActivityLogRequestDTO findActivityLogRequestDTO) {

        PageHelper.startPage(findActivityLogRequestDTO.getPage(), findActivityLogRequestDTO.getPageSize());

        List<FindActivityLogResponseDTO> findActivityLogResponseDTOList = activityLogMapper.findActivityLog();

        if (findActivityLogResponseDTOList.isEmpty()) {
            log.warn("Activity log list is empty");
            throw new BusinessException(ErrorCodeEnum.ACTIVITY_LOG_NOT_FOUND);
        }

        Page<FindActivityLogResponseDTO> page = (Page<FindActivityLogResponseDTO>) findActivityLogResponseDTOList;
        return new PageResult<>(page.getTotal(), page.getResult());

    }
}