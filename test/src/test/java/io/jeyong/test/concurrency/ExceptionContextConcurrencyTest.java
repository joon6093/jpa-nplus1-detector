package io.jeyong.test.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.exception.NPlusOneQueryException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExceptionContextConcurrencyTest {

    @Test
    @DisplayName("ExceptionContext 동시성 테스트")
    void testSaveExceptionConcurrency() throws InterruptedException {
        // given
        final ExceptionContext exceptionContext = new ExceptionContext();
        final int numberOfThreads = 10;
        final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        final CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadIndex = i;
            executorService.submit(() -> {
                try {
                    final NPlusOneQueryException exception = new NPlusOneQueryException("Exception-" + threadIndex);
                    exceptionContext.saveException(exception);
                } catch (final Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        // then
        final NPlusOneQueryException primaryException = exceptionContext.getContext().get();
        assertThat(primaryException.getSuppressed()).hasSize(numberOfThreads - 1);
    }
}
