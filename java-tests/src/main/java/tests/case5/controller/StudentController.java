package tests.case5.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tests.case5.entity.Student;
import tests.case5.service.StudentService;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    // @formatter:off
    /**
     * Student와 Course는 다대다(N:N) 관계
     * 모든 Student을 조회한 후 각 Student에 대해 별도의 쿼리로 Course를 조회
     * N:N 관계에서 연관관계의 주인인 다(N)를 조회하는 상황에서 감지하는 것을 검증
     */
    // @formatter:on
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.findAllStudents();
    }
}
