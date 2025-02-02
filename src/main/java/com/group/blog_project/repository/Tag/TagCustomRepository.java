package com.group.blog_project.repository.Tag;

import com.group.blog_project.model.Tag;

import java.util.Optional;

public interface TagCustomRepository {
    Optional<Tag> findFirstByName(String name);
}
