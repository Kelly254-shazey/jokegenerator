package com.example.jokegenerator.controller;

import com.example.jokegenerator.service.AIJokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000")
public class AIController {

    @Autowired
    private AIJokeService aiJokeService;

    @GetMapping("/joke")
    public ResponseEntity<?> generateAIJoke(
            @RequestParam(defaultValue = "General") String category,
            @RequestParam(defaultValue = "en") String language) {
        try {
            String joke = aiJokeService.generateAIJoke(category, language);
            return ResponseEntity.ok(Map.of(
                "joke", joke,
                "category", category,
                "language", language,
                "aiGenerated", true,
                "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/chat")
    public ResponseEntity<?> chatWithAI(@RequestBody Map<String, String> payload) {
        try {
            String userMessage = payload.get("message");
            if (userMessage == null || userMessage.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty"));
            }
            
            String aiResponse = aiJokeService.chatWithAI(userMessage);
            return ResponseEntity.ok(Map.of(
                "response", aiResponse,
                "timestamp", System.currentTimeMillis()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
