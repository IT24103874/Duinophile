package com.duinophile.repository;

import com.duinophile.model.course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface courseRepo extends MongoRepository<course, Integer> {
}