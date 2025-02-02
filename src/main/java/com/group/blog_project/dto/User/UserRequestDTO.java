package com.group.blog_project.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Username is required and cannot be empty")
    private String username;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required and cannot be empty")
    private String email;

    private String avatarUrl;

    @NotNull(message = "Roles are required")
    @Size(min = 1, message = "At least one role must be provided")
    private List<String> roleNames;

    @NotBlank(message = "Password is required and cannot be empty")
    private String passwordHash;
}

