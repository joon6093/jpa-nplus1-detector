package io.jeyong.test.case1.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

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

    /**
     * V1은 클래스 단위로 @Transactional이 선언되어 있으며, N+1 문제가 발생할 수 있음.
     */
    @Test
    void testNPlusOneDetectionInV1(CapturedOutput output) {
        libraryServiceV1.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * V2는 메서드 단위로 @Transactional이 선언되어 있으며, 여전히 N+1 문제가 발생할 수 있음.
     */
    @Test
    void testNPlusOneDetectionInV2(CapturedOutput output) {
        libraryServiceV2.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * V3는 OSIV(Open Session In View)를 이용한 지연 조회를 사용하며, N+1 문제가 발생하지 않아야 정상임.
     */
    @Test
    void testNPlusOneDetectionInV3(CapturedOutput output) {
        libraryServiceV3.findAllAuthors();

        assertThat(output).doesNotContain("N+1 issue detected");
    }
}
