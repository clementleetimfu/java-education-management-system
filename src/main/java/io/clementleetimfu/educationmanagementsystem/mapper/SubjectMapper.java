package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.subject.SubjectFindAllDTO;

import java.util.List;

public interface SubjectMapper {
    List<SubjectFindAllDTO> selectAllSubjects();
}
