package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.*;
import io.clementleetimfu.educationmanagementsystem.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/search")
    public Result<PageResult<ClazzSearchResponseDTO>> searchClazz(@ModelAttribute("clazzSearchRequestDTO") ClazzSearchRequestDTO clazzSearchRequestDTO) {
        return Result.success(clazzService.searchClazz(clazzSearchRequestDTO));
    }

    @AddActivityLog
    @PostMapping
    public Result<Boolean> addClazz(@RequestBody ClazzAddDTO clazzAddDTO) {
        return Result.success(clazzService.addClazz(clazzAddDTO));
    }

    @GetMapping("/{id}")
    public Result<ClazzFindByIdResponseDTO> findClazzById(@PathVariable("id") Integer id) {
        return Result.success(clazzService.findClazzById(id));
    }

    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateClazzName(@RequestBody ClazzUpdateRequestDTO clazzUpdateRequestDTO) {
        return Result.success(clazzService.updateClazzName(clazzUpdateRequestDTO));
    }

    @AddActivityLog
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteClazzById(@PathVariable("id") Integer id) {
        return Result.success(clazzService.deleteClazzById(id));
    }

    @GetMapping
    public Result<List<ClazzFindAllDTO>> findAllClazz() {
        return Result.success(clazzService.findAllClazz());
    }
}
