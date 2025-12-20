package io.clementleetimfu.educationmanagementsystem.service.impl;

import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import io.clementleetimfu.educationmanagementsystem.exception.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.mapper.EmployeeMapper;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.LoginDTO;
import io.clementleetimfu.educationmanagementsystem.pojo.vo.auth.LoginVO;
import io.clementleetimfu.educationmanagementsystem.pojo.dto.auth.UpdatePasswordDTO;
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
    public LoginVO login(LoginDTO loginDTO) {

        LoginVO loginVo = employeeMapper.selectEmployeeByUsername(loginDTO.getUsername());

        if (loginVo == null) {
            log.warn("User with username:{} not found", loginDTO.getUsername());
            throw new BusinessException(ErrorCodeEnum.INVALID_CREDENTIALS);
        }

        String hashedPassword = loginVo.getPassword();
        boolean verifyResult = bcryptUtil.verify(loginDTO.getPassword(), hashedPassword);

        if (!verifyResult) {
            log.warn("Invalid password");
            throw new BusinessException(ErrorCodeEnum.INVALID_CREDENTIALS);
        }

        if (!loginVo.getIsFirstLogged()) {
            return loginVo;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", loginVo.getId());
        claims.put("username", loginDTO.getUsername());

        loginVo.setToken(jwtUtil.generateToken(claims));
        return loginVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updatePassword(UpdatePasswordDTO updatePasswordDTO) {

        String hashedPassword = bcryptUtil.hash(updatePasswordDTO.getPassword());

        Employee employee = new Employee();
        employee.setId(updatePasswordDTO.getId());
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
