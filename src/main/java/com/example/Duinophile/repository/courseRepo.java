package com.example.Duinophile.repository;

import com.example.Duinophile.model.course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface courseRepo extends MongoRepository<course, Integer> {
}