package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog.FindActivityLogResponseDTO;
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
    public Result<PageResult<FindActivityLogResponseDTO>> findActivityLog(FindActivityLogRequestDTO findActivityLogRequestDTO) {
        return Result.success(activityLogService.findActivityLog(findActivityLogRequestDTO));
    }

}