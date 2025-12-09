package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeUpdateDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;
import io.clementleetimfu.educationmanagementsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/search")
    public Result<PageResult<StudentSearchResponseDTO>> searchStudent(@ModelAttribute("studentSearchRequestDTO") StudentSearchRequestDTO studentSearchRequestDTO) {
        return Result.success(studentService.searchStudent(studentSearchRequestDTO));
    }

    @PostMapping
    public Result<Boolean> addStudent(@RequestBody StudentAddDTO studentAddDTO) {
        return Result.success(studentService.addStudent(studentAddDTO));
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
    public Result<Boolean> updateStudent(@RequestBody StudentUpdateDTO studentUpdateDTO) {
        return Result.success(studentService.updateStudent(studentUpdateDTO));
    }
}
