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
     * @formatter:off
     * V1 Service: 클래스 레벨 @Transactional을 사용하는 경우 N+1 문제가 발생하는지 확인합니다.
     * LibraryServiceV1에서 N+1 문제가 발생해야 합니다.
     * @formatter:on
     */
    @Test
    void testNPlusOneDetectionInV1(CapturedOutput output) {
        libraryServiceV1.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * @formatter:off
     * V2 Service: 메서드 레벨 @Transactional을 사용하는 경우 N+1 문제가 발생하는지 확인합니다.
     * LibraryServiceV2에서 N+1 문제가 발생해야 합니다.
     * @formatter:on
     */
    @Test
    void testNPlusOneDetectionInV2(CapturedOutput output) {
        libraryServiceV2.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * @formatter:off
     * V3 Service: OSIV를 사용하는 경우 N+1 문제가 발생하지 않는지 확인합니다.
     * LibraryServiceV3에서 N+1 문제가 발생하지 않아야 합니다.
     * @formatter:on
     */
    @Test
    void testNPlusOneDetectionInV3(CapturedOutput output) {
        libraryServiceV3.findAllAuthors();

        assertThat(output).doesNotContain("N+1 issue detected");
    }
}
