package com.duinophile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
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
    private String title;

    @NotBlank(message = "Lesson content is required")
    private String content;

    private String courseId; // Reference to Course id
    private List<QuizQuestion> quiz = new ArrayList<>(); // Integrated Quiz

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuizQuestion {
        private String question;
        private List<String> options = new ArrayList<>();
        private int correctOptionIndex;
    }
}
