package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeFindClassTeachersVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeJobTitleCountVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.employee.EmployeeSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.*;
import io.clementleetimfu.educationmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emps")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/search")
    public Result<PageResult<EmployeeSearchVO>> searchEmployee(@ModelAttribute("employeeSearchRequestDTO")
                                                               EmployeeSearchDTO employeeSearchDTO) {
        return Result.success(employeeService.searchEmployee(employeeSearchDTO));
    }

    @AddActivityLog
    @PostMapping
    public Result<Boolean> addEmployee(@RequestBody EmployeeAddDTO employeeAddDTO) {
        return Result.success(employeeService.addEmployee(employeeAddDTO));
    }

    @AddActivityLog
    @DeleteMapping
    public Result<Boolean> deleteEmployeeByIds(@RequestParam("ids") List<Integer> ids) {
        return Result.success(employeeService.deleteEmployeeByIds(ids));
    }

    @GetMapping("/{id}")
    public Result<EmployeeFindByIdVO> findEmployeeById(@PathVariable("id") Integer id) {
        return Result.success(employeeService.findEmployeeById(id));
    }

    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateEmployee(@RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        return Result.success(employeeService.updateEmployee(employeeUpdateDTO));
    }

    @GetMapping("/jobTitle/count")
    public Result<EmployeeJobTitleCountVO> findEmployeeJobTitleCount() {
        return Result.success(employeeService.findEmployeeJobTitleCount());
    }

    @GetMapping("/gender/count")
    public Result<List<Map<String, Object>>> findEmployeeGenderCount() {
        return Result.success(employeeService.findEmployeeGenderCount());
    }

    @GetMapping("/teachers")
    public Result<List<EmployeeFindClassTeachersVO>> findAllTeachers() {
        return Result.success(employeeService.findAllTeachers());
    }

}