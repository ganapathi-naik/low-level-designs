package org.name.ratelimiter.config;

import lombok.Getter;

@Getter
public class Util {

    public static Config getRateLimiterConfig(String key) {
        if ("id-1".equalsIgnoreCase(key)) {
            return new Config(1, 10);
        } else {
            return new Config(1, 2);
        }
    }
}
