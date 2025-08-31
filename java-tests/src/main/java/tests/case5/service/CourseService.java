package tests.case5.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tests.case5.entity.Course;
import tests.case5.repository.CourseRepository;

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
