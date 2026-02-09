package com.example.jokegenerator.service;

import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final String[] ROTATING_MESSAGES = {
        "Time for a BrainBreak! 🧠 New jokes are waiting.",
        "Can you guess the number in less than 5 tries? 🔢",
        "Don't forget to claim your daily coin reward! 💰",
        "Stress level high? Laugh it off with a Dad Joke. 🤣"
    };

    private final Random random = new Random();

    // Runs every 2 hours
    @Scheduled(cron = "0 0 */2 * * *")
    public void schedulePeriodicNotifications() {
        String message = ROTATING_MESSAGES[random.nextInt(ROTATING_MESSAGES.length)];
        
        // Logic to fetch users who haven't been active in the last 2 hours
        // and send them a push notification (via Firebase/OneSignal)
        // For now, we log it.
        System.out.println("Scheduled Notification: " + message);
        
        // In a real implementation:
        // List<User> inactiveUsers = userRepository.findInactiveSince(LocalDateTime.now().minusHours(2));
        // notificationProvider.sendBatch(inactiveUsers, "BrainBreak", message);
    }
    
    public void sendImmediateNotification(Long userId, String message) {
        // Send specific notification (e.g., "Your turn!")
        System.out.println("Sending to user " + userId + ": " + message);
    }
}