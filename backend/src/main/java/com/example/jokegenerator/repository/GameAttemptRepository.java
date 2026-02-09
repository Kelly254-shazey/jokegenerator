package com.example.jokegenerator.repository;

import com.example.jokegenerator.entity.GameAttempt;
import com.example.jokegenerator.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameAttemptRepository extends JpaRepository<GameAttempt, Long> {
    List<GameAttempt> findByRoom(Room room);
}
