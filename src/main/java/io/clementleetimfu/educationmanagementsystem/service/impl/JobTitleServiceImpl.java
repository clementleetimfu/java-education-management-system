package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.JobTitleMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.jobTitle.JobTitleFindAllDTO;
import io.clementleetimfu.educationmanagementsystem.service.JobTitleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class JobTitleServiceImpl implements JobTitleService {

    @Autowired
    private JobTitleMapper jobTitleMapper;

    @Override
    public List<JobTitleFindAllDTO> findAllJobTitle() {

        List<JobTitleFindAllDTO> jobTitleFindAllDTOList = jobTitleMapper.selectAllJobTitle();
        if (jobTitleFindAllDTOList.isEmpty()) {
            log.warn("Job title list is empty");
            throw new BusinessException(ErrorCodeEnum.JOB_TITLE_NOT_FOUND);
        }
        return jobTitleFindAllDTOList;
    }
}
