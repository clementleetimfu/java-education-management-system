package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.LoginRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.employee.LoginResponseDTO;
import io.clementleetimfu.educationmanagementsystem.service.LoginService;
import io.clementleetimfu.educationmanagementsystem.utils.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO loginResponseDTO = employeeMapper.selectEmployeeByUsernameAndPassWord(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());

        if (loginResponseDTO == null) {
            log.warn("User with username:{} not found", loginRequestDTO.getUsername());
            throw new BusinessException(ErrorCodeEnum.INVALID_CREDENTIALS);
        }
        loginResponseDTO.setToken(jwtUtil.generateToken(loginResponseDTO.getId()));
        return loginResponseDTO;
    }

}
