package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.StudentMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Student;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import io.clementleetimfu.educationmanagementsystem.service.StudentNumberSequenceService;
import io.clementleetimfu.educationmanagementsystem.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StudentNumberSequenceService studentNumberSequenceService;

    @Override
    public PageResult<StudentSearchResponseDTO> searchStudent(StudentSearchRequestDTO studentSearchRequestDTO) {

        PageHelper.startPage(studentSearchRequestDTO.getPage(), studentSearchRequestDTO.getPageSize());

        List<StudentSearchResponseDTO> studentSearchResponseDTOList = studentMapper.searchStudent(studentSearchRequestDTO);

        if (studentSearchResponseDTOList.isEmpty()) {
            log.warn("Student list is empty");
            throw new BusinessException(ErrorCodeEnum.STUDENT_NOT_FOUND);
        }

        Page<StudentSearchResponseDTO> page = (Page<StudentSearchResponseDTO>) studentSearchResponseDTOList;
        return new PageResult<>(page.getTotal(), page.getResult());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addStudent(StudentAddDTO studentAddDTO) {
        Student student = modelMapper.map(studentAddDTO, Student.class);

        LocalDate intakeDate = studentAddDTO.getIntakeDate();
        String datePart = intakeDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Boolean intakeExist = studentNumberSequenceService.isIntakeExist(intakeDate);
        if (intakeExist) {
            StudentNumberSequence studentNumberSequence = studentNumberSequenceService.findStudentNumberSequence(intakeDate);
            String seqPart = String.format("%05d", studentNumberSequence.getLastSeq());
            student.setNo(datePart + seqPart);
        } else {
            studentNumberSequenceService.addStudentNumberSequence(studentAddDTO.getIntakeDate());
            student.setNo(datePart + "00001");
        }

        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        student.setIsDeleted(false);
        Integer addStudentRowsAffected = studentMapper.insertStudent(student);

        if (addStudentRowsAffected == 0) {
            log.warn("Failed to add student:{}", student);
            throw new BusinessException(ErrorCodeEnum.STUDENT_ADD_FAILED);
        }

        return Boolean.TRUE;
    }

    @Override
    public StudentFindByIdResponseDTO findStudentById(Integer id) {

        StudentFindByIdResponseDTO studentFindByIdResponseDTO = studentMapper.selectStudentById(id);
        if (studentFindByIdResponseDTO == null) {
            log.warn("Student with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.STUDENT_NOT_FOUND);
        }
        return studentFindByIdResponseDTO;
    }

    @Override
    public Boolean deleteStudentByIds(List<Integer> ids) {
        Integer deleteStudentRowsAffected = studentMapper.deleteStudentByIds(ids);
        if (deleteStudentRowsAffected == 0) {
            log.warn("Failed to delete student with ids:{}", ids);
            throw new BusinessException(ErrorCodeEnum.STUDENT_DELETE_FAILED);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateStudent(StudentUpdateDTO studentUpdateDTO) {
        Student student = modelMapper.map(studentUpdateDTO, Student.class);
        student.setUpdateTime(LocalDateTime.now());
        Integer updateStudentRowsAffected = studentMapper.updateStudent(student);

        if (updateStudentRowsAffected == 0) {
            log.warn("Failed to update student:{}", student);
            throw new BusinessException(ErrorCodeEnum.STUDENT_UPDATE_FAILED);
        }

        return Boolean.TRUE;
    }


}