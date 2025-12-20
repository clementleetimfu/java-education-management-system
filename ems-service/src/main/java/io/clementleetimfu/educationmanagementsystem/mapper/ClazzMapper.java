package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindAllVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz.ClazzSearchDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.clazz.ClazzSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Clazz;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzMapper {
    List<ClazzSearchVO> searchClazz(ClazzSearchDTO clazzSearchDTO);

    Integer insertClazz(Clazz clazz);

    ClazzFindByIdVO selectClazzById(@Param("id") Integer id);

    Integer updateClazz(Clazz clazz);

    Integer deleteClazzById(@Param("id") Integer id);

    List<ClazzFindAllVO> selectAllClazz();
}
