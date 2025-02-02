package com.group.blog_project.repository.Post;

import com.group.blog_project.model.Post;

import java.util.List;

public interface PostCustomRepository {
    List<Post> findByKeyword(String keyword);
}

