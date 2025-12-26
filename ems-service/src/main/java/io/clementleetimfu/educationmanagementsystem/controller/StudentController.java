package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.annotation.Permission;
import io.clementleetimfu.educationmanagementsystem.constants.RoleEnum;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindCountByClazzVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentSearchVO;
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
    public Result<PageResult<StudentSearchVO>> searchStudent(@ModelAttribute("studentSearchRequestDTO") StudentSearchDTO studentSearchDTO) {
        return Result.success(studentService.searchStudent(studentSearchDTO));
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @PostMapping
    public Result<Boolean> addStudent(@RequestBody StudentAddDTO studentAddDTO) {
        return Result.success(studentService.addStudent(studentAddDTO));
    }

    @GetMapping("/{id}")
    public Result<StudentFindByIdVO> findStudentById(@PathVariable("id") Integer id) {
        return Result.success(studentService.findStudentById(id));
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @DeleteMapping
    public Result<Boolean> deleteStudentByIds(@RequestParam("ids") List<Integer> ids) {
        return Result.success(studentService.deleteStudentByIds(ids));
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateStudent(@RequestBody StudentUpdateDTO studentUpdateDTO) {
        return Result.success(studentService.updateStudent(studentUpdateDTO));
    }

    @GetMapping("/clazz/count")
    public Result<StudentFindCountByClazzVO> findStudentCountByClazz() {
        return Result.success(studentService.findStudentCountByClazz());
    }

    @GetMapping("/edu-level/count")
    public Result<List<Map<String, Object>>> findStudentEduLevelCount() {
        return Result.success(studentService.findStudentEduLevelCount());
    }

}