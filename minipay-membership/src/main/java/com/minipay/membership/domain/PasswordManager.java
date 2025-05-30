package com.minipay.membership.domain;

public interface PasswordManager {
    String hash(String rawPassword);
    boolean matches(String rawPassword, String hashedPassword);
}
