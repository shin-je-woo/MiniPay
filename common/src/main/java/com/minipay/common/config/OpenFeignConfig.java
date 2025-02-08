package com.minipay.common.config;

import feign.Request;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "com.minipay")
public class OpenFeignConfig {

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 20000;

    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
                CONNECT_TIMEOUT, TimeUnit.MILLISECONDS,
                READ_TIMEOUT, TimeUnit.MILLISECONDS,
                false
        );
    }
}
