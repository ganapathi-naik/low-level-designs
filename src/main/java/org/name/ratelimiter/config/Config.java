package org.name.ratelimiter.config;

import lombok.Getter;

@Getter
public class Config {
    private final int timeWindowInSeconds;
    private final int bucketCapacity;

    public Config(int timeWindowInSeconds, int bucketCapacity) {
        this.timeWindowInSeconds = timeWindowInSeconds;
        this.bucketCapacity = bucketCapacity;
    }
}
