package com.example.jokegenerator.controller;

import com.example.jokegenerator.entity.User;
import com.example.jokegenerator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * Authentication controller for BrainBreak - a fun game app
 * Simple authentication without complex security for casual gaming
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Login endpoint - simplified for casual gaming
     * Returns user info or creates a new user if not exists
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String email = request.getEmail();
            
            // For casual gaming, we allow login by email or create anonymous user
            if (email == null || email.trim().isEmpty()) {
                // Create anonymous user
                User anonymousUser = createAnonymousUser();
                return ResponseEntity.ok(Map.of(
                    "userId", anonymousUser.getId(),
                    "username", anonymousUser.getUsername(),
                    "email", anonymousUser.getEmail(),
                    "walletCoins", anonymousUser.getWalletCoins(),
                    "streakDays", anonymousUser.getStreakDays(),
                    "token", "anonymous_token_" + anonymousUser.getId(),
                    "message", "Welcome to BrainBreak! 🎉"
                ));
            }
            
            // Try to find existing user by email
            Optional<User> existingUser = userRepository.findByEmail(email);
            
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                return ResponseEntity.ok(Map.of(
                    "userId", user.getId(),
                    "username", user.getUsername(),
                    "email", user.getEmail(),
                    "walletCoins", user.getWalletCoins(),
                    "streakDays", user.getStreakDays(),
                    "token", "user_token_" + user.getId(),
                    "message", "Welcome back, " + user.getUsername() + "! 🎉"
                ));
            }
            
            // Create new user with provided email
            User newUser = new User();
            newUser.setUsername(request.getUsername() != null ? request.getUsername() : "Player");
            newUser.setEmail(email);
            newUser.setWalletCoins(120); // Starting coins
            newUser.setStreakDays(0);
            
            User savedUser = userRepository.save(newUser);
            
            return ResponseEntity.ok(Map.of(
                "userId", savedUser.getId(),
                "username", savedUser.getUsername(),
                "email", savedUser.getEmail(),
                "walletCoins", savedUser.getWalletCoins(),
                "streakDays", savedUser.getStreakDays(),
                "token", "user_token_" + savedUser.getId(),
                "message", "Welcome to BrainBreak, " + savedUser.getUsername() + "! 🎉"
            ));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Create an anonymous user for quick gameplay
     */
    private User createAnonymousUser() {
        User anonymousUser = new User();
        anonymousUser.setUsername("Guest_" + System.currentTimeMillis() % 10000);
        anonymousUser.setEmail("guest" + System.currentTimeMillis() + "@brainbreak.com");
        anonymousUser.setWalletCoins(120);
        anonymousUser.setStreakDays(0);
        return userRepository.save(anonymousUser);
    }

    /**
     * Request body for login
     */
    public static class LoginRequest {
        private String email;
        private String username;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
