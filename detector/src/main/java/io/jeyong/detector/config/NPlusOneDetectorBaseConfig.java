package io.jeyong.detector.config;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.detector.aspect.ConnectionProxyAspect;
import io.jeyong.detector.interceptor.QueryCaptureInspector;
import io.jeyong.detector.template.NPlusOneQueryTemplate;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;

public abstract class NPlusOneDetectorBaseConfig {

    @Bean
    public ConnectionProxyAspect connectionProxyAspect(final NPlusOneQueryTemplate nPlusOneQueryTemplate) {
        return new ConnectionProxyAspect(nPlusOneQueryTemplate);
    }

    @Bean
    public QueryCaptureInspector queryCaptureInspector() {
        return new QueryCaptureInspector();
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(
            final QueryCaptureInspector queryCaptureInspector) {
        return hibernateProperties -> hibernateProperties.put(STATEMENT_INSPECTOR, queryCaptureInspector);
    }

    protected abstract NPlusOneQueryTemplate nPlusOneQueryTemplate();
}
