package com.group.blog_project.controller;

import com.group.blog_project.constants.ValidationConstants;
import com.group.blog_project.dto.Comment.CommentRequestDTO;
import com.group.blog_project.dto.Comment.CommentResponseDTO;
import com.group.blog_project.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments/postId={postId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPostId(
            @PathVariable UUID postId
    ) {
        List<CommentResponseDTO> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/commentId={commentId}")
    public ResponseEntity<CommentResponseDTO> getCommentById(
            @PathVariable UUID commentId
    ) {
        CommentResponseDTO comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDTO> createComment(
            @RequestBody @Valid CommentRequestDTO dto
    ) {
        CommentResponseDTO commentResponseDTO = commentService.createComment(dto);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> updateComment(
            @PathVariable UUID commentId,
            @RequestBody @Valid CommentRequestDTO dto
    ) {
        CommentResponseDTO updatedComment = commentService.updateComment(commentId, dto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable UUID commentId
    ) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
