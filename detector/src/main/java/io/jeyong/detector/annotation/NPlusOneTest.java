package io.jeyong.detector.annotation;

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
 * To enable the N+1 Detector and customize its behavior, users
 * can apply this annotation at the class level to detect and log or throw exceptions
 * for N+1 query issues during the execution of test cases.
 * </p>
 *
 * <ul>
 *     <li><b>mode:</b> Set the mode in which the detector operates (default: LOGGING).
 *         <ul>
 *             <li><b>LOGGING:</b> Logs the detected N+1 queries at the specified log level.</li>
 *             <li><b>EXCEPTION:</b> Throws an {@code NPlusOneQueryException} for the detected N+1 queries.</li>
 *         </ul>
 *     </li>
 *     <li><b>threshold:</b> Set the threshold for the number of query executions to detect N+1 queries (default: 2).</li>
 *     <li><b>exclude:</b> Set the list of specific queries to be excluded from N+1 queries (optional).</li>
 *     <li><b>level:</b> Set the log level for detected N+1 queries (default: WARN).</li>
 * </ul>
 *
 * <pre>
 * Example usage:
 * {@code
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
 * This annotation is intended for use in <b>test code only</b> to detect N+1 queries during unit and integration testing.
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

    @AliasFor("mode")
    Mode value() default Mode.LOGGING;

    @AliasFor("value")
    Mode mode() default Mode.LOGGING;

    enum Mode {
        LOGGING,
        EXCEPTION
    }

    int threshold() default 2;

    String[] exclude() default {};

    Level level() default Level.WARN;
}
