package com.minipay.global.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.minipay.global.consumer", "com.minipay.common"})
public class GlobalConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobalConsumerApplication.class, args);
    }
}
