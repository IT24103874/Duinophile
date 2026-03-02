package com.example.Duinophile.controller;

import com.example.Duinophile.dto.CreatePostDto;
import com.example.Duinophile.dto.PostResponseDto;
import com.example.Duinophile.model.Post;
import com.example.Duinophile.repository.PostRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto dto,
                                        BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldErrors().stream()
                    .map(e -> e.getField() + ": " + e.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(errorMsg);
        }

        Post post = new Post();
        post.setUserId(dto.getUserId());
        post.setUsername(dto.getUsername());
        post.setAchievementType(dto.getAchievementType());
        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());
        post.setImageUrl(dto.getImageUrl());
        post.setLevelOrValue(dto.getLevelOrValue());
        post.setPublic(dto.getIsPublic() != null ? dto.getIsPublic() : true);

        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        Post saved = postRepository.save(post);

        // Convert to response DTO
        PostResponseDto response = mapToResponseDto(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // READ - user's posts
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@PathVariable String userId) {
        List<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<PostResponseDto> dtos = posts.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // READ - single post
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable String id) {
        Optional<Post> opt = postRepository.findById(id);
        return opt.map(post -> ResponseEntity.ok(mapToResponseDto(post)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // READ - public feed
    @GetMapping("/feed")
    public ResponseEntity<List<PostResponseDto>> getPublicFeed(
            @RequestParam(defaultValue = "10") int limit) {

        List<Post> posts = postRepository.findByIsPublicTrueOrderByCreatedAtDesc();
        if (posts.size() > limit) {
            posts = posts.subList(0, limit);
        }

        List<PostResponseDto> dtos = posts.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable String id,
                                        @RequestBody CreatePostDto dto) {  // reuse DTO for simplicity

        Optional<Post> opt = postRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post existing = opt.get();

        // Only update fields that are provided (null-safe)
        if (dto.getTitle() != null) existing.setTitle(dto.getTitle());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getImageUrl() != null) existing.setImageUrl(dto.getImageUrl());
        if (dto.getIsPublic() != null) existing.setPublic(dto.getIsPublic());

        existing.setUpdatedAt(LocalDateTime.now());

        Post saved = postRepository.save(existing);
        return ResponseEntity.ok(mapToResponseDto(saved));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        if (!postRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Helper method to convert entity → DTO
    private PostResponseDto mapToResponseDto(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setUserId(post.getUserId());
        dto.setUsername(post.getUsername());
        dto.setAchievementType(post.getAchievementType());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setImageUrl(post.getImageUrl());
        dto.setLevelOrValue(post.getLevelOrValue());
        dto.setPublic(post.isPublic());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        dto.setLikesCount(post.getLikesCount());
        dto.setCommentsCount(post.getCommentsCount());
        return dto;
    }
}