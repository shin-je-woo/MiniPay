package com.minipay.gateway.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryRouteConfig {

    @Value("${minipay.query.url}")
    private String queryServiceUrl;

    @Bean
    public RouteLocator queryRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/query/**")
                        .filters(f -> f.rewritePath("/query/(?<segment>.*)", "/${segment}"))
                        .uri(queryServiceUrl)
                )
                .build();
    }
}
