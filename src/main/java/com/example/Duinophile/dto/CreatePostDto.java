package com.example.Duinophile.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePostDto {

    @NotBlank(message = "userId is required")
    private String userId;

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "achievementType is required")
    private String achievementType;

    @NotBlank(message = "title is required")
    private String title;

    private String description;

    private String imageUrl;

    private Integer levelOrValue;

    @NotNull(message = "isPublic is required")
    private Boolean isPublic = true;
}