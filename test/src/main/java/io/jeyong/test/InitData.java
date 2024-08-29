package io.jeyong.test;

import io.jeyong.test.case1.entity.Author;
import io.jeyong.test.case1.entity.Book;
import io.jeyong.test.case1.repository.AuthorRepository;
import io.jeyong.test.case1.repository.BookRepository;
import io.jeyong.test.case2.entity.Order;
import io.jeyong.test.case2.entity.Product;
import io.jeyong.test.case2.repository.OrderRepository;
import io.jeyong.test.case2.repository.ProductRepository;
import io.jeyong.test.case3.entity.Member;
import io.jeyong.test.case3.entity.Team;
import io.jeyong.test.case3.repository.MemberRepository;
import io.jeyong.test.case3.repository.TeamRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
        initCase1();
        initCase2();
        initCase3();
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
}
