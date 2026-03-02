package com.example.Duinophile.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "posts")
public class Post {

    @Id
    private String id;

    @Indexed
    private String userId;

    private String username;

    private String achievementType;

    private String title;

    private String description;

    private String imageUrl;

    private int levelOrValue;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isPublic = true;



    private int likesCount = 0;
    private int commentsCount = 0;

}
