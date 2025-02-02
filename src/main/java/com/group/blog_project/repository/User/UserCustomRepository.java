package com.group.blog_project.repository.User;

import com.group.blog_project.model.Tag;
import com.group.blog_project.model.User;

import java.util.Optional;

public interface UserCustomRepository {
    Optional<User> findByUsername(String username);
}
