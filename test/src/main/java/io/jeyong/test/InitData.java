package io.jeyong.test;

import io.jeyong.test.entity.Author;
import io.jeyong.test.entity.Book;
import io.jeyong.test.repository.AuthorRepository;
import io.jeyong.test.repository.BookRepository;
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

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initData() {
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
}
