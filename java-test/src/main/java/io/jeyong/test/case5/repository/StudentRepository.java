package io.jeyong.test.case5.repository;

import io.jeyong.test.case5.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
