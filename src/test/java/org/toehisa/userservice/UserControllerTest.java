package org.toehisa.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import org.toehisa.userservice.controller.UserController;
import org.toehisa.userservice.data.dto.UserRequestDto;
import org.toehisa.userservice.data.dto.UserResponseDto;
import org.toehisa.userservice.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/users -> 201 Created и JSON с пользователем")
    void createUser_Returns201() throws Exception {
        UUID id = UUID.randomUUID();
        UserRequestDto request = new UserRequestDto("Иван", "Иванов", "ivan@test.ru", 25);
        UserResponseDto response = new UserResponseDto(id, "Иван", "Иванов", "ivan@test.ru", 25, LocalDateTime.now());

        when(userService.createUser(any(UserRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Иван"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    @DisplayName("GET /api/users/{id} -> 200 OK")
    void getById_Returns200() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponseDto response = new UserResponseDto(id, "Иван", "Иванов", "ivan@test.ru", 25, LocalDateTime.now());

        when(userService.getUserById(id)).thenReturn(response);

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.email").value("ivan@test.ru"));
    }

    @Test
    @DisplayName("GET /api/users/{id} -> 404 Not Found")
    void getById_NotFound_Returns404() throws Exception {
        UUID id = UUID.randomUUID();
        when(userService.getUserById(id))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"));

        mockMvc.perform(get("/api/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/users -> 200 OK и список")
    void getAll_Returns200() throws Exception {
        UserResponseDto u1 = new UserResponseDto(UUID.randomUUID(), "Иван", "Иванов", "ivan@test.ru", 25, LocalDateTime.now());
        when(userService.getAllUsers()).thenReturn(List.of(u1));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Иван"));
    }

    @Test
    @DisplayName("PUT /api/users/{id} -> 200 OK")
    void updateUser_Returns200() throws Exception {
        UUID id = UUID.randomUUID();
        UserRequestDto request = new UserRequestDto("Пётр", "Петров", "petr@test.ru", 30);
        UserResponseDto response = new UserResponseDto(id, "Пётр", "Петров", "petr@test.ru", 30, LocalDateTime.now());

        when(userService.updateUser(eq(id), any(UserRequestDto.class))).thenReturn(response);

        mockMvc.perform(put("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Пётр"))
                .andExpect(jsonPath("$.age").value(30));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} -> 204 No Content")
    void deleteUser_Returns204() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(userService).deleteUser(id);

        mockMvc.perform(delete("/api/users/{id}", id))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(id);
    }
}
