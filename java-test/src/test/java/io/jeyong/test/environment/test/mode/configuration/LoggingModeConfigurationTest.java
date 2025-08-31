package io.jeyong.test.environment.test.mode.configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.jeyong.core.annotation.NPlusOneTest;
import io.jeyong.core.config.NPlusOneDetectorProperties;
import io.jeyong.core.template.NPlusOneQueryLogger;
import io.jeyong.core.template.NPlusOneQueryTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@NPlusOneTest(mode = NPlusOneTest.Mode.LOGGING)
@ExtendWith(SpringExtension.class)
public class LoggingModeConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NPlusOneDetectorProperties nPlusOneDetectorProperties;

    @Test
    @DisplayName("LOGGING 모드의 설정이 작동한다.")
    void testLoggingModeConfiguration() {
        assertThat(nPlusOneDetectorProperties.isEnabled()).isTrue();

        NPlusOneQueryTemplate template = applicationContext.getBean(NPlusOneQueryTemplate.class);
        assertThat(template).isInstanceOf(NPlusOneQueryLogger.class);
    }
}
