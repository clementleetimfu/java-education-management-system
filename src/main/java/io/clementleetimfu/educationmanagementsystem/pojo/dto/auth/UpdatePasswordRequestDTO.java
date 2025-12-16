package io.clementleetimfu.educationmanagementsystem.pojo.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequestDTO {
    private Integer id;
    private String password;
}