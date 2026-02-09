package com.example.jokegenerator.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jokegenerator.entity.Room;
import com.example.jokegenerator.entity.SinglePlayerGame;
import com.example.jokegenerator.entity.User;
import com.example.jokegenerator.service.GameService;
import com.example.jokegenerator.service.UserService;

@RestController
@RequestMapping("/api/games")
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @PostMapping("/single/start")
    public ResponseEntity<?> startSinglePlayer(@RequestBody Map<String, Object> payload) {
        try {
            Number userIdNum = (Number) payload.get("userId");
            Long userId = userIdNum != null ? userIdNum.longValue() : 1L;
            
            User user = userService.findById(userId).orElse(createDefaultUser(userId));
            SinglePlayerGame game = gameService.startSinglePlayerGame(user);
            return ResponseEntity.ok(game);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/single/guess/{gameId}")
    public ResponseEntity<?> guessSinglePlayer(
            @PathVariable Long gameId,
            @RequestBody Map<String, Object> payload) {
        try {
            Number guessNum = (Number) payload.get("guess");
            Number userIdNum = (Number) payload.get("userId");
            
            int guess = guessNum.intValue();
            Long userId = userIdNum != null ? userIdNum.longValue() : 1L;
            
            User user = userService.findById(userId).orElse(createDefaultUser(userId));
            String feedback = gameService.guessSinglePlayer(gameId, guess, user);
            return ResponseEntity.ok(feedback);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/multi/create")
    public ResponseEntity<?> createRoom(@RequestBody Map<String, Object> payload) {
        try {
            Number userIdNum = (Number) payload.get("userId");
            Long userId = userIdNum != null ? userIdNum.longValue() : 1L;
            
            User user = userService.findById(userId).orElse(createDefaultUser(userId));
            Room room = gameService.createRoom(user);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/multi/join/{roomCode}")
    public ResponseEntity<?> joinRoom(
            @PathVariable String roomCode,
            @RequestBody Map<String, Object> payload) {
        try {
            Number userIdNum = (Number) payload.get("userId");
            Long userId = userIdNum != null ? userIdNum.longValue() : 1L;
            
            User user = userService.findById(userId).orElse(createDefaultUser(userId));
            Room room = gameService.joinRoom(roomCode, user);
            return ResponseEntity.ok(room);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/multi/guess/{roomId}")
    public ResponseEntity<?> guessMultiplayer(
            @PathVariable Long roomId,
            @RequestBody Map<String, Object> payload) {
        try {
            Number guessNum = (Number) payload.get("guess");
            Number userIdNum = (Number) payload.get("userId");
            
            int guess = guessNum.intValue();
            Long userId = userIdNum != null ? userIdNum.longValue() : 1L;
            
            User user = userService.findById(userId).orElse(createDefaultUser(userId));
            String feedback = gameService.guessMultiplayer(roomId, guess, user);
            return ResponseEntity.ok(feedback);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private User createDefaultUser(Long userId) {
        // Check if user already exists
        return userService.findById(userId).orElseGet(() -> {
            User user = new User();
            user.setId(userId);
            user.setUsername("Player_" + userId);
            user.setEmail("player" + userId + "@brainbreak.com");
            user.setPassword("default_password_" + userId);
            return userService.saveUser(user);
        });
    }
}
