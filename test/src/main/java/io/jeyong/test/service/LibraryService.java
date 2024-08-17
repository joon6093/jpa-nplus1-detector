package io.jeyong.test.service;

import io.jeyong.test.entity.Author;
import io.jeyong.test.repository.AuthorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibraryService {

    private final AuthorRepository authorRepository;

    public List<Author> findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> author.getBooks().size());  // N+1 문제 발생 가능
        return authors;
    }
}
