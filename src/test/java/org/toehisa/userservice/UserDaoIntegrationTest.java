package org.toehisa.userservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_pass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Должен сохранить пользователя, найти по ID, обновить и удалить")
    void shouldPerformCrudOperations() {
        // 1. SAVE
        User user = User.builder()
                .name("Валера")
                .lastName("Жмышенко")
                .age(25)
                .build();

        User savedUser = userRepository.saveAndFlush(user);
        Assertions.assertNotNull(savedUser.getId(), "ID должен был сгенерироваться!");

        // 2. FIND BY ID
        Optional<User> foundOpt = userRepository.findById(savedUser.getId());
        Assertions.assertTrue(foundOpt.isPresent());
        Assertions.assertEquals("Валера", foundOpt.get().getName());

        // 3. UPDATE
        User foundUser = foundOpt.get();
        foundUser.setName("Валерий");
        userRepository.saveAndFlush(foundUser);

        User updatedUser = userRepository.findById(savedUser.getId()).orElseThrow();
        Assertions.assertEquals("Валерий", updatedUser.getName(), "Имя должно было обновиться в БД");

        // 4. FIND ALL
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(1, users.size(), "В базе должен быть ровно 1 пользователь");

        // 5. DELETE
        userRepository.deleteById(updatedUser.getId());
        userRepository.flush();
        Assertions.assertTrue(userRepository.findById(savedUser.getId()).isEmpty(), "Пользователь должен был быть удален из БД");
    }
}