package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;

public interface ActivityLogService {
    Boolean addActivityLog(ActivityLog activityLog);

    PageResult<FindActivityLogResponseDTO> findActivityLog(FindActivityLogRequestDTO findActivityLogRequestDTO);
}
