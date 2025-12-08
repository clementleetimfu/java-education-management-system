package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.subject.SubjectFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public Result<List<SubjectFindAllDTO>> findAllSubjects() {
        return Result.success(subjectService.findAllSubjects());
    }
}