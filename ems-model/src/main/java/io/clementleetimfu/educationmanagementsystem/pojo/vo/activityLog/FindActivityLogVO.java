package io.clementleetimfu.educationmanagementsystem.pojo.vo.activityLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindActivityLogVO {
    private String operator;
    private LocalDateTime operateTime;
    private String className;
    private String methodName;
    private Long duration;
    private String methodParams;
    private String returnValue;
}
