package com.minipay.gateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRouteConfig {

    @Value("${minipay.payment.url}")
    private String paymentServiceUrl;

    @Bean
    public RouteLocator paymentRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/payment/**")
                        .filters(f -> f.rewritePath("/payment/(?<segment>.*)", "/${segment}"))
                        .uri(paymentServiceUrl)
                )
                .build();
    }
}
