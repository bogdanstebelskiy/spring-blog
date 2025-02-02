package com.group.blog_project.repository.Category;

import com.group.blog_project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID>, CategoryCustomRepository {
}
