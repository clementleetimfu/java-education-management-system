package io.clementleetimfu.educationmanagementsystem.controller;

import io.clementleetimfu.educationmanagementsystem.pojo.Result;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.LoginRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.LoginResponseDTO;
import io.clementleetimfu.educationmanagementsystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return Result.success(loginService.login(loginRequestDTO));
    }

}