package io.jeyong.detector.config;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.detector.aspect.ConnectionProxyAspect;
import io.jeyong.detector.interceptor.QueryStatementInspector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;

public abstract class NPlusOneDetectorBaseConfig {

    @Bean
    public ConnectionProxyAspect connectionProxyAspect(final NPlusOneQueryTemplate nPlusOneQueryTemplate) {
        return new ConnectionProxyAspect(nPlusOneQueryTemplate);
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

    protected abstract NPlusOneQueryTemplate nPlusOneQueryTemplate();
}
