package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.SubjectMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.subject.SubjectFindAllDTO;
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
    public List<SubjectFindAllDTO> findAllSubjects() {
        List<SubjectFindAllDTO> subjectFindAllDTOList = subjectMapper.selectAllSubjects();
        if (subjectFindAllDTOList.isEmpty()) {
            log.warn("Subject list is empty");
            throw new BusinessException(ErrorCodeEnum.SUBJECT_NOT_FOUND);
        }
        return subjectFindAllDTOList;
    }

}
