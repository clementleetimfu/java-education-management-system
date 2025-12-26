package io.clementleetimfu.educationmanagementsystem.pojo.vo.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private Integer id;
    @JsonIgnore
    private String username;
    @JsonIgnore
    private String password;
    private String token;
    private Boolean isFirstLogged;
    private String roleName;
}