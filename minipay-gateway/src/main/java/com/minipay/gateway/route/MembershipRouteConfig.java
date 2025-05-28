package com.minipay.gateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MembershipRouteConfig {

    @Value("${minipay.membership.url}")
    private String membershipServiceUrl;

    @Bean
    public RouteLocator membershipRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/membership/**")
                        .filters(f -> f.rewritePath("/membership/(?<segment>.*)", "/${segment}"))
                        .uri(membershipServiceUrl)
                )
                .build();
    }
}
