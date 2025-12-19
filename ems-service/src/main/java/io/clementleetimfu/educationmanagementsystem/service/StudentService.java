package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;

import java.util.List;
import java.util.Map;

public interface StudentService {
    PageResult<StudentSearchResponseDTO> searchStudent(StudentSearchRequestDTO studentSearchRequestDTO);

    Boolean addStudent(StudentAddRequestDTO studentAddRequestDTO);

    StudentFindByIdResponseDTO findStudentById(Integer id);

    Boolean deleteStudentByIds(List<Integer> ids);

    Boolean updateStudent(StudentUpdateRequestDTO studentUpdateRequestDTO);

    StudentFindCountByClazzDTO findStudentCountByClazz();

    List<Map<String, Object>> findStudentEduLevelCount();

    Boolean isStudentExistsInClazz(Integer clazzId);
}