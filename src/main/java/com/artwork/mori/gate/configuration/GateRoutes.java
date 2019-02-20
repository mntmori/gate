package com.artwork.mori.gate.configuration;

import com.artwork.mori.gate.filters.ThrottleGatewayFilter;
import org.isomorphism.util.TokenBucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateRoutes {

    private final String SELF = "http://localhost:8080";
    private final String TARGET_TEST_SERVICE = "http://localhost:8081";

    @Autowired
    private TokenBucket fixedIntervalTokenBucket;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("testRoute", r -> r.path("/test")
                        .uri(TARGET_TEST_SERVICE))

                .route("resourceRoute", r -> r.path("/resource")
                        .uri(TARGET_TEST_SERVICE))

                .route("throttledResource", r -> r.path("/throttled")
                        .filters(f -> f.filter(
                                new ThrottleGatewayFilter()
                                        .setTokenBucket(fixedIntervalTokenBucket)))
                        .uri(SELF))

                .build();
    }

}
