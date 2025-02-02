package com.group.blog_project.repository.Category;

import com.group.blog_project.model.Category;

import java.util.Optional;

public interface CategoryCustomRepository {
    Optional<Category> findByName(String name);
}


