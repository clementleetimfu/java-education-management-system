package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindAllResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateRequestDTO;
import io.clementleetimfu.educationmanagementsystem.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/depts")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Result<List<DepartmentFindAllResponseDTO>> findAllDepartment() {
        return Result.success(departmentService.findAllDepartment());
    }

    @AddActivityLog
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDepartmentById(@PathVariable("id") Integer id) {
        return Result.success(departmentService.deleteDepartmentById(id));
    }

    @AddActivityLog
    @PostMapping
    public Result<Boolean> addDepartment(@RequestBody DepartmentAddRequestDTO departmentAddRequestDTO) {
        return Result.success(departmentService.addDepartment(departmentAddRequestDTO));
    }

    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateDepartmentName(@RequestBody DepartmentUpdateRequestDTO departmentUpdateRequestDTO) {
        return Result.success(departmentService.updateDepartmentName(departmentUpdateRequestDTO));
    }

}