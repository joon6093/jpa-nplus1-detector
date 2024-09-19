package io.jeyong.detector.test;

import io.jeyong.detector.config.NPlusOneDetectorConfig;
import java.util.Map;
import java.util.Objects;
import org.slf4j.event.Level;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

public final class NplusOneTestImportSelector implements ImportSelector, EnvironmentAware {

    private ConfigurableEnvironment environment;

    @Override
    public void setEnvironment(@NonNull final Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    @NonNull
    @Override
    public String[] selectImports(final AnnotationMetadata importingClassMetadata) {
        final Map<String, Object> attributes = Objects.requireNonNull(
                importingClassMetadata.getAnnotationAttributes(NPlusOneTest.class.getName()),
                "Attributes for @NPlusOneTest cannot be null"
        );

        final int threshold = (int) attributes.get("threshold");
        final Level logLevel = (Level) attributes.get("level");
        final NPlusOneTest.Mode mode = (NPlusOneTest.Mode) attributes.get("mode");

        final Map<String, Object> propertyMap = Map.of(
                "spring.jpa.properties.hibernate.detector.enabled", "true",
                "spring.jpa.properties.hibernate.detector.threshold", String.valueOf(threshold),
                "spring.jpa.properties.hibernate.detector.level", logLevel.toString()
        );

        environment.getPropertySources().addFirst(new MapPropertySource("nPlusOneDetectorProperties", propertyMap));

        return new String[]{
                mode == NPlusOneTest.Mode.LOGGING
                        ? NPlusOneDetectorConfig.class.getName()
                        : NPlusOneTestConfig.class.getName()
        };
    }
}
