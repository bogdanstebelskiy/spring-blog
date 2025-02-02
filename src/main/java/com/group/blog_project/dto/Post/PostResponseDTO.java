package com.group.blog_project.dto.Post;

import com.group.blog_project.dto.Category.CategoryResponseDTO;
import com.group.blog_project.dto.Comment.CommentResponseDTO;
import com.group.blog_project.dto.Like.LikeResponseDTO;
import com.group.blog_project.dto.Tag.TagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostResponseDTO {
    private UUID id;
    private String title;
    private String content;
    private UUID authorId;
    private String authorUsername;
    private CategoryResponseDTO category;
    private List<TagResponseDTO> tags;
    private List<CommentResponseDTO> comments;
    private long viewsCount;
    private List<LikeResponseDTO> likes;
    private Date createdAt;
    private Date updatedAt;
}
