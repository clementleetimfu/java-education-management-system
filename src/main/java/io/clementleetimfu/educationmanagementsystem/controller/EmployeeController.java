package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
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
    public Result<PageResult<EmployeeSearchResponseDTO>> searchEmployee(@ModelAttribute("employeeSearchRequestDTO")
                                                                        EmployeeSearchRequestDTO employeeSearchRequestDTO) {
        return Result.success(employeeService.searchEmployee(employeeSearchRequestDTO));
    }

    @AddActivityLog
    @PostMapping
    public Result<Boolean> addEmployee(@RequestBody EmployeeAddRequestDTO employeeAddRequestDTO) {
        return Result.success(employeeService.addEmployee(employeeAddRequestDTO));
    }

    @AddActivityLog
    @DeleteMapping
    public Result<Boolean> deleteEmployeeByIds(@RequestParam("ids") List<Integer> ids) {
        return Result.success(employeeService.deleteEmployeeByIds(ids));
    }

    @GetMapping("/{id}")
    public Result<EmployeeFindByIdRequestDTO> findEmployeeById(@PathVariable("id") Integer id) {
        return Result.success(employeeService.findEmployeeById(id));
    }

    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateEmployee(@RequestBody EmployeeUpdateRequestDTO employeeUpdateRequestDTO) {
        return Result.success(employeeService.updateEmployee(employeeUpdateRequestDTO));
    }

    @GetMapping("/jobTitle/count")
    public Result<EmployeeJobTitleCountRequestDTO> findEmployeeJobTitleCount() {
        return Result.success(employeeService.findEmployeeJobTitleCount());
    }

    @GetMapping("/gender/count")
    public Result<List<Map<String, Object>>> findEmployeeGenderCount() {
        return Result.success(employeeService.findEmployeeGenderCount());
    }

    @GetMapping("/teachers")
    public Result<List<EmployeeFindClassTeachersDTO>> findAllTeachers() {
        return Result.success(employeeService.findAllTeachers());
    }

}