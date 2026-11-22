package org.toehisa.userservice.data.dto;


import org.toehisa.userservice.data.entities.User;
import java.time.LocalDateTime;
import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String lastName,
        String email,
        Integer age,
        LocalDateTime createdAt
) {
    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getCreatedAt()
        );
    }
}