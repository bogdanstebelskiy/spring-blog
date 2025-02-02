package com.group.blog_project.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RoleResponseDTO {
    private UUID id;
    private String name;
}
