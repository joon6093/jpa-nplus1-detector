package tests.case5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case5.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
