package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.WorkExperience;

import java.util.List;

public interface WorkExperienceMapper {
    Integer addWorkExperienceByBatch(List<WorkExperience> workExperienceList);

    Integer deleteWorkExperienceByEmpIds(List<Integer> ids);
}
