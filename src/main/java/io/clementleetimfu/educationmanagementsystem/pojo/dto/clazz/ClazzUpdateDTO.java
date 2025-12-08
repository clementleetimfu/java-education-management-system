package io.clementleetimfu.educationmanagementsystem.pojo.dto.clazz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClazzUpdateDTO {
    private Integer id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer teacherId;
    private Integer subject;
}
