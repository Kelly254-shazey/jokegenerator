package com.example.jokegenerator.repository;

import com.example.jokegenerator.entity.JokeReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JokeReactionRepository extends JpaRepository<JokeReaction, Long> {
    boolean existsByUserIdAndJokeIdAndReactionType(Long userId, Long jokeId, String reactionType);
}