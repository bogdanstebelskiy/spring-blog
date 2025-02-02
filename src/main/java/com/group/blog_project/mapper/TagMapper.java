package com.group.blog_project.mapper;

import com.group.blog_project.dto.Tag.TagResponseDTO;
import com.group.blog_project.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    TagResponseDTO toResponseDto(Tag tag) {
        return new TagResponseDTO(tag.getId(), tag.getName());
    }
}
