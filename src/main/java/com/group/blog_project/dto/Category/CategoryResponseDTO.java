package com.group.blog_project.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CategoryResponseDTO {
    private UUID id;
    private String name;
}
