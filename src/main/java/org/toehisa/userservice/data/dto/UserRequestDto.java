package org.toehisa.userservice.data.dto;

public record UserRequestDto(
        String name,
        String lastName,
        String email,
        Integer age
) {}