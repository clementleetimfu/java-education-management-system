package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;

import java.util.List;

public interface StudentService {
    PageResult<StudentSearchResponseDTO> searchStudent(StudentSearchRequestDTO studentSearchRequestDTO);

    Boolean addStudent(StudentAddDTO studentAddDTO);

    StudentFindByIdResponseDTO findStudentById(Integer id);

    Boolean deleteStudentByIds(List<Integer> ids);

    Boolean updateStudent(StudentUpdateDTO studentUpdateDTO);
}
