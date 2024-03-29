package com.example.libraryspring.util;

import com.example.libraryspring.dao.PersonDAO;
import com.example.libraryspring.enitity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.getPersonByFullName(person.getName()).isPresent())
            errors.rejectValue("name", "", "Человек с таким ФИО уже существует");

        if (!Character.isUpperCase(person.getName().codePointAt(0))){
            errors.rejectValue("name","","Name should start with a capital letter");
        }
    }
}
