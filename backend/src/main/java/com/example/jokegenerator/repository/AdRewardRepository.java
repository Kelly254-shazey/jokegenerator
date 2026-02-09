package com.example.jokegenerator.repository;

import com.example.jokegenerator.entity.AdReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface AdRewardRepository extends JpaRepository<AdReward, Long> {
    int countByUserIdAndWatchedAtAfter(Long userId, LocalDateTime watchedAt);
}