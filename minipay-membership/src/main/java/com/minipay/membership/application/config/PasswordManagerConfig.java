package com.minipay.membership.application.config;

import com.minipay.membership.domain.PasswordManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class PasswordManagerConfig {

    @Bean
    public PasswordManager passwordManager() {
        return new BCryptPasswordManager();
    }

    static class BCryptPasswordManager implements PasswordManager {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

        @Override
        public String hash(String rawPassword) {
            return bCryptPasswordEncoder.encode(rawPassword);
        }

        @Override
        public boolean matches(String rawPassword, String hashedPassword) {
            return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
        }
    }
}
