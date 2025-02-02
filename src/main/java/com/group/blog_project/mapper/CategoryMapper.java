package com.group.blog_project.mapper;

import com.group.blog_project.dto.Category.CategoryResponseDTO;
import com.group.blog_project.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    CategoryResponseDTO toResponseDto(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName());
    };
}
