package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeAddDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeQueryRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.EmployeeQueryResponseDTO;
import io.clementleetimfu.educationmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emps")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Result<PageResult<EmployeeQueryResponseDTO>> queryEmployee(EmployeeQueryRequestDTO employeeQueryRequestDTO) {
        return Result.success(employeeService.queryEmployee(employeeQueryRequestDTO));
    }

    @PostMapping
    public Result<Boolean> addEmployee(@RequestBody EmployeeAddDTO employeeAddDTO) {
        return Result.success(employeeService.addEmployee(employeeAddDTO));
    }

}