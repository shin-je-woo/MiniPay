package com.minipay.gateway.route;

import com.minipay.gateway.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RemittanceRouteConfig {

    @Value("${minipay.remittance.url}")
    private String remittanceServiceUrl;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public RouteLocator remittanceRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/remittance/**")
                        .filters(f -> f
                                .rewritePath("/remittance/(?<segment>.*)", "/${segment}")
                                .filter(tokenAuthenticationFilter.apply(new TokenAuthenticationFilter.Config()))
                        )
                        .uri(remittanceServiceUrl)
                )
                .build();
    }
}
