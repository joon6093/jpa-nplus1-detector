package io.jeyong.test.case4.service;

import io.jeyong.test.case4.entity.Person;
import io.jeyong.test.case4.repository.PersonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    public List<Person> findAllPersons() {
        List<Person> persons = personRepository.findAll();
        persons.forEach(person -> person.getAddress().getCity());  // N+1 문제 발생
        return persons;
    }
}
