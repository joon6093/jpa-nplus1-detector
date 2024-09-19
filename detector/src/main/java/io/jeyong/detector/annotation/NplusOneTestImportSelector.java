package io.jeyong.detector.annotation;

import io.jeyong.detector.annotation.NPlusOneTest.Mode;
import io.jeyong.detector.config.NPlusOneDetectorExceptionConfig;
import io.jeyong.detector.config.NPlusOneDetectorLoggingConfig;
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

        final Mode mode = (Mode) attributes.get("mode");

        if (mode == Mode.LOGGING) {
            configureForLoggingMode(attributes);
            return new String[]{NPlusOneDetectorLoggingConfig.class.getName()};
        } else if (mode == Mode.EXCEPTION) {
            configureForExceptionMode(attributes);
            return new String[]{NPlusOneDetectorExceptionConfig.class.getName()};
        } else {
            throw new IllegalArgumentException("Invalid mode provided for @NPlusOneTest: " + mode);
        }
    }

    private void configureForLoggingMode(final Map<String, Object> attributes) {
        final int threshold = (int) attributes.get("threshold");
        final Level level = (Level) attributes.get("level");

        final Map<String, Object> propertyMap = Map.of(
                "spring.jpa.properties.hibernate.detector.enabled", "true",
                "spring.jpa.properties.hibernate.detector.threshold", String.valueOf(threshold),
                "spring.jpa.properties.hibernate.detector.level", level.toString()
        );

        environment.getPropertySources().addFirst(new MapPropertySource("DetectorProperties", propertyMap));
    }

    private void configureForExceptionMode(final Map<String, Object> attributes) {
        final int threshold = (int) attributes.get("threshold");

        final Map<String, Object> propertyMap = Map.of(
                "spring.jpa.properties.hibernate.detector.enabled", "false",
                "spring.jpa.properties.hibernate.detector.threshold", String.valueOf(threshold)
        );

        environment.getPropertySources().addFirst(new MapPropertySource("DetectorProperties", propertyMap));
    }
}