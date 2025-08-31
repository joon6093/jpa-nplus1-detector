package tests.case1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case1.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
