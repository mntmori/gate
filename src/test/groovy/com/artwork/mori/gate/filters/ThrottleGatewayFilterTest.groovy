package com.artwork.mori.gate.filters

import org.isomorphism.util.TokenBucket
import org.isomorphism.util.TokenBuckets
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.web.server.ServerWebExchange
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class ThrottleGatewayFilterTest extends Specification {

    ServerWebExchange serverWebExchange = Mock()
    GatewayFilterChain gatewayFilterChain = Mock()

    def "SetTokenBucket"() {
        given: "Token Bucket and Filter"
          TokenBucket tokenBucket = TokenBuckets
                  .builder()
                  .withCapacity(1)
                  .withFixedIntervalRefillStrategy(1, 1, TimeUnit.HOURS)
                  .build()
          ThrottleGatewayFilter throttleGatewayFilter = new ThrottleGatewayFilter()

        when: "Token Bucket is set"
          throttleGatewayFilter.setTokenBucket(tokenBucket)

        then: "check thant Bucket was set"
          Objects.nonNull(throttleGatewayFilter.getTokenBucket())
    }

    def "Filter"() {
        given: "Token Bucket and Filter"
          TokenBucket tokenBucket = TokenBuckets
                  .builder()
                  .withCapacity(1)
                  .withFixedIntervalRefillStrategy(1, 1, TimeUnit.HOURS)
                  .build()
          ThrottleGatewayFilter throttleGatewayFilter = new ThrottleGatewayFilter()
                  .setTokenBucket(tokenBucket)

        when: "Filter method is called"
          throttleGatewayFilter.filter(serverWebExchange, gatewayFilterChain)

        then: "check that token was consumed"
          tokenBucket.getNumTokens() == 0
    }
}
