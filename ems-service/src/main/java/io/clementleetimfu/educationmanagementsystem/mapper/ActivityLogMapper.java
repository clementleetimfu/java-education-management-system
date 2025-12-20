package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.activityLog.FindActivityLogVO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;

import java.util.List;

public interface ActivityLogMapper {

    Integer addActivityLog(ActivityLog activityLog);

    List<FindActivityLogVO> findActivityLog();
}
