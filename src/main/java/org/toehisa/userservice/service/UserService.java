package org.toehisa.userservice.service;

import org.toehisa.userservice.data.dto.UserRequestDto;
import org.toehisa.userservice.data.dto.UserResponseDto;
import java.util.List;
import java.util.UUID;


public interface UserService {
    UserResponseDto createUser(UserRequestDto dto);
    UserResponseDto getUserById(UUID id);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(UUID id, UserRequestDto dto);
    void deleteUser(UUID id);
}
