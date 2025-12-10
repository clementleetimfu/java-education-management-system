package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.WorkExperience;

import java.util.List;

public interface WorkExperienceMapper {
    Integer insertWorkExperienceByBatch(List<WorkExperience> workExperienceList);

    Integer deleteWorkExperienceByEmpIds(List<Integer> ids);

    Long selectWorkExperienceCountByEmpIds(List<Integer> ids);
}