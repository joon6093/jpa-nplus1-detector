package io.jeyong.detector.test;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.detector.interceptor.QueryStatementInspector;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NPlusOneTestConfig {

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
