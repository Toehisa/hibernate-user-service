package org.toehisa.userservice.singltones;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.toehisa.userservice.entities.User;

//Нужен чтобы фабрика сессий существовала в 1 экземпляре, для того чтобы не забивать пул соединений другими экземплярами
public class HibernateSessionFactoryHolder {
    private static final SessionFactory sessionFactory;//Инициализируется 1 раз в объекте рефлексии без возможности подмены ссылки

    static {
        try {
            Configuration config = new Configuration();//Пустой конфигуратор, сам читает hibernate.properties при инициализации
            config.addAnnotatedClass(User.class);//Передаем в конфигуратор метаданные класса User, конфигуратор сам при помощи рефлексии мапит поля в "кэш"
            sessionFactory = config.buildSessionFactory();//Билдим фабрику и открываем пулл сокетов
        } catch (Throwable e) {
            System.err.println("SessionFactory hasn't been initialized: " + e);
            throw new ExceptionInInitializerError(e);//Пробрасываем ошибку на уровень jvm, чтобы остановить инициализацию
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}


