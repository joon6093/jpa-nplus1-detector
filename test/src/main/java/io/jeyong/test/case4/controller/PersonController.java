package io.jeyong.test.case4.controller;

import io.jeyong.test.case4.entity.Person;
import io.jeyong.test.case4.service.PersonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * @formatter:off
     * Person과 Address는 일대일(1:1) 관계이며,
     * 모든 Person을 조회한 후 각 Person에 대해 별도의 쿼리로 Address를 조회
     * 1:1 관계에서 연관관계의 주인인 일(1)을 조회하는 상황에서 감지하는 것을 검증
     * @formatter:on
     */
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.findAllPersons();
    }
}
