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
     * V1 Service: Verify if N+1 issue occurs when using class-level @Transactional.
     * The N+1 issue should occur in LibraryServiceV1.
     * @formatter:on
     */
    @Test
    void testNPlusOneDetectionInV1(CapturedOutput output) {
        libraryServiceV1.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * @formatter:off
     * V2 Service: Verify if N+1 issue occurs when using method-level @Transactional.
     * The N+1 issue should occur in LibraryServiceV2.
     * @formatter:on
     */
    @Test
    void testNPlusOneDetectionInV2(CapturedOutput output) {
        libraryServiceV2.findAllAuthors();

        assertThat(output).contains("N+1 issue detected");
    }

    /**
     * @formatter:off
     * V3 Service: Verify if N+1 issue does not occur when using OSIV.
     * The N+1 issue should not occur in LibraryServiceV3.
     * @formatter:on
     */
    @Test
    void testNPlusOneDetectionInV3(CapturedOutput output) {
        libraryServiceV3.findAllAuthors();

        assertThat(output).doesNotContain("N+1 issue detected");
    }
}
