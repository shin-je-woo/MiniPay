package com.minipay.remittance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.minipay.remittance", "com.minipay.common"})
public class RemittanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemittanceApplication.class, args);
    }
}
