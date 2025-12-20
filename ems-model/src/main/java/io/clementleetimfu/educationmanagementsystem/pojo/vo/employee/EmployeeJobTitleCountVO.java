package io.clementleetimfu.educationmanagementsystem.pojo.vo.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeJobTitleCountVO {
    private List<String> jobTitleList;
    private List<Integer> jobTitleCountList;
}
