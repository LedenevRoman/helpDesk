package com.training.rledenev.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    Optional<T> findOne(long id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(long entityId);
}
