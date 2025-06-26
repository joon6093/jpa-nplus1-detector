package io.jeyong.test.case5.controller;

import io.jeyong.test.case5.entity.Course;
import io.jeyong.test.case5.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // @formatter:off
    /**
     * Course와 Student는 다대다(N:N) 관계
     * 모든 Course를 조회한 후 각 Course에 대해 별도의 쿼리로 Student를 조회
     * N:N 관계에서 연관관계의 주인이 아닌 다(N)를 조회하는 상황에서 감지하는 것을 검증
     */
    // @formatter:on
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAllCourses();
    }
}
