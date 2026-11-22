package org.toehisa.userservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

// Импорт общих сущностей
import org.toehisa.userservice.data.entities.User;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InheritanceMappingTest {

    @Test
    @DisplayName("2. SINGLE_TABLE (Одна таблица на всех)")
    void testSingleTableStrategy() {
        Class<?>[] classes = {
                User.class,
                org.toehisa.userservice.data.entities.second.Payment.class,
                org.toehisa.userservice.data.entities.second.CardPayment.class,
                org.toehisa.userservice.data.entities.second.CryptoPayment.class
        };

        runInheritanceTest("SINGLE_TABLE (Одна таблица)", classes, session -> {
            User user = createUser(session);

            org.toehisa.userservice.data.entities.second.CardPayment card = new org.toehisa.userservice.data.entities.second.CardPayment();
            card.setAmount(new BigDecimal("1000.00"));
            card.setCardNumber("1111-2222-3333-4444");
            card.setUser(user);
            session.persist(card);

            org.toehisa.userservice.data.entities.second.CryptoPayment crypto = new org.toehisa.userservice.data.entities.second.CryptoPayment();
            crypto.setAmount(new BigDecimal("0.5"));
            crypto.setWalletAddress("bc1qxy2kgdy...");
            crypto.setUser(user);
            session.persist(crypto);
        });
    }

    @Test
    @DisplayName("3. JOINED (Одна таблица через join)")
    void testJoinedStrategy() {
        Class<?>[] classes = {
                User.class,
                org.toehisa.userservice.data.entities.third.Payment.class,
                org.toehisa.userservice.data.entities.third.CardPayment.class,
                org.toehisa.userservice.data.entities.third.CryptoPayment.class
        };

        runInheritanceTest("JOINED (Таблицы через JOIN)", classes, session -> {
            User user = createUser(session);

            org.toehisa.userservice.data.entities.third.CardPayment card = new org.toehisa.userservice.data.entities.third.CardPayment();
            card.setAmount(new BigDecimal("1000.00"));
            card.setCardNumber("1111-2222-3333-4444");
            card.setUser(user);
            session.persist(card);

            org.toehisa.userservice.data.entities.third.CryptoPayment crypto = new org.toehisa.userservice.data.entities.third.CryptoPayment();
            crypto.setAmount(new BigDecimal("0.5"));
            crypto.setWalletAddress("bc1qxy2kgdy...");
            crypto.setUser(user);
            session.persist(crypto);
        });
    }

    @Test
    @DisplayName("4. TABLE_PER_CLASS (Каждому по таблице)")
    void testTablePerClassStrategy() {
        Class<?>[] classes = {
                User.class,
                org.toehisa.userservice.data.entities.fourth.Payment.class,
                org.toehisa.userservice.data.entities.fourth.CardPayment.class,
                org.toehisa.userservice.data.entities.fourth.CryptoPayment.class
        };

        runInheritanceTest("TABLE_PER_CLASS (Каждому классу по таблице)", classes, session -> {
            User user = createUser(session);

            org.toehisa.userservice.data.entities.fourth.CardPayment card = new org.toehisa.userservice.data.entities.fourth.CardPayment();
            card.setAmount(new BigDecimal("1000.00"));
            card.setCardNumber("1111-2222-3333-4444");
            card.setUser(user);
            session.persist(card);

            org.toehisa.userservice.data.entities.fourth.CryptoPayment crypto = new org.toehisa.userservice.data.entities.fourth.CryptoPayment();
            crypto.setAmount(new BigDecimal("0.5"));
            crypto.setWalletAddress("bc1qxy2kgdy...");
            crypto.setUser(user);
            session.persist(crypto);
        });
    }

    // --- Инфраструктурные методы ---

    private void runInheritanceTest(String strategyName, Class<?>[] annotatedClasses, HibernateAction action) {
        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.show_sql", "false");

        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        for (Class<?> clazz : annotatedClasses) {
            configuration.addAnnotatedClass(clazz);
        }

        try (SessionFactory sessionFactory = configuration.buildSessionFactory(); Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            action.execute(session);

            transaction.commit();

            printDatabaseSchema(session, strategyName);
        }
    }

    private User createUser(Session session) {
        User user = new User();
        user.setName("Валера");
        user.setLastName("Жмышенко");
        user.setAge(25);
        session.persist(user);
        return user;
    }

    private void printDatabaseSchema(Session session, String inheritanceType) {
        String sql = "SELECT table_name, column_name, data_type " +
                "FROM information_schema.columns " +
                "WHERE table_schema = 'public' " +
                "AND table_name LIKE '%payment%' " +
                "ORDER BY table_name, ordinal_position";

        List<Object[]> columns = session.createNativeQuery(sql, Object[].class).list();

        System.out.println("\n=======================================================");
        System.out.println(" СТРУКТУРА ТАБЛИЦ В POSTGRESQL ДЛЯ: " + inheritanceType);
        System.out.println("=======================================================");

        Assertions.assertFalse(columns.isEmpty(), "Таблицы не найдены! Проверь маппинг.");

        String currentTable = "";
        for (Object[] row : columns) {
            String tableName = (String) row[0];
            String columnName = (String) row[1];
            String dataType = (String) row[2];

            if (!tableName.equals(currentTable)) {
                System.out.println("\nТаблица: [ " + tableName.toUpperCase() + " ]");
                System.out.println("-------------------------------------------------------");
                System.out.printf("| %-25s | %-20s |\n", "КОЛОНКА", "ТИП ДАННЫХ");
                System.out.println("-------------------------------------------------------");
                currentTable = tableName;
            }
            System.out.printf("| %-25s | %-20s |\n", columnName, dataType);
        }
        System.out.println("-------------------------------------------------------\n");
    }

    @FunctionalInterface
    private interface HibernateAction {
        void execute(Session session);
    }
}