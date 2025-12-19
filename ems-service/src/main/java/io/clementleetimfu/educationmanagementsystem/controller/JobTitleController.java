package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.jobTitle.JobTitleFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobTitleController {

    @Autowired
    private JobTitleService jobTitleService;

    @GetMapping
    public Result<List<JobTitleFindAllDTO>> findAllJobTitle() {
        return Result.success(jobTitleService.findAllJobTitle());

    }
}
