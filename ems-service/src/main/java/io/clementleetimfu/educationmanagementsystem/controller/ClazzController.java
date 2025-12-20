package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindAllVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
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
    public Result<PageResult<ClazzSearchVO>> searchClazz(@ModelAttribute("clazzSearchRequestDTO") ClazzSearchDTO clazzSearchDTO) {
        return Result.success(clazzService.searchClazz(clazzSearchDTO));
    }

    @AddActivityLog
    @PostMapping
    public Result<Boolean> addClazz(@RequestBody ClazzAddDTO clazzAddDTO) {
        return Result.success(clazzService.addClazz(clazzAddDTO));
    }

    @GetMapping("/{id}")
    public Result<ClazzFindByIdVO> findClazzById(@PathVariable("id") Integer id) {
        return Result.success(clazzService.findClazzById(id));
    }

    @AddActivityLog
    @PutMapping
    public Result<Boolean> updateClazzName(@RequestBody ClazzUpdateDTO clazzUpdateDTO) {
        return Result.success(clazzService.updateClazzName(clazzUpdateDTO));
    }

    @AddActivityLog
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteClazzById(@PathVariable("id") Integer id) {
        return Result.success(clazzService.deleteClazzById(id));
    }

    @GetMapping
    public Result<List<ClazzFindAllVO>> findAllClazz() {
        return Result.success(clazzService.findAllClazz());
    }
}
