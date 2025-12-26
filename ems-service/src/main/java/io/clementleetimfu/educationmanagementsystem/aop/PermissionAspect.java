package io.clementleetimfu.educationmanagementsystem.aop;

import io.clementleetimfu.educationmanagementsystem.annotation.Permission;
import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.constants.RoleEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentRole;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class PermissionAspect {

    @Around("@annotation(io.clementleetimfu.educationmanagementsystem.annotation.Permission)")
    public Object checkPermission(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Permission annotation = method.getAnnotation(Permission.class);
        RoleEnum requiredRole = annotation.role();
        RoleEnum currentRole = RoleEnum.valueOf(CurrentRole.get());

        if (!requiredRole.equals(currentRole)) {
            throw new BusinessException(ErrorCodeEnum.PERMISSION_DENIED);
        }

        return pjp.proceed();
    }
}