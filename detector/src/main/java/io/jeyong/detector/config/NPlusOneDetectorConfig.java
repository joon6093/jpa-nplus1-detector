package io.jeyong.detector.config;

import io.jeyong.detector.aop.NPlusOneDetectionAop;
import io.jeyong.detector.context.LoggingContext;
import io.jeyong.detector.interceptor.NPlusOneStatementInspector;
import org.hibernate.cfg.AvailableSettings;
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

    private final NPlusOneDetectorProperties nPlusOneDetectorProperties;

    public NPlusOneDetectorConfig(NPlusOneDetectorProperties nPlusOneDetectorProperties) {
        this.nPlusOneDetectorProperties = nPlusOneDetectorProperties;
    }

    @Bean
    public LoggingContext loggingContext() {
        return new LoggingContext();
    }

    @Bean
    public NPlusOneDetectionAop nPlusOneDetectionAop(final LoggingContext loggingContext) {
        return new NPlusOneDetectionAop(loggingContext, nPlusOneDetectorProperties.getThreshold());
    }

    @Bean
    public NPlusOneStatementInspector nPlusOneStatementInspector(final LoggingContext loggingContext) {
        return new NPlusOneStatementInspector(loggingContext);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertyConfig(
            final NPlusOneStatementInspector nPlusOneStatementInspector) {
        return hibernateProperties ->
                hibernateProperties.put(AvailableSettings.STATEMENT_INSPECTOR, nPlusOneStatementInspector);
    }
}
