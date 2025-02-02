package com.group.blog_project.dto.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CommentResponseDTO {
    private UUID id;
    private UUID postId;
    private UUID authorId;
    private String authorUsername;
    private UUID parentCommentId;
    private List<CommentResponseDTO> replies;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
