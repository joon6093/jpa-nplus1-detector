package tests.environment.main.case5.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import tests.case5.service.StudentService;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=true",
                "spring.jpa.properties.hibernate.detector.threshold=2",
                "spring.jpa.properties.hibernate.detector.exclude=",
                "spring.jpa.properties.hibernate.detector.level=warn"
        })
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    @DisplayName("N:N 관계에서 연관관계의 주인인 다(N)를 조회하는 상황에서 감지한다.")
    void testFindAllStudents(CapturedOutput output) {
        studentService.findAllStudents();

        assertThat(output).contains("N+1 query detected");
    }
}
