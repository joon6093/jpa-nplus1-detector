package io.jeyong.test.mode;

import static org.assertj.core.api.Assertions.assertThat;

import io.jeyong.detector.test.NPlusOneTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
@NPlusOneTest(threshold = 5, mode = NPlusOneTest.Mode.EXCEPTION)
class AnnotationExceptionModeTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("EXCEPTION 모드의 설정이 제대로 동작한다.")
    void testNPlusOneTestExceptionMode() {
        assertThat(System.getProperty("spring.jpa.properties.hibernate.detector.threshold")).isEqualTo("5");

        assertThat(context.containsBean("nPlusOneQueryExceptionCollector")).isTrue();
        assertThat(context.containsBean("exceptionContext")).isTrue();
    }
}
