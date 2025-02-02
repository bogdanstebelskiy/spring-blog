package com.group.blog_project.mapper;

import com.group.blog_project.dto.Comment.CommentResponseDTO;
import com.group.blog_project.model.Comment;

import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentResponseDTO toResponseDto(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getPost().getId(),
                comment.getAuthor().getId(),
                comment.getAuthor().getUsername(),
                comment.getParentComment() != null ? comment.getParentComment().getId() : null,
                comment.getReplies().stream().map(this::toResponseDto).toList(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    /*public Comment toEntity(CommentRequestDTO dto, Post post, User author, Comment parentComment) {
        return Comment.builder()
                .post(post)
                .author(author)
                .parentComment(parentComment)
                .replies(new ArrayList<>())
                .content(dto.getContent())
                .build();
    }*/
}
