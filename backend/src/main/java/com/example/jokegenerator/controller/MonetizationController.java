package com.example.jokegenerator.controller;

import com.example.jokegenerator.service.MonetizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/monetization")
@CrossOrigin(origins = "http://localhost:3000")
public class MonetizationController {

    @Autowired
    private MonetizationService monetizationService;

    @PostMapping("/watch-ad")
    public ResponseEntity<?> watchRewardedAd(@RequestBody Map<String, Object> payload) {
        try {
            Number userIdNum = (Number) payload.get("userId");
            Long userId = userIdNum.longValue();
            String adType = (String) payload.get("adType");
            
            int remaining = monetizationService.watchRewardedAd(userId, adType);
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Ad watched, coins rewarded!",
                "remainingAds", remaining
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/ad-status/{userId}")
    public ResponseEntity<?> getAdStatus(@PathVariable Long userId) {
        try {
            int remaining = monetizationService.getRemainingAds(userId);
            return ResponseEntity.ok(Map.of(
                "canWatchAd", remaining > 0,
                "remainingAds", remaining
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/daily-login/{userId}")
    public ResponseEntity<?> processDailyLogin(@PathVariable Long userId) {
        try {
            monetizationService.processDailyLogin(userId);
            return ResponseEntity.ok(Map.of("success", true, "message", "Daily bonus claimed!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PostMapping("/share-bonus")
    public ResponseEntity<?> awardShareBonus(@RequestBody Map<String, Object> payload) {
        try {
            Number userIdNum = (Number) payload.get("userId");
            Long userId = userIdNum.longValue();
            String contentType = (String) payload.getOrDefault("contentType", "joke");
            
            monetizationService.awardShareBonus(userId, contentType);
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Share bonus awarded!",
                "coinsEarned", MonetizationService.SHARE_BONUS
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}