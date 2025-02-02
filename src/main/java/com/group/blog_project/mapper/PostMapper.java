package com.group.blog_project.mapper;

import com.group.blog_project.dto.Post.PostResponseDTO;
import com.group.blog_project.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMapper {
    private final CommentMapper commentMapper;
    private final LikeMapper likeMapper;
    private final TagMapper tagMapper;
    private final CategoryMapper categoryMapper;

    public PostResponseDTO toResponseDto(Post post) {
        return new PostResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getId(),
                post.getAuthor().getUsername(),
                categoryMapper.toResponseDto(post.getCategory()),
                post.getTags().stream().map(tagMapper::toResponseDto).toList(),
                post.getComments().stream().map(commentMapper::toResponseDto).toList(),
                post.getViews().size(),
                post.getLikes().stream().map(likeMapper::toResponseDto).toList(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }

    /*public Post toEntity(PostRequestDTO dto, User author, List<Tag> tags, Category category) {
       return Post.builder()
               .title(dto.getTitle())
               .content(dto.getContent())
               .author(author)
               .category(category)
               .tags(tags)
               .comments(new ArrayList<>())
               .views(new ArrayList<>())
               .likes(new ArrayList<>())
               .createdAt(new Date())
               .updatedAt(new Date())
               .build();
    }*/
}
