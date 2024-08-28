package io.jeyong.test.case1.service;

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
public class LibraryServiceTest {

    @Autowired
    private LibraryServiceV1 libraryServiceV1;

    @Autowired
    private LibraryServiceV2 libraryServiceV2;

    @Autowired
    private LibraryServiceV3 libraryServiceV3;

    @Test
    @DisplayName("클래스 단위의 @Transactional 상황에서 감지하는 것을 검증")
    void testFindAllAuthorsV1(CapturedOutput output) {
        libraryServiceV1.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    @Test
    @DisplayName("메서드 단위의 @Transactional 상황에서 감지하는 것을 검증")
    void testFindAllAuthorsV2(CapturedOutput output) {
        libraryServiceV2.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    @Test
    @DisplayName("OSIV를 이용한 지연 조회 상황에서 감지하는 것을 검증")
    void testFindAllAuthorsV3(CapturedOutput output) {
        libraryServiceV3.findAllAuthors();

        assertThat(output).doesNotContain("N+1 issue detected");
    }
}
