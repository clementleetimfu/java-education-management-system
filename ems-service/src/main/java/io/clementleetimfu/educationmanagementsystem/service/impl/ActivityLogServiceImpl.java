package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.mapper.ActivityLogMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.activityLog.FindActivityLogVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.service.ActivityLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogMapper activityLogMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addActivityLog(ActivityLog activityLog) {
        return activityLogMapper.addActivityLog(activityLog) > 0;
    }

    @Override
    public PageResult<FindActivityLogVO> findActivityLog(FindActivityLogDTO findActivityLogDTO) {

        PageHelper.startPage(findActivityLogDTO.getPage(), findActivityLogDTO.getPageSize());

        List<FindActivityLogVO> findActivityLogVOList = activityLogMapper.findActivityLog();

        if (findActivityLogVOList.isEmpty()) {
            log.warn("Activity log list is empty");
            throw new BusinessException(ErrorCodeEnum.ACTIVITY_LOG_NOT_FOUND);
        }

        Page<FindActivityLogVO> page = (Page<FindActivityLogVO>) findActivityLogVOList;
        return new PageResult<>(page.getTotal(), page.getResult());

    }
}