package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.StudentSearchDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentSearchVO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Student;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    List<StudentSearchVO> searchStudent(StudentSearchDTO studentSearchDTO);

    Integer insertStudent(Student student);

    StudentFindByIdVO selectStudentById(@Param("id") Integer id);

    Integer deleteStudentByIds(List<Integer> ids);

    Integer updateStudent(Student student);

    @MapKey("educationLevel")
    List<Map<String, Object>> findStudentEduLevelCount();

    @MapKey("clazzName")
    List<Map<String, Object>> findStudentCountByClazz();

    Long selectStudentCountByClazzId(@Param("clazzId") Integer clazzId);
}