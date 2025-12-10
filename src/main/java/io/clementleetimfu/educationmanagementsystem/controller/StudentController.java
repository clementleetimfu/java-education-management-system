package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;
import io.clementleetimfu.educationmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/search")
    public Result<PageResult<StudentSearchResponseDTO>> searchStudent(@ModelAttribute("studentSearchRequestDTO") StudentSearchRequestDTO studentSearchRequestDTO) {
        return Result.success(studentService.searchStudent(studentSearchRequestDTO));
    }

    @AddActivityLog
    @PostMapping
    public Result<Boolean> addStudent(@RequestBody StudentAddRequestDTO studentAddRequestDTO) {
        return Result.success(studentService.addStudent(studentAddRequestDTO));
    }

    @GetMapping("/{id}")
    public Result<StudentFindByIdResponseDTO> findStudentById(@PathVariable("id") Integer id) {
        return Result.success(studentService.findStudentById(id));
    }

    @AddActivityLog
    @DeleteMapping
    public Result<Boolean> deleteStudentByIds(@RequestParam("ids") List<Integer> ids) {
        return Result.success(studentService.deleteStudentByIds(ids));
    }

    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateStudent(@RequestBody StudentUpdateRequestDTO studentUpdateRequestDTO) {
        return Result.success(studentService.updateStudent(studentUpdateRequestDTO));
    }

    @GetMapping("/clazz/count")
    public Result<StudentFindCountByClazzDTO> findStudentCountByClazz() {
        return Result.success(studentService.findStudentCountByClazz());
    }

    @GetMapping("/edu-level/count")
    public Result<List<Map<String, Object>>> findStudentEduLevelCount() {
        return Result.success(studentService.findStudentEduLevelCount());
    }

}