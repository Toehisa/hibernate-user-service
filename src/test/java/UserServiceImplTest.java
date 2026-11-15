import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.toehisa.userservice.data.dao.DataAccessObject;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.service.UserServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private DataAccessObject<User> userDao;

    @InjectMocks
    private UserServiceImpl userService;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(UUID.randomUUID());
        validUser.setName("Иван");
        validUser.setLastName("Иванов");
        validUser.setAge(25);
    }

    //ТЕСТЫ CREATE

    @Test
    @DisplayName("createUser: Должен вызывать userDao.save при валидном пользователе")
    void createUser_ValidUser_CallsDaoSave() {
        userService.createUser(validUser);
        verify(userDao, times(1)).save(validUser);
    }

    @Test
    @DisplayName("createUser: Должен выбрасывать исключение, если пользователь null")
    void createUser_NullUser_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(null));
        assertEquals("Пользователь не может быть null", ex.getMessage());
        verify(userDao, never()).save(any());
    }

    @Test
    @DisplayName("createUser: Должен выбрасывать исключение при невалидных данных (возраст < 12)")
    void createUser_InvalidAge_ThrowsException() {
        validUser.setAge(10);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(validUser));
        assertEquals("Возраст должен быть от 12 до 100 лет", ex.getMessage());
        verify(userDao, never()).save(any());
    }

    //ТЕСТЫ GET BY ID

    @Test
    @DisplayName("getUserById: Должен возвращать пользователя по ID")
    void getUserById_ValidId_ReturnsUser() {
        UUID id = UUID.randomUUID();
        when(userDao.findById(id)).thenReturn(validUser);

        User result = userService.getUserById(id);

        assertNotNull(result);
        assertEquals(validUser.getName(), result.getName());
        verify(userDao, times(1)).findById(id);
    }

    @Test
    @DisplayName("getUserById: Должен выбрасывать исключение при null ID")
    void getUserById_NullId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(null));
        verify(userDao, never()).findById(any());
    }

    //ТЕСТЫ GET ALL

    @Test
    @DisplayName("getAllUsers: Должен возвращать список пользователей")
    void getAllUsers_ReturnsList() {
        List<User> expectedList = List.of(validUser, new User());
        when(userDao.findAll()).thenReturn(expectedList);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userDao, times(1)).findAll();
    }

    //ТЕСТЫ UPDATE

    @Test
    @DisplayName("updateUser: Должен вызывать userDao.update при валидном пользователе")
    void updateUser_ValidUser_CallsDaoUpdate() {
        userService.updateUser(validUser);
        verify(userDao, times(1)).update(validUser);
    }

    @Test
    @DisplayName("updateUser: Должен падать на пустом имени")
    void updateUser_EmptyName_ThrowsException() {
        validUser.setName("   ");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.updateUser(validUser));
        assertEquals("Имя не может быть пустым", ex.getMessage());
        verify(userDao, never()).update(any());
    }

    //ТЕСТЫ DELETE

    @Test
    @DisplayName("deleteUser: Должен вызывать userDao.delete")
    void deleteUser_ValidUser_CallsDaoDelete() {
        // Устанавливаем ID через рефлексию или сеттер, так как метод deleteUser проверяет user.getId() != null
        // Для примера предполагаем, что есть сеттер или настраиваем заглушку, если это мок.
        // Если сеттера нет, используем mock(User.class)
        User spyUser = spy(validUser);
        when(spyUser.getId()).thenReturn(UUID.randomUUID());

        userService.deleteUser(spyUser);

        verify(userDao, times(1)).delete(spyUser);
    }

    @Test
    @DisplayName("deleteUser: Должен выбрасывать исключение, если ID = null")
    void deleteUser_NullId_ThrowsException() {
        User userWithoutId = new User();

        assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(userWithoutId));
        verify(userDao, never()).delete(any());
    }
}