package io.jeyong.test.case3.service;

import io.jeyong.test.case3.entity.Team;
import io.jeyong.test.case3.repository.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public List<Team> findAllTeams() {
        List<Team> teams = teamRepository.findAll();
        teams.forEach(team -> team.getMembers().size());  // N+1 문제 발생 가능
        return teams;
    }
}
