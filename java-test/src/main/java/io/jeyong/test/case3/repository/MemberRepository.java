package io.jeyong.test.case3.repository;

import io.jeyong.test.case3.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
