package io.jeyong.test.case4.repository;

import io.jeyong.test.case4.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
