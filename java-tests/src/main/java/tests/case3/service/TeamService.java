package tests.case3.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tests.case3.entity.Team;
import tests.case3.repository.TeamRepository;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public List<Team> findAllTeams() {
        List<Team> teams = teamRepository.findAll();
        teams.forEach(team -> team.getMembers().size());  // N+1 문제 발생
        return teams;
    }
}
