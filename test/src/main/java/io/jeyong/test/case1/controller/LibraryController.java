package io.jeyong.test.case1.controller;

import io.jeyong.test.case1.entity.Author;
import io.jeyong.test.case1.service.LibraryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/authors")
    public List<Author> getAuthors() {
        return libraryService.findAllAuthors();
    }
}
