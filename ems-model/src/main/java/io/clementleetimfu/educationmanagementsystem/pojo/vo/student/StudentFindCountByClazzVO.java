package io.clementleetimfu.educationmanagementsystem.pojo.vo.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFindCountByClazzVO {
    private List<String> clazzNameList;
    private List<Integer> studentCountList;
}
