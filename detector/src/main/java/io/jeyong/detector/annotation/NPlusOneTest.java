package io.jeyong.detector.annotation;

import io.jeyong.detector.exception.NPlusOneQueryException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.event.Level;
import org.springframework.context.annotation.Import;

// @formatter:off
/**
 * <p>
 * Annotation to enable N+1 Query Detection in JPA (Hibernate) based applications.
 * This annotation can be applied at the class level to detect and log or throw exceptions
 * for N+1 query issues during the execution of test cases.
 * </p>
 *
 * <p>
 * When applied to a test class, it integrates with the {@code NPlusOneTestExtension} to automatically detect N+1 queries
 * and applies the configured threshold, log level, and behavior (logging or exception) based on the provided parameters.
 * </p>
 *
 * <ul>
 *     <li><b>threshold:</b> The number of times a query must be executed before an N+1 issue is flagged (default: 2).</li>
 *     <li><b>level:</b> The log level for detected N+1 issues (default: WARN).</li>
 *     <li><b>mode:</b> The mode in which the detector operates:
 *         <ul>
 *             <li><b>LOGGING:</b> Logs the detected N+1 issues at the specified log level.</li>
 *             <li><b>EXCEPTION:</b> Throws an {@code NPlusOneQueryException} when an N+1 issue is detected.</li>
 *         </ul>
 *     </li>
 * </ul>
 *
 * <pre>
 * Example usage:
 * {@code
 * @NPlusOneTest(threshold = 3, mode = NPlusOneTest.Mode.EXCEPTION)
 * @SpringBootTest
 * class MyJpaTest {
 *     // Test cases here
 * }
 * }
 * </pre>
 *
 * <p>
 * This annotation is intended for use in <b>test code only</b> to detect N+1 query issues during unit and integration testing.
 * </p>
 *
 * @author jeyong
 * @since 2.0
 * @see NPlusOneTestExtension
 * @see NplusOneTestImportSelector
 * @see NPlusOneQueryException
 */
// @formatter:on
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(NPlusOneTestExtension.class)
@Import(NplusOneTestImportSelector.class)
public @interface NPlusOneTest {

    int threshold() default 2;

    Level level() default Level.WARN;

    Mode mode() default Mode.LOGGING;

    enum Mode {
        LOGGING,
        EXCEPTION
    }
}
