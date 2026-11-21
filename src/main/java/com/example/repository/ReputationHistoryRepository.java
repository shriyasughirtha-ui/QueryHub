package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.ReputationHistory;

public interface ReputationHistoryRepository extends JpaRepository<ReputationHistory, Long> {

    List<ReputationHistory> findByUserId(Long userId);

    long countByUserId(Long userId);

    long countByUserIdAndReasonContainingIgnoreCase(Long userId, String reason);

    @Query("SELECT COALESCE(SUM(r.changeAmount), 0) FROM ReputationHistory r WHERE r.user.id = :userId AND r.changeAmount > :amount")
    long sumPositivePoints(
            @Param("userId") Long userId,
            @Param("amount") int amount
    );

    @Query("SELECT COALESCE(SUM(r.changeAmount), 0) FROM ReputationHistory r WHERE r.user.id = :userId AND r.changeAmount < :amount")
    long sumNegativePoints(
            @Param("userId") Long userId,
            @Param("amount") int amount
    );
}