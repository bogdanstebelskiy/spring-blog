package com.group.blog_project.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Username is required and cannot be empty")
    private String username;

    @NotBlank(message = "Password is required and cannot be empty")
    private String passwordHash;
}

