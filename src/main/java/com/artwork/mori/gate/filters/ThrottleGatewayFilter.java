package com.artwork.mori.gate.filters;

import lombok.extern.slf4j.Slf4j;
import org.isomorphism.util.TokenBucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class ThrottleGatewayFilter implements GatewayFilter {

    Logger logger = LoggerFactory.getLogger(ThrottleGatewayFilter.class);

    private TokenBucket tokenBucket;

    public ThrottleGatewayFilter setTokenBucket(TokenBucket tokenBucket) {
        this.tokenBucket = tokenBucket;
        return this;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("[TOKEN STATUS] available tokens: {}", tokenBucket.getNumTokens());
        boolean consumed = tokenBucket.tryConsume();
        if (consumed) {
            logger.info("[TOKEN STATUS] token consumed");
            logger.info("[TOKEN STATUS] available tokens: {}", tokenBucket.getNumTokens());
            return chain.filter(exchange);
        }
        logger.info("[TOKEN STATUS] available tokens: {}", tokenBucket.getNumTokens());
        logger.info("[TOKEN STATUS] token not available, to many requests...");
        exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
        return exchange.getResponse().setComplete();
    }
}
