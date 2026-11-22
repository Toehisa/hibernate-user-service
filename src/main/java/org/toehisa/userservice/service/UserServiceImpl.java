package org.toehisa.userservice.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.toehisa.userservice.data.entities.User;
import org.toehisa.userservice.data.dto.UserRequestDto;
import org.toehisa.userservice.data.dto.UserResponseDto;
import org.toehisa.userservice.data.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        validateDto(dto);
        User user = User.builder()
                .name(dto.name())
                .lastName(dto.lastName())
                .email(dto.email())
                .age(dto.age())
                .build();
        return UserResponseDto.from(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + id));
        return UserResponseDto.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::from)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UUID id, UserRequestDto dto) {
        validateDto(dto);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + id));

        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setAge(dto.age());

        return UserResponseDto.from(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден: " + id);
        }
        userRepository.deleteById(id);
    }

    private void validateDto(UserRequestDto dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь не может быть null");
        }
        if (dto.name() == null || dto.name().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Имя не может быть пустым");
        }
        if (dto.lastName() == null || dto.lastName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Фамилия не может быть пустой");
        }
        if (dto.age() == null || dto.age() < 12 || dto.age() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Возраст должен быть от 12 до 100 лет");
        }
    }
}