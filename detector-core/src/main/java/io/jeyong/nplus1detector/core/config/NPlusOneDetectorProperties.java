package io.jeyong.nplus1detector.core.config;

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
 * To enable and customize the N+1 Detector, configure the following properties
 * in your {@code application.yml} or {@code application.properties}.
 * </p>

 * Example configuration (YAML):
 * <pre>{@code
 * nplus1detector:
 *   enabled: true
 *   threshold: 2
 *   exclude:
 *     - select ... from table1 where ...
 *     - select ... from table2 where ...
 *   level: warn
 * }
 * </pre>
 *
 * Example configuration (Properties):
 * <pre>{@code
 * nplus1detector.enabled=true
 * nplus1detector.threshold=2
 * nplus1detector.exclude[0]=select ... from table1 where ...
 * nplus1detector.exclude[1]=select ... from table2 where ...
 * nplus1detector.level=warn
 * }
 * </pre>
 *
 * <p>
 * <p><b>Note:</b> The N+1 Detector is disabled by default
 * to avoid potential performance overhead in production environments.
 * It is recommended to enable it only in development or testing environments.
 * </p>
 *
 * @author jeyong
 * @since 1.0
 * @see NPlusOneDetectorBaseConfiguration
 * @see NPlusOneDetectorLoggingConfiguration
 */
// @formatter:on
 @ConfigurationProperties(prefix = "nplus1detector")
 public class NPlusOneDetectorProperties {

     /**
      * Set whether the detector is enabled or disabled (default: false).
      */
     private boolean enabled = false;

     /**
      * Set the threshold for the number of query executions to detect N+1 queries (default: 2).
      */
     private int threshold = 2;

     /**
      * Set the list of specific queries to be excluded from N+1 queries (optional).
      */
     private List<String> exclude = List.of();

     /**
      * Set the log level for detected N+1 queries (default: WARN).
      */
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
