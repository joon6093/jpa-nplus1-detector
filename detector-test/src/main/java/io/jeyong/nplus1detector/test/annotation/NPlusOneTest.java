package io.jeyong.nplus1detector.test.annotation;

import io.jeyong.nplus1detector.test.annotation.support.NPlusOneTestListener;
import io.jeyong.nplus1detector.test.annotation.support.NplusOneTestImportSelector;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.slf4j.event.Level;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.TestExecutionListeners;

// @formatter:off
/**
 * <p>
 * Annotation for enabling the N+1 Detector in JPA (Hibernate).
 * </p>
 *
 * <p>
 * To enable and customize the N+1 Detector, apply this annotation at the class level
 * during test execution to detect N+1 queries and either log them or throw exceptions.
 * </p>
 *
 * Example usage:
 * <pre>{@code
 * @NPlusOneTest(
 *      mode = NPlusOneTest.Mode.EXCEPTION,
 *      threshold = 5,
 *      exclude = {
 *          "select ... from table1 where ...",
 *          "select ... from table2 where ..."
 *      })
 * @SpringBootTest or @DataJpaTest
 * class MyJpaTest {
 *     // Test cases here
 * }
 * }
 * </pre>
 *
 * <p>
 * <b>Note:</b> This annotation is intended for use in <b>test code only</b>
 * to detect N+1 queries during unit and integration testing.
 * </p>
 *
 * @author jeyong
 * @since 2.0
 * @see NplusOneTestImportSelector
 * @see NPlusOneTestListener
 */
// @formatter:on
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAspectJAutoProxy
@Import(NplusOneTestImportSelector.class)
@TestExecutionListeners(
        value = NPlusOneTestListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public @interface NPlusOneTest {

    /**
     * Set the mode in which the detector operates (default: LOGGING).
     */
    @AliasFor("mode")
    Mode value() default Mode.LOGGING;

    /**
     * Set the mode in which the detector operates (default: LOGGING).
     */
    @AliasFor("value")
    Mode mode() default Mode.LOGGING;

    /**
     * Set the threshold for the number of query executions to detect N+1 queries (default: 2).
     */
    int threshold() default 2;

    /**
     * Set the list of specific queries to be excluded from N+1 queries (optional).
     */
    String[] exclude() default {};

    /**
     * Set the log level for detected N+1 queries (default: WARN).
     */
    Level level() default Level.WARN;

    /**
     * Mode in which the N+1 Detector operates.
     */
    enum Mode {
        /**
         * Logs the detected N+1 queries at the specified log level.
         */
        LOGGING,
        /**
         * Throws an {@code NPlusOneQueryException} for the detected N+1 queries.
         */
        EXCEPTION
    }
}
