package io.clementleetimfu.educationmanagementsystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperience {
    private Integer id;
    private Integer empId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String companyName;
    private String jobTitle;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;
}