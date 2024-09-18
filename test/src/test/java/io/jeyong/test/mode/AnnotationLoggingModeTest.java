package io.jeyong.test.mode;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.jeyong.detector.test.NPlusOneTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@NPlusOneTest(threshold = 3, level = Level.DEBUG, mode = NPlusOneTest.Mode.LOGGING)
class AnnotationLoggingModeTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("LOGGING 모드의 설정이 제대로 동작한다.")
    void testNPlusOneTestLoggingMode() {
        assertThat(System.getProperty("spring.jpa.properties.hibernate.detector.threshold")).isEqualTo("3");
        assertThat(System.getProperty("spring.jpa.properties.hibernate.detector.level")).isEqualTo("DEBUG");

        assertThat(context.containsBean("nPlusOneQueryLogger")).isTrue();
    }
}
