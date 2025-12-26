package io.clementleetimfu.educationmanagementsystem.aop;

import io.clementleetimfu.educationmanagementsystem.pojo.entity.ActivityLog;
import io.clementleetimfu.educationmanagementsystem.service.ActivityLogService;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentEmployee;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class ActivityLogAspect {

    @Autowired
    private ActivityLogService activityLogService;

    @Around("@annotation(io.clementleetimfu.educationmanagementsystem.annotation.AddActivityLog)")
    public Object addActivityLog(ProceedingJoinPoint pjp) throws Throwable {

        long duration = 0;
        Object result = null;
        try {
            long startTime = System.currentTimeMillis();

            result = pjp.proceed();

            long endTime = System.currentTimeMillis();
            duration = endTime - startTime;

        } finally {
            ActivityLog activityLog = new ActivityLog();
            activityLog.setOperateEmpId(CurrentEmployee.get());
            activityLog.setOperateTime(LocalDateTime.now());
            activityLog.setClassName(pjp.getTarget().getClass().getName());
            activityLog.setMethodName(pjp.getSignature().getName());
            activityLog.setMethodParams(Arrays.toString(pjp.getArgs()));
            activityLog.setReturnValue(result != null ? result.toString() : "void");
            activityLog.setDuration(duration);
            activityLog.setCreateTime(LocalDateTime.now());
            activityLog.setUpdateTime(LocalDateTime.now());
            activityLog.setIsDeleted(Boolean.FALSE);
            activityLogService.addActivityLog(activityLog);
        }
        return result;
    }
}
