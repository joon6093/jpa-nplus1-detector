package io.jeyong.detector.config;

import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.template.NPlusOneQueryExceptionCollector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import io.jeyong.detector.test.NPlusOneTest.Mode;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(NPlusOneDetectorProperties.class)
public class NPlusOneDetectorExceptionConfig extends NPlusOneDetectorBaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneDetectorExceptionConfig.class);
    private final NPlusOneDetectorProperties nPlusOneDetectorProperties;

    public NPlusOneDetectorExceptionConfig(final NPlusOneDetectorProperties nPlusOneDetectorProperties) {
        this.nPlusOneDetectorProperties = nPlusOneDetectorProperties;
    }

    @PostConstruct
    public void logInitialization() {
        logger.info(
                "N+1 Detector enabled in '{}' mode. Monitoring queries with a threshold of '{}'.",
                Mode.EXCEPTION,
                nPlusOneDetectorProperties.getThreshold());
    }

    @Bean
    public ExceptionContext exceptionContext() {
        return new ExceptionContext();
    }

    @Bean
    @Override
    public NPlusOneQueryTemplate nPlusOneQueryTemplate() {
        return new NPlusOneQueryExceptionCollector(
                nPlusOneDetectorProperties.getThreshold(), exceptionContext());
    }
}
