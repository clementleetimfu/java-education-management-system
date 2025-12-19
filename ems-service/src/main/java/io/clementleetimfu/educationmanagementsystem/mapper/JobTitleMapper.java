package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.jobTitle.JobTitleFindAllDTO;

import java.util.List;

public interface JobTitleMapper {
    List<JobTitleFindAllDTO> selectAllJobTitle();
}
