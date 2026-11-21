package com.example.dto;

public class ReputationPointsDTO {

    private Long userId;
    private long positivePoints;
    private long negativePoints;

    public ReputationPointsDTO(Long userId, long positivePoints, long negativePoints) {
        this.userId = userId;
        this.positivePoints = positivePoints;
        this.negativePoints = negativePoints;
    }

    public Long getUserId() {
        return userId;
    }

    public long getPositivePoints() {
        return positivePoints;
    }

    public long getNegativePoints() {
        return negativePoints;
    }
}