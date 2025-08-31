package tests.case1.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tests.case1.entity.Author;
import tests.case1.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceV2 {

    private final AuthorRepository authorRepository;

    @Transactional
    public List<Author> findAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> author.getBooks().size());  // N+1 문제 발생
        return authors;
    }
}
