package io.jeyong.detector.config;

import io.jeyong.detector.annotation.NPlusOneTest.Mode;
import io.jeyong.detector.template.NPlusOneQueryLogger;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(
        prefix = "spring.jpa.properties.hibernate.detector",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false
)
@EnableConfigurationProperties(NPlusOneDetectorProperties.class)
public class NPlusOneDetectorLoggingConfig extends NPlusOneDetectorBaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneDetectorLoggingConfig.class);
    private final NPlusOneDetectorProperties nPlusOneDetectorProperties;

    public NPlusOneDetectorLoggingConfig(final NPlusOneDetectorProperties nPlusOneDetectorProperties) {
        this.nPlusOneDetectorProperties = nPlusOneDetectorProperties;
    }

    @PostConstruct
    public void logInitialization() {
        logger.info(
                "N+1 Detector enabled in '{}' mode. Monitoring queries with a threshold of '{}' and logging at '{}' level.",
                Mode.LOGGING,
                nPlusOneDetectorProperties.getThreshold(),
                nPlusOneDetectorProperties.getLevel().toString().toLowerCase());
    }

    @Bean
    public NPlusOneQueryTemplate nPlusOneQueryTemplate() {
        return new NPlusOneQueryLogger(
                nPlusOneDetectorProperties.getThreshold(),
                nPlusOneDetectorProperties.getExclude(),
                nPlusOneDetectorProperties.getLevel());
    }
}
