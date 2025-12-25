package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.SubjectMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.subject.SubjectFindAllVO;
import io.clementleetimfu.educationmanagementsystem.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public List<SubjectFindAllVO> findAllSubjects() {
        List<SubjectFindAllVO> subjectFindAllVOList = subjectMapper.selectAllSubjects();
        if (subjectFindAllVOList.isEmpty()) {
            log.warn("Subject list is empty");
            throw new BusinessException(ErrorCodeEnum.SUBJECT_NOT_FOUND);
        }
        return subjectFindAllVOList;
    }

}
