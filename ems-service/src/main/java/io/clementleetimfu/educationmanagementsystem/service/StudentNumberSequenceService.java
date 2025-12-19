package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;

public interface StudentNumberSequenceService {

    Boolean addStudentNumberSequence(LocalDate intakeDate);

    StudentNumberSequence findStudentNumberSequence(LocalDate intakeDate);

    Boolean updateStudentNumberSequence(@Param("intakeDate") LocalDate intakeDate, @Param("seq") Integer seq);
}
