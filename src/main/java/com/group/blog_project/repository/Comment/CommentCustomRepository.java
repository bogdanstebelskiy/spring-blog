package com.group.blog_project.repository.Comment;

import com.group.blog_project.model.Comment;

import java.util.List;
import java.util.UUID;

public interface CommentCustomRepository {
    List<Comment> findByPostId(UUID postId);
}
