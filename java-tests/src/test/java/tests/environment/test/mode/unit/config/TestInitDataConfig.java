package tests.environment.test.mode.unit.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import tests.InitData;
import tests.case1.repository.AuthorRepository;
import tests.case1.repository.BookRepository;
import tests.case2.repository.OrderRepository;
import tests.case2.repository.ProductRepository;
import tests.case3.repository.MemberRepository;
import tests.case3.repository.TeamRepository;
import tests.case4.repository.AddressRepository;
import tests.case4.repository.PersonRepository;
import tests.case5.repository.CourseRepository;
import tests.case5.repository.StudentRepository;

@TestConfiguration
public class TestInitDataConfig {

    @Bean
    public InitData initData(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            TeamRepository teamRepository,
            MemberRepository memberRepository,
            PersonRepository personRepository,
            AddressRepository addressRepository,
            CourseRepository courseRepository,
            StudentRepository studentRepository
    ) {
        return new InitData(
                authorRepository, bookRepository, orderRepository, productRepository,
                teamRepository, memberRepository, personRepository,
                addressRepository, courseRepository, studentRepository);
    }
}
