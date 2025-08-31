package tests.case3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case3.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
