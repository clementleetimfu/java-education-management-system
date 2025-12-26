package io.clementleetimfu.educationmanagementsystem.annotation;

import io.clementleetimfu.educationmanagementsystem.constants.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    RoleEnum role();
}