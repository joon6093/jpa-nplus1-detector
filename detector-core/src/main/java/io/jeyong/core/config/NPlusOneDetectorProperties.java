package io.jeyong.core.config;

import java.util.List;
import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

// @formatter:off
/**
 * <p>
 * Configuration properties for the N+1 Detector in JPA (Hibernate).
 * </p>
 *
 * <p>
 * To enable the N+1 Detector and customize its behavior, users
 * can configure the following properties in the application configuration
 * (e.g., application.yml or application.properties):
 * </p>
 *
 * <ul>
 *     <li><b>spring.jpa.properties.hibernate.detector.enabled:</b> Set whether the detector is enabled or disabled (default: false).</li>
 *     <li><b>spring.jpa.properties.hibernate.detector.threshold:</b> Set the threshold for the number of query executions to detect N+1 queries (default: 2).</li>
 *     <li><b>spring.jpa.properties.hibernate.detector.exclude:</b> Set the list of specific queries to be excluded from N+1 queries (optional).</li>
 *     <li><b>spring.jpa.properties.hibernate.detector.level:</b> Set the log level for detected N+1 queries (default: WARN).</li>
 * </ul>
 *
 * Example configuration (YAML):
 * <pre>{@code
 * spring:
 *   jpa:
 *     properties:
 *       hibernate:
 *         detector:
 *           enabled: true
 *           threshold: 2
 *           exclude:
 *             - select ... from table1 where ...
 *             - select ... from table2 where ...
 *           level: warn
 * }
 * </pre>
 *
 * Example configuration (Properties):
 * <pre>{@code
 * spring.jpa.properties.hibernate.detector.enabled=true
 * spring.jpa.properties.hibernate.detector.threshold=2
 * spring.jpa.properties.hibernate.detector.exclude[0]=select ... from table1 where ...
 * spring.jpa.properties.hibernate.detector.exclude[1]=select ... from table2 where ...
 * spring.jpa.properties.hibernate.detector.level=warn
 * }
 * </pre>
 *
 * <p>
 * The N+1 Detector is disabled by default to avoid potential performance overhead in production environments.
 * It is recommended to enable it only in development or testing environments.
 * </p>
 *
 * @author jeyong
 * @since 1.0
 * @see NPlusOneDetectorBaseConfiguration
 * @see NPlusOneDetectorLoggingConfiguration
 */
// @formatter:on
@ConfigurationProperties(prefix = "spring.jpa.properties.hibernate.detector")
public class NPlusOneDetectorProperties {

    private boolean enabled = false;

    private int threshold = 2;

    private List<String> exclude = List.of();

    private Level level = Level.WARN;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(final int threshold) {
        this.threshold = threshold;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public void setExclude(final List<String> exclude) {
        this.exclude = exclude;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }
}
