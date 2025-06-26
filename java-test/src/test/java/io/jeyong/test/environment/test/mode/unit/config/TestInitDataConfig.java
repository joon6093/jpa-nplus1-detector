package io.jeyong.test.environment.test.mode.unit.config;

import io.jeyong.test.InitData;
import io.jeyong.test.case1.repository.AuthorRepository;
import io.jeyong.test.case1.repository.BookRepository;
import io.jeyong.test.case2.repository.OrderRepository;
import io.jeyong.test.case2.repository.ProductRepository;
import io.jeyong.test.case3.repository.MemberRepository;
import io.jeyong.test.case3.repository.TeamRepository;
import io.jeyong.test.case4.repository.AddressRepository;
import io.jeyong.test.case4.repository.PersonRepository;
import io.jeyong.test.case5.repository.CourseRepository;
import io.jeyong.test.case5.repository.StudentRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

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
