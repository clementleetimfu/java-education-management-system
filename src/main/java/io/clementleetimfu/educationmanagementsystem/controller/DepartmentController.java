package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentListDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentUpdateDTO;
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
    public Result<List<DepartmentListDTO>> findAllDepartment() {
        return Result.success(departmentService.findAllDepartment());
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDepartmentById(@PathVariable("id") Integer id) {
        return Result.success(departmentService.deleteDepartmentById(id));
    }

    @PostMapping
    public Result<Boolean> addDepartment(@RequestBody DepartmentAddDTO departmentAddDTO) {
        return Result.success(departmentService.addDepartment(departmentAddDTO));
    }

    @GetMapping("/{id}")
    public Result<DepartmentFindByIdDTO> findDepartmentById(@PathVariable("id") Integer id) {
        return Result.success(departmentService.findDepartmentById(id));
    }

    @PutMapping
    public Result<Boolean> updateDepartment(@RequestBody DepartmentUpdateDTO departmentUpdateDTO) {
        return Result.success(departmentService.updateDepartment(departmentUpdateDTO));
    }

}
