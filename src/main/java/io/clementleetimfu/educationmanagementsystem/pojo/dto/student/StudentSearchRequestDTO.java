package io.clementleetimfu.educationmanagementsystem.pojo.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSearchRequestDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    private Integer highestEducation;
    private Integer clazzId;
}