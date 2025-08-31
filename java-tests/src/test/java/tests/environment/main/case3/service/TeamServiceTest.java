package tests.environment.main.case3.service;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import tests.case3.service.TeamService;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(
        properties = {
                "spring.jpa.properties.hibernate.detector.enabled=true",
                "spring.jpa.properties.hibernate.detector.threshold=2",
                "spring.jpa.properties.hibernate.detector.exclude=",
                "spring.jpa.properties.hibernate.detector.level=warn"
        })
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Test
    @DisplayName("@BatchSize 상황에서 감지하지 않는다.")
    void testFindAllTeams(CapturedOutput output) {
        teamService.findAllTeams();

        assertThat(output).doesNotContain("N+1 query detected");
    }
}
