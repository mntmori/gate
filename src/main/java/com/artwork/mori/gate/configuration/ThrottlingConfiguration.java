package com.artwork.mori.gate.configuration;

import org.isomorphism.util.TokenBucket;
import org.isomorphism.util.TokenBuckets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ThrottlingConfiguration {

    private final int TOKEN_CAPACITY = 1000;
    private final int REFILL_TOKENS = 1;
    private final int REFILL_PERIOD = 200;
    private final TimeUnit REFILL_UNIT = TimeUnit.MILLISECONDS;

    /**
     * FixedIntervalTokenBucket
     * @return
     */
    @Bean("fixedIntervalTokenBucket")
    public TokenBucket fixedIntervalTokenBucket() {
        return TokenBuckets
                .builder()
                .withCapacity(TOKEN_CAPACITY)
                .withFixedIntervalRefillStrategy(REFILL_TOKENS, REFILL_PERIOD, REFILL_UNIT)
                .withInitialTokens(REFILL_TOKENS)
                .build();
    }

    /**
     * BusyWaitTokenBucket
     * @return
     */
    @Bean("busyWaitTokenBucket")
    public TokenBucket busyWaitTokenBucket() {
        return TokenBuckets
                .builder()
                .withCapacity(TOKEN_CAPACITY)
                .withFixedIntervalRefillStrategy(REFILL_TOKENS, REFILL_PERIOD, REFILL_UNIT)
                .withBusyWaitSleepStrategy()
                .withInitialTokens(REFILL_TOKENS)
                .build();
    }

    /**
     * YieldSleepTokenBucket
     * @return
     */
    @Bean("yieldSleepTokenBucket")
    public TokenBucket yieldSleepTokenBucket() {
        return TokenBuckets
                .builder()
                .withCapacity(TOKEN_CAPACITY)
                .withFixedIntervalRefillStrategy(REFILL_TOKENS, REFILL_PERIOD, REFILL_UNIT)
                .withYieldingSleepStrategy()
                .withInitialTokens(REFILL_TOKENS)
                .build();
    }

}
