package io.clementleetimfu.educationmanagementsystem.mapper;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.WorkExperience;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkExperienceMapper {
    Integer addWorkExperienceByBatch(@Param("workExperienceList") List<WorkExperience> workExperienceList);

    Integer deleteWorkExperienceByEmpIds(@Param("ids") List<Integer> ids);

    Long countWorkExperienceByEmpIds(@Param("ids") List<Integer> ids);
}