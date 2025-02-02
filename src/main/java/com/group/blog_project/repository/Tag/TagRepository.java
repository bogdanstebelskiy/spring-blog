package com.group.blog_project.repository.Tag;

import com.group.blog_project.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID>, TagCustomRepository {
}
