package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.subject.SubjectFindAllVO;

import java.util.List;

public interface SubjectService {
    List<SubjectFindAllVO> findAllSubjects();
}
