package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.auth.LoginVO;

public interface AuthService {
    LoginVO login(LoginDTO loginDTO);

    Boolean updatePassword(UpdatePasswordDTO updatePasswordDTO);

    void logout(String authHeader);
}
