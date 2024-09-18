package io.jeyong.detector.test;

import io.jeyong.detector.config.NPlusOneDetectorConfig;
import io.jeyong.detector.config.NPlusOneDetectorProperties;
import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.template.NPlusOneQueryExceptionCollector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@Import(NPlusOneDetectorConfig.class)
@EnableConfigurationProperties(NPlusOneDetectorProperties.class)
public class NPlusOneTestConfig {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneTestConfig.class);
    private final NPlusOneDetectorProperties nPlusOneDetectorProperties;

    public NPlusOneTestConfig(final NPlusOneDetectorProperties nPlusOneDetectorProperties) {
        this.nPlusOneDetectorProperties = nPlusOneDetectorProperties;
    }

    @PostConstruct
    public void logInitialization() {
        logger.info("N+1 issues detected will throw exceptions.");
    }

    @Bean
    public ExceptionContext exceptionContext() {
        return new ExceptionContext();
    }
    
    @Primary
    @Bean
    public NPlusOneQueryTemplate nPlusOneQueryExceptionCollector(final ExceptionContext exceptionContext) {
        return new NPlusOneQueryExceptionCollector(
                nPlusOneDetectorProperties.getThreshold(), exceptionContext);
    }
}
