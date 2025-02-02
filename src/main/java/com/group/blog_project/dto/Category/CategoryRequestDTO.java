package com.group.blog_project.dto.Category;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryRequestDTO {
    @NotBlank(message = "Category name is required and cannot be empty")
    private String name;
}
