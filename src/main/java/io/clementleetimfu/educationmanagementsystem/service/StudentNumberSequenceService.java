package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;

import java.time.LocalDate;

public interface StudentNumberSequenceService {
    Boolean isIntakeExist(LocalDate intakeDate);

    Boolean addStudentNumberSequence(LocalDate intakeDate);

    StudentNumberSequence findStudentNumberSequence(LocalDate intakeDate);
}
