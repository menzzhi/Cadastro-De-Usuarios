package com.projeto.application.dto.post;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserPostDto(
        String username,
        String email,
        String password) {
}
