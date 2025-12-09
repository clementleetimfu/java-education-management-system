package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentFindByIdResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentSearchRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentSearchResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Student;

import java.util.List;

public interface StudentMapper {
    List<StudentSearchResponseDTO> searchStudent(StudentSearchRequestDTO studentSearchRequestDTO);

    Integer insertStudent(Student student);

    StudentFindByIdResponseDTO selectStudentById(Integer id);

    Integer deleteStudentByIds(List<Integer> ids);

    Integer updateStudent(Student student);
}
