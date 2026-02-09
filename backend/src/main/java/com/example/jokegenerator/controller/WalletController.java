package com.example.jokegenerator.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jokegenerator.entity.User;
import com.example.jokegenerator.service.UserService;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "http://localhost:3000")
public class WalletController {

    @Autowired
    private UserService userService;

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<?> getTransactions(@PathVariable Long userId) {
        List<Map<String, Object>> transactions = new ArrayList<>();
        
        // Mock transactions for demo
        transactions.add(createTransaction("AD_WATCH", "Watched rewarded ad", 25));
        transactions.add(createTransaction("SHARE_BONUS", "Shared a joke", 10));
        transactions.add(createTransaction("DAILY_BONUS", "Daily login bonus", 50));
        transactions.add(createTransaction("GAME_WIN", "Won single player game", 100));
        
        return ResponseEntity.ok(transactions);
    }

    private Map<String, Object> createTransaction(String type, String description, int amount) {
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("type", type);
        transaction.put("description", description);
        transaction.put("amount", amount);
        transaction.put("timestamp", System.currentTimeMillis());
        return transaction;
    }
}
