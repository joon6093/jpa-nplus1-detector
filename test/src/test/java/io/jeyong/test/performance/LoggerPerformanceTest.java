package io.jeyong.test.performance;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerPerformanceTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggerPerformanceTest.class);
    private static final int queryThreshold = 2;
    private static final int DATA_SIZE = 5;
    private QueryContext queryContext;

    static class QueryContext {

        private final Map<String, Long> queryCounts = new HashMap<>();

        public void initTestData(int size) {
            for (int i = 0; i < size; i++) {
                queryCounts.put("query" + i, (long) (Math.random() * 10));
            }
        }

        public Map<String, Long> getQueryCounts() {
            return queryCounts;
        }

        public void clear() {
            queryCounts.clear();
        }
    }

    @BeforeEach
    public void setUp() {
        queryContext = new QueryContext();
        queryContext.initTestData(DATA_SIZE);
    }

    @Test
    @DisplayName("forEach 성능 테스트")
    public void testForEachPerformance() {
        long startTime = System.nanoTime();
        queryContext.getQueryCounts().forEach((query, count) -> {
            if (count >= queryThreshold) {
                logger.warn("N+1 issue detected: '{}' was executed {} times.", query, count);
            }
        });
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("forEach 소요 시간: " + duration + " ns");

        queryContext.clear();
        assertThat(duration).isGreaterThan(0);
    }

    @Test
    @DisplayName("Stream 성능 테스트")
    public void testStreamPerformance() {
        long startTime = System.nanoTime();
        queryContext.getQueryCounts().entrySet().stream().forEach(entry -> {
            String query = entry.getKey();
            Long count = entry.getValue();
            if (count >= queryThreshold) {
                logger.warn("N+1 issue detected: '{}' was executed {} times.", query, count);
            }
        });
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Stream 소요 시간: " + duration + " ns");

        queryContext.clear();
        assertThat(duration).isGreaterThan(0);
    }

    @Test
    @DisplayName("ParallelStream 성능 테스트")
    public void testParallelStreamPerformance() {
        long startTime = System.nanoTime();
        queryContext.getQueryCounts().entrySet().parallelStream().forEach(entry -> {
            String query = entry.getKey();
            Long count = entry.getValue();
            if (count >= queryThreshold) {
                logger.warn("N+1 issue detected: '{}' was executed {} times.", query, count);
            }
        });
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("ParallelStream 소요 시간: " + duration + " ns");

        queryContext.clear();
        assertThat(duration).isGreaterThan(0);
    }
}
