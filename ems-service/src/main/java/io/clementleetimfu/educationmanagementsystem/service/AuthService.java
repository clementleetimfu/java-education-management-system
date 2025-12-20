package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.auth.LoginVO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordDTO;

public interface AuthService {
    LoginVO login(LoginDTO loginDTO);

    Boolean updatePassword(UpdatePasswordDTO updatePasswordDTO);
}
