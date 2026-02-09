package com.example.jokegenerator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.jokegenerator.entity.Joke;
import com.example.jokegenerator.service.JokeService;

@RestController
@RequestMapping("/api/jokes")
@CrossOrigin(origins = "http://localhost:3000")
public class JokeController {

    @Autowired
    private JokeService jokeService;

    /**
     * Get random joke with optional category filter
     */
    @GetMapping("/random")
    public ResponseEntity<Joke> getRandomJoke(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "false") boolean isPremium) {
        Joke joke = jokeService.getRandomJoke(category, isPremium);
        if (joke == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(joke);
    }

    /**
     * Get joke of the moment (trending)
     */
    @GetMapping("/moment")
    public ResponseEntity<Joke> getJokeOfTheMoment() {
        Joke joke = jokeService.getJokeOfTheMoment();
        if (joke == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(joke);
    }

    /**
     * Add new joke (with user upload)
     */
    @PostMapping("/add")
    public ResponseEntity<?> addJoke(@RequestBody JokeRequest request) {
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Joke content cannot be empty"));
        }
        
        if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Category is required"));
        }

        try {
            Joke joke = jokeService.addJoke(
                request.getCategory(),
                request.getContent(),
                request.isSafe(),
                request.isPremium(),
                request.getUploadedBy()
            );
            return ResponseEntity.ok(joke);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Failed to add joke: " + e.getMessage()));
        }
    }

    /**
     * Add reaction to joke
     */
    @PostMapping("/{jokeId}/react")
    public ResponseEntity<?> addReaction(
            @PathVariable Long jokeId,
            @RequestParam Long userId,
            @RequestParam String reactionType) {
        try {
            jokeService.addReaction(userId, jokeId, reactionType);
            return ResponseEntity.ok(new SuccessResponse("Reaction added successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Failed to add reaction: " + e.getMessage()));
        }
    }

    /**
     * Share joke on social media
     */
    @PostMapping("/{jokeId}/share")
    public ResponseEntity<?> shareJoke(
            @PathVariable Long jokeId,
            @RequestParam Long userId,
            @RequestParam(required = false) String platform) {
        try {
            String shareableContent = jokeService.shareJoke(userId, jokeId, platform);
            if (shareableContent == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(new ShareResponse(shareableContent));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Failed to share joke: " + e.getMessage()));
        }
    }

    /**
     * Search jokes by category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getJokesByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "false") boolean isPremium) {
        try {
            var jokes = jokeService.getJokesByCategory(category, isPremium);
            return ResponseEntity.ok(jokes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                new ErrorResponse("Failed to fetch jokes: " + e.getMessage()));
        }
    }

    // Inner classes for requests/responses
    public static class JokeRequest {
        private String category;
        private String content;
        private boolean safe;
        private boolean premium;
        private Long uploadedBy;

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        public boolean isSafe() { return safe; }
        public void setSafe(boolean safe) { this.safe = safe; }

        public boolean isPremium() { return premium; }
        public void setPremium(boolean premium) { this.premium = premium; }

        public Long getUploadedBy() { return uploadedBy; }
        public void setUploadedBy(Long uploadedBy) { this.uploadedBy = uploadedBy; }
    }

    public static class ErrorResponse {
        private String error;
        private long timestamp;

        public ErrorResponse(String error) {
            this.error = error;
            this.timestamp = System.currentTimeMillis();
        }

        public String getError() { return error; }
        public long getTimestamp() { return timestamp; }
    }

    public static class SuccessResponse {
        private String message;
        private long timestamp;

        public SuccessResponse(String message) {
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }

        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
    }

    public static class ShareResponse {
        private String content;
        private long timestamp;

        public ShareResponse(String content) {
            this.content = content;
            this.timestamp = System.currentTimeMillis();
        }

        public String getContent() { return content; }
        public long getTimestamp() { return timestamp; }
    }
}

