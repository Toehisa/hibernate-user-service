import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.toehisa.userservice.data.dao.UserDataAccessObject;
import org.toehisa.userservice.data.entities.User;

import java.lang.reflect.Field;
import java.util.List;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_pass");

    private SessionFactory testSessionFactory;
    private UserDataAccessObject userDao;

    @BeforeAll
    void initTestEnvironment() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.addAnnotatedClass(User.class);

        testSessionFactory = configuration.buildSessionFactory();

        injectTestSessionFactoryIntoHolder(testSessionFactory);

        userDao = new UserDataAccessObject();
    }

    @AfterAll
    void tearDown() {
        if (testSessionFactory != null) {
            testSessionFactory.close();
        }
    }

    @Test
    @DisplayName("Должен сохранить пользователя, найти по ID, обновить и удалить")
    void shouldPerformCrudOperations() {
        //1. SAVE
        User user = new User();
        user.setName("Валера");
        user.setLastName("Жмышенко");
        user.setAge(25);

        userDao.save(user);
        Assertions.assertNotNull(user.getId(), "ID должен был сгенерироваться!");

        //2. FIND BY ID
        User foundUser = userDao.findById(user.getId());
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals("Валера", foundUser.getName());

        //3. UPDATE
        foundUser.setName("Валерий");
        userDao.update(foundUser);

        User updatedUser = userDao.findById(user.getId());
        Assertions.assertEquals("Валерий", updatedUser.getName(), "Имя должно было обновиться в БД");

        //4. FIND ALL
        List<User> users = userDao.findAll();
        Assertions.assertEquals(1, users.size(), "В базе должен быть ровно 1 пользователь");

        //5. DELETE
        userDao.delete(updatedUser);
        User deletedUser = userDao.findById(user.getId());
        Assertions.assertNull(deletedUser, "Пользователь должен был быть удален из БД");
    }

    /**
     * Этот метод нужен, чтобы подменить SessionFactory в HibernateSessionFactoryHolder.
     * Так как поле там, private static, мы используем рефлексию.
     */
    private void injectTestSessionFactoryIntoHolder(SessionFactory factory) throws Exception {
        Class<?> holderClass = Class.forName("org.toehisa.userservice.data.singltones.HibernateSessionFactoryHolder");

        Field sessionFactoryField = holderClass.getDeclaredField("sessionFactory");
        sessionFactoryField.setAccessible(true);
        sessionFactoryField.set(null, factory);
    }
}