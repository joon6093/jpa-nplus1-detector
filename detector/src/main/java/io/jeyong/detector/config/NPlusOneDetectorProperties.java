package io.jeyong.detector.config;

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
 * </ul>
 *
 * <pre>
 * Example configuration (YAML):
 * jpa:
 *   properties:
 *     hibernate:
 *       detector:
 *         enabled: true
 *         threshold: 2
 * </pre>
 *
 * <pre>
 * Example configuration (Properties):
 * jpa.properties.hibernate.detector.enabled=true
 * jpa.properties.hibernate.detector.threshold=3
 * </pre>
 *
 * <p>
 * The N+1 detector is disabled by default to avoid potential performance overhead in production environments.
 * It is recommended to enable it only in development or testing environments.
 * </p>
 *
 * @author jeyong
 * @since 1.0
 */
// @formatter:on
@ConfigurationProperties(prefix = "spring.jpa.properties.hibernate.detector")
public class NPlusOneDetectorProperties {

    private boolean enabled = false;

    private int threshold = 2;

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
}
