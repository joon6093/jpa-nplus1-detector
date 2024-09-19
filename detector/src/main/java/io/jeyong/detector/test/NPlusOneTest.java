package io.jeyong.detector.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.event.Level;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(DetectNPlusOneExtension.class)
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
