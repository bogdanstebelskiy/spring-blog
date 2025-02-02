package com.group.blog_project.mapper;

import com.group.blog_project.dto.Role.RoleResponseDTO;
import com.group.blog_project.model.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    RoleResponseDTO toResponseDto(Role role) {
        return new RoleResponseDTO(role.getId(), role.getName());
    }
}
