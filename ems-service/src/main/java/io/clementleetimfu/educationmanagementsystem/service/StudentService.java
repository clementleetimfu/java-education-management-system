package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindCountByClazzVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentSearchVO;

import java.util.List;
import java.util.Map;

public interface StudentService {
    PageResult<StudentSearchVO> searchStudent(StudentSearchDTO studentSearchDTO);

    Boolean addStudent(StudentAddDTO studentAddDTO);

    StudentFindByIdVO findStudentById(Integer id);

    Boolean deleteStudentByIds(List<Integer> ids);

    Boolean updateStudent(StudentUpdateDTO studentUpdateDTO);

    StudentFindCountByClazzVO findStudentCountByClazz();

    List<Map<String, Object>> findStudentEduLevelCount();

    Boolean isStudentExistsInClazz(Integer clazzId);
}