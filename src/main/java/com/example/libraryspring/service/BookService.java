package com.example.libraryspring.service;

import com.example.libraryspring.enitity.Book;
import com.example.libraryspring.enitity.Person;

import javax.validation.constraints.Digits;
import java.util.List;
import java.util.Optional;

public interface BookService{

    public List<Book> findAll(boolean sortByYear);

    public Book findOne(int id);

    public void save(Book book);

    public List<Book> searchByTitle(String query);

    public void update(int id, Book updatedBook);

    public void delete(int id);

    public Person getBookOwner(int id);

    public void release(int id);

    public void assign(int id, Person selectedPerson);

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear);
}
