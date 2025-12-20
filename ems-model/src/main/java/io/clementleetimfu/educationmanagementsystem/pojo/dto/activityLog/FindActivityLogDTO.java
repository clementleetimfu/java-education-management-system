package io.clementleetimfu.educationmanagementsystem.pojo.dto.activityLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindActivityLogDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
}
