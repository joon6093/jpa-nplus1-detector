package io.jeyong.detector.test;

import io.jeyong.detector.config.NPlusOneDetectorConfig;
import java.util.Map;
import org.slf4j.event.Level;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.NonNull;

public class NplusOneTestImportSelector implements ImportSelector {

    @NonNull
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata
                .getAnnotationAttributes(NPlusOneTest.class.getName());

        assert attributes != null;
        int threshold = (int) attributes.get("threshold");
        Level logLevel = (Level) attributes.get("level");
        NPlusOneTest.Mode mode = (NPlusOneTest.Mode) attributes.get("mode");

        System.setProperty("spring.jpa.properties.hibernate.detector.threshold", String.valueOf(threshold));
        System.setProperty("spring.jpa.properties.hibernate.detector.level", logLevel.toString());
        return new String[]{
                mode == NPlusOneTest.Mode.LOGGING
                        ? NPlusOneDetectorConfig.class.getName()
                        : NPlusOneTestConfig.class.getName()
        };
    }
}
