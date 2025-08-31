package tests.case3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case3.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
