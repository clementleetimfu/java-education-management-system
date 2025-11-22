package io.clementleetimfu.educationmanagementsystem.pojo.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceAddDTO {
    private String companyName;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
}
