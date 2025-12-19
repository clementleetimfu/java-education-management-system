package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.educationLevel.EduLevelFindAllDTO;

import java.util.List;

public interface EducationLevelMapper {
    List<EduLevelFindAllDTO> selectAllEduLevel();
}
