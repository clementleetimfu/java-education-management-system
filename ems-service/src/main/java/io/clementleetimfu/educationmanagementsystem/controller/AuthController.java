package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.vo.result.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.auth.LoginVO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordDTO;
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
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) {
        return Result.success(authService.login(loginDTO));
    }

    @PostMapping("update-password")
    public Result<Boolean> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        return Result.success(authService.updatePassword(updatePasswordDTO));
    }

}