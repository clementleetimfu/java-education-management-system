package io.clementleetimfu.educationmanagementsystem.utils.bcrypt;

import io.clementleetimfu.educationmanagementsystem.constants.ErrorCodeEnum;
import io.clementleetimfu.educationmanagementsystem.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BcryptUtil {

    private String pepper;

    public BcryptUtil(@Value("${auth.bcrypt.pepper}") String pepper) {
        this.pepper = pepper;
    }

    private static final int STRENGTH = 10;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(STRENGTH);

    public String hash(String rawPassword) {
        if (rawPassword == null) {
            throw new BusinessException(ErrorCodeEnum.RAW_PASSWORD_IS_NULL);
        }
        return passwordEncoder.encode(rawPassword + pepper);
    }

    public boolean verify(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null) {
            return false;
        }
        return passwordEncoder.matches(rawPassword + pepper, hashedPassword);
    }

}