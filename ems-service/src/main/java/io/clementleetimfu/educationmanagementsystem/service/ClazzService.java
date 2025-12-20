package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindAllVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.*;

import java.util.List;

public interface ClazzService {
    PageResult<ClazzSearchVO> searchClazz(ClazzSearchDTO clazzSearchDTO);

    Boolean addClazz(ClazzAddDTO clazzAddDTO);

    ClazzFindByIdVO findClazzById(Integer id);

    Boolean updateClazzName(ClazzUpdateDTO clazzUpdateDTO);

    Boolean deleteClazzById(Integer id);

    List<ClazzFindAllVO> findAllClazz();
}
