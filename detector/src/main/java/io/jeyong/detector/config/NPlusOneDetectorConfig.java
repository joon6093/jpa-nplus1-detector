package io.jeyong.detector.config;

import io.jeyong.detector.aop.NPlusOneDetectionAop;
import io.jeyong.detector.context.LoggingContext;
import io.jeyong.detector.interceptor.NPlusOneStatementInspector;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
        prefix = "spring.jpa.properties.hibernate",
        name = "detector",
        havingValue = "true",
        matchIfMissing = false
)
public class NPlusOneDetectorConfig {

    @Bean
    public LoggingContext loggingContext() {
        return new LoggingContext();
    }

    @Bean
    public NPlusOneDetectionAop nPlusOneDetectionAop(LoggingContext loggingContext) {
        return new NPlusOneDetectionAop(loggingContext);
    }

    @Bean
    public NPlusOneStatementInspector nPlusOneStatementInspector(LoggingContext loggingContext) {
        return new NPlusOneStatementInspector(loggingContext);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertyConfig(
            NPlusOneStatementInspector nPlusOneStatementInspector) {
        return hibernateProperties ->
                hibernateProperties.put(AvailableSettings.STATEMENT_INSPECTOR, nPlusOneStatementInspector);
    }
}
