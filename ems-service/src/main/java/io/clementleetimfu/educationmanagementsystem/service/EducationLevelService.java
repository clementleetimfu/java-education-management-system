package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.educationLevel.EduLevelFindAllDTO;

import java.util.List;

public interface EducationLevelService {
    List<EduLevelFindAllDTO> findAllEduLevel();
}
