package io.jeyong.core.config;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.core.aspect.ConnectionProxyAspect;
import io.jeyong.core.query.interceptor.QueryCaptureInspector;
import io.jeyong.core.template.NPlusOneQueryTemplate;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class NPlusOneDetectorBaseConfiguration {

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> hibernateProperties.put(STATEMENT_INSPECTOR, new QueryCaptureInspector());
    }

    @Bean
    public ConnectionProxyAspect connectionProxyAspect(final NPlusOneQueryTemplate nPlusOneQueryTemplate) {
        return new ConnectionProxyAspect(nPlusOneQueryTemplate);
    }
}
