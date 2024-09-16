package io.jeyong.detector.config;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.detector.aspect.ConnectionProxyAspect;
import io.jeyong.detector.interceptor.QueryStatementInspector;
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

    public NPlusOneDetectorConfig(final NPlusOneDetectorProperties nPlusOneDetectorProperties) {
        this.nPlusOneDetectorProperties = nPlusOneDetectorProperties;
    }

    @PostConstruct
    public void logInitialization() {
        logger.info("N+1 Detector is enabled with threshold: {}", nPlusOneDetectorProperties.getThreshold());
    }

    @Bean
    public NPlusOneQueryLogger nPlusOneQueryLogger() {
        return new NPlusOneQueryLogger(nPlusOneDetectorProperties.getThreshold(),
                nPlusOneDetectorProperties.getLevel());
    }

    @Bean
    public ConnectionProxyAspect connectionProxyAspect(final NPlusOneQueryLogger nPlusOneQueryLogger) {
        return new ConnectionProxyAspect(nPlusOneQueryLogger);
    }

    @Bean
    public QueryStatementInspector queryStatementInspector() {
        return new QueryStatementInspector();
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(
            final QueryStatementInspector queryStatementInspector) {
        return hibernateProperties -> hibernateProperties.put(STATEMENT_INSPECTOR, queryStatementInspector);
    }
}
