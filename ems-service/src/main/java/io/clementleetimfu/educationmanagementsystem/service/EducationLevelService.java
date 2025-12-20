package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.educationLevel.EduLevelFindAllVO;

import java.util.List;

public interface EducationLevelService {
    List<EduLevelFindAllVO> findAllEduLevel();
}
