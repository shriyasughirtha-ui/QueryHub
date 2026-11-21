package com.example.dto;

import java.util.List;

public class ReputationReportDTO {

    private Long userId;
    private int reputation;
    private String reputationLevel;
    private long positivePoints;
    private long negativePoints;
    private long netPoints;
    private long reputationEvents;
    private List<ReputationBreakdownDTO> breakdown;

    public ReputationReportDTO(
            Long userId,
            int reputation,
            String reputationLevel,
            long positivePoints,
            long negativePoints,
            long netPoints,
            long reputationEvents,
            List<ReputationBreakdownDTO> breakdown) {

        this.userId = userId;
        this.reputation = reputation;
        this.reputationLevel = reputationLevel;
        this.positivePoints = positivePoints;
        this.negativePoints = negativePoints;
        this.netPoints = netPoints;
        this.reputationEvents = reputationEvents;
        this.breakdown = breakdown;
    }

    public Long getUserId() {
        return userId;
    }

    public int getReputation() {
        return reputation;
    }

    public String getReputationLevel() {
        return reputationLevel;
    }

    public long getPositivePoints() {
        return positivePoints;
    }

    public long getNegativePoints() {
        return negativePoints;
    }

    public long getNetPoints() {
        return netPoints;
    }

    public long getReputationEvents() {
        return reputationEvents;
    }

    public List<ReputationBreakdownDTO> getBreakdown() {
        return breakdown;
    }
}