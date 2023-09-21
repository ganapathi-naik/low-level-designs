package org.name.ratelimiter;
public interface RateLimiter {
    boolean isRequestAllowed(String key);
}
