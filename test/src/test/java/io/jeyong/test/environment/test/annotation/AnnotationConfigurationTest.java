package io.jeyong.test.environment.test.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@NPlusOneTest(threshold = 3, level = Level.DEBUG)
@ExtendWith(SpringExtension.class)
@TestPropertySource(
        properties = {
                "spring.jpa.properties.hibernate.detector.threshold=10",
                "spring.jpa.properties.hibernate.detector.level=warn"
        })
class AnnotationConfigurationTest {

    @Autowired
    private NPlusOneDetectorProperties nPlusOneDetectorProperties;

    @Test
    @DisplayName("@NPlusOneTest에서 지정한 설정이 우선적으로 적용된다.")
    void testAnnotationConfiguration() {
        assertThat(nPlusOneDetectorProperties.getThreshold()).isEqualTo(3);
        assertThat(nPlusOneDetectorProperties.getLevel()).isEqualTo(Level.DEBUG);
    }
}
