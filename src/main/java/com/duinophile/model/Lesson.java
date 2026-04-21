package com.duinophile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "lessons")
public class Lesson {

    @Id
    private String id;

    @NotBlank(message = "Lesson title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;

    @NotBlank(message = "Lesson content is required")
    @Size(min = 20, message = "Content must be at least 20 characters long")
    private String content;

    // Optional grouping reference — not required
    private String courseId;

    private List<QuizQuestion> quiz = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizQuestion {

        @NotBlank(message = "Quiz question text is required")
        @Size(min = 5, max = 50, message = "Question must be between 5 and 50 characters")
        private String question;

        @Size(min = 2, message = "At least 2 options are required")
        private List<String> options = new ArrayList<>();

        private int correctOptionIndex;
    }
}
