package com.example.jokegenerator.repository;

import com.example.jokegenerator.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    boolean existsByUserIdAndTypeAndCreatedAtAfter(Long userId, WalletTransaction.TransactionType type, LocalDateTime createdAt);
}