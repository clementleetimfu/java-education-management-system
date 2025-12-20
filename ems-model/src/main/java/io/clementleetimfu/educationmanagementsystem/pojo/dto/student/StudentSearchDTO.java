package io.clementleetimfu.educationmanagementsystem.pojo.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentSearchDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    private Integer educationLevel;
    private Integer clazzId;
}