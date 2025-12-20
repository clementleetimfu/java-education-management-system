package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.jobTitle.JobTitleFindAllVO;

import java.util.List;

public interface JobTitleService {
    List<JobTitleFindAllVO> findAllJobTitle();
}
