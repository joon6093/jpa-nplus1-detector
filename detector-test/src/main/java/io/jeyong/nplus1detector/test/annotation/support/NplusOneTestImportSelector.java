package io.jeyong.nplus1detector.test.annotation.support;

import io.jeyong.nplus1detector.core.config.NPlusOneDetectorLoggingConfiguration;
import io.jeyong.nplus1detector.test.annotation.NPlusOneTest;
import io.jeyong.nplus1detector.test.annotation.NPlusOneTest.Mode;
import io.jeyong.nplus1detector.test.config.NPlusOneDetectorExceptionConfiguration;
import java.util.HashMap;
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
            return new String[]{NPlusOneDetectorLoggingConfiguration.class.getName()};
        } else if (mode == Mode.EXCEPTION) {
            configureForExceptionMode(attributes);
            return new String[]{NPlusOneDetectorExceptionConfiguration.class.getName()};
        } else {
            throw new IllegalArgumentException("Invalid mode provided for @NPlusOneTest: " + mode);
        }
    }

    private void configureForLoggingMode(final Map<String, Object> attributes) {
        final int threshold = (int) attributes.get("threshold");
        final String[] exclude = (String[]) attributes.get("exclude");
        final Level level = (Level) attributes.get("level");

        final Map<String, Object> propertyMap = createBasePropertyMap(threshold, exclude);
        propertyMap.put("nplus1detector.enabled", "true");
        propertyMap.put("nplus1detector.level", level.toString());

        environment.getPropertySources().addFirst(new MapPropertySource("DetectorProperties", propertyMap));
    }

    private void configureForExceptionMode(final Map<String, Object> attributes) {
        final int threshold = (int) attributes.get("threshold");
        final String[] exclude = (String[]) attributes.get("exclude");

        final Map<String, Object> propertyMap = createBasePropertyMap(threshold, exclude);
        propertyMap.put("nplus1detector.enabled", "false");

        environment.getPropertySources().addFirst(new MapPropertySource("DetectorProperties", propertyMap));
    }

    private Map<String, Object> createBasePropertyMap(final int threshold, final String[] exclude) {
        final Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("nplus1detector.threshold", String.valueOf(threshold));
        for (int i = 0; i < exclude.length; i++) {
            propertyMap.put("nplus1detector.exclude[" + i + "]", exclude[i]);
        }

        return propertyMap;
    }
}
