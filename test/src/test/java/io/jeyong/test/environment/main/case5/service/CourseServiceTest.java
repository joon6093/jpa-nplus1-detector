package io.jeyong.test.environment.main.case5.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import io.jeyong.test.case5.service.CourseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Test
    @DisplayName("N:N 관계에서 연관관계의 주인이 아닌 다(N)를 조회하는 상황에서 감지한다.")
    void testFindAllCourses(CapturedOutput output) {
        courseService.findAllCourses();

        assertThat(output).contains("N+1 query detected");
    }
}
