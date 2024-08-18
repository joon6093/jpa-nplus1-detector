package io.jeyong.detector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jpa.properties.hibernate.detector")
public class NPlusOneDetectorProperties {

    /**
     * N+1 문제 감지 기능 활성화 여부. 기본값은 false.
     */
    private boolean enabled = false;

    /**
     * 쿼리 실행 횟수 기준 설정. 기본값은 2.
     */
    private int threshold = 2;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
}
