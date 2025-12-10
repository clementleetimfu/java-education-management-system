package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentFindByIdResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentSearchRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentSearchResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Student;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    List<StudentSearchResponseDTO> searchStudent(StudentSearchRequestDTO studentSearchRequestDTO);

    Integer insertStudent(Student student);

    StudentFindByIdResponseDTO selectStudentById(@Param("id") Integer id);

    Integer deleteStudentByIds(List<Integer> ids);

    Integer updateStudent(Student student);

    @MapKey("educationLevel")
    List<Map<String, Object>> findStudentEduLevelCount();

    @MapKey("clazzName")
    List<Map<String, Object>> findStudentCountByClazz();

    Long selectStudentCountByClazzId(@Param("clazzId") Integer clazzId);
}