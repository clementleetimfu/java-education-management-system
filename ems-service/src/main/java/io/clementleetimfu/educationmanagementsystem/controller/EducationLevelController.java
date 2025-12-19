package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.educationLevel.EduLevelFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.service.EducationLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu-levels")
public class EducationLevelController {

    @Autowired
    private EducationLevelService educationLevelService;

    @GetMapping
    public Result<List<EduLevelFindAllDTO>> findAllEduLevel() {
        return Result.success(educationLevelService.findAllEduLevel());

    }
}


