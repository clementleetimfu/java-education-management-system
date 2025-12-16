package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginResponseDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordRequestDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.entity.Employee;
import io.clementleetimfu.educationmanagementsystem.service.AuthService;
import io.clementleetimfu.educationmanagementsystem.utils.bcrypt.BcryptUtil;
import io.clementleetimfu.educationmanagementsystem.utils.jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BcryptUtil bcryptUtil;

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

        LoginResponseDTO loginResponseDTO = employeeMapper.selectEmployeeByUsername(loginRequestDTO.getUsername());

        if (loginResponseDTO == null) {
            log.warn("User with username:{} not found", loginRequestDTO.getUsername());
            throw new BusinessException(ErrorCodeEnum.INVALID_CREDENTIALS);
        }

        String hashedPassword = loginResponseDTO.getPassword();
        boolean verifyResult = bcryptUtil.verify(loginRequestDTO.getPassword(), hashedPassword);

        if (!verifyResult) {
            log.warn("Invalid password");
            throw new BusinessException(ErrorCodeEnum.INVALID_CREDENTIALS);
        }

        if (!loginResponseDTO.getIsFirstLogged()) {
            return loginResponseDTO;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginResponseDTO.getId());
        claims.put("username", loginRequestDTO.getUsername());

        loginResponseDTO.setToken(jwtUtil.generateToken(claims));
        return loginResponseDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updatePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO) {

        String hashedPassword = bcryptUtil.hash(updatePasswordRequestDTO.getPassword());

        Employee employee = new Employee();
        employee.setId(updatePasswordRequestDTO.getId());
        employee.setPassword(hashedPassword);
        employee.setIsFirstLogged(Boolean.TRUE);
        employee.setUpdateTime(LocalDateTime.now());

        Integer rowsAffected = employeeMapper.updateEmployee(employee);
        if (rowsAffected == 0) {
            log.warn("Failed to update employee password:{}", employee);
            throw new BusinessException(ErrorCodeEnum.EMPLOYEE_UPDATE_FAILED);
        }

        return Boolean.TRUE;
    }

}
