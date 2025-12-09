package io.clementleetimfu.educationmanagementsystem.pojo.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFindByIdResponseDTO {
    private Integer id;
    private String name;
    private Integer gender;
    private LocalDate birthdate;
    private String phone;
    private String email;
    private String address;
    private Integer highestEducation;
    private LocalDate graduationDate;
    private Integer clazzId;
    private LocalDate intakeDate;
}
