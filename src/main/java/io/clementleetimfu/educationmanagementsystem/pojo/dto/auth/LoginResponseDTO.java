package io.clementleetimfu.educationmanagementsystem.pojo.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String token;
    private Boolean isFirstLogged;
}