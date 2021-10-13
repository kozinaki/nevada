package net.kozinaki.concurrent.ratelimiter;

import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://www.alibabacloud.com/blog/detailed-explanation-of-guava-ratelimiters-throttling-mechanism_594820
 */
@SuppressWarnings("UnstableApiUsage")
public class App {

  public static void main(String[] args) {
    int expectedAcquiredPermits = 2;
    int expectedNotAcquiredPermits = 38;
    RateLimiter rateLimiter = RateLimiter.create(2);
    CountDownLatch countDownLatch = new CountDownLatch(1);
    AtomicBoolean isStopped = new AtomicBoolean(false);
    AtomicInteger actualAcquiredPermits = new AtomicInteger(0);
    AtomicInteger actualNotAcquiredPermits = new AtomicInteger(0);
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    for (int i = 0; i < 4; i++) {
      executorService.execute(getTaskAcquirePermit(rateLimiter, countDownLatch, actualAcquiredPermits, actualNotAcquiredPermits, isStopped));
    }
    long start = System.currentTimeMillis();
    countDownLatch.countDown();
    while (System.currentTimeMillis() - start < 1000) {}
    isStopped.set(true);
    executorService.shutdownNow();
    assert expectedAcquiredPermits != actualAcquiredPermits.get();
    assert expectedNotAcquiredPermits == actualNotAcquiredPermits.get();
    System.out.println(actualAcquiredPermits.get());
    System.out.println(actualNotAcquiredPermits.get());
  }

  private static Runnable getTaskAcquirePermit(RateLimiter rateLimiter, CountDownLatch countDownLatch,
      AtomicInteger trueCounter, AtomicInteger falseCounter, AtomicBoolean isStopped) {
    return new Runnable() {
      private RateLimiter rateLimiter;
      private CountDownLatch countDownLatch;
      private AtomicInteger trueCounter;
      private AtomicInteger falseCounter;
      private AtomicBoolean isStopped;

      public Runnable init(RateLimiter rateLimiter, CountDownLatch countDownLatch,
          AtomicInteger trueCounter, AtomicInteger falseCounter, AtomicBoolean isStopped) {
        this.rateLimiter = rateLimiter;
        this.countDownLatch = countDownLatch;
        this.trueCounter = trueCounter;
        this.falseCounter = falseCounter;
        this.isStopped = isStopped;
        return this;
      }

      @Override
      public void run() {
        try {
          countDownLatch.await();
        } catch (InterruptedException ignore) {}
        while (!isStopped.get()) {
          boolean isAcquired = rateLimiter.tryAcquire();
          if (isAcquired) {
            trueCounter.incrementAndGet();
          } else {
            falseCounter.incrementAndGet();
          }
          try {
            Thread.sleep(100);
          } catch (InterruptedException ignored) {
          }
        }
      }
    }.init(rateLimiter, countDownLatch, trueCounter, falseCounter, isStopped);
  }
}
