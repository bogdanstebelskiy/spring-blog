package com.group.blog_project.dto.Comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentRequestDTO {

    @NotNull(message = "Post ID is required")
    private UUID postId;

    @NotNull(message = "Author ID is required")
    private UUID authorId;

    private UUID parentCommentId;

    @NotEmpty(message = "Content cannot be empty")
    private String content;
}

