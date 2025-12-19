package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzFindByIdResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzSearchRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzSearchResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper {
    List<ClazzSearchResponseDTO> searchClazz(ClazzSearchRequestDTO clazzSearchRequestDTO);

    Integer insertClazz(Clazz clazz);

    ClazzFindByIdResponseDTO selectClazzById(@Param("id") Integer id);

    Integer updateClazz(Clazz clazz);

    Integer deleteClazzById(@Param("id") Integer id);

    List<ClazzFindAllDTO> selectAllClazz();
}
