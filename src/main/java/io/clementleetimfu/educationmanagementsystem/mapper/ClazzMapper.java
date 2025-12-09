package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzFindByIdDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzSearchRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzSearchResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Clazz;

import java.util.List;

public interface ClazzMapper {
    List<ClazzSearchResponseDTO> searchClazz(ClazzSearchRequestDTO clazzSearchRequestDTO);

    Integer insertClazz(Clazz clazz);

    ClazzFindByIdDTO selectClazzById(Integer id);

    Integer updateClazz(Clazz clazz);

    Integer deleteClazzById(Integer id);

    List<ClazzFindAllDTO> selectAllClazz();
}
