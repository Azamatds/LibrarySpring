package com.example.libraryspring.service.impl;

import com.example.libraryspring.enitity.Book;
import com.example.libraryspring.enitity.Person;
import com.example.libraryspring.repository.PeopleRepository;
import com.example.libraryspring.service.PeopleService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
@Transactional(readOnly = true)
public class PeopleServiceImpl implements PeopleService {

    @Autowired
    private PeopleRepository peopleRepository;

    @Override
    public List<Person> index() {
        return peopleRepository.findAll();
    }

    @Override
    public Person show(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Override
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Override
    public Optional<Person> getPersonByFullName(String fullName) {
        return Optional.empty();
    }


    @Override
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Override
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        }else {
            return Collections.emptyList();
        }
    }
}
