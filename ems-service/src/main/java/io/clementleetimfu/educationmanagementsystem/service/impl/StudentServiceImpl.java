package io.clementleetimfu.educationmanagementsystem.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.StudentMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.PageResult;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.student.*;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Student;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.StudentNumberSequence;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindByIdVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentFindCountByClazzVO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.student.StudentSearchVO;
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
import java.util.Map;

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
    public PageResult<StudentSearchVO> searchStudent(StudentSearchDTO studentSearchDTO) {

        PageHelper.startPage(studentSearchDTO.getPage(), studentSearchDTO.getPageSize());

        List<StudentSearchVO> studentSearchVOList = studentMapper.searchStudent(studentSearchDTO);

        if (studentSearchVOList.isEmpty()) {
            log.warn("Student list is empty");
            throw new BusinessException(ErrorCodeEnum.STUDENT_NOT_FOUND);
        }

        Page<StudentSearchVO> page = (Page<StudentSearchVO>) studentSearchVOList;
        return new PageResult<>(page.getTotal(), page.getResult());

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addStudent(StudentAddDTO studentAddDTO) {

        Student student = modelMapper.map(studentAddDTO, Student.class);

        LocalDate intakeDate = studentAddDTO.getIntakeDate();
        String datePart = intakeDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        StudentNumberSequence studentNumberSequence = studentNumberSequenceService.findStudentNumberSequence(intakeDate);
        if (studentNumberSequence != null) {
            Integer latestSeq = studentNumberSequence.getLastSeq() + 1;
            String seqPart = String.format("%05d", latestSeq); // fixed 5 character padding
            student.setNo(datePart + seqPart);
            studentNumberSequenceService.updateStudentNumberSequence(intakeDate, latestSeq);
            
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public StudentFindByIdVO findStudentById(Integer id) {

        StudentFindByIdVO studentFindByIdVO = studentMapper.selectStudentById(id);
        if (studentFindByIdVO == null) {
            log.warn("Student with id:{} not found", id);
            throw new BusinessException(ErrorCodeEnum.STUDENT_NOT_FOUND);
        }

        return studentFindByIdVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteStudentByIds(List<Integer> ids) {

        Integer deleteStudentRowsAffected = studentMapper.deleteStudentByIds(ids);
        if (deleteStudentRowsAffected == 0) {
            log.warn("Failed to delete student with ids:{}", ids);
            throw new BusinessException(ErrorCodeEnum.STUDENT_DELETE_FAILED);
        }

        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
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

    @Override
    public List<Map<String, Object>> findStudentEduLevelCount() {

        List<Map<String, Object>> studentEduLevelCountList = studentMapper.findStudentEduLevelCount();

        if (studentEduLevelCountList.isEmpty()) {
            log.warn("Student education level count list is empty");
            throw new BusinessException(ErrorCodeEnum.STUDENT_NOT_FOUND);
        }

        return studentEduLevelCountList;
    }

    @Override
    public Boolean isStudentExistsInClazz(Integer clazzId) {
        return studentMapper.selectStudentCountByClazzId(clazzId) > 0;
    }

    @Override
    public StudentFindCountByClazzVO findStudentCountByClazz() {

        List<Map<String, Object>> studentCountByClazzMapList = studentMapper.findStudentCountByClazz();

        log.info("studentCountByClazzMapList:{}", studentCountByClazzMapList);


        if (studentCountByClazzMapList.isEmpty()) {
            log.warn("Student count by clazz list is empty");
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_NOT_FOUND);
        }

        StudentFindCountByClazzVO studentFindCountByClazzVO = new StudentFindCountByClazzVO();
        studentFindCountByClazzVO
                .setClazzNameList(studentCountByClazzMapList.stream().map(map -> (String) map.get("clazzName")).toList());

        studentFindCountByClazzVO
                .setStudentCountList(studentCountByClazzMapList.stream().map(map -> ((Number) map.get("count")).intValue()).toList());

        return studentFindCountByClazzVO;
    }

}