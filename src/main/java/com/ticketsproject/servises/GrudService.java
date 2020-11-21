package com.ticketsproject.servises;

import java.util.List;

public interface GrudService<T, ID> {
    T save(T t);
    T findById(ID id);
    List<T> findAll();
    void delete(T object);
    void deleteById(ID id);
}
