package tests.case1.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tests.case1.entity.Author;
import tests.case1.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceV3 {

    private final AuthorRepository authorRepository;

    @Transactional
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
}
