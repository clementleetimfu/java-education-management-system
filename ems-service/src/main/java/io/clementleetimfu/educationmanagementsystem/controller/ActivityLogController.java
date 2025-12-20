package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.activityLog.FindActivityLogVO;
import io.clementleetimfu.educationmanagementsystem.service.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class ActivityLogController {

    @Autowired
    private ActivityLogService activityLogService;

    @GetMapping
    public Result<PageResult<FindActivityLogVO>> findActivityLog(FindActivityLogDTO findActivityLogDTO) {
        return Result.success(activityLogService.findActivityLog(findActivityLogDTO));
    }

}