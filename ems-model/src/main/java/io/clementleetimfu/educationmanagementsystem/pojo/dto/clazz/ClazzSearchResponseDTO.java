package io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzSearchResponseDTO {
    private Integer id;
    private String clazzName;
    private String teacherName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime updateTime;
}