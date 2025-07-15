package io.jeyong.test.case1.service;

import io.jeyong.test.case1.entity.Author;
import io.jeyong.test.case1.repository.AuthorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceV1 {

    private final AuthorRepository authorRepository;

    public List<Author> findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> author.getBooks().size());  // N+1 문제 발생
        return authors;
    }
}
