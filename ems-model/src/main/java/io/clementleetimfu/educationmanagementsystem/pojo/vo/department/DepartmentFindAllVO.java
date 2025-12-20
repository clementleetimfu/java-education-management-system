package io.clementleetimfu.educationmanagementsystem.pojo.vo.department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentFindAllVO {

    private Integer id;

    private String name;
    
    private LocalDateTime updateTime;
}