package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordRequestDTO;
import io.clementleetimfu.educationmanagementsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return Result.success(authService.login(loginRequestDTO));
    }

    @PostMapping("update-password")
    public Result<Boolean> updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO) {
        return Result.success(authService.updatePassword(updatePasswordRequestDTO));
    }

}