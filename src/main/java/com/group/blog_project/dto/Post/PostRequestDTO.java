package com.group.blog_project.dto.Post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PostRequestDTO {

    @NotNull(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotNull(message = "Content is required")
    @NotEmpty(message = "Content cannot be empty")
    @Size(min = 10, message = "Content should have at least 10 characters")
    private String content;

    @NotNull(message = "Author ID is required")
    private UUID authorId;

    @NotNull(message = "Category name is required")
    @NotEmpty(message = "Category name cannot be empty")
    private String categoryName;

    @NotNull(message = "At least one tag is required")
    @NotEmpty(message = "Tags names cannot be empty")
    private List<String> tagNames;
}
