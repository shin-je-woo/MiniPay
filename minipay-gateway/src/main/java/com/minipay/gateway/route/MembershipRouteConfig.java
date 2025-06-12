package com.minipay.gateway.route;

import com.minipay.gateway.filter.TokenAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class MembershipRouteConfig {

    @Value("${minipay.membership.url}")
    private String membershipServiceUrl;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public RouteLocator membershipRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/membership/auth/**", "/membership/membership/sign-up")
                        .and().method(HttpMethod.POST)
                        .filters(f -> f
                                .rewritePath("/membership/(?<segment>.*)", "/${segment}")
                        )
                        .uri(membershipServiceUrl)
                )
                .route(r -> r
                        .path("/membership/**")
                        .filters(f -> f
                                .rewritePath("/membership/(?<segment>.*)", "/${segment}")
                                .filter(tokenAuthenticationFilter.apply(new TokenAuthenticationFilter.Config()))
                        )
                        .uri(membershipServiceUrl)
                )
                .build();
    }
}
