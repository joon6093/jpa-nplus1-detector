package io.jeyong.core.config;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.core.aspect.ConnectionProxyAspect;
import io.jeyong.core.interceptor.QueryCaptureInspector;
import io.jeyong.core.template.NPlusOneQueryTemplate;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class NPlusOneDetectorBaseConfiguration {

    @Bean
    public ConnectionProxyAspect connectionProxyAspect(final NPlusOneQueryTemplate nPlusOneQueryTemplate) {
        return new ConnectionProxyAspect(nPlusOneQueryTemplate);
    }

    @Bean
    public QueryCaptureInspector queryCaptureInspector() {
        return new QueryCaptureInspector();
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(final QueryCaptureInspector queryCaptureInspector) {
        return hibernateProperties -> hibernateProperties.put(STATEMENT_INSPECTOR, queryCaptureInspector);
    }
}
