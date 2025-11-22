package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.mapper.ActivityLogMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;
import io.clementleetimfu.educationmanagementsystem.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    private ActivityLogMapper activityLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean addActivityLog(String info) {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setOperateTime(LocalDateTime.now());
        activityLog.setInfo(info);
        activityLog.setCreateTime(LocalDateTime.now());
        activityLog.setUpdateTime(LocalDateTime.now());
        activityLog.setIsDeleted(Boolean.FALSE);
        return activityLogMapper.addActivityLog(activityLog) > 0;
    }
}
