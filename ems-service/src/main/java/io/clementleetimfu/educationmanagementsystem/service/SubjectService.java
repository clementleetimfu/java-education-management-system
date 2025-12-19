package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.subject.SubjectFindAllDTO;

import java.util.List;

public interface SubjectService {
    List<SubjectFindAllDTO> findAllSubjects();
}
