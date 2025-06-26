package io.jeyong.test.case3.repository;

import io.jeyong.test.case3.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
