package com.group.blog_project.mapper;

import com.group.blog_project.dto.Like.LikeResponseDTO;
import com.group.blog_project.model.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {
    LikeResponseDTO toResponseDto(Like like) {
        return new LikeResponseDTO(like.getId(), like.getUser().getId(), like.getPost().getId(), like.getCreatedAt());
    };
}

