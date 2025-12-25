package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.StudentNumberSequenceMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import io.clementleetimfu.educationmanagementsystem.service.StudentNumberSequenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
public class StudentNumberSequenceServiceImpl implements StudentNumberSequenceService {

    @Autowired
    private StudentNumberSequenceMapper studentNumberSequenceMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addStudentNumberSequence(LocalDate intakeDate) {

        StudentNumberSequence studentNumberSequence = new StudentNumberSequence();
        studentNumberSequence.setIntakeDate(intakeDate);
        studentNumberSequence.setLastSeq(1);
        studentNumberSequence.setCreateTime(LocalDateTime.now());
        studentNumberSequence.setUpdateTime(LocalDateTime.now());
        studentNumberSequence.setIsDeleted(Boolean.FALSE);

        Integer insertSnsRowsAffected = studentNumberSequenceMapper.insertStudentNumberSequence(studentNumberSequence);
        if (insertSnsRowsAffected == 0) {
            log.warn("Failed to add student number sequence:{}", studentNumberSequence);
            throw new BusinessException(ErrorCodeEnum.STUDENT_NUMBER_SEQUENCE_ADD_FAILED);
        }

        return Boolean.TRUE;
    }

    @Override
    public StudentNumberSequence findStudentNumberSequence(LocalDate intakeDate) {
        return studentNumberSequenceMapper.selectStudentNumberSequenceByIntakeDate(intakeDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateStudentNumberSequence(LocalDate intakeDate, Integer seq) {

        Integer updateSnsRowsAffected = studentNumberSequenceMapper.updateStudentNumberSequence(intakeDate, seq);
        if (updateSnsRowsAffected == 0) {
            log.warn("Failed to update student number sequence:{}", updateSnsRowsAffected);
            throw new BusinessException(ErrorCodeEnum.STUDENT_NUMBER_SEQUENCE_UPDATE_FAILED);
        }

        return Boolean.TRUE;
    }
}
