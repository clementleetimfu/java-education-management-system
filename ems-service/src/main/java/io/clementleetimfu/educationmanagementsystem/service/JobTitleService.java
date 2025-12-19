package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.jobTitle.JobTitleFindAllDTO;

import java.util.List;

public interface JobTitleService {
    List<JobTitleFindAllDTO> findAllJobTitle();
}
