package io.jeyong.test.case5.service;

import io.jeyong.test.case5.entity.Student;
import io.jeyong.test.case5.repository.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public List<Student> findAllStudents() {
        List<Student> students = studentRepository.findAll();
        students.forEach(student -> student.getCourses().size());  // N+1 문제 발생
        return students;
    }
}
