package org.toehisa.userservice.data.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.toehisa.userservice.data.entities.User;

import java.util.Objects;

import static org.toehisa.userservice.data.singltones.HibernateSessionFactoryHolder.getSessionFactory;

public class UserDataAccessObject implements DataAccessObject<User>{
    @Override
    public void save(User user) {
        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {//Достаем сессию из пула сессий
            transaction = session.beginTransaction();//Начинаем транзакцию в сессии
            session.persist(user);//IDENTITY сначала в бд потом в кеше, SEQUENCE, UUID сначала в кеше потом в бд
            transaction.commit();
        } catch (Exception e) {
            transactionIsBadRollback(transaction);
            e.printStackTrace();// Логируем ошибку или пробрасываем дальше
        }
    }

    @Override
    public User findById(Object id) {
        try(Session session = getSessionFactory().openSession()) {
            return session.get(User.class, id);//Передаем класс, который ищем, и сам ID
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(User user) {
        Transaction transaction = null;

        try(Session session = getSessionFactory().openSession()) {//Достаем сессию из пула сессий
            transaction = session.beginTransaction();//Начинаем транзакцию в сессии
            session.merge(user);//Обновляем запись в кеше
            transaction.commit();//После коммита в бд
        } catch (Exception e) {
            transactionIsBadRollback(transaction);
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try(Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction(); // Удаление тоже требует транзакции
            session.remove(user); // Удаляем объект из кеша
            transaction.commit();// Удаляем из бд
        } catch (Exception e) {
            transactionIsBadRollback(transaction);
            e.printStackTrace();
        }
    }

    private void transactionIsBadRollback(Transaction transaction) {
        if (Objects.nonNull(transaction)) {
            transaction.rollback();
        }
    }
}
