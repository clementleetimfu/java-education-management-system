package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.educationLevel.EduLevelFindAllVO;

import java.util.List;

public interface EducationLevelMapper {
    List<EduLevelFindAllVO> selectAllEduLevel();
}
