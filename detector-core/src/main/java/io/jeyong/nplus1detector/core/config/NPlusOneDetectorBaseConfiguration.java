package io.jeyong.nplus1detector.core.config;

import static org.hibernate.cfg.AvailableSettings.STATEMENT_INSPECTOR;

import io.jeyong.nplus1detector.core.aspect.ConnectionProxyAspect;
import io.jeyong.nplus1detector.core.query.interceptor.QueryCaptureInspector;
import io.jeyong.nplus1detector.core.template.NPlusOneQueryTemplate;
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
