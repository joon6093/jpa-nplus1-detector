package io.jeyong.test.environment.main.case1.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import io.jeyong.test.case1.service.AuthorServiceV1;
import io.jeyong.test.case1.service.AuthorServiceV2;
import io.jeyong.test.case1.service.AuthorServiceV3;
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
public class AuthorServiceTest {

    @Autowired
    private AuthorServiceV1 authorServiceV1;

    @Autowired
    private AuthorServiceV2 authorServiceV2;

    @Autowired
    private AuthorServiceV3 authorServiceV3;

    @Test
    @DisplayName("클래스 단위의 @Transactional 상황에서 감지한다.")
    void testFindAllAuthorsV1(CapturedOutput output) {
        authorServiceV1.findAllAuthors();

        assertThat(output).contains("N+1 query detected");
    }

    @Test
    @DisplayName("메서드 단위의 @Transactional 상황에서 감지한다.")
    void testFindAllAuthorsV2(CapturedOutput output) {
        authorServiceV2.findAllAuthors();

        assertThat(output).contains("N+1 query detected");
    }

    @Test
    @DisplayName("OSIV를 이용한 지연 조회 상황에서 감지한다.")
    void testFindAllAuthorsV3(CapturedOutput output) {
        authorServiceV3.findAllAuthors();

        assertThat(output).doesNotContain("N+1 query detected");
    }
}
