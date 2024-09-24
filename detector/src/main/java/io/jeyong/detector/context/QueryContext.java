package io.jeyong.detector.context;

import java.util.HashMap;
import java.util.Map;

public final class QueryContext {

    private final Map<String, Long> queryCounts = new HashMap<>();

    QueryContext() {
    }

    void incrementQueryCount(final String query) {
        queryCounts.put(query, queryCounts.getOrDefault(query, 0L) + 1);
    }

    public Map<String, Long> getQueryCounts() {
        return Map.copyOf(queryCounts);
    }
}
