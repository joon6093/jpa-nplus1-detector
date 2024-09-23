package io.jeyong.test.mode.unit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import io.jeyong.detector.template.NPlusOneQueryCollector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import io.jeyong.test.case4.repository.AddressRepository;
import io.jeyong.test.mode.unit.config.TestInitDataConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@NPlusOneTest(mode = NPlusOneTest.Mode.EXCEPTION, threshold = 3)
@DataJpaTest
@TestPropertySource(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=true",
                "spring.jpa.properties.hibernate.detector.threshold=10",
        })
@Import(TestInitDataConfig.class)
class ExceptionModeUnitTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NPlusOneDetectorProperties nPlusOneDetectorProperties;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("EXCEPTION 모드의 설정이 우선적으로 적용된다.")
    void testExceptionModeConfiguration() {
        assertThat(nPlusOneDetectorProperties.isEnabled()).isFalse();
        assertThat(nPlusOneDetectorProperties.getThreshold()).isEqualTo(3);

        NPlusOneQueryTemplate template = applicationContext.getBean(NPlusOneQueryTemplate.class);
        assertThat(template).isInstanceOf(NPlusOneQueryCollector.class);
    }

    @Disabled("BeforeEach 메서드에서 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("Repository 호출에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInBusinessLogicCall() {
        addressRepository.findAll();  // N+1 문제 발생
    }
}
