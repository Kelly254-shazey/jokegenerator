package com.example.jokegenerator.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jokegenerator.entity.SinglePlayerGame;
import com.example.jokegenerator.entity.User;

@Repository
public interface SinglePlayerGameRepository extends JpaRepository<SinglePlayerGame, Long> {
    List<SinglePlayerGame> findByUserAndStatus(User user, SinglePlayerGame.GameStatus status);
}
