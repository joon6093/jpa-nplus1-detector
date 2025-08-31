package tests.case4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case4.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
