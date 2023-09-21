package org.name.ratelimiter.slidingwindow;

import org.name.ratelimiter.RateLimiter;
import org.name.ratelimiter.config.Config;
import org.name.ratelimiter.config.Util;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final Queue<Long> slidingWindow;

    public SlidingWindowRateLimiter() {
        this.slidingWindow = new ConcurrentLinkedQueue<>();
    }

    @Override
    public boolean isRequestAllowed(String key) {
        long currentTime = System.currentTimeMillis();
        // Fetch rate limiter config for key
        Config rateLimiterConfig = Util.getRateLimiterConfig(key);

        // Remove the expired invocations, make room for new API requests
        removeExpiredRequests(currentTime, rateLimiterConfig);

        //Check the current API request can be accommodated ?
        if (slidingWindow.size() < rateLimiterConfig.getBucketCapacity()) {
            slidingWindow.offer(currentTime);
            return true;
        }
        return false;
    }

    private void removeExpiredRequests(long currentTime, Config rateLimiterConfig) {
        if (slidingWindow.isEmpty()) {
            return;
        }
        long startTime = currentTime - (rateLimiterConfig.getTimeWindowInSeconds() * 1000L);
        long oldestRequestTime = slidingWindow.peek();
        while (oldestRequestTime < startTime) {
            slidingWindow.poll();
            if (slidingWindow.isEmpty()) {
                break;
            }
            oldestRequestTime = slidingWindow.peek();
        }
    }
}
