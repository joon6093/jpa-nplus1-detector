package tests.environment.main.case4.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import tests.case4.service.PersonService;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=true",
                "spring.jpa.properties.hibernate.detector.threshold=2",
                "spring.jpa.properties.hibernate.detector.exclude=",
                "spring.jpa.properties.hibernate.detector.level=warn"
        })
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    @DisplayName("1:1 관계에서 연관관계의 주인인 일(1)을 조회하는 상황에서 감지한다.")
    void testFindAllPersons(CapturedOutput output) {
        personService.findAllPersons();

        assertThat(output).contains("N+1 query detected");
    }
}
