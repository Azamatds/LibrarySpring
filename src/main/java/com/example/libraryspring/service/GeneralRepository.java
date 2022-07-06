package com.example.libraryspring.service;

import java.util.List;
import java.util.Optional;

public interface GeneralRepository<T> {
    List<T> getAll();

    Optional<T> getById(int id);

    void delete(int id);

    Optional<T> getByName(String name);

    void save(T user);

    void update(T user,int id);
}
