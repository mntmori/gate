package com.artwork.mori.gate.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateRoutes {

    private final String TARGET_TEST_SERVICE = "http://localhost:8081";

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("testRoute", r -> r.path("/test")
                        .uri(TARGET_TEST_SERVICE))
                .build();
    }

}
