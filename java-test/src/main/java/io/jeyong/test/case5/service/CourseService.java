package io.jeyong.test.case5.service;

import io.jeyong.test.case5.entity.Course;
import io.jeyong.test.case5.repository.CourseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public List<Course> findAllCourses() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(course -> course.getStudents().size());  // N+1 문제 발생
        return courses;
    }
}
