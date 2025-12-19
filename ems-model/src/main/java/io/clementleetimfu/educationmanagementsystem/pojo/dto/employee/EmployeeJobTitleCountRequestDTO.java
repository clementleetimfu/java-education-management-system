package io.clementleetimfu.educationmanagementsystem.pojo.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeJobTitleCountRequestDTO {
    private List<String> jobTitleList;
    private List<Integer> jobTitleCountList;
}
