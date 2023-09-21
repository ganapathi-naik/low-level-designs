package org.name;

import org.name.ratelimiter.RateLimiter;
import org.name.ratelimiter.slidingwindow.SlidingWindowRateLimiter;

public class Client {

    private final RateLimiter rateLimiter;

    public Client() {
        rateLimiter = new SlidingWindowRateLimiter();
    }

    public boolean invoke(String key) {
        boolean requestAllowed = rateLimiter.isRequestAllowed(key);
        if (requestAllowed) {
            System.out.println(Thread.currentThread().getName() + " - is allowed to invoke the API");
        } else {
            System.out.println(Thread.currentThread().getName() + " - too many requests, please try after sometime!");
        }
        return requestAllowed;
    }
}
