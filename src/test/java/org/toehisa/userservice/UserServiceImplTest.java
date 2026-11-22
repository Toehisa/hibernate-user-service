package org.toehisa.userservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.service.UserServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.toehisa.userservice.data.dto.UserRequestDto;
import org.toehisa.userservice.data.dto.UserResponseDto;
import org.toehisa.userservice.data.repository.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private User validUser;
    private UserRequestDto validDto;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        validUser = User.builder()
                .name("Иван")
                .lastName("Иванов")
                .email("ivan@test.ru")
                .age(25)
                .build();
        validUser.setId(userId);

        validDto = new UserRequestDto("Иван", "Иванов", "ivan@test.ru", 25);
    }

    @Test
    @DisplayName("createUser: сохраняет валидного пользователя и возвращает DTO")
    void createUser_ValidDto_SavesAndReturnsDto() {
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        UserResponseDto result = userService.createUser(validDto);

        assertEquals(userId, result.id());
        assertEquals("Иван", result.name());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("createUser: 400 Bad Request, если DTO == null")
    void createUser_NullDto_ThrowsBadRequest() {
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.createUser(null));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("createUser: 400 Bad Request при возрасте < 12")
    void createUser_InvalidAge_ThrowsBadRequest() {
        UserRequestDto invalidDto = new UserRequestDto("Иван", "Иванов", "ivan@test.ru", 10);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.createUser(invalidDto));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("getUserById: возвращает DTO по существующему ID")
    void getUserById_ValidId_ReturnsDto() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(validUser));

        UserResponseDto result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals("Иван", result.name());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("getUserById: 404 Not Found, если пользователь не найден")
    void getUserById_NotFound_ThrowsNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.getUserById(userId));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("getAllUsers: возвращает список DTO")
    void getAllUsers_ReturnsList() {
        when(userRepository.findAll()).thenReturn(List.of(validUser, new User()));

        List<UserResponseDto> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("updateUser: обновляет поля и сохраняет")
    void updateUser_ValidDto_UpdatesAndSaves() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(validUser));
        when(userRepository.save(any(User.class))).thenReturn(validUser);

        UserRequestDto updatedDto = new UserRequestDto("Пётр", "Петров", "petr@test.ru", 30);
        UserResponseDto result = userService.updateUser(userId, updatedDto);

        assertEquals("Пётр", result.name());
        verify(userRepository, times(1)).save(validUser);
    }

    @Test
    @DisplayName("updateUser: 400 Bad Request на пустом имени")
    void updateUser_EmptyName_ThrowsBadRequest() {
        UserRequestDto invalidDto = new UserRequestDto("   ", "Иванов", "ivan@test.ru", 25);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.updateUser(userId, invalidDto));
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteUser: удаляет существующего пользователя")
    void deleteUser_Exists_CallsDeleteById() {
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("deleteUser: 404 Not Found, если пользователя нет")
    void deleteUser_NotExists_ThrowsNotFound() {
        when(userRepository.existsById(userId)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> userService.deleteUser(userId));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(userRepository, never()).deleteById(any());
    }
}