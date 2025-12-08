package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.*;

public interface ClazzService {
    PageResult<ClazzSearchResponseDTO> searchClazz(ClazzSearchRequestDTO clazzSearchRequestDTO);

    Boolean addClazz(ClazzAddDTO clazzAddDTO);

    ClazzFindByIdDTO findClazzById(Integer id);

    Boolean updateClazzName(ClazzUpdateDTO clazzUpdateDTO);

    Boolean deleteClazzById(Integer id);
}
