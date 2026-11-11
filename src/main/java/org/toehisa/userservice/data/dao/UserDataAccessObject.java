package org.toehisa.userservice.data.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.toehisa.userservice.data.entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.toehisa.userservice.data.singltones.HibernateSessionFactoryHolder.getSessionFactory;

public class UserDataAccessObject implements DataAccessObject<User>{
    @Override
    public void save(User user) {
        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {//Достаем сессию из пула сессий
            try {
                transaction = session.beginTransaction();//Начинаем транзакцию в сессии
                session.persist(user);//IDENTITY сначала в бд потом в кеше, SEQUENCE, UUID сначала в кеше потом в бд
                transaction.commit();
            } catch (HibernateException e) {
                transactionIsBadRollback(transaction);
                throw new RuntimeException("Не удалось сохранить пользователя в БД", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Критический сбой в методе save: " + e.getMessage(), e);
        }
    }

    @Override
    public User findById(Object id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при поиске пользователя: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {//Достаем сессию из пула сессий
            try {
                transaction = session.beginTransaction();//Начинаем транзакцию в сессии
                session.merge(user);//Обновляем запись в кеше
                transaction.commit();//После коммита в бд
            } catch (HibernateException e) {
                transactionIsBadRollback(transaction);
                throw new RuntimeException("Не удалось обновить пользователя в БД", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Критический сбой в методе update: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            try {
                transaction = session.beginTransaction(); // Удаление тоже требует транзакции
                session.remove(user); // Удаляем объект из кеша
                transaction.commit();// Удаляем из бд
            } catch (HibernateException e) {
                transactionIsBadRollback(transaction);
                throw new RuntimeException("Не удалось удалить пользователя в БД", e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Критический сбой в методе delete: " + e.getMessage(), e);
        }
    }

    public List<User> findAll() {
        try(Session session = getSessionFactory().openSession()) {
            return session.createNativeQuery("SELECT * FROM users", User.class).list();
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    private void transactionIsBadRollback(Transaction transaction) {
        if (Objects.nonNull(transaction) && transaction.isActive()) {
            transaction.rollback();
        }
    }
}
