package com.example.jokegenerator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "wallet_coins")
    private int walletCoins = 0;

    @Column(name = "is_premium")
    private boolean isPremium = false;

    @Column(name = "game_wins")
    private int gameWins = 0;

    @Column(name = "total_jokes_liked")
    private int totalJokesLiked = 0;

    @Column(name = "streak_days")
    private int streakDays = 0;

    @Column(name = "total_jokes_shared")
    private int totalJokesShared = 0;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public int getWalletCoins() { return walletCoins; }
    public void setWalletCoins(int walletCoins) { this.walletCoins = walletCoins; }
    
    public boolean isPremium() { return isPremium; }
    public void setPremium(boolean premium) { isPremium = premium; }
    
    public int getGameWins() { return gameWins; }
    public void setGameWins(int gameWins) { this.gameWins = gameWins; }
    
    public int getTotalJokesLiked() { return totalJokesLiked; }
    public void setTotalJokesLiked(int totalJokesLiked) { this.totalJokesLiked = totalJokesLiked; }
    
    public int getStreakDays() { return streakDays; }
    public void setStreakDays(int streakDays) { this.streakDays = streakDays; }
    
    public int getTotalJokesShared() { return totalJokesShared; }
    public void setTotalJokesShared(int totalJokesShared) { this.totalJokesShared = totalJokesShared; }
    
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}