package com.example.jokegenerator.repository;

import com.example.jokegenerator.entity.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<Joke, Long> {
    List<Joke> findByIsSafeTrue();
    List<Joke> findByIsSafeTrueAndIsPremiumFalse();
    List<Joke> findByCategoryAndIsSafeTrue(String category);
    List<Joke> findByCategoryAndIsSafeTrueAndIsPremiumFalse(String category);
    
    @Query("SELECT j FROM Joke j ORDER BY j.shareCount DESC")
    List<Joke> findTrendingJokes();
}