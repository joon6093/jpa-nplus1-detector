package io.jeyong.test.config;

import io.jeyong.core.config.NPlusOneDetectorBaseConfiguration;
import io.jeyong.core.config.NPlusOneDetectorProperties;
import io.jeyong.core.template.NPlusOneQueryTemplate;
import io.jeyong.test.exception.context.ExceptionContext;
import io.jeyong.test.template.NPlusOneQueryCollector;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@TestConfiguration(proxyBeanMethods = false)
@EnableConfigurationProperties(NPlusOneDetectorProperties.class)
@Import(NPlusOneDetectorBaseConfiguration.class)
public class NPlusOneDetectorExceptionConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneDetectorExceptionConfiguration.class);
    private final NPlusOneDetectorProperties nPlusOneDetectorProperties;

    public NPlusOneDetectorExceptionConfiguration(final NPlusOneDetectorProperties nPlusOneDetectorProperties) {
        this.nPlusOneDetectorProperties = nPlusOneDetectorProperties;
    }

    @PostConstruct
    public void logInitialization() {
        logger.info(
                "N+1 Detector enabled in 'EXCEPTION' mode. Monitoring queries with a threshold of '{}'.",
                nPlusOneDetectorProperties.getThreshold());
    }

    @Bean
    public ExceptionContext exceptionContext() {
        return new ExceptionContext();
    }

    @Bean
    public NPlusOneQueryTemplate nPlusOneQueryTemplate(final ExceptionContext exceptionContext) {
        return new NPlusOneQueryCollector(
                nPlusOneDetectorProperties.getThreshold(),
                nPlusOneDetectorProperties.getExclude(),
                exceptionContext);
    }
}
