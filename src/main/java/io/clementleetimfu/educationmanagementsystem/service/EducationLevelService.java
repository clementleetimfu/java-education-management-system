package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.educationLevel.EduLevelFindAllDTO;

import java.util.List;

public interface EducationLevelService {
    List<EduLevelFindAllDTO> findAllEduLevel();
}
