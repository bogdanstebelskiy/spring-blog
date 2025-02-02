package com.group.blog_project.service;

import com.group.blog_project.dto.Comment.CommentRequestDTO;
import com.group.blog_project.dto.Comment.CommentResponseDTO;
import com.group.blog_project.mapper.CommentMapper;
import com.group.blog_project.model.Comment;
import com.group.blog_project.model.Post;
import com.group.blog_project.model.User;
import com.group.blog_project.repository.Comment.CommentRepository;
import com.group.blog_project.repository.Post.PostRepository;
import com.group.blog_project.repository.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponseDTO createComment(CommentRequestDTO dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id=%s was not found!", dto.getPostId())));

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id=%s was not found!", dto.getAuthorId())));

        Comment parentComment = null;
        if (dto.getParentCommentId() != null) {
            parentComment = commentRepository.findById(dto.getParentCommentId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Parent comment with id=%s was not found!", dto.getParentCommentId())));
        }

        Comment newComment = Comment.builder()
                .post(post)
                .author(author)
                .parentComment(parentComment)
                .replies(new ArrayList<>())
                .content(dto.getContent())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        Comment savedComment = commentRepository.save(newComment);

        return commentMapper.toResponseDto(savedComment);
    }

    @Transactional
    public CommentResponseDTO updateComment(UUID commentId, CommentRequestDTO dto) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Comment with id=%s was not found!", commentId)));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id=%s was not found!", dto.getPostId())));

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id=%s was not found!", dto.getAuthorId())));

        Comment parentComment = commentRepository.findById(dto.getParentCommentId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Parent comment with id=%s was not found!", dto.getParentCommentId())));

        existingComment.setPost(post);
        existingComment.setAuthor(author);
        existingComment.setParentComment(parentComment);
        existingComment.setContent(dto.getContent());
        existingComment.setUpdatedAt(new Date());

        Comment updatedComment = commentRepository.save(existingComment);

        return commentMapper.toResponseDto(updatedComment);
    }

    public void deleteComment(UUID commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<CommentResponseDTO> getCommentsByPostId(UUID postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        if (comments.isEmpty()) {
            throw new EntityNotFoundException(String.format("No comments found for post with id=%s!", postId));
        }
        return comments.stream()
                .map(commentMapper::toResponseDto)
                .toList();
    }

    public CommentResponseDTO getCommentById(UUID commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Comment with id=%s was not found!", commentId)));
        return commentMapper.toResponseDto(comment);
    }

}

