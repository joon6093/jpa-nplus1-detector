package io.jeyong.test.environment.main.case4.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import io.jeyong.test.case4.service.AddressService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=true",
                "spring.jpa.properties.hibernate.detector.threshold=2",
                "spring.jpa.properties.hibernate.detector.exclude=",
                "spring.jpa.properties.hibernate.detector.level=warn"
        })
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    @DisplayName("1:1 관계에서 연관관계의 주인이 아닌 일(1)을 조회하는 상황에서 감지한다.")
    void testFindAllAddresses(CapturedOutput output) {
        addressService.findAllAddresses();

        assertThat(output).contains("N+1 query detected");
    }
}
