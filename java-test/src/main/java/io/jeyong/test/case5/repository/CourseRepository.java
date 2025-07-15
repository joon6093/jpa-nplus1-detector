package io.jeyong.test.case5.repository;

import io.jeyong.test.case5.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
