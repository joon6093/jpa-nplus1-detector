package tests.case1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case1.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
