package tests.environment.workbench.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import io.jeyong.nplus1detector.test.exception.NPlusOneQueryException;
import io.jeyong.nplus1detector.test.exception.context.ExceptionContext;
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
        ExceptionContext exceptionContext = new ExceptionContext();
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            int threadIndex = i;
            executorService.submit(() -> {
                try {
                    NPlusOneQueryException exception = new NPlusOneQueryException("testQuery", 1L);
                    exceptionContext.saveException(exception);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        executorService.shutdown();

        // then
        NPlusOneQueryException primaryException = exceptionContext.getContext().get();
        assertThat(primaryException.getSuppressed()).hasSize(numberOfThreads - 1);
    }
}
