package com.group.blog_project.service;

import com.group.blog_project.dto.Comment.CommentResponseDTO;
import com.group.blog_project.mapper.CommentMapper;
import com.group.blog_project.model.Comment;
import com.group.blog_project.model.Post;
import com.group.blog_project.model.User;
import com.group.blog_project.repository.Comment.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(CommentServiceTest.class);

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private CommentService commentService;

    private UUID postId;
    private Comment comment;
    private CommentResponseDTO commentResponseDTO;

    @BeforeEach
    void setUp() {
        postId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        comment = Comment.builder()
                .id(UUID.randomUUID())
                .post(Post.builder().id(postId).build())
                .author(User.builder().id(authorId).build())
                .content("Test comment")
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        logger.info("Created test comment: {}", comment);

        commentResponseDTO = new CommentResponseDTO(
                comment.getId(),
                postId,
                authorId,
                "testUser",
                null,
                List.of(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
        logger.info("Created test commentResponseDTO: {}", commentResponseDTO);
    }

    @Test
    void getCommentsByPostId_ShouldReturnCommentsDto() {
        when(commentRepository.findByPostId(postId)).thenReturn(List.of(comment));
        when(commentMapper.toResponseDto(comment)).thenReturn(commentResponseDTO);

        List<CommentResponseDTO> result = commentService.getCommentsByPostId(postId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(commentResponseDTO.getId(), result.getFirst().getId());
        assertEquals(commentResponseDTO.getContent(), result.getFirst().getContent());

        verify(commentRepository, times(1)).findByPostId(postId);
        verify(commentMapper, times(1)).toResponseDto(comment);
    }
}
