import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

// Импортируй нужный пакет для тестирования конкретной стратегии
import org.toehisa.userservice.data.entities.User;
//import org.toehisa.userservice.data.entities.second.*;
//import org.toehisa.userservice.data.entities.third.*;
import org.toehisa.userservice.data.entities.fourth.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InheritanceMappingTest {

    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeAll
    void initHibernate() {
        Configuration configuration = new Configuration();

        configuration
                .addAnnotatedClass(org.toehisa.userservice.data.entities.User.class)

                .addAnnotatedClass(org.toehisa.userservice.data.entities.fourth.Payment.class)
                .addAnnotatedClass(org.toehisa.userservice.data.entities.fourth.CardPayment.class)
                .addAnnotatedClass(org.toehisa.userservice.data.entities.fourth.CryptoPayment.class);

        sessionFactory = configuration.buildSessionFactory();
    }

    @AfterAll
    void closeHibernate() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @AfterEach
    void closeSession() {
        // Если транзакция еще активна (не была закоммичена в тесте), откатываем её
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }
        if (session != null) {
            session.close();
        }
    }

    @Test
    @DisplayName("Проверка генерации схемы БД для стратегии наследования")
    void shouldGenerateAndPrintCorrectDatabaseSchema() {

        User user = new User();
        user.setName("Джон");
        user.setLastName("Доу");
        user.setAge(25);
        session.persist(user);

        CardPayment card = new CardPayment();
        card.setAmount(new BigDecimal("1000.00"));
        card.setCardNumber("1111-2222-3333-4444");
        card.setUser(user);
        session.persist(card);

        CryptoPayment crypto = new CryptoPayment();
        crypto.setAmount(new BigDecimal("0.5"));
        crypto.setWalletAddress("bc1qxy2kgdy...");
        crypto.setUser(user);
        session.persist(crypto);

        transaction.commit();
        printDatabaseSchema();
    }

    private void printDatabaseSchema() {
        System.out.println("\n=======================================================");
        System.out.println(" СТРУКТУРА ТАБЛИЦ В POSTGRESQL ДЛЯ ТЕКУЩЕЙ СТРАТЕГИИ");
        System.out.println("=======================================================");

        String sql = "SELECT table_name, column_name, data_type " +
                "FROM information_schema.columns " +
                "WHERE table_schema = 'public' " +
                "AND table_name IN ('users', 'payments', 'payment', 'cardpayment', 'cryptopayment') " +
                "ORDER BY table_name, ordinal_position";

        List<Object[]> columns = session.createNativeQuery(sql, Object[].class).list();

        Assertions.assertFalse(columns.isEmpty(), "Таблицы не найдены! Проверь настройки маппинга и hbm2ddl.auto");

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
}