package com.example.dto;

public class ReputationSummaryDTO {

    private Long userId;
    private int reputation;
    private long reputationHistoryCount;

    public ReputationSummaryDTO(Long userId, int reputation, long reputationHistoryCount) {
        this.userId = userId;
        this.reputation = reputation;
        this.reputationHistoryCount = reputationHistoryCount;
    }

    public Long getUserId() {
        return userId;
    }

    public int getReputation() {
        return reputation;
    }

    public long getReputationHistoryCount() {
        return reputationHistoryCount;
    }
}
