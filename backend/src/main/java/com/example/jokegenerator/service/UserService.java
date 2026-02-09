package com.example.jokegenerator.service;

import com.example.jokegenerator.entity.User;
import com.example.jokegenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Find user by username
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Register a new user
     */
    public User registerUser(String username, String email, String password) {
        // Check if user already exists
        Optional<User> existing = userRepository.findByUsername(username);
        if (existing.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // In production, use BCryptPasswordEncoder
        user.setWalletCoins(0);
        user.setTotalJokesLiked(0);
        user.setGameWins(0);

        return userRepository.save(user);
    }

    /**
     * Get user by ID
     */
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Save user
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Get user by ID (legacy)
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Update wallet coins
     */
    public void updateWallet(User user, int amount) {
        user.setWalletCoins(user.getWalletCoins() + amount);
        userRepository.save(user);
    }

    /**
     * Update user profile
     */
    public User updateUserProfile(User user) {
        return userRepository.save(user);
    }

    /**
     * Increment game wins
     */
    public void incrementGameWins(User user) {
        user.setGameWins(user.getGameWins() + 1);
        userRepository.save(user);
    }

    /**
     * Increment likes
     */
    public void incrementJokesLiked(User user) {
        user.setTotalJokesLiked(user.getTotalJokesLiked() + 1);
        userRepository.save(user);
    }
}