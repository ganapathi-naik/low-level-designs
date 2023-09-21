package org.name.ratelimiter.slidingwindow;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.name.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SlidingWindowRateLimiterTest {

    private final int NUMBER_OF_THREADS = 20;

    private Client client;
    private ExecutorService executorService;

    @Before
    public void testSetup() {
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        client = new Client();
    }

    @Test
    public void testApplicationAccessShouldBlockOverwhelmingRequests() throws InterruptedException, ExecutionException {
        List<Future<Boolean>> futures = new ArrayList<>();
        String key = "id-1";
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Thread.sleep(10);
            futures.add(executorService.submit(() -> client.invoke(key)));
        }
        int count = 0;
        for (Future<Boolean> future : futures) {
            if (future.get()) {
                count++;
            }
        }
        Assert.assertEquals(10, count);
    }

    @After
    public void tearDown() {
        executorService.shutdown();
    }
}
