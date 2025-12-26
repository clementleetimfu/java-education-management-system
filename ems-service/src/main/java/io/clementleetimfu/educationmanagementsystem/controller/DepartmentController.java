package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.annotation.Permission;
import io.clementleetimfu.educationmanagementsystem.constants.RoleEnum;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.department.DepartmentAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.department.DepartmentFindAllVO;
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
    public Result<List<DepartmentFindAllVO>> findAllDepartment() {
        return Result.success(departmentService.findAllDepartment());
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDepartmentById(@PathVariable("id") Integer id) {
        return Result.success(departmentService.deleteDepartmentById(id));
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @PostMapping
    public Result<Boolean> addDepartment(@RequestBody DepartmentAddDTO departmentAddDTO) {
        return Result.success(departmentService.addDepartment(departmentAddDTO));
    }

    @Permission(role = RoleEnum.ROLE_ADMIN)
    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateDepartmentName(@RequestBody DepartmentUpdateDTO departmentUpdateDTO) {
        return Result.success(departmentService.updateDepartmentName(departmentUpdateDTO));
    }

}