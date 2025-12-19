package io.clementleetimfu.educationmanagementsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MapperScan("io.clementleetimfu.educationmanagementsystem.mapper")
@SpringBootApplication
@ServletComponentScan
public class EducationManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(EducationManagementSystemApplication.class, args);
    }

}
