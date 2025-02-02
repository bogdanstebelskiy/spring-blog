package com.group.blog_project.service;

import com.group.blog_project.dto.Like.LikeRequestDTO;
import com.group.blog_project.dto.Like.LikeResponseDTO;
import com.group.blog_project.dto.Post.PostRequestDTO;
import com.group.blog_project.dto.Post.PostResponseDTO;
import com.group.blog_project.mapper.PostMapper;
import com.group.blog_project.model.*;
import com.group.blog_project.repository.Category.CategoryRepository;
import com.group.blog_project.repository.Like.LikeRepository;
import com.group.blog_project.repository.Post.PostRepository;
import com.group.blog_project.repository.Tag.TagRepository;
import com.group.blog_project.repository.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final LikeRepository likeRepository;
    private final CategoryRepository categoryRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO dto) {
        Category category = categoryRepository.findByName(dto.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with name=%s was not found!", dto.getCategoryName())));

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id=%s was not found!", dto.getAuthorId())));

        List<Tag> tags = dto.getTagNames().stream()
                .map(tagName -> tagRepository.findFirstByName(tagName)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with name=%s was not found!", tagName))))
                        .toList();

        // maybe remove builder, and add all args constructor
        Post newPost = Post.builder()
                .category(category)
                .author(author)
                .tags(tags)
                .title(dto.getTitle())
                .content(dto.getContent())
                .comments(new ArrayList<>())
                .views(new ArrayList<>())
                .likes(new ArrayList<>())
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();

        Post savedPost = postRepository.save(newPost);

        return postMapper.toResponseDto(savedPost);
    }


    @Transactional
    public PostResponseDTO updatePost(PostRequestDTO dto, UUID id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id=%s was not found!", id)));

        Category category = categoryRepository.findByName(dto.getCategoryName())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with name=%s was not found!", dto.getCategoryName())));

        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id=%s was not found!", dto.getAuthorId())));

        List<Tag> tags = dto.getTagNames().stream()
                .map(tagName -> tagRepository.findFirstByName(tagName)
                        .orElseThrow(() -> new EntityNotFoundException(String.format("Tag with name=%s was not found!", tagName)))).collect(Collectors.toList());

        existingPost.setCategory(category);
        existingPost.setAuthor(author);
        existingPost.setTags(tags);
        existingPost.setTitle(dto.getTitle());
        existingPost.setContent(dto.getContent());

        Post updatedPost = postRepository.save(existingPost);

        return postMapper.toResponseDto(updatedPost);
    }


    public void deletePost(UUID id) {
        postRepository.deleteById(id);
    }

    public List<PostResponseDTO> getAllPosts(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return postRepository.findAll(pageable).stream().map(postMapper::toResponseDto).toList();
    }

    public PostResponseDTO getPostById(UUID id) {
        Post post = postRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(String.format("Post with id=%s was not found!", id)));

        return postMapper.toResponseDto(post);
    }

    public List<PostResponseDTO> getPostsByKeyword(String name) {
        List<Post> posts = postRepository.findByKeyword(name);
        if(posts.isEmpty()){
            throw new EntityNotFoundException(String.format("No posts with keyword=%s was found!", name));
        }
        return posts.stream().map(postMapper::toResponseDto).toList();
    }

    @Transactional
    public LikeResponseDTO addLikeToPost(LikeRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with id=%s was not found!", dto.getUserId())));

        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id=%s was not found!", dto.getPostId())));

        Like like = Like.builder().user(user).post(post).build();

        Like savedLike = likeRepository.save(like);
        return new LikeResponseDTO(savedLike.getId(), savedLike.getUser().getId(), savedLike.getPost().getId(), savedLike.getCreatedAt());
    }

    public void removeLikeFromPost(LikeRequestDTO dto) {
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Post with id=%s was not found!", dto.getPostId())));

        Like like = post.getLikes().stream()
                .filter(l -> l.getUser().getId().equals(dto.getUserId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Like not found for this post and user"));

        likeRepository.deleteById(like.getId());
    }

}
