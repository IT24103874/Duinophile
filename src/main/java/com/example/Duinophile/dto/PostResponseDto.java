package com.example.Duinophile.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostResponseDto {
    private String id;
    private String userId;
    private String username;
    private String achievementType;
    private String title;
    private String description;
    private String imageUrl;
    private Integer levelOrValue;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likesCount;
    private int commentsCount;
}