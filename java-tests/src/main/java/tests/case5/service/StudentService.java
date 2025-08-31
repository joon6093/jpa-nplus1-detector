package tests.case5.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tests.case5.entity.Student;
import tests.case5.repository.StudentRepository;

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
