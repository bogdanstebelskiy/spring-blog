package com.group.blog_project.dto.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class TagRequestDTO {
    @NotBlank(message = "Tag name is required and cannot be empty")
    private String name;
}

