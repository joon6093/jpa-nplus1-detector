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
     * @formatter:off
     * Author과 Book는 일대다(1:N) 관계이며,
     * 모든 Author을 조회한 후 각 Author에 대해 별도의 쿼리로 Book을 조회
     * 클래스 단위의 @Transactional 상황에서 감지하는 것을 검증
     * @formatter:on
     */
    @GetMapping("/v1/authors")
    public List<Author> getAuthorsV1() {
        return libraryServiceV1.findAllAuthors();
    }

    /**
     * @formatter:off
     * Author과 Book는 일대다(1:N) 관계이며,
     * 모든 Author을 조회한 후 각 Author에 대해 별도의 쿼리로 Book을 조회
     * 메서드 단위의 @Transactional 상황에서 감지하는 것을 검증
     * @formatter:on
     */
    @GetMapping("/v2/authors")
    public List<Author> getAuthorsV2() {
        return libraryServiceV2.findAllAuthors();
    }

    /**
     * @formatter:off
     * Author과 Book는 일대다(1:N) 관계이며,
     * 모든 Author을 조회한 후 각 Author에 대해 별도의 쿼리로 Book을 조회
     * OSIV를 이용한 지연 조회 상황에서 감지하는 것을 검증
     * @formatter:on
     */
    @GetMapping("/v3/authors")
    public List<Author> getAuthorsV3() {
        List<Author> allAuthors = libraryServiceV3.findAllAuthors();
        allAuthors.forEach(author -> author.getBooks().size());  // N+1 문제 발생 가능
        return allAuthors;
    }
}
