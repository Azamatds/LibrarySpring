package com.example.libraryspring.repository;

import com.example.libraryspring.enitity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Bookrepostiory extends JpaRepository<Book,Integer> {

    public List<Book> findByTitleStartingWith(String title);
    public Optional<Book> findByTitle(String title);

}
