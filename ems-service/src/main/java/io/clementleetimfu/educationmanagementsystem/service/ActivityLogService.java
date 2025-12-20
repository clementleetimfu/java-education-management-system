package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.activityLog.FindActivityLogVO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;

public interface ActivityLogService {
    Boolean addActivityLog(ActivityLog activityLog);

    PageResult<FindActivityLogVO> findActivityLog(FindActivityLogDTO findActivityLogDTO);
}
