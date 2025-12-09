package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

public interface StudentNumberSequenceMapper {
    Long isIntakeExist(@Param("intakeDate") LocalDate intakeDate);

    Integer insertStudentNumberSequence(StudentNumberSequence studentNumberSequence);

    StudentNumberSequence selectStudentNumberSequenceByIntakeDate(LocalDate intakeDate);
}
