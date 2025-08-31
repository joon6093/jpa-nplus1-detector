package tests.environment.test.mode.configuration;

import io.jeyong.core.config.NPlusOneDetectorProperties;
import io.jeyong.core.template.NPlusOneQueryTemplate;
import io.jeyong.test.annotation.NPlusOneTest;
import io.jeyong.test.exception.context.ExceptionContext;
import io.jeyong.test.template.NPlusOneQueryCollector;
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
