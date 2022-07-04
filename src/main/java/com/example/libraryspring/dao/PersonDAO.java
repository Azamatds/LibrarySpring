package com.example.libraryspring.dao;

import com.example.libraryspring.enitity.Book;
import com.example.libraryspring.enitity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person",new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person){
        jdbcTemplate.update("INSERT INTO Person(name ,year_of_birth) VALUES(?,?)",
                person.getName(),person.getYear_of_birth());
    }

    public Person show(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?",new BeanPropertyRowMapper<>(Person.class),
                id).stream().findAny().orElse(null);
    }

    public void update(int id,Person updatedPerson){
        jdbcTemplate.update("UPDATE Person SET name =?,year_of_birth=? WHERE id=?",
                updatedPerson.getName(),updatedPerson.getYear_of_birth(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    // Для валидации уникальности ФИО
    public Optional<Person> getPersonByFullName(String name) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=?",
                new BeanPropertyRowMapper<>(Person.class),name).stream().findAny();
    }

    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM BOOK Where person_id=?",
                new BeanPropertyRowMapper<>(Book.class),id);
    }


}
