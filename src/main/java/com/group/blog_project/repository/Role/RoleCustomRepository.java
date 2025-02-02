package com.group.blog_project.repository.Role;

import com.group.blog_project.model.Role;

import java.util.Optional;

public interface RoleCustomRepository {
    Optional<Role> findFirstByName(String name);
}
