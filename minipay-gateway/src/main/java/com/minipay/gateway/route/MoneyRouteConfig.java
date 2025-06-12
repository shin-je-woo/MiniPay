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
public class MoneyRouteConfig {

    @Value("${minipay.money.url}")
    private String moneyServiceUrl;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public RouteLocator moneyRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/money/**")
                        .filters(f -> f
                                .rewritePath("/money/(?<segment>.*)", "/${segment}")
                                .filter(tokenAuthenticationFilter.apply(new TokenAuthenticationFilter.Config()))
                        )
                        .uri(moneyServiceUrl)
                )
                .build();
    }
}
