package io.jeyong.test.case1.service;

import io.jeyong.test.case1.entity.Author;
import io.jeyong.test.case1.repository.AuthorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LibraryServiceV3 {

    private final AuthorRepository authorRepository;

    @Transactional
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }
}
