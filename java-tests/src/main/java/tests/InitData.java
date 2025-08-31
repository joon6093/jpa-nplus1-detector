package tests;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tests.case1.entity.Author;
import tests.case1.entity.Book;
import tests.case1.repository.AuthorRepository;
import tests.case1.repository.BookRepository;
import tests.case2.entity.Order;
import tests.case2.entity.Product;
import tests.case2.repository.OrderRepository;
import tests.case2.repository.ProductRepository;
import tests.case3.entity.Member;
import tests.case3.entity.Team;
import tests.case3.repository.MemberRepository;
import tests.case3.repository.TeamRepository;
import tests.case4.entity.Address;
import tests.case4.entity.Person;
import tests.case4.repository.AddressRepository;
import tests.case4.repository.PersonRepository;
import tests.case5.entity.Course;
import tests.case5.entity.Student;
import tests.case5.repository.CourseRepository;
import tests.case5.repository.StudentRepository;

@Component
@RequiredArgsConstructor
public class InitData {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
        initCase1();
        initCase2();
        initCase3();
        initCase4();
        initCase5();
    }

    private void initCase1() {
        Author author1 = new Author();
        author1.setName("Author 1");

        Author author2 = new Author();
        author2.setName("Author 2");

        authorRepository.save(author1);
        authorRepository.save(author2);

        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setAuthor(author1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setAuthor(author1);

        Book book3 = new Book();
        book3.setTitle("Book 3");
        book3.setAuthor(author2);

        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);
    }

    private void initCase2() {
        Order order1 = new Order();
        order1.setOrderNumber("Order 1");

        Order order2 = new Order();
        order2.setOrderNumber("Order 2");

        Order order3 = new Order();
        order3.setOrderNumber("Order 3");

        orderRepository.save(order1);
        orderRepository.save(order2);
        orderRepository.save(order3);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(100.0);
        product1.setOrder(order1);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(200.0);
        product2.setOrder(order1);

        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setPrice(300.0);
        product3.setOrder(order2);

        Product product4 = new Product();
        product4.setName("Product 4");
        product4.setPrice(150.0);
        product4.setOrder(order3);

        productRepository.saveAll(List.of(product1, product2, product3, product4));
    }

    private void initCase3() {
        Team team1 = new Team();
        team1.setName("Team 1");

        Team team2 = new Team();
        team2.setName("Team 2");

        Team team3 = new Team();
        team3.setName("Team 3");

        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);

        Member member1 = new Member();
        member1.setName("Member 1");
        member1.setTeam(team1);

        Member member2 = new Member();
        member2.setName("Member 2");
        member2.setTeam(team1);

        Member member3 = new Member();
        member3.setName("Member 3");
        member3.setTeam(team2);

        Member member4 = new Member();
        member4.setName("Member 4");
        member4.setTeam(team3);

        memberRepository.saveAll(List.of(member1, member2, member3, member4));
    }

    private void initCase4() {
        Address address1 = new Address();
        address1.setStreet("123 Main St");
        address1.setCity("City A");

        Address address2 = new Address();
        address2.setStreet("456 Elm St");
        address2.setCity("City B");

        Address address3 = new Address();
        address3.setStreet("789 Oak St");
        address3.setCity("City C");

        addressRepository.saveAll(List.of(address1, address2, address3));

        Person person1 = new Person();
        person1.setName("Person 1");
        person1.setAddress(address1);

        Person person2 = new Person();
        person2.setName("Person 2");
        person2.setAddress(address2);

        Person person3 = new Person();
        person3.setName("Person 3");
        person3.setAddress(address3);

        personRepository.saveAll(List.of(person1, person2, person3));
    }

    private void initCase5() {
        Course course1 = new Course();
        course1.setTitle("Course 1");

        Course course2 = new Course();
        course2.setTitle("Course 2");

        Course course3 = new Course();
        course3.setTitle("Course 3");

        courseRepository.saveAll(List.of(course1, course2, course3));

        Student student1 = new Student();
        student1.setName("Student 1");
        student1.getCourses().addAll(List.of(course1, course2));

        Student student2 = new Student();
        student2.setName("Student 2");
        student2.getCourses().add(course2);

        Student student3 = new Student();
        student3.setName("Student 3");
        student3.getCourses().addAll(List.of(course2, course3));

        studentRepository.saveAll(List.of(student1, student2, student3));
    }
}
