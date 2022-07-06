package com.example.libraryspring.service;

import com.example.libraryspring.enitity.Book;
import com.example.libraryspring.enitity.Person;

import java.util.List;
import java.util.Optional;

public interface PeopleService{
    public List<Person> index();

    public Person show(int id);

    public void save(Person person);

    public void update(int id, Person updatedPerson);

    public void delete(int id);

    public Optional<Person> getPersonByFullName(String fullName);

    public List<Book> getBooksByPersonId(int id);
}
