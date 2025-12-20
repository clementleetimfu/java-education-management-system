package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.subject.SubjectFindAllVO;

import java.util.List;

public interface SubjectMapper {
    List<SubjectFindAllVO> selectAllSubjects();
}
