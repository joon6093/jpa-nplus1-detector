package io.jeyong.test.case1.repository;

import io.jeyong.test.case1.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
