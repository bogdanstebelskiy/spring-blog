package com.group.blog_project.mapper;

import com.group.blog_project.dto.User.UserResponseDTO;
import com.group.blog_project.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final RoleMapper roleMapper;

    public UserResponseDTO toResponseDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getRoles().stream().map(roleMapper::toResponseDto).toList(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
