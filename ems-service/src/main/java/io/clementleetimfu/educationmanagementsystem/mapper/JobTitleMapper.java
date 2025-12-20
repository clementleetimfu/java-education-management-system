package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.jobTitle.JobTitleFindAllVO;

import java.util.List;

public interface JobTitleMapper {
    List<JobTitleFindAllVO> selectAllJobTitle();
}
