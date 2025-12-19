package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.*;

import java.util.List;

public interface ClazzService {
    PageResult<ClazzSearchResponseDTO> searchClazz(ClazzSearchRequestDTO clazzSearchRequestDTO);

    Boolean addClazz(ClazzAddDTO clazzAddDTO);

    ClazzFindByIdResponseDTO findClazzById(Integer id);

    Boolean updateClazzName(ClazzUpdateRequestDTO clazzUpdateRequestDTO);

    Boolean deleteClazzById(Integer id);

    List<ClazzFindAllDTO> findAllClazz();
}
