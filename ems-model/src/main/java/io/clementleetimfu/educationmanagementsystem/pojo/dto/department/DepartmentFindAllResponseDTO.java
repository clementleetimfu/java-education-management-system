package io.clementleetimfu.educationmanagementsystem.pojo.dto.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentFindAllResponseDTO {

    private Integer id;

    private String name;
    
    private LocalDateTime updateTime;
}