package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

public interface StudentNumberSequenceMapper {

    Integer insertStudentNumberSequence(StudentNumberSequence studentNumberSequence);

    StudentNumberSequence selectStudentNumberSequenceByIntakeDate(@Param("intakeDate") LocalDate intakeDate);

    Integer updateStudentNumberSequence(@Param("intakeDate") LocalDate intakeDate, @Param("seq") Integer seq);
}
