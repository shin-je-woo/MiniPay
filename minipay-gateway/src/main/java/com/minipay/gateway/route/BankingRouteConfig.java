package com.minipay.gateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankingRouteConfig {

    @Value("${minipay.banking.url}")
    private String bankingServiceUrl;

    @Bean
    public RouteLocator bankingRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/banking/**")
                        .filters(f -> f.rewritePath("/banking/(?<segment>.*)", "/${segment}"))
                        .uri(bankingServiceUrl)
                )
                .build();
    }
}
