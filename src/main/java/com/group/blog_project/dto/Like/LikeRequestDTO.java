package com.group.blog_project.dto.Like;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequestDTO {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotNull(message = "Post ID is required")
    private UUID postId;
}
