package io.clementleetimfu.educationmanagementsystem.service;

import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordRequestDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    Boolean updatePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO);
}
