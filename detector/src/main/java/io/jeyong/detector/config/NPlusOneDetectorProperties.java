package io.jeyong.detector.config;

import org.slf4j.event.Level;
import org.springframework.boot.context.properties.ConfigurationProperties;

// @formatter:off
/**
 * <p>
 * Configuration properties for the N+1 Query Detector in JPA (Hibernate).
 * </p>
 *
 * <p>
 * To enable the N+1 query detector and customize its behavior, users
 * can configure the following properties in the application configuration
 * (e.g., application.yml or application.properties):
 * </p>
 *
 * <ul>
 *     <li><b>spring.jpa.properties.hibernate.detector.enabled:</b> Enable or disable the detector (default: false).</li>
 *     <li><b>spring.jpa.properties.hibernate.detector.threshold:</b> Set the threshold for the number of query executions to detect an N+1 issue (default: 2).</li>
 *     <li><b>spring.jpa.properties.hibernate.detector.level:</b> Set the log level for detected N+1 issues (default: WARN).</li>
 * </ul>
 *
 * <pre>
 * Example configuration (YAML):
 * spring:
 *   jpa:
 *     properties:
 *       hibernate:
 *         detector:
 *           enabled: true
 *           threshold: 2
 *           level: warn
 * </pre>
 *
 * <pre>
 * Example configuration (Properties):
 * spring.jpa.properties.hibernate.detector.enabled=true
 * spring.jpa.properties.hibernate.detector.threshold=2
 * spring.jpa.properties.hibernate.detector.level=warn
 * </pre>
 *
 * <p>
 * The N+1 detector is disabled by default to avoid potential performance overhead in production environments.
 * It is recommended to enable it only in development or testing environments.
 * </p>
 *
 * @author jeyong
 * @since 1.0
 * @see NPlusOneDetectorBaseConfig
 * @see NPlusOneDetectorLoggingConfig
 * @see NPlusOneDetectorExceptionConfig
 */
// @formatter:on
@ConfigurationProperties(prefix = "spring.jpa.properties.hibernate.detector")
public class NPlusOneDetectorProperties {

    private boolean enabled = false;

    private int threshold = 2;

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

    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }
}
