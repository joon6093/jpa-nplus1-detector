package tests.environment.test.annotation;

import static org.assertj.core.api.Assertions.assertThat;

import io.jeyong.nplus1detector.core.config.NPlusOneDetectorProperties;
import io.jeyong.nplus1detector.test.annotation.NPlusOneTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@NPlusOneTest(
        threshold = 3,
        level = Level.DEBUG,
        exclude = {
                "annotationExcludeQuery1",
                "annotationExcludeQuery2"
        }
)
@ExtendWith(SpringExtension.class)
@TestPropertySource(
        properties = {
                "spring.jpa.properties.hibernate.detector.threshold=10",
                "spring.jpa.properties.hibernate.detector.level=warn",
                "spring.jpa.properties.hibernate.detector.exclude[0]=envExcludeQuery1",
                "spring.jpa.properties.hibernate.detector.exclude[1]=envExcludeQuery2"
        })
class AnnotationTest {

    @Autowired
    private NPlusOneDetectorProperties nPlusOneDetectorProperties;

    @Test
    @DisplayName("@NPlusOneTest에서 지정한 설정이 우선적으로 적용된다.")
    void testAnnotationConfiguration() {
        assertThat(nPlusOneDetectorProperties.getThreshold()).isEqualTo(3);
        assertThat(nPlusOneDetectorProperties.getLevel()).isEqualTo(Level.DEBUG);

        assertThat(nPlusOneDetectorProperties.getExclude())
                .containsExactly(
                        "annotationExcludeQuery1",
                        "annotationExcludeQuery2"
                );
    }
}
