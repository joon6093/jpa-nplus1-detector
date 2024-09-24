package io.jeyong.test.mode.unit;

import static org.assertj.core.api.Assertions.assertThat;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import io.jeyong.detector.template.NPlusOneQueryLogger;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import io.jeyong.test.case4.repository.AddressRepository;
import io.jeyong.test.mode.unit.config.TestInitDataConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@NPlusOneTest(mode = NPlusOneTest.Mode.LOGGING, threshold = 3, level = Level.DEBUG)
@DataJpaTest(showSql = false)
@TestPropertySource(
        properties = {
                "logging.level.io.jeyong=debug",
                "spring.jpa.properties.hibernate.detector.enabled=false",
                "spring.jpa.properties.hibernate.detector.threshold=10",
                "spring.jpa.properties.hibernate.detector.level=warn"
        })
@Import(TestInitDataConfig.class)
class LoggingModeUnitTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NPlusOneDetectorProperties nPlusOneDetectorProperties;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("LOGGING 모드의 설정이 우선적으로 적용된다.")
    void testLoggingModeConfiguration() {
        assertThat(nPlusOneDetectorProperties.isEnabled()).isTrue();
        assertThat(nPlusOneDetectorProperties.getThreshold()).isEqualTo(3);
        assertThat(nPlusOneDetectorProperties.getLevel()).isEqualTo(Level.DEBUG);

        NPlusOneQueryTemplate template = applicationContext.getBean(NPlusOneQueryTemplate.class);
        assertThat(template).isInstanceOf(NPlusOneQueryLogger.class);
    }

    @Test
    @DisplayName("Repository 호출에서 LOGGING 모드가 동작한다.")
    void testLoggingModeInRepositoryCall() {
        addressRepository.findAll();  // N+1 문제 발생
    }
}
