package com.example.Duinophile.repository;


import com.example.Duinophile.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PostRepository extends MongoRepository<Post, String> {


    List<Post> findByUserIdOrderByCreatedAtDesc(String userId);


    List<Post> findByIsPublicTrueOrderByCreatedAtDesc();


    List<Post> findByAchievementTypeAndIsPublicTrueOrderByCreatedAtDesc(String achievementType);


    List<Post> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String title, String description);

    long countByUserId(String userId);
}