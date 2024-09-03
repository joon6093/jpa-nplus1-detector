package io.jeyong.detector.config;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.detector.aop.NPlusOneDetectionAspect;
import io.jeyong.detector.context.QueryLoggingContext;
import io.jeyong.detector.interceptor.NPlusOneStatementInspector;
import io.jeyong.detector.logging.NPlusOneQueryLogger;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "spring.jpa.properties.hibernate.detector",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = false
)
@EnableConfigurationProperties(NPlusOneDetectorProperties.class)
public class NPlusOneDetectorConfig {

    private static final Logger logger = LoggerFactory.getLogger(NPlusOneDetectorConfig.class);
    private final NPlusOneDetectorProperties nPlusOneDetectorProperties;

    public NPlusOneDetectorConfig(NPlusOneDetectorProperties nPlusOneDetectorProperties) {
        this.nPlusOneDetectorProperties = nPlusOneDetectorProperties;
    }

    @PostConstruct
    public void logInitialization() {
        logger.info("N+1 Detector is enabled with threshold: {}", nPlusOneDetectorProperties.getThreshold());
    }

    @Bean
    public QueryLoggingContext queryLoggingContext() {
        return new QueryLoggingContext();
    }

    @Bean
    public NPlusOneQueryLogger nPlusOneQueryLogger(final QueryLoggingContext queryLoggingContext) {
        return new NPlusOneQueryLogger(queryLoggingContext, nPlusOneDetectorProperties.getThreshold());
    }

    @Bean
    public NPlusOneDetectionAspect nPlusOneDetectionAspect(final NPlusOneQueryLogger nPlusOneQueryLogger) {
        return new NPlusOneDetectionAspect(nPlusOneQueryLogger);
    }

    @Bean
    public NPlusOneStatementInspector nPlusOneStatementInspector(final QueryLoggingContext queryLoggingContext) {
        return new NPlusOneStatementInspector(queryLoggingContext);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertyConfig(
            final NPlusOneStatementInspector nPlusOneStatementInspector) {
        return hibernateProperties -> hibernateProperties.put(STATEMENT_INSPECTOR, nPlusOneStatementInspector);
    }
}
