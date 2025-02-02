package com.group.blog_project.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class RoleRequestDTO {
    @NotBlank(message = "Role name is required and cannot be empty")
    private String name;
}

