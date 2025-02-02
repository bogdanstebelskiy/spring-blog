package com.group.blog_project.controller;

import com.group.blog_project.constants.ValidationConstants;
import com.group.blog_project.dto.Like.LikeRequestDTO;
import com.group.blog_project.dto.Like.LikeResponseDTO;
import com.group.blog_project.dto.Post.PostRequestDTO;
import com.group.blog_project.dto.Post.PostResponseDTO;
import com.group.blog_project.model.Like;
import com.group.blog_project.service.PostService;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts(
            @RequestParam(defaultValue = "0", required = false)
            @Min(value = 0, message = "Page number must be greater than or equal to 0") int page,

            @RequestParam(defaultValue = "10", required = false)
            @Min(value = 1, message = "Limit must be greater than or equal to 1")
            @Max(value = 100, message = "Limit must not exceed 100") int limit
    ) {
        List<PostResponseDTO> postResponseDTOs = postService.getAllPosts(page, limit);
        return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
    }


    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> getPostById(
            @PathVariable UUID postId
    ) {
        PostResponseDTO postResponseDTO = postService.getPostById(postId);
        return new ResponseEntity<>(postResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/posts/keyword={postName}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByKeyword(
           @PathVariable @NotEmpty(message = "Post name must not be empty") String postName
    ) {
        List<PostResponseDTO> postResponseDTOs = postService.getPostsByKeyword(postName);
        return new ResponseEntity<>(postResponseDTOs, HttpStatus.OK);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> createPost(
            @RequestBody @Valid PostRequestDTO dto
    ) {
        PostResponseDTO postResponseDTO = postService.createPost(dto);
        return new ResponseEntity<>(postResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @RequestBody @Valid PostRequestDTO dto,
            @PathVariable UUID postId
    ){
        PostResponseDTO updatedPostResponse = postService.updatePost(dto, postId);
        return new ResponseEntity<>(updatedPostResponse, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable UUID postId
    ) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/posts/like")
    public ResponseEntity<LikeResponseDTO> likePost(
            @RequestBody @Valid LikeRequestDTO dto
    ) {
        LikeResponseDTO likeResponseDTO = postService.addLikeToPost(dto);
        return new ResponseEntity<>(likeResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/posts/like")
    public ResponseEntity<Void> unlikePost(
            @RequestBody @Valid LikeRequestDTO dto
    ) {
        postService.removeLikeFromPost(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
