package com.example.dto;

public class ReputationLevelDTO {

    private Long userId;
    private int reputation;
    private String level;

    public ReputationLevelDTO(Long userId, int reputation, String level) {
        this.userId = userId;
        this.reputation = reputation;
        this.level = level;
    }

    public Long getUserId() {
        return userId;
    }

    public int getReputation() {
        return reputation;
    }

    public String getLevel() {
        return level;
    }
}
