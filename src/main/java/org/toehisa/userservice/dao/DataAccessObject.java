package org.toehisa.userservice.dao;

//Нужен для изоляции работы с бд
public interface DataAccessObject<T> {
    void save(T entity);      // CREATE
    T findById(Object id);    // READ
    void update(T entity);    // UPDATE
    void delete(T entity);    // DELETE
}
