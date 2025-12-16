package io.clementleetimfu.educationmanagementsystem.utils.bcrypt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BcryptUtil {

    @Value("${auth.bcrypt.pepper}")
    private String pepper;

    private static final int STRENGTH = 10;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(STRENGTH);

    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword + pepper);
    }

    public boolean verify(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword + pepper, hashedPassword);
    }

}