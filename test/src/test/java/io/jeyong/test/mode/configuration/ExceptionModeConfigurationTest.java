package io.jeyong.test.mode.configuration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.template.NPlusOneQueryCollector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@NPlusOneTest(mode = NPlusOneTest.Mode.EXCEPTION)
@ExtendWith(SpringExtension.class)
public class ExceptionModeConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private NPlusOneDetectorProperties nPlusOneDetectorProperties;

    @Test
    @DisplayName("EXCEPTION 모드의 설정이 작동한다.")
    void testExceptionModeConfiguration() {
        assertThat(nPlusOneDetectorProperties.isEnabled()).isFalse();

        NPlusOneQueryTemplate template = applicationContext.getBean(NPlusOneQueryTemplate.class);
        assertThat(template).isInstanceOf(NPlusOneQueryCollector.class);

        ExceptionContext exceptionContext = applicationContext.getBean(ExceptionContext.class);
        assertThat(exceptionContext).isNotNull();
    }
}
