package io.jeyong.detector.config;

import io.jeyong.detector.annotation.NPlusOneTest.Mode;
import io.jeyong.detector.context.ExceptionContext;
import io.jeyong.detector.template.NPlusOneQueryCollector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration(proxyBeanMethods = false)
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
                "N+1 Detector enabled in '{}' mode. Monitoring queries with a threshold of '{}'.",
                Mode.EXCEPTION,
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
