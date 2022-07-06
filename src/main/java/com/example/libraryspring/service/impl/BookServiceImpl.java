package com.example.libraryspring.service.impl;

import com.example.libraryspring.enitity.Book;
import com.example.libraryspring.enitity.Person;
import com.example.libraryspring.repository.Bookrepostiory;
import com.example.libraryspring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    @Autowired
    private Bookrepostiory bookrepostiory;

    @Override
    public List<Book> findAll(boolean sortByYear) {
        if (sortByYear){
            return bookrepostiory.findAll(Sort.by("year"));
        }else {
            return bookrepostiory.findAll();
        }
    }

    @Override
    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookrepostiory.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        else
            return bookrepostiory.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    @Override
    public Book findOne(int id) {
        return bookrepostiory.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void save(Book book) {
        bookrepostiory.save(book);
    }

    @Override
    public List<Book> searchByTitle(String query) {
        return bookrepostiory.findByTitleStartingWith(query);
    }



    @Override
    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        bookrepostiory.save(updatedBook);
    }

    @Override
    @Transactional
    public void delete(int id) {
        bookrepostiory.deleteById(id);
    }

    @Override
    public Person getBookOwner(int id) {
        return bookrepostiory.findById(id).map(book -> book.getOwner()).orElse(null);
    }

    @Override
    @Transactional
    public void release(int id) {
        bookrepostiory.findById(id).ifPresent(book ->{
            book.setOwner(null);
            book.setTakenAt(null);
        });
    }

    @Override
    @Transactional
    public void assign(int id, Person selectedPerson) {
        bookrepostiory.findById(id).ifPresent(book ->{
            book.setOwner(selectedPerson);
            book.setTakenAt(new Date());
        });
    }
}
