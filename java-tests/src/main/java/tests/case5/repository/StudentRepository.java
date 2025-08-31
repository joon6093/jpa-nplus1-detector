package tests.case5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case5.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
