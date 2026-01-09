package com.projeto.application.dto.response;

public record UserResponseDto(
        Long userId,
        String username,
        String email) {
}
