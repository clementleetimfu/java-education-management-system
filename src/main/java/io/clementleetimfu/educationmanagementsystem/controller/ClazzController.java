package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.*;
import io.clementleetimfu.educationmanagementsystem.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/search")
    public Result<PageResult<ClazzSearchResponseDTO>> searchClazz(@ModelAttribute("clazzSearchRequestDTO") ClazzSearchRequestDTO clazzSearchRequestDTO) {
        return Result.success(clazzService.searchClazz(clazzSearchRequestDTO));
    }

    @PostMapping
    public Result<Boolean> addClazz(@RequestBody ClazzAddDTO clazzAddDTO) {
        return Result.success(clazzService.addClazz(clazzAddDTO));
    }

    @GetMapping("/{id}")
    public Result<ClazzFindByIdDTO> findClazzById(@PathVariable Integer id) {
        return Result.success(clazzService.findClazzById(id));
    }

    @PutMapping
    public Result<Boolean> updateClazzName(@RequestBody ClazzUpdateDTO clazzUpdateDTO) {
        return Result.success(clazzService.updateClazzName(clazzUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> deleteClazzById(@PathVariable Integer id) {
        return Result.success(clazzService.deleteClazzById(id));
    }

}
