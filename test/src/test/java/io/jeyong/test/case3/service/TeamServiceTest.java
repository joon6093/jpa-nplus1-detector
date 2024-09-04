package io.jeyong.test.case3.service;

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
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Test
    @DisplayName("@BatchSize 상황에서 감지하지 않는다.")
    void testFindAllTeams(CapturedOutput output) {
        teamService.findAllTeams();

        assertThat(output).doesNotContain("N+1 issue detected");
    }
}
