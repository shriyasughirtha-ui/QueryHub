package com.example.dto;

public class ReputationAnalyticsDTO {

    private Long userId;
    private int reputation;
    private long positivePoints;
    private long negativePoints;
    private long reputationEvents;
    private String reputationLevel;

    public ReputationAnalyticsDTO(
            Long userId,
            int reputation,
            long positivePoints,
            long negativePoints,
            long reputationEvents,
            String reputationLevel) {

        this.userId = userId;
        this.reputation = reputation;
        this.positivePoints = positivePoints;
        this.negativePoints = negativePoints;
        this.reputationEvents = reputationEvents;
        this.reputationLevel = reputationLevel;
    }

    public Long getUserId() {
        return userId;
    }

    public int getReputation() {
        return reputation;
    }

    public long getPositivePoints() {
        return positivePoints;
    }

    public long getNegativePoints() {
        return negativePoints;
    }

    public long getReputationEvents() {
        return reputationEvents;
    }

    public String getReputationLevel() {
        return reputationLevel;
    }
}
