package io.jeyong.test.performance;

import static org.assertj.core.api.Assertions.assertThat;

import io.jeyong.detector.exception.NPlusOneQueryException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionContextPerformanceTest {

    static class SyncExceptionContext {

        private NPlusOneQueryException primaryException;

        public synchronized void saveException(final NPlusOneQueryException nPlusOneQueryException) {
            if (primaryException != null) {
                primaryException.addSuppressed(nPlusOneQueryException);
            } else {
                primaryException = nPlusOneQueryException;
            }
        }

        public synchronized Optional<NPlusOneQueryException> getContext() {
            return Optional.ofNullable(primaryException);
        }

        public synchronized void clearContext() {
            primaryException = null;
        }
    }

    static class AtomicExceptionContext {

        private final AtomicReference<NPlusOneQueryException> primaryException = new AtomicReference<>();

        public void saveException(final NPlusOneQueryException nPlusOneQueryException) {
            primaryException.updateAndGet(existingException -> {
                if (existingException != null) {
                    existingException.addSuppressed(nPlusOneQueryException);
                    return existingException;
                } else {
                    return nPlusOneQueryException;
                }
            });
        }

        public Optional<NPlusOneQueryException> getContext() {
            return Optional.ofNullable(primaryException.get());
        }

        public void clearContext() {
            primaryException.set(null);
        }
    }

    private static final int NUM_ITERATIONS = 100000;

    @Test
    @DisplayName("Synchronized 성능 테스트")
    void testSynchronizedPerformance() {
        SyncExceptionContext syncExceptionContext = new SyncExceptionContext();

        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            syncExceptionContext.saveException(new NPlusOneQueryException("Exception " + i));
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Synchronized 소요 시간: " + duration + " ns");

        syncExceptionContext.clearContext();
        assertThat(duration).isGreaterThan(0);
    }

    @Test
    @DisplayName("AtomicReference 성능 테스트")
    void testAtomicReferencePerformance() {
        AtomicExceptionContext atomicExceptionContext = new AtomicExceptionContext();

        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ITERATIONS; i++) {
            atomicExceptionContext.saveException(new NPlusOneQueryException("Exception " + i));
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("AtomicReference 소요 시간: " + duration + " ns");

        atomicExceptionContext.clearContext();
        assertThat(duration).isGreaterThan(0);
    }

    private static final int NUM_THREADS = 100;

    @Test
    @DisplayName("Synchronized MultiThread 성능 테스트")
    void testSynchronizedMultiThreadPerformance() throws InterruptedException {
        SyncExceptionContext syncExceptionContext = new SyncExceptionContext();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch countDownLatch = new CountDownLatch(NUM_THREADS);

        long startTime = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadIndex = i;
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < NUM_ITERATIONS / NUM_THREADS; j++) {
                        syncExceptionContext.saveException(
                                new NPlusOneQueryException("Exception-" + threadIndex + "-" + j));
                    }
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Synchronized MultiThread 소요 시간: " + duration + " ns");

        syncExceptionContext.clearContext();
        assertThat(duration).isGreaterThan(0);
    }

    @Test
    @DisplayName("AtomicReference MultiThread 성능 테스트")
    void testAtomicReferenceMultiThreadPerformance() throws InterruptedException {
        AtomicExceptionContext atomicExceptionContext = new AtomicExceptionContext();
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        CountDownLatch countDownLatch = new CountDownLatch(NUM_THREADS);

        long startTime = System.nanoTime();

        for (int i = 0; i < NUM_THREADS; i++) {
            final int threadIndex = i;
            executorService.submit(() -> {
                try {
                    for (int j = 0; j < NUM_ITERATIONS / NUM_THREADS; j++) {
                        atomicExceptionContext.saveException(
                                new NPlusOneQueryException("Exception-" + threadIndex + "-" + j));
                    }
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("AtomicReference MultiThread 소요 시간: " + duration + " ns");

        atomicExceptionContext.clearContext();
        assertThat(duration).isGreaterThan(0);
    }
}
