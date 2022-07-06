package com.example.libraryspring.repository;

import com.example.libraryspring.enitity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person,Integer> {

    Optional<Person> findByName(String full_name);
}
