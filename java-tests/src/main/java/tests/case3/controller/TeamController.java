package tests.case3.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tests.case3.entity.Team;
import tests.case3.service.TeamService;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    // @formatter:off
    /**
     * Team과 Member는 일대다(1:N) 관계이며,
     * 모든 Team을 조회한 후 각 Team에 대해 별도의 쿼리로 Member를 조회
     * @BatchSize 상황에서 감지하지 않는 것을 검증
     */
    // @formatter:on
    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.findAllTeams();
    }
}
