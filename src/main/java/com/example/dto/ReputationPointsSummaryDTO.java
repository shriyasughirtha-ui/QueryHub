package com.example.dto;

public class ReputationPointsSummaryDTO {

    private Long userId;
    private long totalPositivePoints;
    private long totalNegativePoints;
    private long netPoints;

    public ReputationPointsSummaryDTO(
            Long userId,
            long totalPositivePoints,
            long totalNegativePoints,
            long netPoints) {

        this.userId = userId;
        this.totalPositivePoints = totalPositivePoints;
        this.totalNegativePoints = totalNegativePoints;
        this.netPoints = netPoints;
    }

    public Long getUserId() {
        return userId;
    }

    public long getTotalPositivePoints() {
        return totalPositivePoints;
    }

    public long getTotalNegativePoints() {
        return totalNegativePoints;
    }

    public long getNetPoints() {
        return netPoints;
    }
}
