package com.example.jokegenerator.service;

import com.example.jokegenerator.entity.AdReward;
import com.example.jokegenerator.entity.User;
import com.example.jokegenerator.entity.WalletTransaction;
import com.example.jokegenerator.repository.AdRewardRepository;
import com.example.jokegenerator.repository.UserRepository;
import com.example.jokegenerator.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class MonetizationService {

    public static final int SHARE_BONUS = 5;
    public static final int DAILY_BONUS = 10;
    public static final int AD_REWARD_COINS = 50;

    @Autowired private WalletTransactionRepository walletTransactionRepository;
    @Autowired private AdRewardRepository adRewardRepository;
    @Autowired private UserRepository userRepository;

    @Transactional
    public void awardShareBonus(Long userId, String contentType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update wallet
        user.setWalletCoins(user.getWalletCoins() + SHARE_BONUS);
        userRepository.save(user);

        // Record transaction
        WalletTransaction transaction = new WalletTransaction();
        transaction.setUser(user);
        transaction.setAmount(SHARE_BONUS);
        transaction.setType(WalletTransaction.TransactionType.reward);
        transaction.setDescription("Share bonus for " + contentType);
        walletTransactionRepository.save(transaction);
    }

    @Transactional
    public void recordAdWatch(Long userId, String adType) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Update wallet
        user.setWalletCoins(user.getWalletCoins() + AD_REWARD_COINS);
        userRepository.save(user);

        // Record Ad Reward
        AdReward adReward = new AdReward();
        adReward.setUser(user);
        adReward.setAdType(adType);
        adReward.setRewardCoins(AD_REWARD_COINS);
        adRewardRepository.save(adReward);

        // Record Transaction
        WalletTransaction transaction = new WalletTransaction();
        transaction.setUser(user);
        transaction.setAmount(AD_REWARD_COINS);
        transaction.setType(WalletTransaction.TransactionType.ad_watch);
        transaction.setDescription("Watched " + adType + " ad");
        walletTransactionRepository.save(transaction);
    }

    public int watchRewardedAd(Long userId, String adType) {
        if (!canWatchAd(userId)) {
            throw new RuntimeException("Daily ad limit reached");
        }
        recordAdWatch(userId, adType);
        return getRemainingAds(userId);
    }

    public boolean canWatchAd(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        int adsWatched = adRewardRepository.countByUserIdAndWatchedAtAfter(userId, startOfDay);
        return adsWatched < 5; // Limit to 5 ads per day
    }

    public int getRemainingAds(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        int adsWatched = adRewardRepository.countByUserIdAndWatchedAtAfter(userId, startOfDay);
        return Math.max(0, 5 - adsWatched);
    }

    @Transactional
    public void processDailyLogin(Long userId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        if (walletTransactionRepository.existsByUserIdAndTypeAndCreatedAtAfter(userId, WalletTransaction.TransactionType.daily_login, startOfDay)) {
            throw new RuntimeException("Daily bonus already claimed today");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setWalletCoins(user.getWalletCoins() + DAILY_BONUS);
        userRepository.save(user);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setUser(user);
        transaction.setAmount(DAILY_BONUS);
        transaction.setType(WalletTransaction.TransactionType.daily_login);
        transaction.setDescription("Daily Login Bonus");
        walletTransactionRepository.save(transaction);
    }
}