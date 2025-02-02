package com.group.blog_project.dto.Like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDTO {
    private UUID id;
    private UUID userId;
    private UUID postId;
    private Date createdAt;
}