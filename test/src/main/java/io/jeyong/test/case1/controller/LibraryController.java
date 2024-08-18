package io.jeyong.test.case1.controller;

import io.jeyong.test.case1.entity.Author;
import io.jeyong.test.case1.service.LibraryServiceV1;
import io.jeyong.test.case1.service.LibraryServiceV2;
import io.jeyong.test.case1.service.LibraryServiceV3;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryServiceV1 libraryServiceV1;
    private final LibraryServiceV2 libraryServiceV2;
    private final LibraryServiceV3 libraryServiceV3;


    /**
     * 클래스 단위의 @Transactional 선언
     */
    @GetMapping("/v1/authors")
    public List<Author> getAuthorsV1() {
        List<Author> allAuthors = libraryServiceV1.findAllAuthors();
        return allAuthors;
    }

    /**
     * 메서드 단위의 @Transactional 선언
     */
    @GetMapping("/v2/authors")
    public List<Author> getAuthorsV2() {
        List<Author> allAuthors = libraryServiceV2.findAllAuthors();
        return allAuthors;
    }

    /**
     * OSIV를 이용한 지연 조회
     */
    @GetMapping("/v3/authors")
    public List<Author> getAuthorsV3() {
        List<Author> allAuthors = libraryServiceV3.findAllAuthors();
        allAuthors.forEach(author -> author.getBooks().size());  // N+1 문제 발생 가능
        return allAuthors;
    }
}
