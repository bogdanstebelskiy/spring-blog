package com.group.blog_project.dto.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TagResponseDTO {
    private UUID id;
    private String name;
}
