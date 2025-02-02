package com.group.blog_project.dto.User;

import com.group.blog_project.dto.Role.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String username;
    private String email;
    private String avatarUrl;
    private List<RoleResponseDTO> roles;
    private Date createdAt;
    private Date updatedAt;
}