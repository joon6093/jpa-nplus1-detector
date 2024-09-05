package io.jeyong.test.case4.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Test
    @DisplayName("1:1 관계에서 일(1)을 조회하는 상황에서 감지한다.")
    void testFindAllPersons(CapturedOutput output) {
        personService.findAllPersons();

        assertThat(output).contains("N+1 issue detected");
    }
}
