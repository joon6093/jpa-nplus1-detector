package io.jeyong.detector.config;

import io.jeyong.detector.aop.NPlusOneDetectionAop;
import io.jeyong.detector.context.QueryLoggingContext;
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
    public QueryLoggingContext loggingContext() {
        return new QueryLoggingContext();
    }

    @Bean
    public NPlusOneDetectionAop nPlusOneDetectionAop(final QueryLoggingContext queryLoggingContext) {
        return new NPlusOneDetectionAop(queryLoggingContext, nPlusOneDetectorProperties.getThreshold());
    }

    @Bean
    public NPlusOneStatementInspector nPlusOneStatementInspector(final QueryLoggingContext queryLoggingContext) {
        return new NPlusOneStatementInspector(queryLoggingContext);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertyConfig(
            final NPlusOneStatementInspector nPlusOneStatementInspector) {
        return hibernateProperties ->
                hibernateProperties.put(AvailableSettings.STATEMENT_INSPECTOR, nPlusOneStatementInspector);
    }
}
